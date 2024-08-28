/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.KeygenOperatorImpl;

/**
 *
 * @author enmadmin
 */
public class KeygenUpdatePositiveScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenUpdatePositiveScenariosTestSteps.class);
    
    public static final int START_IDX_NODELIST = 6;
    public static final int END_IDX_NODELIST = 10;
    
    public static final String ENM_SSH_PUBLIC_KEY = "enmSshPublicKey";    
    public static final String ENM_SSH_PRIVATE_KEY = "enmSshPrivateKey";
    
    public static final int DELAY_START_NETSIM = 3000;
    
    @TestStep(id = "keygenUpdatePositiveNodeListTest")
    public void keygenUpdatePositiveNodeListTest (
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenUpdatePositiveNodeListTest ############# \n\n");

        // NOTE: This test exploits the results of the previous positive test KeygenCreatePositiveScenariosTests 
        
        final String[] nodeArray = nodeList.split(",");
        
        final String expectedResult = NscsServiceGetter.SECADM_KEYGEN_UPDATE_SUCCESS;
        
        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_TOUPDATE_KEYS];        
        
        if (!isNodeNameOnNetSimMap(nodeName)) {
        	return;
        }
        
        String oldPublicKey = getkeygenCreateFromDPS(nodeName, ENM_SSH_PUBLIC_KEY).trim().toLowerCase();
        String oldPrivateKey = getkeygenCreateFromDPS(nodeName, ENM_SSH_PRIVATE_KEY).trim().toLowerCase();
        
        System.out.println("oldPublicKey: " + oldPublicKey);
        System.out.println("oldPrivateKey: " + oldPrivateKey);
        
        if (isPresent(oldPublicKey) && isPresent(oldPrivateKey)) {
        	
        	System.out.println("starting key update command on node " + nodeName);
        	executeScriptEngineCommandAndVerifyResponseContainsExpectedContent(KeygenOperatorImpl.getKeygenUpdateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048), expectedResult);
        	
        	delay(NscsServiceGetter.DELAY_KEY_GENERATION);
        	
        	String currentPublicKey = getkeygenCreateFromDPS(nodeName, ENM_SSH_PUBLIC_KEY).trim().toLowerCase();
            String currentPrivateKey = getkeygenCreateFromDPS(nodeName, ENM_SSH_PRIVATE_KEY).trim().toLowerCase();
            
            assertTrue("Current Public and Private Key must be updated", 
            		(isPresent(oldPublicKey) && !currentPublicKey.equals(oldPublicKey)) &&
    				(isPresent(oldPrivateKey) && !currentPrivateKey.equals(oldPrivateKey)));
            
            System.out.println("UPDATED public key: " + currentPublicKey + " and private key: " + currentPrivateKey);
            
        } else {
        	System.out.println("ABORTING TEST: publicKey and privateKey are not present for node: " + nodeName);
        	return;
        }
        
    }

    
    @TestStep(id = "keygenUpdatePositiveNodeFileTest")
    public void keygenUpdatePositiveNodeFileTest (
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenUpdatePositiveNodeFileTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_TOUPDATE_KEYS_FILE];
                
        if (!isNodeNameOnNetSimMap(nodeName)) {
        	return;
        }
        
        final String fileName = "KeygenUpdateInputFile.txt";
        byte[] fileContents = createByteArrayWithNodeNamesForSecadmCommand(nodeName);
        
        final String expectedResult = NscsServiceGetter.SECADM_KEYGEN_UPDATE_SUCCESS;
        
        executeScriptEngineCommandWithFileAndVerifyResponseContainsExpectedContent(KeygenOperatorImpl.getKeygenUpdateNodeFileCommand(fileName), fileName, fileContents, expectedResult);
                		
    }
    
    private boolean isPresent(String value) {
    	return !"null".equals(value) && !"".equals(value);
    }
        
}
