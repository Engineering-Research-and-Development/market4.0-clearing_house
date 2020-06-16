package it.eng.idsa.clearinghouse.client.config;

import it.eng.idsa.clearinghouse.client.utils.FabricUtils;

public class HLFConfigPropertiesBean implements HLFConfigProperties {

    public final static String ORGANIZATION_NAME = "fabric-organization-name";
    public final static String CHANNEL_NAME = "fabric-channel-name";
    public final static String USER_NAME = "fabric-user-name";
    public final static String CHAINCODE_NAME = "fabric-chaincode-name";
    public final static String CERT_FILENAME = "fabric-cert-filename";
    public final static String KEYSTORE_FILENAME = "fabric-keystore-filename";
    public final static String NETWORK_FILENAME = "fabric-network-filename";
    public final static String WALLET_PATH = "fabric-wallet-path";


    public HLFConfigPropertiesBean() {
    }

    public String getOrganizationName() {
        return FabricUtils.readProperty(ORGANIZATION_NAME);
    }

    public String getChannelName() {
        return FabricUtils.readProperty(CHANNEL_NAME);
    }

    public String getUserName() {
        return FabricUtils.readProperty(USER_NAME);
    }

    public String getChaincodeName() {
        return FabricUtils.readProperty(CHAINCODE_NAME);
    }

    public String getCertFilename() {
        return FabricUtils.readProperty(CERT_FILENAME);
    }

    public String getKeystoreFilename() {
        return FabricUtils.readProperty(KEYSTORE_FILENAME);
    }

    public String getNetworkFilename() {
        return FabricUtils.readProperty(NETWORK_FILENAME);
    }

    public String getWalletPath() {
        return FabricUtils.readProperty(WALLET_PATH);
    }


}
