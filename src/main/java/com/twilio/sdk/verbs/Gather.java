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

import java.util.ArrayList;

public class Gather extends Verb {

    public Gather() {
        super(V_GATHER, null);
        this.allowedVerbs = new ArrayList<String>();
        this.allowedVerbs.add(Verb.V_SAY);
        this.allowedVerbs.add(Verb.V_PLAY);
        this.allowedVerbs.add(Verb.V_PAUSE);
    }

    public void setAction(String url) {
        this.set("action", url);
    }

    public void setMethod(String method) {
        this.set("method", method);
    }

    public void setTimeout(int i) {
        this.set("timeout", Integer.toString(i));
    }

    public void setFinishOnKey(String key) {
        this.set("finishOnKey", key);
    }

    public void setNumDigits(int i) {
        this.set("numDigits", Integer.toString(i));
    }

}

