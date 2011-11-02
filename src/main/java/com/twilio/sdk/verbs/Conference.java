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

public class Conference extends Verb {

    public Conference(String name) {
        super(V_CONFERENCE, name);
        this.allowedVerbs = null;
    }

    private void setBoolean(String attr, Boolean bool) {
        if (bool)
            this.set(attr, "true");
        else
            this.set(attr, "false");
    }

    public void setMuted(Boolean bool) {
        this.setBoolean("muted", bool);
    }

    public void setBeep(Boolean bool) {
        this.setBoolean("beep", bool);
    }

    public void setStartConferenceOnEnter(Boolean bool) {
        this.setBoolean("startConferenceOnEnter", bool);
    }

    public void setEndConferenceOnExit(Boolean bool) {
        this.setBoolean("endConferenceOnExit", bool);
    }

    public void setWaitMethod(String method) {
        this.set("waitMethod", method);
    }

    public void setWaitUrl(String url) {
        this.set("waitUrl", url);
    }

}

