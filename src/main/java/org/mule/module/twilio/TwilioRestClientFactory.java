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

import com.twilio.sdk.TwilioRestClient;

public class TwilioRestClientFactory {

    public static ITwilioRestClient getClient(String accountSid, String authToken) {
        if (accountSid.equals("THIS IS A TEST") && authToken.equals("THIS IS A TEST")) {
            return new FakeTwilioRestClient();
        }
        return new TwilioRestClient(accountSid, authToken, null);
    }
}