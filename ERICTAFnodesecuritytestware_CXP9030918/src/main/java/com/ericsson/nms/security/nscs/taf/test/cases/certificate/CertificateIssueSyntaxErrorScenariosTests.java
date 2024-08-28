/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.certificate;

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
public class CertificateIssueSyntaxErrorScenariosTests extends NodeSecurityTestCaseHelper {
    
    @Inject
    CertificateIssueSyntaxErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-51129_ErrorScenarios_01", title = "Certificate issue syntax error")
    @Context(context = {Context.REST})
    public void certificateSyntaxError_positive() {

        TestStepFlow certificateSyntaxErrorCreation = flow("")
            .withDataSources(dataSource("CertificateSyntaxErrors"))
            .addTestStep(annotatedMethod(steps, "certificateSyntaxError"))
            .build();

        TestScenario scenario = scenario("certificate syntax error scenario")
            .addFlow(certificateSyntaxErrorCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
}
