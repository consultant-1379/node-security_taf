/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

import javax.validation.constraints.AssertTrue;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.KeygenOperatorImpl;
import com.ericsson.nms.security.nscs.taf.test.operators.ResponseDtoWrapper;

/**
 *
 * @author enmadmin
 */
public class KeygenCreatePositiveUnassociatedMeContextScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenCreatePositiveUnassociatedMeContextScenariosTestSteps.class);
    
    public static final String ENM_SSH_PUBLIC_KEY = "enmSshPublicKey";    
    public static final String ENM_SSH_PRIVATE_KEY = "enmSshPrivateKey";
    

    @TestStep(id = "keygenCreatePositiveUnassociatedMeContextTest")
    public void keygenCreatePositiveUnassociatedMeContextTest (
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenCreatePositiveUnassociatedMeContextTest ############# \n\n");
        
        final String[] nodeArray = nodeList.split(",");
        
        final String expectedResult = NscsServiceGetter.SECADM_KEYGEN_CREATE_SUCCESS;

        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_TOCREATE_KEYS_UNASSOCIATED];
                        
        if (!isNodeNameOnNetSimMap(nodeName)) {
        	return;
        }
        
        System.out.println("Deleting MeContext for node: " + nodeName);
        
        deleteMeContextForTestSetup(nodeName);
        
        System.out.println("Deleted MeContext for node: " + nodeName);
        
        String publicKey = getkeygenCreateFromDPS(nodeName, ENM_SSH_PUBLIC_KEY).trim().toLowerCase();
        String privateKey = getkeygenCreateFromDPS(nodeName, ENM_SSH_PRIVATE_KEY).trim().toLowerCase();
        
        if (!(isPresent(publicKey) || isPresent(privateKey))) {
        	
        	System.out.println("starting key generation command on node " + nodeName);
        	executeScriptEngineCommandAndVerifyResponseContainsExpectedContent(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA1024), expectedResult);
        	
        	delay(NscsServiceGetter.DELAY_KEY_GENERATION);
        	
        	publicKey = getkeygenCreateFromDPS(nodeName, ENM_SSH_PUBLIC_KEY).trim().toLowerCase();
            privateKey = getkeygenCreateFromDPS(nodeName, ENM_SSH_PRIVATE_KEY).trim().toLowerCase();
            
            assertTrue("Public Key must not be NULL", isPresent(publicKey));
            assertTrue("Private Key must not be NULL", isPresent(privateKey));
            
            System.out.println("valid public key: " + publicKey + " and private key: " + privateKey);
                        
        } else {
        	System.out.println("ABORTING TEST: Invalid value for public key: " + publicKey + " and private key: " + privateKey);
        	return;
        }   

    }    
    
    private boolean isPresent(String value) {
    	return !"null".equals(value) && !"".equals(value);
    }
}
