/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.trustdistr;

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
public class CreateNodesNeededForSecadmTrustDistrTests extends NodeSecurityTestCaseHelper {
    
    @Inject
    CreateNodesNeededForSecadmTrustDistrTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-46030_SetUpTestEnvironment", title = "Set up trust distribute test environment")
    @Context(context = {Context.REST})
    public void createNodesNeededForSecadmTrustDistr() {

        TestStepFlow createNodesNeededForSecadmTrustDistr = flow("")
            .withDataSources(dataSource("Complete_Node_List_Trust_Distribute_NetSim"))
            .addTestStep(annotatedMethod(steps, "createNodesNeededForSecadmTrustDistrTest"))
            .build();

        TestScenario scenario = scenario("Set up trust distribute test environment scenario")
            .addFlow(createNodesNeededForSecadmTrustDistr).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
}
