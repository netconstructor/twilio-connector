/**
 * Mule Twilio Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

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
