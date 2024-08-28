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
public class CertificateIssueSecurityFunctionUnassociatedErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    CertificateIssueSecurityFunctionUnassociatedErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-51129_ErrorScenarios_05", title = "Certificate Issue Security Function Unassociated Network Error")
    @Context(context = {Context.REST})
    public void certificateSecurityFunctionUnassociatedError() {

        TestStepFlow certificateIssueSecurityFunctionUnassociatedErrorTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Certificate_NetSim"))
            .addTestStep(annotatedMethod(steps, "certificateIssueSecurityFunctionUnassociatedErrorTest"))
            .build();

        TestScenario scenario = scenario("Certificate issue Security Function Unassociated Network error scenario")
            .addFlow(certificateIssueSecurityFunctionUnassociatedErrorTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
