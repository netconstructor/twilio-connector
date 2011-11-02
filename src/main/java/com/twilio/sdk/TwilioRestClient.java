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

import org.apache.commons.codec.binary.Base64;
import org.mule.module.twilio.ITwilioRestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class TwilioRestClient implements ITwilioRestClient {

    public static final String TWILIO_URL = "https://api.twilio.com";
    private String endpoint = TWILIO_URL;
    private String accountSid;
    private String authToken;


    public TwilioRestClient(String accountSid, String authToken, String endpoint) {

        this.accountSid = accountSid;
        this.authToken = authToken;
        if ((endpoint != null) && (!endpoint.equals(""))) {
            this.endpoint = endpoint;
        }
    }


    /*
    * sendRequst
    *   Sends a REST Request to the Twilio REST API
    *   $path : the URL (relative to the endpoint URL, after the /v1)
    *   $method : the HTTP method to use, defaults to GET
    *   $vars : for POST or PUT, a key/value associative array of data to send, for GET will be appended to the URL as query params
    */
    @Override
    public TwilioRestResponse request(String path, String method, Map<String, String> vars) throws TwilioRestException {

        String encoded = "";
        if (vars != null && !vars.isEmpty()) {
            for (String key : vars.keySet()) {
                try {
                    encoded += "&" + key + "=" + URLEncoder.encode(vars.get(key), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new TwilioRestException(e);
                }
            }
            encoded = encoded.substring(1);
        }

        // construct full url
        String url = this.endpoint + path;

        // if GET and vars, append them
        if (method.toUpperCase().equals("GET"))
            url += ((path.indexOf('?') == -1) ? "?" : "&") + encoded;


        try {
            HttpURLConnection con = openConnection(url);
            String userpass = this.accountSid + ":" + this.authToken;
            String encodeuserpass = new String(Base64.encodeBase64(userpass.getBytes()));

            con.setRequestProperty("Authorization", "Basic " + encodeuserpass);

            con.setDoOutput(true);

            // initialize a new curl object            
            if (method.toUpperCase().equals("GET")) {
                con.setRequestMethod("GET");
            } else if (method.toUpperCase().equals("POST")) {
                con.setRequestMethod("POST");
                OutputStreamWriter out = new OutputStreamWriter(
                        con.getOutputStream());
                out.write(encoded);
                out.close();
            } else if (method.toUpperCase().equals("PUT")) {
                con.setRequestMethod("PUT");
                OutputStreamWriter out = new OutputStreamWriter(
                        con.getOutputStream());
                out.write(encoded);
                out.close();
            } else if (method.toUpperCase().equals("DELETE")) {
                con.setRequestMethod("DELETE");
            } else {
                throw new TwilioRestException("Unknown method " + method);
            }

            BufferedReader in = null;
            try {
                if (con.getInputStream() != null) {
                    in = new BufferedReader(
                            new InputStreamReader(
                                    con.getInputStream()));
                }
            } catch (IOException e) {
                if (con.getErrorStream() != null) {
                    in = new BufferedReader(
                            new InputStreamReader(
                                    con.getErrorStream()));
                }
            }

            if (in == null) {
                throw new TwilioRestException("Unable to read response from server");
            }

            StringBuilder decodedString = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                decodedString.append(line);
            }
            in.close();

            // get result code
            int responseCode = con.getResponseCode();

            return new TwilioRestResponse(url, decodedString.toString(), responseCode);
        } catch (MalformedURLException e) {
            throw new TwilioRestException(e);
        } catch (IOException e) {
            throw new TwilioRestException(e);
        }
    }

    protected HttpURLConnection openConnection(String url) throws IOException {
        URL resturl = new URL(url);
        return (HttpURLConnection) resturl.openConnection();
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }


}
