/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

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
public class KeygenCreateCheckDpsScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    KeygenCreateCheckDpsScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-51129_PositiveScenarios_03", title = "Keygen Check Dps Keypair successful")
    @Context(context = {Context.REST})
    public void certificateCheckDps() {

        TestStepFlow certificateCheckDpsTest = flow("")
            .withDataSources(dataSource("Complete_Node_List_Keygen"))
            .addTestStep(annotatedMethod(steps, "certificateIssueCheckDpsTest"))
            .build();

        TestScenario scenario = scenario("Keygen create CheckDps scenario")
            .addFlow(certificateCheckDpsTest).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }
    
}
