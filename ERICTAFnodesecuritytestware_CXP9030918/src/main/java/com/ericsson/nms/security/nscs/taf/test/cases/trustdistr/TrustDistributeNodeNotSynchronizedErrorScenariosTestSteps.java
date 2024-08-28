/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.trustdistr;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class TrustDistributeNodeNotSynchronizedErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(TrustDistributeNodeNotSynchronizedErrorScenariosTestSteps.class);

    @TestStep(id = "trustDistributeNodeNotSynchronizedErrorTest")
    public void trustDistributeNodeNotSynchronizedErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test trustDistributeNodeNotSynchronizedErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test trustDistributeNodeNotSynchronizedErrorTest ############# \n\n");
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(NscsServiceGetter.CERTISSUE_USER_NAME);

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_NOT_SYNCHRONIZED_TRUST_DISTR];
        
        final boolean isNodeSynched = getSyncNodeOperator().checkNodeIsSynchronised(nodeName);
        System.out.println(String.format("%s synched %b", nodeName, isNodeSynched));
        if (isNodeSynched) {
        	// unsynchronize node setting CmNodeHeartbeatSupervision=1 active=false
        	System.out.println("Unsynchronizing node " + nodeName);
        	unsynchronizeNode(nodeName);
        	delay(3000);
        }
        
        String expectedError = NscsServiceGetter.NODE_NOT_SYNCHRONIZED;
        
//		String expectedSuggestedSolution = NscsServiceGetter.PLEASE_ENSURE_THE_NODE_SPECIFIED_IS_SYNCHRONIZED;
        
//		String xmlInputFileName = "certificate_issue_sample_test.xml";
//        String xmlContent = readResourceFile("data/certificate/" + xmlInputFileName);
//						
//		
//        executeScriptEngineCommandWithFileAndVerifyResponseContainsError(
//        		CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, xmlInputFileName),
//        		xmlInputFileName,
//        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent,nodeName)),
//                expectedError);
        
        executeScriptEngineCommandWithFileAndVerifyResponseContainsError(
        		TrustDistributeOperatorImpl.getTrustDistributeCommandNodeFile(NscsServiceGetter.CERT_TYPE_IPSEC, NscsServiceGetter.TRUST_NODE_FILE),
        		NscsServiceGetter.TRUST_NODE_FILE,
        		createByteArrayWithNodeFileForSecadmCommand(nodeName),
                expectedError);
			
		executeScriptEngineCommandAndVerifyResponseContainsExpectedContent(
				TrustDistributeOperatorImpl.getTrustDistributeCommandNodeList(NscsServiceGetter.CERT_TYPE_IPSEC, nodeName),
                expectedError);
        		
//        getLoginOperator().deleteUser();
        
    }    
    
}
