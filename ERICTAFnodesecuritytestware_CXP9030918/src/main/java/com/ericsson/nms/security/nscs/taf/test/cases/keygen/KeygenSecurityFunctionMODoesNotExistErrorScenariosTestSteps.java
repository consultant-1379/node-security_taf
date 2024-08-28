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
public class KeygenSecurityFunctionMODoesNotExistErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenSecurityFunctionMODoesNotExistErrorScenariosTestSteps.class);

    @TestStep(id = "keygenSecurityFunctionMODoesNotExistErrorTest")
    public void keygenSecurityFunctionMODoesNotExistErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenSecurityFunctionMODoesNotExistErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test keygenSecurityFunctionMODoesNotExistErrorTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_NO_SECURITY_FUNCTION];
                
        String expectedError = NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED;
		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_SECURITY_FUNCTION_MO;
		
		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(
        		String.format(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048)),
                expectedError, 
                expectedSuggestedSolution);		
		
    }
    
}
