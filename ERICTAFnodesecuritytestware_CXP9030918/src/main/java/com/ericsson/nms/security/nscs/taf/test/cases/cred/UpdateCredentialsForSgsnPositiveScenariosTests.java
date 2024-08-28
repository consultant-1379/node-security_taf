/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.cred;


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
public class UpdateCredentialsForSgsnPositiveScenariosTests extends NodeSecurityTestCaseHelper {
    
    @Inject
	UpdateCredentialsPositiveScenariosTestSteps steps;
    
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_CredentialsPositiveScenariosSgsn_02",
            title = "Verify credentials update with different inputs for Sgsn node")
    @Context(context = { Context.REST })
    public void UpdateCredentialsForSgsnPositive() {
    	
    		TestStepFlow updateSgsnCredentialsPositiveFlow = flow("")
    				.withDataSources(dataSource("Complete_SGSN_Node_List_Credentials"))
    				.addTestStep(annotatedMethod(steps, "credentialsUpdateOneNode"))
    				.addTestStep(annotatedMethod(steps, "credentialsUpdateListOfNodes"))
                    .build();
            
    		TestScenario scenario = scenario("Verify credentials update with different inputs scenario")
    				.addFlow(updateSgsnCredentialsPositiveFlow).build();
    	
    		TestScenarioRunner runner = runner().build();
    	
    		runner.start(scenario);

     }   
}
