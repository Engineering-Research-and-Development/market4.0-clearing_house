import it.eng.idsa.clearinghouse.client.ClearingHouseService;
import it.eng.idsa.clearinghouse.client.impl.ClearingHouseServiceImpl;
import org.junit.After;
import org.junit.Before;

import java.util.Random;

public class End2EndTest {
    private ClearingHouseService clearingHouseService;
    private Random random;

    @Before
    public void setup() {
        try {
            random = new Random();
             clearingHouseService = new ClearingHouseServiceImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        clearingHouseService = null;
        random = null;
    }

//    @Test
//    public void register() {
//        try {
//            GregorianCalendar gcal = new GregorianCalendar();
//            XMLGregorianCalendar xgcal = DatatypeFactory.newInstance()
//                    .newXMLGregorianCalendar(gcal);
//            String id_ = "https://w3id.org/idsa/autogen/brokerQueryMessage/6bed5855-489b-4f47-82dc-08c5f1656101" + random.nextInt(100);
//            URI id = new URI(id_);
//            URI connector = new URI("https://ids.tno.nl/test");
//            Message message = new MessageBuilder(id
//            )
//                    ._modelVersion_("1.0.3")
//                    ._issued_(xgcal)
//                    ._correlationMessage_(id)
//                    ._issuerConnector_(connector)
//                    ._recipientConnector_(null)
//                    ._senderAgent_(null)
//                    ._recipientAgent_(null)
//                    ._transferContract_(null)
//                    .build();
//            String msgSerialized = new Serializer().serializePlainJson(message);
//            clearingHouseService.registerTransaction(msgSerialized);
//            final LedgerMessage message1 = clearingHouseService.retrieveTransaction(message.getId().toString());
//            Assert.assertTrue("Succesfully completed", true);
//
//        } catch (Exception e) {
//            Assert.fail(e.getMessage());
//
//        }
//    }
}