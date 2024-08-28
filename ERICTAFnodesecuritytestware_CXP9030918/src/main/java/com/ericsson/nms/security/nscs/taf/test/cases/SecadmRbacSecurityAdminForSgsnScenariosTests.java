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
public class SecadmRbacSecurityAdminForSgsnScenariosTests extends NodeSecurityTestCaseHelper {

    @Inject
    SecadmRbacSecurityAdminScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_RoleBasedAccessControlSgsn_02", title = "Secadm Rbac Security Admin for Sgsn node")
    @Context(context = {Context.REST})
    public void secadmRbacSecurityAdmin_positive() {

        TestStepFlow secadmRbacSecurityAdminCreationForSgsn = flow("")
            .addTestStep(annotatedMethod(steps, "verifyCannotUseSecadmWhenSecurityAdminUserRoleForSgsn"))
            .build();
        
        TestScenario scenario = scenario("Secadm Rbac Security Admin scenario")
            .addFlow(secadmRbacSecurityAdminCreationForSgsn).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
}
