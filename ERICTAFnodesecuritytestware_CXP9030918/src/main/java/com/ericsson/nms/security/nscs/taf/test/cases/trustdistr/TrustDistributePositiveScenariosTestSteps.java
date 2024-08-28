/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.trustdistr;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.netsim.NetSimResult;
import com.ericsson.cifwk.taf.handlers.netsim.commands.NetSimCommands;
import com.ericsson.cifwk.taf.handlers.netsim.commands.ShowalarmCommand;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.*;
import com.ericsson.oss.netsim.operator.cm.NetSimCmOperatorImpl;
import com.sun.xml.internal.rngom.ast.util.CheckingSchemaBuilder;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class TrustDistributePositiveScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(TrustDistributePositiveScenariosTestSteps.class);
    
//    public static final int START_IDX_NODELIST = 6;
//    public static final int END_IDX_NODELIST = 10;
    

    @TestStep(id = "trustDistributePositiveTest")
    public void trustDistributePositiveTest (
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test trustDistributePositiveTest ############# \n\n");
        logger.info(" \n\n ############# Starting test trustDistributePositiveTest ############# \n\n");

        
        final String[] nodeArray = nodeList.split(",");
        
//        String nodeName = "LTE07ERBS00004";
        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_NOT_SYNCHRONIZED_TRUST_DISTR];
        
        System.out.println("Checking node " + nodeName + " on netsim");
        final NetworkElement networkElement = getNetSimOperator().getNetworkElement(nodeName);
        
        if (networkElement != null) {
        	if (!startNode(networkElement, nodeName)) {
        		return;
        	}        	
        } else {
        	System.out.println("ABORTING TEST: node " + nodeName + " does not exist in netsim. Please check your simulation and connection");
        	return;
        }
        
        System.out.println("node " + nodeName + " is STARTED in netsim");
        
//        checkNetworkElementIsStartedForTestSetup(nodeName);
        
        final boolean isNodeSynched = getSyncNodeOperator().checkNodeIsSynchronised(nodeName);
        System.out.println(String.format("%s synched %b", nodeName, isNodeSynched));
        if (!isNodeSynched) {
        	// unsynchronize node setting CmNodeHeartbeatSupervision=1 active=false
        	unsynchronizeNode(nodeName);        	
        	System.out.println("Unsynchronizing node " + nodeName);
        	delay(3000);
        	
        	// synchronize node setting CmNodeHeartbeatSupervision=1 active=true
        	getSyncNodeOperator().orderSynchroniseNode(nodeName);
        	System.out.println("Synchronizing node " + nodeName);
        	delay(3000);
        }        
        
        int numberOfRetry = 1;
        int maxRetry = 10;
        while (numberOfRetry <= maxRetry && !getSyncNodeOperator().checkNodeIsSynchronised(nodeName)) {
        	numberOfRetry++;
        	delay(3000);
        }
        
        if (numberOfRetry > maxRetry) {
        	System.out.println("ABORTING TEST - Cannot synchronize node " + nodeName);
        } else {
        	System.out.println("Node " + nodeName + " is SYNC");
        
	        final String expectedResult = NscsServiceGetter.SECADM_TRUST_DISTRIBUTE_SUCCESS;
	        
//	        String xmlInputFileName = "certificate_issue_sample_test_oam.xml";
//	        String xmlContent = readResourceFile("data/certificate/" + xmlInputFileName);
	        
	//        String xmlInputFile = readResourceFile("data/certificate/certificate_issue_sample_test.xml");
	//        System.out.println(String.format(xmlInputFile, nodeName));
	        
//	        executeScriptEngineCommandWithFileAndVerifyResponseContainsExpectedContent(
//	        		CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_OAM, xmlInputFileName),
//	        		xmlInputFileName,
//	        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent, nodeName)),
//	        		expectedResult);
	        
	        executeScriptEngineCommandWithFileAndVerifyResponseContainsExpectedContent(
	        		TrustDistributeOperatorImpl.getTrustDistributeCommandNodeFile(NscsServiceGetter.CERT_TYPE_IPSEC, NscsServiceGetter.TRUST_NODE_FILE),
	        		NscsServiceGetter.TRUST_NODE_FILE,
	        		createByteArrayWithNodeFileForSecadmCommand(nodeName),
	        		expectedResult);
	        
	        executeScriptEngineCommandAndVerifyResponseContainsExpectedContent(
					TrustDistributeOperatorImpl.getTrustDistributeCommandNodeList(NscsServiceGetter.CERT_TYPE_IPSEC, nodeName),
	                expectedResult);
        }
        		
    }
        
}
