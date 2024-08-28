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
import com.ericsson.cifwk.taf.scenario.api.DataSourceDefinitionBuilder;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;

import javax.inject.Inject;

import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class CertificateIssueXmlContentNotValidScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    CertificateIssueXmlContentNotValidScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-51129_ErrorScenarios_03", title = "Certificate Issue Xml content not valid")
    @Context(context = {Context.REST})
    public void certificateIssueXmlNotValidError() {

        TestStepFlow certificateIssueXmlNotValidErrorTest = flow("")
            .addTestStep(annotatedMethod(steps, "certificateIssueXmlNotValidErrorTest"))
            .build();

        TestScenario scenario = scenario("Certificate issue Xml Not Valid error scenario")
            .addFlow(certificateIssueXmlNotValidErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
