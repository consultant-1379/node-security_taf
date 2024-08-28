package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class DeleteNodesNeededForSecadmCredentialsTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(DeleteNodesNeededForSecadmCredentialsTest.class);

    @DataDriven(name = "Complete_Node_List_Credentials")
    @Test(groups = {"Acceptance"})
    public void deleteNodesNeededForSecadmTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test deleteNodesNeededForSecadmTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String[] ipArray = ipAddressList.split(",");

        //Delete Total Number of Nodes Nodes if they are already added
        for (String nodeName : nodeArray) {
            deleteNode(nodeName);
        }
    }

}


