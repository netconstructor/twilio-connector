package org.mule.module.twilio;

// TODO: Exchanging phone numbers
// TODO: Available Phone Numbers --> Additional Advanced Features


import org.mule.module.twilio.TwilioParameters.TwilioParamater;
import org.mule.module.twilio.TwilioParameters.TwilioParametersStategy;

public class TwilioClient {

    public static final String PATH_PREFIX = "/2010-04-01";
    private static final String ACCOUNT_URI = PATH_PREFIX + "/Accounts/";
    private TwilioRequestExecutor twilioRequestExecutor;
    private String accountSid;

    public TwilioClient(String accountSid, String authToken) {
        this.accountSid = accountSid;
        twilioRequestExecutor = new TwilioRequestExecutor(accountSid, authToken);
    }

    TwilioClient(TwilioRequestExecutor twilioRequestExecutor) {
        this.twilioRequestExecutor = twilioRequestExecutor;
    }

    public String getAccountDetails(String accountSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid), TwilioParameters.NO_PARAMETERS);
    }

    public String getAllAcountsDetails(String friendlyName, AccountStatus accountStatus) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.STATUS, accountStatus);
        return twilioRequestExecutor.executeGetRequest(ACCOUNT_URI, twilioParameters);
    }

    public String changeAccountStatus(String accountSid, AccountStatus accountStatus, String friendlyName) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.STATUS, accountStatus.toString());
        return twilioRequestExecutor.executePostRequest(getUri(accountSid), twilioParameters);
    }

    public String createSubAccount(String friendlyName) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executePostRequest(ACCOUNT_URI, twilioParameters);
    }

    public String getSubAccountBySid(String accoundSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accoundSid), TwilioParameters.NO_PARAMETERS);
    }

    public String getSubAccountByFriendlyName(String friendlyName) {
        TwilioParameters twilioParameters = new TwilioParameters();
        twilioParameters.addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executeGetRequest(ACCOUNT_URI, twilioParameters);
    }

    public String getAvailablePhoneNumbers(String accountSid, String isoCountryCode, String areaCode, String contains, String inRegion, String inPostalCode) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL);
        addBasicSearchParams(areaCode, contains, inRegion, inPostalCode, twilioParameters);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/AvailablePhoneNumbers/" + isoCountryCode + "/Local", twilioParameters);
    }

    public String getAvailablePhoneNumbersAdvancedSeach(String accountSid, String isoCountryCode, String areaCode, String contains, String inRegion,
                                                        String inPostalCode, String nearLatLong, String nearPhoneNumber, String inLata, String inRateCenter, String distance) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL);
        addBasicSearchParams(areaCode, contains, inRegion, inPostalCode, twilioParameters);
        twilioParameters.addIfValueNotNull(TwilioParamater.NEAR_LAT_LONG, nearLatLong).
                addIfValueNotNull(TwilioParamater.NEAR_NUMBER, nearPhoneNumber).
                addIfValueNotNull(TwilioParamater.IN_LATA, inLata).
                addIfValueNotNull(TwilioParamater.IN_RATE_CENTER, inRateCenter).
                addIfValueNotNull(TwilioParamater.DISTANCE, distance);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/AvailablePhoneNumbers/" + isoCountryCode + "/Local", twilioParameters);
    }

    public String getAvailableTollFreeNumbers(String accountSid, String isoCountryCode, String contains) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.CONTAINS, contains);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/AvailablePhoneNumbers/" + isoCountryCode + "/TollFree", twilioParameters);
    }

    public String getOutgoingCallerIdByOutgoingCallerIdSid(String accountSid, String outgoingCallerIdSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/OutgoingCallerIds/" + outgoingCallerIdSid, TwilioParameters.NO_PARAMETERS);
    }

    public String updateOutgoingCallerIdByOutgoingCallerIdSid(String accountSid, String outgoingCallerIdSid, String friendlyName) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/OutgoingCallerIds/" + outgoingCallerIdSid, twilioParameters);
    }

    private void addBasicSearchParams(String areaCode, String contains, String inRegion, String inPostalCode, TwilioParameters twilioParameters) {
        twilioParameters.addIfValueNotNull(TwilioParamater.AREA_CODE, areaCode);
        twilioParameters.addIfValueNotNull(TwilioParamater.CONTAINS, contains);
        twilioParameters.addIfValueNotNull(TwilioParamater.IN_REGION, inRegion);
        twilioParameters.addIfValueNotNull(TwilioParamater.IN_POSTAL_CODE, inPostalCode);
    }

    public String getAllOutgoingCallerIds(String accountSid, String phoneNumber, String friendlyName) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.PHONE_NUMBER, phoneNumber).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/OutgoingCallerIds/", twilioParameters);
    }

    public String addNewCallerId(String accountSid, String phoneNumber, String friendlyName, String callDelay, String extension) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.PHONE_NUMBER, phoneNumber);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.CALL_DELAY, callDelay).
                addIfValueNotNull(TwilioParamater.EXTENSION, extension);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/OutgoingCallerIds/", requiredParams, optionalParams);
    }

    public String getIncomingPhoneNumbers(String accountSid, String incomingPhoneNumberSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/IncomingPhoneNumbers/" + incomingPhoneNumberSid, TwilioParameters.NO_PARAMETERS);
    }

    public String updateIncomingPhoneNumbers(String accountSid, String incomingPhoneNumberSid, String friendlyName, String apiVersion, String voiceUrl, String voiceMethod, String voiceFallbackUrl, String voiceFallbackMethod,
                                             String statusCallback, String statusCallbackMethod, Boolean voiceCallerIdLookup, String voiceApplicationSid, String smsUrl, String smsMethod, String smsFallbackUrl, String smsFallbackMethod,
                                             String smsApplicationSid, String accountSidDestination) {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.AT_LEAST_ONE_REQUIRED).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.API_VERSION, apiVersion).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallbackUrl).
                addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, voiceFallbackMethod).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, statusCallbackMethod).
                addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup).
                addIfValueNotNull(TwilioParamater.VOICE_APPLICATION_SID, voiceApplicationSid).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod).
                addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallbackUrl).
                addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, smsFallbackMethod).
                addIfValueNotNull(TwilioParamater.SMS_APPLICATION_SID, smsApplicationSid).
                addIfValueNotNull(TwilioParamater.ACCOUNT_SID, accountSidDestination);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/IncomingPhoneNumbers/" + incomingPhoneNumberSid, twilioParameters);
    }

    public String deleteIncomingPhoneNumber(String accountSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/IncomingPhoneNumbers");
    }

    public String getIncomingPhoneNumbers(String accountSid, String phoneNumber, String friendlyName) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.PHONE_NUMBER, phoneNumber).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/IncomingPhoneNumbers", optionalParams);
    }

    public String addIncomingPhoneNumberByPhoneNumber(String accountSid, String phoneNumber, String friendlyName, String voiceUrl, String voiceMethod, String voiceFallbackUrl, String voiceFallbackMethod,
                                                      String statusCallback, String statusCallbackMethod, Boolean voiceCallerIdLookup, String voiceApplicationSid, String smsUrl, String smsMethod,
                                                      String smsFallbackUrl, String smsFallbackMethod, String smsApplicationSid) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.PHONE_NUMBER, phoneNumber);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallbackUrl).
                addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, voiceFallbackMethod).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, statusCallbackMethod).
                addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup).
                addIfValueNotNull(TwilioParamater.VOICE_APPLICATION_SID, voiceApplicationSid).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod).
                addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallbackUrl).
                addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, smsFallbackMethod).
                addIfValueNotNull(TwilioParamater.SMS_APPLICATION_SID, smsApplicationSid);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/IncomingPhoneNumbers", requiredParams, optionalParams);
    }

    public String addIncomingPhoneNumberByAreaCode(String accountSid, String areaCode, String friendlyName, String voiceUrl, String voiceMethod, String voiceFallbackUrl, String voiceFallbackMethod,
                                                   String statusCallback, String statusCallbackMethod, String voiceCallerIdLookup, String voiceApplicationSid, String smsUrl, String smsMethod,
                                                   String smsFallbackUrl, String smsFallbackMethod, String smsApplicationSid) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.AREA_CODE, areaCode);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallbackUrl).
                addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, voiceFallbackMethod).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, statusCallbackMethod).
                addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup).
                addIfValueNotNull(TwilioParamater.VOICE_APPLICATION_SID, voiceApplicationSid).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod).
                addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallbackUrl).
                addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, smsFallbackMethod).
                addIfValueNotNull(TwilioParamater.SMS_APPLICATION_SID, smsApplicationSid);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/IncomingPhoneNumbers", requiredParams, optionalParams);
    }


    public String getApplication(String accountSid, String applicationSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Applications/" + applicationSid, TwilioParameters.NO_PARAMETERS);
    }

    public String updateApplication(String accountSid, String applicationSid, String friendlyName, String apiVersion, String voiceUrl, String voiceMethod,
                                    String voiceFallbackUrl, String voiceFallbackMethod, String statusCallback, String statusCallbackMethod, String voiceCallerIdLookup,
                                    String smsUrl, String smsMethod, String smsFallbackUrl, String smsFallbackMethod, String smsStatusCallback) {
        TwilioParameters twilioParameters = new TwilioParameters();
        twilioParameters.addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        twilioParameters.addIfValueNotNull(TwilioParamater.API_VERSION, apiVersion);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallbackUrl);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, voiceFallbackMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback);
        twilioParameters.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, statusCallbackMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallbackUrl);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, smsFallbackMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_STATUS_CALLBACK, smsStatusCallback);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Applications/" + applicationSid, twilioParameters);

    }

    public String deleteApplication(String accountSid, String applicationSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/Applications/" + applicationSid);
    }

    public String getAllApplications(String accountSid, String friendlyName) {
        TwilioParameters twilioParameters = new TwilioParameters();
        twilioParameters.addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Applications", twilioParameters);
    }

    public String createApplication(String accountSid, String friendlyName, String apiVersion, String voiceUrl, String voiceMethod, String voiceFallbackUrl,
                                    String voiceFallbackMethod, String statusCallback, String statusCallbackMethod, String voiceCallerIdLookup, String smsUrl,
                                    String smsMethod, String smsFallbackUrl, String smsFallbackMethod, String smsStatusCallback) {
        TwilioParameters twilioParameters = new TwilioParameters();
        twilioParameters.addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        twilioParameters.addIfValueNotNull(TwilioParamater.API_VERSION, apiVersion);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallbackUrl);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, voiceFallbackMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback);
        twilioParameters.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, statusCallbackMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallbackUrl);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, smsFallbackMethod);
        twilioParameters.addIfValueNotNull(TwilioParamater.SMS_STATUS_CALLBACK, smsStatusCallback);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Applications/", twilioParameters);
    }

    public String getCall(String accountSid, String callSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Calls/" + callSid, TwilioParameters.NO_PARAMETERS);
    }

    public String getCalls(String accountSid, String to, String from, String status, String startTime) {
        TwilioParameters twilioParameters = new TwilioParameters();
        twilioParameters.addIfValueNotNull(TwilioParamater.TO, to);
        twilioParameters.addIfValueNotNull(TwilioParamater.FROM, from);
        twilioParameters.addIfValueNotNull(TwilioParamater.STATUS, status);
        twilioParameters.addIfValueNotNull(TwilioParamater.START_TIME, startTime);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Calls/", twilioParameters);
    }

    public String makeCall(String accountSid, String from, String to, String url, String applicationSid, String method, String fallbackUrl, String fallbackMethod,
                           String statusCallback, String statusCallbackMethod, String sendDigits, String ifMachine, String timeout) {
        TwilioParameters toFromParameters = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.TO, to).
                addIfValueNotNull(TwilioParamater.FROM, from);
        TwilioParameters urlOrApplicationSidParamaters = new TwilioParameters(TwilioParametersStategy.EXACTLY_ONE_REQUIRED).
                addIfValueNotNull(TwilioParamater.URL, url).
                addIfValueNotNull(TwilioParamater.APPLICATION_SID, applicationSid);
        TwilioParameters optionalParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.METHOD, method).
                addIfValueNotNull(TwilioParamater.FALLBACK_URL, fallbackUrl).
                addIfValueNotNull(TwilioParamater.FALLBACK_METHOD, fallbackMethod).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, statusCallbackMethod).
                addIfValueNotNull(TwilioParamater.SEND_DIGITS, sendDigits).
                addIfValueNotNull(TwilioParamater.IF_MACHINE, ifMachine).
                addIfValueNotNull(TwilioParamater.TIMEOUT, timeout);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Calls/", toFromParameters, urlOrApplicationSidParamaters, optionalParameters);
    }

    public String changeCallState(String accountSid, String callSid, String url, String method, String status) {
        TwilioParameters twilioParameters = new TwilioParameters().
                addIfValueNotNull(TwilioParamater.URL, url).
                addIfValueNotNull(TwilioParamater.METHOD, method).
                addIfValueNotNull(TwilioParamater.STATUS, status);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Calls/" + callSid, twilioParameters);
    }

    public String getConference(String accountSid, String conferenceSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Conferences/" + conferenceSid, TwilioParameters.NO_PARAMETERS);
    }


    public String getConferences(String accountSid, String status, String friendlyName, String dateCreated, String dateUpdated) {
        TwilioParameters optionalParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.STATUS, status).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.DATE_CREATED, dateCreated).
                addIfValueNotNull(TwilioParamater.DATE_UPDATED, dateUpdated);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Conferences/", optionalParameters);
    }

    public String getParticipant(String accountSid, String conferenceSid, String callSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Conferences/" + conferenceSid + "/Participants/" + callSid, TwilioParameters.NO_PARAMETERS);
    }

    public String updateParticipantStauts(String accountSid, String conferenceSid, String callSid, Boolean muted) {
        TwilioParameters twilioParameters = new TwilioParameters().
                addIfValueNotNull(TwilioParamater.MUTED, muted);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Conferences/" + conferenceSid + "/Participants/" + callSid, twilioParameters);
    }

    public String deleteParticipant(String accountSid, String conferenceSid, String callSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/Conferences/" + conferenceSid + "/Participants/" + callSid);
    }

    public String getParticipants(String accountSid, String conferenceSid, Boolean muted) {
        TwilioParameters optionalParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.MUTED, muted);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Conferences/" + conferenceSid + "/Participants/", optionalParameters);
    }

    public String getSmsMessage(String accountSid, String smsMessageSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/SMS/Messages/" + smsMessageSid, TwilioParameters.NO_PARAMETERS);
    }

    public String getAllSmsMessages(String accountSid, String to, String from, String dateSent) {
        TwilioParameters optionalParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.TO, to).
                addIfValueNotNull(TwilioParamater.FROM, from).
                addIfValueNotNull(TwilioParamater.DATE_SENT, dateSent);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/SMS/Messages/", optionalParameters);
    }

    public String sendSmsMessage(String accountSid, String from, String to, String body, String statusCallback, String applicationSid) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.FROM, from).
                addIfValueNotNull(TwilioParamater.TO, to).
                addIfValueNotNull(TwilioParamater.BODY, body);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback).
                addIfValueNotNull(TwilioParamater.APPLICATION_SID, applicationSid);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/SMS/Messages/", requiredParams, optionalParams);
    }

    public String getRecording(String accountSid, String recordingSid, RecordingType recordingType) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Recordings/" + recordingSid + recordingType.getExtension(),
                TwilioParameters.NO_PARAMETERS);
    }

    public String deleteRecording(String accountSid, String recordingSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/Recordings/" + recordingSid);
    }

    public String getRecordings(String accountSid, String callSid, String dateCreated) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.CALL_SID, callSid).
                addIfValueNotNull(TwilioParamater.DATE_CREATED, dateCreated);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Recordings/", optionalParams);
    }

    public String getTranscriptionByTranscriptionSid(String accountSid, String transcriptionSid, TranscriptionFormat transcriptionFormat) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Transcriptions/" + transcriptionSid + transcriptionFormat.getExtension(), TwilioParameters.NO_PARAMETERS);
    }

    public String getTranscriptionByRecordingSid(String accountSid, String recordingSid, TranscriptionFormat transcriptionFormat) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Recordings/" + recordingSid + transcriptionFormat.getExtension(), TwilioParameters.NO_PARAMETERS);
    }

    public String getNotification(String accountSid, String notificationSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Notifications/" + notificationSid, TwilioParameters.NO_PARAMETERS);
    }

    public String deleteNotification(String accountSid, String notificationSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/Notifications/" + notificationSid);
    }

    public String getNotifications(String accountSid, Integer log, String messageDate) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.LOG, log).
                addIfValueNotNull(TwilioParamater.MESSAGE_DATE, messageDate);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Notifications/", optionalParams);
    }

    public String getNotificationsByCallSid(String accountSid, String callSid, String log, String messageDate) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.LOG, log).
                addIfValueNotNull(TwilioParamater.MESSAGE_DATE, messageDate);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Calls/" + callSid + "/Notifications/", optionalParams);
    }

    public String getSandbox(String accountSid) {
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Sandbox", TwilioParameters.NO_PARAMETERS);
    }

    public String updateSandbox(String accountSid, String voiceUrl, String voiceMethod, String smsUrl, String smsMethod) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Sandbox", optionalParams);
    }

    /**
     * If the request made to Twilio includes the accound sid use it, if not use the account sid used to create
     * the client.
     */
    private String getUri(String accountSid) {
        if (accountSid == null) {
            return ACCOUNT_URI + this.accountSid;
        }
        return ACCOUNT_URI + accountSid;
    }
}