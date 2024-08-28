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
public class KeygenNetworkElementSecurityDoesNotExistErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    KeygenNetworkElementSecurityDoesNotExistErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-45998_ErrorScenarios_06", title = "Keygen Network Element Security Does Not Exist error")
    @Context(context = {Context.REST})
    public void keygenNetworkElementSecurityError() {

        TestStepFlow keygenNetworkElementSecurityErrorTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Keygen_NetSim"))
            .addTestStep(annotatedMethod(steps, "keygenNetworkElementSecurityDoesNotExistErrorTest"))
            .build();

        TestScenario scenario = scenario("Keygen Network Element Security Does Not Exist error scenario")
            .addFlow(keygenNetworkElementSecurityErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
}
