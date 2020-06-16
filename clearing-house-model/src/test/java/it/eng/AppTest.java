package it.eng;

import de.fraunhofer.iais.eis.*;
import it.eng.idsa.clearinghouse.model.Body;
import it.eng.idsa.clearinghouse.model.NotificationContent;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testRejectionMessageModel() {
        RejectionMessageBuilder messageBuilder = new RejectionMessageBuilder();
        RejectionMessage rejectionMessage = messageBuilder._rejectionReason_(RejectionReason.TEMPORARILY_NOT_AVAILABLE).build();
        Body body = new Body(rejectionMessage, "test");
        de.fraunhofer.iais.eis.LogNotification header = new LogNotificationBuilder().build();
        NotificationContent notificationContent = new NotificationContent(header, body);
        RejectionMessage rejectionMessageExpected = (RejectionMessage) notificationContent.getBody().getHeader();
        Assert.assertEquals(rejectionMessageExpected.getRejectionReason(), rejectionMessage.getRejectionReason());
    }

    @Test
    public void testQueryMessageModel() {
        QueryMessageBuilder messageBuilder = new QueryMessageBuilder();
        QueryMessage queryMessage = messageBuilder._queryLanguage_(QueryLanguage.SPARQL).build();
        Body body = new Body(queryMessage, "test");
        de.fraunhofer.iais.eis.LogNotification header = new LogNotificationBuilder().build();
        NotificationContent notificationContent = new NotificationContent(header, body);
        QueryMessage queryMessageExpected = (QueryMessage) notificationContent.getBody().getHeader();
        Assert.assertEquals(queryMessageExpected.getQueryLanguage(), queryMessage.getQueryLanguage());
    }


}
