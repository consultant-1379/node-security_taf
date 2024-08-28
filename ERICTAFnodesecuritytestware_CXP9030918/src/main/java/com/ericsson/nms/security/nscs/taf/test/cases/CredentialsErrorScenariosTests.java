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
import com.ericsson.cifwk.taf.scenario.api.DataSourceDefinitionBuilder;
import javax.inject.Inject;
import org.testng.annotations.Test;

/**
 *
 * @author enmadmin
 */
public class CredentialsErrorScenariosTests extends NodeSecurityTestCaseHelper {
   
    @Inject
    CredentialsErrorScenariosTestSteps steps;

    @Test(enabled = true, priority = 1, groups = {"Acceptance"})
    @TestId(id = "TORF-44067_ErrorScenarios_03", title = "Credentials error")
    @Context(context = {Context.REST})
    public void credentialsError_positive() {

        TestStepFlow credentialsErrorCreation = flow("")
            .withDataSources(dataSource("Complete_Node_List_Credentials"))
            .addTestStep(annotatedMethod(steps, "credentialsErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesNotExist"))
            .addTestStep(annotatedMethod(steps, "credentialsErrorResponsesWhenMeContextExistsAndNetworkElementDoesNotExist"))
            .addTestStep(annotatedMethod(steps, "credentialsErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesExist"))
            .addTestStep(annotatedMethod(steps, "credentialsErrorResponsesWhenSecurityFunctionDoesNotExist"))
            .addTestStep(annotatedMethod(steps, "credentialsUpdateErrorResponsesWhenCredentialsDoesNotExist"))            
            .addTestStep(annotatedMethod(steps, "credentialsCreateErrorResponsesWhenCredentialsAlreadyExist"))            
            .addTestStep(annotatedMethod(steps, "credentialsErrorResponsesWhenDuplicateNodes"))  
            .addTestStep(annotatedMethod(steps, "credentialsErrorUsingEmptyFile"))  
            .addTestStep(annotatedMethod(steps, "credentialsErrorFileWithNodesWithInvalidDataDelimitersComma")) 
            .addTestStep(annotatedMethod(steps, "credentialsMultipleErrorsOnMultipleNodes")) 
            .build();

        TestScenario scenario = scenario("Credentials error scenario")
            .addFlow(credentialsErrorCreation).build();

        TestScenarioRunner runner = runner().build();

        runner.start(scenario);
    }

    
}
