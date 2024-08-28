package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.SecurityCommandsOperator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public class SecurityLevelErrorScenariosNoNodesTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger
            .getLogger(SecurityLevelErrorScenariosNoNodesTest.class);

    @Test(groups={"Acceptance"})
    public void securityLevelErrorGetAllWhenNoNodesExist() {

        System.out.println(" \n\n ############# Starting test securityLevelErrorGetAllWhenNoNodesExist ############# \n\n");
        logger.info("############# Starting test securityLevelErrorGetAllWhenNoNodesExist #############");

        Set<String> nodesAtASpecificSecurityLevelUsingCmedit = getAllNodesAtASpecificSecurityLevelUsingCmedit(NscsServiceGetter.LEVEL_ALL);
        logger.info("nodesAtASpecificSecurityLevelUsingCmedit.toString()" + nodesAtASpecificSecurityLevelUsingCmedit.toString());
        Assert.assertTrue( nodesAtASpecificSecurityLevelUsingCmedit.size() == 0, "Test Setup Problem : Expected no nodes exist but nodes found in the system.");

        final SecurityCommandsOperator securityCommandsOperator = getSecurityCommandsOperator();
        String httpResponseBody = securityCommandsOperator.getSecurityLevelForAllNodes().getBody();
        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.NO_NODES_WITH_SECURITY_MO_FOUND), true, "Test Case Failure : Error message received does not match expected "
                + NscsServiceGetter.NO_NODES_WITH_SECURITY_MO_FOUND);

    }

    @Test(groups={"Acceptance"})
    public void securityLevelErrorGetAllWhenNoNodesExistAtLevel2AndRequestNodesAtLevel2()
    {

        System.out.println(" \n\n ############# Starting test securityLevelErrorGetAllWhenNoNodesExistAndLevelSpecified ############# \n\n");
        logger.info("############# Starting test securityLevelErrorGetAllWhenNoNodesExistAndLevelSpecified #############");

        Set<String> nodesAtASpecificSecurityLevelUsingCmedit = getAllNodesAtASpecificSecurityLevelUsingCmedit(NscsServiceGetter.LEVEL_2);
        Assert.assertTrue(nodesAtASpecificSecurityLevelUsingCmedit.size() == 0, "Test Setup Failure : Expected no nodes to be at Security Lvel 2 but nodes found in the system at that level.");

        final SecurityCommandsOperator securityCommandsOperator = getSecurityCommandsOperator();
        String httpResponseBody = securityCommandsOperator.getSecurityLevelForAllNodes("2").getBody();

        Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.NO_NODES_FOUND_AT_REQUESTED_SECURITY_LEVEL), true, "Test Case Failure : Error message received does not match expected "
                + NscsServiceGetter.NO_NODES_FOUND_AT_REQUESTED_SECURITY_LEVEL);
    }

}
