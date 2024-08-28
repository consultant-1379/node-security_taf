package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.TreeMap;

public class SyncNodesNeededForSecadmTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(SyncNodesNeededForSecadmTest.class);

    //    @Context(context = { Context.REST })
    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void syncNodesNeededForSecadmTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test createNodesNeededForSecadmTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String[] ipArray = ipAddressList.split(",");


       //Order the Synch of the nodes that have cmfunction of the nodes
        for (int i = 3; i < nodeArray.length; i++) {
            getSyncNodeOperator().orderSynchroniseNode(nodeArray[i]);
        }

        //Check the synch was completed
        for (int i = 3; i < nodeArray.length; i++) {
            Assert.assertTrue(getSyncNodeOperator().checkNodeIsSynchronisedOrTimeout(nodeArray[i], 10, 12), "Test Setup Problem. Problem synchronising  node " + nodeArray[i]);
        }


    }
}


