package it.eng.idsa.clearinghouse.client.impl;

import it.eng.idsa.clearinghouse.client.ClearingHouseService;
import it.eng.idsa.clearinghouse.client.config.HLFConfigProperties;
import it.eng.idsa.clearinghouse.client.config.HLFConfigPropertiesBean;
import it.eng.idsa.clearinghouse.client.utils.FabricUtils;
import it.eng.idsa.clearinghouse.client.utils.TimezoneRemover;
import it.eng.idsa.clearinghouse.model.NotificationContent;
import it.eng.idsa.clearinghouse.model.json.JsonHandler;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.sql.Time;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

public class ClearingHouseServiceImpl implements ClearingHouseService {
    private static final Logger log = LoggerFactory.getLogger(ClearingHouseServiceImpl.class);

    public static final String CREATE_METHOD = "createNotificationContent";
    public static final String GET_METHOD = "getNotificationContent";
    public static final String GET_CORRELATED_METHOD = "getNotificationContents";
    public static final String GET_ALL_METHOD = "getAllNotificationContents";

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private HLFConfigProperties hlfConfigProperties;
    private Wallet wallet;
    private Gateway.Builder gatewayBuilder;
    private Path networkConfigFile;


    public ClearingHouseServiceImpl() throws Exception {
        hlfConfigProperties = new HLFConfigPropertiesBean();
        connect();
    }

    public ClearingHouseServiceImpl(HLFConfigProperties hlfConfigProperties) throws Exception {
        this.hlfConfigProperties = hlfConfigProperties;
        if (null == hlfConfigProperties) {
            this.hlfConfigProperties = new HLFConfigPropertiesBean();
        }
        connect();
    }

    private void connect() throws Exception {
        String walletPathProp = hlfConfigProperties.getWalletPath();
        Path walletPath = Paths.get(walletPathProp);
        wallet = Wallet.createFileSystemWallet(walletPath);
        networkConfigFile = Paths.get(walletPathProp, hlfConfigProperties.getNetworkFilename());

        final String cert = FabricUtils.readLineByLine("", hlfConfigProperties.getCertFilename());
        byte[] keyBytes = Files.readAllBytes(Paths.get("", hlfConfigProperties.getKeystoreFilename()));

        final PrivateKey key = FabricUtils.getPrivateKeyFromBytes(keyBytes);
        Wallet.Identity user = Wallet.Identity.createIdentity(hlfConfigProperties.getOrganizationName(), cert, key);
        String userName = hlfConfigProperties.getUserName();
        wallet.put(userName, user);
        gatewayBuilder = Gateway.createBuilder()
                .identity(wallet, userName)
                .networkConfig(networkConfigFile);
    }

    private Contract getContract() throws Exception {
        try {
            Gateway gateway = gatewayBuilder.connect();
            Network network = gateway.getNetwork(hlfConfigProperties.getChannelName());
            return network.getContract(hlfConfigProperties.getChaincodeName());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void registerNotification(NotificationContent notificationContent) throws Exception {
        if (notificationContent != null
                && notificationContent.getBody() != null
                && notificationContent.getBody().getPayload() != null &&
                notificationContent.getBody().getPayload() != null &&
                notificationContent.getBody().getPayload().length() > 64)
            throw new Exception("Message Body Payload should be in HASH format! Max length allowed 64 characters");
        Contract contract = getContract();
        byte[] registerResult = contract.createTransaction(CREATE_METHOD)
                .submit(JsonHandler.convertToJson(notificationContent));
        log.info(new String(registerResult, StandardCharsets.UTF_8));
    }


    @Override
    public NotificationContent retrieveNotification(String id) throws Exception {
        Contract contract = getContract();
        final byte[] retrieveTransactions = contract.createTransaction(GET_METHOD).submit(id);
        String retrTransactionString = new String(retrieveTransactions, StandardCharsets.UTF_8);
        return (NotificationContent) JsonHandler.
                convertFromJson(TimezoneRemover.removeTimezoneFromIssued(retrTransactionString), NotificationContent.class);
    }

    @Override
    public List<NotificationContent> retrieveNotifications(String correlatedMessageId) throws Exception {
        Contract contract = getContract();
        List<NotificationContent> notificationContents = null;
        final byte[] retrieveTransactions = contract.createTransaction(GET_CORRELATED_METHOD).submit(correlatedMessageId);
        String retrTransactionString = new String(retrieveTransactions, StandardCharsets.UTF_8);
        String[] notifications = (String[]) JsonHandler.convertFromJson(retrTransactionString, String[].class);
        if (notifications != null && notifications.length > 0) {
            notificationContents = new ArrayList<>();
            for (String notification : notifications) {
                notificationContents.add((NotificationContent) JsonHandler.
                        convertFromJson(TimezoneRemover.removeTimezoneFromIssued(notification), NotificationContent.class));
            }
        }
        return notificationContents;
    }

    @Override
    public List<NotificationContent> retrieveAllNotifications() throws Exception {
        Contract contract = getContract();
        List<NotificationContent> notificationContents = null;
        final byte[] retrieveTransactions = contract.createTransaction(GET_ALL_METHOD).submit();
        String retrTransactionString = new String(retrieveTransactions, StandardCharsets.UTF_8);
        String[] notifications = (String[]) JsonHandler.convertFromJson(retrTransactionString, String[].class);
        if (notifications != null && notifications.length > 0) {
            notificationContents = new ArrayList<>();
            for (String notification : notifications) {
                notificationContents.add((NotificationContent) JsonHandler.
                        convertFromJson(TimezoneRemover.removeTimezoneFromIssued(notification), NotificationContent.class));
            }
        }
        return notificationContents;
    }

}
