package com.ericsson.nms.security.nscs.taf.test.cases.ipsec;

import static com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil.*;
import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.*;
import com.ericsson.nms.security.nscs.taf.test.operators.ResponseDtoWrapper;

/**
 * Aim of these tests is to validate installation of ipsec trust and node cert.
 * without ipsecmo - LTE07ERBS00006
 * with ipsec deact - LTE07ERBS00007
 * without trust and node - LTE07ERBS00008
 * @author ehimnay
 */
public class IPsecInstallIPsecCertificatesScenariosTest extends
		IPsecurityTestCase {

	private static final Logger LOGGER = Logger
			.getLogger(IPsecInstallIPsecCertificatesScenariosTest.class);

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Node_TrustCert")
	@Test(groups = { "Acceptance" }, testName = "Create Node For Trust Cert", dataProvider = "IPsec_Create_Node_TrustCert", enabled = true, priority = 1)
	public void createNode(@Input("NODE") final String nodes,
			@Input("IP") final String ips) {
		createNodes(nodes, ips);
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Node_TrustCert")
	@Test(groups = { "Acceptance" }, testName = "install trust cert on a node which dont have ipsec", dataProvider = "IPsec_Create_Node_TrustCert", enabled = true, priority = 2)
	public void checkIPsecExist() {
		LOGGER.info("inside IPsecInstallIPsecCertificatesScenariosTest -> checkIPsecExist");
		final String nodeWithoutIPsec = "TrustCertNodeCertWithoutIPsec.xml";

		final Map<String, Object> properties = templateLoader(nodeWithoutIPsec,
				null);

		String httpResponseBody = executeScriptEngineCommand(
				IPSEC_OM_COMMAND + nodeWithoutIPsec, properties);

		if (!httpResponseBody.contains(IPSEC_SUCCESS_MESSAGE)) {
			assertTrue(
					"install trust cert on a node which dont have ipsec - passed",
					true);
		} else {
			assertFalse(
					"install trust cert on a node which dont have ipsec - failed",
					true);
		}
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Node_TrustCert")
	@Test(groups = { "Acceptance" }, testName = "install trust cert on node whose ipsec is deactive", dataProvider = "IPsec_Create_Node_TrustCert", enabled = true, priority = 2)
	public void checkTrustCertOnIPsecDeact(@Input("NODE") final String node) {
		LOGGER.info("inside IPsecInstallIPsecCertificatesScenariosTest -> checkTrustCertOnIPsecDeact");
		final String nodeWithoutIPsec = "TrustCertNodeCertWithoutIPsec.xml";

		final Map<String, Object> properties = templateLoader(nodeWithoutIPsec,
				null);
		String nodes[] = node.split(COMMA_SEPARATOR);
		List<String> trustCertBeforeCmdExe = getTrustCertFromDPS(nodes[1]);

		String httpResponseBody = executeScriptEngineCommand(
				IPSEC_OM_COMMAND + nodeWithoutIPsec, properties);

		if (httpResponseBody.contains(IPSEC_SUCCESS_MESSAGE)) {

			sleep(60);

			List<String> trustCertAfterList = getTrustCertFromDPS(nodes[1]);
			if (CollectionUtils.isNotEmpty(trustCertAfterList)) {
				if (trustCertAfterList.size() > trustCertBeforeCmdExe.size()) {
					assertFalse(
							"IPsec Add Trust Cert installed on a deactive ipsec",
							true);
				}
			} else {
				assertTrue("IPsec Add Trust Cert failed", true);
			}
		} else {
			assertTrue("IPsec Trust Cert command execution failed", false);
		}
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Node_TrustCert")
	@Test(groups = { "Acceptance" }, testName = "install trust cert and node cert", dataProvider = "IPsec_Create_Node_TrustCert", enabled = true, priority = 2)
	public void checkTrustCertNodeCert(@Input("NODE") final String node) {
		LOGGER.info("inside IPsecInstallIPsecCertificatesScenariosTest -> checkTrustCertNodeCert");
		final String trustCertXML = "TrustCertNodeCert.xml";

		final Map<String, Object> properties = templateLoader(trustCertXML,
				null);
		String nodes[] = node.split(COMMA_SEPARATOR);

		List<String> trustCertBeforeCmdExe = getTrustCertFromDPS(nodes[3]);

		String trustCertBeforeList = getNodeCertFromDPS(nodes[3]);

		String httpResponseBody = executeScriptEngineCommand(
				IPSEC_OM_COMMAND + trustCertXML, properties);

		if (httpResponseBody.contains(IPSEC_SUCCESS_MESSAGE)) {

			sleep(60);

			List<String> trustCertAfterCmdExe = getTrustCertFromDPS(nodes[3]);

			String trustCertAfterList = getNodeCertFromDPS(nodes[3]);

			if (trustCertAfterList.equals(trustCertBeforeList)
					&& trustCertAfterCmdExe.size() > trustCertBeforeCmdExe
							.size()) {
				assertTrue(
						"IPsec node cert and trust cert installed successfully",
						true);
			} else {
				assertTrue("IPsec node cert install failed", false);
			}

		} else {
			assertTrue("IPsec Trust Cert command execution failed", false);
		}
	}

	@Context(context = { Context.REST })
	@DataDriven(name = "IPsec_Create_Node_TrustCert")
	@Test(groups = { "Acceptance" }, testName = "IPsec Delete Node", dataProvider = "IPsec_Create_Node_TrustCert", enabled = true, priority = 3)
	public void cleanNode(@Input("NODE") final String node) {
		LOGGER.info("Inside IPsecInstallIPsecCertificatesScenariosTest -> cleanNode");
		deleteNodes(node);
	}

	private List<String> getTrustCertFromDPS(String node) {
		ResponseDtoWrapper response = getIPsecDTO(node);
		String trustCerts = response
				.fetchSingleAttributeValueForAttributeNameFromResponse("installedTrustedCertificates");
		LOGGER.info("List of Trust Cert for Node " + node + " : " + trustCerts);

		return splitStringToToken(trustCerts, "\\{(.*?)\\}");
	}

	private String getNodeCertFromDPS(String node) {
		ResponseDtoWrapper response = getIPsecDTO(node);
		String trustCerts = response
				.fetchSingleAttributeValueForAttributeNameFromResponse("certificate");
		LOGGER.info("List of Trust Cert for Node " + node + " : " + trustCerts);
		return trustCerts;
	}

	private ResponseDtoWrapper getIPsecDTO(String nodeName) {
		String fdn = String.format(CMEDIT_IPSEC_STATUS_COMMAND, nodeName);
		return ResponseDtoWrapper
				.newResponseDtoWrapper(executeScriptEngineCommand(fdn));
	}

}