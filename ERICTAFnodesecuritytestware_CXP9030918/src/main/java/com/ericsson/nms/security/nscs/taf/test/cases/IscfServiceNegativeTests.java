package com.ericsson.nms.security.nscs.taf.test.cases;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.annotatedMethod;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.dataSource;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.flow;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;

import javax.inject.Inject;

import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.TestScenario;
import com.ericsson.cifwk.taf.scenario.TestScenarioRunner;
import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;

public class IscfServiceNegativeTests extends NodeSecurityTestCaseHelper {

    @Inject
	IscfServicePositiveTestSteps steps;
    
//	@Context(context = { Context.REST, Context.API })
//	@Context(context = { Context.API, Context.REST })
//	@Context(context = { Context.REST })
//	@Context(context = { Context.API })
    
    /* FOR BACKWARD COMPATIBILITY ONLY ... */
	/* TODO x3 */
	
    // TODO
    // 1. Logger logger --> declare correctly
    // 2. System.out.println --> logger
    @Context(context = { Context.API })
    @Test(enabled = true, priority = 2, groups = {"Acceptance"})
    @TestId(id = "TORF-46171_IscfServiceNegative_01", title = "Verify OAM Flow")

    public void iscfServiceOAMPositive() {
    	
		TestStepFlow iscfServiceOAMFlowNegative = flow("Verify OAM flow negative")
            .addTestStep(annotatedMethod(steps, "setUp"))
			.addTestStep(annotatedMethod(steps, "generate_OAM_Iscf"))
            .addTestStep(annotatedMethod(steps, "tearDown"))
			.withDataSources(dataSource("IscfService_OldInputs_OAM"),
			                 dataSource("IscfService_NewInputs_and_ExpectedOutputs_OAM"))
            .build();
 
		TestScenario iscfServiceOAMScenarioNegative = scenario("Verify OAM scenario negative")
			.addFlow(iscfServiceOAMFlowNegative).build();
	
	 // TestScenarioRunner runner = runner().build();
		
		TestScenarioRunner runner = runner()
			.withListener(new LoggingScenarioListener())
		 // .withExceptionHandler(ExceptionHandler.LOGONLY)
			.build();
		
		runner.start(iscfServiceOAMScenarioNegative);

 }

}
