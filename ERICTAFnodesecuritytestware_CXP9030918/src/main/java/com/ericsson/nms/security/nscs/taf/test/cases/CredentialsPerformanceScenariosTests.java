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
public class CredentialsPerformanceScenariosTests extends NodeSecurityTestCaseHelper {
    
    @Inject
	CredentialsPerformanceScenariosTestSteps steps;
    
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_PerformanceTests", title = "Performance test on the secadm credential command")
    @Context(context = { Context.REST })
    public void CredentialsPerformance() {
    	
    		TestStepFlow CredentialsPerformanceFlow = flow("")
    			  //.withDataSources(dataSource(""))
    				.addTestStep(annotatedMethod(steps, "credentialsCreateUpdateLargeNumberOfNodes"))
                    .build();
	
    		TestScenario scenario = scenario("Performance test on the secadm credential command scenario")
    				.addFlow(CredentialsPerformanceFlow).build();
    	
    		TestScenarioRunner runner = runner().build();
    	
    		runner.start(scenario);

     }
}
