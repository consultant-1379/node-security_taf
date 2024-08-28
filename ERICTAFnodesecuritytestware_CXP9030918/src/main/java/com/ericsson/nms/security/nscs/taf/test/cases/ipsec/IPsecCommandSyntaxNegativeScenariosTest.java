package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import static com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil.*;
import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.*;

/**
 * Aim of these tests is to validate syntax of all IPsec command.
 *
 * @author ehimnay
 */
public class IPsecCommandSyntaxNegativeScenariosTest extends IPsecurityTestCase {

    private static final Logger LOGGER = Logger
            .getLogger(IPsecCommandSyntaxNegativeScenariosTest.class);

    private String IPSEC_STATUS_CMD = "secadm ipsec --status --nodefile " + FILE_ABC_TXT;

    @Context(context = {Context.REST})
    @DataDriven(name = "IPsec_Create_Multiple_Node")
    @Test(groups = {"Acceptance"}, testName = "IPsec Status Create Node", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 1)
    public void createNode(@Input("NODE") final String node,
                           @Input("IP") final String ip) {
        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> createNode");
        createNodes(node, ip);
    }

    @Context(context = {Context.REST})
    @DataDriven(name = "IPsec_Validate_OM_Command_Syntax_Negative")
    @Test(groups = {"Acceptance"}, testName = "ipsec OM command syntax validation", dataProvider = "IPsec_Validate_OM_Command_Syntax_Negative", enabled = true, priority = 2)
    public void checkIPsecOMCommandSyntaxNegative(
            @Input("COMMAND") final String commandString,
            @Input("EXPECTEDMESSAGE") final String expectedMessage) {

        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> checkIPsecOMCommandSyntaxNegative");
        String fileName = commandString.endsWith(IPSEC_OM_ENABLE_COFIG_ONE) ? IPSEC_OM_ENABLE_COFIG_ONE
                : (commandString.endsWith(IPSEC_OM_ENABLE_COFIG_TWO) ? IPSEC_OM_ENABLE_COFIG_TWO
                : (commandString.endsWith(IPSEC_OM_DISABLE_COFIG) ? IPSEC_OM_DISABLE_COFIG
                : EMPTY_STRING));

        final Map<String, Object> properties = templateLoader(fileName, null);
        String httpResponseBody = commandString.endsWith(".xml") ? executeScriptEngineCommand(
                commandString, properties) : executeScriptEngineCommand(commandString);

        assertTrue(IPSEC_COMMAND_SUCCESS_MSG,
                httpResponseBody
                        .contains(expectedMessage));
    }

    @Context(context = {Context.REST})
    @DataDriven(name = "IPsec_Validate_Status_Command_Syntax_Negative")
    @Test(groups = {"Acceptance"}, testName = "ipsec status command syntax validation", dataProvider = "IPsec_Validate_Status_Command_Syntax_Negative", enabled = true, priority = 2)
    public void checkIPsecStatusCommandSyntaxNegative(
            @Input("COMMAND") final String commandString,
            @Input("FDN") final String fdn,
            @Input("EXPECTEDMESSAGE") final String expectedMessage) {

        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> checkIPsecStatusCommandSyntaxNegative");
        byte[] fileByte = createBytesFromString(fdn);
        String httpResponseBody = commandString.endsWith(".txt") ? executeScriptEngineCommandWithFile(
                commandString, FILE_ABC_TXT, fileByte)
                : executeScriptEngineCommand(commandString);

        assertTrue(IPSEC_COMMAND_SUCCESS_MSG,
                httpResponseBody
                        .contains(expectedMessage));
    }

    @Context(context = {Context.REST})
    @DataDriven(name = "IPsec_Status_Dup_Node")
    @Test(groups = {"Acceptance"}, testName = "ipsec status command dup node", dataProvider = "IPsec_Status_Dup_Node", enabled = true, priority = 2)
    public void checkIPsecStatusCommandDupNodeNegative(
            @Input("FDN") final String fdn) {

        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> checkIPsecStatusCommandDupNodeNegative");
        byte[] fileByte = createBytesFromString(fdn);
        String httpResponseBody = executeScriptEngineCommandWithFile(
                IPSEC_STATUS_CMD, FILE_ABC_TXT, fileByte);

        assertTrue(IPSEC_COMMAND_SUCCESS_MSG,
                httpResponseBody
                        .contains("The list of nodes specified contains duplicates"));
    }

    @Context(context = {Context.REST})
    @Test(groups = {"Acceptance"}, testName = "ipsec status command invalid node", enabled = true, priority = 2)
    public void checkIPsecStatusCommandInvalidNodeNegative() {

        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> checkIPsecStatusCommandInvalidNodeNegative");
        String invalidNode = String.format(CMEDIT_MECONTEXT, "Ericsson");
        byte[] fileByte = createBytesFromString(invalidNode);
        String httpResponseBody = executeScriptEngineCommandWithFile(
                IPSEC_STATUS_CMD, FILE_ABC_TXT, fileByte);

        assertTrue(IPSEC_COMMAND_SUCCESS_MSG,
                httpResponseBody
                        .contains(IPSEC_INVALID_NODE_MESSAGE));
    }

    @Context(context = {Context.REST})
    @Test(groups = {"Acceptance"}, testName = "ipsec status command empty file", enabled = true, priority = 2)
    public void checkIPsecStatusCommandEmptyFileNegative() {

        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> checkIPsecStatusCommandEmptyFileNegative.");
        byte[] fileData = createBytesFromString(EMPTY_STRING);
        String httpResponseBody = executeScriptEngineCommandWithFile(
                IPSEC_STATUS_CMD, FILE_ABC_TXT, fileData);

        assertTrue(IPSEC_COMMAND_SUCCESS_MSG,
                httpResponseBody
                        .contains("Suggested Solution : Please see the Online Help for the correct format of the contents of the file"));
    }

    @Context(context = {Context.REST})
    @DataDriven(name = "IPsec_Create_Multiple_Node")
    @Test(groups = {"Acceptance"}, testName = "ipsec status command when node file has invalid delimiter", dataProvider = "IPsec_Create_Multiple_Node", enabled = true, priority = 2)
    public void checkIPsecStatusCommandNodesFileWithInvalidDataDelimitersComma(
            @Input("NODE") final String node) {
        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> checkIPsecStatusCommandNodesFileWithInvalidDataDelimitersComma");

        String fdn = String.format(CMEDIT_MECONTEXT, node);
        byte[] fileData = createBytesFromString(fdn.replace(";", COMMA_SEPARATOR));
        String httpResponseBody = executeScriptEngineCommandWithFile(
                IPSEC_STATUS_CMD, FILE_ABC_TXT, fileData);

        assertTrue(IPSEC_COMMAND_SUCCESS_MSG,
                httpResponseBody
                        .contains("Error 10002 : The contents of the file provided are not in the correct format"));

    }

    @Context(context = {Context.REST})
    @DataDriven(name = "IPsec_Unsync_Node")
    @Test(groups = {"Acceptance"}, testName = "ipsec status command when node is not sync", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 3)
    public void checkIPsecStatusCommandWhenNodeIsNotSynch(
            @Input("NODE") final String node, @Input("IP") final String ip) {
        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> checkIPsecStatusCommandWhenNodeIsNotSynch");

        deleteAndAddNodeForTestSetup(node, ip);
        String fdn = String.format(CMEDIT_MECONTEXT, node);
        byte[] fileData = createBytesFromString(fdn);
        String httpResponseBody = executeScriptEngineCommandWithFile(
                IPSEC_STATUS_CMD, FILE_ABC_TXT, fileData);

        Assert.assertEquals(httpResponseBody
                .contains("The node specified is not synchronized"), true,
                "Test Case Failure : Error message received does not match expected "
                        + "The node specified is not synchronized");

    }

    @Context(context = {Context.REST})
    @DataDriven(name = "IPsec_Create_Multiple_Node")
    @Test(groups = {"Acceptance"}, testName = "IPsec Delete Node", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 4)
    public void cleanNode(@Input("NODE") final String node) {
        LOGGER.info("Inside IPsecCommandSyntaxNegativeScenariosTest -> cleanNode");
        deleteNodes(node);
    }

}