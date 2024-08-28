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
public class TrustDistributeInvalidArgumentErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    TrustDistributeInvalidArgumentErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-46030_ErrorScenarios_02", title = "Trust distribute invalid argument")
    @Context(context = {Context.REST})
    public void trustDistrInvalidArgumentError() {

        TestStepFlow trustDistrInvalidArgumentErrorTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Trust_Distribute"))
            .addTestStep(annotatedMethod(steps, "trustDistributeInvalidArgumentErrorTest"))
            .build();

        TestScenario scenario = scenario("Trust Distribute invalid argument error scenario")
            .addFlow(trustDistrInvalidArgumentErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
