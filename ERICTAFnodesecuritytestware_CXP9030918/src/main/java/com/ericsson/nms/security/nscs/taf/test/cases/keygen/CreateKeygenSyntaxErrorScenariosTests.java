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

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class CreateKeygenSyntaxErrorScenariosTests extends NodeSecurityTestCaseHelper {
    
    @Inject
    CreateKeygenSyntaxErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-45998_ErrorScenarios_01", title = "Keygen Create syntax error")
    @Context(context = {Context.REST})
    public void createKeysSyntaxError_positive() {

        TestStepFlow createKeygenSyntaxErrorCreation = flow("")
            .withDataSources(dataSource("KeygenCreateSyntaxErrors"))
            .addTestStep(annotatedMethod(steps, "createKeygenSyntaxError"))
            .build();

        TestScenario scenario = scenario("Create keygen syntax error scenario")
            .addFlow(createKeygenSyntaxErrorCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
