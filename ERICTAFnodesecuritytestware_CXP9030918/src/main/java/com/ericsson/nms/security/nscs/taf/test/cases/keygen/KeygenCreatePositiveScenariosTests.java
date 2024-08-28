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
public class KeygenCreatePositiveScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    KeygenCreatePositiveScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-45998_PositiveScenarios_01", title = "Keygen create successful")
    @Context(context = {Context.REST})
    public void keygenCreatePositive() {

        TestStepFlow keygenCreatePositiveTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Keygen_NetSim"))
            .addTestStep(annotatedMethod(steps, "keygenCreatePositiveNodeListTest"))
            .addTestStep(annotatedMethod(steps, "keygenCreatePositiveNodeFileTest"))
            .build();

        TestScenario scenario = scenario("Keygen create positive scenario")
            .addFlow(keygenCreatePositiveTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
