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
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class TrustDistributeSyntaxErrorScenariosTests extends NodeSecurityTestCaseHelper {
    
    @Inject
    TrustDistributeSyntaxErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-46030_ErrorScenarios_01", title = "TRust distribute syntax error")
    @Context(context = {Context.REST})
    public void trustDistrSyntaxError_positive() {

        TestStepFlow trustDistrSyntaxErrorCreation = flow("")
            .withDataSources(dataSource("TrustDistrSyntaxErrors"))
            .addTestStep(annotatedMethod(steps, "trustDistrSyntaxError"))
            .build();

        TestScenario scenario = scenario("trust distribution syntax error scenario")
            .addFlow(trustDistrSyntaxErrorCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
}
