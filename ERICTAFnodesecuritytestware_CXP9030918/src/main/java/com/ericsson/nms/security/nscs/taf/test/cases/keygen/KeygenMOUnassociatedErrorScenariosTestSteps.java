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
public class KeygenMOUnassociatedErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenMOUnassociatedErrorScenariosTestSteps.class);

    @TestStep(id = "keygenMOUnassociatedErrorTest")
    public void keygenMOUnassociatedErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {
//
//        System.out.println(" \n\n ############# Starting test keygenMOUnassociatedErrorTest ############# \n\n");
//        logger.info(" \n\n ############# Starting test keygenMOUnassociatedErrorTest ############# \n\n");
//
//        final String[] nodeArray = nodeList.split(",");
////        final String[] ipArray = ipAddressList.split(",");
//        
//        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_UNASSCOCIATED];
//        
//        deleteMeContextForTestSetup(nodeName);
//                
//        String expectedError = NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO;
//		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_ME_CONTEXT_CORRESPONDING_TO_THE_SPECIFIED_MO;
//		
//		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(
//        		String.format(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048)),
//                expectedError, 
//                expectedSuggestedSolution);
//		
//		
    }
    
}
