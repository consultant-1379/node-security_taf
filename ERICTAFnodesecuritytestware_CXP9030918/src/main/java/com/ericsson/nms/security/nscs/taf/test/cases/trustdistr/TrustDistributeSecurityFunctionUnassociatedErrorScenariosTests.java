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
import com.ericsson.cifwk.taf.scenario.api.DataSourceDefinitionBuilder;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class TrustDistributeSecurityFunctionUnassociatedErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    TrustDistributeSecurityFunctionUnassociatedErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-46030_ErrorScenarios_04", title = "Trust Distribute Security Function Unassociated Network Error")
    @Context(context = {Context.REST})
    public void certificateSecurityFunctionUnassociatedError() {

        TestStepFlow trustDistributeSecurityFunctionUnassociatedErrorTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Trust_Distribute_NetSim"))
            .addTestStep(annotatedMethod(steps, "trustDistributeSecurityFunctionUnassociatedErrorTest"))
            .build();

        TestScenario scenario = scenario("Trust Distribute Security Function Unassociated Network error scenario")
            .addFlow(trustDistributeSecurityFunctionUnassociatedErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
