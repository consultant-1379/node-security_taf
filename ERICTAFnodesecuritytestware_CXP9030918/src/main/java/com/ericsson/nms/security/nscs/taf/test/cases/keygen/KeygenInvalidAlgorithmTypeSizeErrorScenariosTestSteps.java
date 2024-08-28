/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.KeygenOperator;
import com.ericsson.nms.security.nscs.taf.test.operators.KeygenOperatorImpl;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class KeygenInvalidAlgorithmTypeSizeErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenInvalidAlgorithmTypeSizeErrorScenariosTestSteps.class);

    @TestStep(id = "keygenInvalidAlgorithmTypeSizeErrorTest")
    public void keygenInvalidAlgorithmTypeSizeErrorTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenInvalidAlgorithmTypeSizeErrorTest ############# \n\n");
        logger.info(" \n\n ############# Starting test keygenInvalidAlgorithmTypeSizeErrorTest ############# \n\n");

        final boolean introduceDelay=false;       // Sometimes the server responses are unreliable so as to not send too many commands too often introduce a delay between some activities
        final int delayInMilliseconds = 3000;

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NETWORK_ELEMENT_SECURITY];
        
        String expectedError = NscsServiceGetter.ERROR_10091_INVALID_ALGORITHM_TYPE_SIZE;
		String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX;
		
		executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(
        		String.format(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_INVALID)),
                expectedError, 
                expectedSuggestedSolution);
		
		
    }
    
}
