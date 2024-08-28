package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import static com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil.*;
import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.*;
import com.ericsson.nms.security.nscs.taf.test.operators.ResponseDtoWrapper;

/**
 * Aim of these tests is to validate command for IPsec OM Enable.
 * Conf 1 - LTE07ERBS00001, LTE07ERBS00003
 * Conf 2 - LTE07ERBS00002, LTE07ERBS00004
 * Disb   - LTE07ERBS00005
 * unsync node - LTE07ERBS00006
 * @author ehimnay
 */
public class IPsecOMEnableCommandScenariosTest extends IPsecurityTestCase {

	private static final Logger LOGGER = Logger
			.getLogger(IPsecOMEnableCommandScenariosTest.class);

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "IPsec Role Create Node", dataProvider = "IPsec_Create_Multiple_Node", enabled = true, priority = 1)
	public void createNode(@Input("NODE") final String nodes,
			@Input("IP") final String ips) {
		createNodes(nodes, ips);
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "enable ipsec om configuration one", dataProvider = "IPsec_Create_Multiple_Node", enabled = true, priority = 2)
	public void checkIPsecOMEnableConfigurationOne(
			@Input("NODE") final String nodes) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecOMEnableConfigurationOne");

		String httpResponseBody = executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_COFIG_ONE);

		String node = nodes.split(COMMA_SEPARATOR)[0];

		if (httpResponseBody.contains(IPSEC_SUCCESS_MESSAGE)) {

			sleep(60);

			String omState = checkOMConfiguration(node);

			if (omState.equals(IPSEC_ACT_CONF1)) {
				assertTrue("IPsec with configuration one activated for node : "
						+ node, true);
			} else {
				assertTrue(
						"IPsec with configuration one activated failed for node : "
								+ node, false);
			}
		} else {
			assertTrue("IPsec command execution failed", false);
		}
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "enable ipsec om configuration two", dataProvider = "IPsec_Create_Multiple_Node", enabled = true, priority = 2)
	public void checkIPsecOMEnableConfigurationTwo(
			@Input("NODE") final String nodes) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecOMEnableConfigurationTwo");

		String httpResponseBody = executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_COFIG_TWO);

		String node = nodes.split(COMMA_SEPARATOR)[1];

		if (httpResponseBody.contains(IPSEC_SUCCESS_MESSAGE)) {

			sleep(60);

			String omState = checkOMConfiguration(node);

			if (omState.equals(IPSEC_ACT_CONF2)) {
				assertTrue("IPsec with configuration two activated for node : "
						+ node, true);
			} else {
				assertTrue(
						"IPsec with configuration two activated failed for node : "
								+ node, false);
			}
		} else {
			assertTrue("IPsec command execution failed", false);
		}
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "ipsec om enable using multiple configuration", dataProvider = "IPsec_Create_Multiple_Node", enabled = true, priority = 2)
	public void checkIPsecOMEnableMultipleConfiguration(
			@Input("NODE") final String node) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecOMEnableMultipleConfiguration");

		String httpResponseBody = executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_MULTIPLE_COFIG);

		String nodeArray[] = node.split(COMMA_SEPARATOR);

		if (httpResponseBody.contains(IPSEC_SUCCESS_MESSAGE)) {

			sleep(60);

			String omStatus = checkOMConfiguration(nodeArray[2]);

			if (omStatus.equals(IPSEC_ACT_CONF1)) {
				assertTrue("IPsec with configuration one activated for node : "
						+ nodeArray[2], true);
			} else {
				assertTrue(
						"IPsec with configuration one activated failed for node : "
								+ nodeArray[2], false);
			}

			omStatus = checkOMConfiguration(nodeArray[3]);

			if (omStatus.equals(IPSEC_ACT_CONF2)) {
				assertTrue("IPsec with configuration two activated for node : "
						+ nodeArray[3], true);
			} else {
				assertTrue(
						"IPsec with configuration two activated failed for node : "
								+ nodeArray[3], false);
			}

			String ipsecState = getIPsecState(nodeArray[4]);
			if (ipsecState.contains(IPSEC_DEACT)) {
				assertTrue("IPsec is deactive", true);
			}
			assertTrue("IPsec is still active", false);

		} else {
			assertTrue("IPsec command execution failed", false);
		}

	}

	@Context(context = { Context.REST })
	@Test(groups = { "Acceptance" }, testName = "om configuration one enable for node already in workflow", enabled = false, priority = 2)
	public void checkIPsecOMEnableConfigurationOneWhenNodeAlreadyInWorkflow() {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecOMEnableConfigurationOneWhenNodeAlreadyInWorkflow");

		executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_COFIG_ONE);

		LOGGER.info("Executing ipsec om enable for config one one more time");

		String httpResponseBody = executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_COFIG_ONE);

		if (httpResponseBody
				.contains("Secadm command already in progress for this node")) {
			assertTrue("IPsec command already in progress for this node", true);
		} else {
			assertTrue(
					"IPsec command executed on node which is already in workflow",
					false);
		}

	}

	@Context(context = { Context.REST })
	@Test(groups = { "Acceptance" }, testName = "om configuration two enable for node already in workflow", enabled = false, priority = 2)
	public void checkIPsecOMEnableConfigurationTwoWhenNodeAlreadyInWorkflow() {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecOMEnableConfigurationTwoWhenNodeAlreadyInWorkflow");

		executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_COFIG_TWO);

		sleep(2);

		LOGGER.info("Executing ipsec om enable for config two one more time");

		String httpResponseBody = executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_COFIG_TWO);

		if (httpResponseBody
				.contains("Secadm command already in progress for this node")) {
			assertTrue("IPsec command already in progress for this node", true);
		} else {
			assertTrue(
					"IPsec command executed on node which is already in workflow",
					false);
		}
	}

	@Context(context = { Context.REST })
	@Test(groups = { "Acceptance" }, testName = "om multiple configuration for node already in workflow", enabled = false, priority = 2)
	public void checkIPsecOMEnableMultipleConfigurationWhenNodeAlreadyInWorkflow() {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecOMEnableMultipleConfigurationWhenNodeAlreadyInWorkflow");

		executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_MULTIPLE_COFIG);

		sleep(2);

		LOGGER.info("Executing ipsec om enable for multiple configuration one more time");

		String httpResponseBody = executeIPsecOMEnableConfiguration(IPSEC_OM_ENABLE_MULTIPLE_COFIG);

		if (httpResponseBody
				.contains("Secadm command already in progress for this node")) {
			assertTrue("IPsec command already in progress for this node", true);
		} else {
			assertTrue(
					"IPsec command executed on node which is already in workflow",
					false);
		}
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Unsync_Node")
	@Test(groups = { "Acceptance" }, testName = "enable om on a unsync node", dataProvider = "IPsec_Unsync_Node", enabled = true, priority = 2)
	public void checkIPsecOMEnableWhenNodeIsNotSync(
			@Input("NODE") final String nodes, @Input("IP") final String ips) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecOMEnableWhenNodeIsNotSync");

		final String ENABLE_COFIG_ONE_UnSync = "OMUnSyncNodeConfigurationOne.xml";

		deleteAndAddNodeForTestSetup(nodes, ips);

		String httpResponseBody = executeIPsecOMEnableConfiguration(ENABLE_COFIG_ONE_UnSync);

		deleteNodes(nodes);

		Assert.assertEquals(httpResponseBody
				.contains("The node specified is not synchronized"), true,
				"Test Case Failure : Error message received does not match expected "
						+ "The node specified is not synchronized");
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "IPsec Delete Node", dataProvider = "IPsec_Create_Multiple_Node", enabled = true, priority = 3)
	public void cleanNode(@Input("NODE") final String node) {
		LOGGER.info("Inside IPsecCommandSyntaxPositiveScenariosTest -> cleanNode");
		deleteNodes(node);
	}

	private String executeIPsecOMEnableConfiguration(String configFile) {
		final Map<String, Object> properties = templateLoader(configFile, null);
		String command = IPSEC_OM_COMMAND + configFile;
		return executeScriptEngineCommand(command, properties);
	}

	private String getIPsecState(String node) {
		String ipInterfaceCommandString = String.format(
				CMEDIT_IPSEC_STATUS_COMMAND, node);
		String httpResponse = getScriptEngineOperator().runCommand(
				ipInterfaceCommandString).getBody();
		String ipsecfeatureState = ResponseDtoWrapper.newResponseDtoWrapper(
				httpResponse)
				.fetchSingleAttributeValueForAttributeNameFromResponse(
						"featureState");
		return StringUtils.isNotBlank(ipsecfeatureState) ? (ipsecfeatureState
				.equalsIgnoreCase(IPSEC_ACT) ? IPSEC_ACT : IPSEC_DEACT)
				: IPSEC_UNKNOWN;
	}

	private String checkOMConfiguration(String node) {
		Set<String> trafficResult = getIPAccessIDForConfiguration(
				CMEDIT_IPSEC_TRAFFIC_IPACCESSHOST_COMMAND, node, IPSEC_TRAFFIC);

		LOGGER.info("DPS Traffic Status : " + trafficResult);

		Set<String> omResult = getIPAccessIDForConfiguration(
				CMEDIT_IPSEC_OM_IPHOSTLINK_COMMAND, node, IPSEC_OM);

		LOGGER.info("DPS OM Status : " + omResult);

		if (CollectionUtils.isEmpty(trafficResult)
				|| CollectionUtils.isEmpty(omResult)) {
			LOGGER.info("Traffic or OM is empty");
			return IPSEC_DEACT;
		} else {
			for (String status : trafficResult) {
				if (omResult.contains(status)) {
					return IPSEC_ACT_CONF2;
				}
			}
		}
		return IPSEC_ACT_CONF1;

	}

}