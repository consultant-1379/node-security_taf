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
public class SecadmRbacOperatorForSgsnScenariosTests  extends NodeSecurityTestCaseHelper{

    @Inject
    SecadmRbacOperatorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_RoleBasedAccessControlSgsn_01", title = "Secadm Rbac Operator for Sgsn node")
    @Context(context = {Context.REST})
    public void secadmRbacOperatorForSgsn_positive() {

        TestStepFlow secadmRbacOperatorCreationForSgsn = flow("")
            .addTestStep(annotatedMethod(steps, "verifyCannotUseSecadmWhenOperatorUserRoleForSgsn"))
            .build();
        
        TestScenario scenario = scenario("Secadm Rbac Operator scenario")
            .addFlow(secadmRbacOperatorCreationForSgsn).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
}
