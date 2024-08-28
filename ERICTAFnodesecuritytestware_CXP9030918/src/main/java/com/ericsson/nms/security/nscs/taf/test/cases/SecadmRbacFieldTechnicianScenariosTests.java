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
public class SecadmRbacFieldTechnicianScenariosTests extends NodeSecurityTestCaseHelper {
    
    @Inject
    SecadmRbacFieldTechnicianScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_RoleBasedAccessControl_03", title = "Secadm Rbac Field Technician")
    @Context(context = {Context.REST})
    public void secadmRbacFieldTechnician_positive() {

        TestStepFlow secadmRbacFieldTechnicianCreation = flow("")
            .addTestStep(annotatedMethod(steps, "verifyCannotUseSecadmWhenFieldTechnicianUserRole"))
            .build();
        
        TestScenario scenario = scenario("Secadm Rbac Field Technician scenario")
            .addFlow(secadmRbacFieldTechnicianCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
}
