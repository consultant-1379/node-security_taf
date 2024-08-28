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

import org.apache.log4j.Logger;

/**
 *
 * @author enmadmin
 */
public class CreateKeygenSyntaxErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(CreateKeygenSyntaxErrorScenariosTestSteps.class);

    @TestStep(id = "createKeygenSyntaxError")
    public void createKeygenSyntaxError(
            @Input("command") final String command,
            @Input("purpose") final String purpose){

        System.out.println(" \n\n ############# Starting test createKeygenSyntaxError  ############# ");
        System.out.println("   #############     testing " + purpose + "    ############# \n\n");
        logger.info("test keygenSyntaxErrors:" + " #############     testing \" + purpose + \"    #############");

//        final String httpResponseBody=getScriptEngineOperator().runCommand(command).getBody();

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(command,NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX);

//        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
//                NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR,
//                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX);

    }
    
}
