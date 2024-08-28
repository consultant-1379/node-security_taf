package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.TestCase;
import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.Ports;
import com.ericsson.cifwk.taf.handlers.netsim.NetSimResult;
import com.ericsson.cifwk.taf.handlers.netsim.commands.*;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.tools.cli.Shell;
import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.*;
//import com.ericsson.oss.netsim.operator.api.NetSimCmOperator;
import com.ericsson.oss.netsim.operator.cm.NetSimCmOperatorImpl;
import com.ericsson.oss.services.scriptengine.spi.dtos.Command;

import org.apache.log4j.Logger;
import org.testng.Assert;

import javax.inject.Inject;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeSecurityTestCaseHelper extends TorTestCaseHelper implements TestCase {

    private static Logger logger = Logger
            .getLogger(NodeSecurityTestCaseHelper.class);

    static {
   	
    	// ecappie's checks: begin...
    	
    	Host apache = HostConfigurator.getApache();

    	Host cmservice = HostConfigurator.getCmService();
    	Host pmservice = HostConfigurator.getPmService();
    	
    	Host MS = HostConfigurator.getMS();  // To leave 'ms' unchanged below
    	String MSIp = MS.getIp();
    	logger.debug("MSIp = " + MSIp);
    	
        Host sc1 = HostConfigurator.getSC1();
        Host sc2 = HostConfigurator.getSC2();
        
    	Host netsim = HostConfigurator.getNetsim();
    	String netsimHostname  = netsim.getHostname();
    	String netsimIp = netsim.getIp();
    	String netsimOffset = netsim.getOffset();
    	List<Host> netsimNodes = netsim.getNodes();
    	Map<Ports,String> netsimPorts = netsim.getPort();
    	logger.debug("netsimHostname = " + netsimHostname);
    	logger.debug("netsimIp = " + netsimIp);
    	logger.debug("netsimOffset = " + netsimOffset);
    	logger.debug("netsimNodes = " + netsimNodes);
    	logger.debug("netsimPorts = " + netsimPorts);
    	
    	Host data1 = HostConfigurator.getData1();
    	Host data2 = HostConfigurator.getData2();
    	List<Host> databaseNodes = HostConfigurator.getDatabaseNodes();
    	
    	Host svc1 = HostConfigurator.getSVC1();
    	Host svc2 = HostConfigurator.getSVC2();
    	Host svc3 = HostConfigurator.getSVC3();
    	Host svc4 = HostConfigurator.getSVC3();
    	List<Host> svcNodes = HostConfigurator.getSVCNodes();
    	logger.debug("svcNodes = " + svcNodes);
    	
    	Host secServiceUnit0 = HostConfigurator.getSecServiceUnit0();
    	Host secServiceUnit1 = HostConfigurator.getSecServiceUnit1();
    	logger.debug("secServiceUnit0 = " + secServiceUnit0);
    	logger.debug("secServiceUnit1 = " + secServiceUnit1);
    	
    	Host southboundNamingServiceHost = HostConfigurator.getSouthboundNamingServiceHost();
    	
    	// ecappie's checks: ...end.
    	
        //host.properties contains the name of our simulation we ar eusing on netsimlin554 or our cloud simulation
        //check if test is executing in a cloud test environment base don the ms ip address
        //then set the simulations and ip address based on that in DataHandler.
        final Host ms =  HostConfigurator.getMS();
        Assert.assertTrue(ms!=null," HostConfigurator cant reach the CI servers to get the json configuration .i.e ipaddresses");
//            seen null pointer exceptions here as a result

        final String msIpAddress = ms.getIp();
        logger.debug("msIpAddress = " + msIpAddress);

        String simulationName = DataHandler.getAttribute("netSim.simulations").toString();
        
//        DataHandler.setAttribute("netSim.simulations", simulationName);
        logger.info("DataHandler: setting attribute 'netSim.simulations' to:" + simulationName);
//        logger.info("netSim.simulations : " + DataHandler.getAttribute("netSim.simulations"));
                
        // Some sanity checking that the data in handler is correct. Print out various Attributes being used in test output so we can check if any issues.

        logger.debug("Printing DataHandlerAttributesMap (original...)");
        Map DataHandlerAttributesMap = DataHandler.getAttributes();
        staticPrintMap(DataHandlerAttributesMap, "DataHandlerAttributesMap");

     /* Dom 12/4
     // Host netsimlin633Host = DataHandler.getHostByName("netsimlin633");
     // netsimlin633Host.setIp("192.168.0.2");
        
     // logger.debug("DataHandler.unsetAttribute...");
     // DataHandler.unsetAttribute("host.netsimlin633.ip");
     // DataHandler.unsetAttribute("host.netsimlin633.port.ssh");
     // DataHandler.unsetAttribute("host.netsimlin633.type");
     // DataHandler.unsetAttribute("host.netsimlin633.user.netsim.pass");
     // DataHandler.unsetAttribute("host.netsimlin633.user.netsim.type");
        logger.debug("DataHandler.setAttribute...");
        DataHandler.setAttribute("host.netsimlin633.ip", "192.168.0.2");
        
        logger.debug("Printing DataHandlerAttributesMap (...should be modified now!)");
        Map DataHandlerAttributesMapMod = DataHandler.getAttributes();
        staticPrintMap(DataHandlerAttributesMapMod, "DataHandlerAttributesMapMod");
      */
    }

    @Inject
    private CredentialsOperatorImpl credentialsOperator;
    
    @Inject
    private KeygenOperatorImpl keygenOperator;

    @Inject
    private SecurityCommandsOperatorImpl securityCommandsOperator;

    @Inject
    private ScriptEngineOperatorImpl scriptEngineOperator;

    @Inject
    private NodeOperatorImpl nodeOperator;

    @Inject
    private SyncNodeOperatorImpl syncNodeOperator;

    @Inject
    private MyOpenIDMOperatorImpl openIDMOperator;

//TODO, use official when updated to incude checks
//    @Inject
//    private OpenIDMOperatorImpl openIDMOperator;

    @Inject
    private LoginOperatorImpl loginOperator;

    //@Inject
    //private OperatorRegistry<LoginOperator> loginOperatorProvider;

    @Inject
    private NetSimCmOperatorImpl netSimOperator;
    //TODO: Use interface private NetSimCmOperator netSimOperator;    rather than implementation

    @Inject
    private NodeSetParametersImpl nodeParameters;
    
    private static Map<String,NetworkElement> mapNetSimNodes = new HashMap<>();

    static void staticPrintMap(final Map map, final String mapName){

        System.out.println("printing Map" + mapName);

        final Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry mapEntry = (Map.Entry)it.next();
            System.out.println(mapEntry.getKey() + " : " + mapEntry.getValue());
        }
    }


    private static final String TAIL_COMMAND = "tail -F -n0";

    private boolean netSimOperatorInitialised=false;


    protected void deleteNode(final String nodeName) {
        final NodeOperator nodeOperator = getNodeOperator();
        if (!nodeOperator.deleteNode(nodeName)) {
//        if (nodeOperator.checkNodeExists(nodeName)) {
            Assert.fail(" Problem: Node (MeContext or NetworkElement MO) Still exists even after calling delete node : " + nodeName);
        }
    }

    protected void deleteNetworkElementSecurity(final String nodeName) {
        final CredentialsOperator credentialsOperator = getCredentialsOperator();
        credentialsOperator.deleteCredentials(nodeName);
        if (credentialsOperator.checkCredentialsExist(nodeName)) {
            Assert.fail(" Problem: NetworkElementSecurityMO Still exists even after calling delete credentials : " + nodeName);
        }
    }
    protected void addNodeForTestSetup(final String nodeName, final String ipAddress) {
        final NodeOperator nodeOperator = getNodeOperator();
        final boolean nodeCreated = nodeOperator.createNode(nodeName,ipAddress);
        Assert.assertEquals(nodeCreated, true, "Test Setup Problem: Could not create the Node" + nodeName);
    }

    protected void synchroniseNodeForTestSetup(final String nodeName) {
        final SyncNodeOperator syncNodeOperator = getSyncNodeOperator();
        final boolean nodeSynched = syncNodeOperator.synchroniseNode(nodeName);
        Assert.assertEquals(nodeSynched, true, "Test Setup Problem: Could not synchronise the Node :" + nodeName);

    }

    protected void createCredentialsForTestSetup(final String nodeName) {
        final CredentialsOperator credentialsOperator = getCredentialsOperator();

        String httpResponseBody = credentialsOperator.createCredentials(nodeName, NscsServiceGetter.NETSIM_ROOT_USER_NAME,
                NscsServiceGetter.NETSIM_ROOT_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
                NscsServiceGetter.NETSIM_NORMAL_PASSWORD).getBody();
            
        if(nodeParameters.checkSgsnForCurrentNode(nodeName)){
            httpResponseBody = credentialsOperator.createCredentialsForSgsn(nodeName,
                    NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                    NscsServiceGetter.NETSIM_SECURE_PASSWORD).getBody();
        }
            

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS), "Test Setup Problem: Could not create the credentials for the Node " + nodeName);

        final boolean credentialsExist = credentialsOperator.checkCredentialsExist(nodeName);
        Assert.assertEquals(credentialsExist, true, "Test Setup Problem: Could not create the credentials for the Node " + nodeName);
    }
    
    protected void createKeygenForTestSetup(final String nodeName) {
        final KeygenOperator keygenOperator = getKeygenOperator();

        final String httpResponseBody = keygenOperator.createKeypair(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048).getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECADM_KEYGEN_CREATE_SUCCESS), "Test Setup Problem: Could not create the keys for the Node " + nodeName);
    }

    protected void createCredentialsWithWrongPasswordForTestSetup(final String nodeName) {
        final CredentialsOperator credentialsOperator = getCredentialsOperator();

        final String httpResponseBody = credentialsOperator.createCredentials(nodeName, NscsServiceGetter.NETSIM_ROOT_USER_NAME,
                "wrong_password",
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                "wrong_password",
                NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
                "wrong_password").getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS), "Test Setup Problem: Could not create the credentials for the Node " + nodeName);

        final boolean credentialsExist = credentialsOperator.checkCredentialsExist(nodeName);
        Assert.assertEquals(credentialsExist, true, "Test Setup Problem: Could not create the credentials for the Node " + nodeName);
    }

    protected void deleteAndAddNodeForTestSetup(final String nodeName, final String ipAddress) {
        deleteNode(nodeName);
        addNodeForTestSetup(nodeName, ipAddress);
    }

    protected void deleteAndAddAndSynchroniseNodeForTestSetup(final String nodeName, final String ipAddress) {
        deleteNode(nodeName);
        addNodeForTestSetup(nodeName, ipAddress);
        synchroniseNodeForTestSetup(nodeName);
    }

    protected void deleteAndAddAndSynchroniseNodeCreateCredentialsForTestSetup(final String nodeName, final String ipAddress) {
        deleteNode(nodeName);
        addNodeForTestSetup(nodeName, ipAddress);
        synchroniseNodeForTestSetup(nodeName);
        createCredentialsForTestSetup(nodeName);
    }


    protected String convertCmeditLevelFormatToSecadmLevelFormat(final String levelInCmeditFormat) {
        return levelInCmeditFormat.replace("_", " ").toLowerCase().trim();

    }


    protected String executeScriptEngineCommandWithFile(final String command, final String fileName, final byte[] fileContents) {

        final File file = new File(fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileContents);
            fileOutputStream.close();
        } catch (final Exception e) {
            logger.error(e);
            Assert.fail("problem handling file operations");
        }

        return getScriptEngineOperator().runCommand(command, file, fileName).getBody();

    }
    
    protected String executeScriptEngineCommand(final String commandString) {
        
        final HttpResponse httpResponse = getScriptEngineOperator().runCommand(commandString);
        return httpResponse != null ? httpResponse.getBody() : NscsServiceGetter.EMPTY_STRING;
    }


    protected void prepareAndExecuteScriptEngineCommandAndCheckResults(final String nodeName, final String ipAddress, final String command, final String... expectedResults) {

        //Delete node if it exists
        deleteNode(nodeName);
        addNodeForTestSetup(nodeName, ipAddress);

        final String httpResponseBody = getScriptEngineOperator().runCommand(command).getBody();

        for (String expectedResult : expectedResults) {
            if (!httpResponseBody.contains(expectedResult)) {
                Assert.assertEquals(httpResponseBody.contains(expectedResult), true, "expected response from command not as expected : ");
            }
        }

    }

    protected void deleteMeContextForTestSetup(final String nodeName){

        String commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_MECONTEXT_COMMAND, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(commandString, expectedResult);
        Assert.assertTrue(commandResult, "Problem deleting MeContext for " + nodeName);

    }

    protected void deleteSecurityFunctionForTestSetup(final String nodeName){

        String commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_SECURITYFUNCTION_COMMAND, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(commandString, expectedResult);
        Assert.assertTrue(commandResult, "Problem deleting Security Function for " + nodeName);

    }

    protected void createMeContextForTestSetup(final String nodeName) {
        
        String neType = nodeParameters.getNeTypeForCurrentNode(nodeName);
        String platformType = nodeParameters.getPlatformTypeForCurrentNode(nodeName);
        
        final String createMeContext = String.format(NscsServiceGetter.CMEDIT_CREATE_MECONTEXT_COMMAND, nodeName, nodeName,neType,platformType);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(createMeContext, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating MeContext for " + nodeName);
    }
    
    protected void createMeContextForTestSgsnSetup(final String nodeName) {

        final String createMeContext = String.format(NscsServiceGetter.CMEDIT_CREATE_MECONTEXT_SGSN_COMMAND, nodeName, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(createMeContext, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating MeContext for " + nodeName);
    }

    protected void createNetworkElementForTestSetup(final String nodeName) {

        String neType = nodeParameters.getNeTypeForCurrentNode(nodeName);
        String platformType = nodeParameters.getPlatformTypeForCurrentNode(nodeName);
   
        final String createNetworkElement = String.format(NscsServiceGetter.CMEDIT_CREATE_NETWORK_ELEMENT_COMMAND, nodeName, nodeName, neType, platformType, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;
        commandResult = getScriptEngineOperator().runCommand(createNetworkElement, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating NetworkElement for " + nodeName);
    }
    
    protected void createNetworkElementForTestSgsnSetup(final String nodeName) {

        final String createNetworkElement = String.format(NscsServiceGetter.CMEDIT_CREATE_NETWORK_ELEMENT_SGSN_COMMAND, nodeName, nodeName, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;
        commandResult = getScriptEngineOperator().runCommand(createNetworkElement, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating NetworkElement for " + nodeName);
    }

    protected void createSecurityFunctionForTestSetup(final String nodeName) {

        final String createSecurityFunction = String.format(NscsServiceGetter.CMEDIT_CREATE_SECURITY_FUNCTION_COMMAND, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(createSecurityFunction, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating SecurityFunction for " + nodeName);
    }
    
    
    
    protected void createComConnectivityInfo(final String nodeName, String ipAddress) {

    	System.out.println("Checking node " + nodeName + " on netsim");
    	
    	final NetworkElement networkElement = getNetworkElementFromNetSim(nodeName);
    	if (networkElement != null) {
    		ipAddress = networkElement.getIp();
        	System.out.println(String.format("Getting ipAddress [%s] from Netsim", ipAddress));
        	
        	String regex = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
        	Matcher matcher = Pattern.compile(regex).matcher(ipAddress);
        	if (matcher != null && matcher.find()) {
        		ipAddress = matcher.group();
        		System.out.println(String.format("ipAddress [%s]", ipAddress));
        	}
    	}    	
        
        final String createSecurityFunction = String.format(NscsServiceGetter.CMEDIT_CREATE_COM_CONNECTIVITY_INFO, nodeName, ipAddress);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(createSecurityFunction, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating ComConnectivityInfo for " + nodeName);
    }
    
    protected void createCredentialsForSgsnTestSetup(final String nodeName) {

        final String createSecurityFunction = String.format(NscsServiceGetter.SECADM_CREATE_CREDENTIAL_FOR_SGSN, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS;

        commandResult = getScriptEngineOperator().runCommand(createSecurityFunction, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating credentials for " + nodeName);
    }
    
    protected void createCmFunctionForTestSetup(final String nodeName) {

        final String createSecurityFunction = String.format(NscsServiceGetter.CMEDIT_CREATE_CM_FUNCTION_COMMAND, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(createSecurityFunction, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating CmFunction for " + nodeName);
    }

    protected void createCppConnectivityInfoForTestSetup(final String nodeName, final String ipAddress) {

        String createSecurityFunction;
        
        if(nodeParameters.checkSgsnForCurrentNode(nodeName)){
            createSecurityFunction = String.format(NscsServiceGetter.CMEDIT_CREATE_COM_CONNECTIVITY_INFO_COMMAND, nodeName, ipAddress);
        } else {
            createSecurityFunction = String.format(NscsServiceGetter.CMEDIT_CREATE_CPP_CONNECTIVITY_INFO_COMMAND, nodeName, ipAddress);
        }

        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(createSecurityFunction, expectedResult);
        Assert.assertTrue(commandResult, "Problem creating CmFunction for " + nodeName);
    }





    protected void setAlarmSupervisionOnForTestSetup(final String nodeName) {

        final String setAlarmSupervisionOn = String.format(NscsServiceGetter.FMEDIT_ALARM_SUPERVISION_COMMAND, nodeName, "true");
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(setAlarmSupervisionOn, expectedResult);
        Assert.assertTrue(commandResult, "Problem turning on Alarm Supervision for " + nodeName);
    }

    protected void checkNetworkElementIsStartedForTestSetup(final String nodeName) {

        final NetworkElement networkElement = getNetSimOperator().getNetworkElement(nodeName);
        System.out.println("netSim.simulations : " + DataHandler.getAttribute("netSim.simulations"));
        System.out.println("netsim server ipaddress : " + DataHandler.getAttribute("host.ms1.node.Netsim1.ip"));

        Assert.assertTrue(networkElement != null, "Test Setup Problem locating NE ::: " + nodeName + "::: on the netsim server " + DataHandler.getAttribute("host.ms1.node.Netsim1.ip") + "simulations :" + DataHandler.getAttribute("netSim.simulations"));
        Assert.assertTrue(networkElement.isStarted(), "Test Environment Problem with NE ::: " + nodeName + "::: on the netsim server, It is not started on the netsim server");
        System.out.println(" ********* " + nodeName + " : is started on Netsim" + " ********* ");
    }


    protected boolean addNodeAndExecuteScriptEngineCommandAndCheckResults(final String nodeName, final String ipAddress, final String command, final String... expectedResults) {

        //Delete node if it exists
        deleteNode(nodeName);

        addNodeForTestSetup(nodeName, ipAddress);

        final String httpResponseBody = getScriptEngineOperator().runCommand(command).getBody();

        for (String expectedResult : expectedResults) {
            if (!httpResponseBody.contains(expectedResult)) {
                return false;
            }
        }
        return (true);

    }

    protected Set<String> getAllNodesAtASpecificSecurityLevelUsingCmedit(final String level) {

        final String commandString = String.format(NscsServiceGetter.CMEDIT_GET_ALL_NODES_AT_SECURITY_LEVEL, level);
        final String httpResponseBody = getScriptEngineOperator().runCommand(commandString).getBody();
        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        final Set<String> nodeListWithFullFdn = responseDtoWrapper.fetchFdnOfMosFromResponseFromCmeditMoQuery();

        final Set<String> nodeList = new HashSet<>();

        for (String fdn : nodeListWithFullFdn) {
            fdn = fdn.replace("MeContext=", "");
            fdn = fdn.replace(",ManagedElement=1,SystemFunctions=1,Security=1", "");
            nodeList.add(fdn.trim());
        }

        return nodeList;

    }


    protected String getNodeSecurityLevelUsingCmedit(final String nodeName) {

        //TODO use this
        // getNodeAttributeValueUsingCmedit(nodeName,"Security",NscsServiceGetter.CMEDIT_SECURITY_LEVEL_ATTRIBUTE);

        final String commandString = String.format(NscsServiceGetter.CMEDIT_GET_SECURITY_LEVEL, nodeName);
        final String httpResponseBody = getScriptEngineOperator().runCommand(commandString).getBody();
        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        return responseDtoWrapper.fetchSingleAttributeValueForAttributeNameFromResponse(NscsServiceGetter.CMEDIT_SECURITY_LEVEL_ATTRIBUTE).trim();

    }

    protected String getNodeAttributeValueUsingCmedit(final String nodeName, final String motype, final String attributeName) {
        final String commandString = String.format(NscsServiceGetter.CMEDIT_GET_ATTRIBUTE, nodeName, motype, attributeName);
        final String httpResponseBody = getScriptEngineOperator().runCommand(commandString).getBody();
        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        return responseDtoWrapper.fetchSingleAttributeValueForAttributeNameFromResponse(attributeName).trim();

    }

    protected String getNodeAlarmsUsingFmedit(final String nodeName) {
        final String commandString = String.format(NscsServiceGetter.FMEDIT_ALARM_LIST_COMMAND, nodeName);
        final String httpResponseBody = getScriptEngineOperator().runCommand(commandString).getBody();
        return httpResponseBody;
       // final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
       // return responseDtoWrapper.fetchSingleAttributeValueForAttributeNameFromResponse(attributeName).trim();

    }

    protected boolean checkNodeSynchedAndCredentialsExist(final String nodeName) {
        //Check node exists
        final boolean nodeExists = getNodeOperator().checkNodeExists(nodeName);
        if (!nodeExists) {
            return false;
        }
        //Check node is synchronised
        final boolean nodeSynched = getSyncNodeOperator().checkNodeIsSynchronised(nodeName);
        if (!nodeSynched) {
            return false;
        }
        //Check credentials exist
        return getCredentialsOperator().checkCredentialsExist(nodeName);

    }

    protected CredentialsOperator getCredentialsOperator() {
        return credentialsOperator;
    }

    protected KeygenOperator getKeygenOperator() {
        return keygenOperator;
    }
    
    protected SecurityCommandsOperator getSecurityCommandsOperator() {
        return securityCommandsOperator;
    }

    protected ScriptEngineOperator getScriptEngineOperator() {
        return scriptEngineOperator;
    }

    protected NodeOperator getNodeOperator() {
        return nodeOperator;
    }

    protected SyncNodeOperator getSyncNodeOperator() {
        return syncNodeOperator;
    }

    protected LoginOperator getLoginOperator() {
        return loginOperator;
    }

    protected MyOpenIDMOperator getMyOpenIDMOperator() {
        return openIDMOperator;
    }

    protected MyOpenIDMOperator getOpenIDMOperator() {
        return openIDMOperator;
    }

    protected NetSimCmOperatorImpl getNetSimOperator() {
        return netSimOperator;
    }

    protected void printMap(final Map map, final String mapName){

        System.out.println("printing Map" + mapName);

        final Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry mapEntry = (Map.Entry)it.next();
            System.out.println(mapEntry.getKey() + " : " + mapEntry.getValue());
        }
    }

    protected void delay(final long timeInMillis) {
        try {
            logger.info(String.format("Sleeping for %d milliseconds", timeInMillis));
            Thread.sleep(timeInMillis);
        } catch (final InterruptedException e) {
            logger.error(String.format("Caught InterruptedException: %s", e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }
    }

    protected void tailMultipleFilesOnHost(final Host host, final String fileNamesWithFullPathToTail, final String fileExtensionToAppendToFileName, final int sleepTime) {
        String[] fileNames = splitCommaSeparatedStringIntoArray(fileNamesWithFullPathToTail);
        for (String fileNameWithFullPathToTail : fileNames) {
            startTailingFileOnServer(host, fileNameWithFullPathToTail, fileExtensionToAppendToFileName, sleepTime);
        }
    }

    protected void startTailingFileOnServer(Host host, String fileNameWithFullPathToTail, String fileNameWithFullPathToTailContentTo, int sleepTime) {

        System.out.println("Attempting to kill any existing process that might be tailing this log and attempting to Delete any existing log file");
        stopTailingLogFileOnServer(host, fileNameWithFullPathToTail);
        deleteFile(host,fileNameWithFullPathToTailContentTo);

        final CLICommandHelper ch = new CLICommandHelper(host);
        Shell s = ch.openShell();

        final String command = String.format("nohup %s %s > %s ", TAIL_COMMAND, fileNameWithFullPathToTail,
                fileNameWithFullPathToTailContentTo);
        System.out.println(String.format(host.getHostname() + " : Running %s ", command));
        s.writeln(command);

        delay(sleepTime);

//TODO Suggested by TAf group but did not work, need better way in case of load on system.
        //TODO check if file exists on host using RemoteFileHandler ? Then continue if it does or retry until does or timeout
//        s.writeln("echo MARKER");
//        s.expect("MARKER");
        System.out.println(host.getHostname() + " : disconnecting Shell ");

        s.disconnect();
    }



    protected String[] splitCommaSeparatedStringIntoArray(String commaSeparatedString) {
        if (commaSeparatedString.contains(",")) {
            String[] stringArray = commaSeparatedString.split(",");
            return stringArray;
        }
        return new String[]{commaSeparatedString};
    }

    protected void stopTailingMultipleFilesOnServer(Host host, String fileNamesWithFullPathBeingTailed) {


        String[] files = splitCommaSeparatedStringIntoArray(fileNamesWithFullPathBeingTailed);
        for (String fileNameWithFullPathBeingTailed : files) {
            stopTailingLogFileOnServer(host, fileNameWithFullPathBeingTailed);
        }
    }
    

    protected void stopTailingLogFileOnServer(final Host host, final String fileNameWithFullPathBeingTailed) {
        final CLICommandHelper ch = new CLICommandHelper(host);

        //check all process with our tail command on node
        String command = String.format("ps -ef | grep \"%s\" | grep -v grep", TAIL_COMMAND);
        String result = ch.simpleExec(command);
        logger.debug(host.getHostname() + " : Result of executing " + command + " : " + result);

        //Fetch all process ids with our tail command and with the file we want to start tailing on
        command = String.format("ps -ef | grep \"%s\" | grep -v grep | grep %s | awk \'{ print $2 }\'", TAIL_COMMAND, fileNameWithFullPathBeingTailed);
        final String pid = ch.simpleExec(command).trim();

        //kill the process tailing the log
        if (!pid.equals("")) {
            command = String.format("kill -9 %s", pid);
            result = ch.simpleExec(command);
            System.out.println(host.getHostname() + ": " + command + "\n Process Id retrieved : " + pid);
        } else {
            System.out.println(host.getHostname() + ": No matching Process Id found after executing command : " + command + " : " + result);
        }
        ch.interactWithShell("exit"); //Close the shell process
    }

    protected void deleteFile(final Host host, final String fileNameWithFullPath) {
        executeCommandOnServerViaSshAndExit(host, String.format("rm -f %s", fileNameWithFullPath));
    }

    protected String getFileContentThatMatchFilter(final Host host, final String fileNameWithFullPath, final String filter) {
        return executeCommandOnServerViaSshAndExit(host, String.format("grep -i \"%s\" %s", filter, fileNameWithFullPath));
    }

    protected String getNumberOfOccurencesThatMatchFilter(final Host host, final String fileNameWithFullPath, final String filter) {
        return executeCommandOnServerViaSshAndExit(host, String.format("grep -c \"%s\" %s", filter, fileNameWithFullPath));
    }

    protected String executeCommandOnServerViaSshAndExit(final Host host, final String command) {
    	String result = "";
        CLICommandHelper ch = new CLICommandHelper(host);
        logger.info(host.getHostname() + ": Executing command : " + command);
        result = ch.simpleExec(command);
        logger.info(host.getHostname() + ": Result from command : " + command + " :" + result);
        ch.interactWithShell("exit");
        return result;
    }


    protected String createAttributeNameAndValueListForSetMoAttributeCommand(final Map<String, String> attributeNameAndValueMap) {
        String attributeNameAndValueList = "";
        final String separator = "||";

        final Object[] keyArray = attributeNameAndValueMap.keySet().toArray();

        for (int i = 0; i < keyArray.length; i++) {

            attributeNameAndValueList = attributeNameAndValueList + keyArray[i] + "=" + attributeNameAndValueMap.get(keyArray[i]);
            if (!(i == keyArray.length - 1)) {
                attributeNameAndValueList = attributeNameAndValueList + separator;
            }
        }
        return attributeNameAndValueList;
    }

    //Tested with a string, boolean and an integer values for attributes
    protected void setMoAttribute(final NetworkElement networkElement, final String MoFdn, final Map<String, String> attributeNameAndValueMap){

        final String attributeNameAndValueList = createAttributeNameAndValueListForSetMoAttributeCommand(attributeNameAndValueMap);
        final SetmoattributeCommand setMoAttributeCommand = NetSimCommands.setmoattribute(MoFdn, attributeNameAndValueList);
        final NetSimResult netSimResult = networkElement.exec(setMoAttributeCommand);
        logger.info(netSimResult.toString());
    }

    protected void setMoAttribute(final NetworkElement networkElement, final String MoFdn, final String attributeNameAndValueList){

        final SetmoattributeCommand setMoAttributeCommand = NetSimCommands.setmoattribute(MoFdn, attributeNameAndValueList);
        final NetSimResult netSimResult = networkElement.exec(setMoAttributeCommand);
        logger.info(netSimResult.toString());
    }

    protected void VerifyResponseContainsExpectedContent(String httpResponseBody, String expectedResponse) {

        Assert.assertTrue(httpResponseBody.contains(expectedResponse), "Test Case Failure : Did not receive expected message : " + expectedResponse + " :  in " + httpResponseBody);

    }

    protected void verifyResponseContainsExpectedContent(String httpResponseBody, String expectedError, String expectedSuggestedSolution) {

        Assert.assertTrue(httpResponseBody.contains(expectedError), "Test Case Failure : Did not receive expected message : " + expectedError + " :  in " + httpResponseBody);
        Assert.assertTrue(httpResponseBody.contains(expectedSuggestedSolution), "Test Case Failure : Did not receive expected message : " + expectedSuggestedSolution + " :  in " + httpResponseBody);

    }
    
    
    protected void verifyResponseContainsExactErrorAndSuggestedSolution(String httpResponseBody, String expectedError, String expectedSuggestedSolution) {
        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        Map<String, String> errorSuggestedSolutionMap = responseDtoWrapper.fetchErrorAndSuggestSolutionForSingleNodeErrorScenarioFromResponse();

        Assert.assertTrue(errorSuggestedSolutionMap.get("Error").equals(expectedError),
                "Test Case Failure : Did not receive expected error :" + expectedError
                        + " Received " + errorSuggestedSolutionMap.get("Error") + " : " + errorSuggestedSolutionMap.get("SuggestedSolution"));

        Assert.assertTrue(errorSuggestedSolutionMap.get("SuggestedSolution").equals(expectedSuggestedSolution),
                "Test Case Failure : Did not receive expected Solution :" + expectedSuggestedSolution
                        + ": Received " + errorSuggestedSolutionMap.get("Error") + " : " + errorSuggestedSolutionMap.get("SuggestedSolution"));
    }


    protected boolean checkAttributeValueMatchesOrTimeout(final String nodeName, String moType, final String attributeName, final String wantedAttributeValue, final int timeout, final int numberOfTimesToCheck) {
        String attributeValue = "";
        boolean attributeValueFound = false;
        // check every 5 seconds if attribute is set
        int waitTime = timeout;
        for (int i = 0; i < numberOfTimesToCheck; i++) {
            attributeValue = getNodeAttributeValueUsingCmedit(nodeName, moType, attributeName);
            if (attributeValue.equals(wantedAttributeValue)) {
                attributeValueFound = true;
                logger.info("\n\nFound Wanted Attribute Value :" + attributeName + " : " + attributeValue  );
                break;
            } else {
                try {
                    logger.info("\n\nFound :" + attributeName + " : " + attributeValue +  " : Waiting for " + wantedAttributeValue );
                    logger.info("\n\nWaiting " + waitTime + " seconds before checking attribute " + attributeName + " on " + nodeName + " Sleeping : sleep number + " + i + "\n\n");
                    Thread.sleep(waitTime * 1000);
                } catch (final InterruptedException e) {
                        throw new RuntimeException(e.getMessage());
                }
            }
        }
        return attributeValueFound;
    }


    protected boolean checkAlarmReceivedOrTimeout(final String nodeName, final String wantedAlarm, final int timeout, final int numberOfTimesToCheck) {
        boolean alarmFound = false;
        String rawHttpResponseBody = getNodeAlarmsUsingFmedit(nodeName);
        return alarmFound;

        //TODO
        //Check if alarm has been received in FM using fmedit commands before checking our server logs
        // check every 5 seconds if attribute is set
//        int waitTime = timeout;
//        for (int i = 0; i < numberOfTimesToCheck; i++) {
//            String rawHttpResponseBody = getNodeAlarmsUsingFmedit(nodeName);  //TODO
//
//            if (attributeValue.equals(wantedAttributeValue)) {
//                alarmFound = true;
//                logger.info("\n\nFound Attribute Value waiting for:" + attributeName + " : " + attributeValue  );
//                break;
//            } else {
//                try {
//                    logger.info("\n\nFound :" + attributeName + " : " + attributeValue +  " : Waiting for " + wantedAttributeValue );
//                    logger.info("\n\nWaiting " + waitTime + " seconds before checking attribute " + attributeName + " on " + nodeName + " Sleeping : sleep number + " + i + "\n\n");
//                    Thread.sleep(waitTime * 1000);
//                } catch (final InterruptedException e) {
//                    throw new RuntimeException(e.getMessage());
//                }
//            }
//        }
//        return alarmFound;
    }

    //These methods should all have been provided by FM operators
    protected void clearAlarmsOnNodeInENMAndOnNE (final String nodeName)
    {
        removeAlarmsOnNodeInENM(nodeName);
        clearAlarmsOnNE(nodeName);

    }

    protected void removeAlarmsOnNodeInENM(final String nodeName) {
        acknowledgeAlarmsOnNodeInENM(nodeName);
        clearAlarmsOnNodeInENM(nodeName);
        showAlarmsOnNodeInENM(nodeName);
    }

    protected void showAlarmsOnNodeInENM(final String nodeName) {
        final String alarmListCommand = String.format(NscsServiceGetter.FMEDIT_ALARM_LIST_COMMAND, nodeName);
        getScriptEngineOperator().runCommand(alarmListCommand).getBody();
    }

    protected void acknowledgeAlarmsOnNodeInENM(final String nodeName) {
        final String alarmAcknowledgeCommand = String.format(NscsServiceGetter.FMEDIT_ALARM_ACKNOWLEDGE_COMMAND, nodeName);
        getScriptEngineOperator().runCommand(alarmAcknowledgeCommand).getBody();
    }

    protected void clearAlarmsOnNodeInENM(final String nodeName) {
        final String alarmClearCommand = String.format(NscsServiceGetter.FMEDIT_ALARM_CLEAR_COMMAND, nodeName);
        getScriptEngineOperator().runCommand(alarmClearCommand).getBody();
    }

    protected void clearAlarmsOnNE(final String nodeName) {
        showAlarmsOnNE(nodeName);
        ceaseAlarmsOnNE(nodeName);
        showAlarmsOnNE(nodeName);

    }

    protected void ceaseAlarmsOnNE(final String nodeName) {
        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();
        NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);
        CeasealarmCommand ceaseAlarmCommand = NetSimCommands.ceasealarm();
        ceaseAlarmCommand.setAll("all");
        NetSimResult netSimResult = networkElement.exec(ceaseAlarmCommand);
        System.out.println("Result of ceasealarm command Alarms on Node : " + nodeName + " : " + netSimResult.toString());

    }

    protected void showAlarmsOnNE(final String nodeName) {
        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();
        NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

        ShowalarmCommand showAlarmCommand = NetSimCommands.showalarm();
        NetSimResult netSimResult = networkElement.exec(showAlarmCommand);
        System.out.println("Alarms on Node : " + nodeName + " :" + netSimResult.toString());
    }

    protected String sendAlarmsOnNE(final String nodeName, String specificProblem, int cause, int severity,String instance) {

        NetSimCmOperatorImpl netSimOperator = getNetSimOperator();
        NetworkElement networkElement = netSimOperator.getNetworkElement(nodeName);

          //This is just sending a general alarm. not on any specific MO
        SendalarmCommand sendAlarmCommand = NetSimCommands.sendalarm();
        sendAlarmCommand.setProblem(specificProblem);
        sendAlarmCommand.setCause(cause);
        sendAlarmCommand.setSeverity(severity);
        sendAlarmCommand.setInstance(instance);
//        sendAlarmCommand.setSystemdn(systemDn);
        return networkElement.exec(sendAlarmCommand).toString();

    }




    protected void printSecurityMOAttributeValuesOnNE(NetSimCmOperatorImpl netSimOperator, String nodeName){
        printMOAttributeValuesOnNE(netSimOperator,"MeContext=" + nodeName + ",ManagedElement=1,SystemFunctions=1,Security=1");
    }

    protected void printMOAttributeValuesOnNE(NetSimCmOperatorImpl netSimOperator, String moFdnInFormatRequiredForOperator ){
        Map<String, String> attributeMap = netSimOperator.getAllAttributes(moFdnInFormatRequiredForOperator);
        printMap(attributeMap, "attributeMap");
    }

    protected void printMOAttributeValueOnNE(NetSimCmOperatorImpl netSimOperator, String moFdnInFormatRequiredForOperator, String attributeName ){
        String attributeValue = netSimOperator.getAttributeValue(moFdnInFormatRequiredForOperator, attributeName);
        System.out.println(moFdnInFormatRequiredForOperator);
        System.out.println(attributeName + " : " + attributeValue);

    }


    protected  byte[] createByteArrayWithNodeNamesForSecadmCommand(String nodeList) {

        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeList.replace(",", ";").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());  //cause test case to fail
        }
        return fileContents;
    }


    protected void executeScriptEngineCommandAndVerifyResponseContainsExpectedContent(final String command, final String expectedContent) {

        final String httpResponseBody = getScriptEngineOperator().runCommand(command).getBody();

        Assert.assertEquals(httpResponseBody.contains(expectedContent), true,
                "Test Case Failure : Expected response not received in httpResponseBody : " + expectedContent);

    }    
    

    protected void executeScriptEngineCommandAndVerifyResponseContainsErrorAndSuggestedSolution(final String command, final String expectedError,final String expectedSuggestedSolution) {

        final String httpResponseBody = getScriptEngineOperator().runCommand(command).getBody();
        Assert.assertEquals(httpResponseBody.contains(expectedError), true,
                "Test Case Failure : Expected Error not received in httpResponseBody : " + expectedError);

        Assert.assertEquals(httpResponseBody.contains(expectedSuggestedSolution), true,
                "Test Case Failure : Expected Suggested Solution not received in httpResponseBody : " + expectedSuggestedSolution);

    }

    protected void executeScriptEngineCommandAndVerifyResponseContainsExactErrorAndSolution(final String command, final String expectedError,final String expectedSuggestedSolution) {

        final String httpResponseBody = getScriptEngineOperator().runCommand(command).getBody();

        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        Map<String, String> errorSuggestedSolutionMap = responseDtoWrapper.fetchErrorAndSuggestSolutionForSingleNodeErrorScenarioFromResponse();

        Assert.assertTrue(errorSuggestedSolutionMap.get("Error").equals(expectedError),
                "Test Case Failure : Did not receive expected error :" + expectedError
                        + " Received " + errorSuggestedSolutionMap.get("Error") + " : " + errorSuggestedSolutionMap.get("SuggestedSolution"));

        Assert.assertTrue(errorSuggestedSolutionMap.get("SuggestedSolution").equals(expectedSuggestedSolution),
                "Test Case Failure : Did not receive expected Solution :" + expectedSuggestedSolution
                        + ": Received " + errorSuggestedSolutionMap.get("Error") + " : " + errorSuggestedSolutionMap.get("SuggestedSolution"));
    }
    
    protected void executeScriptEngineCommandAndVerifyResponseContainsErrorAndSolution(final String command, final String expectedError,final String expectedSuggestedSolution) {

        final String httpResponseBody = getScriptEngineOperator().runCommand(command).getBody();

        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        Map<String, String> errorSuggestedSolutionMap = responseDtoWrapper.fetchErrorAndSuggestSolutionForSingleNodeErrorScenarioFromResponse();

        Assert.assertTrue(errorSuggestedSolutionMap.get("Error").contains(expectedError),
                "Test Case Failure : Did not receive expected error :" + expectedError
                        + " Received " + errorSuggestedSolutionMap.get("Error") + " : " + errorSuggestedSolutionMap.get("SuggestedSolution"));

        Assert.assertTrue(errorSuggestedSolutionMap.get("SuggestedSolution").equals(expectedSuggestedSolution),
                "Test Case Failure : Did not receive expected Solution :" + expectedSuggestedSolution
                        + ": Received " + errorSuggestedSolutionMap.get("Error") + " : " + errorSuggestedSolutionMap.get("SuggestedSolution"));
    }

    protected void executeScriptEngineCommandWithFileAndVerifyResponseContainsExpectedContent(final String command, final String fileName, final byte[] fileContents,final String expectedContent) {

        String httpResponseBody=executeScriptEngineCommandWithFile(command,fileName,fileContents);

        Assert.assertEquals(httpResponseBody.contains(expectedContent), true,
                "Test Case Failure : Expected response not received in httpResponseBody : " + expectedContent);

    }

    protected void executeScriptEngineCommandWithFileAndVerifyResponseContainsErrorAndSuggestedSolution(final String command, final String fileName, final byte[] fileContents,final String expectedError,final String expectedSuggestedSolution) {


        String httpResponseBody=executeScriptEngineCommandWithFile(command,fileName,fileContents);

        Assert.assertEquals(httpResponseBody.contains(expectedError), true,
                "Test Case Failure : Expected Error not received in httpResponseBody : " + expectedError);

        Assert.assertEquals(httpResponseBody.contains(expectedSuggestedSolution), true,
                "Test Case Failure : Expected Suggested Solution not received in httpResponseBody : " + expectedSuggestedSolution);

    }
    
    protected void executeScriptEngineCommandWithFileAndVerifyResponseContainsError(final String command, final String fileName, final byte[] fileContents,final String expectedError) {


        String httpResponseBody=executeScriptEngineCommandWithFile(command,fileName,fileContents);

        Assert.assertEquals(httpResponseBody.contains(expectedError), true,
                "Test Case Failure : Expected Error not received in httpResponseBody : " + expectedError);
        
    }
    
    protected  byte[] createByteArrayWithXmlNodeFileForSecadmCommand(String xmlNodeFileContent) {

        byte[] fileContents = new byte[0];
        try {
            fileContents = xmlNodeFileContent.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());  //cause test case to fail
        }
        return fileContents;
    }
    
    protected  byte[] createByteArrayWithNodeFileForSecadmCommand(String nodeFileContent) {

        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeFileContent.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());  //cause test case to fail
        }
        return fileContents;
    }

    protected void executeScriptEngineCommandWithFileAndVerifyResponseContainsExactErrorAndSuggestedSolution(final String command, final String fileName, final byte[] fileContents,final String expectedError,final String expectedSuggestedSolution) {


        String httpResponseBody=executeScriptEngineCommandWithFile(command,fileName,fileContents);

        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        Map<String, String> errorSuggestedSolutionMap = responseDtoWrapper.fetchErrorAndSuggestSolutionForSingleNodeErrorScenarioFromResponse();

        dumpMap(errorSuggestedSolutionMap);
        
        Assert.assertTrue(errorSuggestedSolutionMap.get("Error").equals(expectedError),
                "Test Case Failure : Did not receive expected error :" + expectedError
                        + " Received " + errorSuggestedSolutionMap.get("Error") + " : " + errorSuggestedSolutionMap.get("SuggestedSolution"));

        Assert.assertTrue(errorSuggestedSolutionMap.get("SuggestedSolution").equals(expectedSuggestedSolution),
                "Test Case Failure : Did not receive expected Solution :" + expectedSuggestedSolution
                        + ": Received " + errorSuggestedSolutionMap.get("Error") + " : " + errorSuggestedSolutionMap.get("SuggestedSolution"));

    }


    protected  void printHost(final Host host) {
        if (null != host) {
            String parent = null;
            try {
                parent = host.getParentName();
            } catch (final Exception e) {
                // getParentName throws exception in case parent is null
            }

            try {
                System.out.println("Host: " + host.getHostname() + "\n"
                        + "Type: " + host.getType() + "\n" + "Ip: "
                        + host.getIp() + "\n" + "OrigIp: "
                        + host.getOriginalIp() + "\n"  + "User: " + host.getUser()
                        + "\n" + "Pass: " + host.getPass() + "\n"
                        + "TunnelPortOffset: " + host.getTunnelPortOffset()
                        + "\n" + "Parent: " + parent + "\n" + "Nodes: "
                        + host.getNodes() + "\n");

            } catch (final Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Host: null" + "\n");
        }
    }


    protected void assertCredentialsCreated(final HttpResponse response) {
        assertTrue("Failed to create credentials", response.getBody().contains(NscsServiceGetter.SECADM_CREDENTIALS_CREATE_SUCCESS));
    }
    protected void assertCredentialsUpdated(final HttpResponse response) {
        assertTrue("Failed to update credentials", response.getBody().contains(NscsServiceGetter.SECADM_CREDENTIALS_UPDATE_SUCCESS));
    }

    protected String readResourceFile(String filepath) {
    	StringBuilder sb = new StringBuilder();
        String line = "";
        
        BufferedReader br = null;
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
                
        if (is != null) {        	
        	try {
				br = new BufferedReader(new InputStreamReader(is));
				
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
				logger.error(ex.getMessage(),ex);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
        }
        
        return sb.toString();
    }
    
    protected void dumpMap(Map<String, String> map) {
    	if (map != null) {
    		for (Entry<String, String> entry : map.entrySet()) {
    			System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
    		}
    	}
    }
    
    protected void unsynchronizeNode(final String nodeName) {

        final String setHeartbeatSupervision = String.format(NscsServiceGetter.CMEDIT_UNSYNCH_COMMAND, nodeName);
        boolean commandResult = false;

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

        commandResult = getScriptEngineOperator().runCommand(setHeartbeatSupervision, expectedResult);
        Assert.assertTrue(commandResult, "Problem setting Heartbeat Supervision to false for " + nodeName);
    }
    
    protected String getkeygenCreateFromDPS(String node, String key) {
		ResponseDtoWrapper response = getKeyDTO(node);
		return response.fetchSingleAttributeValueForAttributeNameFromResponse(key);
		
	}
    
    protected ResponseDtoWrapper getKeyDTO(String nodeName) {
		String fdn = String.format(NscsServiceGetter.CMEDIT_GET_NETWORK_ELEMENT_SECURITY_COMMAND, nodeName);
		return ResponseDtoWrapper
				.newResponseDtoWrapper(executeScriptEngineCommand(fdn));
	}
    
    protected boolean startNode(NetworkElement networkElement, String nodeName) {
    	int maxRetry = 3;
    	int numRetry = 1;
    	while (numRetry <= maxRetry && !networkElement.isStarted()) {
    		System.out.println("nodeName: " + nodeName + " is not started in netsim");
    		System.out.println("starting node " + nodeName);
    		networkElement.start();
    		numRetry++;
    		delay(NscsServiceGetter.DELAY_START_NETSIM);
    	}
    	if (!networkElement.isStarted()) {        		
    		System.out.println("ABORTING TEST: node " + nodeName + " has not started in netsim");
    		return false;
    	}
    	mapNetSimNodes.put(nodeName, networkElement);
    	return true;
    }
    
    protected NetworkElement getNetworkElementFromNetSim(String nodeName) {
    	NetworkElement networkElement = null;
    	try {
    		networkElement = getNetSimOperator().getNetworkElement(nodeName);

    		if (networkElement != null) {
    			if (!startNode(networkElement, nodeName)) {
    				System.out.println("Node " + nodeName + " cannot be started");
    				networkElement = null;
    			} 
    		}
    	} catch (Exception ex) {
    		System.out.println("Cannot connect to NetSim.. " + ex.getMessage());
    	}
    	
    	return networkElement;
    }
    
    protected boolean isNodeNameOnNetSim(String nodeName) {
    	System.out.println("Checking node " + nodeName + " on netsim");
    	final NetworkElement networkElement = getNetworkElementFromNetSim(nodeName);
    	if (networkElement != null) {
    		System.out.println("node " + nodeName + " is STARTED in netsim");
    		return true;
    	}
    	System.out.println("ABORTING TEST: node " + nodeName + " does not exist in netsim. Please check your simulation and connection");
    	return false;
    }
    
    protected boolean isNodeNameOnNetSimMap(String nodeName) {
    	if (!mapNetSimNodes.containsKey(nodeName)) {
    		System.out.println("ABORTING TEST: node " + nodeName + " does not exist in netsim. Please check your simulation and connection");
        	return false;
    	}
    	return true;
    }
}

