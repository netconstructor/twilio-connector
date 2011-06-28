package org.mule.module.twilio;

import com.twilio.sdk.TwilioRestResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TwilioRequestExecutorTest {

    private static final String PATH = "some path";
    private static final String RESPONSE_TEXT = "response text";
    private static final Map<String, String> NO_PARAMS = Collections.emptyMap();
    private TwilioRequestExecutor twilioRequestExecutor;
    private TwilioParameters twilioParams1, twilioParams2;
    @Mock
    private ITwilioRestClient mockTwilioRestClient;
    @Mock
    private TwilioRestResponse mockTwilioResponse;

    @Before
    public void setUpTests() {
        MockitoAnnotations.initMocks(this);
        twilioRequestExecutor = new TwilioRequestExecutor(mockTwilioRestClient);
        twilioParams1 = new TwilioParameters(TwilioParameters.TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParameters.TwilioParamater.ACCOUNT_SID, "abc");
        twilioParams2 = new TwilioParameters(TwilioParameters.TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParameters.TwilioParamater.API_VERSION, "def");
        when(mockTwilioResponse.getResponseText()).thenReturn(RESPONSE_TEXT);
    }

    @Test
    public void testExecuteGetRequestNoParams() throws Exception {
        when(mockTwilioRestClient.request(PATH, "GET", NO_PARAMS)).thenReturn(mockTwilioResponse);
        assertEquals(RESPONSE_TEXT, twilioRequestExecutor.executeGetRequestNoParams(PATH));
    }

    @Test
    public void testExecuteGetRequest() throws Exception {
        when(mockTwilioRestClient.request(PATH, "GET", twilioParams1.getMap())).thenReturn(mockTwilioResponse);
        assertEquals(RESPONSE_TEXT, twilioRequestExecutor.executeGetRequest(PATH, twilioParams1));
    }

    @Test
    public void testExecutePostRequest() throws Exception {
        when(mockTwilioRestClient.request(PATH, "POST", twilioParams1.getMap())).thenReturn(mockTwilioResponse);
        assertEquals(RESPONSE_TEXT, twilioRequestExecutor.executePostRequest(PATH, twilioParams1));
    }

    @Test
    public void testExecutePostRequestWithMultipleTwilioParamters() throws Exception {
        Map<String, String> expectedParamsToCallRestClient = new HashMap<String, String>();
        expectedParamsToCallRestClient.putAll(twilioParams1.getMap());
        expectedParamsToCallRestClient.putAll(twilioParams2.getMap());
        when(mockTwilioRestClient.request(PATH, "POST", expectedParamsToCallRestClient)).thenReturn(mockTwilioResponse);
        assertEquals(RESPONSE_TEXT, twilioRequestExecutor.executePostRequest(PATH, twilioParams1, twilioParams2));
    }

    @Test
    public void testExecuteDeleteRequest() throws Exception {
        when(mockTwilioRestClient.request(PATH, "DELETE", NO_PARAMS)).thenReturn(mockTwilioResponse);
        assertEquals(RESPONSE_TEXT, twilioRequestExecutor.executeDeleteRequest(PATH));
    }
}