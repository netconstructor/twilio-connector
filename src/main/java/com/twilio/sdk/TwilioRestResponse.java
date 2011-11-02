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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TwilioRestResponse holds all the REST response data
 * Before using the reponse, check IsError to see if an exception occurred with the data sent to Twilio
 * ResponseXml will contain a SimpleXml object with the response xml
 * ResponseText contains the raw string response
 * Url and QueryString are from the request
 * HttpStatus is the response code of the request
 */
public class TwilioRestResponse {

    private String responseText;
    private int httpStatus;
    private String url;
    private String queryString;
    private boolean error;


    public TwilioRestResponse(String url, String text, int status) {
        Pattern p = Pattern.compile("([^?]+)\\??(.*)");
        Matcher m = p.matcher(url);
        m.matches();
        this.url = m.group(1);
        this.queryString = m.group(2);
        this.responseText = text;
        this.httpStatus = status;
        this.error = (status >= 400);
    }

    // getters and setters 
    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "TwilioRestResponse{" +
                "responseText='" + responseText + '\'' +
                ", httpStatus=" + httpStatus +
                ", url='" + url + '\'' +
                ", queryString='" + queryString + '\'' +
                ", error=" + error +
                '}';
    }
}
