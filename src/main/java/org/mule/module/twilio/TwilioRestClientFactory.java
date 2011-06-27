package org.mule.module.twilio;

import com.twilio.sdk.TwilioRestClient;

public class TwilioRestClientFactory {

    public static ITwilioRestClient getClient(String accountSid, String authToken) {
        if (accountSid.equals("THIS IS A TEST") && authToken.equals("THIS IS A TEST")) {
            return new FakeTwilioRestClient();
        }
        return new TwilioRestClient(accountSid, authToken, null);
    }
}