package org.mule.module.twilio;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;

import java.util.Map;

public interface ITwilioRestClient {

    TwilioRestResponse request(String path, String method, Map<String, String> vars) throws TwilioRestException;
}