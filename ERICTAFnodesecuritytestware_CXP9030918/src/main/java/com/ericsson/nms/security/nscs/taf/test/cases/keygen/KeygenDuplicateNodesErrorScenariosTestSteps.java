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
public class KeygenDuplicateNodesErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenDuplicateNodesErrorScenariosTestSteps.class);

    @TestStep(id = "keygenDuplicateNodesErrorTest")
    public void keygenDuplicateNodesErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenDuplicateNodesErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test keygenDuplicateNodesErrorTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName1 = nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS];
        String nodeName2 = nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS];
                
        String expectedError = NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES;
		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND;
		
		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(
        		String.format(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName1 + "," + nodeName2, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048)),
                expectedError, 
                expectedSuggestedSolution);
		
		
    }
    
}
