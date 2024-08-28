package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import static com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil.*;
import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.*;

/**
 * Aim of these tests is to validate syntax of all IPsec command.
 * 
 * @author ehimnay
 */
public class IPsecCommandSyntaxPositiveScenariosTest extends IPsecurityTestCase {

	private static final Logger LOGGER = Logger
			.getLogger(IPsecCommandSyntaxPositiveScenariosTest.class);

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "IPsec Status Create Node", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 1)
	public void createNode(@Input("NODE") final String node,
			@Input("IP") final String ip) {
		LOGGER.info("Inside IPsecCommandSyntaxPositiveScenariosTest -> createNode");
		createNodes(node, ip);
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Validate_OM_Command_Syntax_Positive")
	@Test(groups = { "Acceptance" }, testName = "ipsec OM command syntax validation - Positive", dataProvider = "IPsec_Validate_OM_Command_Syntax_Positive", enabled = true, priority = 2)
	public void checkIPsecOMCommandSyntaxPositive(
			@Input("COMMAND") final String commandString,
			@Input("EXPECTEDMESSAGE") final String expectedMessage) {
		LOGGER.info("Inside IPsecCommandSyntaxPositiveScenariosTest -> checkIPsecOMCommandSyntaxPositive");

		String fileName = getFileName(commandString);
		final Map<String, Object> properties = templateLoader(fileName, null);
		String httpResponseBody = executeScriptEngineCommand(
				commandString, properties);

		assertTrue(IPSEC_COMMAND_SUCCESS_MSG,
				httpResponseBody.contains(expectedMessage));
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Validate_Status_Command_Syntax_Positive")
	@Test(groups = { "Acceptance" }, testName = "ipsec status command syntax validation - Positive", dataProvider = "IPsec_Validate_Status_Command_Syntax_Positive", enabled = true, priority = 2)
	public void checkIPsecStatusCommandSyntaxPositive(
			@Input("COMMAND") final String commandString,
			@Input("FDN") final String fdn,
			@Input("EXPECTEDMESSAGE") final String expectedMessage) {
		LOGGER.info("Inside IPsecCommandSyntaxPositiveScenariosTest -> checkIPsecStatusCommandSyntaxPositive");

		byte[] fileData = createBytesFromString(fdn);
		String httpResponseBody = executeScriptEngineCommandWithFile(
				commandString, FILE_ABC_TXT, fileData);

		assertTrue(IPSEC_COMMAND_SUCCESS_MSG,
				httpResponseBody.contains(expectedMessage));
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "IPsec Delete Node", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 3)
	public void cleanNode(@Input("NODE") final String node) {
		LOGGER.info("Inside IPsecCommandSyntaxPositiveScenariosTest -> cleanNode");
		deleteNodes(node);
	}
}