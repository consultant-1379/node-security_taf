/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.certificate;

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
public class CertificateIssueXmlContentNotValidScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(CertificateIssueXmlContentNotValidScenariosTestSteps.class);

    @TestStep(id = "certificateIssueXmlNotValidErrorTest")
    public void certificateIssueXmlNotValidErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test certificateIssueXmlNotValidErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test certificateIssueXmlNotValidErrorTest ############# \n\n");
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(NscsServiceGetter.CERTISSUE_USER_NAME);

//        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = "ERBS_TEST";
        
        // XML mismatch XSD
                
        String expectedError = NscsServiceGetter.INVALID_INPUT_XML_FILE_CONTENT;
        
		String expectedSuggestedSolution = NscsServiceGetter.INVALID_INPUT_XML_FILE_CONTENT_SOLUTION;
        
		String xmlInputFileName = "certificate_issue_invalid_test.xml";
		
        String xmlContent = readResourceFile("data/certificate/" + xmlInputFileName);
				
//		executeScriptEngineCommandWithFileAndVerifyResponseContainsError(
//        		CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, NscsServiceGetter.CERT_NODE_FILE_XML_INVALID),
//        		NscsServiceGetter.CERT_NODE_FILE_XML_INVALID,
//        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlInputFile,nodeName)),
//                expectedError);
		
		executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(
				CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, xmlInputFileName),
				xmlInputFileName,
        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent,nodeName)),
                expectedError, 
                expectedSuggestedSolution);
		
		
		// FILE EMPTY
		
		expectedError = NscsServiceGetter.FILE_CONTENT_NOT_VALID;
        
		expectedSuggestedSolution = NscsServiceGetter.FILE_CONTENT_NOT_VALID_SOLUTION;
        
		xmlInputFileName = "certificate_issue_empty_test.xml";
		
		xmlContent = readResourceFile("data/certificate/" + xmlInputFileName);
		
        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(
        		CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, xmlInputFileName),
        		xmlInputFileName,
        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent,nodeName)),
                expectedError, 
                expectedSuggestedSolution);
        
        
        
        // Missing mandatory parameters for IPSEC
        
        expectedError = NscsServiceGetter.MISSING_MANDATORY_PARAMS_FOR_IPSEC;
        
		expectedSuggestedSolution = NscsServiceGetter.MISSING_MANDATORY_PARAMS_FOR_IPSEC_SOLUTION;
        
		xmlInputFileName = "certificate_issue_missing_mandatory_param_ipsec.xml";
		
		xmlContent = readResourceFile("data/certificate/" + xmlInputFileName);
		
        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(
        		CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, xmlInputFileName),
        		xmlInputFileName,
        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(xmlContent,nodeName)),
                expectedError, 
                expectedSuggestedSolution);
        
//        getLoginOperator().deleteUser();
    }
    
}
