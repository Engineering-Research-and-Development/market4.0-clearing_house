package it.eng.idsa.clearinghouse.client.utils;

import de.fraunhofer.iais.eis.Message;
import de.fraunhofer.iais.eis.ids.jsonld.Serializer;
import it.eng.idsa.clearinghouse.client.config.HLFConfigProperties;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class FabricUtils {

    private static final Logger log = LoggerFactory.getLogger(FabricUtils.class);

    public static Map<String, Message> parseMessage(String message) throws IOException {
        de.fraunhofer.iais.eis.Message messageObj = new Serializer().deserialize(message, de.fraunhofer.iais.eis.Message.class);
        Map<String, de.fraunhofer.iais.eis.Message> messageMap = new HashMap<>();
        messageMap.put(messageObj.getId().toString(), messageObj); //USE ID ad key
        return messageMap;
    }

    public static String readProperty(String propName) {
        try {
            Properties prop = new Properties();
            String propFileName = HLFConfigProperties.CONFIG_PROPERTIES;
            InputStream inputStream = FabricUtils.class.getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            if (prop.containsKey(propName)) {
                return prop.getProperty(propName);
            } else {
                throw new Exception("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static String readLineByLine(String dirPath, String certFileName) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(dirPath, certFileName), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));
        return contentBuilder.toString();
    }


    public static PrivateKey getPrivateKeyFromBytes(byte[] data) throws NoSuchAlgorithmException,
            IOException, InvalidKeySpecException {
        final Reader pemReader = new StringReader(new String(data));
        final PrivateKeyInfo pemPair;
        try (PEMParser pemParser = new PEMParser(pemReader)) {
            pemPair = (PrivateKeyInfo) pemParser.readObject();
        }
        Security.addProvider(new BouncyCastleProvider());
        return new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
                .getPrivateKey(pemPair);
    }

}
