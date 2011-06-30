package org.mule.module.twilio;

import org.junit.Test;
import org.mule.module.twilio.TwilioParameters.TwilioParamater;
import org.mule.module.twilio.TwilioParameters.TwilioParametersStategy;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.junit.Assert.assertTrue;

public class TwilioParametersTest {

    private static final TwilioParamater PARAMETER_A = TwilioParamater.APPLICATION_SID;
    private static final TwilioParamater PARAMETER_B = TwilioParamater.ACCOUNT_SID;
    private static final String VALUE_A = "some value";
    private static final String VALUE_B = "some other value";

    @Test
    public void testAllParamatersRequired() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(PARAMETER_A, VALUE_A).
                addIfValueNotNull(PARAMETER_B, VALUE_B);
        assertThat(twilioParameters.getMap()).
                hasSize(2).
                includes(
                        entry(PARAMETER_A.getCode(), VALUE_A),
                        entry(PARAMETER_B.getCode(), VALUE_B));
    }

    @Test(expected = TwilioConnectorException.class)
    public void testAllParamatersRequiredButNoneSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(PARAMETER_A, null);
        twilioParameters.getMap();
    }

    @Test
    public void testAllParametersOptionalNoneSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(PARAMETER_A, null);
        assertTrue(twilioParameters.getMap().isEmpty());
    }

    @Test
    public void testAtLeastOneParameterRequired() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.AT_LEAST_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, VALUE_A);
        assertThat(twilioParameters.getMap()).hasSize(1).includes(entry(PARAMETER_A.getCode(), VALUE_A));
    }

    @Test(expected = TwilioConnectorException.class)
    public void testAtLeastOneParameterRequiredButNoneSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.AT_LEAST_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, null);
        twilioParameters.getMap();
    }

    @Test
    public void testExactlyOneParameterRequired() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.EXACTLY_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, VALUE_A);
        assertThat(twilioParameters.getMap()).hasSize(1).includes(entry(PARAMETER_A.getCode(), VALUE_A));
    }

    @Test(expected = TwilioConnectorException.class)
    public void testExactlyOneParameterRequiredButNoneSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.EXACTLY_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, null);
        twilioParameters.getMap();
    }

    @Test(expected = TwilioConnectorException.class)
    public void testExactlyOneParameterRequiredButTwoSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParametersStategy.EXACTLY_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, VALUE_A).
                addIfValueNotNull(PARAMETER_B, VALUE_B);
        twilioParameters.getMap();
    }
}