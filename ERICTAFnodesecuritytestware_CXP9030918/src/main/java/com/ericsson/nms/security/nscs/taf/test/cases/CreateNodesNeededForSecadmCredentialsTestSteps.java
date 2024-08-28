/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class CreateNodesNeededForSecadmCredentialsTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(CreateNodesNeededForSecadmCredentialsTestSteps.class);

    @TestStep(id = "createNodesNeededForSecadmCredentialsTest")
    public void createNodesNeededForSecadmCredentialsTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test createNodesNeededForSecadmCredentialsTest ############# \n\n");

        final boolean introduceDelay=false;       // Sometimes the server responses are unreliable so as to not send too many commands too often introduce a delay between some activities
        final int delayInMilliseconds = 3000;

        final String[] nodeArray = nodeList.split(",");
        final String[] ipArray = ipAddressList.split(",");

        Assert.assertTrue(nodeArray.length > NscsServiceGetter.NUMBER_OF_NODES_NEEDED_FOR_CREDENTIALS_TESTS,
                "Not enough nodes provided in csv file to meet number needed for tests : csv contains : " +
                        nodeArray.length + " : Required " + NscsServiceGetter.NUMBER_OF_NODES_NEEDED_FOR_CREDENTIALS_TESTS);

        Map<String, String> nodeAndDescription = new TreeMap<>();

        //Delete Total Number of Nodes Nodes if they are already added
        for (String nodeName : nodeArray) {
            deleteNode(nodeName);
        if (introduceDelay) { delay(delayInMilliseconds);}
        }

        //Create MeContext for All Nodes
        for (int i = 0; i < nodeArray.length; i++) {
            createMeContextForTestSetup(nodeArray[i]);

        }
        nodeAndDescription.put(nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_DOES_NOT_EXIST], "Only MeContext MO exists");

        //Create Network Element MO on all nodes except one
        for (int i = 1; i < nodeArray.length; i++) {
            createNetworkElementForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
        }
        //Delete the MeContext but leave NetworkElement
        deleteMeContextForTestSetup(nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS]);
        nodeAndDescription.put(nodeArray[NscsServiceGetter.INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS], "Only Network ElementMO Exists, No MeContext exists");


//        for (int i = 3; i < nodeArray.length; i++) {
//            createSecurityFunctionForTestSetup(nodeArray[i]);
//            if (introduceDelay) { delay(delayInMilliseconds);}
//            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and CppConnectivityInfo created, SecurityFunction and CmFunction should eb automatically created by system" );
//        }

        //        Create Ssystem Functions by creating CPPConnectivityInformation MO
        //        Create Credentials for remainder
        for (int i = 4; i < nodeArray.length; i++) {
            createCppConnectivityInfoForTestSetup(nodeArray[i], ipArray[i]);
            createCredentialsForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and SecurityFunction , NetworkElementSecurity exist");
        }
        printMap(nodeAndDescription, "nodeAndDescription");
    }
    
}
