/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.*;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.cases.cred.UpdateCredentialsSyntaxErrorScenariosTestSteps;

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class UpdateKeygenSyntaxErrorScenariosTests extends NodeSecurityTestCaseHelper  {
    
    @Inject
    UpdateKeygenSyntaxErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-45998_ErrorScenarios_02", title = "Keygen Update syntax error")
    @Context(context = {Context.REST})
    public void updateKeygenSyntaxError_positive() {

        TestStepFlow updateKeygenSyntaxErroCreation = flow("")
            .withDataSources(dataSource("KeygenUpdateSyntaxErrors"))
            .addTestStep(annotatedMethod(steps, "updateKeygenSyntaxError"))
            .build();

        TestScenario scenario = scenario("Update keygen syntax error scenario")
            .addFlow(updateKeygenSyntaxErroCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
    
}
