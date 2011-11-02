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

public class TwiMLResponseExample {

    public static void main(String[] args) {
        // Say, Dial, and Play
        TwiMLResponse response = new TwiMLResponse();
        Say say = new Say("Hello World");
        say.setVoice("man");
        say.setLoop(5);

        try {
            response.append(say);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        System.out.println(response.toXML());

        // Gather, Redirect
        response = new TwiMLResponse();
        Gather gather = new Gather();
        gather.setNumDigits(10);
        say = new Say("Press 1");
        Redirect redirect = new Redirect();

        try {
            gather.append(say);
            response.append(gather);
            response.append(redirect);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        System.out.println(response.toXML());

        // Conference
        response = new TwiMLResponse();
        Dial dial = new Dial();
        Conference conf = new Conference("MyRoom");
        conf.setBeep(true);

        try {
            dial.append(conf);
            response.append(dial);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        System.out.println(response.toXML());

        // Set an arbitrary attribute / value pair
        response = new TwiMLResponse();

        redirect = new Redirect();
        redirect.set("crazy", "delicious");

        try {
            response.append(redirect);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        System.out.println(response.toXML());

        // Set an arbitrary attribute / value pair
        response = new TwiMLResponse();

        Reject reject = new Reject();
        reject.setReason("busy");

        try {
            response.append(reject);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        System.out.println(response.toXML());

    }
}
