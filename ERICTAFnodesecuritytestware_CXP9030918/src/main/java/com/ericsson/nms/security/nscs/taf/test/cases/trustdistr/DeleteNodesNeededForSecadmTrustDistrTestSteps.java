/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.trustdistr;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;

import org.apache.log4j.Logger;

/**
 *
 * @author enmadmin
 */
public class DeleteNodesNeededForSecadmTrustDistrTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(DeleteNodesNeededForSecadmTrustDistrTestSteps.class);
    
    @TestStep(id = "deleteNodesNeededForSecadmTrustDistrTest")
    public void deleteNodesNeededForSecadmTrustDistrTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList) {

        System.out.println(" \n\n ############# Starting test deleteNodesNeededForSecadmTrustDistrTest ############# \n\n");
        logger.info(" \n\n ############# Starting test deleteNodesNeededForSecadmTrustDistrTest ############# \n\n");
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(NscsServiceGetter.CERTISSUE_USER_NAME);

        final String[] nodeArray = nodeList.split(",");
//        final String[] ipArray = ipAddressList.split(",");

        //Delete Total Number of Nodes Nodes if they are already added
        for (String nodeName : nodeArray) {
            deleteNode(nodeName);
        }
        
//        getLoginOperator().deleteUser();
    }
}
