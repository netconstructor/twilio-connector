package org.mule.module.twilio;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;

public class TwilioConnectorException extends RuntimeException {

    private static final long serialVersionUID = 5578855335572348078L;

    public TwilioConnectorException(TwilioRestResponse twilioRestResponse) {
        super(twilioRestResponse.toString());
    }

    public TwilioConnectorException(TwilioRestException twilioRestException) {
        super(twilioRestException);
    }

    public TwilioConnectorException(String message) {
        super(message);
    }
}