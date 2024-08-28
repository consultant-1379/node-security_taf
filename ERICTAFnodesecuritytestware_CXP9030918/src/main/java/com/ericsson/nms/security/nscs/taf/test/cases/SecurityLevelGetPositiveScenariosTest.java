package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.*;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class SecurityLevelGetPositiveScenariosTest extends NodeSecurityTestCaseHelper {

    private static Logger logger = Logger.getLogger(SecurityLevelGetPositiveScenariosTest.class);

    @DataDriven(name = "1_Node")
    @Test(groups = {"Acceptance"})
    public void securityLevelGetResponsesOneNode(
            @Input("nodeName") final String nodeName,
            @Input("ipAddress") final String ipAddress) {

            System.out.println(" \n\n ############# Starting test securityLevelGetResponsesOneNode ############# \n\n");

        Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");

        // Get security level using cmedit security MO and then parse the result and extract the security level
        final String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);
        String securityLevelInSecadmFormat=convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);

        // Get security level using secadm securitylevel get and then parse the result and extract the security level
        String httpResponseBody=getSecurityCommandsOperator().getSecurityLevel(nodeName).getBody();


        //check response contains nodename and the expected security level
        Assert.assertEquals(httpResponseBody.contains(nodeName),true,"Test Case Failure : " + nodeName + " : not found in Response ");
        Assert.assertEquals(httpResponseBody.contains(securityLevelInSecadmFormat),true,"Test Case Failure : " + securityLevelInSecadmFormat + " : not found in Response ");

        String nodeNameWithMeContext="MeContext="+nodeName;
        httpResponseBody=getSecurityCommandsOperator().getSecurityLevel(nodeNameWithMeContext).getBody();

        //check response contains nodename and the expected security level
        Assert.assertEquals(httpResponseBody.contains(nodeNameWithMeContext),true,"Test Case Failure : " + nodeNameWithMeContext + " : not found in Response ");
        Assert.assertEquals(httpResponseBody.contains(securityLevelInSecadmFormat),true,"Test Case Failure : " + securityLevelInSecadmFormat + " : not found in Response ");

        String nodeNameWithNetworkElement="NetworkElement="+nodeName;
        httpResponseBody=getSecurityCommandsOperator().getSecurityLevel(nodeNameWithNetworkElement).getBody();

        //check response contains nodename and the expected security level
        Assert.assertEquals(httpResponseBody.contains(nodeNameWithNetworkElement),true,"Test Case Failure : " + nodeNameWithNetworkElement + " : not found in Response ");
        Assert.assertEquals(httpResponseBody.contains(securityLevelInSecadmFormat),true,"Test Case Failure : " + securityLevelInSecadmFormat + " : not found in Response ");

     }


    @DataDriven(name = "1_Node")
    @Test(groups = {"Acceptance"})
    public void securityLevelGetResponsesOneNodeUsingShortArgumentNames(
            @Input("nodeName") final String nodeName,
            @Input("ipAddress") final String ipAddress) {

        System.out.println(" \n\n ############# Starting test securityLevelGetResponsesOneNodeUsingShortArgumentNames ############# \n\n");

        Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");

        // Get security level using cmedit security MO and then parse the result and extract the security level
        final String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);
        final String securityLevelInSecadmFormat=convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);

        //Use short versions of arguments in secadm command on command line
        // Get security level using secadm sl get -n and then parse the result and extract the security level
        String secadmGetCommand= String.format(NscsServiceGetter.SECADM_SL_GET_COMMAND_SHORT, nodeName);
        String httpResponseBody=getScriptEngineOperator().runCommand(secadmGetCommand).getBody();

        //check response contains nodename and the expected security level
        Assert.assertEquals(httpResponseBody.contains(nodeName),true,"Test Case Failure : " + nodeName + " : not found in Response ");
        Assert.assertEquals(httpResponseBody.contains(securityLevelInSecadmFormat),true,"Test Case Failure : " + securityLevelInSecadmFormat + " : not found in Response ");

        String nodeNameWithMeContext="MeContext="+nodeName;

        secadmGetCommand= String.format(NscsServiceGetter.SECADM_SL_GET_COMMAND_SHORT, nodeNameWithMeContext);
        httpResponseBody=getScriptEngineOperator().runCommand(secadmGetCommand).getBody();

        //check response contains nodename and the expected security level
        Assert.assertEquals(httpResponseBody.contains(nodeName),true,"Test Case Failure : " + nodeName + " : not found in Response ");
        Assert.assertEquals(httpResponseBody.contains(securityLevelInSecadmFormat),true,"Test Case Failure : " + securityLevelInSecadmFormat + " : not found in Response ");

        String nodeNameWithNetworkElement="NetworkElement="+nodeName;

        secadmGetCommand= String.format(NscsServiceGetter.SECADM_SL_GET_COMMAND_SHORT, nodeNameWithNetworkElement);
        httpResponseBody=getScriptEngineOperator().runCommand(secadmGetCommand).getBody();

        //check response contains nodename and the expected security level
        Assert.assertEquals(httpResponseBody.contains(nodeName),true,"Test Case Failure : " + nodeName + " : not found in Response ");
        Assert.assertEquals(httpResponseBody.contains(securityLevelInSecadmFormat),true,"Test Case Failure : " + securityLevelInSecadmFormat + " : not found in Response ");

    }


    @DataDriven(name = "5_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelGetResponsesMultipleNode(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList)
    {

        System.out.println(" \n\n ############# Starting test securityLevelGetResponsesMultipleNode ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        for (String nodeName :  nodeArray) {
            Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");
        }

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromCmeditCommand = new HashMap();

        for (String nodeName :  nodeArray) {
            String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);
            String securityLevelInSecadmFormat=convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);
            nodeNamesAndSecurityLevelsMapFromCmeditCommand.put(nodeName, securityLevelInSecadmFormat);
        }

        // Ensure nodeNamesAndSecurityLevelsMapFromCmeditCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromCmeditCommand.size(), nodeArray.length, "Number of nodes and size of Map returned from cmedit mismatch ");


        final String httpResponseBody = getSecurityCommandsOperator().getSecurityLevel(nodeList).getBody();

        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

        // Ensure nodeNamesAndSecurityLevelsMapFromSecadmCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),nodeArray.length,"Number of nodes and size of Map returned from secadm mismatch ");

        //Check the security level returned from cmedit query is the same as the security level return from secadm for each Node
        for (String nodeName :  nodeArray) {
            Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName),
                    nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName),
                    "Security levels not matching from cmedit and secadm");
        }

    }


    @DataDriven(name = "5_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelGetMultipleNodeSpecifySecurityLevelSL1(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList)
    {
        System.out.println(" \n\n ############# Starting test securityLevelGetMultipleNodeSpecifySecurityLevelSL1 ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        int countOfNodesAtSL1 = nodeArray.length;

        //Check nodes required for test exist and are synched and credentials exist
        for (String nodeName : nodeArray ) {
            Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");
        }

        //Get the security level for the nodes using cmedit

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromCmeditCommand = new HashMap();

        for (String nodeName : nodeArray) {

            String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);
            String securityLevelInSecadmFormat = convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);

            if (securityLevelInSecadmFormat.contains("1")) {
                nodeNamesAndSecurityLevelsMapFromCmeditCommand.put(nodeName, securityLevelInSecadmFormat);
            } else {
                countOfNodesAtSL1--;
            }
        }
        // Ensure nodeNamesAndSecurityLevelsMapFromCmeditCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromCmeditCommand.size(), countOfNodesAtSL1, "Number of nodes and size of Map returned from cmedit command mismatch ");

        //execute get sl on node list provided to get the security level from secadm command
        final SecurityCommandsOperator securityCommandsOperator = getSecurityCommandsOperator();

        String httpResponseBody = securityCommandsOperator.getSecurityLevel(nodeList,"1").getBody();

        ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);

        Map<String,String> nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

        // Ensure nodeNamesAndSecurityLevelsMapFromSecadmCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),countOfNodesAtSL1,"Number of nodes and size of Map returned from secadm mismatch ");

        //Check the security level returned from cmedit query is the same as the security level return from secadm for each Node
        for (String nodeName : nodeArray) {
            Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName),
                    nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName),
                    "Security levels not matching from cmedit and secadm");
        }
    }

    @DataDriven(name = "5_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelGetMultipleNodeSpecifySecurityLevelSL2(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList)
    {
        System.out.println(" \n\n ############# Starting test securityLevelGetMultipleNodeSpecifySecurityLevelSL2 ############# \n\n");

        final String[] nodeArray = nodeList.split(",");
        int countOfNodesAtSL2 = nodeArray.length;

//        Check nodes required for test exist and are synched and credentials exist
        for (String nodeName : nodeArray ) {
            Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");
        }

        //Get the security level for the nodes using cmedit

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromCmeditCommand = new HashMap();

        for (String nodeName : nodeArray) {

            String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);
            String securityLevelInSecadmFormat = convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);

            if (securityLevelInSecadmFormat.contains("2")) {
                nodeNamesAndSecurityLevelsMapFromCmeditCommand.put(nodeName, securityLevelInSecadmFormat);
            } else {
                countOfNodesAtSL2--;
            }
        }
        // Ensure nodeNamesAndSecurityLevelsMapFromCmeditCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromCmeditCommand.size(), countOfNodesAtSL2, "Number of nodes and size of Map returned from cmedit command mismatch ");

        //execute get sl on node list provided to get the security level from secadm command
        final SecurityCommandsOperator securityCommandsOperator = getSecurityCommandsOperator();

        String httpResponseBody = securityCommandsOperator.getSecurityLevel(nodeList,"2").getBody();

        ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);

        Map<String,String> nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

        // Ensure nodeNamesAndSecurityLevelsMapFromSecadmCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),countOfNodesAtSL2,"Number of nodes and size of Map returned from secadm mismatch ");

        //Check the security level returned from cmedit query is the same as the security level return from secadm for each Node
        for (String nodeName : nodeArray) {
            Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName),
                    nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName),
                    "Security levels not matching from cmedit and secadm");
        }
    }


    @Test(groups={"Acceptance"})
    public void securityLevelGetAllNodeSpecifySecurityLevelSL1AndSL2(){

        System.out.println(" \n\n ############# Starting test securityLevelGetAllNodeSpecifySecurityLevelSL1AndSL2 ############# \n\n");

        //Get nodes at Security Level 1 using cmedit  and store in a set
        Set<String> nodesAtASpecificSecurityLevelUsingCmedit = getAllNodesAtASpecificSecurityLevelUsingCmedit(NscsServiceGetter.LEVEL_1);

        //Get nodes at Security Level 1 using secadm  and store in map

        final SecurityCommandsOperator securityCommandsOperator = getSecurityCommandsOperator();
        String httpResponseBody = securityCommandsOperator.getSecurityLevelForAllNodes("1").getBody();
        ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);

        Map<String,String> nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

        Assert.assertEquals(nodesAtASpecificSecurityLevelUsingCmedit.size(),nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),
                "Test Case Failure : number of nodes retireved from cmedit and secadm are different");

        // check all nodes returned by cmedit are also returned by secadm

        if (nodesAtASpecificSecurityLevelUsingCmedit.size()!=0){
            for (String nodeName : nodesAtASpecificSecurityLevelUsingCmedit){
                System.out.println("Checking :" + nodeName + " : returned from cmedit is also returned from secadm for that security level" );
                Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.containsKey(nodeName),
                        true,
                        "Test Case Failure : node retrieved from cmedit not retrieved by secadm sl get command");
            }
        }

        //Get nodes at Security Level 2 using cmedit and store in a set

        nodesAtASpecificSecurityLevelUsingCmedit = getAllNodesAtASpecificSecurityLevelUsingCmedit(NscsServiceGetter.LEVEL_2);

        //Get nodes at Security Level 2 using secadm and store in map

        httpResponseBody = securityCommandsOperator.getSecurityLevelForAllNodes("2").getBody();
        responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);

        nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

        Assert.assertEquals(nodesAtASpecificSecurityLevelUsingCmedit.size(),nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),
                "Test Case Failure : number of nodes retrieved from cmedit and secadm are different");

        // check all nodes returned by cmedit are also returned by secadm

        if (nodesAtASpecificSecurityLevelUsingCmedit.size()!=0){

            for (String nodeName : nodesAtASpecificSecurityLevelUsingCmedit){
            //    System.out.println("Checking :" + nodeName + " : returned from cmedit is also returned from secadm for that security level" );

                Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.containsKey(nodeName),
                        true,
                        "Test Case Failure : node retrieved from cmedit not retrieved by secadm sl get command");
            }
        }

    }



    @Test(groups={"Acceptance"})
    public void securityLevelGetAllUsingStar()
    {

        System.out.println(" \n\n ############# Starting test securityLevelGetAllUsingStar ############# \n\n");

        //TODO This is all based on assumption that MeContext and NetworkElement will use the same name

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromCmeditCommand = new HashMap();

        final String cmeditGetAllSecurityMosCommand=NscsServiceGetter.CMEDIT_GET_ALL_SECURITY_MOS_COMMAND;

        String httpResponseBody=getScriptEngineOperator().runCommand(cmeditGetAllSecurityMosCommand).getBody();
        ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        Set<String> SecurityFdnSet = responseDtoWrapper.fetchFdnOfMosFromResponseFromCmeditMoQuery();

        Set<String> nodesWithSecurityMo = new HashSet();

        for (String fdn : SecurityFdnSet){
            nodesWithSecurityMo.add(fdn.replace("MeContext=","").replace(",ManagedElement=1,SystemFunctions=1,Security=1",""));
        }

        //TODO Limit to maybe first 100 in Set ? probably need to do all ?
        for (String fdn : nodesWithSecurityMo ) {
            String nodeName=fdn.trim();
            String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);
            String securityLevelInSecadmFormat=convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);
            nodeNamesAndSecurityLevelsMapFromCmeditCommand.put(nodeName, securityLevelInSecadmFormat);
        }

        final String secadmGetCommand=NscsServiceGetter.SECADM_SL_GET_STAR_COMMAND;
        httpResponseBody=getScriptEngineOperator().runCommand(secadmGetCommand).getBody();
        responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

//        Ensure nodeNamesAndSecurityLevelsMapFromSecadmCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),nodesWithSecurityMo.size(),
                "Number of nodes with Security MO and size of Map from secadm mismatch ");

        for (String nodeName : nodeNamesAndSecurityLevelsMapFromCmeditCommand.keySet() ) {

            Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName),
                    nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName),
                    "Security levels not matching from cmedit and secadm for node " + nodeName + ":" + nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName) + " " + nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName));
        }

    }


    @Test(groups={"Acceptance"})
    public void securityLevelGetAllUsingAll()
    {
        System.out.println(" \n\n ############# Starting test securityLevelGetAllUsingAll ############# \n\n");

        //TODO This is all based on assumption that MeContext and NetworkElement will use the same name

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromCmeditCommand = new HashMap();

        final String cmeditGetAllSecurityMosCommand=NscsServiceGetter.CMEDIT_GET_ALL_SECURITY_MOS_COMMAND;

        String httpResponseBody=getScriptEngineOperator().runCommand(cmeditGetAllSecurityMosCommand).getBody();
        ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        Set<String> SecurityFdnSet = responseDtoWrapper.fetchFdnOfMosFromResponseFromCmeditMoQuery();

        Set<String> nodesWithSecurityMo = new HashSet();

        for (String fdn : SecurityFdnSet){
            nodesWithSecurityMo.add(fdn.replace("MeContext=","").replace(",ManagedElement=1,SystemFunctions=1,Security=1",""));
        }

        //TODO Limit to maybe first 100 in Set ? probably need to do all ?
        for (String fdn : nodesWithSecurityMo ) {
            String nodeName=fdn.trim();
            String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);
            String securityLevelInSecadmFormat=convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);
            nodeNamesAndSecurityLevelsMapFromCmeditCommand.put(nodeName, securityLevelInSecadmFormat);
        }

        final String secadmGetCommand=NscsServiceGetter.SECADM_SL_GET_ALL_COMMAND;
        httpResponseBody=getScriptEngineOperator().runCommand(secadmGetCommand).getBody();
        responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

//        Ensure nodeNamesAndSecurityLevelsMapFromSecadmCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),nodesWithSecurityMo.size(),
                "Number of nodes with Security MO and size of Map from secadm mismatch ");

        for (String nodeName : nodeNamesAndSecurityLevelsMapFromCmeditCommand.keySet() ) {

            Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName),
                    nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName),
                    "Security levels not matching from cmedit and secadm for node " + nodeName + ":" + nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName) + " " + nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName));
        }
    }


    @Test(groups={"Acceptance"})
    public void securityLevelGetAllUsingAllShortVersion()
    {

        System.out.println(" \n\n ############# Starting test securityLevelGetAllUsingAllShortVersion ############# \n\n");

        //TODO This is all based on assumption that MeContext and NetworkElement will use the same name

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromCmeditCommand = new HashMap();

        final String cmeditGetAllSecurityMosCommand=NscsServiceGetter.CMEDIT_GET_ALL_SECURITY_MOS_COMMAND;

        String httpResponseBody=getScriptEngineOperator().runCommand(cmeditGetAllSecurityMosCommand).getBody();
        ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        Set<String> SecurityFdnSet = responseDtoWrapper.fetchFdnOfMosFromResponseFromCmeditMoQuery();

        Set<String> nodesWithSecurityMo = new HashSet();

        for (String fdn : SecurityFdnSet){
            nodesWithSecurityMo.add(fdn.replace("MeContext=","").replace(",ManagedElement=1,SystemFunctions=1,Security=1",""));
        }

        //TODO Limit to maybe first 100 in Set ? probably need to do all ?
        for (String fdn : nodesWithSecurityMo ) {
            String nodeName=fdn.trim();
            String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);
            String securityLevelInSecadmFormat=convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);
            nodeNamesAndSecurityLevelsMapFromCmeditCommand.put(nodeName, securityLevelInSecadmFormat);
        }

        final String secadmGetCommand=NscsServiceGetter.SECADM_SL_GET_ALL_COMMAND_SHORT;
        httpResponseBody=getScriptEngineOperator().runCommand(secadmGetCommand).getBody();
        responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);

        final Map<String,String> nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

//        Ensure nodeNamesAndSecurityLevelsMapFromSecadmCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),nodesWithSecurityMo.size(),
                "Number of nodes with Security MO and size of Map from secadm mismatch ");

        for (String nodeName : nodeNamesAndSecurityLevelsMapFromCmeditCommand.keySet() ) {

            Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName),
                    nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName),
                    "Security levels not matching from cmedit and secadm for node " + nodeName + ":" + nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName) + " " + nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName));
        }
    }


    @DataDriven(name = "5_Node_List")
    @Test(groups={"Acceptance"})
    public void securityLevelGetUsingFileOfNodes(
            @Input("nodeList") final String nodeList,
            @Input("ipAddressList") final String ipAddressList)       {

        System.out.println(" \n\n ############# Starting test securityLevelGetUsingFileOfNodes ############# \n\n");

        final String[] nodeArray = nodeList.split(",");

        //Check nodes required for test exist and are synched and credentials exist
        for (String nodeName : nodeArray) {
            Assert.assertEquals(checkNodeSynchedAndCredentialsExist(nodeName),true,"Test Setup Problem: " + nodeName + " : not in correct state , created, synched and credentials created to run test ");
        }


        Map<String,String> nodeNamesAndSecurityLevelsMapFromCmeditCommand = new HashMap();

        for (String nodeName : nodeArray ) {

            String securityLevelInCmeditFormat = getNodeSecurityLevelUsingCmedit(nodeName);

            String securityLevelInSecadmFormat=convertCmeditLevelFormatToSecadmLevelFormat(securityLevelInCmeditFormat);

            nodeNamesAndSecurityLevelsMapFromCmeditCommand.put(nodeName, securityLevelInSecadmFormat);
        }
        // Ensure nodeNamesAndSecurityLevelsMapFromCmeditCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromCmeditCommand.size(), nodeArray.length, "Number of nodes and size of Map returned from cmedit mismatch ");


        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeList.replace(",",";").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Assert.fail("Problem encoding file contents");
        }

        final String commandString = "secadm sl get "+
                " --nodefile " + fileName;

        final String httpResponseBody=executeScriptEngineCommandWithFile(commandString,fileName,fileContents);

        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        final Map<String,String> nodeNamesAndSecurityLevelsMapFromSecadmCommand = responseDtoWrapper.getNodeNamesAndSecurityLevelsFromSecadmSecurityLevelGetCommand();

        // Ensure nodeNamesAndSecurityLevelsMapFromSecadmCommand map has right number of nodes
        Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.size(),nodeArray.length,"Number of nodes and size of Map returned from secadm mismatch ");

        //Check the security level returned from cmedit query is the same as the security level return from secadm for each Node
        for (String nodeName : nodeArray) {
            Assert.assertEquals(nodeNamesAndSecurityLevelsMapFromSecadmCommand.get(nodeName),
                    nodeNamesAndSecurityLevelsMapFromCmeditCommand.get(nodeName),
                    "Security levels not matching from cmedit and secadm");
        }
    }
}




