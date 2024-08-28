/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.nms.security.nscs.taf.test.cases.cred;

import com.ericsson.cifwk.taf.TestCase;
import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.testng.annotations.Test;

import java.util.Arrays;

public class UpdateCredentialsPositiveScenariosTest extends NodeSecurityTestCaseHelper implements TestCase {

    @TestId(id = "update-creds-positive-1", title ="Verify credentials updated successfully when passed node name on it's own")
    @DataDriven(name = "Complete_Node_List_Credentials")
	@Test(groups = { "Acceptance" })
	public void credentialsUpdateOneNode(@Input("nodeList") final String nodeList,
			@Input("ipAddressList") final String ipAddressList) {

        final String[] nodeArray = nodeList.split(",");
        final int indexOfNodewithCredentialsExisting=5;
        String nodeName=nodeArray[indexOfNodewithCredentialsExisting];

		//Update credentials
		assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodeName, "ru2", "rp2", "su2", "sp2", "nu2", "np2"));

        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodeName, "netsim", "netsim", "netsim", "netsim", "netsim", "netsim"));

        final String nodeNetworkElement = "NetworkElement=" + nodeName;

        assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodeNetworkElement, "ru2", "rp2", "su2", "sp2", "nu2", "np2"));

        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodeNetworkElement, "netsim", "netsim", "netsim", "netsim", "netsim", "netsim"));

        final String nodeMeContext = "MeContext=" + nodeName;

        assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodeMeContext, "ru2", "rp2", "su2", "sp2", "nu2", "np2"));

        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodeMeContext, "netsim", "netsim", "netsim", "netsim", "netsim", "netsim"));

    }


    @TestId(id = "update-creds-positive-2", title ="Verify credentials updated successfully when passed list of nodes with all params to be changed")
    @DataDriven(name = "Complete_Node_List_Credentials")
	@Test(groups = { "Acceptance" })
	public void credentialsUpdateListOfNodes(@Input("nodeList") final String nodeList,
			@Input("ipAddressList") final String ipAddressList) {

        final String[] nodeArray = nodeList.split(",");
        final int startIndexOfNodewithCredentialsExisting=5;
        final int endIndexOfNodewithCredentialsExisting=8;

        String[] multipleNodes = Arrays.copyOfRange(nodeArray, startIndexOfNodewithCredentialsExisting, endIndexOfNodewithCredentialsExisting);

        if (multipleNodes.length < 3){
            throw new RuntimeException("Need at least 3 nodes for this test");
        }

        String nodeswithMixofnodeNameANdMeContextAndNetworkElement =
                multipleNodes[0] + "," +
                        "NetworkElement=" + multipleNodes[1] + "," +
                        "MeContext=" + multipleNodes[2];

        String nodes="";
        String nodesWithNetworkElement="";
        String nodesWithMeContext="";

        for (int i=0 ; i<multipleNodes.length;i++){
            String nodeName = multipleNodes[i];
            if (i==multipleNodes.length-1 ) {
                nodes = nodes + nodeName;
                nodesWithNetworkElement= nodesWithNetworkElement + "NetworkElement=" + nodeName;
                nodesWithMeContext= nodesWithMeContext + "MeContext=" + nodeName;

            }else {
                nodes = nodes + nodeName + ",";
                nodesWithNetworkElement= nodesWithNetworkElement + "NetworkElement=" + nodeName + ",";
                nodesWithMeContext= nodesWithMeContext + "MeContext=" + nodeName + ",";
            }
        }

//Update credentials all params
		assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodes, "ru2", "rp2", "su2", "sp2", "nu2", "np2"));
        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodes, "netsim", "netsim", "netsim", "netsim", "netsim", "netsim"));

        assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodesWithNetworkElement, "ru2", "rp2", "su2", "sp2", "nu2", "np2"));
        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodesWithNetworkElement, "netsim", "netsim", "netsim", "netsim", "netsim", "netsim"));

        assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodesWithMeContext, "ru2", "rp2", "su2", "sp2", "nu2", "np2"));
        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodesWithMeContext, "netsim", "netsim", "netsim", "netsim", "netsim", "netsim"));

 //mix of nodename, networkElement and MeContext

        assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodeswithMixofnodeNameANdMeContextAndNetworkElement, "ru2", "rp2", "su2", "sp2", "nu2", "np2"));
        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodeswithMixofnodeNameANdMeContextAndNetworkElement, "netsim", "netsim", "netsim", "netsim", "netsim", "netsim"));

 //with only one param to be changed
        assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodes, "ru2", null, null, null, null, null));
        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodes, "netsim", null, null, null, null, null));


        //with multiple param to be changed
        assertCredentialsUpdated(getCredentialsOperator().updateCredentials(nodes, "ru2", "rp2", null, null, null, null));
        assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFile(nodes, "netsim", "netsim", null, null, null, null));

    }

}
