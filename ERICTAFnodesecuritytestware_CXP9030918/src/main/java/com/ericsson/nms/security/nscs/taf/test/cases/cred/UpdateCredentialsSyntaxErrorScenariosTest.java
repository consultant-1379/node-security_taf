package com.ericsson.nms.security.nscs.taf.test.cases.cred;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.*;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;

public class UpdateCredentialsSyntaxErrorScenariosTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger
            .getLogger(UpdateCredentialsSyntaxErrorScenariosTest.class);

    @TestId(id = "update-creds-syntax-negative-1", title ="Verify syntax errors are thrown for badly written update credentials commands")
    @Context(context = { Context.REST })
    @DataDriven(name = "CredentialsUpdateSyntaxErrors")
    @Test(groups = { "Acceptance" })
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
