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
public class KeygenKeypairAlreadyGeneratedErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenKeypairAlreadyGeneratedErrorScenariosTestSteps.class);

    @TestStep(id = "keygenKeypairAlreadyGeneratedErrorTest")
    public void keygenKeypairAlreadyGeneratedErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenKeypairAlreadyGeneratedErrorTest ############# \n\n");
        
        // NOTE: This test exploits the results of the previous positive test KeygenCreatePositiveScenariosTests 


        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_KEYPAIR_ALREADY_GENERATED];
        
        if (!isNodeNameOnNetSimMap(nodeName)) {
        	return;
        }
                              
        String expectedError = NscsServiceGetter.ERROR_10093_KEYPAIR_ALREADY_GENERATED;
		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX;
		
		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(
        		String.format(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048)),
                expectedError, 
                expectedSuggestedSolution);
		
		
    }
    
}
