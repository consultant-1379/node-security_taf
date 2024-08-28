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
public class CertificateIssueInvalidArgumentErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(CertificateIssueInvalidArgumentErrorScenariosTestSteps.class);

    @TestStep(id = "certificateIssueInvalidArgumentErrorTest")
    public void certificateIssueInvalidArgumentErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test certificateIssueInvalidArgumentErrorTest ############# \n\n");
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(NscsServiceGetter.CERTISSUE_USER_NAME);

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NETWORK_ELEMENT_SECURITY];
                
        String expectedError = NscsServiceGetter.CERTIFICATE_INVALID_ARGUMENT;
        
		String expectedSuggestedSolution = NscsServiceGetter.CERTIFICATE_INVALID_ARGUMENT_SOLUTION;
						
		executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC_INVALID, NscsServiceGetter.CERT_NODE_FILE_XML),
        		NscsServiceGetter.CERT_NODE_FILE_XML,
        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(NscsServiceGetter.CERT_NODE_FILE_XML_CONTENT,nodeName)),
                expectedError,
                expectedSuggestedSolution);
			
		executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_OAM_INVALID, NscsServiceGetter.CERT_NODE_FILE_XML),
        		NscsServiceGetter.CERT_NODE_FILE_XML,
        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(NscsServiceGetter.CERT_NODE_FILE_XML_CONTENT,nodeName)),
                expectedError,
                expectedSuggestedSolution);
		
//		getLoginOperator().deleteUser();
    }
    
}
