/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.*;
import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import javax.inject.Inject;
import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class CreateCredentialsForSgsnSyntaxErrorScenariosTests extends NodeSecurityTestCaseHelper {
    
    @Inject
    CreateCredentialsSyntaxErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_ErrorScenariosSgsn_01", title = "Create credentials syntax error for Sgsn node")
    @Context(context = {Context.REST})
    public void createCredentialsSyntaxError_positive() {

        TestStepFlow createCredentialsSyntaxErrorCreationForSgsn = flow("")
            .withDataSources(dataSource("CredentialsCreateSyntaxErrorsForSgsn"))
            .addTestStep(annotatedMethod(steps, "createCredentialsSyntaxError"))
            .build();
        
        TestScenario scenario = scenario("Create credentials syntax error scenario")
            .addFlow(createCredentialsSyntaxErrorCreationForSgsn).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

}
