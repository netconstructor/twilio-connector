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

public class TwilioConnectorException extends RuntimeException {

    private static final long serialVersionUID = 5578855335572348078L;

    public TwilioConnectorException(TwilioRestResponse twilioRestResponse) {
        super(twilioRestResponse.toString());
    }

    public TwilioConnectorException(TwilioRestException twilioRestException) {
        super(twilioRestException);
    }

    public TwilioConnectorException(String message) {
        super(message);
    }
}