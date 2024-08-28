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
import com.ericsson.cifwk.taf.scenario.api.DataSourceDefinitionBuilder;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class KeygenUnsupportedNodeTypeErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    KeygenUnsupportedNodeTypeErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-45998_ErrorScenarios_07", title = "Keygen unsupported node type error")
    @Context(context = {Context.REST})
    public void keygenUnsupportedNodeTypeError() {

        TestStepFlow keygenUnsupportedNodeTypeErrorTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Keygen_NetSim"))
            .addTestStep(annotatedMethod(steps, "keygenUnsupportedNodeTypeErrorTest"))
            .build();

        TestScenario scenario = scenario("Keygen Unsupported Node Type error scenario")
            .addFlow(keygenUnsupportedNodeTypeErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
