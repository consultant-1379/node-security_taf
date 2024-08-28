/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import org.apache.log4j.Logger;

/**
 *
 * @author enmadmin
 */
public class DeleteNodesNeededForSecadmCredentialsTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(DeleteNodesNeededForSecadmCredentialsTestSteps.class);
    
    @TestStep(id = "deleteNodesNeededForSecadmTest")
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
