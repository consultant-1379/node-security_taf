package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.*;

/**
 * Aim of these tests is to validate IPsec Status command.
Conf 1 - LTE07ERBS00001, LTE07ERBS00003
 * Conf 2 - LTE07ERBS00002, LTE07ERBS00004
 * Disb   - LTE07ERBS00005
 * @author ehimnay
 */
public class IPsecStatusConfigCommandScenariosTest extends IPsecurityTestCase {

	private static final Logger LOGGER = Logger
			.getLogger(IPsecStatusConfigCommandScenariosTest.class);

	private final String IPSEC_STATUS_CONF_CMD = "secadm ipsec --status --configuration --nodefile "
			+ FILE_ABC_TXT;

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Single_Node")
	@Test(groups = { "Acceptance" }, testName = "IPsec status command for configuration on single node", dataProvider = "IPsec_Create_Single_Node", enabled = true, priority = 2)
	public void checkIPsecStatusForConfigurationSingleNode(
			@Input("NODE") final String node, @Input("IP") final String ip) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecStatusForConfigurationSingleNode");

		createNodes(node, ip);

		// execute ipsec status command
		String fdn = String.format(CMEDIT_MECONTEXT, node);
		Map<String, Map<String, String>> ipsecStatusResult = executeIPsecStatus(
				IPSEC_STATUS_CONF_CMD, fdn);
		String ipsecStatus = getKeyFromHashMap(ipsecStatusResult);
		LOGGER.info("IPsec Status : " + ipsecStatus);

		// get traffic IPAccessID from dps
		Set<String> trafficIPAccessIDSet = getIPAccessIDForConfiguration(
				CMEDIT_IPSEC_TRAFFIC_IPACCESSHOST_COMMAND, node, IPSEC_TRAFFIC);
		LOGGER.info("DPS Traffic Status : " + trafficIPAccessIDSet);

		// get OM IPAccessID from dps
		Set<String> omIPAccessIDSet = getIPAccessIDForConfiguration(
				CMEDIT_IPSEC_OM_IPHOSTLINK_COMMAND, node, IPSEC_OM);
		LOGGER.info("DPS OM Status : " + omIPAccessIDSet);

		// check dps IPAccessID for configuraton
		String cmeditStatus = getIPsecOMConfiguration(trafficIPAccessIDSet,
				omIPAccessIDSet);
		LOGGER.info("ipsec config status : " + cmeditStatus);

		// compare dps and ipsec result
		if (ipsecStatus.equals(cmeditStatus)) {
			assertTrue(
					"IPsec status command is validated succefully for node : "
							+ node, true);
		} else {
			assertTrue("IPsec status command is validated fails for node : "
					+ node, false);
		}

		deleteNodes(node);
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Multiple_Node")
	@Test(groups = { "Acceptance" }, testName = "IPsec status command for configuration on multiple node", dataProvider = "IPsec_Create_Multiple_Node", enabled = true, priority = 3)
	public void checkIPsecStatusForConfigurationMultipleNode(
			@Input("NODE") final String nodes, @Input("IP") final String ip) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecStatusForConfigurationMultipleNode");

		createNodes(nodes, ip);

		for (String node : nodes.split(COMMA_SEPARATOR)) {

			// execute ipsec status command
			String fdn = String.format(CMEDIT_MECONTEXT, node);
			Map<String, Map<String, String>> ipsecStatusResult = executeIPsecStatus(
					IPSEC_STATUS_CONF_CMD, fdn);
			String ipsecStatus = getKeyFromHashMap(ipsecStatusResult);
			LOGGER.info("IPsec Status : " + ipsecStatus);

			// get traffic IPAccessID from dps
			Set<String> trafficIPAccessIDSet = getIPAccessIDForConfiguration(
					CMEDIT_IPSEC_TRAFFIC_IPACCESSHOST_COMMAND, node,
					IPSEC_TRAFFIC);
			LOGGER.info("DPS Traffic Status : " + trafficIPAccessIDSet);

			// get OM IPAccessID from dps
			Set<String> omIPAccessIDSet = getIPAccessIDForConfiguration(
					CMEDIT_IPSEC_OM_IPHOSTLINK_COMMAND, node, IPSEC_OM);
			LOGGER.info("DPS OM Status : " + omIPAccessIDSet);

			// check dps IPAccessID for configuraton
			String cmeditStatus = getIPsecOMConfiguration(trafficIPAccessIDSet,
					omIPAccessIDSet);
			LOGGER.info("ipsec config status : " + cmeditStatus);

			if (ipsecStatus.equals(cmeditStatus)) {
				assertTrue("IPsec valid Status with config for node : " + node,
						true);
			} else {
				assertTrue("IPsec valid Status with config failed for node : "
						+ node, false);
			}
		}

		deleteNodes(nodes);
	}

	private String getIPsecOMConfiguration(Set<String> traffic, Set<String> OM) {
		LOGGER.info("inside IPsecStatusConfigCommandScenariosTest -> checkIPsecConfigStatus");

		if (CollectionUtils.isEmpty(traffic) || CollectionUtils.isEmpty(OM))
			return IPSEC_DEACT;
		else {
			String omStatus = IPSEC_ACT_CONF1;
			for (String status : traffic) {
				if (status.equals(IPSEC_ACT)) {
					omStatus = IPSEC_ACT;
				} else if (status.equals(IPSEC_DEACT)) {
					omStatus = IPSEC_DEACT;
				} else if (OM.contains(status)) {
					omStatus = IPSEC_ACT_CONF2;
					break;
				}
			}
			return omStatus;
		}
	}

	private String getKeyFromHashMap(
			Map<String, Map<String, String>> ipsecStatus) {
		for (Entry<String, Map<String, String>> entry : ipsecStatus.entrySet()) {
			Map<String, String> value = entry.getValue();
			for (Entry<String, String> nodeStatus : value.entrySet()) {
				return nodeStatus.getKey();
			}
		}
		return EMPTY_STRING;
	}

}