package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.operators.ResponseDtoWrapper;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;

import static com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil.*;
import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

/**
 * Functional tests for IPsec Disable OM workflow
 *
 * @author edotser
 */

public class IPsecOMDisableCommandScenariosTest extends NodeSecurityTestCaseHelper {

    private static final Logger LOGGER = Logger
        .getLogger(IPsecOMDisableCommandScenariosTest.class);

    private static final String IPSEC_EXECUTION_SUCCESS_MESSAGE = "IPSec activation/deactivation change initiated";
    private static final String NODE_DOESNT_HAVE_IPSEC_MO = "doesn't have valid IpSecMO";
    private static final String NODE_IS_NOT_SYNCHRONIZED = "is not synchronized";
    private static final String NODE_IS_ALREADY_IN_WORKFLOW = "is already in workflow";
    private static final String FEATURE_STATE_ATTRIBUTE = "featureState";
    private static final String UNEXPECTED_HTTP_RESP_BODY_MESSAGE = "Unexpected httpResponseBody";
    private static final String HTTP_RESPONSE_BODY = "httpResponseBody is: ";
    private static final String DEACTIVATED_FEATURE_STATE_MESSAGE = "IPSec FeatureState is DEACTIVATED as expected";
    private static final String FEATURE_STATE_MESSAGE = "IPSec FeatureState is: %s";
    private static final String DELAY_MESSAGE = "[DELAY] 60 seconds...";

    @Context(context = { Context.REST })
    @DataDriven(name = "IPsec_OM_Disable")
    @Test(
        testName = "IPSec OM Disable Prerequisite Step",
        enabled = true, alwaysRun = true)
    public void checkIPsecOMDisablePrerequisiteStep(
        @Input("nodeName") final String nodeName,
        @Input("ipAddress") final String ipAddress,
        @Input("isNodeSynchonized") final boolean isNodeSynchonized) {

        if (!nodeName.contains(SEMICOLON_SEPARATOR)) {
            deleteAndAddNodeForTestSetup(nodeName, ipAddress);
            if (isNodeSynchonized) {
                synchroniseNodeForTestSetup(nodeName);
            }
        } else {
            final String[] nodeArray = nodeName.split(SEMICOLON_SEPARATOR);
            final String[] ipArray = ipAddress.split(SEMICOLON_SEPARATOR);
            if (nodeArray.length == ipArray.length) {
                for (int i = 0; i < nodeArray.length; i++) {
                    deleteAndAddNodeForTestSetup(nodeArray[i], ipArray[i]);
                    if (isNodeSynchonized) {
                        synchroniseNodeForTestSetup(nodeName);
                    }
                }
            } else {
                final String ErrorMessage = "Node and ipAddress fields count mismatch";
                LOGGER.info(ErrorMessage);
                assertTrue(ErrorMessage, false);
            }
        }
    }

    @Context(context = { Context.REST })
    @DataDriven(name = "IPsec_OM_Disable",
        filter = "testCaseName =='checkIPsecOMDisableCommandScenariosTest'")
    @Test(
        testName = "IPSec OM Disable Functional Test",
        enabled = true, priority = 1,
        dependsOnMethods = "checkIPsecOMDisablePrerequisiteStep")
    public void checkIPsecOMDisableCommandScenariosTest(
        @Input("nodeName") final String nodeName,
        @Input("xmlFileName") final String xmlFileName) {

        final String httpResponseBody = executeIPSecCommand(xmlFileName);
        if (!httpResponseBody.contains(IPSEC_EXECUTION_SUCCESS_MESSAGE)) {
            assertTrue(UNEXPECTED_HTTP_RESP_BODY_MESSAGE, false);
            LOGGER.info(HTTP_RESPONSE_BODY + httpResponseBody);
        } else {
            final boolean result = isFeatureStateDeactivated(nodeName);
            if (result) {
                assertTrue(DEACTIVATED_FEATURE_STATE_MESSAGE, true);
            } else {
                String logMessage = String
                    .format(FEATURE_STATE_MESSAGE, getFeatureStateValue(nodeName));
                assertTrue(logMessage, false);
                LOGGER.info(logMessage);
            }
        }
    }

    @Context(context = { Context.REST })
    @DataDriven(name = "IPsec_OM_Disable",
        filter = "testCaseName =='checkIPsecOMDisableRegardingNodeWithoutIPsecMO'")
    @Test(testName = "IPSec OM Disable regarding the node without IPSec MO",
        enabled = true, priority = 2,
        dependsOnMethods = "checkIPsecOMDisablePrerequisiteStep")
    public void checkIPsecOMDisableRegardingNodeWithoutIPsecMO(
        @Input("xmlFileName") final String xmlFileName) {

        final String httpResponseBody = executeIPSecCommand(xmlFileName);
        if (httpResponseBody.contains(NODE_DOESNT_HAVE_IPSEC_MO)) {
            assertTrue("Node doesn't have valid IPSecMO.", true);
        } else {
            assertTrue(UNEXPECTED_HTTP_RESP_BODY_MESSAGE, false);
            LOGGER.info(HTTP_RESPONSE_BODY + httpResponseBody);
        }
    }

    @Context(context = { Context.REST })
    @DataDriven(name = "IPsec_OM_Disable",
        filter = "testCaseName =='checkIPsecOMDisableRegardingUnsynchronizedNode'")
    @Test(testName = "IPSec OM Disable regarding unsynchronized node",
        enabled = true, priority = 3,
        dependsOnMethods = "checkIPsecOMDisablePrerequisiteStep")
    public void checkIPsecOMDisableRegardingUnsynchronizedNode(
        @Input("xmlFileName") final String xmlFileName) {

        final String httpResponseBody = executeIPSecCommand(xmlFileName);
        if (httpResponseBody.contains(NODE_IS_NOT_SYNCHRONIZED)) {
            assertTrue("Node is not synchronized", true);
        } else {
            assertTrue(UNEXPECTED_HTTP_RESP_BODY_MESSAGE, false);
            LOGGER.info(HTTP_RESPONSE_BODY + httpResponseBody);
        }
    }

    @Context(context = { Context.REST })
    @DataDriven(name = "IPsec_OM_Disable",
        filter = "testCaseName =='checkIPsecOMDisableRegardingSameNodeTwice'")
    @Test(testName = "IPSec OM Disable twice execution regarding the same node",
        enabled = true, priority = 3,
        dependsOnMethods = "checkIPsecOMDisablePrerequisiteStep")
    public void checkIPsecOMDisableRegardingSameNodeTwice(
        @Input("nodeName") final String nodeName,
        @Input("xmlFileName") final String xmlFileName) {

        String httpResponseBody = executeIPSecCommand(xmlFileName);
        if (httpResponseBody.contains(IPSEC_EXECUTION_SUCCESS_MESSAGE)) {
            httpResponseBody = executeIPSecCommand(xmlFileName);
            if (httpResponseBody.contains(NODE_IS_ALREADY_IN_WORKFLOW)) {
                final boolean result = isFeatureStateDeactivated(nodeName);
                if (result) {
                    assertTrue(DEACTIVATED_FEATURE_STATE_MESSAGE, true);
                } else {
                    final String logMessage = String
                        .format(FEATURE_STATE_MESSAGE, getFeatureStateValue(nodeName));
                    assertTrue(logMessage, false);
                    LOGGER.info(logMessage);
                }
            } else {
                assertTrue(UNEXPECTED_HTTP_RESP_BODY_MESSAGE, false);
                LOGGER.info(HTTP_RESPONSE_BODY + httpResponseBody);
            }

        } else {
            assertTrue(UNEXPECTED_HTTP_RESP_BODY_MESSAGE, false);
            LOGGER.info(HTTP_RESPONSE_BODY + httpResponseBody);
        }
    }

    private String executeIPSecCommand(final String xmlFileName) {
        final String executionCommand = IPSEC_OM_COMMAND + xmlFileName;
        final File xmlFile = inputStreamToFile(
        		getStreamFromAbsoluteFilePath(IPSEC_DATA_FILE_PATH + xmlFileName), xmlFileName);
        return getScriptEngineOperator().runCommand(executionCommand, xmlFile, xmlFileName)
            .getBody();
    }

    private String getFeatureStateValue(final String nodeName) {
        final String getFeatureState = String.format(CMEDIT_IPSEC_STATUS_COMMAND, nodeName);
        final String httpResponseBody = getScriptEngineOperator().runCommand(getFeatureState)
            .getBody();
        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper
            .newResponseDtoWrapper(httpResponseBody);
        return responseDtoWrapper.fetchSingleAttributeValueForAttributeNameFromResponse(
            FEATURE_STATE_ATTRIBUTE);
    }

    private boolean isFeatureStateDeactivated(final String nodeName) {
        LOGGER.info(DELAY_MESSAGE);
        delay(60000);
        boolean result = true;
        String actualFeatureState;
        if (!nodeName.contains(SEMICOLON_SEPARATOR)) {
            actualFeatureState = getFeatureStateValue(nodeName);
            if (!actualFeatureState.equals(IPSEC_DEACT)) {
                result = false;
            }
        }
        else {
            final String[] nodeArray = nodeName.split(SEMICOLON_SEPARATOR);
            for (String node : nodeArray) {
                actualFeatureState = getFeatureStateValue(node);
                if (!actualFeatureState.equals(IPSEC_DEACT)) {
                    result = false;
                }
            }
        }
        return result;
    }
}