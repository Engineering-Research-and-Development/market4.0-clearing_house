package it.eng.idsa.clearinghouse.client.config;

public interface HLFConfigProperties {

    String CONFIG_PROPERTIES = "config.properties";

    String getOrganizationName();

    String getChannelName();

    String getUserName();

    String getChaincodeName();

    String getCertFilename();

    String getKeystoreFilename();

    String getNetworkFilename();

    String getWalletPath();
}
