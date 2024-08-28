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
public class DeleteNodesSgsnNeededForSecadmCredentialsTests extends NodeSecurityTestCaseHelper {
    
    @Inject
	DeleteNodesNeededForSecadmCredentialsTestSteps steps;
    
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_TearDownTestEnvironmentSgsn", title = "Tear down test environment for Sgsn nodes")
    @Context(context = { Context.REST })
    public void deleteNodesSgsnNeededForSecadmCredentialsTest() {
    	
    		TestStepFlow deleteSgsnNodesNeededForSecadmCredentialsTestFlow = flow("")
    				.withDataSources(dataSource("Complete_SGSN_Node_List_Credentials"))
    				.addTestStep(annotatedMethod(steps, "deleteNodesNeededForSecadmTest"))
                    .build();
            
    		TestScenario scenario = scenario("Tear down test environment scenario")
    				.addFlow(deleteSgsnNodesNeededForSecadmCredentialsTestFlow).build();
    	
    		TestScenarioRunner runner = runner().build();
    	
    		runner.start(scenario);

     }

}
