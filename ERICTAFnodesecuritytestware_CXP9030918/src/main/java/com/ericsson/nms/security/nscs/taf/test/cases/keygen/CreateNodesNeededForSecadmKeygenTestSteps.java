/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;

/**
 *
 * @author enmadmin
 */
public class CreateNodesNeededForSecadmKeygenTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(CreateNodesNeededForSecadmKeygenTestSteps.class);
    
    final boolean introduceDelay=false;       // Sometimes the server responses are unreliable so as to not send too many commands too often introduce a delay between some activities
    final int delayInMilliseconds = 3000;
    
    public static final String KEYGEN_OPERATOR = "keygen_operator";

    @TestStep(id = "createNodesNeededForSecadmKeygenTest")
    public void createNodesNeededForSecadmKeygenTest(
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test createNodesNeededForSecadmKeygenTest ############# \n\n");
        logger.info(" \n\n ############# Starting test createNodesNeededForSecadmKeygenTest ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String[] ipArray = ipAddressList.split(",");
        
    	//Delete Total Number of Nodes Nodes if they are already added
      for (String nodeName : nodeArray) {
          deleteNode(nodeName);
          if (introduceDelay) { delay(delayInMilliseconds);}
      }
        
//        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
//        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation(KEYGEN_OPERATOR);
        
        Map<String, String> nodeAndDescription = new TreeMap<>();

        createSgsnNode(nodeArray, ipArray, nodeAndDescription);
        
        System.out.println("created sgsn nodes");
        logger.info("created sgsn nodes");
        
        createOtherNode(nodeArray, ipArray, nodeAndDescription);
        
        System.out.println("created other nodes");
        logger.info("created other nodes");        
        
        printMap(nodeAndDescription, "nodeAndDescription");
        
//        getLoginOperator().deleteUser();
    }
    
    private void createSgsnNode(String[] nodeArray, String[] ipArray, Map<String, String> nodeAndDescription) {

        //Create MeContext for All Nodes
        for (int i = 0; i < NscsServiceGetter.END_INDEXOF_SGSN_NODE; i++) {
            createMeContextForTestSgsnSetup(nodeArray[i]);
        }
        System.out.println("Created MeContext for SGSN keygen");
        logger.info("Created MeContext for SGSN keygen");
        
        
        //Create Network Element MO on all nodes except one
        for (int i = 1; i < NscsServiceGetter.END_INDEXOF_SGSN_NODE; i++) {
        	createNetworkElementForTestSgsnSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
        }
        System.out.println("Created NetworkElement for SGSN keygen");
        logger.info("Created NetworkElement for SGSN keygen");
        

        for (int i = 2; i < NscsServiceGetter.END_INDEXOF_SGSN_NODE; i++) {
        	createComConnectivityInfo(nodeArray[i],ipArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and ComConnectivityInformation exist" );
        }
        System.out.println("Created ComConnectivityInfo for SGSN keygen");
        logger.info("Created ComConnectivityInfo for SGSN keygen");

        for (int i = 3; i < NscsServiceGetter.END_INDEXOF_SGSN_NODE; i++) {
            createCredentialsForSgsnTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement, ComConnectivityInformation and NetworkElementSecurity exist");
        }
        System.out.println("Created Credentials for SGSN keygen");
        logger.info("Created Credentials for SGSN keygen");
        
    }
    
    private void createOtherNode(String[] nodeArray, String[] ipArray, Map<String, String> nodeAndDescription) {

        //Create MeContext for All Nodes
        for (int i = NscsServiceGetter.END_INDEXOF_SGSN_NODE; i < nodeArray.length; i++) {
            createMeContextForTestSetup(nodeArray[i]);
        }        
        System.out.println("Created CPP MeContext for SGSN keygen");
        logger.info("Created CPP MeContext for SGSN keygen");
        

        //Create Network Element MO on all nodes except one
        for (int i = NscsServiceGetter.END_INDEXOF_SGSN_NODE; i < nodeArray.length; i++) {
        	createNetworkElementForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
        }
        System.out.println("Created CPP NetworkElement for SGSN keygen");
        logger.info("Created CPP NetworkElement for SGSN keygen");

        for (int i = NscsServiceGetter.END_INDEXOF_SGSN_NODE; i < nodeArray.length; i++) {
        	createCppConnectivityInfoForTestSetup(nodeArray[i],ipArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement and CppConnectivityInformation exist" );
        }
        System.out.println("Created CPP CppConnectivityInfo for SGSN keygen");
        logger.info("Created CPP CppConnectivityInfo for SGSN keygen");

        for (int i = NscsServiceGetter.END_INDEXOF_SGSN_NODE; i < nodeArray.length; i++) {
        	createCredentialsForTestSetup(nodeArray[i]);
            if (introduceDelay) { delay(delayInMilliseconds);}
            nodeAndDescription.put(nodeArray[i], "MeContext, NetworkElement, CppConnectivityInformation and NetworkElementSecurity exist");
        }
        System.out.println("Created Credentials for SGSN keygen to CPP nodes");
        logger.info("Created Credentials for SGSN keygen to CPP nodes");
    }
    
}
