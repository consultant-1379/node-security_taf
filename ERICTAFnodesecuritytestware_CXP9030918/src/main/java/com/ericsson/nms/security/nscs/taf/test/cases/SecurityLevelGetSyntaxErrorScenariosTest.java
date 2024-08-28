package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class SecurityLevelGetSyntaxErrorScenariosTest extends NodeSecurityTestCaseHelper {

    public Set<String> nodeCreated = new HashSet<>();

    private static Logger logger = Logger
            .getLogger(SecurityLevelGetSyntaxErrorScenariosTest.class);


    @Context(context = { Context.REST })
    @DataDriven(name = "SecurityLevelGetSyntaxErrors")
    @Test(groups = { "Acceptance" })
    public void securityLevelGetSyntaxError(
            @Input("command") final String command,
            @Input("purpose") final String purpose){

        System.out.println(" \n\n ############# Starting test securityLevelGetSyntaxErrors  ############# ");
        System.out.println("   #############     testing " + purpose + "    ############# \n\n");
        logger.info("Starting test securityLevelGetSyntaxErrors " + "testing " + purpose);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(command,NscsServiceGetter.ERROR_10001_COMMAND_SYNTAX_ERROR,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX);

    }


}
