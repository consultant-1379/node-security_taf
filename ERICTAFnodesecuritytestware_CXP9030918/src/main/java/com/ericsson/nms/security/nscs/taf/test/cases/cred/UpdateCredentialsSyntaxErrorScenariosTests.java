/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.cred;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.*;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class UpdateCredentialsSyntaxErrorScenariosTests extends NodeSecurityTestCaseHelper  {
    
    @Inject
    UpdateCredentialsSyntaxErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_ErrorScenarios_02", title = "Update credentials syntax error")
    @Context(context = {Context.REST})
    public void updateCredentialsSyntaxError_positive() {

        TestStepFlow updateCredentialsSyntaxErroCreation = flow("")
            .withDataSources(dataSource("CredentialsUpdateSyntaxErrors"))
            .addTestStep(annotatedMethod(steps, "updateCredentialsSyntaxError"))
            .build();

        TestScenario scenario = scenario("Update credentials syntax error scenario")
            .addFlow(updateCredentialsSyntaxErroCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
    
}
