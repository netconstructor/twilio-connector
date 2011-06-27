package org.mule.module.twilio;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TwilioParameters {

    public static final TwilioParameters NO_PARAMETERS = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL);
    private Map<String, String> params = new HashMap<String, String>();
    private TwilioParametersStategy twilioParametersStategy;
    private boolean nullAdded;

    public TwilioParameters(TwilioParametersStategy twilioParametersStategy) {
        this.twilioParametersStategy = twilioParametersStategy;
    }

    public TwilioParameters addIfValueNotNull(TwilioParamater twilioParameter, Object value) {
        if (value != null) {
            params.put(twilioParameter.getCode(), value.toString());
        } else {
            nullAdded = true;
        }
        return this;
    }

    public Map<String, String> getMap() {
        validate();
        if (params.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableMap(params);
    }

    private void validate() {
        if (twilioParametersStategy.equals(TwilioParametersStategy.AT_LEAST_ONE_REQUIRED) && params.isEmpty()) {
            throw new TwilioConnectorException("At least one parameter value must be specified");
        }
        if (twilioParametersStategy.equals(TwilioParametersStategy.ALL_REQUIRED) && nullAdded) {
            throw new TwilioConnectorException("All parameters values must be specified");
        }
        if (twilioParametersStategy.equals(TwilioParametersStategy.EXACTLY_ONE_REQUIRED) && params.size() != 1) {
            throw new TwilioConnectorException("Exactly one paramter value must be specified");
        }
    }

    public enum TwilioParametersStategy {
        ALL_OPTIONAL, AT_LEAST_ONE_REQUIRED, EXACTLY_ONE_REQUIRED, ALL_REQUIRED
    }

    public enum TwilioParamater {

        FRIENDLY_NAME("FriendlyName"),
        STATUS("Status"),
        AREA_CODE("AreaCode"),
        CONTAINS("Contains"),
        IN_REGION("InRegion"),
        IN_POSTAL_CODE("InPostalCode"),
        NEAR_LAT_LONG("NearLatLong"),
        NEAR_NUMBER("NearNumber"),
        IN_LATA("InLata"),
        IN_RATE_CENTER("InRateCenter"),
        DISTANCE("Distance"),
        PHONE_NUMBER("PhoneNumber"),
        CALL_DELAY("CallDelay"),
        EXTENSION("Extension"),
        API_VERSION("ApiVersion"),
        VOICE_URL("VoiceUrl"),
        VOICE_METHOD("VoiceMethod"),
        VOICE_FALLBACK_URL("VoiceFallbackUrl"),
        VOICE_FALLBACK_METHOD("VoiceFallbackMethod"),
        STATUS_CALLBACK("StatusCallback"),
        STATUS_CALLBACK_METHOD("StatusCallbackMethod"),
        VOICE_CALLER_ID_LOOKUP("VoiceCallerIdLookup"),
        VOICE_APPLICATION_SID("VoiceApplicationSid"),
        SMS_URL("SmsUrl"),
        SMS_METHOD("SmsMethod"),
        SMS_FALLBACK_URL("SmsFallbackUrl"),
        SMS_FALLBACK_METHOD("SmsFallbackMethod"),
        SMS_APPLICATION_SID("SmsApplicationSid"),
        ACCOUNT_SID("AccountSid"),
        SMS_STATUS_CALLBACK("SmsStatusCallback"),
        FROM("From"),
        TO("To"),
        START_TIME("StartTime"),
        URL("Url"),
        APPLICATION_SID("ApplicationSid"),
        METHOD("Method"),
        FALLBACK_URL("FallbackUrl"),
        FALLBACK_METHOD("FallbackMethod"),
        SEND_DIGITS("SendDigits"),
        IF_MACHINE("IfMachine"),
        TIMEOUT("Timeout"),
        DATE_CREATED("DateCreated"),
        DATE_UPDATED("DateUpdated"),
        MUTED("Muted"),
        DATE_SENT("DateSent"),
        BODY("Body"),
        CALL_SID("CallSid"),
        LOG("Log"),
        MESSAGE_DATE("MessageDate");

        private String code;

        TwilioParamater(String code) {
            this.code = code;
        }

        String getCode() {
            return code;
        }
    }
}