/**
 * Mule Twilio Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.twilio.sdk.verbs;

public class Sms extends Verb {

    public Sms(String message) {
        super(V_SMS, message);
        this.allowedVerbs = null;
    }

    public void setTo(String str) {
        this.set("to", str);
    }

    public void setFrom(String str) {
        this.set("from", str);
    }

    public void setMethod(String method) {
        this.set("method", method);
    }

    public void setAction(String url) {
        this.set("action", url);
    }

    public void setStatusCallback(String url) {
        this.set("statusCallback", url);
    }

}

