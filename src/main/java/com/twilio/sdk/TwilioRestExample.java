/**
 * Mule Twilio Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.twilio.sdk;

import java.util.HashMap;
import java.util.Map;

public class TwilioRestExample {

    /* Twilio REST API version */
    public static final String APIVERSION = "2010-04-01";

    public static void main(String[] args) {

        /* Twilio AccountSid and AuthToken */
        String AccountSid = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        String AuthToken = "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY";

        /* Outgoing Caller ID previously validated with Twilio */
        String CallerID = "NNNNNNNNNN";

        /* Instantiate a new Twilio Rest Client */
        TwilioRestClient client = new TwilioRestClient(AccountSid, AuthToken, null);

        /*  
        * Initiate a new outbound call
        *         Is a POST to the Calls resource
        *         Returns a TwilioRestResponse object
        */
        makeCallExample(client, CallerID, "415-555-1212", "http://demo.twilio.com/welcome");

        System.out.println("========================================");

        /* 
        * get list of past calls
        *   is a GET to the Calls resources
        */
        getCallsExample(client);

        System.out.println("========================================");

        /* 
        * get recent developer notifications
        * is a GET to the Notifications resource
        */
        getNotificationsExample(client);

        System.out.println("========================================");

        /*
         * get the recording for call
         * is a GET to the Recordings resource 
         * with a parameter CallSid to filter results for recordings 
         * on that call object
         */
        getRecordingsExample(client, "CA123456789012345678901234567890AF");

        System.out.println("========================================");

        /* 
        * delete a specific recording
        *     is a DELETE to the Recordings resource
        */
        deleteRecordingsExample(client, "RC123456789012345678901234567890AF");

    }

    /**
     * Example of making an outgoing call.
     *
     * @param client     authenticated twilio client object
     * @param AccountSid your accountId
     * @param from       the caller id of the phone call
     * @param to         the phone number to be called
     * @param url        the URL to execute when the called party answers
     */
    private static void makeCallExample(TwilioRestClient client, String from, String to, String url) {

        //build map of post parameters 
        Map<String, String> params = new HashMap<String, String>();
        params.put("From", from);
        params.put("To", to);
        params.put("Url", url);
        TwilioRestResponse response;
        try {
            response = client.request("/" + APIVERSION + "/Accounts/" + client.getAccountSid() + "/Calls", "POST", params);

            if (response.isError())
                System.out.println("Error making outgoing call: " + response.getHttpStatus() + "\n" + response.getResponseText());
            else {
                System.out.println(response.getResponseText());

            }
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }

    /**
     * Example of retrieving recent Calls made on your account
     *
     * @param client authenticated twilio client object
     */
    private static void getCallsExample(TwilioRestClient client) {
        TwilioRestResponse response;
        try {
            response = client.request("/" + APIVERSION + "/Accounts/" + client.getAccountSid() + "/Calls", "GET", null);

            if (response.isError())
                System.out.println("Error fetching recent calls: " + response.getHttpStatus() + "\n" + response.getResponseText());
            else {
                System.out.println(response.getResponseText());
            }
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }

    /**
     * Example of retrieving recent error and warning notifications from your account
     *
     * @param client authenticated twilio client object
     */
    private static void getNotificationsExample(TwilioRestClient client) {
        TwilioRestResponse response;
        try {
            response = client.request("/" + APIVERSION + "/Accounts/" + client.getAccountSid() + "/Notifications", "GET", null);

            if (response.isError())
                System.out.println("Error fetching recent notifications: " + response.getHttpStatus() + "\n" + response.getResponseText());
            else {
                System.out.println(response.getResponseText());
            }
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }

    /**
     * Example of retrieving the Recordings for an account, filtered by call id
     *
     * @param client  authenticated twilio client object
     * @param CallSid the twilio call ID string
     */
    private static void getRecordingsExample(TwilioRestClient client, String CallSid) {

        //build map of parameters 
        Map<String, String> params = new HashMap<String, String>();
        params.put("CallSid", CallSid);

        TwilioRestResponse response;
        try {
            response = client.request("/" + APIVERSION + "/Accounts/" + client.getAccountSid() + "/Recordings", "GET", params);

            if (response.isError())
                System.out.println("Error fetching recordings: " + response.getHttpStatus() + "\n" + response.getResponseText());
            else {
                System.out.println(response.getResponseText());
            }
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }

    /**
     * Example of deleting a recording from your twilio account
     *
     * @param client       authenticated twilio client object
     * @param RecordingSid the twilio Recording Id String you wish to delete
     */
    private static void deleteRecordingsExample(TwilioRestClient client, String RecordingSid) {
        TwilioRestResponse response;
        try {
            response = client.request("/" + APIVERSION + "/Accounts/" + client.getAccountSid() + "/Recordings/" + RecordingSid, "GET", null);

            if (response.isError())
                System.out.println("Error deleting recording: " + response.getHttpStatus() + "\n" + response.getResponseText());
            else {
                System.out.println(response.getResponseText());
            }
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }
}
