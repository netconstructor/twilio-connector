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

public class Say extends Verb {

    public Say(String body) {
        super(V_SAY, body);
        this.allowedVerbs = null;
    }

    public void setLoop(int i) {
        this.set("loop", Integer.toString(i));
    }

    public void setLanguage(String str) {
        this.set("language", str);
    }

    public void setVoice(String str) {
        this.set("voice", str);
    }

}

