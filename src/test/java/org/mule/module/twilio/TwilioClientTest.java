package org.mule.module.twilio;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class TwilioClientTest {

    private TwilioClient twilioClient;

    @Mock
    private TwilioRequestExecutor mockTwilioRequestExecutor;

    @Before
    public void setUpTests() {
        MockitoAnnotations.initMocks(this);
        twilioClient = new TwilioClient(mockTwilioRequestExecutor);
    }

//    @Test
//    public void testGetAccount() throws Exception {
//        String requestedSid = "some requested sid";
//        String expectedUrlToUse = TwilioClient.PATH_PREFIX + "/Accounts/" + requestedSid;
//        twilioClient.getAccountDetails(requestedSid);
//        verify(mockTwilioRequestExecutor).executeGetRequest(expectedUrlToUse, TwilioParameters.NO_PARAMETERS);
 //   }

//    @Test
//    public void testGetAllCountsDetails() throws Exception {
//        String expectedUrlToUse = TwilioClient.PATH_PREFIX + "/Accounts";
//        twilioClient.getAllAcountsDetails("friendlyNameToSearch", AccountStatus.ACTIVE);
//        verify(mockTwilioRequestExecutor).executeGetRequest(expectedUrlToUse, eq(Map.class));
//    }
}