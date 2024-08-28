package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.SecurityCommandsOperator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;

public class SecurityLevelErrorScenariosTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger
            .getLogger(SecurityLevelErrorScenariosTest.class);

    @Test(groups={"Acceptance"})
    public void securityLevelErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesNotExist(){

        logger.info("############# Starting test securityLevelErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesNotExist #############");
        System.out.println(" \n\n ############# Starting test securityLevelErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesNotExist ############# \n\n");

        String nodeName="nonExistingNode";

        String expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + "NetworkElement=" + nodeName;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2" ,nodeName ),
                expectedError, expectedSuggestedSolution);

        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        nodeName="NetworkElement=" + "nonExistingNode";
        expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + nodeName;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2" ,nodeName ),
                expectedError, expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);


        nodeName="MeContext=" + "nonExistingNode";
        expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + nodeName;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2" ,nodeName ),
                expectedError, expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);
    }


    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelErrorResponsesWhenMeContextExistsAndNetworkElementDoesNotExist(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        logger.info("############# Starting test securityLevelErrorResponsesWhenMeContextExistsAndNetworkElementDoesNotExist #############");
        System.out.println(" \n\n ############# Starting test securityLevelErrorResponsesWhenMeContextExistsAndNetworkElementDoesNotExist ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_DOES_NOT_EXIST];

        String expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + "NetworkElement=" + nodeName;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2" ,nodeName ),
                expectedError, expectedSuggestedSolution);

        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        nodeName="NetworkElement=" + nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_DOES_NOT_EXIST];
        expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + nodeName;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2" ,nodeName ),
                expectedError, expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);


        nodeName="MeContext=" + nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_DOES_NOT_EXIST];

        expectedError=NscsServiceGetter.ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_ENSURE_THE_NODE_SPECIFIED_IS_SYNCHRONIZED;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        expectedError=NscsServiceGetter.ERROR_10007_THE_NETWORK_ELEMENT_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_NETWORK_ELEMENT_MO_AND_ANY_OTHER_REQUIRED_MOS_FOR_THE_NODE;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2" ,nodeName ),
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "2", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

    }


    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesExist(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        logger.info("############# Starting test securityLevelErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesExist #############");
        System.out.println(" \n\n ############# Starting test securityLevelErrorResponsesWhenMeContextDoesNotExistAndNetworkElementDoesExist ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS];

        String expectedError=NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO;
        String expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_THE_ME_CONTEXT_CORRESPONDING_TO_THE_SPECIFIED_MO;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2" ,nodeName ),
                expectedError, expectedSuggestedSolution);

        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        nodeName="NetworkElement=" + nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS];

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2", nodeName),
                expectedError, expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);


        nodeName="MeContext=" + nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS];

        expectedError=NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST + nodeName;
        expectedSuggestedSolution=NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "2" ,nodeName ),
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "2", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

    }

    //TODO

    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelErrorResponsesWhenNodeIsNotSynchronised(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        logger.info("############# Starting test securityLevelErrorResponsesWhenNodeIsNotSynchronised #############");
        System.out.println(" \n\n ############# Starting test securityLevelErrorResponsesWhenNodeIsNotSynchronised ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_EXISTS_NOT_SYNCHRONISED];

        final String expectedError = NscsServiceGetter.ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED;
        final String expectedSuggestedSolution = NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_ENSURE_THE_NODE_SPECIFIED_IS_SYNCHRONIZED;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST,nodeName),
                expectedError,expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "1", nodeName),
                expectedError, expectedSuggestedSolution);


        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeName);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        final String nodeNameWithMeContext="MeContext=" + nodeName;

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_NODELIST, nodeNameWithMeContext),
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_NODELIST, "1", nodeNameWithMeContext),
                expectedError, expectedSuggestedSolution);


        fileContents=createByteArrayWithNodeNamesForSecadmCommand(nodeNameWithMeContext);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_GET_SL_WITH_FILE, fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);

        executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(String.format(NscsServiceGetter.SECADM_SET_SL_WITH_FILE, "1", fileName),
                fileName, fileContents,
                expectedError, expectedSuggestedSolution);


    }

    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelSetErrorResponsesWhenNodeIsSynchronisedAndNetworkElementSecurityDoesNotExist(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        logger.info("############# Starting test securityLevelErrorResponsesWhenNodeIsSynchronisedAndNetworkElementSecurityDoesNotExist #############");
        System.out.println(" \n\n ############# Starting test securityLevelErrorResponsesWhenNodeIsSynchronisedAndNetworkElementSecurityDoesNotExist ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_DOES_NOT_EXIST];

        executeSecurityLevelSetCommandAndVerifyResponses (nodeName,"1",
                NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE);

        final String nodeNameWithMeContext="MeContext=" + nodeName;

        executeSecurityLevelSetCommandAndVerifyResponses (nodeNameWithMeContext,"1",
                NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE);

        final String nodeNameWithNetworkElement="NetworkElement=" + nodeName;

        executeSecurityLevelSetCommandAndVerifyResponses (nodeNameWithNetworkElement,"1",
                NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE);

    }


    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelErrorResponsesWhenDuplicateNodes(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_DOES_NOT_EXIST];

        String duplicateNodeList=nodeName + "," + nodeName;

        logger.info("############# Starting test securityLevelErrorResponsesWhenDuplicateNodes #############");
        System.out.println(" \n\n ############# Starting test securityLevelErrorResponsesWhenDuplicateNodes ############# \n\n");

        executeSecurityLevelGetCommandAndVerifyResponses(duplicateNodeList,
                NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND);

        executeSecurityLevelSetCommandAndVerifyResponses(duplicateNodeList, "1",
                NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND);

        duplicateNodeList="MeContext=" + nodeName + "," + "MeContext=" + nodeName;

        executeSecurityLevelGetCommandAndVerifyResponses(duplicateNodeList,
                NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND);

        executeSecurityLevelSetCommandAndVerifyResponses(duplicateNodeList, "1",
                NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND);

        duplicateNodeList="NetworkElement=" + nodeName + "," + "NetworkElement=" + nodeName;

        executeSecurityLevelGetCommandAndVerifyResponses(duplicateNodeList,
                NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND);

        executeSecurityLevelSetCommandAndVerifyResponses(duplicateNodeList, "1",
                NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND);

        duplicateNodeList="MeContext=" + nodeName + "," + "NetworkElement=" + nodeName;

        executeSecurityLevelGetCommandAndVerifyResponses(duplicateNodeList,
                NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND);

        executeSecurityLevelSetCommandAndVerifyResponses (duplicateNodeList,"1",
                NscsServiceGetter.ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND);


    }


    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelSetErrorWhenNodeAlreadyAtRequestedSecurityLevel(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test securityLevelSetErrorWhenNodeAlreadyAtRequestedSecurityLevel ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[5];

        final String secadmLevel1Format=convertCmeditLevelFormatToSecadmLevelFormat(NscsServiceGetter.LEVEL_1);

        final SecurityCommandsOperator securityCommandsOperator = getSecurityCommandsOperator();

        String httpResponseBody = securityCommandsOperator.getSecurityLevel(nodeName).getBody();
        Assert.assertEquals(httpResponseBody.contains(secadmLevel1Format),true,"Test Setup Problem: Node not at expected Security Level :" + NscsServiceGetter.LEVEL_1);

        httpResponseBody = securityCommandsOperator.setSecurityLevel(nodeName,"1").getBody();

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10011_THE_NODE_SPECIFIED_IS_ALREADY_AT_THE_REQUESTED_SECURITY_LEVEL,
                NscsServiceGetter.SECADM_SL_SET_NODE_ALREADY_AT_REQUESTED_LEVEL_SUGGESTED_SOLUTION);

        //TODO Get a Node that is at SL2 already and execute on that one when we can set nodes to sl2 E2E
//        httpResponseBody = securityCommandsOperator.setSecurityLevel(nodeName, "2").getBody();
//        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
//                NscsServiceGetter.ERROR_10011_THE_NODE_SPECIFIED_IS_ALREADY_AT_THE_REQUESTED_SECURITY_LEVEL,
//                NscsServiceGetter.SECADM_SL_SET_NODE_ALREADY_AT_REQUESTED_LEVEL_SUGGESTED_SOLUTION);

    }




    @Test(groups={"Acceptance"})
    public void securityLevelErrorUsingEmptyFile() {

        System.out.println(" \n\n ############# Starting test securityLevelErrorUsingEmptyFile ############# \n\n");

        final byte[] fileContents = {};
        final String fileName = "file:SecadmInputFile.txt";

        String commandString = "secadm sl get "+
                " --nodefile " + fileName;

        String httpResponseBody = executeScriptEngineCommandWithFile(commandString, fileName, fileContents);

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE);

        commandString = "secadm sl set --level 1"+
                " --nodefile " + fileName;

        httpResponseBody = executeScriptEngineCommandWithFile(commandString, fileName, fileContents);

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE);

    }


    @Test(groups={"Acceptance"})
    public void securityLevelFileWithNodesWithInvalidDataDelimitersComma( )
    {

        System.out.println(" \n\n ############# Starting test securityLevelFileWithNodesWithInvalidDataDelimitersComma ############# \n\n");

        String nodeList="somenode1,somenode2,somenode3";
        //replace comma with $ to make content invalid
        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeList.replace(",","$").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Assert.fail("Problem with file encoding");
        }

        String commandString = "secadm sl get "+
                " --nodefile " + fileName;

        String httpResponseBody = executeScriptEngineCommandWithFile(commandString, fileName, fileContents);

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE);

        commandString = "secadm sl set --level 1"+
                " --nodefile " + fileName;

        httpResponseBody = executeScriptEngineCommandWithFile(commandString, fileName, fileContents);

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                NscsServiceGetter.ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE);

    }




    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelSetErrorWhenNodeAlreadyInWorkflow(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test securityLevelSetErrorWhenNodeAlreadyInWorkflow ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[6];

        String httpResponseBody = getSecurityCommandsOperator().setSecurityLevel(nodeName,"2").getBody();
        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG),true,"Test Case Failure : Message received does not match expected " + NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG);

        executeSecurityLevelSetCommandAndVerifyResponses(nodeName, "2",
                NscsServiceGetter.ERROR_10015_THE_NODE_SPECIFIED_IS_ALREADY_INVOLVED_IN_A_SECURITY_CONFIGURATION_CHANGE,
                NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_WAIT_UNTIL_THE_CONFIGURATION_CHANGE_IS_COMPLETED_AND_RERUN_COMMAND);
    }


    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelErrorWhenMultipleNodesWithMultipleErrors(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList)
    {
        //TODO just use subset of list that have errors
        System.out.println(" \n\n ############# Starting test securityLevelSetErrorWhenMultipleNodesWithMultipleErrors ############# \n\n");

        final SecurityCommandsOperator securityCommandsOperator = getSecurityCommandsOperator();
        String httpResponseBody = securityCommandsOperator.getSecurityLevel(nodeList + ",someNode").getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED);

        //execute set sl1 on all nodes now to check the error messages
        httpResponseBody = securityCommandsOperator.setSecurityLevel(nodeList,"1").getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10011_THE_NODE_SPECIFIED_IS_ALREADY_AT_THE_REQUESTED_SECURITY_LEVEL.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10011_THE_NODE_SPECIFIED_IS_ALREADY_AT_THE_REQUESTED_SECURITY_LEVEL);
        //TODO
        //Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10015_THE_NODE_SPECIFIED_IS_ALREADY_INVOLVED_IN_A_SECURITY_CONFIGURATION_CHANGE),"Error message when node already in workflow not correct" + NscsServiceGetter.ERROR_10015_THE_NODE_SPECIFIED_IS_ALREADY_INVOLVED_IN_A_SECURITY_CONFIGURATION_CHANGE);

        // Suggested Solutions and overall Error message and suggested solution
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED),"Test failure: the message should contain " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE),"Test failure: the message should contain " + NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE);


        //execute set sl1 on all nodes now to check the error messages
        httpResponseBody = securityCommandsOperator.setSecurityLevel(nodeList,"2").getBody();


        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED.split(":")[1].trim()),"Test Case Failure : Message received does not match expected " + NscsServiceGetter.ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED);
        //TODO
        //Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10015_THE_NODE_SPECIFIED_IS_ALREADY_INVOLVED_IN_A_SECURITY_CONFIGURATION_CHANGE),"Error message when node already in workflow not correct" + NscsServiceGetter.ERROR_10015_THE_NODE_SPECIFIED_IS_ALREADY_INVOLVED_IN_A_SECURITY_CONFIGURATION_CHANGE);

        // Suggested Solutions and overall Error message and suggested solution
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED),"Test failure: the message should contain " + NscsServiceGetter.ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED);
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE),"Test failure: the message should contain " + NscsServiceGetter.SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE);

    }


    @Test(groups = {"Acceptance"})
    public void securityLevelSetErrorWhenExceedingMaxSupportedNodes() {
        System.out.println(" \n\n ############# Starting test securityLevelSetErrorWhenExceedingMaxSupportedNodes ############# \n\n");
        String nodeList = "NodeName0";
        int MAXNODESUPPORTED = 160;
        for (int nodeNumber = 1; nodeNumber <= MAXNODESUPPORTED; nodeNumber++){
            nodeList +=  ",NodeName" + nodeNumber;
        }

        final String httpResponseBody=getSecurityCommandsOperator().setSecurityLevel(nodeList,"2").getBody();

        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SECADM_SL_SET_MAX_NODES_EXCEEDED),true,"Test Case Failure : Error message received does not match expected:: " + NscsServiceGetter.SECADM_SL_SET_MAX_NODES_EXCEEDED);
        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SECADM_SL_SET_MAX_NODES_EXCEEDED_SUGGESTED_SOLUTION),true,"Test Case Failure: Expected response not received:: " + NscsServiceGetter.SECADM_SL_SET_MAX_NODES_EXCEEDED_SUGGESTED_SOLUTION);
    }

    private void executeSecurityLevelGetCommandAndVerifyResponses(String nodeList, String expectedError, String expectedSuggestSolution)
    {

        String httpResponseBody = getSecurityCommandsOperator().getSecurityLevel(nodeList).getBody();

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                expectedError,
                expectedSuggestSolution);

    }

    private void executeSecurityLevelSetCommandAndVerifyResponses(String nodeList, String securityLevel, String expectedError, String expectedSuggestSolution)
    {
        String httpResponseBody = getSecurityCommandsOperator().setSecurityLevel(nodeList,securityLevel).getBody();

        verifyResponseContainsExactErrorAndSuggestedSolution(httpResponseBody,
                expectedError,
                expectedSuggestSolution);

    }




}
