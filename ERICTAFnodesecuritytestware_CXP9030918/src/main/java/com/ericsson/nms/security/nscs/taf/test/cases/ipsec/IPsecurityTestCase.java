/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.ResponseDtoWrapper;
import com.ericsson.nms.security.nscs.taf.test.operators.ScriptEngineOperator;
import com.ericsson.oss.services.scriptengine.spi.dtos.Command;

public class IPsecurityTestCase extends NodeSecurityTestCaseHelper {

	private static final Logger LOGGER = Logger
			.getLogger(IPsecurityTestCase.class);

	/**
	 * create multiple node mo
	 * 
	 * @param string nodes
	 * @param string ips
	 */
	protected void createNodes(final String nodes, final String ips) {
		LOGGER.info("Nodes : " + nodes + " IP : " + ips);
		String nodeArray[] = nodes.split(COMMA_SEPARATOR);
		String ipArray[] = ips.split(COMMA_SEPARATOR);
		int i = 0;
		for (String node : nodeArray) {
			deleteAndAddAndSynchroniseNodeForTestSetup(node, ipArray[i++]);
		}
	}

	/**
	 * check if ipsec is active for om or traffic
	 * 
	 * @param string fdn
	 * @param string node
	 * @param string type - om or traffic
	 * @return ACTIVATED, DEACTIVATED or UNKNOWN
	 */
	protected String checkTrafficOMActive(String fdnQuery, String node,
			String type) {

		// get IpHostLink or IpAccessHostEt FDN
		Set<String> fdns = getIPsecFDN(fdnQuery);
		String status = IPSEC_DEACT;

		for (String fdn : fdns) {

			// check ipHostLink MO
			if (fdn.contains(node)) {

				String ipInterfaceMoRef = getIPInterfaceMO(CMEDIT_GET + fdn);

				// check ipInterfaceMoRef
				if (StringUtils.isNotBlank(ipInterfaceMoRef)) {

					// get vpnInterfaceMO
					String vpnInterfaceMO = String.format(
							CMEDIT_IPSEC_QUERY_COMMAND, node, ipInterfaceMoRef);

					// get vpnInterfaceID from MO
					String vpnInterfaceID = getVPNInterfaceID(vpnInterfaceMO);

					// check vpnInterfaceID
					if (isVPNPointTrafficOM(vpnInterfaceID, type)) {
						return IPSEC_ACT;
					}
				} else {
					status = IPSEC_UNKNOWN;
				}
			}
		}
		return status;
	}

	/**
	 * returns a list of IPAccessID if and only they are configuration 1 or 2
	 * 
	 * @param string fdn
	 * @param string node
	 * @param string type - om or traffic
	 * @return set of IPAccessID
	 */
	protected Set<String> getIPAccessIDForConfiguration(String fdnQuery,
			String node, String type) {
		Set<String> ipsecConfiguration = new HashSet<>();

		// get IpHostLink or IpAccessHostEt FDN
		Set<String> nodeFDN = getIPsecFDN(fdnQuery);
		for (String fdn : nodeFDN) {

			if (fdn.contains(node)) {

				String ipInterfaceMoRef = getIPInterfaceMO(CMEDIT_GET + fdn);

				// check ipInterfaceMoRef
				if (StringUtils.isNotBlank(ipInterfaceMoRef)) {

					// get vpnInterfaceMO
					String vpnInterfaceMOQuery = String.format(
							CMEDIT_IPSEC_QUERY_COMMAND, node, ipInterfaceMoRef);

					// get vpnInterfaceID from MO
					String vpnInterfaceID = getVPNInterfaceID(vpnInterfaceMOQuery);

					// check vpnInterfaceID
					if (isVPNPointTrafficOM(vpnInterfaceID, type)) {

						// get ipInterfaceMoRef and IpAccessHostEtId from
						// vpnInterfaceMO MO
						String ipAccessHostID = getIPAccessHostID(node,
								vpnInterfaceMOQuery);
						LOGGER.info("ipAccessHostID : " + ipAccessHostID);
						if (StringUtils.isNotBlank(ipAccessHostID))
							ipsecConfiguration.add(ipAccessHostID);
					}
				}
			}
		}
		return ipsecConfiguration;
	}

	/**
	 * execute ipsec status
	 * 
	 * @param string command
	 * @param string fileCommand
	 * @return map contains the node status ie. key - node, value - om status, traffic status
	 */
	protected Map<String, Map<String, String>> executeIPsecStatus(
			String command, String fileCommand) {
		String httpResponseBody = getSecurityCommandsOperator()
				.executeIPSecStatusCommand(command, fileCommand);
		return ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody)
				.getNodeNamesAndStatus();
	}

	/**
	 * delete multiple nodes
	 * 
	 * @param string nodes
	 */
	protected void deleteNodes(final String nodes) {
		String nodeArray[] = nodes.split(COMMA_SEPARATOR);
		for (String node : nodeArray) {
			deleteNode(node);
		}
	}
	
	/**
	 * execute ipsec status command
	 * 
	 * @param string commandstring
	 * @return httpResponseBody
	 */
	protected String executeScriptEngineCommand(final String commandString) {
        ScriptEngineOperator scriptEngineOperator = getScriptEngineOperator();
        final HttpResponse httpResponse = scriptEngineOperator.runCommand(commandString);
        return httpResponse != null ? httpResponse.getBody() : NscsServiceGetter.EMPTY_STRING;
    }

	/**
	 * execute ipsec om command
	 * 
	 * @param string commandstring
	 * @return httpResponseBody
	 */
	protected String executeScriptEngineCommand(final String command,
                                                        final Map<String, Object> properties) {
        ScriptEngineOperator scriptEngineOperator = getScriptEngineOperator();
        final Command secCommand = new Command(null, command,
                properties);
        return scriptEngineOperator
                .executeWithFile(secCommand);
    }

	private boolean isVPNPointTrafficOM(String vpnInterfaceID, String type) {
		boolean vpnStatus = false;
		if (StringUtils.isNotBlank(vpnInterfaceID)) {
			if (type.equals(IPSEC_TRAFFIC)
					&& vpnInterfaceID.equals(IPSEC_TRAFFIC_ACTIVE)) {
				vpnStatus = true;
			} else if (type.equals(IPSEC_OM)
					&& vpnInterfaceID.equals(IPSEC_OM_ACTIVE)) {
				vpnStatus = true;
			}
		}
		return vpnStatus;
	}

	private Set<String> getIPsecFDN(String fdnQuery) {
		return ResponseDtoWrapper.newResponseDtoWrapper(
				executeScriptEngineCommand(fdnQuery))
				.fetchFdnOfMosFromResponseFromCmeditMoQuery();
	}

	private String getVPNInterfaceID(String fdn) {
		return ResponseDtoWrapper.newResponseDtoWrapper(
				executeScriptEngineCommand(fdn))
				.fetchSingleAttributeValueForAttributeNameFromResponse(
						"VpnInterfaceId");
	}

	private String getIPInterfaceMO(String fdn) {
		return ResponseDtoWrapper.newResponseDtoWrapper(
				executeScriptEngineCommand(fdn))
				.fetchSingleAttributeValueForAttributeNameFromResponse(
						"ipInterfaceMoRef");
	}

	private String getIPAccessHostID(String node, String vpnInterfaceMOQuery) {
		String ipAccessHostEtRef = ResponseDtoWrapper.newResponseDtoWrapper(
				executeScriptEngineCommand(vpnInterfaceMOQuery))
				.fetchSingleAttributeValueForAttributeNameFromResponse(
						"ipAccessHostEtRef");

		String ipInterfaceCommandString = String.format(
				CMEDIT_IPSEC_QUERY_COMMAND, node, ipAccessHostEtRef);

		return ResponseDtoWrapper.newResponseDtoWrapper(
				executeScriptEngineCommand(ipInterfaceCommandString))
				.fetchSingleAttributeValueForAttributeNameFromResponse(
						"IpAccessHostEtId");
	}

}