package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.UnsupportedEncodingException;

public class CredentialsErrorScenariosTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger
            .getLogger(CredentialsErrorScenariosTest.class);

    @Test(groups={"Acceptance"})
    public void credentialsErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesNotExist(){

        logger.info("############# Starting test credentialsErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesNotExist #############");
        System.out.println(" \n\n ############# Starting test credentialsErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesNotExist ############# \n\n");

        String nodeName="nonExistingNode";

        String expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + "NetworkElement=" + nodeName;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST, nodeName),
                expectedError, expectedSuggestedSolution);


        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        nodeName="MeContext=" + "nonExistingNode";
        expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + nodeName;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST, nodeName),
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST, nodeName),
                expectedError, expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        nodeName="NetworkElement=" + "nonExistingNode";
        expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + nodeName;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST, nodeName),
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST, nodeName),
                expectedError, expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);


        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);


    }


    @DataDriven(name = "Complete_Node_List_Credentials")
    @Test(groups={"Acceptance"})
    public void credentialsErrorResponsesWhenMeContextExistsAndNetworkElementDoesNotExist(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        logger.info("############# Starting test credentialsErrorResponsesWhenMeContextExistsAndNetworkElementDoesNotExist #############");
        System.out.println(" \n\n ############# Starting test credentialsErrorResponsesWhenMeContextExistsAndNetworkElementDoesNotExist ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_DOES_NOT_EXIST];

        String expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + "NetworkElement=" + nodeName;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);


        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);


        nodeName="NetworkElement=" + nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_DOES_NOT_EXIST];

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        nodeName="MeContext=" + nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_DOES_NOT_EXIST];
        expectedError=NscsServiceGetter.ERROR_10007_THE_NETWORK_ELEMENT_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_NETWORK_ELEMENT_MO_AND_ANY_OTHER_REQUIRED_MOS_FOR_THE_NODE;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);



    }

    @DataDriven(name = "Complete_Node_List_Credentials")
    @Test(groups={"Acceptance"})
    public void credentialsErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesExist(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        logger.info("############# Starting test credentialsErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesExist #############");
        System.out.println(" \n\n ############# Starting test credentialsErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesExist ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS];

        String expectedError=NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_ME_CONTEXT_CORRESPONDING_TO_THE_SPECIFIED_MO;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);


        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        nodeName="NetworkElement=" + nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS];

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST, nodeName),
                expectedError, expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);


        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);


        nodeName="MeContext=" + nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS];
        expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + nodeName;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

    }


    @DataDriven(name = "Complete_Node_List_Credentials")
    @Test(groups={"Acceptance"})
    public void credentialsErrorResponsesWhenSecurityFunctionDoesNotExist(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        logger.info("############# Starting test credentialsErrorResponsesWhenSecurityFunctionDoesNotExist #############");
        System.out.println(" \n\n ############# Starting test credentialsErrorResponsesWhenSecurityFunctionDoesNotExist ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_EXISTS_NOT_SYNCHRONISED];
        //3rd Node in list used for this test as it has no SecurityFunction MO

        String expectedError=NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_SECURITY_FUNCTION_MO;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        expectedError=NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        final String nodeNameWithNetworkElement="NetworkElement=" + nodeName;

        expectedError=NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_SECURITY_FUNCTION_MO;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeNameWithNetworkElement),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeNameWithNetworkElement);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);


        expectedError=NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeNameWithNetworkElement),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        final String nodeNameWithMeContext="MeContext=" + nodeName;

        expectedError=NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_SECURITY_FUNCTION_MO;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeNameWithMeContext),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeNameWithMeContext);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        expectedError=NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST, nodeNameWithMeContext),
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);



    }


    @DataDriven(name = "Complete_Node_List_Credentials")
    @Test(groups={"Acceptance"})
    public void credentialsUpdateErrorResponsesWhenCredentialsDoesNotExist(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        logger.info("############# Starting test credentialsUpdateErrorResponsesWhenCredentialsDoesNotExist #############");
        System.out.println(" \n\n ############# Starting test credentialsUpdateErrorResponsesWhenCredentialsDoesNotExist ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_EXISTS_NOT_SYNCHRONISED];
        //Nodes created and setup for all tests in advance to reduce need for deleting/ creating/ synching numerous times
        //3rd Node in list used for this test as it has no NetworkElementSecurity MO


        String expectedError=NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        final String nodeNameWithNetworkElement="NetworkElement=" + nodeName;
                fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeNameWithNetworkElement);

                executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeNameWithNetworkElement),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        final String nodeNameWithMeContext="MeContext=" + nodeName;
        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeNameWithMeContext);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,nodeNameWithMeContext),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

    }


    @DataDriven(name = "Complete_Node_List_Credentials")
    @Test(groups={"Acceptance"})
    public void credentialsCreateErrorResponsesWhenCredentialsAlreadyExist(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test credentialsCreateErrorResponsesWhenCredentialsAlreadyExist ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        final String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST];
        //Nodes created and setup for all tests in advance to reduce need for deleting/ creating/ synching numerous times
        //6rd Node in list used for this test as it has no SecurityFunction MO

        String expectedError=NscsServiceGetter.ERROR_10009_THE_NODE_SPECIFIED_ALREADY_HAS_CREDENTIALS_DEFINED;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SPECIFY_NODES_WITHOUT_EXISTING_CREDENTIALS_DEFINED;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        final String nodeNameWithNetworkElement="NetworkElement=" + nodeName;

              executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeNameWithNetworkElement),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeNameWithNetworkElement);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        final String nodeNameWithMeContext="MeContext=" + nodeName;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,nodeNameWithMeContext),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeNameWithMeContext);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

    }

    @DataDriven(name = "Complete_Node_List_Credentials")
    @Test(groups={"Acceptance"})
    public void credentialsErrorResponsesWhenDuplicateNodes(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST];

        String expectedError=NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND;

        String duplicateNodeList=nodeName + "," + nodeName;

        logger.info("############# Starting test credentialsErrorResponsesWhenDuplicateNodes #############");
        System.out.println(" \n\n ############# Starting test credentialsErrorResponsesWhenDuplicateNodes ############# \n\n");

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,duplicateNodeList),
                expectedError,expectedSuggestedSolution);

        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(duplicateNodeList);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);


        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,duplicateNodeList),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        duplicateNodeList="NetworkElement=" + nodeName + "," + "NetworkElement=" + nodeName;


        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,duplicateNodeList),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(duplicateNodeList);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST,duplicateNodeList),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        duplicateNodeList="MeContext=" + nodeName + "," + "NetworkElement=" + nodeName;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_NODELIST,duplicateNodeList),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(duplicateNodeList);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsErrorAndSuggestedSolution(String.format(NscsServiceGetter.CREATE_CREDENTIALS_WITH_FILE,fileName),
                fileName, fileContents,
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_NODELIST, duplicateNodeList),
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsErrorAndSuggestedSolution(String.format(NscsServiceGetter.UPDATE_CREDENTIALS_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);



    }


    @Test(groups={"Acceptance"})
    public void credentialsErrorUsingEmptyFile() {

        System.out.println(" \n\n ############# Starting test credentialsErrorUsingEmptyFile ############# \n\n");

        final byte[] fileContents = {};
        final String fileName = "file:SecadmInputFile.txt";

        String commandString = "secadm credentials create"+
                " --rootusername "+ NscsServiceGetter.NETSIM_ROOT_USER_NAME +
                " --rootuserpassword "+ NscsServiceGetter.NETSIM_ROOT_PASSWORD +
                " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
                " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
                " --normaluserpassword "+NscsServiceGetter.NETSIM_NORMAL_PASSWORD +
                " --normalusername "+NscsServiceGetter.NETSIM_NORMAL_USER_NAME +
                " --nodefile " + fileName;

        String httpResponseBody = executeScriptEngineCommandWithFile(commandString, fileName, fileContents);

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE);

        commandString = "secadm credentials update"+
                " --rootusername "+ NscsServiceGetter.NETSIM_ROOT_USER_NAME +
                " --rootuserpassword "+ NscsServiceGetter.NETSIM_ROOT_PASSWORD +
                " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
                " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
                " --normaluserpassword "+NscsServiceGetter.NETSIM_NORMAL_PASSWORD +
                " --normalusername "+NscsServiceGetter.NETSIM_NORMAL_USER_NAME +
                " --nodefile " + fileName;

        httpResponseBody = executeScriptEngineCommandWithFile(commandString, fileName, fileContents);

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE);

    }



    @Test(groups={"Acceptance"})
    public void credentialsErrorFileWithNodesWithInvalidDataDelimitersComma( )
    {

        System.out.println(" \n\n ############# Starting test credentialsErrorFileWithNodesWithInvalidDataDelimitersComma ############# \n\n");

        String nodeList="somenode1,somenode2,somenode3";
        //replace comma with $ to make content invalid
        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeList.replace(",","$").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Assert.fail("Problem with file encoding");
        }

        final String fileName = "file:SecadmInputFile.txt";

        String command = "secadm credentials create"+
                " --rootusername "+ NscsServiceGetter.NETSIM_ROOT_USER_NAME +
                " --rootuserpassword "+ NscsServiceGetter.NETSIM_ROOT_PASSWORD +
                " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
                " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
                " --normaluserpassword "+NscsServiceGetter.NETSIM_NORMAL_PASSWORD +
                " --normalusername "+NscsServiceGetter.NETSIM_NORMAL_USER_NAME +
                " --nodefile " + fileName;

        String httpResponseBody=executeScriptEngineCommandWithFile(command,fileName,fileContents);

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE);

        command = "secadm credentials update"+
                " --rootusername "+ NscsServiceGetter.NETSIM_ROOT_USER_NAME +
                " --rootuserpassword "+ NscsServiceGetter.NETSIM_ROOT_PASSWORD +
                " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
                " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
                " --normaluserpassword "+NscsServiceGetter.NETSIM_NORMAL_PASSWORD +
                " --normalusername "+NscsServiceGetter.NETSIM_NORMAL_USER_NAME +
                " --nodefile " + fileName;

        httpResponseBody=executeScriptEngineCommandWithFile(command,fileName,fileContents);

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE);

    }

    @DataDriven(name = "Complete_Node_List_Credentials")
    @Test(groups={"Acceptance"})
    public void credentialsMultipleErrorsOnMultipleNodes(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test credentialsMultipleErrorsOnMultipleNodes ############# \n\n");

        //execute create credentials on all nodes now to check the error messages
        String httpResponseBody = getCredentialsOperator().createCredentials(nodeList,NscsServiceGetter.NETSIM_ROOT_USER_NAME,
                NscsServiceGetter.NETSIM_ROOT_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
                NscsServiceGetter.NETSIM_NORMAL_PASSWORD).getBody();

        //TODO when error messages are finalised in production code
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10009_THE_NODE_SPECIFIED_ALREADY_HAS_CREDENTIALS_DEFINED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10009_THE_NODE_SPECIFIED_ALREADY_HAS_CREDENTIALS_DEFINED);

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);


        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeList);
        final String fileName = "file:SecadmInputFile.txt";

        String commandString = "secadm credentials create"+
                " --rootusername "+ NscsServiceGetter.NETSIM_ROOT_USER_NAME +
                " --rootuserpassword "+ NscsServiceGetter.NETSIM_ROOT_PASSWORD +
                " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
                " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
                " --normaluserpassword "+NscsServiceGetter.NETSIM_NORMAL_PASSWORD +
                " --normalusername "+NscsServiceGetter.NETSIM_NORMAL_USER_NAME +
                " --nodefile " + fileName;


        httpResponseBody=executeScriptEngineCommandWithFile(commandString,fileName,fileContents);

        //TODO when error messages are finalised in production code
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10009_THE_NODE_SPECIFIED_ALREADY_HAS_CREDENTIALS_DEFINED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10009_THE_NODE_SPECIFIED_ALREADY_HAS_CREDENTIALS_DEFINED);

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);


        //execute create credentials on all nodes now to check the error messages
        httpResponseBody = getCredentialsOperator().updateCredentials(nodeList,NscsServiceGetter.NETSIM_ROOT_USER_NAME,
                NscsServiceGetter.NETSIM_ROOT_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
                NscsServiceGetter.NETSIM_NORMAL_PASSWORD).getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST.split(":")[1].trim()),"Error message when node does not exist not correct");
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);


        commandString = "secadm credentials update"+
                " --rootusername "+ NscsServiceGetter.NETSIM_ROOT_USER_NAME +
                " --rootuserpassword "+ NscsServiceGetter.NETSIM_ROOT_PASSWORD +
                " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
                " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
                " --normaluserpassword "+NscsServiceGetter.NETSIM_NORMAL_PASSWORD +
                " --normalusername "+NscsServiceGetter.NETSIM_NORMAL_USER_NAME +
                " --nodefile " + fileName;

        httpResponseBody=executeScriptEngineCommandWithFile(commandString,fileName,fileContents);

        //TODO when error messages are finalised in production code
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST.split(":")[1].trim()),"Error message when node does not exist not correct");
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);

    }

}
