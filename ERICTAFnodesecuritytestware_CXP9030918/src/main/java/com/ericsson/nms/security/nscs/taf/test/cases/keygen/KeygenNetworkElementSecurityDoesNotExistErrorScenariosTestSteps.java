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
public class KeygenNetworkElementSecurityDoesNotExistErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenNetworkElementSecurityDoesNotExistErrorScenariosTestSteps.class);

    @TestStep(id = "keygenNetworkElementSecurityDoesNotExistErrorTest")
    public void keygenNetworkElementSecurityDoesNotExistErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenNetworkElementSecurityDoesNotExistErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test keygenNetworkElementSecurityDoesNotExistErrorTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_NO_NETWORK_ELEMENT_SECURITY];
                
        String expectedError = NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED;
		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE;
		
		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(
        		String.format(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048)),
                expectedError, 
                expectedSuggestedSolution);
		
    }
    
}
