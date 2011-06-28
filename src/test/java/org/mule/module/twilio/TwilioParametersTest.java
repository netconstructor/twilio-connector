package org.mule.module.twilio;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.junit.Assert.assertTrue;

public class TwilioParametersTest {

    private static final TwilioParameters.TwilioParamater PARAMETER_A = TwilioParameters.TwilioParamater.APPLICATION_SID;
    private static final TwilioParameters.TwilioParamater PARAMETER_B = TwilioParameters.TwilioParamater.ACCOUNT_SID;
    private static final String VALUE_A = "some value";
    private static final String VALUE_B = "some other value";

    @Test
    public void testAllParamatersRequired() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParameters.TwilioParametersStategy.ALL_REQUIRED).
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
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParameters.TwilioParametersStategy.ALL_REQUIRED).
                addIfValueNotNull(PARAMETER_A, null);
        twilioParameters.getMap();
    }

    @Test
    public void testAllParametersOptionalNoneSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParameters.TwilioParametersStategy.ALL_OPTIONAL).
                addIfValueNotNull(PARAMETER_A, null);
        assertTrue(twilioParameters.getMap().isEmpty());
    }

    @Test
    public void testAtLeastOneParameterRequired() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParameters.TwilioParametersStategy.AT_LEAST_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, VALUE_A);
        assertThat(twilioParameters.getMap()).hasSize(1).includes(entry(PARAMETER_A.getCode(), VALUE_A));
    }

    @Test(expected = TwilioConnectorException.class)
    public void testAtLeastOneParameterRequiredButNoneSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParameters.TwilioParametersStategy.AT_LEAST_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, null);
        twilioParameters.getMap();
    }

    @Test
    public void testExactlyOneParameterRequired() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParameters.TwilioParametersStategy.EXACTLY_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, VALUE_A);
        assertThat(twilioParameters.getMap()).hasSize(1).includes(entry(PARAMETER_A.getCode(), VALUE_A));
    }

    @Test(expected = TwilioConnectorException.class)
    public void testExactlyOneParameterRequiredButNoneSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParameters.TwilioParametersStategy.EXACTLY_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, null);
        twilioParameters.getMap();
    }

    @Test(expected = TwilioConnectorException.class)
    public void testExactlyOneParameterRequiredButTwoSpecified() throws Exception {
        TwilioParameters twilioParameters = new TwilioParameters(TwilioParameters.TwilioParametersStategy.EXACTLY_ONE_REQUIRED).
                addIfValueNotNull(PARAMETER_A, VALUE_A).
                addIfValueNotNull(PARAMETER_B, VALUE_B);
        twilioParameters.getMap();
    }
}