/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.cred;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.apache.log4j.Logger;

/**
 *
 * @author enmadmin
 */
public class UpdateCredentialsSyntaxErrorScenariosTestSteps extends NodeSecurityTestCaseHelper {
  
    private static Logger logger = Logger.getLogger(UpdateCredentialsSyntaxErrorScenariosTestSteps.class);

    @TestStep(id = "updateCredentialsSyntaxError")
    public void updateCredentialsSyntaxError(
            @Input("command") final String command,
            @Input("purpose") final String purpose){

        System.out.println(" \n\n ############# Starting test CredentialsUpdateSyntaxErrors  ############# ");
        System.out.println("   #############     testing " + purpose + "    ############# \n\n");
        logger.info("test CredentialsUpdateSyntaxErrors:" + " #############     testing " + purpose + "    #############");

//        final String httpResponseBody = getScriptEngineOperator().runCommand(command).getBody();
//
//        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR), true, "Test Case Failure : Expected Error not received : " + NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR );
//        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX), true, "Test Case Failure : Expected Suggested not received" + NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(command,NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX);



    }


}
