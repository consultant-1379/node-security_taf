package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.*;

/**
 * Aim of these tests is to validate IPsec Status config command.
 * Node - LTE07ERBS00001, LTE07ERBS00002,LTE07ERBS00003,LTE07ERBS00004,LTE07ERBS00005
 * @author ehimnay
 */
public class IPsecStatusCommandScenariosTest extends IPsecurityTestCase {

	private static final Logger LOGGER = Logger
			.getLogger(IPsecStatusCommandScenariosTest.class);

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Single_Node")
	@Test(groups = { "Acceptance" }, testName = "ipsec status command validation on a single node", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 2)
	public void checkIPsecStatusSingleNode(@Input("NODE") final String node,
			@Input("FDN") final String fdn, @Input("IP") final String ip) {
		LOGGER.info("inside IPsecStatusCommandScenariosTest -> checkIPsecStatusSingleNode");

		createNodes(node, ip);

		// execute ipsec status command
		Map<String, Map<String, String>> ipsecStatus = executeIPsecStatus(
                IPSEC_STATUS_COMMAND + FILE_ABC_TXT, fdn);
		LOGGER.info("IPsec Status : " + ipsecStatus);

		// get om status
		String omStatus = checkTrafficOMActive(
				CMEDIT_IPSEC_OM_IPHOSTLINK_COMMAND, node, IPSEC_OM);
		LOGGER.info("DPS OM Status : " + omStatus);

		// get traffic status
		String trafficStatus = checkTrafficOMActive(
				CMEDIT_IPSEC_TRAFFIC_IPACCESSHOST_COMMAND, node, IPSEC_TRAFFIC);
		LOGGER.info("DPS Traffic Status : " + trafficStatus);

		// compare dps and ipsec status result
		Map<String, String> status = new HashMap<>();
		status.put(omStatus, trafficStatus);
		Map<String, Map<String, String>> cmeditStatus = new HashMap<>();
		cmeditStatus.put(node, status);
		if (ipsecStatus.equals(cmeditStatus)) {
			assertTrue(
					"IPsec status command for single node check is successfull",
					true);
		} else {
			assertTrue("IPsec status command for single node check failed",
					false);
		}

		deleteNodes(node);
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "ipsec status command validation on a multiple node", dataProvider = "IPsec_Create_Multiple_Node", enabled = true, priority = 2)
	public void checkIPsecStatusMultipleNode(@Input("NODE") final String nodes,
			@Input("FDN") final String fdn, @Input("IP") final String ips) {
		LOGGER.info("inside IPsecStatusCommandScenariosTest -> checkIPsecStatusMultipleNode");

		Map<String, Map<String, String>> cmeditStatus = new HashMap<>();
		createNodes(nodes, ips);

		// execute ipsec status command
		Map<String, Map<String, String>> ipsecStatus = executeIPsecStatus(
                IPSEC_STATUS_COMMAND + FILE_ABC_TXT, fdn);
		LOGGER.info("IPsec Status : " + ipsecStatus);

		for (String node : nodes.split(COMMA_SEPARATOR)) {

			// get om status
			String omStatus = checkTrafficOMActive(
					CMEDIT_IPSEC_OM_IPHOSTLINK_COMMAND, node, IPSEC_OM);
			LOGGER.info("DPS OM Status : " + omStatus);

			// get traffic status
			String trafficStatus = checkTrafficOMActive(
					CMEDIT_IPSEC_TRAFFIC_IPACCESSHOST_COMMAND, node,
					IPSEC_TRAFFIC);
			LOGGER.info("DPS Traffic Status : " + trafficStatus);

			Map<String, String> status = new HashMap<>();
			status.put(omStatus, trafficStatus);
			cmeditStatus.put(node, status);
		}

		// compare dps and ipsec status result
		if (ipsecStatus.equals(cmeditStatus)) {
			assertTrue(
					"IPsec status command check for multiple node is successfull",
					true);
		} else {
			assertTrue("IPsec status command check for multiple node failed",
					false);
		}

		deleteNodes(nodes);

	}
}