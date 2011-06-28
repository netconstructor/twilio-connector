package org.mule.module.twilio;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;

import java.util.Map;

public class FakeTwilioRestClient implements ITwilioRestClient {

    @Override
    public TwilioRestResponse request(String path, String method, Map<String, String> vars) throws TwilioRestException {
        return new FakeTwilioResponse(path, method, vars);
    }

    static final class FakeTwilioResponse extends TwilioRestResponse {

        // build a fake response from twilio using the url requested, the method and the arguments
        FakeTwilioResponse(String path, String method, Map<String, String> vars) {
            super(path, method + " " + path + " " + vars, 0);
        }
    }
}
