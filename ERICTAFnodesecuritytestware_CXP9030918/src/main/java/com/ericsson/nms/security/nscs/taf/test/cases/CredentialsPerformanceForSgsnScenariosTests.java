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
public class CredentialsPerformanceForSgsnScenariosTests extends NodeSecurityTestCaseHelper {
    
    @Inject
	CredentialsPerformanceScenariosTestSteps steps;
    
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_PerformanceTestsSgsn", title = "Performance test on the secadm credential command for Sgsn nodes")
    @Context(context = { Context.REST })
    public void CredentialsPerformanceForSgsn() {
    	
    		TestStepFlow CredentialsPerformanceForSgsnFlow = flow("")
    			  //.withDataSources(dataSource(""))
    				.addTestStep(annotatedMethod(steps, "credentialsCreateUpdateLargeNumberOfNodesForSgsn"))
                    .build();
	
    		TestScenario scenario = scenario("Performance test on the secadm credential command scenario")
    				.addFlow(CredentialsPerformanceForSgsnFlow).build();
    	
    		TestScenarioRunner runner = runner().build();
    	
    		runner.start(scenario);

     }
}
