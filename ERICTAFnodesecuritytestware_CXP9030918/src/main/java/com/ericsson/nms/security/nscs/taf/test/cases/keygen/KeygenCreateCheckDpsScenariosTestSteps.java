/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

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
public class KeygenCreateCheckDpsScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenCreateCheckDpsScenariosTestSteps.class);
    
    public static final int START_IDX_NODELIST = 6;
    public static final int END_IDX_NODELIST = 10;
    
    public static final String ENM_SSH_PUBLIC_KEY = "enmSshPublicKey";

    @TestStep(id = "keygenCreateCheckDpsTest")
    public void keygenCreateCheckDpsTest (
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenCreateCheckDpsTest ############# \n\n");
        logger.info(" \n\n ############# Starting test keygenCreateCheckDpsTest ############# \n\n");

        
        final String[] nodeArray = nodeList.split(",");
                
        for (int i = START_IDX_NODELIST; i < END_IDX_NODELIST; i++) {
        	String keys = getkeygenCreateFromDPS(nodeArray[i],"");
        	assertTrue(String.format("Key must not be empty for node %s", nodeArray[i]), keys.length() > 0);
        }

    }
    
    
//    private String getkeygenCreateFromDPS(String node) {
//		ResponseDtoWrapper response = getKeyDTO(node);
//		return response.fetchSingleAttributeValueForAttributeNameFromResponse(ENM_SSH_PUBLIC_KEY);
//		
//	}
//    
//    private ResponseDtoWrapper getKeyDTO(String nodeName) {
//		String fdn = String.format(NscsServiceGetter.CMEDIT_GET_NETWORK_ELEMENT_SECURITY_COMMAND, nodeName);
//		return ResponseDtoWrapper
//				.newResponseDtoWrapper(executeScriptEngineCommand(fdn));
//	}
        
}
