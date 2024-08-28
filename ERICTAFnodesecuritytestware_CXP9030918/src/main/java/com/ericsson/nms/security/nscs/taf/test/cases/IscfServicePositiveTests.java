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

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.cifwk.taf.scenario.api.ExceptionHandler;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class IscfServicePositiveTests extends NodeSecurityTestCaseHelper {
    
    @Inject
	IscfServicePositiveTestSteps steps;
    
//	@Context(context = { Context.REST, Context.API })
//	@Context(context = { Context.API, Context.REST })
//	@Context(context = { Context.REST })
	@Context(context = { Context.API })
    
    /* FOR BACKWARD COMPATIBILITY ONLY ... */
    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-46171_IscfServiceLegacy_00", title = "Verify legacy API (FOR BACKWARD COMPATIBILITY ONLY)")
    public void iscfServiceTestLegacy() {
    	
		TestStepFlow iscfServiceTestLegacyFlow = flow("Verify legacy API flow (FOR BACKWARD COMPATIBILITY ONLY)")
            .addTestStep(annotatedMethod(steps, "setUp"))
			.addTestStep(annotatedMethod(steps, "tORF12546_Func_1_Generate_OAM_Iscf"))
			.addTestStep(annotatedMethod(steps, "tORF12546_Func_1_Generate_IPSec_Iscf"))
			.addTestStep(annotatedMethod(steps, "tORF12546_Func_1_Generate_COMBO_Iscf"))
            .addTestStep(annotatedMethod(steps, "tearDown"))
            .build();
 
		TestScenario iscfScenarioLegacy = scenario("Verify legacy API scenario (FOR BACKWARD COMPATIBILITY ONLY)")
			.addFlow(iscfServiceTestLegacyFlow).build();
	
		TestScenarioRunner runner = runner()
			.withListener(new LoggingScenarioListener())
			 // .withExceptionHandler(ExceptionHandler.LOGONLY)
			.build();
	
		runner.start(iscfScenarioLegacy);

    }
    /* ... FOR BACKWARD COMPATIBILITY ONLY */
    
    // TODO
    // 1. Logger logger --> declare correctly
    // 2. System.out.println --> logger
    @Context(context = { Context.API })
    @Test(enabled = true, priority = 2, groups = {"Acceptance"})
    @TestId(id = "TORF-46171_IscfServicePositive_01", title = "Verify OAM Flow Positive")
    public void iscfServiceOAMPositive() {
    	
		TestStepFlow iscfServiceOAMFlowPositive = flow("Verify OAM flow positive")
            .addTestStep(annotatedMethod(steps, "setUp"))
			.addTestStep(annotatedMethod(steps, "generate_OAM_Iscf"))
            .addTestStep(annotatedMethod(steps, "tearDown"))
			.withDataSources(dataSource("IscfService_OldInputs_OAM"),
			                 dataSource("IscfService_NewInputs_and_ExpectedOutputs_OAM"))
            .build();
 
		TestScenario iscfServiceOAMScenarioPositive = scenario("Verify OAM scenario positive")
			.addFlow(iscfServiceOAMFlowPositive).build();
	
	 // TestScenarioRunner runner = runner().build();
		
		TestScenarioRunner runner = runner()
			.withListener(new LoggingScenarioListener())
		 // .withExceptionHandler(ExceptionHandler.LOGONLY)
			.build();
		
		runner.start(iscfServiceOAMScenarioPositive);

 }
    
    // TODO
    // 1. Logger logger --> declare correctly
    // 2. System.out.println --> logger
    @Context(context = { Context.API })
    @Test(enabled = true, priority = 2, groups = {"Acceptance"})
    @TestId(id = "TORF-46171_IscfServicePositive_02", title = "Verify IPSec Flow Positive")
    public void iscfServiceIPSecPositive() {
    	
		TestStepFlow iscfServiceIPSecFlowPositive = flow("Verify IPSec flow positive")
			.withDataSources(dataSource("IscfService_OldInputs_IPSec"),
			                 dataSource("IscfService_NewInputs_and_ExpectedOutputs_IPSec"))
            .addTestStep(annotatedMethod(steps, "setUp"))
			.addTestStep(annotatedMethod(steps, "generate_IPSec_Iscf"))
            .addTestStep(annotatedMethod(steps, "tearDown"))
            .build();
 
		TestScenario iscfServiceIPSecScenarioPositive = scenario("Verify IPSec scenario positive")
			.addFlow(iscfServiceIPSecFlowPositive).build();
	
	 // TestScenarioRunner runner = runner().build();
		
		TestScenarioRunner runner = runner()
			.withListener(new LoggingScenarioListener())
		 // .withExceptionHandler(ExceptionHandler.LOGONLY)
			.build();
		
		runner.start(iscfServiceIPSecScenarioPositive);

 }
    
    // TODO
    // 1. Logger logger --> declare correctly
    // 2. System.out.println --> logger
    @Context(context = { Context.API })
    @Test(enabled = true, priority = 2, groups = {"Acceptance"})
    @TestId(id = "TORF-46171_IscfServicePositive_03", title = "Verify COMBO Flow Positive")
    public void iscfServiceCOMBOPositive() {
    	
		TestStepFlow iscfServiceCOMBOFlowPositive = flow("Verify COMBO flow positive")
			.withDataSources(dataSource("IscfService_OldInputs_COMBO"),
			                 dataSource("IscfService_NewInputs_and_ExpectedOutputs_COMBO"))
            .addTestStep(annotatedMethod(steps, "setUp"))
			.addTestStep(annotatedMethod(steps, "generate_COMBO_Iscf"))
            .addTestStep(annotatedMethod(steps, "tearDown"))
            .build();
 
		TestScenario iscfServiceCOMBOScenarioPositive = scenario("Verify COMBO scenario positive")
			.addFlow(iscfServiceCOMBOFlowPositive).build();
	
	 // TestScenarioRunner runner = runner().build();
		
		TestScenarioRunner runner = runner()
			.withListener(new LoggingScenarioListener())
		 // .withExceptionHandler(ExceptionHandler.LOGONLY)
			.build();
		
		runner.start(iscfServiceCOMBOScenarioPositive);

    }
}
