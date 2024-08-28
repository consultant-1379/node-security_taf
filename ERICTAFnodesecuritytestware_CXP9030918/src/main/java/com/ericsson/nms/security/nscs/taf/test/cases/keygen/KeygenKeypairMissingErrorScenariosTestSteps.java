/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.KeygenOperatorImpl;

/**
 *
 * @author enmadmin
 */
public class KeygenKeypairMissingErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenKeypairMissingErrorScenariosTestSteps.class);

    @TestStep(id = "keygenKeypairMissingErrorTest")
    public void keygenKeypairMissingErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenKeypairMissingErrorTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_KEYPAIR_NOT_FOUND];
        
        String expectedError = NscsServiceGetter.ERROR_10092_KEYPAIR_MISSING;
		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX;
		
		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(
        		String.format(KeygenOperatorImpl.getKeygenUpdateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048)),
                expectedError, 
                expectedSuggestedSolution);		
		
    }
    
}
