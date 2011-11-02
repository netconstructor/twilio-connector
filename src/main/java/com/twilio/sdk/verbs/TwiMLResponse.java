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

public class TwiMLResponse extends Verb {

    public TwiMLResponse() {
        super(V_RESPONSE, null);
        this.allowedVerbs = new ArrayList<String>();
        this.allowedVerbs.add(V_GATHER);
        this.allowedVerbs.add(V_RECORD);
        this.allowedVerbs.add(V_DIAL);
        this.allowedVerbs.add(V_SAY);
        this.allowedVerbs.add(V_PLAY);
        this.allowedVerbs.add(V_REDIRECT);
        this.allowedVerbs.add(V_REJECT);
        this.allowedVerbs.add(V_HANGUP);
        this.allowedVerbs.add(V_SMS);
    }

}

