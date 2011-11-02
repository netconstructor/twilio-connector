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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Verb {

    public static final String V_SAY = "Say";
    public static final String V_PLAY = "Play";
    public static final String V_GATHER = "Gather";
    public static final String V_RECORD = "Record";
    public static final String V_PAUSE = "Pause";
    public static final String V_HANGUP = "Hangup";
    public static final String V_DIAL = "Dial";
    public static final String V_NUMBER = "Number";
    public static final String V_REDIRECT = "Redirect";
    public static final String V_RESPONSE = "Response";
    public static final String V_CONFERENCE = "Conference";
    public static final String V_SMS = "Sms";
    public static final String V_REJECT = "Reject";

    protected String tag;
    protected String body;
    protected HashMap<String, String> attributes;
    protected List<Verb> children;

    protected List<String> allowedVerbs;

    public Verb(String tag, String body) {
        this.tag = tag;
        this.body = body;
        this.attributes = new HashMap<String, String>();
        this.children = new ArrayList<Verb>();
    }

    public Verb append(Verb verb) throws TwiMLException {
        if (allowedVerbs != null && allowedVerbs.contains(verb.getTag())) {
            children.add(verb);
            return verb;
        } else {
            throw new TwiMLException("This is not a supported verb");
        }
    }

    public String toXML() {
        String xml = "<" + this.tag;
        for (String key : attributes.keySet()) {
            xml += " " + key + "=\"" + attributes.get(key) + "\"";
        }
        xml += ">";
        if (this.body != null)
            xml += this.body;
        for (Verb child : children) {
            xml += child.toXML();
        }
        return xml += "</" + this.tag + ">";
    }

    public String asURL() {
        try {
            return URLEncoder.encode(toXML(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public void set(String key, String value) {
        attributes.put(key, value);
    }

    public String getBody() {
        return body;
    }

    public String getTag() {
        return tag;
    }

    public List<Verb> getChildren() {
        return children;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
