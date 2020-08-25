package it.eng.idsa.clearinghouse.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fraunhofer.iais.eis.LogMessage;
import de.fraunhofer.iais.eis.LogMessageBuilder;
import de.fraunhofer.iais.eis.Message;
import de.fraunhofer.iais.eis.NotificationMessageBuilder;
import it.eng.idsa.clearinghouse.api.rest.ClearingHouseApi;
import it.eng.idsa.clearinghouse.model.Body;
import it.eng.idsa.clearinghouse.model.NotificationContent;
import it.eng.idsa.clearinghouse.model.json.JsonHandler;
import lombok.extern.java.Log;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.GregorianCalendar;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
    //MESSAGE
    {
      "@context" : "https://w3id.org/idsa/contexts/context.jsonld",
      "@type" : "ids:BrokerQueryMessage",
      "modelVersion" : "1.0.2",
      "issued" : "2019-07-18T11:51:03.604+02:00",
      "issuerConnector" : "https://ids.tno.nl/test",
      "@id" : "https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101"
    }

    //SERIALIZED OBJECT
    {
	"header": {
		"@type": "ids:LogNotification",
		"modelVersion": "1.0.3",
		"issued": "2020-03-19T08:58:07.945Z",
		"correlationMessage": "https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101",
		"issuerConnector": "https://ids.tno.nl/test",
		"recipientConnector": null,
		"senderAgent": null,
		"transferContract": null,
		"contentVersion": null,
		"securityToken": null,
		"recipientAgent": null,
		"authorizationToken": null,
		"@id": "https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101-457"
	},
	"body": {
		"header": {
			"@type": "ids:Message",
			"modelVersion": "1.0.3",
			"issued": "2020-03-19T08:58:07.945Z",
			"correlationMessage": "https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101",
			"issuerConnector": "https://ids.tno.nl/test",
			"recipientConnector": null,
			"senderAgent": null,
			"transferContract": null,
			"contentVersion": null,
			"securityToken": null,
			"recipientAgent": null,
			"authorizationToken": null,
			"@id": "https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101-457"
		},
		"payload": "http://109.232.32.193:8280/swagger-ui.html"
	}
}
*/

@Log
@RunWith(SpringRunner.class)
@WebMvcTest(ClearingHouseApi.class)
class ClearingHouseApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper om = new ObjectMapper();

    @MockBean
    private ClearingHouseApi clearingHouseApi;
    private Random rand = new Random();


    NotificationContent createLogNotification() {
        LogMessage header = null;
        GregorianCalendar gcal = new GregorianCalendar();
        XMLGregorianCalendar xgcal = null;
        try {
            xgcal = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gcal);
            URI id = new URI("https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101-" +
                    Math.abs(rand.nextInt(1000)));
            header = new LogMessageBuilder(id)
                    ._modelVersion_("1.0.3")
                    ._issued_(xgcal)
                    ._correlationMessage_(new URI("https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101"))
                    ._issuerConnector_(new URI("https://ids.tno.nl/test"))
                    ._recipientConnector_(null)
                    ._senderAgent_(null)
                    ._recipientAgent_(null)
                    ._transferContract_(null)
                    .build();
           Message headerBody = new NotificationMessageBuilder(id)
                    ._modelVersion_("1.0.3")
                    ._issued_(xgcal)
                    ._correlationMessage_(new URI("https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101"))
                    ._issuerConnector_(new URI("https://ids.tno.nl/test"))
                    ._recipientConnector_(null)
                    ._senderAgent_(null)
                    ._recipientAgent_(null)
                    ._transferContract_(null)
                    .build();
            NotificationContent notificationContent = new NotificationContent(header, new Body(headerBody, "http://109.232.32.193:8280/swagger-ui.html"));
            return notificationContent;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    @Test
    void postData() {
        try {
            NotificationContent notificationContent = createLogNotification();
            String objToString = JsonHandler.convertToJson(notificationContent);
            log.info("LogNotification Object to String ->\n " + objToString);
            final NotificationContent deserialize = (NotificationContent) JsonHandler.convertFromJson(objToString, NotificationContent.class);
            final NotificationContent deserializeLM = (NotificationContent) JsonHandler.convertFromJson(objToString, NotificationContent.class);

            mockMvc.perform(post("/data")
                    .content(objToString)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
