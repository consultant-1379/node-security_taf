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
public class TrustDistributeNodeNotExistErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    TrustDistributeNodeNotExistErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-46030_ErrorScenarios_03", title = "Trust Distribute: MO Does Not Exist")
    @Context(context = {Context.REST})
    public void trustDistrNodeNotExistError() {

        TestStepFlow trustDistributeNodeNotExistErrorTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Trust_Distribute_NetSim"))
            .addTestStep(annotatedMethod(steps, "trustDistributeNodeNotExistErrorTest"))
            .build();

        TestScenario scenario = scenario("Trust distribute node not exist error scenario")
            .addFlow(trustDistributeNodeNotExistErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
