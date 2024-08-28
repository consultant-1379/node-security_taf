/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.CredentialsOperator;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class CredentialsPerformanceScenariosTestSteps extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(CredentialsPerformanceScenariosTestSteps.class);

    @TestStep(id = "credentialsCreateUpdateLargeNumberOfNodes")
    public void credentialsCreateUpdateLargeNumberOfNodes() {
        System.out.println(" \n\n ############# Starting test credentialsCreateLargeNumberOfNodes ############# \n\n");

        final CredentialsOperator credentialsOperator = getCredentialsOperator();
        String nodeList = "";
        final Set<String> nodeSet = new HashSet();

        //Add 3 MOs required for create credentials to succeed
        //Build a nodelist to use in the create credentials command
        //Number of nodes to use
        final int numberToUse = NscsServiceGetter.NUMBER_OF_NODES_TO_USE_FOR_CREDENTIAL_PERFORMANCE_TEST;
        final String nodeNameStartsWith = "oasis_perf_node";

//        TODO If possible to delete all nodes with a filter
//        getNodeOperator().deleteNodesBasedOnFilter(nodeNameStartsWith);
        for (int i = 1; i <= numberToUse; i++) {
            String nodeName = nodeNameStartsWith + i;
            nodeSet.add(nodeName);
            deleteNode(nodeName);
            createMeContextForTestSetup(nodeName);
            createNetworkElementForTestSetup(nodeName);
       /* ecappie's change: BEGIN... */
         // createSecurityFunctionForTestSetup(nodeName);  // CANNOT BE USED
            logger.info("credentialsCreateUpdateLargeNumberOfNodes(): INTRODUCED createCppConnectivityInfoForTestSetup: Begin...");
         // Create an ipAddress for the nodeName being processed, to be given to the underlying CMEDIT_CREATE_CPP_CONNECTIVITY_INFO_COMMAND
         // Address range chosen for the 25 nodes: e.g.: "192.1.1.21" - "192.1.1.45"
            int baseAddressForPerformanceNodes = 20;  // 20
            Integer addressForPerformanceNodes = baseAddressForPerformanceNodes + i;  // 21 - 45
            final String nodeIpAddress = "192.1.1." + addressForPerformanceNodes.toString();  // ""192.1.1.21"
            logger.info("nodeName: " + nodeName + "; nodeIpAddress: " + nodeIpAddress);
            createCppConnectivityInfoForTestSetup(nodeName, nodeIpAddress);
            logger.info("credentialsCreateUpdateLargeNumberOfNodes(): INTRODUCED createCppConnectivityInfoForTestSetup:  ...End.");
       /* ecappie's change:  ...END */
            if (i == numberToUse) {
                nodeList = nodeList + nodeName;
            } else {
                nodeList = nodeList + nodeName + ",";
            }
        } // END for (int i = 1; i <= numberToUse; i++)

        //Create credentials towards number of nodes to use
        String httpResponseBody = credentialsOperator.createCredentials(nodeList,
            NscsServiceGetter.NETSIM_ROOT_USER_NAME,
            NscsServiceGetter.NETSIM_ROOT_PASSWORD,
            NscsServiceGetter.NETSIM_SECURE_PASSWORD,
            NscsServiceGetter.NETSIM_SECURE_PASSWORD,
            NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
            NscsServiceGetter.NETSIM_NORMAL_PASSWORD).getBody();

        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS), true, "Did not receive expected response from command ");

        //Check Network Element Security exist
        for (String nodeName : nodeSet) {
            final boolean credentialsExist = credentialsOperator.checkCredentialsExist(nodeName);
            Assert.assertTrue(credentialsExist, "Did not find Network Element Security MO for this node " + nodeName);
        }

        //Update credentials towards number of nodes to use
        httpResponseBody = credentialsOperator.updateCredentials(nodeList,
            NscsServiceGetter.NETSIM_ROOT_USER_NAME,
            NscsServiceGetter.NETSIM_ROOT_PASSWORD,
            NscsServiceGetter.NETSIM_SECURE_PASSWORD,
            NscsServiceGetter.NETSIM_SECURE_PASSWORD,
            NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
            NscsServiceGetter.NETSIM_NORMAL_PASSWORD).getBody();

        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SECADM_CREDENTIALS_UPDATE_SUCCESS), true, "Did not receive expected response from command ");

//        TODO If possible to delete all nodes with a filter
//        getNodeOperator().deleteNodesBasedOnFilter(nodeNameStartsWith);
        for (String nodeName : nodeSet) {
            deleteNode(nodeName);
        }

    }

    @TestStep(id = "credentialsCreateUpdateLargeNumberOfNodesForSgsn")
    public void credentialsCreateUpdateLargeNumberOfNodesForSgsn() {
        System.out.println(" \n\n ############# Starting test credentialsCreateLargeNumberOfNodesForSgsn ############# \n\n");

        final CredentialsOperator credentialsOperator = getCredentialsOperator();
        String nodeList = "";
        final Set<String> nodeSet = new HashSet();

        //Add 3 MOs required for create credentials to succeed
        //Build a nodelist to use in the create credentials command
        //Number of nodes to use
        final int numberToUse = NscsServiceGetter.NUMBER_OF_NODES_TO_USE_FOR_CREDENTIAL_PERFORMANCE_TEST;
        final String nodeNameStartsWith = "oasis_SGSN_perf_node";

//        TODO If possible to delete all nodes with a filter
//        getNodeOperator().deleteNodesBasedOnFilter(nodeNameStartsWith);
        for (int i = 1; i <= numberToUse; i++) {
            String nodeName = nodeNameStartsWith + i;
            nodeSet.add(nodeName);
            deleteNode(nodeName);
            createMeContextForTestSetup(nodeName);
            createNetworkElementForTestSetup(nodeName);
       /* teigrul's change: BEGIN... */
         // createSecurityFunctionForTestSetup(nodeName);  // CANNOT BE USED
            logger.info("credentialsCreateUpdateLargeNumberOfNodesForSgsn(): INTRODUCED createCppConnectivityInfoForTestSetup: Begin...");
         // Create an ipAddress for the nodeName being processed, to be given to the underlying CMEDIT_CREATE_CPP_CONNECTIVITY_INFO_COMMAND
         // Address range chosen for the 25 nodes: e.g.: "192.1.1.21" - "192.1.1.45"
            int baseAddressForPerformanceNodes = 20;  // 20
            Integer addressForPerformanceNodes = baseAddressForPerformanceNodes + i;  // 21 - 45
            final String nodeIpAddress = "192.1.1." + addressForPerformanceNodes.toString();  // ""192.1.1.21"
            logger.info("nodeName: " + nodeName + "; nodeIpAddress: " + nodeIpAddress);
            createCppConnectivityInfoForTestSetup(nodeName, nodeIpAddress);
            logger.info("credentialsCreateUpdateLargeNumberOfNodesForSgsn(): INTRODUCED createCppConnectivityInfoForTestSetup:  ...End.");
       /* teigrul's change:  ...END */
            if (i == numberToUse) {
                nodeList = nodeList + nodeName;
            } else {
                nodeList = nodeList + nodeName + ",";
            }
        }

        //Create credentials towards number of nodes to use
        String httpResponseBody = credentialsOperator.createCredentialsForSgsn(nodeList,
            NscsServiceGetter.NETSIM_SECURE_PASSWORD,
            NscsServiceGetter.NETSIM_SECURE_PASSWORD).getBody();

        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS), true, "Did not receive expected response from command ");

        //Check Network Element Security exist
        for (String nodeName : nodeSet) {
            final boolean credentialsExist = credentialsOperator.checkCredentialsExist(nodeName);
            Assert.assertTrue(credentialsExist, "Did not find Network Element Security MO for this node " + nodeName);
        }

        //Update credentials towards number of nodes to use
        httpResponseBody = credentialsOperator.updateCredentialsForSgsn(nodeList,
            NscsServiceGetter.NETSIM_SECURE_PASSWORD,
            NscsServiceGetter.NETSIM_SECURE_PASSWORD).getBody();

        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SECADM_CREDENTIALS_UPDATE_SUCCESS), true, "Did not receive expected response from command ");

//        TODO If possible to delete all nodes with a filter
//        getNodeOperator().deleteNodesBasedOnFilter(nodeNameStartsWith);
        for (String nodeName : nodeSet) {
            deleteNode(nodeName);
        }

    }
    
}
