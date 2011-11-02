/**
 * Mule Twilio Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.twilio.sdk;

public class TwilioRestException extends Exception {

    public TwilioRestException(String arg0) {
        super(arg0);
    }

    public TwilioRestException(Exception e) {
        super(e);
    }
}