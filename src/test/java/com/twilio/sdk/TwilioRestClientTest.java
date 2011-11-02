package com.twilio.sdk;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TwilioRestClientTest {

    @Mock
    private HttpURLConnection httpURLConnection;
    private TwilioRestClient twilioRestClient;

    @Before
    public void setUpTests() throws Exception {
        MockitoAnnotations.initMocks(this);
        twilioRestClient = new LocalTwilioRestClient(httpURLConnection);
    }

    @Test
    public void testRequestSucessfulResponse() throws Exception {
        setServerResponse(200, "this is the server response");

        TwilioRestResponse twilioRestResponse = twilioRestClient.request("/test", "GET", createParamaters());

        assertEquals("this is the server response", twilioRestResponse.getResponseText());
        assertEquals(200, twilioRestResponse.getHttpStatus());
        assertEquals(TwilioRestClient.TWILIO_URL + "/test", twilioRestResponse.getUrl());
        assertEquals("name=fede&callbackUrl=http%3A%2F%2FmiUrl%2FmiPath", twilioRestResponse.getQueryString());
    }

    @Test
    public void testRequestErrorResponse() throws Exception {
        setErrorResponse(400, "an error ocurred");

        TwilioRestResponse twilioRestResponse = twilioRestClient.request("/test", "GET", createParamaters());

        assertEquals("an error ocurred", twilioRestResponse.getResponseText());
        assertEquals(400, twilioRestResponse.getHttpStatus());
        assertEquals(TwilioRestClient.TWILIO_URL + "/test", twilioRestResponse.getUrl());
        assertEquals("name=fede&callbackUrl=http%3A%2F%2FmiUrl%2FmiPath", twilioRestResponse.getQueryString());
    }

    @Test(expected = TwilioRestException.class)
    public void testRequestNoResponse() throws Exception {
        when(httpURLConnection.getInputStream()).thenReturn(null);
        twilioRestClient.request("/test", "GET", Collections.<String, String>emptyMap());
    }

    private void setServerResponse(int responseCode, String responseText) throws IOException {
        ByteArrayInputStream serverResponse = new ByteArrayInputStream(responseText.getBytes());
        when(httpURLConnection.getInputStream()).thenReturn(serverResponse);
        when(httpURLConnection.getResponseCode()).thenReturn(responseCode);
    }

    private void setErrorResponse(int responseCode, String errorText) throws IOException {
        ByteArrayInputStream errorResponse = new ByteArrayInputStream(errorText.getBytes());
        when(httpURLConnection.getInputStream()).thenThrow(new IOException());
        when(httpURLConnection.getErrorStream()).thenReturn(errorResponse);
        when(httpURLConnection.getResponseCode()).thenReturn(responseCode);
    }

    private Map<String, String> createParamaters() {
        Map<String, String> vars = new LinkedHashMap<String, String>(2);
        vars.put("name", "fede");
        vars.put("callbackUrl", "http://miUrl/miPath");
        return vars;
    }

    class LocalTwilioRestClient extends TwilioRestClient {

        private HttpURLConnection httpURLConnection;

        LocalTwilioRestClient(HttpURLConnection httpURLConnection) {
            super("", "", "");
            this.httpURLConnection = httpURLConnection;
        }

        @Override
        protected HttpURLConnection openConnection(String url) throws IOException {
            return httpURLConnection;
        }
    }
}