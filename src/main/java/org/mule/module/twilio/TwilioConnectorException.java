package org.mule.module.twilio;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;

public class TwilioConnectorException extends RuntimeException {

    private static final long serialVersionUID = 5578855335572348078L;
    private TwilioRestResponse twilioRestResponse;
    private TwilioRestException twilioRestException;

    public TwilioConnectorException(TwilioRestResponse twilioRestResponse) {
        this.twilioRestResponse = twilioRestResponse;
    }

    public TwilioConnectorException(TwilioRestException twilioRestException) {
        this.twilioRestException = twilioRestException;
    }

    public TwilioConnectorException(String message) {
        super(message);
    }

    public TwilioRestResponse getTwilioRestResponse() {
        return twilioRestResponse;
    }

    public TwilioRestException getTwilioRestException() {
        return twilioRestException;
    }
}