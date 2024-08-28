package com.ericsson.nms.security.nscs.taf.test.cases;


import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class CreateCredentialsSyntaxErrorScenariosTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger
            .getLogger(CreateCredentialsSyntaxErrorScenariosTest.class);

    @Context(context = { Context.REST })
    @DataDriven(name = "CredentialsCreateSyntaxErrors")
    @Test(groups = { "Acceptance" })
    public void createCredentialsSyntaxError(
            @Input("command") final String command,
            @Input("purpose") final String purpose){

        System.out.println(" \n\n ############# Starting test credentialsCreateSyntaxErrors  ############# ");
        System.out.println("   #############     testing " + purpose + "    ############# \n\n");
        logger.info("test credentialsCreateSyntaxErrors:" + " #############     testing \" + purpose + \"    #############");

//        final String httpResponseBody=getScriptEngineOperator().runCommand(command).getBody();

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(command,NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX);

//        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
//                NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR,
//                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX);

    }


}
