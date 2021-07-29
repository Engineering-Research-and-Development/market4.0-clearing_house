package it.eng.idsa.clearinghouse.model;

import de.fraunhofer.iais.eis.DynamicAttributeToken;
import de.fraunhofer.iais.eis.DynamicAttributeTokenBuilder;
import de.fraunhofer.iais.eis.LogMessage;
import de.fraunhofer.iais.eis.LogMessageBuilder;
import de.fraunhofer.iais.eis.Message;
import de.fraunhofer.iais.eis.TokenFormat;

public class NotificationContent {

    private LogMessage header;
    private Body body;


    public NotificationContent() {
    }

    public NotificationContent(LogMessage header, Body body) {
        build(header);
        this.body = body;
    }

    private void build(LogMessage header) {
        this.header = new LogMessageBuilder(header.getId())
                ._modelVersion_(header.getModelVersion())
                ._issued_(header.getIssued())
                ._correlationMessage_(header.getCorrelationMessage())
                ._issuerConnector_(header.getIssuerConnector())
                ._recipientConnector_(header.getRecipientConnector())
                ._senderAgent_(header.getSenderAgent())
                ._transferContract_(header.getTransferContract())
                ._securityToken_(getDynamicAttributeToken()) //mandatory in SPECS but non suitable for Blockchain
                ._authorizationToken_(null)
                ._contentVersion_(null)
                .build();
    }

    public Message getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    public void setHeader(LogMessage header) {
        build(header);
    }

    public void setBody(Body body) {
        this.body = body;
    }
    
    private DynamicAttributeToken getDynamicAttributeToken() {
		return new DynamicAttributeTokenBuilder()
				._tokenFormat_(TokenFormat.JWT)
				._tokenValue_("DummyTokenValue")
				.build();		
	}


}