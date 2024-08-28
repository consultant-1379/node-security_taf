/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.certificate;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.*;

import javax.inject.Inject;

import org.testng.annotations.Test;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.scenario.*;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;

/**
 *
 * @author enmadmin
 */
public class CreateNodesNeededForSecadmCertificateTests extends NodeSecurityTestCaseHelper {
    
    @Inject
    CreateNodesNeededForSecadmCertificateTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-51129_SetUpTestEnvironment", title = "Set up certificate issue test environment")
    @Context(context = {Context.REST})
    public void createNodesNeededForSecadmCertificate() {

        TestStepFlow createNodesNeededForSecadmCertificate = flow("")
            .withDataSources(dataSource("Complete_Node_List_Certificate_NetSim"))
            .addTestStep(annotatedMethod(steps, "createNodesNeededForSecadmCertificateTest"))
            .build();

        TestScenario scenario = scenario("Set up certificate issue test environment scenario")
            .addFlow(createNodesNeededForSecadmCertificate).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
}
