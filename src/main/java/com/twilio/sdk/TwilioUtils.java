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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TwilioUtils {

    protected String authToken;
    protected String accountSid;

    TwilioUtils(String authToken, String accountSid) {
        this.authToken = authToken;
        this.accountSid = accountSid;
    }

    public boolean validateRequest(String expectedSignature, String url, Map<String, String> params) {

        SecretKeySpec signingKey = new SecretKeySpec(this.authToken.getBytes(), "HmacSHA1");

        try {
            //initialize the hash algortihm
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            //sort the params alphabetically, and append the key and value of each to the url
            StringBuffer data = new StringBuffer(url);
            if (params != null) {
                List<String> sortedKeys = new ArrayList<String>(params.keySet());
                Collections.sort(sortedKeys);

                for (String s : sortedKeys) {
                    data.append(s);
                    String v = "";
                    if (params.get(s) != null)
                        v = params.get(s);
                    data.append(v);
                }
            }

            //compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.toString().getBytes());

            //base64-encode the hmac
            String signature = new String(Base64.encodeBase64(rawHmac));

            return signature.equals(expectedSignature);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }
}
