/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.certificate;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.netsim.NetSimResult;
import com.ericsson.cifwk.taf.handlers.netsim.commands.NetSimCommands;
import com.ericsson.cifwk.taf.handlers.netsim.commands.ShowalarmCommand;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.*;
import com.ericsson.oss.netsim.operator.cm.NetSimCmOperatorImpl;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class CertificateIssuePositiveNetSimScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(CertificateIssuePositiveNetSimScenariosTestSteps.class);
       

    @TestStep(id = "certificateIssuePositiveTest")
    public void certificateIssuePositiveTest (
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test certificateIssuePositiveTest ############# \n\n");
        logger.info(" \n\n ############# Starting test certificateIssuePositiveTest ############# \n\n");

        // Create temporary node for netsim test
        String nodeName = "LTEERBS00001_TMP";
        
        createMeContextForTestSetup(nodeName);
        
        createNetworkElementForTestSetup(nodeName);
        
        createCppConnectivityInfoForTestSetup(nodeName, "192.1.1.101");        
        
        // NETSIM OPERATOR
        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();
        Host netsimHost = HostConfigurator.getNetsim();
        printHost(netsimHost);

        System.out.println("netsim IP" + netsimHost.getIp());
        System.out.println("netsim original IP" +netsimHost.getOriginalIp());
        System.out.println("netsim port" + netsimHost.getPort());
                
        checkNetworkElementIsStartedForTestSetup(nodeName);
        
//        netSimOperator = getNetSimOperator();
        NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

        int delayInSeconds=1 * 1000;
        int numberOfRetries = 5;
		for (int i=1;i<=numberOfRetries ;i++) {
            System.out.println("Using TAF to connect to netsim to SC1 node and run netsim to show alarms : Execution number " + i);
            ShowalarmCommand showAlarmCommand = NetSimCommands.showalarm();
            NetSimResult netSimResult = networkElement.exec(showAlarmCommand);
            System.out.println("Alarms on Node : " + nodeName + " :" + netSimResult.toString());
            
			delay(delayInSeconds);
        }
                  
        // SYNC NODE
        setAlarmSupervisionOnForTestSetup(nodeName);
//        getSyncNodeOperator().orderSynchroniseNode(nodeName);
        getSyncNodeOperator().synchroniseNode(nodeName);
        
        Assert.assertTrue(getSyncNodeOperator().checkNodeIsSynchronised(nodeName),"Node " +  nodeName + " is not synchronized. Cannot issue certificate");
       
        
//        final String expectedResult = NscsServiceGetter.SECADM_CERTIFICATE_ISSUE_CREATE_SUCCESS;
//        
//        executeScriptEngineCommandWithFileAndVerifyResponseContainsExpectedContent(
//        		CertificateIssueOperatorImpl.getCertificateIssueCommand(NscsServiceGetter.CERT_TYPE_IPSEC, NscsServiceGetter.CERT_NODE_FILE_XML),
//        		NscsServiceGetter.CERT_NODE_FILE_XML,
//        		createByteArrayWithXmlNodeFileForSecadmCommand(String.format(NscsServiceGetter.CERT_NODE_FILE_XML_CONTENT, nodeName)),
//        		expectedResult);
        
        // Delete temporary node for netsim test
        deleteNode(nodeName);
        		
    }
        
}
