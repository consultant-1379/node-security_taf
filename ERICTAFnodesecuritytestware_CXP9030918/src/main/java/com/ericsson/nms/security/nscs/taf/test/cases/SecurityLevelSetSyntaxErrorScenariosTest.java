package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;


public class SecurityLevelSetSyntaxErrorScenariosTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger
            .getLogger(SecurityLevelSetSyntaxErrorScenariosTest.class);


    @Context(context = { Context.REST })
    @DataDriven(name = "SecurityLevelSetSyntaxErrors")
    @Test(groups = { "Acceptance" })
    public void securityLevelSetSyntaxError(
            @Input("command") final String command,
            @Input("purpose") final String purpose){

        System.out.println(" \n\n ############# Starting test SecurityLevelSetSyntaxErrors  ############# ");
        System.out.println("   #############     testing " + purpose + "    ############# \n\n");
        logger.info("test SecurityLevelSetSyntaxErrors:" + " #############     testing \" + purpose + \"    #############");

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(command,NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX);


    }
}
