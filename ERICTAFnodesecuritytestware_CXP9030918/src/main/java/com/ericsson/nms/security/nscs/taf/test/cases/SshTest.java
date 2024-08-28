package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.DataDriven;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.netsim.NetSimResult;
import com.ericsson.cifwk.taf.handlers.netsim.commands.NetSimCommands;
import com.ericsson.cifwk.taf.handlers.netsim.commands.ShowalarmCommand;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.oss.netsim.operator.cm.NetSimCmOperatorImpl;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class SshTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(SshTest.class);


    @DataDriven(name = "Complete_Node_List")
    @Test(groups = {"Acceptance"})
    public void sshTest(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList){

        System.out.println(" \n\n ############# Starting test sshSC1Test ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        final String nodeName=nodeArray[7];

        int numberOfRetries=5;
        int delayInSeconds=1 * 1000;

        Host sc1Host = HostConfigurator.getSC1();
        printHost(sc1Host);
        Host sc2Host = HostConfigurator.getSC2();
        printHost(sc2Host);

        for (int i=1;i<=numberOfRetries;i++) {
            System.out.println("SSH to SC1 node and Executing command : Execution number " + i);
            executeCommandOnServerViaSshAndExit(HostConfigurator.getSC1(), "pwd");
            delay(delayInSeconds);
        }

        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();
        Host netsimHost = HostConfigurator.getNetsim();
        printHost(netsimHost);

        System.out.println("netsim IP" + netsimHost.getIp());
        System.out.println("netsim original IP" +netsimHost.getOriginalIp());
        System.out.println("netsim port" + netsimHost.getPort());

        for (int i=1;i<=numberOfRetries;i++) {
            System.out.println("SSH to netsim node and Executing command pwd: Execution number " + i);
            executeCommandOnServerViaSshAndExit(netsimHost, "pwd");
            executeCommandOnServerViaSshAndExit(netsimHost, "cat /etc/hosts | grep netsim");

            delay(delayInSeconds);
        }

        for (int i=1;i<=numberOfRetries;i++) {
            System.out.println("SSH to SC2 node and Executing command : Execution number " + i);
            executeCommandOnServerViaSshAndExit(HostConfigurator.getSC2(), "pwd");
            delay(delayInSeconds);
        }




        netSimOperator = getNetSimOperator();
        NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

        for (int i=1;i<=numberOfRetries;i++) {
            System.out.println("Using TAF to connect to netsim to SC1 node and run netsim to show alarms : Execution number " + i);
            ShowalarmCommand showAlarmCommand = NetSimCommands.showalarm();
            NetSimResult netSimResult = networkElement.exec(showAlarmCommand);
            System.out.println("Alarms on Node : " + nodeName + " :" + netSimResult.toString());
            delay(delayInSeconds);
        }

        String moForSetCommand = "ManagedElement=1,SystemFunctions=1,Security=1";
        Map<String,String> attributeNameAndValue = new HashMap<>();
        attributeNameAndValue.put("fileTransferClientMode", "0");
        String attributeAndValueList = createAttributeNameAndValueListForSetMoAttributeCommand(attributeNameAndValue);

        for (int i=1;i<=numberOfRetries;i++) {
            System.out.println("Using TAF to connect to netsim to SC1 node and run netsim to set moattribute : Execution number " + i);
            setMoAttribute(networkElement, moForSetCommand, attributeAndValueList);
            delay(delayInSeconds);
        }





     }




}
