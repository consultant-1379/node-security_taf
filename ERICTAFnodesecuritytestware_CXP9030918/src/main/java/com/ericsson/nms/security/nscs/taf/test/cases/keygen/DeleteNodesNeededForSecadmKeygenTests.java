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
public class DeleteNodesNeededForSecadmKeygenTests extends NodeSecurityTestCaseHelper {
    
    @Inject
	DeleteNodesNeededForSecadmKeygenTestSteps steps;
    
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-45998_TearDownTestEnvironment", title = "Tear down test environment")
    @Context(context = { Context.REST })
    public void deleteNodesNeededForSecadmCredentialsTest() {
    	
    		TestStepFlow deleteNodesNeededForSecadmKeygenTestFlow = flow("")
    				.withDataSources(dataSource("Complete_Node_List_Keygen_NetSim"))
    				.addTestStep(annotatedMethod(steps, "deleteNodesNeededForSecadmTest"))
                    .build();
	 
    		TestScenario scenario = scenario("Tear down test environment scenario")
    				.addFlow(deleteNodesNeededForSecadmKeygenTestFlow).build();
    	
    		TestScenarioRunner runner = runner().build();
    	
    		runner.start(scenario);

     }

}
