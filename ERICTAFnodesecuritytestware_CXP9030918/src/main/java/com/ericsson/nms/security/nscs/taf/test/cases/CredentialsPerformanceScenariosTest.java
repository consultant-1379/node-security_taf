package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashSet;
import java.util.Set;

public class CredentialsPerformanceScenariosTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger
            .getLogger(CredentialsPerformanceScenariosTest.class);

    @Context(context = { Context.REST })
    @Test(groups={"Acceptance"})
    public void credentialsCreateUpdateLargeNumberOfNodes()
    {
        System.out.println(" \n\n ############# Starting test credentialsCreateLargeNumberOfNodes ############# \n\n");

        final CredentialsOperator credentialsOperator = getCredentialsOperator();
        String nodeList="";
        final Set<String> nodeSet= new HashSet();

        //Add 3 MOs required for create credentials to succeed
        //Build a nodelist to use in the create credentials command
        //Number of nodes to use
        final int numberToUse=NscsServiceGetter.NUMBER_OF_NODES_TO_USE_FOR_CREDENTIAL_PERFORMANCE_TEST;
        final String nodeNameStartsWith="oasis_perf_node";

//        TODO If possible to delete all nodes with a filter
//        getNodeOperator().deleteNodesBasedOnFilter(nodeNameStartsWith);

        for (int i=1;i<=numberToUse;i++) {
            String nodeName = nodeNameStartsWith + i;
            nodeSet.add(nodeName);
            deleteNode(nodeName);
            createMeContextForTestSetup(nodeName);
            createNetworkElementForTestSetup(nodeName);
            createSecurityFunctionForTestSetup(nodeName);
            if (i==numberToUse){
                nodeList=nodeList + nodeName;
            }else{
                nodeList=nodeList + nodeName + ",";
            }
        }

        //Create credentials towards number of nodes to use
        String httpResponseBody  = credentialsOperator.createCredentials(nodeList,
                NscsServiceGetter.NETSIM_ROOT_USER_NAME,
                NscsServiceGetter.NETSIM_ROOT_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
                NscsServiceGetter.NETSIM_NORMAL_PASSWORD).getBody();

         Assert.assertEquals(httpResponseBody.contains(NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS), true, "Did not receive expected response from command ");

        //Check Network Element Security exist
        for (String nodeName : nodeSet) {
            final boolean credentialsExist= credentialsOperator.checkCredentialsExist(nodeName);
            Assert.assertTrue(credentialsExist,"Did not find Network Element Security MO for this node " + nodeName);
        }

        //Update credentials towards number of nodes to use
        httpResponseBody  = credentialsOperator.updateCredentials(nodeList,
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

    //TODO
    //Create a test where huge number of nodes are added on server and then try and create credentials for them.




}

