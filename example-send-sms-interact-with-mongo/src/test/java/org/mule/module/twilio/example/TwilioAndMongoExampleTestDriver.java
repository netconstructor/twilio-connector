package org.mule.module.twilio.example;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.construct.SimpleFlowConstruct;
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
        insertClientUsingMongoConnector("Federico", "(415)599-2671", "74303727 1234.59"); // last String is prefixed with a PIN number b/c this is mandatory to send SMSs to free accounts
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

    private SimpleFlowConstruct lookupFlowConstruct(String name) {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
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