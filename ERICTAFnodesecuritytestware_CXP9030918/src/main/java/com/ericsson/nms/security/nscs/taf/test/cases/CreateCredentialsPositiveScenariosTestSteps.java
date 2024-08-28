/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.NodeSetParametersImpl;
import java.util.Arrays;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class CreateCredentialsPositiveScenariosTestSteps extends NodeSecurityTestCaseHelper {

    @Inject
    private NodeSetParametersImpl nodeParameters;

    private static Logger logger = Logger.getLogger(CreateCredentialsPositiveScenariosTestSteps.class);

    @TestStep(id = "credentialsCreateOneNodeUsingSupportedNodeNamesFromList")
    public void credentialsCreateOneNodeUsingSupportedNodeNamesFromList(
        @Input("nodeList") final String nodeList,
        @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test credentialsCreateOneNodeUsingSupportedNodeNames ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST];

        final String[] nodeNames = {nodeName, "NetworkElement=" + nodeName, "MeContext=" + nodeName};
        String createCredentialsCommand;

        for (String node : nodeNames) {

            deleteNetworkElementSecurity(nodeName);

            if(nodeParameters.checkSgsnForCurrentNode(node)){
               createCredentialsCommand = "secadm credentials create"
                    + " --secureusername " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " --secureuserpassword " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " --nodelist " + node;
            } else {
                createCredentialsCommand = "secadm credentials create"
                    + " --rootusername " + NscsServiceGetter.NETSIM_ROOT_USER_NAME
                    + " --rootuserpassword " + NscsServiceGetter.NETSIM_ROOT_PASSWORD
                    + " --secureusername " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " --secureuserpassword " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " --normaluserpassword " + NscsServiceGetter.NETSIM_NORMAL_PASSWORD
                    + " --normalusername " + NscsServiceGetter.NETSIM_NORMAL_USER_NAME
                    + " --nodelist " + node;
            }
 
            final String expectedResult = NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS;

            executeScriptEngineCommandAndVerifyResponseContainsExpectedContent(createCredentialsCommand, expectedResult);

            boolean credentialsExist = getCredentialsOperator().checkCredentialsExist(nodeName);
            Assert.assertEquals(credentialsExist, true, "Test Case Failure :  NetworkElementSecurity MO for " + nodeName + " does not exist after running create credentials");

          }
    }
    
     @TestStep(id = "credentialsCreateOneNodeUsingSupportedNodeNamesFromFile")
     public void credentialsCreateOneNodeUsingSupportedNodeNamesFromFile(
        @Input("nodeList") final String nodeList,
        @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test credentialsCreateOneNodeUsingSupportedNodeNames ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName = nodeArray[NscsServiceGetter.INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST];

        final String[] nodeNames = {nodeName, "NetworkElement=" + nodeName, "MeContext=" + nodeName};
 
        for (String node : nodeNames) {

            deleteNetworkElementSecurity(nodeName);

            final String fileName = "file:SecadmInputFile.txt";
            byte[] fileContents = createByteArrayWithNodeNamesForSecadmCommand(nodeName);
            String commandString;
    
            if(nodeParameters.checkSgsnForCurrentNode(node)){
                commandString = "secadm credentials create"
                    + " --secureuserpassword " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " --secureusername " + NscsServiceGetter.NETSIM_SECURE_USER_NAME
                    + " --nodefile " + fileName;
            } else {
                commandString = "secadm credentials create"
                    + " --rootusername " + NscsServiceGetter.NETSIM_ROOT_USER_NAME
                    + " --rootuserpassword " + NscsServiceGetter.NETSIM_ROOT_PASSWORD
                    + " --secureuserpassword " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " --secureusername " + NscsServiceGetter.NETSIM_SECURE_USER_NAME
                    + " --normaluserpassword " + NscsServiceGetter.NETSIM_NORMAL_PASSWORD
                    + " --normalusername " + NscsServiceGetter.NETSIM_NORMAL_USER_NAME
                    + " --nodefile " + fileName;
            }
       
            executeScriptEngineCommandWithFileAndVerifyResponseContainsExpectedContent(commandString, fileName, fileContents, NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS);

            boolean credentialsExist = getCredentialsOperator().checkCredentialsExist(nodeName);
            Assert.assertEquals(credentialsExist, true, "Test Case Failure :  NetworkElementSecurity MO for " + nodeName + " does not exist after running create credentials");
        }
    }

    @TestStep(id = "credentialsCreateMultipleNodeFromList")
    public void credentialsCreateMultipleNodeFromList(
        @Input("nodeList") final String nodeList,
        @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test credentialsCreateMultipleNode ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        String[] multipleNodes = Arrays.copyOfRange(nodeArray, NscsServiceGetter.START_INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST,
            NscsServiceGetter.END_INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST);

        if (multipleNodes.length < 3) {
            throw new RuntimeException("Need at least 3 nodes for this test");
        }

        String nodeswithMixofnodeNameANdMeContextAndNetworkElement
            = multipleNodes[0] + ","
            + "NetworkElement=" + multipleNodes[1] + ","
            + "MeContext=" + multipleNodes[2];

        String nodes = "";
        String nodesWithNetworkElement = "";
        String nodesWithMeContext = "";

        for (int i = 0; i < multipleNodes.length; i++) {
            String nodeName = multipleNodes[i];
            deleteNetworkElementSecurity(nodeName);
            if (i == multipleNodes.length - 1) {
                nodes = nodes + nodeName;
                nodesWithNetworkElement = nodesWithNetworkElement + "NetworkElement=" + nodeName;
                nodesWithMeContext = nodesWithMeContext + "MeContext=" + nodeName;

            } else {
                nodes = nodes + nodeName + ",";
                nodesWithNetworkElement = nodesWithNetworkElement + "NetworkElement=" + nodeName + ",";
                nodesWithMeContext = nodesWithMeContext + "MeContext=" + nodeName + ",";
            }
        }

        String[] nodeListDifferentFormat = {nodes, nodesWithNetworkElement, nodesWithMeContext, nodeswithMixofnodeNameANdMeContextAndNetworkElement};
        String createCredentialsCommand;

        for (String nodeListArgument : nodeListDifferentFormat) {

            System.out.println("Using : " + nodeListArgument);

            for (int i = 0; i < multipleNodes.length; i++) {
                String nodeName = multipleNodes[i];
                deleteNetworkElementSecurity(nodeName);
            }

            if(nodeParameters.checkSgsnForCurrentNode(nodeListArgument)){
                createCredentialsCommand = "secadm credentials create"
                    + " -sn " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " -sp " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " -n " + nodeListArgument;
            } else {
                createCredentialsCommand = "secadm credentials create"
                    + " -rn " + NscsServiceGetter.NETSIM_ROOT_USER_NAME
                    + " -rp " + NscsServiceGetter.NETSIM_ROOT_PASSWORD
                    + " -sn " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " -sp " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " -nn " + NscsServiceGetter.NETSIM_NORMAL_PASSWORD
                    + " -np " + NscsServiceGetter.NETSIM_NORMAL_USER_NAME
                    + " -n " + nodeListArgument;
            }
            


            final String expectedResult = NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS;

            System.out.println("Using : " + nodeListArgument);

            executeScriptEngineCommandAndVerifyResponseContainsExpectedContent(createCredentialsCommand, expectedResult);

            for (int i = 0; i < multipleNodes.length; i++) {
                String nodeName = multipleNodes[i];
                final boolean credentialsExist = getCredentialsOperator().checkCredentialsExist(nodeName);
                Assert.assertEquals(credentialsExist, true, "Test Case Failure :  NetworkElementSecurity MO for " + nodeName + " does not exist after running create credentials");
            }
        }
    }
    
    
    @TestStep(id = "credentialsCreateMultipleNodeFromFile")
    public void credentialsCreateMultipleNodeFromFile(
        @Input("nodeList") final String nodeList,
        @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test credentialsCreateMultipleNode ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        String[] multipleNodes = Arrays.copyOfRange(nodeArray, NscsServiceGetter.START_INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST,
            NscsServiceGetter.END_INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST);

        if (multipleNodes.length < 3) {
            throw new RuntimeException("Need at least 3 nodes for this test");
        }

        String nodeswithMixofnodeNameANdMeContextAndNetworkElement
            = multipleNodes[0] + ","
            + "NetworkElement=" + multipleNodes[1] + ","
            + "MeContext=" + multipleNodes[2];

        String nodes = "";
        String nodesWithNetworkElement = "";
        String nodesWithMeContext = "";

        for (int i = 0; i < multipleNodes.length; i++) {
            String nodeName = multipleNodes[i];
            deleteNetworkElementSecurity(nodeName);
            if (i == multipleNodes.length - 1) {
                nodes = nodes + nodeName;
                nodesWithNetworkElement = nodesWithNetworkElement + "NetworkElement=" + nodeName;
                nodesWithMeContext = nodesWithMeContext + "MeContext=" + nodeName;

            } else {
                nodes = nodes + nodeName + ",";
                nodesWithNetworkElement = nodesWithNetworkElement + "NetworkElement=" + nodeName + ",";
                nodesWithMeContext = nodesWithMeContext + "MeContext=" + nodeName + ",";
            }
        }

        String[] nodeListDifferentFormat = {nodes, nodesWithNetworkElement, nodesWithMeContext, nodeswithMixofnodeNameANdMeContextAndNetworkElement};

        for (String nodeListArgument : nodeListDifferentFormat) {

            for (int i = 0; i < multipleNodes.length; i++) {
                String nodeName = multipleNodes[i];
                deleteNetworkElementSecurity(nodeName);
            }

            final String fileName = "file:SecadmInputFile.txt";

            byte[] fileContents = createByteArrayWithNodeNamesForSecadmCommand(nodeListArgument);
            String commandString;

            if(nodeParameters.checkSgsnForCurrentNode(nodeListArgument)){
                commandString = "secadm credentials create"
                    + " --secureuserpassword " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " --secureusername " + NscsServiceGetter.NETSIM_SECURE_USER_NAME
                    + " --nodefile " + fileName;
            } else {
                commandString = "secadm credentials create"
                    + " --rootusername " + NscsServiceGetter.NETSIM_ROOT_USER_NAME
                    + " --rootuserpassword " + NscsServiceGetter.NETSIM_ROOT_PASSWORD
                    + " --secureuserpassword " + NscsServiceGetter.NETSIM_SECURE_PASSWORD
                    + " --secureusername " + NscsServiceGetter.NETSIM_SECURE_USER_NAME
                    + " --normaluserpassword " + NscsServiceGetter.NETSIM_NORMAL_PASSWORD
                    + " --normalusername " + NscsServiceGetter.NETSIM_NORMAL_USER_NAME
                    + " --nodefile " + fileName;
            }

            System.out.println("Using : " + nodeListArgument);

            executeScriptEngineCommandWithFileAndVerifyResponseContainsExpectedContent(commandString, fileName, fileContents, NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS);

            for (int i = 0; i < multipleNodes.length; i++) {
                String nodeName = multipleNodes[i];
                final boolean credentialsExist = getCredentialsOperator().checkCredentialsExist(nodeName);
                Assert.assertEquals(credentialsExist, true, "Test Case Failure :  NetworkElementSecurity MO for " + nodeName + " does not exist after running create credentials");
            }
        }
    }

}
