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
public class KeygenDuplicateNodesErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    KeygenDuplicateNodesErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-45998_ErrorScenarios_11", title = "Keygen duplicate nodes error")
    @Context(context = {Context.REST})
    public void keygenDuplicateNodesSecurityError() {

        TestStepFlow keygenUnassociatedErrorTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Keygen"))
            .addTestStep(annotatedMethod(steps, "keygenDuplicateNodesErrorTest"))
            .build();

        TestScenario scenario = scenario("Keygen duplicate nodes error scenario")
            .addFlow(keygenUnassociatedErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
