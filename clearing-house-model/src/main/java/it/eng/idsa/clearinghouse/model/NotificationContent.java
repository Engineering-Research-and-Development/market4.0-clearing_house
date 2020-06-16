package it.eng.idsa.clearinghouse.model;

import de.fraunhofer.iais.eis.*;

public class NotificationContent {

    private LogNotification header;
    private Body body;


    public NotificationContent() {
    }

    public NotificationContent(LogNotification header, Body body) {
        build(header);
        this.body = body;
    }

    private void build(LogNotification header) {
        this.header = new LogNotificationBuilder(header.getId())
                ._modelVersion_(header.getModelVersion())
                ._issued_(header.getIssued())
                ._correlationMessage_(header.getCorrelationMessage())
                ._issuerConnector_(header.getIssuerConnector())
                ._recipientConnector_(header.getRecipientConnector())
                ._senderAgent_(header.getSenderAgent())
                ._recipientAgent_(null)
                ._transferContract_(header.getTransferContract())
                ._securityToken_(null) //mandatory in SPECS but non suitable for Blockchain
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

    public void setHeader(LogNotification header) {
        build(header);
    }

    public void setBody(Body body) {
        this.body = body;
    }


}