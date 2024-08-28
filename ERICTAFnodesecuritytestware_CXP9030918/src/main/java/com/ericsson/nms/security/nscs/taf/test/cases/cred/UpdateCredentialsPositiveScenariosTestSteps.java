/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.cred;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.operators.NodeSetParametersImpl;
import java.util.Arrays;
import javax.inject.Inject;
import org.apache.log4j.Logger;


/**
 *
 * @author enmadmin
 */
public class UpdateCredentialsPositiveScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(UpdateCredentialsPositiveScenariosTestSteps.class);
    
    @Inject
    private NodeSetParametersImpl nodeParameters;

    @TestStep(id = "credentialsUpdateOneNode")   
    public void credentialsUpdateOneNode(
            @Input("nodeList") final String nodeList,
			@Input("ipAddressList") final String ipAddressList) {

        final String[] nodeArray = nodeList.split(",");
        final int indexOfNodewithCredentialsExisting=5;
        String nodeName=nodeArray[indexOfNodewithCredentialsExisting];

        if(nodeParameters.checkSgsnForCurrentNode(nodeList)){
            //Update credentials
            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodeName, "su2", "sp2"));

            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodeName, "netsim", "netsim"));

            final String nodeNetworkElement = "NetworkElement=" + nodeName;

            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodeNetworkElement, "su2", "sp2"));

            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodeNetworkElement, "netsim", "netsim"));

            final String nodeMeContext = "MeContext=" + nodeName;

            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodeMeContext, "su2", "sp2"));

            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodeMeContext, "netsim", "netsim"));
        } else {
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

        
        

    }
    
    @TestStep(id = "credentialsUpdateListOfNodes")
    public void credentialsUpdateListOfNodes(
            @Input("nodeList") final String nodeList,
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
        
        
        if(nodeParameters.checkSgsnForCurrentNode(nodeList)){
    //Update credentials all params
            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodes, "su2", "sp2"));
            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodes, "netsim", "netsim"));

            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodesWithNetworkElement, "su2", "sp2"));
            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodesWithNetworkElement, "netsim", "netsim"));

            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodesWithMeContext, "su2", "sp2"));
            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodesWithMeContext, "netsim", "netsim"));

     //mix of nodename, networkElement and MeContext

            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodeswithMixofnodeNameANdMeContextAndNetworkElement, "su2", "sp2"));
            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodeswithMixofnodeNameANdMeContextAndNetworkElement, "netsim", "netsim"));

     //with only one param to be changed
            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodes, "su2", null));
            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodes, "netsim", null));
//
//
//            //with multiple param to be changed
//            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsForSgsn(nodes, "ru2", "rp2", null, null, null, null));
//            assertCredentialsUpdated(getCredentialsOperator().updateCredentialsWithFileForSgsn(nodes, "netsim", "netsim", null, null, null, null));
        } else {
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
}
