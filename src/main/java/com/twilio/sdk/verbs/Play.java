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

public class Play extends Verb {

    public Play(String body) {
        super("Play", body);
        this.allowedVerbs = null;
    }

    public void setLoop(int i) {
        this.set("loop", Integer.toString(i));
    }

}

