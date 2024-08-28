/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.*;

import javax.inject.Inject;

import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.*;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;

/**
 *
 * @author enmadmin
 */
public class KeygenInvalidAlgorithmTypeSizeErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    KeygenInvalidAlgorithmTypeSizeErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-45998_ErrorScenarios_03", title = "Keygen invalid algorithm-type-size error")
    @Context(context = {Context.REST})
    public void keygenNetworkElementSecurityError() {

        TestStepFlow keygenNetworkElementSecurityErrorTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Keygen_NetSim"))
            .addTestStep(annotatedMethod(steps, "keygenInvalidAlgorithmTypeSizeErrorTest"))
            .build();

        TestScenario scenario = scenario("Keygen Invalid Algorithm Type Size error scenario")
            .addFlow(keygenNetworkElementSecurityErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
