package org.mule.module.twilio;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;

import java.util.Map;

public class FakeTwilioRestClient implements ITwilioRestClient {

    @Override
    public TwilioRestResponse request(String path, String method, Map<String, String> vars) throws TwilioRestException {
        return new FakeTwilioResponse(path, method, vars);
    }

    class FakeTwilioResponse extends TwilioRestResponse {

        private String path;
        private String method;
        private Map<String, String> vars;

        FakeTwilioResponse(String path, String method, Map<String, String> vars) {
            super("this is a test", "this is a test", 0);
            this.path = path;
            this.method = method;
            this.vars = vars;
        }
    }
}