/**
 * Mule Twilio Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.twilio.example;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.construct.Flow;
import org.mule.tck.FunctionalTestCase;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

public class TwilioAndMongoExampleTestDriver extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "mule-config.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        // call this method the times you want to insert multiple clients
        insertClientUsingMongoConnector("the name", "the phone number", "the account balance");
        insertClientUsingMongoConnector("the name", "the phone number", "the account balance");
    }

    @Override
    protected void doTearDown() throws Exception {
        deleteAllClientsUsingMongoConnector();
    }

    public void testRetrieveAllClientsAndSendThemSms() throws Exception {
        MuleEvent muleEvent = lookupFlowConstruct("sendSmsToClients").process(getTestEvent(""));
        String twilioResponse = muleEvent.getMessage().getPayloadAsString();
        printXml(twilioResponse);
    }

    private void deleteAllClientsUsingMongoConnector() throws Exception {
        lookupFlowConstruct("deleteClients").process(getTestEvent(""));
    }

    private void insertClientUsingMongoConnector(String name, String phone, String accountBalance) throws Exception {
        MuleEvent muleEvent = getTestEvent("");
        MuleMessage message = muleEvent.getMessage();
        message.setProperty("name", name, PropertyScope.INBOUND);
        message.setProperty("phone", phone, PropertyScope.INBOUND);
        message.setProperty("accountBalance", accountBalance, PropertyScope.INBOUND);
        lookupFlowConstruct("insertClients").process(muleEvent);
    }

    private Flow lookupFlowConstruct(String name) {
        return (Flow) muleContext.getRegistry().lookupFlowConstruct(name);
    }

    private void printXml(String response) throws Exception {
        Source xmlInput = new StreamSource(new StringReader(response));
        StreamResult xmlOutput = new StreamResult(new StringWriter());
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(xmlInput, xmlOutput);
        System.out.println(xmlOutput.getWriter().toString());
    }
}