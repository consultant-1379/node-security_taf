package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NeGroup;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.oss.netsim.operator.cm.NetSimCmOperatorImpl;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityLevelSetPositiveScenariosTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(SecurityLevelSetPositiveScenariosTest.class);

    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void securityLevelSetSl2OneNode(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test securityLevelSetSl2OneNode ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        String nodeName =  nodeArray[10];

        Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");

        //ENM MEdiation layer loses ability to receive notifications so this is a workaround
        getSyncNodeOperator().orderSynchroniseNode(nodeName);
        Assert.assertTrue(getSyncNodeOperator().checkNodeIsSynchronisedOrTimeout(nodeName, 10, 12), "Test Setup Problem. Problem synchronising  node " + nodeName);

//        String operationalSecurityLevel=getNodeSecurityLevelUsingCmedit(nodeName);
//        Assert.assertFalse(operationalSecurityLevel.equals("LEVEL_2"),"Test Setup Problem: Node is already at Security Level 2");

        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();
        NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

        NeGroup neGroup = netSimOperator.getNeGroup();
        List<NetworkElement> neList = neGroup.getNetworkElements();
        for (NetworkElement ne : neList) {
            System.out.println("Name: " + ne.getName() + " Simulation :" + ne.getSimulationName());
        }
        for (String nodeNameX : nodeArray) {
            NetworkElement ne = netSimOperator.getNetworkElement(nodeNameX);
            System.out.println("Name: " + ne.getName() + " Simulation :" + ne.getSimulationName());
        }

        checkNetworkElementIsStartedForTestSetup(nodeName);
        printSecurityMOAttributeValuesOnNE(netSimOperator, nodeName);

        final int TIMEOUT_PERIOD = 3;
        final int NUMBER_OF_TIMES_TO_CHECK = 20;

        //If node already at securitylevel2, set attribute back// TODO may need better check in future when know what workflow does to attributes on NE

            //Change the operationalSecurityLevel to 1 and fileTransferClientMode to FTP in preparation for the test
            String moForSetCommand = "ManagedElement=1,SystemFunctions=1,Security=1";
            Map<String, String> attributeNamesAndValues = new HashMap<>();
            attributeNamesAndValues.put("fileTransferClientMode", "0");
            attributeNamesAndValues.put("operationalSecurityLevel", "1");
            attributeNamesAndValues.put("trustedCertificateInstallationFailure", "false");
        //attributeNameAndValue.put("certenrollstate", "error");

        setMoAttribute(networkElement, moForSetCommand, attributeNamesAndValues);

        boolean fileTransferClientModeCheck = checkAttributeValueMatchesOrTimeout(nodeName, "Security", "fileTransferClientMode", "FTP", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
        boolean operationalSecurityLevelCheck = checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_1", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
        boolean trustedCertificateInstallationFailureCheck = checkAttributeValueMatchesOrTimeout(nodeName, "Security", "trustedCertificateInstallationFailure", "false", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

        Assert.assertTrue(fileTransferClientModeCheck,
                "Test Setup Failure : fileTransferClientMode not set to FTP within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" );
        Assert.assertTrue(trustedCertificateInstallationFailureCheck,
                "Test Setup Failure : trustedCertificateInstallationFailure not set to False within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" );
        Assert.assertTrue(operationalSecurityLevelCheck,
               "Test Setup Failure : operationalSecurityLevel not set to LEVEL1 within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" );

        // Set security level using secadm securitylevel
        String httpResponseBody=getSecurityCommandsOperator().setSecurityLevel(nodeName,"2").getBody();

        fileTransferClientModeCheck=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "fileTransferClientMode", "SFTP", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
        trustedCertificateInstallationFailureCheck=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "trustedCertificateInstallationFailure", "false", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
//      boolean operationalSecurityLevelCheck= checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_2", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG),"Test Case Failure, Did not receive expected success message"
                + NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG + " Received : " + httpResponseBody);

        Assert.assertTrue(fileTransferClientModeCheck,
                "Test Case Failure : fileTransferClientMode not set to SFTP within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" );
        Assert.assertTrue(trustedCertificateInstallationFailureCheck,
                "Test Case Failure : trustedCertificateInstallationFailure is true after test, indicating problem with" );
//      Assert.assertTrue(operationalSecurityLevelCheck,
//              "Test Case Failure : operationalSecurityLevel not set back to LEVEL2 within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" );

        //  TODO Add more Checks
        // Trusted Certs exist on SMRS,
        // attributes in Security MO contain correct data etc

    }



    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelSetSl1OneNode(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test securityLevelSetSl1OneNode ############# \n\n");

        final int TIMEOUT_PERIOD=10;
        final int NUMBER_OF_TIMES_TO_CHECK=6;

        final String[] nodeArray = nodeList.split(",");
        String nodeName =  nodeArray[11];

        //TODO If we want logs, enable a flag somewhere
        //Then call enable logging on server and pass in test case name as suffix

        final NetSimCmOperatorImpl netSimOperator = getNetSimOperator();
        checkNetworkElementIsStartedForTestSetup(nodeName);
        final NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

        //TODO
        //Not great solution in NetsimOperator : have to give a fdn including node name to the netsimOperator method
        //Then it extracts the node name out of that , gets the NetworkElement using the node name
        //should be improved if you have the NetworkELeemnt, just provide a MO to get attributes.

        final String moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
        Map<String, String> attributeMap = netSimOperator.getAllAttributes(moFdnInFormatRequiredForOperator);
        printMap(attributeMap, "attributeMap");

        if (!getNodeSecurityLevelUsingCmedit(nodeName).equals("LEVEL_2")){

            // set operational security level to 2 on the NE in preparation for using secadm sl set --level 1

            final String moForSetCommand = "ManagedElement=1,SystemFunctions=1,Security=1";
            Map<String,String> attributeNameAndValue = new HashMap<>();
            attributeNameAndValue.put("fileTransferClientMode", "0");
            attributeNameAndValue.put("operationalSecurityLevel", "2");
            final String attributeAndValueList = createAttributeNameAndValueListForSetMoAttributeCommand(attributeNameAndValue);
            setMoAttribute(networkElement, moForSetCommand, attributeAndValueList);
            // continue when notification is received and attribute is set to LEVEL_2 in DPS or timeout in 30 seconds
            checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_2", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
            Assert.assertEquals(checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_2", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK),true,"Test Setup Problem: " + nodeName + " : not in correct state , operationAlSecurityState is not at LEVEL_2 in ENM after attempting to change value on changing on NE");

//        moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
//        attributeMap = netSimOperator.getAllAttributesForSingleMo(moFdnInFormatRequiredForOperator);
//        printMap(attributeMap, "attributeMap");

        }

        // Set security level using secadm securitylevel
        final String httpResponseBody=getSecurityCommandsOperator().setSecurityLevel(nodeName,"1").getBody();

        // continue when attribute is set to LEVEL_1 in DPS or timeout in 30 seconds

        final boolean operationalSecurityLevelMatches= checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_1", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
        final boolean requestedSecurityLevelMatches= checkAttributeValueMatchesOrTimeout(nodeName, "Security", "requestedSecurityLevel", "LEVEL_1", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG),"Test Case Failure, Did not receive expected success message"
                + NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG + " Received : " + httpResponseBody);

        Assert.assertTrue(operationalSecurityLevelMatches,
                "Test Case Failure : operationalSecurityLevel not set back to LEVEL_1 within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" );

        Assert.assertTrue(requestedSecurityLevelMatches,
                "Test Case Failure : operationalSecurityLevel not set back to LEVEL_1 within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" );

        //TODO Anyway to check if the workflow is completed and finished successfully via camunda api or checking our logs ?

    }





    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void securityLevelSetSl2OneNodeUsingShortArgumentNames(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test securityLevelSetSl2OneNodeUsingShortArgumentNames ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        String nodeName =  nodeArray[11];

        Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");

        final String secadmSetCommand= String.format(NscsServiceGetter.SECADM_SL_SET_COMMAND_SHORT,"2",nodeName);

        final String httpResponseBody=getScriptEngineOperator().runCommand(secadmSetCommand).getBody();

        VerifyResponseContainsExpectedContent(httpResponseBody, NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG);

        //TODO Could Add in checks that the security level is changed correctly but this test is just focused on being able to use short argument names


    }

    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelSetSl2UsingFileOfNodesShortArgumentNames(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList)
    {

        System.out.println(" \n\n ############# Starting test securityLevelSetSl2UsingFileOfNodesShortArgumentNames ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        String newNodeList =  nodeArray[12] + "," + nodeArray[13];
        final String[] nodeArraySubset = newNodeList.split(",");
        //Check nodes required for test exist and are synched and credentials exist
        for (String nodeName : nodeArraySubset) {
            Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");
        }

        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents = new byte[0];
        try {
            fileContents = newNodeList.replace(",",";").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Assert.fail("Problem encoding file contents");
        }

        final String commandString = "secadm sl set -l 2 "+
                " -nf " + fileName;

        final String httpResponseBody = executeScriptEngineCommandWithFile(commandString,fileName,fileContents);

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG),"Test Case Failure - There was a problem with One or more of the nodes TODO fetch error codes and suggested soltuions and print so easier to know why failed in future");

        //TODO Could Add in checks that the security level is change but this test is just focused on being able to use a file correctly
    }


    @DataDriven(name = "4_Node_Versions_List")
    @Test(groups={"Acceptance"})
    public void securityLevelSetSl2MultipleNodeAllSupportedNeVersions(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList)
    {

        final int TIMEOUT_PERIOD=6;
        final int NUMBER_OF_TIMES_TO_CHECK=10;

        System.out.println(" \n\n ############# Starting test securityLevelSetSl2MultipleNode ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();

        String moForSetCommand = "ManagedElement=1,SystemFunctions=1,Security=1";
        Map<String, String> attributeNameAndValue = new HashMap<>();
        attributeNameAndValue.put("fileTransferClientMode", "0");
        attributeNameAndValue.put("operationalSecurityLevel", "1");
        attributeNameAndValue.put("trustedCertificateInstallationFailure", "false");
        //Update netsims with relevant patches.
        //attributeNameAndValue.put("certenrollstate", "error");

        String attributeAndValueList = createAttributeNameAndValueListForSetMoAttributeCommand(attributeNameAndValue);
        for (String nodeName : nodeArray) {
            Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName), true, "Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");

            String operationalSecurityLevel=getNodeSecurityLevelUsingCmedit(nodeName);
            Assert.assertFalse(operationalSecurityLevel.equals("LEVEL_1"),"Test Setup Problem: Node is not at Security Level 1");

            checkNetworkElementIsStartedForTestSetup(nodeName);
            NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

            //            fetch and print the MO attributes on the NE and print
            String moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
            Map<String, String> attributeMap = netSimOperator.getAllAttributes(moFdnInFormatRequiredForOperator);
            printMap(attributeMap, moFdnInFormatRequiredForOperator);

            //setMoAttribute(networkElement, moForSetCommand, attributeAndValueList);
            setMoAttribute(networkElement, moForSetCommand, attributeNameAndValue);

            //            fetch and print the MO attributes on the NE and print
            moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
            attributeMap = netSimOperator.getAllAttributes(moFdnInFormatRequiredForOperator);
            printMap(attributeMap, moFdnInFormatRequiredForOperator);
        }
//TODO Uncompress simulation and start network at start of each test run and would not need to reset these values.
        for (String nodeName : nodeArray) {
            //read Attribute value until it is level 1, then continue
            checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_1", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
        }

        // Set security level using secadm securitylevel
        String httpResponseBody=getSecurityCommandsOperator().setSecurityLevel(nodeList,"2").getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG),"Did not receive " + NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG + "in command response");


        for (String nodeName : nodeArray) {
            //read Attribute value until it is level 2, then continue
            boolean fileTransferClientModeCheck=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "fileTransferClientMode", "SFTP", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
            boolean trustedCertificateInstallationFailureCheck=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "trustedCertificateInstallationFailure", "false", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
            boolean IsSecurityLevelCorrect=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_2", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

            //Add checks here to what needs to be checked in MO in DPS or on NE
            Assert.assertTrue(fileTransferClientModeCheck,
                    "Test Case Failure : fileTransferClientMode not set to SFTP within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" + " on " + nodeName);
            Assert.assertTrue(trustedCertificateInstallationFailureCheck,
                    "Test Case Failure : trustedCertificateInstallationFailure is true after test, indicating problem with " + nodeName );
//            Assert.assertTrue(IsSecurityLevelCorrect,"Test Case Failure : Times out waiting for operationalSecurityLevel to be LEVEL_2");

        }

        //  TODO Add more Checks
        // Trusted Certs exist on SMRS,
        // attributes in SEcurity MO contain correct data etc
    }








      /*

    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelSetSl2MultipleNode(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList)
    {

        final int TIMEOUT_PERIOD=6;
        final int NUMBER_OF_TIMES_TO_CHECK=10;


        System.out.println(" \n\n ############# Starting test securityLevelSetSl2MultipleNode ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        String newNodeList =  nodeArray[14] + "," + nodeArray[15];

        NetSimOperator netSimOperator = getNetSimOperator();
        final String[] nodeArraySubset = newNodeList.split(",");


        String moForSetCommand = "ManagedElement=1,SystemFunctions=1,Security=1";
        Map<String, String> attributeNameAndValue = new HashMap<>();
        attributeNameAndValue.put("fileTransferClientMode", "0");
        attributeNameAndValue.put("operationalSecurityLevel", "1");
        attributeNameAndValue.put("trustedCertificateInstallationFailure", "false");

        String attributeAndValueList = createAttributeNameAndValueListForSetMoAttributeCommand(attributeNameAndValue);
        for (String nodeName : nodeArraySubset) {
            Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName), true, "Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");

            //String operationalSecurityLevel=getNodeSecurityLevelUsingCmedit(nodeName);
            //Assert.assertFalse(operationalSecurityLevel.equals("LEVEL_1"),"Test Setup Problem: Node is already at Security Level 1");

            checkNetworkElementIsStartedForTestSetup(nodeName);
            NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

            String moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
            Map<String, String> attributeMap = netSimOperator.getAllAttributesForSingleMo(moFdnInFormatRequiredForOperator);
            printMap(attributeMap, moFdnInFormatRequiredForOperator);

            setMoAttributeOnNE(networkElement, moForSetCommand, attributeAndValueList);

            //            fetch the MO attributes on the NE and print
            moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
            attributeMap = netSimOperator.getAllAttributesForSingleMo(moFdnInFormatRequiredForOperator);
            printMap(attributeMap, moFdnInFormatRequiredForOperator);

        }

        for (String nodeName : nodeArraySubset) {
            //read Attribute value until it is level 2, then continue
            checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_1", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

        }

        // Set security level using secadm securitylevel
        String httpResponseBody=getSecurityCommandsOperator().setSecurityLevel(newNodeList,"2").getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG),"Did not receive " + NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG + "in command response");


        for (String nodeName : nodeArraySubset) {
            //read Attribute value until it is level 1, then continue
            boolean fileTransferClientModeCheck=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "fileTransferClientMode", "SFTP", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
            boolean trustedCertificateInstallationFailureCheck=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "trustedCertificateInstallationFailure", "false", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
            boolean IsSecurityLevelCorrect=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_2", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

            Assert.assertTrue(fileTransferClientModeCheck,
                    "Test Case Failure : fileTransferClientMode not set to SFTP within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" + " on " + nodeName);
            Assert.assertTrue(trustedCertificateInstallationFailureCheck,
                    "Test Case Failure : trustedCertificateInstallationFailure is true after test, indicating problem with " + nodeName );
//            Assert.assertTrue(IsSecurityLevelCorrect,"Test Case Failure : Times out waiting for operationalSecurityLevel to be LEVEL_2");

        }

        //  TODO Add more Checks
        // Trusted Certs exist on SMRS,
        // attributes in SEcurity MO contain correct data etc
    }


    @DataDriven(name = "Complete_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelSetSl1MultipleNode(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList)
    {

        final int TIMEOUT_PERIOD=10;
        final int NUMBER_OF_TIMES_TO_CHECK=6;

        System.out.println(" \n\n ############# Starting test securityLevelSetSl1MultipleNode ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        String newNodeList =  nodeArray[16] + "," + nodeArray[17];

        NetSimOperator netSimOperator = getNetSimOperator();
        final String[] nodeArraySubset = newNodeList.split(",");

        for (String nodeName : nodeArraySubset) {
            Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName), true, "Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");

            //String operationalSecurityLevel=getNodeSecurityLevelUsingCmedit(nodeName);
            //Assert.assertFalse(operationalSecurityLevel.equals("LEVEL_1"),"Test Setup Problem: Node is already at Security Level 1");

            checkNetworkElementIsStartedForTestSetup(nodeName);
            final NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

            // String moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
            // Map<String, String> attributeMap = netSimOperator.getAllAttributesForSingleMo(moFdnInFormatRequiredForOperator);
            //printMap(attributeMap, "attributeMap");

            final String moForSetCommand = "ManagedElement=1,SystemFunctions=1,Security=1";
            Map<String, String> attributeNameAndValue = new HashMap<>();
            attributeNameAndValue.put("fileTransferClientMode", "1");
            attributeNameAndValue.put("operationalSecurityLevel", "2");
            final String attributeAndValueList = createAttributeNameAndValueListForSetMoAttributeCommand(attributeNameAndValue);
            setMoAttributeOnNE(networkElement, moForSetCommand, attributeAndValueList);

        }

        for (String nodeName : nodeArraySubset) {

            //read Attribute value until it is level 2, then continue
            boolean attributeValuefound = checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_2", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
            System.out.println("operationalSecurityLevel is LEVEL_2 ? :  result of check  " + attributeValuefound);
            //fetch the MO attributes on the NE and print
            //  moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
            //  attributeMap = netSimOperator.getAllAttributesForSingleMo(moFdnInFormatRequiredForOperator);
            //  printMap(attributeMap, "attributeMap");
        }

        // Set security level using secadm securitylevel
        final String httpResponseBody=getSecurityCommandsOperator().setSecurityLevel(newNodeList,"1").getBody();



        //Check Set back to Level 1

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG),"Did not receive " + NscsServiceGetter.SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG + "in command response");


        for (String nodeName : nodeArraySubset) {
            //read Attribute value until it is level 1, then continue
            boolean IsSecurityLevelCorrect=checkAttributeValueMatchesOrTimeout(nodeName, "Security", "operationalSecurityLevel", "LEVEL_1", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);
            Assert.assertTrue(IsSecurityLevelCorrect,"Test Case Failure : Times out waiting for operationalSecurityLevel to be LEVEL_1 on node" + nodeName + getNodeSecurityLevelUsingCmedit(nodeName));
        }



    }

     */
}




