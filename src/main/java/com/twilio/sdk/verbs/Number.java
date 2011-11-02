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

public class Number extends Verb {

    public Number(String number) {
        super(V_NUMBER, number);
        this.allowedVerbs = null;
    }

    public void setSendDigits(String digits) {
        this.set("sendDigits", digits);
    }

    public void setUrl(String url) {
        this.set("url", url);
    }

}

