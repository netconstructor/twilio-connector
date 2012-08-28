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

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.param.Optional;
import org.mule.api.callback.HttpCallback;

/**
 * Twilio is a provider of cloud API for voice and SMS communications for customers who want to use phone services as
 * marketing leverage.
 *
 * @author MuleSoft, Inc.
 */
@Module(name = "twilio", friendlyName = "Twilio")
public class TwilioConnector {

    /**
     * The account sid to be used to connect to Twilio.
     */
    @Configurable
    private String accountSid;
    /**
     * The authentication token to be used to connect to Twilio
     */
    @Configurable
    private String authToken;

    private TwilioClient twilioClient;

    @Start
    public void createTwilioClient() {
        twilioClient = new TwilioClient(accountSid, authToken);
    }

    /**
     * Returns a representation of an account.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAccountDetails}
     *
     * @param accountSid the account sid for which to get the details, leave empty to use to use {@link TwilioConnector#accountSid}
     * @return a representation of the account
     */
    @Processor
    public String getAccountDetails(@Optional String accountSid) {
        return twilioClient.getAccountDetails(accountSid);
    }

    /**
     * Retrieve a list of the Account resources belonging to the account used to make the API request. This list will
     * include that Account as well.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAllAccountsDetails}
     *
     * @param accountStatus Only return Account resources with the given status. Can be closed, suspended or active.
     * @param friendlyName  Only return the Account resources with friendly names that exactly match this name.
     * @return a list of the Account resources belonging to the account used to make the API request. This list will
     *         include that Account as well.
     */
    @Processor
    public String getAllAccountsDetails(@Optional AccountStatus accountStatus,
                                        @Optional String friendlyName) {
        return twilioClient.getAllAcountsDetails(friendlyName, accountStatus);
    }

    /**
     * Allows you to modify the properties of an account.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:updateAccount}
     *
     * @param accountSid    optionally override globally configured Account SID
     * @param accountStatus Alter the status of this account: use closed to irreversibly close this account, suspended to temporarily suspend it, or active to reactivate it.
     * @param friendlyName  Update the human-readable description of this account.
     * @return a representation of the account
     */
    @Processor
    public String updateAccount(@Optional String accountSid,
                                @Optional AccountStatus accountStatus,
                                @Optional String friendlyName) {
        return twilioClient.updateAccount(accountSid, accountStatus, friendlyName);
    }

    /**
     * Creates a new subaccount.
     * <p/>
     * Subaccounts in Twilio are just accounts that are "owned" by another account. Using a subaccount you can segment
     * each of your customers' use of Twilio and keep it separate from all the rest, allowing you to easily manage the
     * activity and resources of each customer independently.
     * <p/>
     * For instance, if you are running a hosted service that relies on Twilio you can create a Twilio subaccount for
     * each customer that signs up. Then if a customer closes his or her account with your service, you can simply
     * deactivate the associated Twilio subaccount.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:createSubAccount}
     *
     * @param friendlyName A human readable description of the new subaccount, up to 64 characters. Defaults to "SubAccount Created at {YYYY-MM-DD HH:MM meridiam}".
     * @return a representation of the account
     */
    @Processor
    public String createSubAccount(@Optional String friendlyName) {
        return twilioClient.createSubAccount(friendlyName);
    }

    /**
     * Subaccounts in Twilio are just accounts that are "owned" by another account. Using a subaccount you can segment
     * each of your customers' use of Twilio and keep it separate from all the rest, allowing you to easily manage the
     * activity and resources of each customer independently.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getSubAccountByAccountSid}
     *
     * @param accountSid the account sid to use in the query
     * @return a representation of the subaccount
     */
    @Processor
    public String getSubAccountByAccountSid(String accountSid) {
        return twilioClient.getSubAccountBySid(accountSid);
    }

    /**
     * Subaccounts in Twilio are just accounts that are "owned" by another account. Using a subaccount you can segment
     * each of your customers' use of Twilio and keep it separate from all the rest, allowing you to easily manage the
     * activity and resources of each customer independently.
     * <p/>
     * For instance, if you are running a hosted service that relies on Twilio you can create a Twilio subaccount for
     * each customer that signs up. Then if a customer closes his or her account with your service, you can simply
     * deactivate the associated Twilio subaccount.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getSubAccountByFriendlyName}
     *
     * @param friendlyName the friendly name to use in the query
     * @return a representation of the subaccount
     */
    @Processor
    public String getSubAccountByFriendlyName(String friendlyName) {
        return twilioClient.getSubAccountByFriendlyName(friendlyName);
    }

    /**
     * Trasfers a number between two subaccounts.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:exchangePhoneNumbersBetweenSubaccounts}
     *
     * @param accountSidFrom         the account sid from where to get the number, leave empty to use {@link TwilioConnector#accountSid}
     * @param incomingPhoneNumberSid the incoming phone number sid to transfer
     * @param accountSidTo           the account sid where to transfer the phone number.
     * @return an incoming phone number instance.
     */
    @Processor
    public String exchangePhoneNumbersBetweenSubaccounts(@Optional String accountSidFrom,
                                                         String incomingPhoneNumberSid,
                                                         String accountSidTo) {
        return twilioClient.exchangePhoneNumbersBetweenSubaccounts(accountSidFrom, incomingPhoneNumberSid, accountSidTo);

    }

    /**
     * Returns a list of local AvailablePhoneNumber resource representations that match the specified filters, each representing a phone number that is currently available for provisioning within your account.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAvailablePhoneNumbers}
     *
     * @param accountSid     optionally override globally configured Account SID
     * @param isoCountryCode a country code in ISO 3166-1 alpha-2 format (e.g. 'US' for United States, 'CA' for Canada).
     * @param areaCode       Find phone numbers in the specified Area Code. Only available for North American numbers.
     * @param contains       A pattern to match phone numbers on. Valid characters are '*' and [0-9a-zA-Z]. The '*' character will match any single digit.
     * @param inRegion       Limit results to a particular region (i.e. State/Province). Given a phone number, search within the same Region as that number.
     * @param inPostalCode   Limit results to a particular postal code. Given a phone number, search within the same postal code as that number.
     * @return a list of local AvailablePhoneNumber resource representations that match the specified filters
     */
    @Processor
    public String getAvailablePhoneNumbers(@Optional String accountSid,
                                           String isoCountryCode,
                                           @Optional String areaCode,
                                           @Optional String contains,
                                           @Optional String inRegion,
                                           @Optional String inPostalCode) {
        return twilioClient.getAvailablePhoneNumbers(accountSid, isoCountryCode, areaCode, contains, inRegion, inPostalCode);
    }

    /**
     * Returns a list of local AvailablePhoneNumber resource representations that match the specified filters, each representing a phone number that is currently available for provisioning within your account.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAvailablePhoneNumbersAdvancedSeach}
     *
     * @param accountSid      optionally override globally configured Account SID
     * @param isoCountryCode  a country code in ISO 3166-1 alpha-2 format (e.g. 'US' for United States, 'CA' for Canada).
     * @param areaCode        Find phone numbers in the specified Area Code. Only available for North American numbers.
     * @param contains        A pattern to match phone numbers on. Valid characters are '*' and [0-9a-zA-Z]. The '*' character will match any single digit.
     * @param inRegion        Limit results to a particular region (i.e. State/Province). Given a phone number, search within the same Region as that number.
     * @param inPostalCode    Limit results to a particular postal code. Given a phone number, search within the same postal code as that number.
     * @param nearLatLong     Given a latitude/longitude pair lat,long find geographically close numbers within Distance miles.
     * @param nearPhoneNumber Given a phone number, find a geographically close number within Distance miles. Distance defaults to 25 miles.
     * @param inLata          Limit results to a specific Local access and transport area (LATA). Given a phone number, search within the same LATA as that number.
     * @param inRateCenter    Limit results to a specific rate center, or given a phone number search within the same rate center as that number. Requires InLata to be set as well.
     * @param distance        Specifies the search radius for a Near- query in miles. If not specified this defaults to 25 miles.
     * @return a list of local AvailablePhoneNumber resource representations that match the specified filters
     */
    @Processor
    public String getAvailablePhoneNumbersAdvancedSeach(@Optional String accountSid,
                                                        String isoCountryCode,
                                                        @Optional String areaCode,
                                                        @Optional String contains,
                                                        @Optional String inRegion,
                                                        @Optional String inPostalCode,
                                                        @Placement(group = "Advanced") @Optional String nearLatLong,
                                                        @Placement(group = "Advanced") @Optional String nearPhoneNumber,
                                                        @Placement(group = "Advanced") @Optional String inLata,
                                                        @Placement(group = "Advanced") @Optional String inRateCenter,
                                                        @Placement(group = "Advanced") @Optional String distance) {
        return twilioClient.getAvailablePhoneNumbersAdvancedSeach(accountSid, isoCountryCode, areaCode, contains, inRegion, inPostalCode, nearLatLong, nearPhoneNumber, inLata, inRateCenter, distance);
    }

    /**
     * Returns a list of toll-free AvailablePhoneNumber elements that match the specified filters, each representing a phone number that is currently available for provisioning within your account.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAvailableTollFreeNumbers}
     *
     * @param accountSid     optionally override globally configured Account SID
     * @param isoCountryCode a country code in ISO 3166-1 alpha-2 format (e.g. 'US' for United States, 'CA' for Canada).
     * @param contains       A pattern to match phone numbers on. Valid characters are '*' and [0-9a-zA-Z]. The '*' character will match any single digit.
     * @return a list of toll-free AvailablePhoneNumber elements that match the specified filters
     */
    @Processor
    public String getAvailableTollFreeNumbers(@Optional String accountSid,
                                              String isoCountryCode,
                                              @Optional String contains) {
        return twilioClient.getAvailableTollFreeNumbers(accountSid, isoCountryCode, contains);
    }

    /**
     * Returns an outgoing caller id instance matching the given filters.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getOutgoingCallerIdByOutgoingCallerIdSid}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param outgoingCallerIdSid the outgoing caller id sid to use in the query
     * @return an outgoing caller id instance matching the given filters.
     */
    @Processor
    public String getOutgoingCallerIdByOutgoingCallerIdSid(@Optional String accountSid,
                                                           String outgoingCallerIdSid) {
        return twilioClient.getOutgoingCallerIdByOutgoingCallerIdSid(accountSid, outgoingCallerIdSid);
    }

    /**
     * Updates the caller id, and returns the updated resource if successful.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:updateOutgoingCallerIdByOutgoingCallerIdSid}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param outgoingCallerIdSid the outgoing caller id sid to update
     * @param friendlyName        A human readable description of a Caller ID, with maximum length of 64 characters. Defaults to a nicely formatted version of the phone number.
     * @return returns the updated resource if successful.
     */
    @Processor
    public String updateOutgoingCallerIdByOutgoingCallerIdSid(@Optional String accountSid,
                                                              String outgoingCallerIdSid,
                                                              String friendlyName) {
        return twilioClient.updateOutgoingCallerIdByOutgoingCallerIdSid(accountSid, outgoingCallerIdSid, friendlyName);
    }

    /**
     * Returns a list of OutgoingCallerId resource representations, each representing a Caller ID number valid for an account. The list includes paging information.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAllOutgoingCallerIds}
     *
     * @param accountSid   optionally override globally configured Account SID
     * @param phoneNumber  Only show the caller id resource that exactly matches this phone number.
     * @param friendlyName Only show the caller id resource that exactly matches this name.
     * @return a list of OutgoingCallerId resource representations
     */
    @Processor
    public String getAllOutgoingCallerIds(@Optional String accountSid,
                                          @Optional String phoneNumber,
                                          @Optional String friendlyName) {
        return twilioClient.getAllOutgoingCallerIds(accountSid, phoneNumber, friendlyName);
    }

    /**
     * Adds a new CallerID to your account. After making this request, Twilio will return to you a validation code and Twilio will dial the phone number given to perform validation. The code returned must be entered via the phone before the CallerID will be added to your account.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:addNewCallerId}
     *
     * @param accountSid   optionally override globally configured Account SID
     * @param phoneNumber  The phone number to verify. Should be formatted with a '+' and country code e.g., +16175551212 (E.164 format). Twilio will also accept unformatted US numbers e.g., (415) 555-1212, 415-555-1212.
     * @param friendlyName A human readable description for the new caller ID with maximum length 64 characters. Defaults to a nicely formatted version of the number.
     * @param callDelay    The number of seconds, between 0 and 60, to delay before initiating the validation call. Defaults to 0.
     * @param extension    Digits to dial after connecting the validation call.
     * @return a validation request (see validation request propertie)
     */
    @Processor
    public String addNewCallerId(@Optional String accountSid,
                                 String phoneNumber,
                                 @Optional String friendlyName,
                                 @Optional Integer callDelay,
                                 @Optional String extension) {
        return twilioClient.addNewCallerId(accountSid, phoneNumber, friendlyName, callDelay, extension);
    }

    /**
     * Deletes the caller ID from the account. Returns an HTTP 204 response if successful, with no body.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:deleteOutgoingCallerId}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param outgoingCallerIdSid the outgoing caller id sid to delete
     * @return an HTTP 204 response if successful, with no body.
     */
    @Processor
    public String deleteOutgoingCallerId(@Optional String accountSid,
                                         String outgoingCallerIdSid) {
        return twilioClient.deleteOutgoingCallerId(accountSid, outgoingCallerIdSid);

    }

    /**
     * Returns an incoming phone numbers matching the given query filters.
     * <p/>
     * An IncomingPhoneNumber instance resource represents a Twilio phone number purchased from Twilio or ported to Twilio.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getIncomingPhoneNumbersByIncomingPhoneNumberSid}
     *
     * @param accountSid             optionally override globally configured Account SID
     * @param incomingPhoneNumberSid the incoming phone number sid to use in the query
     * @return an incoming phone number matching the given incoming phone number sid
     */
    @Processor
    public String getIncomingPhoneNumbersByIncomingPhoneNumberSid(@Optional String accountSid,
                                                                  String incomingPhoneNumberSid) {
        return twilioClient.getIncomingPhoneNumbers(accountSid, incomingPhoneNumberSid);
    }

    /**
     * Tries to update the incoming phone number's properties, and returns the updated resource representation if successful.
     * <p/>
     * * An IncomingPhoneNumber instance resource represents a Twilio phone number purchased from Twilio or ported to Twilio.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:updateIncomingPhoneNumbers}
     *
     * @param accountSid             optionally override globally configured Account SID
     * @param incomingPhoneNumberSid the incoming phone number sid to use
     * @param friendlyName           A human readable description of the new incoming phone number resource, with maximum length 64 characters.
     * @param apiVersion             Calls to this phone number will start a new TwiML session with this API version. Either 2010-04-01 or 2008-08-01.
     * @param voiceUrl               The URL that Twilio should request when somebody dials the new phone number.
     * @param voiceMethod            The HTTP method that should be used to request the VoiceUrl. Must be either GET or POST. Defaults to POST.
     * @param voiceFallback          A flow that Twilio will request if an error occurs requesting or executing the TwiML at Url.
     * @param statusCallback         A flow that Twilio will request to pass statusCallback parameters (such as call ended) to your application.
     * @param voiceCallerIdLookup    Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false.
     * @param voiceApplicationSid    The 34 character sid of the application Twilio should use to handle phone calls to this number. If a VoiceApplicationSid is present, Twilio will ignore all of the voice urls above and use those set on the application instead.
     * @param smsUrl                 The URL that Twilio should request when somebody sends an SMS to the phone number.
     * @param smsMethod              The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.
     * @param smsFallback            A flow that Twilio will request if an error occurs requesting or executing the TwiML defined by sms.
     * @param smsApplicationSid      The 34 character sid of the application Twilio should use to handle SMSs sent to this number. If a SmsApplicationSid is present, Twilio will ignore all of the SMS urls above and use those set on the application instead.
     * @param accountSidDestination  The unique 34 character id of the account to which you wish to transfer this phone number.
     * @return if successful the updated incoming phone number instance.
     */

    @Processor
    public String updateIncomingPhoneNumbers(@Optional String accountSid,
                                             String incomingPhoneNumberSid,
                                             @Optional String friendlyName,
                                             @Optional String apiVersion,
                                             @Placement(group = "Voice Settings") @Optional String voiceUrl,
                                             @Placement(group = "Voice Settings") @Optional HttpMethod voiceMethod,
                                             @Placement(group = "Voice Settings") @Optional HttpCallback voiceFallback,
                                             @Placement(group = "Voice Settings") @Optional HttpCallback statusCallback,
                                             @Placement(group = "Voice Settings") @Optional Boolean voiceCallerIdLookup,
                                             @Placement(group = "Voice Settings") @Optional String voiceApplicationSid,
                                             @Placement(group = "SMS Settings") @Optional String smsUrl,
                                             @Placement(group = "SMS Settings") @Optional HttpMethod smsMethod,
                                             @Placement(group = "SMS Settings") @Optional HttpCallback smsFallback,
                                             @Placement(group = "SMS Settings")  @Optional String smsApplicationSid,
                                             @Optional String accountSidDestination) {
        return twilioClient.updateIncomingPhoneNumbers(accountSid, incomingPhoneNumberSid, friendlyName, apiVersion, voiceUrl,
                voiceMethod, voiceFallback, statusCallback, voiceCallerIdLookup, voiceApplicationSid, smsUrl, smsMethod, smsFallback, smsApplicationSid, accountSidDestination);
    }

    /**
     * Release this phone number from your account. Twilio will no longer answer calls to this number, and you will stop being billed the monthly phone number fee. The phone number will eventually be recycled and potentially given to another customer, so use with care.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:deleteIncomingPhoneNumber}
     *
     * @param accountSid             optionally override globally configured Account SID
     * @param incomingPhoneNumberSid the incoming phone number sid to delete
     * @return if succesful, returns an HTTP 204 response with no body.
     */
    @Processor
    public String deleteIncomingPhoneNumber(@Optional String accountSid, String incomingPhoneNumberSid) {
        return twilioClient.deleteIncomingPhoneNumber(accountSid, incomingPhoneNumberSid);
    }

    /**
     * Returns a list of IncomingPhoneNumber resource representations, each representing a phone number given to your account. The list includes paging information.
     * <p/>
     * An IncomingPhoneNumber instance resource represents a Twilio phone number purchased from Twilio or ported to Twilio.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getIncomingPhoneNumbers}
     *
     * @param accountSid   optionally override globally configured Account SID
     * @param phoneNumber  Only show the incoming phone number resources that match this pattern. You can specify partial numbers and use '*' as a wildcard for any digit.
     * @param friendlyName Only show the incoming phone number resources with friendly names that exactly match this name.
     * @return a list of IncomingPhoneNumber resource representations
     */
    @Processor
    public String getIncomingPhoneNumbers(@Optional String accountSid,
                                          @Optional String phoneNumber,
                                          @Optional String friendlyName) {
        return twilioClient.getIncomingPhoneNumbers(accountSid, phoneNumber, friendlyName);
    }

    /**
     * Adds a new phone number to your account. If a phone number is found for your request, Twilio will add it to your account and bill you for the first month's cost of the phone number. If Twilio can't find a phone number to match your request, you will receive an HTTP 400 with Twilio error code 21452.
     * If successful, Twilio responds with a representation of the new phone number that was assigned to your account.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:addIncomingPhoneNumberByPhoneNumber}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param phoneNumber         The phone number you want to purchase. The number should be formated starting with a '+' followed by the country code and the number in E.164 format e.g., '+15105555555'.
     * @param friendlyName        A human readable description of the new incoming phone number. Maximum 64 characters. Defaults to a formatted version of the number.
     * @param voiceUrl            The URL that Twilio should request when somebody dials the new phone number.
     * @param voiceMethod         The HTTP method that should be used to request the VoiceUrl. Must be either GET or POST. Defaults to POST.
     * @param voiceFallback       A flow that Twilio will request if an error occurs requesting or executing the TwiML at Url.
     * @param statusCallback      A flow that Twilio will request to pass status parameters (such as call ended) to your application.
     * @param voiceCallerIdLookup Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false. Defaults to false.
     * @param voiceApplicationSid The 34 character sid of the application Twilio should use to handle phone calls to the new number. If a VoiceApplicationSid is present, Twilio will ignore all of the voice urls above and use those set on the application.
     * @param smsUrl              The URL that Twilio should request when somebody sends an SMS to the phone number.
     * @param smsMethod           The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.
     * @param smsFallback         A flow that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.
     * @param smsApplicationSid   The 34 character sid of the application Twilio should use to handle SMSs sent to the new number. If a SmsApplicationSid is present, Twilio will ignore all of the SMS urls above and use those set on the application.
     * @return If successful, Twilio responds with a representation of the new phone number that was assigned to your account.
     */
    @Processor
    public String addIncomingPhoneNumberByPhoneNumber(@Optional String accountSid,
                                                      String phoneNumber,
                                                      @Optional String friendlyName,
                                                      @Placement(group = "Voice Settings") @Optional String voiceUrl,
                                                      @Placement(group = "Voice Settings") @Optional HttpMethod voiceMethod,
                                                      @Placement(group = "Voice Settings") @Optional HttpCallback voiceFallback,
                                                      @Placement(group = "Voice Settings") @Optional HttpCallback statusCallback,
                                                      @Placement(group = "Voice Settings")  @Optional Boolean voiceCallerIdLookup,
                                                      @Placement(group = "Voice Settings") @Optional String voiceApplicationSid,
                                                      @Placement(group = "SMS Settings") @Optional String smsUrl,
                                                      @Placement(group = "SMS Settings") @Optional HttpMethod smsMethod,
                                                      @Placement(group = "SMS Settings") @Optional HttpCallback smsFallback,
                                                      @Placement(group = "SMS Settings") @Optional String smsApplicationSid) {
        return twilioClient.addIncomingPhoneNumberByPhoneNumber(accountSid, phoneNumber, friendlyName, voiceUrl, voiceMethod, voiceFallback,
                statusCallback, voiceCallerIdLookup, voiceApplicationSid, smsUrl, smsMethod, smsFallback, smsApplicationSid);
    }

    /**
     * Adds a new phone number to your account. If a phone number is found for your request, Twilio will add it to your account and bill you for the first month's cost of the phone number. If Twilio can't find a phone number to match your request, you will receive an HTTP 400 with Twilio error code 21452.
     * If successful, Twilio responds with a representation of the new phone number that was assigned to your account.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:addIncomingPhoneNumberByAreaCode}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param areaCode            The area code in which you'd like a new incoming phone number. Any three digit, US area code is valid. Twilio will provision a random phone number within this area code for you.
     * @param friendlyName        A human readable description of the new incoming phone number. Maximum 64 characters. Defaults to a formatted version of the number.
     * @param voiceUrl            The URL that Twilio should request when somebody dials the new phone number.
     * @param voiceMethod         The HTTP method that should be used to request the VoiceUrl. Must be either GET or POST. Defaults to POST.
     * @param voiceFallback       A flow that Twilio will request if an error occurs requesting or executing the TwiML at Url.
     * @param statusCallback      A flow that Twilio will request to pass status parameters (such as call ended) to your application.
     * @param voiceCallerIdLookup Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false. Defaults to false.
     * @param voiceApplicationSid The 34 character sid of the application Twilio should use to handle phone calls to the new number. If a VoiceApplicationSid is present, Twilio will ignore all of the voice urls above and use those set on the application.
     * @param smsUrl              The URL that Twilio should request when somebody sends an SMS to the phone number.
     * @param smsMethod           The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.
     * @param smsFallback         A flow that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.
     * @param smsApplicationSid   The 34 character sid of the application Twilio should use to handle SMSs sent to the new number. If a SmsApplicationSid is present, Twilio will ignore all of the SMS urls above and use those set on the application.
     * @return If successful, Twilio responds with a representation of the new phone number that was assigned to your account.
     */
    @Processor
    public String addIncomingPhoneNumberByAreaCode(@Optional String accountSid,
                                                   String areaCode,
                                                   @Optional String friendlyName,
                                                   @Placement(group = "Voice Settings") @Optional String voiceUrl,
                                                   @Placement(group = "Voice Settings") @Optional HttpMethod voiceMethod,
                                                   @Placement(group = "Voice Settings") @Optional HttpCallback voiceFallback,
                                                   @Placement(group = "Voice Settings") @Optional HttpCallback statusCallback,
                                                   @Placement(group = "Voice Settings") @Optional Boolean voiceCallerIdLookup,
                                                   @Placement(group = "Voice Settings") @Optional String voiceApplicationSid,
                                                   @Placement(group = "SMS Settings") @Optional String smsUrl,
                                                   @Placement(group = "SMS Settings") @Optional HttpMethod smsMethod,
                                                   @Placement(group = "SMS Settings") @Optional HttpCallback smsFallback,
                                                   @Placement(group = "SMS Settings") @Optional String smsApplicationSid) {
        return twilioClient.addIncomingPhoneNumberByAreaCode(accountSid, areaCode, friendlyName, voiceUrl, voiceMethod, voiceFallback,
                statusCallback, voiceCallerIdLookup, voiceApplicationSid, smsUrl, smsMethod, smsFallback, smsApplicationSid);
    }

    /**
     * Returns an application resource matching the given application sid.
     * <p/>
     * An Application instance resource represents an application that you have created with Twilio. An application inside of Twilio is just a set of URLs and other configuration data that tells Twilio how to behave when one of your Twilio numbers receives a call or SMS message.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getApplication}
     *
     * @param accountSid     optionally override globally configured Account SID
     * @param applicationSid the application sid to use in the query
     * @return an application resource matching the given application sid.
     */
    @Processor
    public String getApplication(@Optional String accountSid,
                                 String applicationSid) {
        return twilioClient.getApplication(accountSid, applicationSid);

    }

    /**
     * Tries to update the application's properties, and returns the updated resource representation if successful.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:updateApplication}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param applicationSid      the application sid to update
     * @param friendlyName        A human readable description of the application, with maximum length 64 characters.
     * @param apiVersion          Requests to this application's URLs will start a new TwiML session with this API version. Either 2010-04-01 or 2008-08-01.
     * @param voiceUrl            The URL that Twilio should request when somebody dials a phone number assigned to this application.
     * @param voiceMethod         The HTTP method that should be used to request the VoiceUrl. Either GET or POST.
     * @param voiceFallback       A flow that Twilio will request if an error occurs requesting or executing the TwiML defined by VoiceUrl.
     * @param statusCallback      A flow that Twilio will request to pass status parameters (such as call ended) to your application.
     * @param voiceCallerIdLookup Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false.
     * @param smsUrl              The URL that Twilio should request when somebody sends an SMS to a phone number assigned to this application.
     * @param smsMethod           The HTTP method that should be used to request the SmsUrl. Either GET or POST.
     * @param smsFallback         A flow that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.
     * @param smsStatusCallback   Twilio will make a POST request to this flow to pass status parameters (such as sent or failed) to your application if you specify this application's Sid as the ApplicationSid on an outgoing SMS request.
     * @return returns the updated resource representation if successful.
     */
    @Processor
    public String updateApplication(@Optional String accountSid,
                                    String applicationSid,
                                    @Optional String friendlyName,
                                    @Optional String apiVersion,
                                    @Placement(group = "Voice Settings") @Optional String voiceUrl,
                                    @Placement(group = "Voice Settings") @Optional HttpMethod voiceMethod,
                                    @Placement(group = "Voice Settings") @Optional HttpCallback voiceFallback,
                                    @Placement(group = "Voice Settings") @Optional HttpCallback statusCallback,
                                    @Placement(group = "Voice Settings") @Optional Boolean voiceCallerIdLookup,
                                    @Placement(group = "SMS Settings") @Optional String smsUrl,
                                    @Placement(group = "SMS Settings") @Optional HttpMethod smsMethod,
                                    @Placement(group = "SMS Settings") @Optional HttpCallback smsFallback,
                                    @Placement(group = "SMS Settings") @Optional HttpCallback smsStatusCallback) {
        return twilioClient.updateApplication(accountSid, applicationSid, friendlyName, apiVersion, voiceUrl, voiceMethod, voiceFallback, statusCallback, voiceCallerIdLookup,
                smsUrl, smsMethod, smsFallback, smsStatusCallback);
    }

    /**
     * Delete this application. If this application's sid is assigned to any IncomingPhoneNumber resources as a VoiceApplicationSid or SmsApplicationSid it will be removed.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:deleteApplication}
     *
     * @param accountSid     optionally override globally configured Account SID
     * @param applicationSid the application sid to use
     * @return If succesful, Twilio will return an HTTP 204 response with no body.
     */
    @Processor
    public String deleteApplication(@Optional String accountSid,
                                    String applicationSid) {
        return twilioClient.deleteApplication(accountSid, applicationSid);
    }

    /**
     * Returns a list of Application resource representations, each representing an application within your account. The list includes paging information.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAllApplications}
     *
     * @param accountSid   optionally override globally configured Account SID
     * @param friendlyName Only return the Application resources with friendly names that exactly match this name.
     * @return a list of Application resource representations
     */
    @Processor
    public String getAllApplications(@Optional String accountSid,
                                     @Optional String friendlyName) {
        return twilioClient.getAllApplications(accountSid, friendlyName);
    }

    /**
     * Creates a new application within your account. If successful, Twilio responds with a representation of the new application.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:createApplication}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param friendlyName        A human readable description of the new application. Maximum 64 characters.
     * @param apiVersion          Requests to this application's URLs will start a new TwiML session with this API version. Either 2010-04-01 or 2008-08-01. Defaults to your account's default API version.
     * @param voiceUrl            The URL that Twilio should request when somebody dials a phone number assigned to this application
     * @param voiceMethod         The HTTP method that should be used to request the VoiceUrl. Must be either GET or POST. Defaults to POST.
     * @param voiceFallback       A flow that Twilio will request if an error occurs requesting or executing the TwiML at Url.
     * @param statusCallback      A flow that Twilio will request to pass status parameters (such as call ended) to your application.
     * @param voiceCallerIdLookup Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false. Defaults to false.
     * @param smsUrl              The URL that Twilio should request when somebody sends an SMS to a phone number assigned to this application.
     * @param smsMethod           The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.
     * @param smsFallback         A flow that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.
     * @param smsStatusCallback   Twilio will make a POST request to this flow to pass status parameters (such as sent or failed) to your application if you specify this application's Sid as the ApplicationSid on an outgoing SMS request.
     * @return If successful, Twilio responds with a representation of the new application.
     */
    @Processor
    public String createApplication(@Optional String accountSid,
                                    String friendlyName,
                                    @Optional String apiVersion,
                                    @Placement(group = "Voice Settings") @Optional String voiceUrl,
                                    @Placement(group = "Voice Settings") @Optional HttpMethod voiceMethod,
                                    @Placement(group = "Voice Settings") @Optional HttpCallback voiceFallback,
                                    @Placement(group = "Voice Settings") @Optional HttpCallback statusCallback,
                                    @Placement(group = "Voice Settings") @Optional Boolean voiceCallerIdLookup,
                                    @Placement(group = "SMS Settings") @Optional String smsUrl,
                                    @Placement(group = "SMS Settings") @Optional HttpMethod smsMethod,
                                    @Placement(group = "SMS Settings") @Optional HttpCallback smsFallback,
                                    @Placement(group = "SMS Settings") @Optional HttpCallback smsStatusCallback) {
        return twilioClient.createApplication(accountSid, friendlyName, apiVersion, voiceUrl, voiceMethod,
                voiceFallback, statusCallback, voiceCallerIdLookup, smsUrl, smsMethod, smsFallback, smsStatusCallback);
    }

    /**
     * Returns the single Call resource identified by the given call sid.
     * <p/>
     * A Call instance resource represents a connection between a telephone and Twilio. This may be inbound, when a person calls your application, or outbound, when your application initiates the call, either via the REST API (see Making Calls) or during a call via the TwiML <Dial> verb. The Calls list resource represents the set of phone calls made to and from an account.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getCall}
     *
     * @param accountSid optionally override globally configured Account SID
     * @param callSid    the call sid to use in the query
     * @return the single Call resource identified by the given call sid.
     */
    @Processor
    public String getCall(@Optional String accountSid,
                          String callSid) {
        return twilioClient.getCall(accountSid, callSid);
    }

    /**
     * Returns a list of phone calls made to and from the account identified by the given account sid. The list includes paging information and is sorted by DateUpdated with most-recent calls first.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getCalls}
     *
     * @param accountSid optionally override globally configured Account SID
     * @param to         Only show calls to this phone number.
     * @param from       Only show calls from this phone number.
     * @param status     Only show calls currently in this status. May be queued, ringing, in-progress, completed, failed, busy, or no-answer.
     * @param startTime  Only show calls that started on this date, given as YYYY-MM-DD. Also supports inequalities, such as StartTime<=YYYY-MM-DD for calls that started at or before midnight on a date, and StartTime>=YYYY-MM-DD for calls that started at or after midnight on a date.
     * @return a list of phone calls made to and from the account identified by the given account sid
     */
    @Processor
    public String getCalls(@Optional String accountSid,
                           @Optional String to,
                           @Optional String from,
                           @Optional String status,
                           @Optional String startTime) {
        return twilioClient.getCalls(accountSid, to, from, status, startTime);
    }

    /**
     * Initiates a call using the given paramaters. Only one of url or applicationSid parameters must be specified, not both. Returns the call representation.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:makeCall}
     *
     * @param accountSid     optionally override globally configured Account SID
     * @param from           The phone number to use as the caller id. Format with a '+' and country code e.g., +16175551212 (E.164 format). Must be a Twilio number or a valid outgoing caller id for your account.
     * @param to             The number to call formatted with a '+' and country code e.g., +16175551212 (E.164 format). Twilio will also accept unformatted US numbers e.g., (415) 555-1212, 415-555-1212.
     * @param url            The fully qualified URL that should be consulted when the call connects. Just like when you set a URL on a phone number for handling inbound calls.
     * @param applicationSid The 34 character sid of the application Twilio should use to handle this phone call. If this parameter is present, Twilio will ignore all of the voice URLs passed and use the URLs set on the application.
     * @param method         The HTTP method Twilio should use when making its request to the above Url parameter's value. Defaults to POST. If an ApplicationSid parameter is present, this parameter is ignored.
     * @param fallback       A flow that Twilio will request if an error occurs requesting or executing the TwiML at Url. If an ApplicationSid parameter is present, this parameter is ignored.
     * @param statusCallback A flow that Twilio will request when the call ends to notify your app. If an ApplicationSid parameter is present, this parameter is ignored.
     * @param sendDigits     A string of keys to dial after connecting to the number. Valid digits in the string include: any digit (0-9), '#' and '*'. For example, if you connected to a company phone number, and wanted to dial extension 1234 and then the pound key, use SendDigits=1234#. Remember to URL-encode this string, since the '#' character has special meaning in a URL.
     * @param ifMachine      Tell Twilio to try and determine if a machine (like voicemail) or a human has answered the call. Possible values are Continue and Hangup.
     * @param timeout        The integer number of seconds that Twilio should allow the phone to ring before assuming there is no answer. Default is 60 seconds, the maximum is 999 seconds. Note, you could set this to a low value, such as 15, to hangup before reaching an answering machine or voicemail.
     * @return the call representation.
     */
    @Processor
    public String makeCall(@Optional String accountSid,
                           String from,
                           String to,
                           @Placement(group = "Voice Settings") @Optional String url,
                           @Placement(group = "Voice Settings") @Optional String applicationSid,
                           @Placement(group = "Voice Settings") @Optional HttpMethod method,
                           @Placement(group = "Callbacks") @Optional HttpCallback fallback,
                           @Placement(group = "Callbacks") @Optional HttpCallback statusCallback,
                           @Placement(group = "Advanced") @Optional String sendDigits,
                           @Placement(group = "Advanced") @Optional String ifMachine,
                           @Placement(group = "Advanced") @Optional String timeout) {
        return twilioClient.makeCall(accountSid, from, to, url, applicationSid, method, fallback, statusCallback, sendDigits, ifMachine, timeout);
    }

    /**
     * Modify the state of a live call. Returns the call representation.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:changeCallState}
     *
     * @param accountSid optionally override globally configured Account SID
     * @param callSid    the call sid to modify
     * @param url        A valid URL that returns TwiML. Twilio will immediately redirect the call to the new TwiML.
     * @param method     The HTTP method Twilio should use when requesting the above URL. Defaults to POST.
     * @param status     Either canceled or completed. Specifying canceled will attempt to hangup calls that are queued or ringing but not affect calls already in progress. Specifying completed will attempt to hang up a call even if it's already in progress.
     * @return the call representation
     */
    @Processor
    public String changeCallState(@Optional String accountSid,
                                  String callSid,
                                  @Optional String url,
                                  @Optional HttpMethod method,
                                  @Optional String status) {
        return twilioClient.changeCallState(accountSid, callSid, url, method, status);
    }

    /**
     * Returns a representation of the conference identified by the given conference id.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getConference}
     *
     * @param accountSid    optionally override globally configured Account SID
     * @param conferenceSid the conference sid to use in the query
     * @return a representation of the conference identified by the given conference id.
     */
    @Processor
    public String getConference(@Optional String accountSid,
                                String conferenceSid) {
        return twilioClient.getConference(accountSid, conferenceSid);
    }

    /**
     * Returns a list of conferences within an account. The list includes paging information and is sorted by DateUpdated, with most recent conferences first.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getConferences}
     *
     * @param accountSid   optionally override globally configured Account SID
     * @param status       Only show conferences currently in with this status. May be init, in-progress, or completed.
     * @param friendlyName List conferences who's FriendlyName is the exact match of this string.
     * @param dateCreated  Only show conferences that started on this date, given as YYYY-MM-DD. You can also specify inequality, such as DateCreated<=YYYY-MM-DD for conferences that started at or before midnight on a date, and DateCreated>=YYYY-MM-DD for conferences that started at or after midnight on a date.
     * @param dateUpdated  Only show conferences that were last updated on this date, given as YYYY-MM-DD. You can also specify inequality, such as DateUpdated<=YYYY-MM-DD for conferences that were last updated at or before midnight on a date, and DateUpdated>=YYYY-MM-DD for conferences that were updated at or after midnight on a date.
     * @return a list of conferences within an account
     */
    @Processor
    public String getConferences(@Optional String accountSid,
                                 @Optional String status,
                                 @Optional String friendlyName,
                                 @Optional String dateCreated,
                                 @Optional String dateUpdated) {
        return twilioClient.getConferences(accountSid, status, friendlyName, dateCreated, dateUpdated);
    }

    /**
     * Returns a representation of this participant.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getParticipant}
     *
     * @param accountSid    optionally override globally configured Account SID
     * @param conferenceSid the conference sid to use in the query
     * @param callSid       the call sid to use in the query
     * @return a representation of this participant.
     */
    @Processor
    public String getParticipant(@Optional String accountSid,
                                 String conferenceSid,
                                 String callSid) {
        return twilioClient.getParticipant(accountSid, conferenceSid, callSid);
    }

    /**
     * Updates the participant status. Returns the participant representation.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:updateParticipantStatus}
     *
     * @param accountSid    optionally override globally configured Account SID
     * @param conferenceSid the conference sid to use
     * @param callSid       the call sid to use
     * @param muted         Specifying true will mute the participant, while false will un-mute.
     * @return the participant representation
     */
    @Processor
    public String updateParticipantStatus(@Optional String accountSid,
                                          String conferenceSid,
                                          String callSid,
                                          Boolean muted) {
        return twilioClient.updateParticipantStauts(accountSid, conferenceSid, callSid, muted);

    }

    /**
     * Kick this participant from the conference. Returns HTTP 204 (No Content), with no body, if the participant was successfuly booted from the conference.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:deleteParticipant}
     *
     * @param accountSid    optionally override globally configured Account SID
     * @param conferenceSid the conference sid to use
     * @param callSid       the call sid to use
     * @return Returns HTTP 204 (No Content), with no body, if the participant was successfuly booted from the conference.
     */
    @Processor
    public String deleteParticipant(@Optional String accountSid,
                                    String conferenceSid,
                                    String callSid) {
        return twilioClient.deleteParticipant(accountSid, conferenceSid, callSid);
    }

    /**
     * Returns the list of participants in the conference identified by the given conference sid.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getParticipants}
     *
     * @param accountSid    optionally override globally configured Account SID
     * @param conferenceSid the conference sid to use in the query
     * @param muted         Only show participants that are muted or unmuted. Either true or false.
     * @return he list of participants in the conference identified by the given conference sid.
     */
    @Processor
    public String getParticipants(@Optional String accountSid,
                                  String conferenceSid,
                                  @Optional Boolean muted) {
        return twilioClient.getParticipants(accountSid, conferenceSid, muted);
    }

    /**
     * Returns a single SMS message specified by the provided SMS message sid.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getSmsMessage}
     *
     * @param accountSid    optionally override globally configured Account SID
     * @param smsMessageSid the SMS message sid to use in the query.
     * @return a single SMS message specified by the provided SMS message sid.
     */
    @Processor
    public String getSmsMessage(@Optional String accountSid,
                                String smsMessageSid) {
        return twilioClient.getSmsMessage(accountSid, smsMessageSid);
    }

    /**
     * Returns a list of SMS messages associated with your account. The list includes paging information and is sorted by DateSent, with most recent messages first.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAllSmsMessages}
     *
     * @param accountSid optionally override globally configured Account SID
     * @param to         Only show SMS messages to this phone number.
     * @param from       Only show SMS messages from this phone number.
     * @param dateSent   Only show SMS messages sent on this date, given as YYYY-MM-DD. Example: DateSent=2009-07-06. You can also specify inequality, such as DateSent<=YYYY-MM-DD for SMS messages that were sent on or before midnight on a date, and DateSent>=YYYY-MM-DD for SMS messages sent on or after midnight on a date.
     * @return a list of SMS messages associated with your account
     */
    @Processor
    public String getAllSmsMessages(@Optional String accountSid,
                                    @Optional String to,
                                    @Optional String from,
                                    @Optional String dateSent) {
        return twilioClient.getAllSmsMessages(accountSid, to, from, dateSent);
    }

    /**
     * Send an outgoing SMS message. By specifying an SMS URL for your SMS enabled Twilio phone number, Twilio will make a request to your application to notify you when someone replies to a message you send.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:sendSmsMessage}
     *
     * @param accountSid     optionally override globally configured Account SID
     * @param from           A Twilio number enabled for SMS. Only phone numbers purchased from Twilio work here; you cannot (for example) spoof SMS messages from your own cell phone number.
     * @param to             The destination phone number. Format with a '+' and country code e.g., +16175551212 (E.164 format). Twilio will also accept unformatted US numbers e.g., (415) 555-1212, 415-555-1212.
     * @param body           The text of the message you want to send, limited to 160 characters.
     * @param statusCallback A flow that Twilio will POST to when your message is processed. Twilio will POST the SmsSid as well as SmsStatus=sent or SmsStatus=failed.
     * @param applicationSid Twilio will POST SmsSid as well as SmsStatus=sent or SmsStatus=failed to the URL in the SmsStatusCallback property of this Application. If the StatusCallback parameter above is also passed, the Application's SmsStatusCallback parameter will take precedence.
     * @return the SMS message representation.
     */
    @Processor
    public String sendSmsMessage(@Optional String accountSid,
                                 String from,
                                 String to,
                                 String body,
                                 @Optional HttpCallback statusCallback,
                                 @Optional String applicationSid) {
        return twilioClient.sendSmsMessage(accountSid, from, to, body, statusCallback, applicationSid);
    }

    /**
     * Returns one of several representations depending on the given recording type.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getRecording}
     *
     * @param accountSid    optionally override globally configured Account SID
     * @param recordingSid  the recording sid to use in the query
     * @param recordingType the recording type to use
     * @return a recording representation.
     */
    @Processor
    public String getRecording(@Optional String accountSid,
                               String recordingSid,
                               RecordingType recordingType) {
        return twilioClient.getRecording(accountSid, recordingSid, recordingType);
    }

    /**
     * Deletes a recording from your account. If successful, returns HTTP 204 (No Content) with no body.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:deleteRecording}
     *
     * @param accountSid   optionally override globally configured Account SID
     * @param recordingSid the recording sid to use.
     * @return If successful, returns HTTP 204 (No Content) with no body.
     */
    @Processor
    public String deleteRecording(@Optional String accountSid, String recordingSid) {
        return twilioClient.deleteRecording(accountSid, recordingSid);

    }

    /**
     * Returns a list of Recording resource representations, each representing a recording generated during the course of a phone call. The list includes paging information.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getRecordings}
     *
     * @param accountSid  optionally override globally configured Account SID
     * @param callSid     Show only recordings made during the call given by this sid.
     * @param dateCreated Only show recordings created on the given date. Should be formatted as YYYY-MM-DD. You can also specify inequality, such as DateCreated<=YYYY-MM-DD for recordings generated at or before midnight on a date, and DateCreated>=YYYY-MM-DD for recordings generated at or after midnight on a date.
     * @return a list of Recording resource representations.
     */
    @Processor
    public String getRecordings(@Optional String accountSid,
                                @Optional String callSid,
                                @Optional String dateCreated) {
        return twilioClient.getRecordings(accountSid, callSid, dateCreated);
    }

    /**
     * Returns a single Transcription resource representation identified by the given transcription sid.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getTranscriptionByTranscriptionSid}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param transcriptionSid    the transcription sid to use in the query.
     * @param transcriptionFormat the transcription format of the response
     * @return a single Transcription resource representation identified by the given transcription sid.
     */
    @Processor
    public String getTranscriptionByTranscriptionSid(@Optional String accountSid,
                                                     String transcriptionSid,
                                                     TranscriptionFormat transcriptionFormat) {
        return twilioClient.getTranscriptionByTranscriptionSid(accountSid, transcriptionSid, transcriptionFormat);
    }

    /**
     * Returns a set of Transcription resource representations that includes paging information, sorted by 'DateUpdated', with most recent transcripts first.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getTranscriptions}
     *
     * @param accountSid          optionally override globally configured Account SID
     * @param recordingSid        the recording sid to use in the query
     * @param transcriptionFormat the transcription format for the response
     * @return a set of Transcription resource representations
     */
    @Processor
    public String getTranscriptions(@Optional String accountSid,
                                    @Optional String recordingSid,
                                    TranscriptionFormat transcriptionFormat) {
        return twilioClient.getTranscriptions(accountSid, recordingSid, transcriptionFormat);
    }

    /**
     * Return a notificaction resource for the given notification sid.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getNotification}
     *
     * @param accountSid      the account sid to use in the query
     * @param notificationSid the notification sid to use in the query
     * @return a notificaction resource for the given notification sid.
     */
    @Processor
    public String getNotification(@Optional String accountSid, String notificationSid) {
        return twilioClient.getNotification(accountSid, notificationSid);
    }

    /**
     * Deletes the notification identified by {NotificationSid} from an account's log.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:deleteNotification}
     *
     * @param accountSid      optionally override globally configured Account SID
     * @param notificationSid the notification sid to use
     * @return If successful, returns HTTP status 204 (No Content) with no body.
     */
    @Processor
    public String deleteNotification(@Optional String accountSid, String notificationSid) {
        return twilioClient.deleteNotification(accountSid, notificationSid);
    }

    /**
     * Returns a list of notifications generated for an account. The list includes paging information and is sorted by DateUpdated, with most recent notifications first.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getAllNotifications}
     *
     * @param accountSid  optionally override globally configured Account SID
     * @param log         An integer log level corresponding to the type of notification: 0 is ERROR, 1 is WARNING.
     * @param messageDate Only show notifications for this date. Should be formatted as YYYY-MM-DD. You can also specify inequality, such as MessageDate<=YYYY-MM-DD for messages logged at or before midnight on a date, and MessageDate>=YYYY-MM-DD for messages logged at or after midnight on a date.
     * @return a list of notifications generated for an account
     */
    @Processor
    public String getAllNotifications(@Optional String accountSid,
                                      @Optional Integer log,
                                      @Optional String messageDate) {
        return twilioClient.getAllNotifications(accountSid, log, messageDate);
    }

    /**
     * Returns a list of notifications generated for an account. The list includes paging information and is sorted by DateUpdated, with most recent notifications first.
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getNotificationsByCallSid}
     *
     * @param accountSid  optionally override globally configured Account SID
     * @param callSid     the call sid to use in the query
     * @param log         An integer log level corresponding to the type of notification: 0 is ERROR, 1 is WARNING.
     * @param messageDate Only show notifications for this date. Should be formatted as YYYY-MM-DD. You can also specify inequality, such as MessageDate<=YYYY-MM-DD for messages logged at or before midnight on a date, and MessageDate>=YYYY-MM-DD for messages logged at or after midnight on a date.
     * @return a list of notifications generated for an account for the given call sid.
     */
    @Processor
    public String getNotificationsByCallSid(@Optional String accountSid,
                                            String callSid,
                                            @Optional Integer log,
                                            @Optional String messageDate) {
        return twilioClient.getNotifications(accountSid, callSid, log, messageDate);
    }

    /**
     * Returns the Sandbox resource associated with the account identified by {YourAccountSid}. Twilio accounts upgraded prior to February 2010 may not have a Sandbox resource, and in this case you will receive a 404 (Not Found) response.
     * <p/>
     * The Sandbox resource gives you programatic access to your Twilio Developer Sandbox phone number. Using this resource you can get the phone number and PIN for your sandbox, view the current voice and SMS URLs and update those URLs just like any other IncomingPhoneNumber resource on a Full Twilio Account.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:getSandbox}
     *
     * @param accountSid optionally override globally configured Account SID
     * @return the Sandbox resource associated with the account identified by {YourAccountSid}. Twilio accounts upgraded prior to February 2010 may not have a Sandbox resource, and in this case you will receive a 404 (Not Found) response.
     */
    @Processor
    public String getSandbox(@Optional String accountSid) {
        return twilioClient.getSandbox(accountSid);
    }

    /**
     * You can POST to the Sandbox resource to update the TwiML voice and SMS URLs associated with the sandbox number.
     * <p/>
     * The Sandbox resource gives you programatic access to your Twilio Developer Sandbox phone number. Using this resource you can get the phone number and PIN for your sandbox, view the current voice and SMS URLs and update those URLs just like any other IncomingPhoneNumber resource on a Full Twilio Account.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/twilio-connector.xml.sample twilio:updateSandbox}
     *
     * @param accountSid  optionally override globally configured Account SID
     * @param voiceUrl    The URL that Twilio should request when somebody calls this sandbox.
     * @param voiceMethod The HTTP method that should be used to request the above URL. Must be either GET or POST. Defaults to POST.
     * @param smsUrl      The URL that Twilio should request when somebody sends an SMS to the sandbox.
     * @param smsMethod   The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.
     * @return If successful, Twilio responds with an updated representation of the sandbox.
     */
    @Processor
    public String updateSandbox(@Optional String accountSid,
                                @Optional String voiceUrl,
                                @Optional HttpMethod voiceMethod,
                                @Optional String smsUrl,
                                @Optional HttpMethod smsMethod) {
        return twilioClient.updateSandbox(accountSid, voiceUrl, voiceMethod, smsUrl, smsMethod);
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}