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

public class Dial extends Verb {

    public Dial() {
        super(V_DIAL, null);
        this.allowedVerbs = new ArrayList<String>();
        this.allowedVerbs.add(V_NUMBER);
        this.allowedVerbs.add(V_CONFERENCE);
    }

    public Dial(String number) {
        super(V_DIAL, number);
        this.allowedVerbs = new ArrayList<String>();
        this.allowedVerbs.add(V_NUMBER);
        this.allowedVerbs.add(V_CONFERENCE);
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

    public void setHangupOnStar(boolean f) {
        if (f)
            this.set("hangupOnStar", "true");
        else
            this.set("hangupOnStar", "false");
    }

    public void setTimeLimit(int i) {
        this.set("timeLimit", Integer.toString(i));
    }

    public void setCallerid(String callerid) {
        this.set("callerId", callerid);
    }

}

