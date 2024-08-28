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
public class KeygenMODoesNotExistErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenMODoesNotExistErrorScenariosTestSteps.class);

    @TestStep(id = "keygenMODoesNotExistErrorTest")
    public void keygenMODoesNotExistErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenMODoesNotExistErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test keygenMODoesNotExistErrorTest ############# \n\n");

        String nodeName = "NODE_NOT_EXIST";
                
        String expectedError = NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST;
		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM;
		
		executeScriptEngineCommandAndVerifyResponseContainsErrorAndSolution(
        		String.format(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048)),
                expectedError, 
                expectedSuggestedSolution);
		
    }
    
}
