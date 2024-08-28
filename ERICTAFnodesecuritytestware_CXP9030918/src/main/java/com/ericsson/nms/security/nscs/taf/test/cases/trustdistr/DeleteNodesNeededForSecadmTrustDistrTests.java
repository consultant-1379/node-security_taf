/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.trustdistr;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.*;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.nms.security.nscs.taf.test.cases.DeleteNodesNeededForSecadmCredentialsTestSteps;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class DeleteNodesNeededForSecadmTrustDistrTests extends NodeSecurityTestCaseHelper {
    
    @Inject
	DeleteNodesNeededForSecadmTrustDistrTestSteps steps;
    
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-46030_TearDownTestEnvironment", title = "Tear down test environment")
    @Context(context = { Context.REST })
    public void deleteNodesNeededForSecadmCredentialsTest() {
    	
    		TestStepFlow deleteNodesNeededForSecadmTrustDistrTestFlow = flow("")
    				.withDataSources(dataSource("Complete_Node_List_Trust_Distribute_NetSim"))
    				.addTestStep(annotatedMethod(steps, "deleteNodesNeededForSecadmTrustDistrTest"))
                    .build();
	 
    		TestScenario scenario = scenario("Tear down test environment scenario")
    				.addFlow(deleteNodesNeededForSecadmTrustDistrTestFlow).build();
    	
    		TestScenarioRunner runner = runner().build();
    	
    		runner.start(scenario);

     }

}
