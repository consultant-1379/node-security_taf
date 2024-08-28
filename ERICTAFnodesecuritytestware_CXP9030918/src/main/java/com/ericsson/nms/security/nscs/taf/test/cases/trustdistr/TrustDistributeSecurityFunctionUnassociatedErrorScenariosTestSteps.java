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

import org.apache.log4j.Logger;

/**
 *
 * @author enmadmin
 */
public class TrustDistributeSecurityFunctionUnassociatedErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(TrustDistributeSecurityFunctionUnassociatedErrorScenariosTestSteps.class);

    @TestStep(id = "trustDistributeSecurityFunctionUnassociatedErrorTest")
    public void trustDistributeSecurityFunctionUnassociatedErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test trustDistributeSecurityFunctionUnavailableErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test trustDistributeSecurityFunctionUnavailableErrorTest ############# \n\n");
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(NscsServiceGetter.CERTISSUE_USER_NAME);

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[1];
                        
        String expectedError = NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED;
		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_SECURITY_FUNCTION_MO;
        
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
		
		System.out.println(" \n\n ############# Starting test trustDistributeUnassociatedErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test trustDistributeUnassociatedErrorTest ############# \n\n");
        
        System.out.println("Deleting MeContext for node: " + nodeName);
        
		deleteMeContextForTestSetup(nodeName);
		
		expectedError = NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO;
		expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_ME_CONTEXT_CORRESPONDING_TO_THE_SPECIFIED_MO;
		
		executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(
				TrustDistributeOperatorImpl.getTrustDistributeCommandNodeFile(NscsServiceGetter.CERT_TYPE_IPSEC, NscsServiceGetter.TRUST_NODE_FILE),
        		NscsServiceGetter.TRUST_NODE_FILE,
        		createByteArrayWithNodeFileForSecadmCommand(nodeName),
                expectedError,
                expectedSuggestedSolution);
			
		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(
				TrustDistributeOperatorImpl.getTrustDistributeCommandNodeList(NscsServiceGetter.CERT_TYPE_IPSEC, nodeName),
                expectedError,
                expectedSuggestedSolution);
		
//		executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(
//				CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, xmlInputFileName),
//				xmlInputFileName,
//        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent,nodeName)),
//                expectedError, 
//                expectedSuggestedSolution);
		
//		getLoginOperator().deleteUser();
    }
    
}
