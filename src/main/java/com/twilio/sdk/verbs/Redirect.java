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

public class Redirect extends Verb {

    public Redirect() {
        super(V_REDIRECT, null);
        this.allowedVerbs = null;
    }

    public Redirect(String url) {
        super(V_REDIRECT, url);
        this.allowedVerbs = null;
    }

    public void setMethod(String method) {
        this.set("method", method);
    }

}

