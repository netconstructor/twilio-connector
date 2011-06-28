package org.mule.module.twilio;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class TwilioRequestExecutor {

    private static final Logger LOGGER = Logger.getLogger(TwilioRequestExecutor.class);
    private ITwilioRestClient twilioRestClient;

    public TwilioRequestExecutor(String accountSid, String authToken) {
        twilioRestClient = TwilioRestClientFactory.getClient(accountSid, authToken);
    }

    TwilioRequestExecutor(ITwilioRestClient twilioRestClient) {
        this.twilioRestClient = twilioRestClient;
    }

    public String executeGetRequestNoParams(String path) {
        return executeGetRequest(path, TwilioParameters.NO_PARAMETERS);
    }

    public String executeGetRequest(String path, TwilioParameters twilioParameters) {
        return executeRequest(path, "GET", twilioParameters.getMap());
    }

    public String executePostRequest(String path, TwilioParameters... twilioParameters) {
        Map<String, String> parameters = new HashMap<String, String>();
        for (TwilioParameters eachTwilioParameters : twilioParameters) {
            if (eachTwilioParameters != null && eachTwilioParameters.getMap() != null) {
                parameters.putAll(eachTwilioParameters.getMap());
            }
        }
        return executeRequest(path, "POST", parameters);
    }

    public String executeDeleteRequest(String path) {
        return executeRequest(path, "DELETE", TwilioParameters.NO_PARAMETERS.getMap());
    }

    private String executeRequest(String path, String method, Map<String, String> vars) {
        try {
            TwilioRestResponse response = twilioRestClient.request(path, method, vars);
            LOGGER.debug("Response from Twilio: " + response);
            if (response.isError()) {
                throw new TwilioConnectorException(response);
            }
            return response.getResponseText();
        } catch (TwilioRestException e) {
            throw new TwilioConnectorException(e);
        }
    }
}