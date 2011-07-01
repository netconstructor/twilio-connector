Twilio Send Sms to clients retrieved using Mongo connector Demo
===============================================================

INTRODUCTION
   This demo shows how to send SMS messages to a set of phone numbers retrieved
   from a Mongo collection.


HOW TO DEMO:
  In order to run this demo it is necessary to have mongodb running locally and an internet connection
  to access Twilio's API.
  1. Fill in your src/main/resources/config.properties file with you Twilio account details.
  2. Open src/test/java/org/mule/module/twilio/example/TwilioAndMongoExampleTestDriver and go to the method doSetUp()
   to fill clients details to be inserted into a Mongo collection
  3. Run the TwilioAndMongoExampleTestDriver

HOW IT WORKS:
   The sequence of the demo is as follows:
   1. Some customers are inserted into a Mongo collection (name, phone number, text message)
   2. These customers are retrieved from the Mongo collection and a SMS is sent to them using
   the data in the collection (name, phone number, account balance)