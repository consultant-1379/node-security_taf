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

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class TrustDistributeNodeNotExistErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(TrustDistributeNodeNotExistErrorScenariosTestSteps.class);

    @TestStep(id = "trustDistributeNodeNotExistErrorTest")
    public void trustDistributeNodeNotExistErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test trustDistributeNodeNotExistErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test trustDistributeNodeNotExistErrorTest ############# \n\n");
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(NscsServiceGetter.CERTISSUE_USER_NAME);

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[0];
        String fdn = "NetworkElement=" + nodeName;
                
        String expectedError = NscsServiceGetter.THE_MO_SPECIFIED_DOES_NOT_EXIST + " - " + fdn;
        
		String expectedSuggestedSolution = NscsServiceGetter.PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM;
        
//		String xmlInputFileName = "certificate_issue_sample_test.xml";
//        String xmlContent = readResourceFile("data/certificate/" + xmlInputFileName);
		
//		executeScriptEngineCommandWithFileAndVerifyResponseContainsError(
//        		CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, NscsServiceGetter.CERT_NODE_FILE_XML),
//        		NscsServiceGetter.CERT_NODE_FILE_XML,
//        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlInputFile,nodeName)),
//                expectedError);
		
//		executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(
//				CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, xmlInputFileName),
//				xmlInputFileName,
//        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent,nodeName)),
//                expectedError, 
//                expectedSuggestedSolution);
        
        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(TrustDistributeOperatorImpl.getTrustDistributeCommandNodeFile(NscsServiceGetter.CERT_TYPE_IPSEC, NscsServiceGetter.TRUST_NODE_FILE),
        		NscsServiceGetter.TRUST_NODE_FILE,
        		createByteArrayWithNodeFileForSecadmCommand(nodeName),
                expectedError,
                expectedSuggestedSolution);
			
		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(TrustDistributeOperatorImpl.getTrustDistributeCommandNodeList(NscsServiceGetter.CERT_TYPE_IPSEC, nodeName),
                expectedError,
                expectedSuggestedSolution);
		
//		getLoginOperator().deleteUser();
    }
    
}
