package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;
import static com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil.*;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.*;

/**
 * Aim of these tests is to check different roles for executing IPsec command.
 * 
 * @author ehimnay
 */
public class IPsecCommandRoles extends IPsecurityTestCase {

	private static final Logger LOGGER = Logger
			.getLogger(IPsecCommandRoles.class);

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Single_Node")
	@Test(groups = { "Acceptance" }, testName = "create node", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 1)
	public void createNode(@Input("NODE") final String node,
			@Input("IP") final String ip) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> createNode");
		createNodes(node, ip);
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Command_RoleCheck")
	@Test(groups = { "Acceptance" }, testName = "check operator role", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 2)
	public void checkOperatorRole(@Input("COMMAND") final String command,
			@Input("FDN") final String fdn) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkOperatorRole");

		String httpResponseBody = checkAuthorizeRole(OPERATOR, fdn, command);
		if (httpResponseBody.contains(ACCESS_VIOLATION_ERROR_MESSAGE)) {
			assertTrue("Operator Role check success for ipsec command : "
					+ command, true);
		} else {
			assertTrue("Operator Role check failed for ipsec command : "
					+ command, false);
		}
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Command_RoleCheck")
	@Test(groups = { "Acceptance" }, testName = "check field technician role", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 2)
	public void checkFieldTechnicianRole(
			@Input("COMMAND") final String command,
			@Input("FDN") final String fdn) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkFieldTechnicianRole");
		String httpResponseBody = checkAuthorizeRole(FIELD_TECHNICIAN, fdn,
				command);
		if (httpResponseBody.contains(ACCESS_VIOLATION_ERROR_MESSAGE)) {
			assertTrue(
					"Field Technician Role check success for ipsec command : "
							+ command, true);
		} else {
			assertTrue(
					"Field Technician Role check failed for ipsec command : "
							+ command, false);
		}
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Command_RoleCheck")
	@Test(groups = { "Acceptance" }, testName = "create authorize role", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 2)
	public void checkSecurityAdminRole(@Input("COMMAND") final String command,
			@Input("FDN") final String fdn) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkSecurityAdminRole");

		String httpResponseBody = checkAuthorizeRole(SECURITY_ADMIN, fdn,
				command);
		if (httpResponseBody.contains(IPSEC_COMMAND_SUCCESS_EXECUTED)) {
			assertTrue("Admin Role check success for ipsec command : "
					+ command, true);
		} else {
			assertTrue(
					"Admin Role check failed for ipsec command : " + command,
					false);
		}

	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Single_Node")
	@Test(groups = { "Acceptance" }, testName = "IPsec Delete Node", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 3)
	public void cleanNode(@Input("NODE") final String node) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> deleteNode");
		deleteNodes(node);
	}

	private String checkAuthorizeRole(String role, String fdn, String command) {
		getLoginOperator().assignRoleThatUserShouldHaveOnCreation(role);

		String httpResponseBody = command.endsWith(".txt") ? getSecurityCommandsOperator()
				.executeIPSecStatusCommand(command, fdn)
				: getSecurityCommandsOperator().executeIPSecCommand(command,
						getFileName(command));

		getLoginOperator().deleteUser();
		return httpResponseBody;

	}

}