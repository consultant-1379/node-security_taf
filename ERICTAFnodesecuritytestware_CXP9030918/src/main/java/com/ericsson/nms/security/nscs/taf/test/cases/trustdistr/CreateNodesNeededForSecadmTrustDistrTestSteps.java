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

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class CreateNodesNeededForSecadmTrustDistrTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(CreateNodesNeededForSecadmTrustDistrTestSteps.class);
    
    private final boolean introduceDelay=false;       // Sometimes the server responses are unreliable so as to not send too many commands too often introduce a delay between some activities
    private final int delayInMilliseconds = 3000;
    
//    private static final int END_INDEXOF_CPP_COM = 20;

    @TestStep(id = "createNodesNeededForSecadmTrustDistrTest")
    public void createNodesNeededForSecadmTrustDistrTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test createNodesNeededForSecadmTrustDistrTest ############# \n\n");
        logger.info(" \n\n ############# Starting test createNodesNeededForSecadmTrustDistrTest ############# \n\n");
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(NscsServiceGetter.CERTISSUE_USER_NAME);
        
    	final String[] nodeArray = nodeList.split(",");
    	final String[] ipArray = ipAddressList.split(",");

    	Map<String, String> nodeAndDescription = new TreeMap<>();

    	//Delete Total Number of Nodes Nodes if they are already added
        for (String nodeName : nodeArray) {
            deleteNode(nodeName);
            if (introduceDelay) { delay(delayInMilliseconds);}
        }

    	createCertifiableNodes(nodeArray, ipArray, nodeAndDescription);

    	logger.info("Created Certifiable Nodes For Secadm Trust Distribute");

    	createUncertifiableNodes(nodeArray, ipArray, nodeAndDescription);

    	logger.info("Created UnCertifiable Nodes For Secadm Trust Distribute");

    	printMap(nodeAndDescription, "nodeAndDescription");
        
//    	getLoginOperator().deleteUser();
    }
    
    private void createCertifiableNodes(String[] nodeArray, String[] ipArray, Map<String, String> nodeAndDescription) {
    	
    	//Create MeContext for All Nodes with index [0:END_INDEXOF_CPP_COM_FOR_TRUST_DISTRIBUTE]
        for (int i = 0; i <= NscsServiceGetter.END_INDEXOF_CPP_FOR_TRUST_DISTRIBUTE; i++) {
        	createMeContextForTestSetup(nodeArray[i]);
        }
        
        //Create NetworkElements for nodes with index [1:END_INDEXOF_CPP_COM_FOR_TRUST_DISTRIBUTE]
        for (int i = 1; i <= NscsServiceGetter.END_INDEXOF_CPP_FOR_TRUST_DISTRIBUTE; i++) {
        	createNetworkElementForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
        }

        //Create CppConnectivityInfo for nodes with index [2:END_INDEXOF_CPP_COM_FOR_TRUST_DISTRIBUTE]
        for (int i = 2; i <= NscsServiceGetter.END_INDEXOF_CPP_FOR_TRUST_DISTRIBUTE; i++) {
        	createCppConnectivityInfoForTestSetup(nodeArray[i],ipArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and CppConnectivityInfo exist" );
        }

        //and create NetworkElementSecurity for nodes with index [2:END_INDEXOF_CPP_COM_FOR_TRUST_DISTRIBUTE]
        for (int i = 2; i <= NscsServiceGetter.END_INDEXOF_CPP_FOR_TRUST_DISTRIBUTE; i++) {
        	createCredentialsForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement, CppConnectivityInfo and NetworkElementSecurity exist");
        }
        
    }
    
    private void createUncertifiableNodes(String[] nodeArray, String[] ipArray, Map<String, String> nodeAndDescription) {
    	
    	//Create MeContext for All Nodes with index [START_INDEXOF_COM_FOR_TRUST_DISTRIBUTE:nodeArray.length]
        for (int i = NscsServiceGetter.START_INDEXOF_COM_FOR_TRUST_DISTRIBUTE; i < nodeArray.length; i++) {
        	createMeContextForTestSgsnSetup(nodeArray[i]);
        }

        //Create NetworkElements for nodes with index [START_INDEXOF_COM_FOR_TRUST_DISTRIBUTE:nodeArray.length]
        for (int i = NscsServiceGetter.START_INDEXOF_COM_FOR_TRUST_DISTRIBUTE; i < nodeArray.length; i++) {
        	createNetworkElementForTestSgsnSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
        }
        
        //Create ComConnectivityInfo for nodes with index [START_INDEXOF_COM_FOR_TRUST_DISTRIBUTE:nodeArray.length]
        for (int i = NscsServiceGetter.START_INDEXOF_COM_FOR_TRUST_DISTRIBUTE; i < nodeArray.length; i++) {
        	createComConnectivityInfo(nodeArray[i],ipArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and ComConnectivityInfo exist" );
        }
        
    }
    
}
