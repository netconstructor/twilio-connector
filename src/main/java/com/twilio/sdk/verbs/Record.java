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

public class Record extends Verb {

    public Record() {
        super(V_RECORD, null);
        this.allowedVerbs = null;
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

    public void setMaxLength(int i) {
        this.set("maxLength", Integer.toString(i));
    }

    public void setTranscribe(boolean f) {
        if (f)
            this.set("transcribe", "true");
        else
            this.set("transcribe", "false");
    }

    public void setTranscribeCallback(String url) {
        this.set("transcribeCallback", url);
    }

}

