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
public class CreateNodesSgsnNeededForSecadmCredentialsTests extends NodeSecurityTestCaseHelper {
    
    @Inject
    CreateNodesNeededForSecadmCredentialsTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_SetUpTestEnvironmentSgsn", title = "Set up test environment for Sgsn nodes")
    @Context(context = {Context.REST})
    public void createNodesSgsnNeededForSecadmCredentials_positive() {

        TestStepFlow createSgsnNodesNeededForSecadmCredentialsCreation = flow("")
            .withDataSources(dataSource("Complete_SGSN_Node_List_Credentials"))
            .addTestStep(annotatedMethod(steps, "createNodesNeededForSecadmCredentialsTest"))
            .build();
        
        TestScenario scenario = scenario("Set up test environment scenario")
            .addFlow(createSgsnNodesNeededForSecadmCredentialsCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
}

