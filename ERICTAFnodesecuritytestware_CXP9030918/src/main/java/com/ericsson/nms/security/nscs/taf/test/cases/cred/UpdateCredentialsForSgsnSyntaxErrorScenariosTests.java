/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.cred;


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
public class UpdateCredentialsForSgsnSyntaxErrorScenariosTests extends NodeSecurityTestCaseHelper  {
    
    @Inject
    UpdateCredentialsSyntaxErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_ErrorScenariosSgsn_02", title = "Update credentials syntax error for Sgsn node")
    @Context(context = {Context.REST})
    public void updateCredentialsForSgsnSyntaxError_positive() {

        TestStepFlow updateCredentialsSyntaxErroCreationForSgsn = flow("")
            .withDataSources(dataSource("CredentialsUpdateSyntaxErrorsForSgsn"))
            .addTestStep(annotatedMethod(steps, "updateCredentialsSyntaxError"))
            .build();

        TestScenario scenario = scenario("Update credentials syntax error scenario")
            .addFlow(updateCredentialsSyntaxErroCreationForSgsn).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
       
}
