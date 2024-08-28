/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.certificate;

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
public class DeleteNodesNeededForSecadmCertificateTests extends NodeSecurityTestCaseHelper {
    
    @Inject
	DeleteNodesNeededForSecadmCertificateTestSteps steps;
    
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-51129_TearDownTestEnvironment", title = "Tear down test environment")
    @Context(context = { Context.REST })
    public void deleteNodesNeededForSecadmCredentialsTest() {
    	
    		TestStepFlow deleteNodesNeededForSecadmCertificateTestFlow = flow("")
    				.withDataSources(dataSource("Complete_Node_List_Certificate_NetSim"))
    				.addTestStep(annotatedMethod(steps, "deleteNodesNeededForSecadmTest"))
                    .build();
	 
    		TestScenario scenario = scenario("Tear down test environment scenario")
    				.addFlow(deleteNodesNeededForSecadmCertificateTestFlow).build();
    	
    		TestScenarioRunner runner = runner().build();
    	
    		runner.start(scenario);

     }

}
