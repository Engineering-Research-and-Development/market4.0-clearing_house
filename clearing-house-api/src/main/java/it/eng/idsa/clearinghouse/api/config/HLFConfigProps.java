package it.eng.idsa.clearinghouse.api.config;

import it.eng.idsa.clearinghouse.client.config.HLFConfigProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class HLFConfigProps implements HLFConfigProperties {

    @Value("${fabric.wallet.path}")
    private String walletPath;

    @Value("${fabric.organization.name}")
    private String organizationName;

    @Value("${fabric.channel.name}")
    private String channelName;

    @Value("${fabric.user.name}")
    private String userName;

    @Value("${fabric.chaincode.name}")
    private String chaincodeName;

    @Value("${fabric.cert.filename}")
    private String certFilename;

    @Value("${fabric.keystore.filename}")
    private String keystoreFilename;

    @Value("${fabric.network.filename}")
    private String networkFilename;


}

