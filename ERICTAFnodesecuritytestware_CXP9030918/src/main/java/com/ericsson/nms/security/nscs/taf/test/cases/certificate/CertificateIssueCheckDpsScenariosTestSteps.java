/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.certificate;

import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.CMEDIT_IPSEC_STATUS_COMMAND;
import static com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil.splitStringToToken;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.*;

import java.util.*;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class CertificateIssueCheckDpsScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(CertificateIssueCheckDpsScenariosTestSteps.class);
    
    public static final int START_IDX_NODELIST = 6;
    public static final int END_IDX_NODELIST = 10;
    
    public static final String CERTIFICATE_ATTR = "CertificateIssue";

    @TestStep(id = "certificateIssueCheckDpsTest")
    public void certificateIssueCheckDpsTest (
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test certificateIssueCheckDpsTest ############# \n\n");
        logger.info(" \n\n ############# Starting test certificateIssueCheckDpsTest ############# \n\n");

        
        final String[] nodeArray = nodeList.split(",");
                
        for (int i = START_IDX_NODELIST; i < END_IDX_NODELIST; i++) {
        	String certificate = getCertificateIssueFromDPS(nodeArray[i]);
        }

    }
    
    
    private String getCertificateIssueFromDPS(String node) {
		ResponseDtoWrapper response = getCertificateDTO(node);
		return response.fetchSingleAttributeValueForAttributeNameFromResponse(CERTIFICATE_ATTR);
		
	}
    
    private ResponseDtoWrapper getCertificateDTO(String nodeName) {
		String fdn = String.format(NscsServiceGetter.CMEDIT_GET_CERTIFICATE_ISSUE, nodeName);
		return ResponseDtoWrapper
				.newResponseDtoWrapper(executeScriptEngineCommand(fdn));
	}
        
}
