package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Create7NodesOneOfEachVersion extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(Create7NodesOneOfEachVersion.class);

    @DataDriven(name = "7_Node_Versions_List")
//    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void create7NodesOneOfEachVersionTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test create7NodesOneOfEachVersionTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String[] ipArray = ipAddressList.split(",");

        //Delete Total Number of Nodes Nodes if they are already added
        for (String nodeName : nodeArray) {
            deleteNode(nodeName);
        }

        for (int i = 0; i < nodeArray.length; i++) {
            createMeContextForTestSetup(nodeArray[i]);
            createNetworkElementForTestSetup(nodeArray[i]);
            createCppConnectivityInfoForTestSetup(nodeArray[i], ipArray[i]);
        }

        //Created the previous MOs first on all nodes ato give mediation flows chance to create the system functions before trying to use the system function MO or create children of them
        for (int i = 0; i < nodeArray.length; i++) {

            setAlarmSupervisionOnForTestSetup(nodeArray[i]);
            createCredentialsForTestSetup(nodeArray[i]);
            getSyncNodeOperator().orderSynchroniseNode(nodeArray[i]);
        }

   //     Check the synch was completed
        for (int i = 0; i < nodeArray.length; i++) {
            Assert.assertTrue(getSyncNodeOperator().checkNodeIsSynchronisedOrTimeout(nodeArray[i], 10, 12), "Test Setup Problem. Problem synchronising  node " + nodeArray[i]);

        }
    }
}


