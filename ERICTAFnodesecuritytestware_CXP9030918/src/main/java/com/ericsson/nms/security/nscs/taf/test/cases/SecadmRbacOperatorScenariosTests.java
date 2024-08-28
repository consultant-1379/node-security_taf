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
public class SecadmRbacOperatorScenariosTests  extends NodeSecurityTestCaseHelper{

    @Inject
    SecadmRbacOperatorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_RoleBasedAccessControl_01", title = "Secadm Rbac Operator")
    @Context(context = {Context.REST})
    public void secadmRbacOperator_positive() {

        TestStepFlow secadmRbacOperatorCreation = flow("")
//            .withDataSources(dataSource("CredMngrCliPositiveScenario"))
            .addTestStep(annotatedMethod(steps, "verifyCannotUseSecadmWhenOperatorUserRole"))
            .build();
        
        TestScenario scenario = scenario("Secadm Rbac Operator scenario")
            .addFlow(secadmRbacOperatorCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
}
