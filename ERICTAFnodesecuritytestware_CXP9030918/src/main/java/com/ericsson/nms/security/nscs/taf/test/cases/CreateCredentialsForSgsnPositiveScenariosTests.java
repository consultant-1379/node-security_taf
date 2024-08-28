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
public class CreateCredentialsForSgsnPositiveScenariosTests  extends NodeSecurityTestCaseHelper {

    @Inject
	CreateCredentialsPositiveScenariosTestSteps steps;
    
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_CredentialsPositiveScenariosSgsn_01", title = "Verify credentials create with different inputs for Sgsn nodes")
    @Context(context = { Context.REST })
    public void createCredentialsForSgsnPositive() {
    	
            TestStepFlow createSgsnCredentialsPositiveFlow = flow("")
    				.withDataSources(dataSource("Complete_SGSN_Node_List_Credentials"))
    				.addTestStep(annotatedMethod(steps, "credentialsCreateOneNodeUsingSupportedNodeNamesFromList"))
                    .addTestStep(annotatedMethod(steps, "credentialsCreateOneNodeUsingSupportedNodeNamesFromFile"))
                    .addTestStep(annotatedMethod(steps, "credentialsCreateMultipleNodeFromList"))
                    .addTestStep(annotatedMethod(steps, "credentialsCreateMultipleNodeFromFile"))
                    .build();
	 
    		TestScenario scenario = scenario("Verify credentials create with different inputs scenario")
    				.addFlow(createSgsnCredentialsPositiveFlow).build();
    	
    		TestScenarioRunner runner = runner().build();
    	
    		runner.start(scenario);

     }

}
