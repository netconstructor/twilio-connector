Twilio Send Sms to clients retrieved using Mongo connector Demo
===============================================================

INTRODUCTION
   This demo shows how to send SMS messages to a set of phone numbers retrieved
   from a Mongo collection.


HOW TO DEMO:
  In order to run this demo it is necessary to have mongodb running locally and an internet connection
  to access Twilio's API.
  1. Run the TwilioAndMongoExampleTestDriver, or deploy this demo an a Mule Container
   a. Some customers are inserted into Mongo by default, but if you wish to add more you can by querying
      http://localhost:9090/insert-clients?name=some_name&phone=some_phone&accountBalance=some_number

HOW IT WORKS:
   The sequence of the demo is as follows:
   1. Some customers are inserted into a Mongo collection (name, phone number, text message)
   2. These customers are retrieved from the Mongo collection and a SMS is sent to them using
   the data in the collection (name, phone number, account balance)