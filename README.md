Mule Twilio Cloud Connector
===========================

Mule Cloud connector to Twilio

Installation
------------

The connector can either be installed for all applications running within the Mule instance or can be setup to be used
for a single application.

*All Applications*

Download the connector from the link above and place the resulting jar file in
/lib/user directory of the Mule installation folder.

*Single Application*

To make the connector available only to single application then place it in the
lib directory of the application otherwise if using Maven to compile and deploy
your application the following can be done:

Add the connector's maven repo to your pom.xml:

    <repositories>
        <repository>
            <id>muleforge-releases</id>
            <name>MuleForge Snapshot Repository</name>
            <url>http://repository.mulesoft.org/releases/</url>
            <layout>default</layout>
        </repsitory>
    </repositories>

Add the connector as a dependency to your project. This can be done by adding
the following under the dependencies element in the pom.xml file of the
application:

    <dependency>
        <groupId>org.mule.modules</groupId>
        <artifactId>mule-module-twilio</artifactId>
        <version>1.1-SNAPSHOT</version>
    </dependency>

Configuration
-------------

You can configure the connector as follows:

    <twilio:config accountSid="value" authToken="value"/>

Here is detailed list of all the configuration attributes:

| attribute | description | optional | default value |
|:-----------|:-----------|:---------|:--------------|
|name|Give a name to this configuration so it can be later referenced by config-ref.|yes||
|accountSid|The account sid to be used to connect to Twilio.|no|
|authToken|The authentication token to be used to connect to Twilio|no|




Get Account Details
-------------------

Returns a representation of an account.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid for which to get the details, leave empty to use to use {@link TwilioConnector#accountSid}|yes||

Returns representation of the account



Get All Accounts Details
------------------------

Retrieve a list of the Account resources belonging to the account used to make the API request. This list will
include that Account as well.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountStatus|Only return Account resources with the given status. Can be closed, suspended or active.|yes||*SUSPENDED*, *ACTIVE*, *CLOSED*
|friendlyName|Only return the Account resources with friendly names that exactly match this name.|yes||

Returns list of the Account resources belonging to the account used to make the API request. This list will include that Account as well.



Update Account
--------------

Allows you to modify the properties of an account.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|accountStatus|Alter the status of this account: use closed to irreversibly close this account, suspended to temporarily suspend it, or active to reactivate it.|yes||*SUSPENDED*, *ACTIVE*, *CLOSED*
|friendlyName|Update the human-readable description of this account.|yes||

Returns representation of the account



Create Sub Account
------------------

Creates a new subaccount.

Subaccounts in Twilio are just accounts that are "owned" by another account. Using a subaccount you can segment
each of your customers' use of Twilio and keep it separate from all the rest, allowing you to easily manage the
activity and resources of each customer independently.

For instance, if you are running a hosted service that relies on Twilio you can create a Twilio subaccount for
each customer that signs up. Then if a customer closes his or her account with your service, you can simply
deactivate the associated Twilio subaccount.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|friendlyName|A human readable description of the new subaccount, up to 64 characters. Defaults to "SubAccount Created at {YYYY-MM-DD HH:MM meridiam}".|yes||

Returns representation of the account



Get Sub Account By Account Sid
------------------------------

Subaccounts in Twilio are just accounts that are "owned" by another account. Using a subaccount you can segment
each of your customers' use of Twilio and keep it separate from all the rest, allowing you to easily manage the
activity and resources of each customer independently.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use in the query|no||

Returns representation of the subaccount



Get Sub Account By Friendly Name
--------------------------------

Subaccounts in Twilio are just accounts that are "owned" by another account. Using a subaccount you can segment
each of your customers' use of Twilio and keep it separate from all the rest, allowing you to easily manage the
activity and resources of each customer independently.

For instance, if you are running a hosted service that relies on Twilio you can create a Twilio subaccount for
each customer that signs up. Then if a customer closes his or her account with your service, you can simply
deactivate the associated Twilio subaccount.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|friendlyName|the friendly name to use in the query|no||

Returns representation of the subaccount



Exchange Phone Numbers Between Subaccounts
------------------------------------------

Trasfers a number between two subaccounts.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSidFrom|the account sid from where to get the number, leave empty to use {@link TwilioConnector#accountSid}|yes||
|incomingPhoneNumberSid|the incoming phone number sid to transfer|no||
|accountSidTo|the account sid where to transfer the phone number.|no||

Returns incoming phone number instance.



Get Available Phone Numbers
---------------------------

Returns a list of local AvailablePhoneNumber resource representations that match the specified filters, each representing a phone number that is currently available for provisioning within your account.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|isoCountryCode|a country code in ISO 3166-1 alpha-2 format (e.g. 'US' for United States, 'CA' for Canada).|no||
|areaCode|Find phone numbers in the specified Area Code. Only available for North American numbers.|yes||
|contains|A pattern to match phone numbers on. Valid characters are '*' and [0-9a-zA-Z]. The '*' character will match any single digit.|yes||
|inRegion|Limit results to a particular region (i.e. State/Province). Given a phone number, search within the same Region as that number.|yes||
|inPostalCode|Limit results to a particular postal code. Given a phone number, search within the same postal code as that number.|yes||

Returns list of local AvailablePhoneNumber resource representations that match the specified filters



Get Available Phone Numbers Advanced Seach
------------------------------------------

Returns a list of local AvailablePhoneNumber resource representations that match the specified filters, each representing a phone number that is currently available for provisioning within your account.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|isoCountryCode|a country code in ISO 3166-1 alpha-2 format (e.g. 'US' for United States, 'CA' for Canada).|no||
|areaCode|Find phone numbers in the specified Area Code. Only available for North American numbers.|yes||
|contains|A pattern to match phone numbers on. Valid characters are '*' and [0-9a-zA-Z]. The '*' character will match any single digit.|yes||
|inRegion|Limit results to a particular region (i.e. State/Province). Given a phone number, search within the same Region as that number.|yes||
|inPostalCode|Limit results to a particular postal code. Given a phone number, search within the same postal code as that number.|yes||
|nearLatLong|Given a latitude/longitude pair lat,long find geographically close numbers within Distance miles.|yes||
|nearPhoneNumber|Given a phone number, find a geographically close number within Distance miles. Distance defaults to 25 miles.|yes||
|inLata|Limit results to a specific Local access and transport area (LATA). Given a phone number, search within the same LATA as that number.|yes||
|inRateCenter|Limit results to a specific rate center, or given a phone number search within the same rate center as that number. Requires InLata to be set as well.|yes||
|distance|Specifies the search radius for a Near- query in miles. If not specified this defaults to 25 miles.|yes||

Returns list of local AvailablePhoneNumber resource representations that match the specified filters



Get Available Toll Free Numbers
-------------------------------

Returns a list of toll-free AvailablePhoneNumber elements that match the specified filters, each representing a phone number that is currently available for provisioning within your account.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|isoCountryCode|a country code in ISO 3166-1 alpha-2 format (e.g. 'US' for United States, 'CA' for Canada).|no||
|contains|A pattern to match phone numbers on. Valid characters are '*' and [0-9a-zA-Z]. The '*' character will match any single digit.|yes||

Returns list of toll-free AvailablePhoneNumber elements that match the specified filters



Get Outgoing Caller Id By Outgoing Caller Id Sid
------------------------------------------------

Returns an outgoing caller id instance matching the given filters.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|outgoingCallerIdSid|the outgoing caller id sid to use in the query|no||

Returns outgoing caller id instance matching the given filters.



Update Outgoing Caller Id By Outgoing Caller Id Sid
---------------------------------------------------

Updates the caller id, and returns the updated resource if successful.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|outgoingCallerIdSid|the outgoing caller id sid to update|no||
|friendlyName|A human readable description of a Caller ID, with maximum length of 64 characters. Defaults to a nicely formatted version of the phone number.|no||

Returns the updated resource if successful.



Get All Outgoing Caller Ids
---------------------------

Returns a list of OutgoingCallerId resource representations, each representing a Caller ID number valid for an account. The list includes paging information.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|phoneNumber|Only show the caller id resource that exactly matches this phone number.|yes||
|friendlyName|Only show the caller id resource that exactly matches this name.|yes||

Returns list of OutgoingCallerId resource representations



Add New Caller Id
-----------------

Adds a new CallerID to your account. After making this request, Twilio will return to you a validation code and Twilio will dial the phone number given to perform validation. The code returned must be entered via the phone before the CallerID will be added to your account.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|phoneNumber|The phone number to verify. Should be formatted with a '+' and country code e.g., +16175551212 (E.164 format). Twilio will also accept unformatted US numbers e.g., (415) 555-1212, 415-555-1212.|no||
|friendlyName|A human readable description for the new caller ID with maximum length 64 characters. Defaults to a nicely formatted version of the number.|yes||
|callDelay|The number of seconds, between 0 and 60, to delay before initiating the validation call. Defaults to 0.|yes||
|extension|Digits to dial after connecting the validation call.|yes||

Returns validation request (see validation request propertie)



Delete Outgoing Caller Id
-------------------------

Deletes the caller ID from the account. Returns an HTTP 204 response if successful, with no body.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|outgoingCallerIdSid|the outgoing caller id sid to delete|no||

Returns HTTP 204 response if successful, with no body.



Get Incoming Phone Numbers By Incoming Phone Number Sid
-------------------------------------------------------

Returns an incoming phone numbers matching the given query filters.

An IncomingPhoneNumber instance resource represents a Twilio phone number purchased from Twilio or ported to Twilio.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|incomingPhoneNumberSid|the incoming phone number sid to use in the query|no||

Returns incoming phone number matching the given incoming phone number sid



Update Incoming Phone Numbers
-----------------------------

Tries to update the incoming phone number's properties, and returns the updated resource representation if successful.

* An IncomingPhoneNumber instance resource represents a Twilio phone number purchased from Twilio or ported to Twilio.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|incomingPhoneNumberSid|the incoming phone number sid to use|no||
|friendlyName|A human readable description of the new incoming phone number resource, with maximum length 64 characters.|yes||
|apiVersion|Calls to this phone number will start a new TwiML session with this API version. Either 2010-04-01 or 2008-08-01.|yes||
|voiceUrl|The URL that Twilio should request when somebody dials the phone number.|yes||
|voiceMethod|The HTTP method that should be used to request the VoiceUrl. Either GET or POST.|yes||*GET*, *POST*
|voiceFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML defined by VoiceUrl.|yes||
|voiceFallbackMethod|The HTTP method that should be used to request the VoiceFallbackUrl. Either GET or POST.|yes||*GET*, *POST*
|statusCallback|The URL that Twilio will request to pass status parameters (such as call ended) to your application|yes||
|statusCallbackMethod|The HTTP method Twilio will use to make requests to the StatusCallback URL. Either GET or POST.|yes||*GET*, *POST*
|voiceCallerIdLookup|Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false.|yes||
|voiceApplicationSid|The 34 character sid of the application Twilio should use to handle phone calls to this number. If a VoiceApplicationSid is present, Twilio will ignore all of the voice urls above and use those set on the application instead.|yes||
|smsUrl|The URL that Twilio should request when somebody sends an SMS to the new phone number.|yes||
|smsMethod|The HTTP method that should be used to request the SmsUrl. Either GET or POST.|yes||*GET*, *POST*
|smsFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.|yes||
|smsFallbackMethod|The HTTP method that should be used to request the SmsFallbackUrl. Either GET or POST.|yes||*GET*, *POST*
|smsApplicationSid|The 34 character sid of the application Twilio should use to handle SMSs sent to this number. If a SmsApplicationSid is present, Twilio will ignore all of the SMS urls above and use those set on the application instead.|yes||
|accountSidDestination|The unique 34 character id of the account to which you wish to transfer this phone number.|yes||

Returns successful the updated incoming phone number instance.



Delete Incoming Phone Number
----------------------------

Release this phone number from your account. Twilio will no longer answer calls to this number, and you will stop being billed the monthly phone number fee. The phone number will eventually be recycled and potentially given to another customer, so use with care.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|incomingPhoneNumberSid|the incoming phone number sid to delete|no||

Returns succesful, returns an HTTP 204 response with no body.



Get Incoming Phone Numbers
--------------------------

Returns a list of IncomingPhoneNumber resource representations, each representing a phone number given to your account. The list includes paging information.

An IncomingPhoneNumber instance resource represents a Twilio phone number purchased from Twilio or ported to Twilio.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|phoneNumber|Only show the incoming phone number resources that match this pattern. You can specify partial numbers and use '*' as a wildcard for any digit.|yes||
|friendlyName|Only show the incoming phone number resources with friendly names that exactly match this name.|yes||

Returns list of IncomingPhoneNumber resource representations



Add Incoming Phone Number By Phone Number
-----------------------------------------

Adds a new phone number to your account. If a phone number is found for your request, Twilio will add it to your account and bill you for the first month's cost of the phone number. If Twilio can't find a phone number to match your request, you will receive an HTTP 400 with Twilio error code 21452.
If successful, Twilio responds with a representation of the new phone number that was assigned to your account.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|phoneNumber|The phone number you want to purchase. The number should be formated starting with a '+' followed by the country code and the number in E.164 format e.g., '+15105555555'.|no||
|friendlyName|A human readable description of the new incoming phone number. Maximum 64 characters. Defaults to a formatted version of the number.|yes||
|voiceUrl|The URL that Twilio should request when somebody dials the new phone number.|yes||
|voiceMethod|The HTTP method that should be used to request the VoiceUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|voiceFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML at Url.|yes||
|voiceFallbackMethod|The HTTP method that should be used to request the VoiceFallbackUrl. Either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|statusCallback|The URL that Twilio will request to pass status parameters (such as call ended) to your application.|yes||
|statusCallbackMethod|The HTTP method Twilio will use to make requests to the StatusCallback URL. Either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|voiceCallerIdLookup|Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false. Defaults to false.|yes||
|voiceApplicationSid|The 34 character sid of the application Twilio should use to handle phone calls to the new number. If a VoiceApplicationSid is present, Twilio will ignore all of the voice urls above and use those set on the application.|yes||
|smsUrl|The URL that Twilio should request when somebody sends an SMS to the phone number.|yes||
|smsMethod|The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|smsFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.|yes||
|smsFallbackMethod|The HTTP method that should be used to request the SmsFallbackUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|smsApplicationSid|The 34 character sid of the application Twilio should use to handle SMSs sent to the new number. If a SmsApplicationSid is present, Twilio will ignore all of the SMS urls above and use those set on the application.|yes||

Returns successful, Twilio responds with a representation of the new phone number that was assigned to your account.



Add Incoming Phone Number By Area Code
--------------------------------------

Adds a new phone number to your account. If a phone number is found for your request, Twilio will add it to your account and bill you for the first month's cost of the phone number. If Twilio can't find a phone number to match your request, you will receive an HTTP 400 with Twilio error code 21452.
If successful, Twilio responds with a representation of the new phone number that was assigned to your account.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|areaCode|The area code in which you'd like a new incoming phone number. Any three digit, US area code is valid. Twilio will provision a random phone number within this area code for you.|no||
|friendlyName|A human readable description of the new incoming phone number. Maximum 64 characters. Defaults to a formatted version of the number.|yes||
|voiceUrl|The URL that Twilio should request when somebody dials the new phone number.|yes||
|voiceMethod|The HTTP method that should be used to request the VoiceUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|voiceFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML at Url.|yes||
|voiceFallbackMethod|The HTTP method that should be used to request the VoiceFallbackUrl. Either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|statusCallback|The URL that Twilio will request to pass status parameters (such as call ended) to your application.|yes||
|statusCallbackMethod|The HTTP method Twilio will use to make requests to the StatusCallback URL. Either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|voiceCallerIdLookup|Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false. Defaults to false.|yes||
|voiceApplicationSid|The 34 character sid of the application Twilio should use to handle phone calls to the new number. If a VoiceApplicationSid is present, Twilio will ignore all of the voice urls above and use those set on the application.|yes||
|smsUrl|The URL that Twilio should request when somebody sends an SMS to the phone number.|yes||
|smsMethod|The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|smsFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.|yes||
|smsFallbackMethod|The HTTP method that should be used to request the SmsFallbackUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|smsApplicationSid|The 34 character sid of the application Twilio should use to handle SMSs sent to the new number. If a SmsApplicationSid is present, Twilio will ignore all of the SMS urls above and use those set on the application.|yes||

Returns successful, Twilio responds with a representation of the new phone number that was assigned to your account.



Get Application
---------------

Returns an application resource matching the given application sid.

An Application instance resource represents an application that you have created with Twilio. An application inside of Twilio is just a set of URLs and other configuration data that tells Twilio how to behave when one of your Twilio numbers receives a call or SMS message.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|applicationSid|the application sid to use in the query|no||

Returns application resource matching the given application sid.



Update Application
------------------

Tries to update the application's properties, and returns the updated resource representation if successful.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|applicationSid|the application sid to update|no||
|friendlyName|A human readable description of the application, with maximum length 64 characters.|yes||
|apiVersion|Requests to this application's URLs will start a new TwiML session with this API version. Either 2010-04-01 or 2008-08-01.|yes||
|voiceUrl|The URL that Twilio should request when somebody dials a phone number assigned to this application.|yes||
|voiceMethod|The HTTP method that should be used to request the VoiceUrl. Either GET or POST.|yes||*GET*, *POST*
|voiceFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML defined by VoiceUrl.|yes||
|voiceFallbackMethod|The HTTP method that should be used to request the VoiceFallbackUrl. Either GET or POST.|yes||*GET*, *POST*
|statusCallback|The URL that Twilio will request to pass status parameters (such as call ended) to your application.|yes||
|statusCallbackMethod|The HTTP method Twilio will use to make requests to the StatusCallback URL. Either GET or POST.|yes||*GET*, *POST*
|voiceCallerIdLookup|Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false.|yes||
|smsUrl|The URL that Twilio should request when somebody sends an SMS to a phone number assigned to this application.|yes||
|smsMethod|The HTTP method that should be used to request the SmsUrl. Either GET or POST.|yes||*GET*, *POST*
|smsFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.|yes||
|smsFallbackMethod|The HTTP method that should be used to request the SmsFallbackUrl. Either GET or POST.|yes||*GET*, *POST*
|smsStatusCallback|Twilio will make a POST request to this URL to pass status parameters (such as sent or failed) to your application if you specify this application's Sid as the ApplicationSid on an outgoing SMS request.|yes||

Returns the updated resource representation if successful.



Delete Application
------------------

Delete this application. If this application's sid is assigned to any IncomingPhoneNumber resources as a VoiceApplicationSid or SmsApplicationSid it will be removed.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|applicationSid|the application sid to use|no||

Returns succesful, Twilio will return an HTTP 204 response with no body.



Get All Applications
--------------------

Returns a list of Application resource representations, each representing an application within your account. The list includes paging information.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|friendlyName|Only return the Application resources with friendly names that exactly match this name.|yes||

Returns list of Application resource representations



Create Application
------------------

Creates a new application within your account. If successful, Twilio responds with a representation of the new application.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|friendlyName|A human readable description of the new application. Maximum 64 characters.|no||
|apiVersion|Requests to this application's URLs will start a new TwiML session with this API version. Either 2010-04-01 or 2008-08-01. Defaults to your account's default API version.|yes||
|voiceUrl|The URL that Twilio should request when somebody dials a phone number assigned to this application|yes||
|voiceMethod|The HTTP method that should be used to request the VoiceUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|voiceFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML at Url.|yes||
|voiceFallbackMethod|The HTTP method that should be used to request the VoiceFallbackUrl. Either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|statusCallback|The URL that Twilio will request to pass status parameters (such as call ended) to your application.|yes||
|statusCallbackMethod|The HTTP method Twilio will use to make requests to the StatusCallback URL. Either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|voiceCallerIdLookup|Do a lookup of a caller's name from the CNAM database and post it to your app. Either true or false. Defaults to false.|yes||
|smsUrl|The URL that Twilio should request when somebody sends an SMS to a phone number assigned to this application.|yes||
|smsMethod|The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|smsFallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML defined by SmsUrl.|yes||
|smsFallbackMethod|The HTTP method that should be used to request the SmsFallbackUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|smsStatusCallback|Twilio will make a POST request to this URL to pass status parameters (such as sent or failed) to your application if you specify this application's Sid as the ApplicationSid on an outgoing SMS request.|yes||

Returns successful, Twilio responds with a representation of the new application.



Get Call
--------

Returns the single Call resource identified by the given call sid.

A Call instance resource represents a connection between a telephone and Twilio. This may be inbound, when a person calls your application, or outbound, when your application initiates the call, either via the REST API (see Making Calls) or during a call via the TwiML <Dial> verb. The Calls list resource represents the set of phone calls made to and from an account.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|callSid|the call sid to use in the query|no||

Returns single Call resource identified by the given call sid.



Get Calls
---------

Returns a list of phone calls made to and from the account identified by the given account sid. The list includes paging information and is sorted by DateUpdated with most-recent calls first.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|to|Only show calls to this phone number.|yes||
|from|Only show calls from this phone number.|yes||
|status|Only show calls currently in this status. May be queued, ringing, in-progress, completed, failed, busy, or no-answer.|yes||
|startTime|Only show calls that started on this date, given as YYYY-MM-DD. Also supports inequalities, such as StartTime<=YYYY-MM-DD for calls that started at or before midnight on a date, and StartTime>=YYYY-MM-DD for calls that started at or after midnight on a date.|yes||

Returns list of phone calls made to and from the account identified by the given account sid



Make Call
---------

Initiates a call using the given paramaters. Only one of url or applicationSid parameters must be specified, not both. Returns the call representation.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|from|The phone number to use as the caller id. Format with a '+' and country code e.g., +16175551212 (E.164 format). Must be a Twilio number or a valid outgoing caller id for your account.|no||
|to|The number to call formatted with a '+' and country code e.g., +16175551212 (E.164 format). Twilio will also accept unformatted US numbers e.g., (415) 555-1212, 415-555-1212.|no||
|url|The fully qualified URL that should be consulted when the call connects. Just like when you set a URL on a phone number for handling inbound calls.|yes||
|applicationSid|The 34 character sid of the application Twilio should use to handle this phone call. If this parameter is present, Twilio will ignore all of the voice URLs passed and use the URLs set on the application.|yes||
|method|The HTTP method Twilio should use when making its request to the above Url parameter's value. Defaults to POST. If an ApplicationSid parameter is present, this parameter is ignored.|yes||
|fallbackUrl|A URL that Twilio will request if an error occurs requesting or executing the TwiML at Url. If an ApplicationSid parameter is present, this parameter is ignored.|yes||
|fallbackMethod|The HTTP method that Twilio should use to request the FallbackUrl. Must be either GET or POST. Defaults to POST. If an ApplicationSid parameter is present, this parameter is ignored.|yes||*GET*, *POST*
|statusCallback|A URL that Twilio will request when the call ends to notify your app. If an ApplicationSid parameter is present, this parameter is ignored.|yes||
|statusCallbackMethod|The HTTP method Twilio should use when requesting the above URL. Defaults to POST. If an ApplicationSid parameter is present, this parameter is ignored.|yes||*GET*, *POST*
|sendDigits|A string of keys to dial after connecting to the number. Valid digits in the string include: any digit (0-9), '#' and '*'. For example, if you connected to a company phone number, and wanted to dial extension 1234 and then the pound key, use SendDigits=1234#. Remember to URL-encode this string, since the '#' character has special meaning in a URL.|yes||
|ifMachine|Tell Twilio to try and determine if a machine (like voicemail) or a human has answered the call. Possible values are Continue and Hangup.|yes||
|timeout|The integer number of seconds that Twilio should allow the phone to ring before assuming there is no answer. Default is 60 seconds, the maximum is 999 seconds. Note, you could set this to a low value, such as 15, to hangup before reaching an answering machine or voicemail.|yes||

Returns call representation.



Change Call State
-----------------

Modify the state of a live call. Returns the call representation.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|callSid|the call sid to modify|no||
|url|A valid URL that returns TwiML. Twilio will immediately redirect the call to the new TwiML.|yes||
|method|The HTTP method Twilio should use when requesting the above URL. Defaults to POST.|yes||*GET*, *POST*
|status|Either canceled or completed. Specifying canceled will attempt to hangup calls that are queued or ringing but not affect calls already in progress. Specifying completed will attempt to hang up a call even if it's already in progress.|yes||

Returns call representation



Get Conference
--------------

Returns a representation of the conference identified by the given conference id.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|conferenceSid|the conference sid to use in the query|no||

Returns representation of the conference identified by the given conference id.



Get Conferences
---------------

Returns a list of conferences within an account. The list includes paging information and is sorted by DateUpdated, with most recent conferences first.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|status|Only show conferences currently in with this status. May be init, in-progress, or completed.|yes||
|friendlyName|List conferences who's FriendlyName is the exact match of this string.|yes||
|dateCreated|Only show conferences that started on this date, given as YYYY-MM-DD. You can also specify inequality, such as DateCreated<=YYYY-MM-DD for conferences that started at or before midnight on a date, and DateCreated>=YYYY-MM-DD for conferences that started at or after midnight on a date.|yes||
|dateUpdated|Only show conferences that were last updated on this date, given as YYYY-MM-DD. You can also specify inequality, such as DateUpdated<=YYYY-MM-DD for conferences that were last updated at or before midnight on a date, and DateUpdated>=YYYY-MM-DD for conferences that were updated at or after midnight on a date.|yes||

Returns list of conferences within an account



Get Participant
---------------

Returns a representation of this participant.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|conferenceSid|the conference sid to use in the query|no||
|callSid|the call sid to use in the query|no||

Returns representation of this participant.



Update Participant Status
-------------------------

Updates the participant status. Returns the participant representation.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|conferenceSid|the conference sid to use|no||
|callSid|the call sid to use|no||
|muted|Specifying true will mute the participant, while false will un-mute.|no||

Returns participant representation



Delete Participant
------------------

Kick this participant from the conference. Returns HTTP 204 (No Content), with no body, if the participant was successfuly booted from the conference.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|conferenceSid|the conference sid to use|no||
|callSid|the call sid to use|no||

Returns HTTP 204 (No Content), with no body, if the participant was successfuly booted from the conference.



Get Participants
----------------

Returns the list of participants in the conference identified by the given conference sid.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|conferenceSid|the conference sid to use in the query|no||
|muted|Only show participants that are muted or unmuted. Either true or false.|yes||

Returns list of participants in the conference identified by the given conference sid.



Get Sms Message
---------------

Returns a single SMS message specified by the provided SMS message sid.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|smsMessageSid|the SMS message sid to use in the query.|no||

Returns single SMS message specified by the provided SMS message sid.



Get All Sms Messages
--------------------

Returns a list of SMS messages associated with your account. The list includes paging information and is sorted by DateSent, with most recent messages first.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|to|Only show SMS messages to this phone number.|yes||
|from|Only show SMS messages from this phone number.|yes||
|dateSent|Only show SMS messages sent on this date, given as YYYY-MM-DD. Example: DateSent=2009-07-06. You can also specify inequality, such as DateSent<=YYYY-MM-DD for SMS messages that were sent on or before midnight on a date, and DateSent>=YYYY-MM-DD for SMS messages sent on or after midnight on a date.|yes||

Returns list of SMS messages associated with your account



Send Sms Message
----------------

Send an outgoing SMS message. By specifying an SMS URL for your SMS enabled Twilio phone number, Twilio will make a request to your application to notify you when someone replies to a message you send.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|from|A Twilio number enabled for SMS. Only phone numbers purchased from Twilio work here; you cannot (for example) spoof SMS messages from your own cell phone number.|no||
|to|The destination phone number. Format with a '+' and country code e.g., +16175551212 (E.164 format). Twilio will also accept unformatted US numbers e.g., (415) 555-1212, 415-555-1212.|no||
|body|The text of the message you want to send, limited to 160 characters.|no||
|statusCallback|A URL that Twilio will POST to when your message is processed. Twilio will POST the SmsSid as well as SmsStatus=sent or SmsStatus=failed.|yes||
|applicationSid|Twilio will POST SmsSid as well as SmsStatus=sent or SmsStatus=failed to the URL in the SmsStatusCallback property of this Application. If the StatusCallback parameter above is also passed, the Application's SmsStatusCallback parameter will take precedence.|yes||

Returns SMS message representation.



Get Recording
-------------

Returns one of several representations depending on the given recording type.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|recordingSid|the recording sid to use in the query|no||
|recordingType|the recording type to use|no||*WAV*, *MP3*, *XML*, *extension*

Returns recording representation.



Delete Recording
----------------

Deletes a recording from your account. If successful, returns HTTP 204 (No Content) with no body.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|recordingSid|the recording sid to use.|no||

Returns successful, returns HTTP 204 (No Content) with no body.



Get Recordings
--------------

Returns a list of Recording resource representations, each representing a recording generated during the course of a phone call. The list includes paging information.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|callSid|Show only recordings made during the call given by this sid.|yes||
|dateCreated|Only show recordings created on the given date. Should be formatted as YYYY-MM-DD. You can also specify inequality, such as DateCreated<=YYYY-MM-DD for recordings generated at or before midnight on a date, and DateCreated>=YYYY-MM-DD for recordings generated at or after midnight on a date.|yes||

Returns list of Recording resource representations.



Get Transcription By Transcription Sid
--------------------------------------

Returns a single Transcription resource representation identified by the given transcription sid.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|transcriptionSid|the transcription sid to use in the query.|no||
|transcriptionFormat|the transcription format of the response|no||*XML*, *TXT*, *extension*

Returns single Transcription resource representation identified by the given transcription sid.



Get Transcriptions
------------------

Returns a set of Transcription resource representations that includes paging information, sorted by 'DateUpdated', with most recent transcripts first.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|recordingSid|the recording sid to use in the query|yes||
|transcriptionFormat|the transcription format for the response|no||*XML*, *TXT*, *extension*

Returns set of Transcription resource representations



Get Notification
----------------

Return a notificaction resource for the given notification sid.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use in the query|yes||
|notificationSid|the notification sid to use in the query|no||

Returns notificaction resource for the given notification sid.



Delete Notification
-------------------

Deletes the notification identified by {NotificationSid} from an account's log.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|notificationSid|the notification sid to use|no||

Returns successful, returns HTTP status 204 (No Content) with no body.



Get All Notifications
---------------------

Returns a list of notifications generated for an account. The list includes paging information and is sorted by DateUpdated, with most recent notifications first.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|log|An integer log level corresponding to the type of notification: 0 is ERROR, 1 is WARNING.|yes||
|messageDate|Only show notifications for this date. Should be formatted as YYYY-MM-DD. You can also specify inequality, such as MessageDate<=YYYY-MM-DD for messages logged at or before midnight on a date, and MessageDate>=YYYY-MM-DD for messages logged at or after midnight on a date.|yes||

Returns list of notifications generated for an account



Get Notifications By Call Sid
-----------------------------

Returns a list of notifications generated for an account. The list includes paging information and is sorted by DateUpdated, with most recent notifications first.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|callSid|the call sid to use in the query|no||
|log|An integer log level corresponding to the type of notification: 0 is ERROR, 1 is WARNING.|yes||
|messageDate|Only show notifications for this date. Should be formatted as YYYY-MM-DD. You can also specify inequality, such as MessageDate<=YYYY-MM-DD for messages logged at or before midnight on a date, and MessageDate>=YYYY-MM-DD for messages logged at or after midnight on a date.|yes||

Returns list of notifications generated for an account for the given call sid.



Get Sandbox
-----------

Returns the Sandbox resource associated with the account identified by {YourAccountSid}. Twilio accounts upgraded prior to February 2010 may not have a Sandbox resource, and in this case you will receive a 404 (Not Found) response.

The Sandbox resource gives you programatic access to your Twilio Developer Sandbox phone number. Using this resource you can get the phone number and PIN for your sandbox, view the current voice and SMS URLs and update those URLs just like any other IncomingPhoneNumber resource on a Full Twilio Account.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||

Returns Sandbox resource associated with the account identified by {YourAccountSid}. Twilio accounts upgraded prior to February 2010 may not have a Sandbox resource, and in this case you will receive a 404 (Not Found) response.



Update Sandbox
--------------

You can POST to the Sandbox resource to update the TwiML voice and SMS URLs associated with the sandbox number.

The Sandbox resource gives you programatic access to your Twilio Developer Sandbox phone number. Using this resource you can get the phone number and PIN for your sandbox, view the current voice and SMS URLs and update those URLs just like any other IncomingPhoneNumber resource on a Full Twilio Account.


| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|accountSid|the account sid to use, leave empty to use {@link TwilioConnector#accountSid}|yes||
|voiceUrl|The URL that Twilio should request when somebody calls this sandbox.|yes||
|voiceMethod|The HTTP method that should be used to request the above URL. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*
|smsUrl|The URL that Twilio should request when somebody sends an SMS to the sandbox.|yes||
|smsMethod|The HTTP method that should be used to request the SmsUrl. Must be either GET or POST. Defaults to POST.|yes||*GET*, *POST*

Returns successful, Twilio responds with an updated representation of the sandbox.



































