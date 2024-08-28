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
public class TrustDistributeNodeNotCertifiableErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(TrustDistributeNodeNotCertifiableErrorScenariosTestSteps.class);

    @TestStep(id = "trustDistributeNodeNotCertifiableErrorTest")
    public void trustDistributeNodeNotCertifiableErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test trustDistributeNodeNotCertifiableErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test trustDistributeNodeNotCertifiableErrorTest ############# \n\n");
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(NscsServiceGetter.CERTISSUE_USER_NAME);

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        for (int i = NscsServiceGetter.START_INDEXOF_COM_FOR_TRUST_DISTRIBUTE; i < nodeArray.length; i++) {
//	        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_NOT_CERTIFIABLE];
        	String nodeName = nodeArray[i];
	        
	        String expectedError = NscsServiceGetter.NODE_NOT_CERTIFIABLE;
	        
			String expectedSuggestedSolution = NscsServiceGetter.SPECIFY_A_CERTFIABLE_NODE;
	        
//	        String xmlInputFileName = "certificate_issue_sample_test.xml";
//	        String xmlContent = readResourceFile("data/certificate/" + xmlInputFileName);
							
//			executeScriptEngineCommandWithFileAndVerifyResponseContainsError(
//	        		CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, xmlInputFileName),
//	        		xmlInputFileName,
//	        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent,nodeName)),
//	                expectedError);
			
//			executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(
//					CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, xmlInputFileName),
//	        		xmlInputFileName,
//	        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent,nodeName)),
//	                expectedError, 
//	                expectedSuggestedSolution);
			
			executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(TrustDistributeOperatorImpl.getTrustDistributeCommandNodeFile(NscsServiceGetter.CERT_TYPE_IPSEC, NscsServiceGetter.TRUST_NODE_FILE),
	        		NscsServiceGetter.TRUST_NODE_FILE,
	        		createByteArrayWithNodeFileForSecadmCommand(nodeName),
	                expectedError,
	                expectedSuggestedSolution);
				
			executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(TrustDistributeOperatorImpl.getTrustDistributeCommandNodeList(NscsServiceGetter.CERT_TYPE_IPSEC, nodeName),
	                expectedError,
	                expectedSuggestedSolution);
			
//			getLoginOperator().deleteUser();
        }
		
    }
    
}
