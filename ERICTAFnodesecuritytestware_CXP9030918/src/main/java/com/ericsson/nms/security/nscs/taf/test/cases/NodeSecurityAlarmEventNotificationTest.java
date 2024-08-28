package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.handlers.netsim.NetSimResult;
import com.ericsson.cifwk.taf.handlers.netsim.commands.*;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NeGroup;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.oss.netsim.operator.cm.NetSimCmOperatorImpl;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class NodeSecurityAlarmEventNotificationTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(NodeSecurityAlarmEventNotificationTest.class);
//    final String SC1_SECSERV_LOG="/var/ericsson/log/jboss/SecServ_su_0_jee_cfg/server.log";
//    final String SC2_SECSERV_LOG="/var/ericsson/log/jboss/SecServ_su_1_jee_cfg/server.log";

    final int sleepTime = 2000;

    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void verifySecurityAlarmsReceivedInNodeSecurityServiceOriginal(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test verifyAlarmsReceivedInNodeSecurityService ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_USED_FOR_NOTIFICATION_ALARM_TEST];

        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();

//        NeGroup neGroup = netSimOperator.getAllNetworkElements();
//        List<NetworkElement> networkElementList = neGroup.getNetworkElements();
//        System.out.println("Number of NEs found in " + DataHandler.getAttribute("netSim.simulations") + " is : " + networkElementList.size());

        //just do for first 3 NEs to show working
//        for (int i = 0; i < 3; i++) {
//            NetworkElement networkElement = networkElementList.get(i);
//
//            String neName = networkElement.getName();
//            System.out.println(neName + " : " + " Started " + networkElement.isStarted());
//            System.out.println(neName + " : " + " ip address " + networkElement.getIp());
//            System.out.println(neName + " : " + " mim " + networkElement.getMim());
//            System.out.println(neName + " : " + " simulation " + networkElement.getSimulationName());
//            System.out.println(neName + " : " + " hostname " + networkElement.getHostName());
//            System.out.println(neName + " : " + " nodetype " + networkElement.getNodeType());
//            System.out.println(neName + " : " + " type " + networkElement.getType());
//            System.out.println(neName + " : " + " toString " + networkElement.toString());
//        }

        //Clear Alarms on ENM side on the Node
        final String alarmAcknowledgeCommand = "fmedit set * OpenAlarm.(objectOfReference==\"MeContext=" + nodeName + "\") alarmState=\"ack\"";
        final String alarmClearCommand = "fmedit set * OpenAlarm.(objectOfReference==\"MeContext=" + nodeName + "\") alarmState=\"clear\"";
        final String alarmListCommand = "fmedit get * OpenAlarm.(objectOfReference==\"MeContext=" + nodeName + "\") OpenAlarm.*";

        getScriptEngineOperator().runCommand(alarmListCommand).getBody();
        getScriptEngineOperator().runCommand(alarmAcknowledgeCommand).getBody();
        getScriptEngineOperator().runCommand(alarmClearCommand).getBody();
        getScriptEngineOperator().runCommand(alarmListCommand).getBody();

        checkNetworkElementIsStartedForTestSetup(nodeName);

        NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

        CeasealarmCommand ceaseAlarmCommand = NetSimCommands.ceasealarm();
        ceaseAlarmCommand.setAll("all");
        NetSimResult netSimResult = networkElement.exec(ceaseAlarmCommand);
        System.out.println("Result of ceasealarm command Alarms on Node : " + nodeName + " : " + netSimResult.toString());

        ShowalarmCommand showAlarmCommand = NetSimCommands.showalarm();
        netSimResult = networkElement.exec(showAlarmCommand);
        System.out.println("Alarms on Node : " + nodeName + " :" + netSimResult.toString());

        // Do for each of our relevant alarms and make sure they end up in node security log and also WFS log ?

        //TODO send alarms on NEs on Security MO
        //TODO send alarms on NEs on non security MOsthat are in workflows
        //TODO send alarms on NEs that are not in workflows
        //TODO send alarms on NEs that are in workflows

        String specificProblem = "NodeSecurity_AlarmTestOnOneSideOfCluster";
        String expectedLogEntry = specificProblem;

        int cause = 2;
        int severity = 2;

        //TODO Send an alarm on a specific MO (Security in our case), This is just sending a general alarm.
        SendalarmCommand sendAlarmCommand = NetSimCommands.sendalarm();
        sendAlarmCommand.setProblem(specificProblem);
        sendAlarmCommand.setCause(cause);
        sendAlarmCommand.setSeverity(severity);

        //just in case any left over from other tests, kill it first
        stopTailingLogFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG);

        final String LOG_EXTENSION=".alarm.testcase.log";

        startTailingFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG, NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION, sleepTime);
        startTailingFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG, NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION, sleepTime);

        netSimResult = networkElement.exec(sendAlarmCommand);
        System.out.println("Result from sendAlarmCommand : " + netSimResult.toString());

        //TODO use FM commands to check the alarm has arrived in FM before stopping the tailing, rather than waiting a period of time hardcoded.
        //TODO Also checks if FM is working as expected
        //TODO Make sure DEBUG Level is not on or get different results

        delay(30000); //give chance for it to reach our services

        System.out.println(" ******* Alarsm in ENM system on node " + nodeName  + "    ********** ");

        getScriptEngineOperator().runCommand(alarmListCommand).getBody();

        stopTailingLogFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG);

        String sc1LogEntry= getFileContentThatMatchFilter(
                HostConfigurator.getSC1(),
                NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION,
                expectedLogEntry);

        String sc2LogEntry= getFileContentThatMatchFilter(
                HostConfigurator.getSC2(),
                NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION,
                expectedLogEntry);

        System.out.println("SC1 :  " + sc1LogEntry);
        System.out.println("SC2 :  " + sc2LogEntry);

        String alarmOccurencesOnSC1 =
                getNumberOfOccurencesThatMatchFilter(
                        HostConfigurator.getSC1(),
                        NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION,
                        expectedLogEntry
                ).trim();

        String alarmOccurencesOnSC2 =
                getNumberOfOccurencesThatMatchFilter(
                        HostConfigurator.getSC2(),
                        NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION,
                        expectedLogEntry
                ).trim();

        System.out.println("alarmOccurencesOnSC1 :  " + alarmOccurencesOnSC1);
        System.out.println("alarmOccurencesOnSC2 :  " + alarmOccurencesOnSC2);

        int numOfAlarmsReceivedBothSidesOfCluster = (Integer.parseInt(alarmOccurencesOnSC1.trim()) + Integer.parseInt(alarmOccurencesOnSC2));

        System.out.println("Deleting files");
        deleteFile(HostConfigurator.getSC1(),NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION);
        deleteFile(HostConfigurator.getSC2(),NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION);

        netSimResult = networkElement.exec(ceaseAlarmCommand);
        System.out.println("Result of Cease Alarms command on Node : " + nodeName + " : " + netSimResult.toString());

        netSimResult = networkElement.exec(showAlarmCommand);
        System.out.println("Alarms on Node : " + nodeName + " :" + netSimResult.toString());

        //Clear Alarms in ENM
        getScriptEngineOperator().runCommand(alarmAcknowledgeCommand).getBody();
        getScriptEngineOperator().runCommand(alarmClearCommand).getBody();
        getScriptEngineOperator().runCommand(alarmListCommand).getBody();


        Assert.assertEquals(numOfAlarmsReceivedBothSidesOfCluster,1,
                "Alarm Test failed. Expected 1 alarm : number Of Alarms Received Both Sides Of Cluster : " +
                        numOfAlarmsReceivedBothSidesOfCluster);

     }


    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void verifySecurityAlarmsReceivedInNodeSecurityService(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test verifyAlarmsReceivedInNodeSecurityService ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_USED_FOR_NOTIFICATION_ALARM_TEST];

        clearAlarmsOnNodeInENMAndOnNE(nodeName);

        final String LOG_EXTENSION=".alarm.testcase.log";

        startTailingFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG, NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION, sleepTime);
        startTailingFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG, NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION, sleepTime);

        String specificProblem = "SecurityMO_Alarm"; int cause = 2; int severity = 2;
        String instance="ManagedElement=1,SystemFunctions=1,Security=1";
        String result =  sendAlarmsOnNE(nodeName, specificProblem, cause, severity,instance);
        System.out.println("Result from sendAlarmCommand : " + result);

        final int TIMEOUT_PERIOD = 3;
        final int NUMBER_OF_TIMES_TO_CHECK = 20;

        //TODO use FM commands to check the alarm has arrived in FM before stopping the tailing, rather than waiting a period of time hardcoded.
        checkAlarmReceivedOrTimeout(nodeName, "Security", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

        //TODO Also checks if FM is working as expected
        //TODO Make sure DEBUG Level is not on or get different results

        delay(30000); //give chance for it to reach our services

        checkAlarmReceivedOrTimeout(nodeName, "Security", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

//        showAlarmsOnNE(nodeName);
//        showAlarmsOnNodeInENM(nodeName);

        String expectedLogEntry = specificProblem;
        stopTailingLogFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG);
        System.out.println("SC1 :  " + getFileContentThatMatchFilter(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION, expectedLogEntry));
        System.out.println("SC2 :  " + getFileContentThatMatchFilter(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION, expectedLogEntry));


        String alarmOccurencesOnSC1 =  getNumberOfOccurencesThatMatchFilter( HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION
                ,expectedLogEntry ).trim();
        String alarmOccurencesOnSC2 = getNumberOfOccurencesThatMatchFilter(  HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION,
                expectedLogEntry ).trim();

        System.out.println("alarmOccurencesOnSC1 :  " + alarmOccurencesOnSC1);
        System.out.println("alarmOccurencesOnSC2 :  " + alarmOccurencesOnSC2);
        int numOfAlarmsReceivedBothSidesOfCluster = (Integer.parseInt(alarmOccurencesOnSC1.trim()) + Integer.parseInt(alarmOccurencesOnSC2));

        System.out.println("Deleting log files after test");
        deleteFile(HostConfigurator.getSC1(),NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION);
        deleteFile(HostConfigurator.getSC2(),NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION);

//        //Clear Alarms in ENM
        removeAlarmsOnNodeInENM(nodeName);
        showAlarmsOnNodeInENM(nodeName);
//        //Clear Alarms on NE
        ceaseAlarmsOnNE(nodeName);
        showAlarmsOnNE(nodeName);

        Assert.assertEquals(numOfAlarmsReceivedBothSidesOfCluster,1,
                "Alarm Test failed. Expected 1 alarm : number Of Alarms Received Both Sides Of Cluster : " +
                        numOfAlarmsReceivedBothSidesOfCluster);
    }



    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void verifyNonSecurityAlarmsNotReceivedInNodeSecurityService(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test verifyNonSecurityAlarmsNotReceivedInNodeSecurityService ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_USED_FOR_NOTIFICATION_ALARM_TEST];

        clearAlarmsOnNodeInENMAndOnNE(nodeName);

        final String LOG_EXTENSION=".alarm.testcase.log";
        startTailingFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG, NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION, sleepTime);
        startTailingFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG, NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION, sleepTime);


        String specificProblem = "Non-SecurityMO_Alarm"; String expectedLogEntry = specificProblem; int cause = 2; int severity = 2;
     //   String result =  sendAlarmsOnNE(nodeName, specificProblem, cause, severity,"ManagedElement=1,SystemFunctions=1,Security=1");
        String result =  sendAlarmsOnNE(nodeName, specificProblem, cause, severity,"ManagedElement=1");
        System.out.println("Result from sendAlarmCommand : " + result);

        final int TIMEOUT_PERIOD = 3;
        final int NUMBER_OF_TIMES_TO_CHECK = 20;

        checkAlarmReceivedOrTimeout(nodeName, "Security", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

        //TODO use FM commands to check the alarm has arrived in FM before stopping the tailing, rather than waiting a period of time hardcoded.
        //TODO Also checks if FM is working as expected
        //TODO Make sure DEBUG Level is not on or get different results

        delay(30000); //give chance for it to reach our services

        checkAlarmReceivedOrTimeout(nodeName, "Security", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

//        showAlarmsOnNE(nodeName);
//        showAlarmsOnNodeInENM(nodeName);

        stopTailingLogFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG);
        System.out.println("SC1 :  " + getFileContentThatMatchFilter(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION, expectedLogEntry));
        System.out.println("SC2 :  " + getFileContentThatMatchFilter(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION, expectedLogEntry));

        String alarmOccurencesOnSC1 =  getNumberOfOccurencesThatMatchFilter( HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION
                        ,expectedLogEntry ).trim();
        String alarmOccurencesOnSC2 = getNumberOfOccurencesThatMatchFilter(  HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION,
                expectedLogEntry ).trim();

        System.out.println("alarmOccurencesOnSC1 :  " + alarmOccurencesOnSC1);
        System.out.println("alarmOccurencesOnSC2 :  " + alarmOccurencesOnSC2);
        int numOfAlarmsReceivedBothSidesOfCluster = (Integer.parseInt(alarmOccurencesOnSC1.trim()) + Integer.parseInt(alarmOccurencesOnSC2));

        System.out.println("Deleting files");
        deleteFile(HostConfigurator.getSC1(),NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION);
        deleteFile(HostConfigurator.getSC2(),NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION);

//        //Clear Alarms in ENM
//        removeAlarmsOnNodeInENM(nodeName);
//        //Clear Alarms on NE
//        ceaseAlarmsOnNE(nodeName);
//        showAlarmsOnNE(nodeName);

        Assert.assertEquals(numOfAlarmsReceivedBothSidesOfCluster,0,
                "Alarm Test failed. Expected no alarms in our service as not a security alarm : number Of Alarms Received Both Sides Of Cluster : " +
                        numOfAlarmsReceivedBothSidesOfCluster);


        //TODO send alarms on NEs on Security MO
        //TODO send alarms on NEs on non security MOsthat are in workflows
        //TODO send alarms on NEs that are not in workflows
        //TODO send alarms on NEs that are in workflows


    }



    @DataDriven(name = "7_Node_Versions_List")
    @Test(groups = {"Acceptance"})
    public void verifyNotificationsReceivedInNodeSecurityService(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test verifyNotificationsReceivedInNodeSecurityService ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        //final String nodeName=nodeArray[NscsServiceGetter.INDEXOF_NODE_USED_FOR_NOTIFICATION_ALARM_TEST];
        final String nodeName=nodeArray[1];

        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();
        NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

        checkNetworkElementIsStartedForTestSetup(nodeName);


        /*
         * These are some of the Netsim attributes we may want to change :
         *
         * setmoattribute:mo="ManagedElement=1,SystemFunctions=1,Security=1",attributes="userLabel=userlabel_nodesecurity";
         * setmoattribute:mo="ManagedElement=1,SystemFunctions=1,Security=1",attributes="trustedCertificateInstallationFailure=true";
         * setmoattribute:mo="ManagedElement=1,SystemFunctions=1,Security=1",attributes="fileTransferClientMode=1";
         */

        //CM NetsimOperator strips off MeContext=
        String moFdnInFormatRequiredForOperator = "MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1";
        Map<String, String> attributeMap = netSimOperator.getAllAttributes(moFdnInFormatRequiredForOperator);
        printMap(attributeMap, "attributeMap");

        String moForSetCommand = "ManagedElement=1,SystemFunctions=1,Security=1";

        Map<String,String> attributeNameAndValue = new HashMap<>();

        attributeNameAndValue = new HashMap<>();
        attributeNameAndValue.put("fileTransferClientMode", "0");
        String attributeAndValueList = createAttributeNameAndValueListForSetMoAttributeCommand(attributeNameAndValue);
        setMoAttribute(networkElement, moForSetCommand, attributeAndValueList);

        final int TIMEOUT_PERIOD=5;
        final int NUMBER_OF_TIMES_TO_CHECK=6;
        checkAttributeValueMatchesOrTimeout(nodeName, "Security", "fileTransferClientMode", "FTP", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

//        stopTailingMultipleFilesOnServer(HostConfigurator.getSC1(),NscsServiceGetter.SC1_SECSERV_LOG + "," + NscsServiceGetter.SC1_MSCM_LOG);
//        stopTailingMultipleFilesOnServer(HostConfigurator.getSC2(),NscsServiceGetter.SC2_SECSERV_LOG + "," + NscsServiceGetter.SC2_MSCM_LOG);

        stopTailingLogFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_MSCM_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_MSCM_LOG);

        final String LOG_EXTENSION=".notification.testcase.log";
        startTailingFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG, NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION, sleepTime);
        startTailingFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG, NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION, sleepTime);
        startTailingFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_MSCM_LOG, NscsServiceGetter.SC1_MSCM_LOG + LOG_EXTENSION, sleepTime);
        startTailingFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_MSCM_LOG, NscsServiceGetter.SC2_MSCM_LOG + LOG_EXTENSION, sleepTime);

//        startTailingMultipleFilesOnServer(HostConfigurator.getSC1(),NscsServiceGetter.SC1_SECSERV_LOG + "," + NscsServiceGetter.SC1_MSCM_LOG,LOG_EXTENSION );
//        startTailingMultipleFilesOnServer(HostConfigurator.getSC2(),NscsServiceGetter.SC2_SECSERV_LOG + "," + NscsServiceGetter.SC2_MSCM_LOG, LOG_EXTENSION);

        //TODO use different Node
        attributeNameAndValue.put("fileTransferClientMode", "1");
        attributeAndValueList = createAttributeNameAndValueListForSetMoAttributeCommand(attributeNameAndValue);

        setMoAttribute(networkElement, moForSetCommand, attributeAndValueList);

        boolean attributeValueMatches= checkAttributeValueMatchesOrTimeout(nodeName, "Security", "fileTransferClientMode", "SFTP", TIMEOUT_PERIOD, NUMBER_OF_TIMES_TO_CHECK);

//        stopTailingMultipleFilesOnServer(HostConfigurator.getSC1(),NscsServiceGetter.SC1_SECSERV_LOG + "," + NscsServiceGetter.SC1_MSCM_LOG);
//        stopTailingMultipleFilesOnServer(HostConfigurator.getSC2(),NscsServiceGetter.SC2_SECSERV_LOG + "," + NscsServiceGetter.SC2_MSCM_LOG);

        stopTailingLogFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_SECSERV_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_SECSERV_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC1(), NscsServiceGetter.SC1_MSCM_LOG);
        stopTailingLogFileOnServer(HostConfigurator.getSC2(), NscsServiceGetter.SC2_MSCM_LOG);


        //TODO find appropriate log entry to use

        String expectedLogEntry="Dispatching message:";

        String sc1LogEntry= getFileContentThatMatchFilter(
                HostConfigurator.getSC1(),
                NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION,
                expectedLogEntry);

        String sc2LogEntry= getFileContentThatMatchFilter(
                HostConfigurator.getSC2(),
                NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION,
                expectedLogEntry);

        logger.debug("SC1 Log Entry :  " + sc1LogEntry);
        logger.debug("SC2 Log Entry :  " + sc2LogEntry);

        String notifcationOccurencesOnSC1 =
                getNumberOfOccurencesThatMatchFilter(
                        HostConfigurator.getSC1(),
                        NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION,
                        expectedLogEntry
                ).trim();

        String notifcationOccurencesOnSC2 =
                getNumberOfOccurencesThatMatchFilter(
                        HostConfigurator.getSC2(),
                        NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION,
                        expectedLogEntry
                ).trim();

        logger.debug("SC1 : Notifcation Occurences  :  " + notifcationOccurencesOnSC1);
        logger.debug("SC2 : Notifcation Occurences  :  " + notifcationOccurencesOnSC2);

        int numOfNotifcationsReceivedBothSidesOfCluster = (Integer.parseInt(notifcationOccurencesOnSC1) + Integer.parseInt(notifcationOccurencesOnSC2));

        logger.debug("Deleting files " + NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION + " and " +  NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION);
        deleteFile(HostConfigurator.getSC1(),NscsServiceGetter.SC1_SECSERV_LOG + LOG_EXTENSION);
        deleteFile(HostConfigurator.getSC2(),NscsServiceGetter.SC2_SECSERV_LOG + LOG_EXTENSION);
        deleteFile(HostConfigurator.getSC1(),NscsServiceGetter.SC1_MSCM_LOG + LOG_EXTENSION);
        deleteFile(HostConfigurator.getSC2(),NscsServiceGetter.SC2_MSCM_LOG + LOG_EXTENSION);

        Assert.assertTrue(attributeValueMatches,
                "Test Case Failure : fileTransferClientMode not set back to SFTP within timeout period of " + (TIMEOUT_PERIOD * NUMBER_OF_TIMES_TO_CHECK) + "seconds" );

        Assert.assertEquals(1,numOfNotifcationsReceivedBothSidesOfCluster,
                "Notification Test failed. Expected 1 alarm : number Of Alarms Received Both Sides Of Cluster"
                        + numOfNotifcationsReceivedBothSidesOfCluster);

    }


}
