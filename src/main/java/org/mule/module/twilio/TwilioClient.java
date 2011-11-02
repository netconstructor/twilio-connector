/**
 * Mule Twilio Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.twilio;

import org.mule.api.callback.HttpCallback;
import org.mule.module.twilio.TwilioParameters.TwilioParamater;
import org.mule.module.twilio.TwilioParameters.TwilioParametersStategy;

public class TwilioClient {

    public static final String PATH_PREFIX = "/2010-04-01";
    private static final String ACCOUNT_URI = PATH_PREFIX + "/Accounts/";
    private static final HttpMethod DEFAULT_CALLBACK_HTTP_METHOD = HttpMethod.GET;
    private TwilioRequestExecutor twilioRequestExecutor;
    private String accountSid;

    public TwilioClient(String accountSid, String authToken) {
        this.accountSid = accountSid;
        twilioRequestExecutor = new TwilioRequestExecutor(accountSid, authToken);
    }

    public String getAccountDetails(String accountSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid));
    }

    public String getAllAcountsDetails(String friendlyName, AccountStatus accountStatus) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.STATUS, accountStatus);
        return twilioRequestExecutor.executeGetRequest(ACCOUNT_URI, optionalParams);
    }

    public String updateAccount(String accountSid, AccountStatus accountStatus, String friendlyName) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.STATUS, accountStatus);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid), optionalParams);
    }

    public String createSubAccount(String friendlyName) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executePostRequest(ACCOUNT_URI, optionalParams);
    }

    public String getSubAccountBySid(String accountSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid));
    }

    public String getSubAccountByFriendlyName(String friendlyName) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executeGetRequest(ACCOUNT_URI, requiredParams);
    }

    public String exchangePhoneNumbersBetweenSubaccounts(String accountSidFrom, String incomingPhoneNumberSid, String accountSidTo) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.ACCOUNT_SID, accountSidTo);
        return twilioRequestExecutor.executePostRequest(getUri(accountSidFrom) + "/IncomingPhoneNumbers/" + incomingPhoneNumberSid, requiredParams);
    }

    public String getAvailablePhoneNumbers(String accountSid, String isoCountryCode, String areaCode, String contains, String inRegion, String inPostalCode) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL);
        addBasicSearchParams(areaCode, contains, inRegion, inPostalCode, optionalParams);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/AvailablePhoneNumbers/" + isoCountryCode + "/Local", optionalParams);
    }

    public String getAvailablePhoneNumbersAdvancedSeach(String accountSid, String isoCountryCode, String areaCode, String contains, String inRegion,
                                                        String inPostalCode, String nearLatLong, String nearPhoneNumber, String inLata, String inRateCenter, String distance) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL);
        addBasicSearchParams(areaCode, contains, inRegion, inPostalCode, optionalParams);
        optionalParams.addIfValueNotNull(TwilioParamater.NEAR_LAT_LONG, nearLatLong).
                addIfValueNotNull(TwilioParamater.NEAR_NUMBER, nearPhoneNumber).
                addIfValueNotNull(TwilioParamater.IN_LATA, inLata).
                addIfValueNotNull(TwilioParamater.IN_RATE_CENTER, inRateCenter).
                addIfValueNotNull(TwilioParamater.DISTANCE, distance);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/AvailablePhoneNumbers/" + isoCountryCode + "/Local", optionalParams);
    }

    public String getAvailableTollFreeNumbers(String accountSid, String isoCountryCode, String contains) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.CONTAINS, contains);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/AvailablePhoneNumbers/" + isoCountryCode + "/TollFree", optionalParams);
    }

    public String getOutgoingCallerIdByOutgoingCallerIdSid(String accountSid, String outgoingCallerIdSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/OutgoingCallerIds/" + outgoingCallerIdSid);
    }

    public String updateOutgoingCallerIdByOutgoingCallerIdSid(String accountSid, String outgoingCallerIdSid, String friendlyName) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/OutgoingCallerIds/" + outgoingCallerIdSid, requiredParams);
    }

    public String getAllOutgoingCallerIds(String accountSid, String phoneNumber, String friendlyName) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.PHONE_NUMBER, phoneNumber).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/OutgoingCallerIds/", optionalParams);
    }

    public String addNewCallerId(String accountSid, String phoneNumber, String friendlyName, Integer callDelay, String extension) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.PHONE_NUMBER, phoneNumber);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.CALL_DELAY, callDelay).
                addIfValueNotNull(TwilioParamater.EXTENSION, extension);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/OutgoingCallerIds/", requiredParams, optionalParams);
    }

    public String deleteOutgoingCallerId(String accountSid, String outgoingCallerIdSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/OutgoingCallerIds/" + outgoingCallerIdSid);
    }

    public String getIncomingPhoneNumbers(String accountSid, String incomingPhoneNumberSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/IncomingPhoneNumbers/" + incomingPhoneNumberSid);
    }

    public String updateIncomingPhoneNumbers(String accountSid, String incomingPhoneNumberSid, String friendlyName, String apiVersion,
                                             String voiceUrl, HttpMethod voiceMethod, HttpCallback voiceFallback, HttpCallback statusCallback,
                                             Boolean voiceCallerIdLookup, String voiceApplicationSid, String smsUrl, HttpMethod smsMethod,
                                             HttpCallback smsFallback, String smsApplicationSid, String accountSidDestination) {
        TwilioParameters twilioParams = new TwilioParameters(TwilioParametersStategy.AT_LEAST_ONE_REQUIRED).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.API_VERSION, apiVersion).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD).
                addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD).
                addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup).
                addIfValueNotNull(TwilioParamater.VOICE_APPLICATION_SID, voiceApplicationSid).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod).
                addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD).
                addIfValueNotNull(TwilioParamater.SMS_APPLICATION_SID, smsApplicationSid).
                addIfValueNotNull(TwilioParamater.ACCOUNT_SID, accountSidDestination);
        if (voiceFallback != null) {
            twilioParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallback.getUrl());
        }
        if (statusCallback != null) {
            twilioParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback.getUrl());
        }
        if (smsFallback != null) {
            twilioParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallback.getUrl());
        }
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/IncomingPhoneNumbers/" + incomingPhoneNumberSid, twilioParams);
    }

    public String deleteIncomingPhoneNumber(String accountSid, String incomingPhoneNumberSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/IncomingPhoneNumbers/" + incomingPhoneNumberSid);
    }

    public String getIncomingPhoneNumbers(String accountSid, String phoneNumber, String friendlyName) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.PHONE_NUMBER, phoneNumber).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/IncomingPhoneNumbers", optionalParams);
    }

    public String addIncomingPhoneNumberByPhoneNumber(String accountSid, String phoneNumber, String friendlyName, String voiceUrl,
                                                      HttpMethod voiceMethod, HttpCallback voiceFallback, HttpCallback statusCallback,
                                                      Boolean voiceCallerIdLookup, String voiceApplicationSid, String smsUrl,
                                                      HttpMethod smsMethod, HttpCallback smsFallback, String smsApplicationSid) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.PHONE_NUMBER, phoneNumber);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup).
                addIfValueNotNull(TwilioParamater.VOICE_APPLICATION_SID, voiceApplicationSid).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod).
                addIfValueNotNull(TwilioParamater.SMS_APPLICATION_SID, smsApplicationSid);
        if (statusCallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (voiceFallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (smsFallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/IncomingPhoneNumbers", requiredParams, optionalParams);
    }

    public String addIncomingPhoneNumberByAreaCode(String accountSid, String areaCode, String friendlyName, String voiceUrl,
                                                   HttpMethod voiceMethod, HttpCallback voiceFallback, HttpCallback statusCallback,
                                                   Boolean voiceCallerIdLookup, String voiceApplicationSid, String smsUrl,
                                                   HttpMethod smsMethod, HttpCallback smsFallback, String smsApplicationSid) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.AREA_CODE, areaCode);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup).
                addIfValueNotNull(TwilioParamater.VOICE_APPLICATION_SID, voiceApplicationSid).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod).
                addIfValueNotNull(TwilioParamater.SMS_APPLICATION_SID, smsApplicationSid);
        if (statusCallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (voiceFallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (smsFallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/IncomingPhoneNumbers", requiredParams, optionalParams);
    }

    public String getApplication(String accountSid, String applicationSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Applications/" + applicationSid);
    }


    public String updateApplication(String accountSid, String applicationSid, String friendlyName, String apiVersion, String voiceUrl,
                                    HttpMethod voiceMethod, HttpCallback voiceFallback, HttpCallback statusCallback, Boolean voiceCallerIdLookup,
                                    String smsUrl, HttpMethod smsMethod, HttpCallback smsFallback, HttpCallback smsStatusCallback) {
        TwilioParameters twilioParams = new TwilioParameters(TwilioParametersStategy.AT_LEAST_ONE_REQUIRED).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.API_VERSION, apiVersion).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod);

        if (statusCallback != null) {
            twilioParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback.getUrl());
            twilioParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (voiceFallback != null) {
            twilioParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallback.getUrl());
            twilioParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (smsFallback != null) {
            twilioParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallback.getUrl());
            twilioParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (smsStatusCallback != null) {
            twilioParams.addIfValueNotNull(TwilioParamater.SMS_STATUS_CALLBACK, smsStatusCallback.getUrl());
        }
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Applications/" + applicationSid, twilioParams);

    }

    public String deleteApplication(String accountSid, String applicationSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/Applications/" + applicationSid);
    }

    public String getAllApplications(String accountSid, String friendlyName) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Applications", optionalParams);
    }

    public String createApplication(String accountSid, String friendlyName, String apiVersion, String voiceUrl, HttpMethod voiceMethod,
                                    HttpCallback voiceFallback, HttpCallback statusCallback, Boolean voiceCallerIdLookup,
                                    String smsUrl, HttpMethod smsMethod, HttpCallback smsFallback, HttpCallback smsStatusCallback) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.API_VERSION, apiVersion).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.VOICE_CALLER_ID_LOOKUP, voiceCallerIdLookup).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod);
        if (smsStatusCallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.SMS_STATUS_CALLBACK, smsStatusCallback.getUrl());
        }
        if (statusCallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (voiceFallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_URL, voiceFallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.VOICE_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (smsFallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_URL, smsFallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.SMS_FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Applications/", requiredParams, optionalParams);
    }

    public String getCall(String accountSid, String callSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Calls/" + callSid);
    }

    public String getCalls(String accountSid, String to, String from, String status, String startTime) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.TO, to).
                addIfValueNotNull(TwilioParamater.FROM, from).
                addIfValueNotNull(TwilioParamater.STATUS, status).
                addIfValueNotNull(TwilioParamater.START_TIME, startTime);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Calls/", optionalParams);
    }

    public String makeCall(String accountSid, String from, String to, String url, String applicationSid, HttpMethod method,
                           HttpCallback fallback, HttpCallback statusCallback, String sendDigits, String ifMachine, String timeout) {
        TwilioParameters toFromParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.TO, to).
                addIfValueNotNull(TwilioParamater.FROM, from);
        TwilioParameters urlOrApplicationSidParams = new TwilioParameters(TwilioParametersStategy.EXACTLY_ONE_REQUIRED).
                addIfValueNotNull(TwilioParamater.URL, url).
                addIfValueNotNull(TwilioParamater.APPLICATION_SID, applicationSid);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.METHOD, method).
                addIfValueNotNull(TwilioParamater.SEND_DIGITS, sendDigits).
                addIfValueNotNull(TwilioParamater.IF_MACHINE, ifMachine).
                addIfValueNotNull(TwilioParamater.TIMEOUT, timeout);
        if (fallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.FALLBACK_URL, fallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.FALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }
        if (statusCallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback.getUrl());
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK_METHOD, DEFAULT_CALLBACK_HTTP_METHOD);
        }

        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Calls/", toFromParams, urlOrApplicationSidParams, optionalParams);
    }

    public String changeCallState(String accountSid, String callSid, String url, HttpMethod method, String status) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.URL, url).
                addIfValueNotNull(TwilioParamater.METHOD, method).
                addIfValueNotNull(TwilioParamater.STATUS, status);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Calls/" + callSid, optionalParams);
    }

    public String getConference(String accountSid, String conferenceSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Conferences/" + conferenceSid);
    }

    public String getConferences(String accountSid, String status, String friendlyName, String dateCreated, String dateUpdated) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.STATUS, status).
                addIfValueNotNull(TwilioParamater.FRIENDLY_NAME, friendlyName).
                addIfValueNotNull(TwilioParamater.DATE_CREATED, dateCreated).
                addIfValueNotNull(TwilioParamater.DATE_UPDATED, dateUpdated);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Conferences/", optionalParams);
    }


    public String getParticipant(String accountSid, String conferenceSid, String callSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Conferences/" + conferenceSid + "/Participants/" + callSid);
    }

    public String updateParticipantStauts(String accountSid, String conferenceSid, String callSid, Boolean muted) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.MUTED, muted);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Conferences/" + conferenceSid + "/Participants/" + callSid, requiredParams);
    }

    public String deleteParticipant(String accountSid, String conferenceSid, String callSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/Conferences/" + conferenceSid + "/Participants/" + callSid);
    }

    public String getParticipants(String accountSid, String conferenceSid, Boolean muted) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.MUTED, muted);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Conferences/" + conferenceSid + "/Participants/", optionalParams);
    }

    public String getSmsMessage(String accountSid, String smsMessageSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/SMS/Messages/" + smsMessageSid);
    }

    public String getAllSmsMessages(String accountSid, String to, String from, String dateSent) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.TO, to).
                addIfValueNotNull(TwilioParamater.FROM, from).
                addIfValueNotNull(TwilioParamater.DATE_SENT, dateSent);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/SMS/Messages/", optionalParams);
    }

    public String sendSmsMessage(String accountSid, String from, String to, String body, HttpCallback statusCallback, String applicationSid) {
        TwilioParameters requiredParams = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(TwilioParamater.FROM, from).
                addIfValueNotNull(TwilioParamater.TO, to).
                addIfValueNotNull(TwilioParamater.BODY, body);
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.APPLICATION_SID, applicationSid);
        if (statusCallback != null) {
            optionalParams.addIfValueNotNull(TwilioParamater.STATUS_CALLBACK, statusCallback.getUrl());
        }
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/SMS/Messages/", requiredParams, optionalParams);
    }

    public String getRecording(String accountSid, String recordingSid, RecordingType recordingType) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Recordings/" + recordingSid + recordingType.getExtension());
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
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Transcriptions/" + transcriptionSid + transcriptionFormat.getExtension());
    }

    public String getTranscriptions(String accountSid, String recordingSid, TranscriptionFormat transcriptionFormat) {
        if (recordingSid == null) {
            return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Transcriptions");
        } else {
            return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Recordings/" + recordingSid + transcriptionFormat.getExtension());
        }
    }

    public String getNotification(String accountSid, String notificationSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Notifications/" + notificationSid);
    }

    public String deleteNotification(String accountSid, String notificationSid) {
        return twilioRequestExecutor.executeDeleteRequest(getUri(accountSid) + "/Notifications/" + notificationSid);
    }

    public String getAllNotifications(String accountSid, Integer log, String messageDate) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.LOG, log).
                addIfValueNotNull(TwilioParamater.MESSAGE_DATE, messageDate);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Notifications/", optionalParams);
    }

    public String getNotifications(String accountSid, String callSid, Integer log, String messageDate) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.LOG, log).
                addIfValueNotNull(TwilioParamater.MESSAGE_DATE, messageDate);
        return twilioRequestExecutor.executeGetRequest(getUri(accountSid) + "/Calls/" + callSid + "/Notifications/", optionalParams);
    }

    public String getSandbox(String accountSid) {
        return twilioRequestExecutor.executeGetRequestNoParams(getUri(accountSid) + "/Sandbox");
    }

    public String updateSandbox(String accountSid, String voiceUrl, HttpMethod voiceMethod, String smsUrl, HttpMethod smsMethod) {
        TwilioParameters optionalParams = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(TwilioParamater.VOICE_URL, voiceUrl).
                addIfValueNotNull(TwilioParamater.VOICE_METHOD, voiceMethod).
                addIfValueNotNull(TwilioParamater.SMS_URL, smsUrl).
                addIfValueNotNull(TwilioParamater.SMS_METHOD, smsMethod);
        return twilioRequestExecutor.executePostRequest(getUri(accountSid) + "/Sandbox", optionalParams);
    }

    private void addBasicSearchParams(String areaCode, String contains, String inRegion, String inPostalCode, TwilioParameters twilioParameters) {
        twilioParameters.addIfValueNotNull(TwilioParamater.AREA_CODE, areaCode);
        twilioParameters.addIfValueNotNull(TwilioParamater.CONTAINS, contains);
        twilioParameters.addIfValueNotNull(TwilioParamater.IN_REGION, inRegion);
        twilioParameters.addIfValueNotNull(TwilioParamater.IN_POSTAL_CODE, inPostalCode);
    }

    // If the request made to Twilio includes the account sid then use it, otherwise use the account sid used to create the client.
    private String getUri(String accountSid) {
        if (accountSid == null) {
            return ACCOUNT_URI + this.accountSid;
        }
        return ACCOUNT_URI + accountSid;
    }
}