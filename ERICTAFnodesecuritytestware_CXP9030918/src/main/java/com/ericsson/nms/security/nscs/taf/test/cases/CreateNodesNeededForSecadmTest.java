package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.ResponseDtoWrapper;
import com.ericsson.nms.security.nscs.taf.test.operators.SecurityCommandsOperator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CreateNodesNeededForSecadmTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(CreateNodesNeededForSecadmTest.class);

    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void createNodesNeededForSecadmTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test createNodesNeededForSecadmTest ############# \n\n");

        final boolean introduceDelay=false;       // Sometimes the server responses are unreliable so as to not send too many commands too often introduce a delay between some activities
        final int delayInMilliseconds = 3000;

        final String[] nodeArray = nodeList.split(",");
        final String[] ipArray = ipAddressList.split(",");

        Assert.assertTrue(nodeArray.length > NscsServiceGetter.NUMBER_OF_NODES_NEEDED_FOR_TESTS,
                "Not enough nodes provided in csv file to meet number needed for tests : csv contains : " +
                        nodeArray.length + " : Required " + NscsServiceGetter.NUMBER_OF_NODES_NEEDED_FOR_TESTS);

        Map<String, String> nodeAndDescription = new TreeMap<>();

        //Delete Total Number of Nodes Nodes if they are already added
//        for (String nodeName : nodeArray) {
//
//            deleteNode(nodeName);
//        if (introduceDelay) { delay(delayInMilliseconds);}
//        }

        //Create MeContext for All Nodes
        for (int i = 0; i < nodeArray.length; i++) {
            createMeContextForTestSetup(nodeArray[i]);

        }
        nodeAndDescription.put(nodeArray[0], "Only MeContext MO exists");
        printMap(nodeAndDescription, "nodeAndDescription");

        //Create Network Element MO on all nodes except one
        for (int i = 1; i < nodeArray.length; i++) {
            createNetworkElementForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
        }
        //Delete the MeContext but leave NetworkElement
        deleteMeContextForTestSetup(nodeArray[1]);
        nodeAndDescription.put(nodeArray[1], "Only Network ElementMO Exists, No MeContext exists");
        printMap(nodeAndDescription, "nodeAndDescription");

        //nodeArray[2] has MeContext and NetworkElement MOs only
        nodeAndDescription.put(nodeArray[2], "Both MeContext and NetworkElement exist");
//TODO  nodeAndDescription.put(nodeArray[2],"Both MeContext and NetworkElement exist as well as all the systemc related child functions of NetworkElement");
        printMap(nodeAndDescription, "nodeAndDescription");


        //Create Security Functions , CmFunction, CppConnectivityInfo for remainder
        for (int i = 3; i < nodeArray.length; i++) {
          //  createSecurityFunctionForTestSetup(nodeArray[i]);
          //  createCmFunctionForTestSetup(nodeArray[i]);
            createCppConnectivityInfoForTestSetup(nodeArray[i], ipArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and CppConnectivityInfo created, SecurityFunction and CmFunction should eb automatically created by system" );

        }
        for (int i = 3; i < nodeArray.length; i++) {
            setAlarmSupervisionOnForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and SecurityFunction , CmFunction, CppConnectivityInfo and FmSupervision on ");

        }

        printMap(nodeAndDescription, "nodeAndDescription");

        //Store wrong password Credentials for 1 of the nodes (which will be used in set test cases).
        createCredentialsWithWrongPasswordForTestSetup(nodeArray[4]);
        nodeAndDescription.put(nodeArray[4], "MeContext, NetworkElement and SecurityFunction , CmFunction, CppConnectivityInfo and FmSupervision on NetworkElementSecurity MO exist with wrong password");
        printMap(nodeAndDescription, "nodeAndDescription");

        //Create Credentials for remainder
        for (int i = 5; i < nodeArray.length; i++) {
            createCredentialsForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
        }
        printMap(nodeAndDescription, "nodeAndDescription");

        //Order the Synch of the nodes that have cmfunction of the nodes
        for (int i = 3; i < nodeArray.length; i++) {
            getSyncNodeOperator().orderSynchroniseNode(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and SecurityFunction , NetworkElementSecurity and Sync ordered");
        }
        nodeAndDescription.put(nodeArray[4], "MeContext, NetworkElement and SecurityFunction , CmFunction, CppConnectivityInfo and and FmSupervision on and NetworkElementSecurity MO exist with wrong password, and Sync ordered");

        printMap(nodeAndDescription, "nodeAndDescription");

        //Check the synch was completed
        for (int i = 3; i < nodeArray.length; i++) {
            if (introduceDelay) { delay(delayInMilliseconds);}
            Assert.assertTrue(getSyncNodeOperator().checkNodeIsSynchronisedOrTimeout(nodeArray[i], 10, 12), "Test Setup Problem. Problem synchronising  node " + nodeArray[i]);
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and SecurityFunction ,CmFunction, CppConnectivityInfo and  NetworkElementSecurity and Synchronised");
        }
        nodeAndDescription.put(nodeArray[4], "MeContext, NetworkElement and SecurityFunction , CmFunction, CppConnectivityInfo and  FmSupervision on and NetworkElementSecurity MO exist with wrong password, and Syncronised");

        printMap(nodeAndDescription, "nodeAndDescription");

    }

//    @DataDriven(name = "Complete_Node_List")
//    @Test(groups = {"Acceptance"})
//    public void synchNodesNeededForSecadmTest(
//            @Input("nodeList") String nodeList,
//            @Input("ipAddressList") final String ipAddressList) {
//
//        System.out.println(" \n\n ############# Starting test synchNodesNeededForSecadmTest ############# \n\n");
//
//
//        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");
//
//
//        //Order the Synch of the nodes that have cmfunction of the nodes
//        for (int i = 3; i < nodeArray.length; i++) {
//            getSyncNodeOperator().orderSynchroniseNode(nodeArray[i]);
//        }
//
//        //Check the synch was completed
//        for (int i = 3; i < nodeArray.length; i++) {
//            Assert.assertTrue(getSyncNodeOperator().checkNodeIsSynchronisedOrTimeout(nodeArray[i], 10, 12), "Test Setup Problem. Problem synchronising  node " + nodeArray[i]);
//        }
//
//    }


}


