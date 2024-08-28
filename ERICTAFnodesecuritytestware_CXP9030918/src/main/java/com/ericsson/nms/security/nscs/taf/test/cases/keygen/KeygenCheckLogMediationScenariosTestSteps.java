/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases.keygen;

import java.io.*;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.netsim.domain.NetworkElement;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.taf.test.cases.NodeSecurityTestCaseHelper;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.KeygenOperatorImpl;
import com.ericsson.oss.netsim.operator.cm.NetSimCmOperatorImpl;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.SshAuthenticationClient;

/**
 *
 * @author enmadmin
 */
public class KeygenCheckLogMediationScenariosTestSteps extends NodeSecurityTestCaseHelper {
    
    private static Logger logger = Logger.getLogger(KeygenCheckLogMediationScenariosTestSteps.class);    
    
    public static final String ENM_SSH_PUBLIC_KEY = "enmSshPublicKey";
    
    public static final String SSH_KEY_CREATE = "Copy_key";
    
    public static final String EXPECTED_LOG_RESULT = "processTask [ConfigureSSHKeyGenerationTask] for node [NodeRef{fdn='%s'}] [%s] is finished";

	private final int sleepTime = 20000;
//    public static final String SSH_KEY_UPDATE = "Update_key";

    @TestStep(id = "keygenCheckLogMediationTest")
    public void keygenCheckLogMediationTest (
            @Input("nodeList") String nodeList,
            @Input("ipAddressList") final String ipAddressList
            ) {

        System.out.println(" \n\n ############# Starting test keygenCheckLogMediationTest ############# \n\n");
        logger.info(" \n\n ############# Starting test keygenCheckLogMediationTest ############# \n\n");
        
        
        final boolean introduceDelay=true;
        final int delayInMilliseconds = 15000;
        
        String nodeName = "SGSN_MEDIATION_001"; // nodeArray[NscsServiceGetter.INDEXOF_NODE_TOUPDATE_KEYS];
        String ip = "192.1.1.111"; // ipArray[NscsServiceGetter.INDEXOF_NODE_TOUPDATE_KEYS];
               
        // CREATE SGSN NODE
        createSgsnNode(nodeName, ip);
        
        
        boolean isAliveSVC1 = true;
        try {
        	executeCommandOnServerViaSshAndExit(HostConfigurator.getSecServiceUnit0(), "ls");
        } catch (Exception ex) {
        	System.err.println("Cannot connect to " + HostConfigurator.getSecServiceUnit1().getHostname());
        	System.err.println(ex.getMessage());
        	logger.error("Cannot connect to " + HostConfigurator.getSecServiceUnit1().getHostname() + "." + ex.getMessage(), ex);
        	isAliveSVC1 = false;
        }
        System.out.println(HostConfigurator.getSecServiceUnit0().getHostname() + " is alive " + isAliveSVC1);
        
        boolean isAliveSVC2 = true;
        try {
        	executeCommandOnServerViaSshAndExit(HostConfigurator.getSecServiceUnit1(), "ls");
        } catch (Exception ex) {
        	System.err.println("Cannot connect to " + HostConfigurator.getSecServiceUnit1().getHostname());
        	System.err.println(ex.getMessage());
        	logger.error("Cannot connect to " + HostConfigurator.getSecServiceUnit1().getHostname() + "." + ex.getMessage(), ex);
        	isAliveSVC2 = false;
        }
        System.out.println(HostConfigurator.getSecServiceUnit1().getHostname() + "SVC2 is alive " + isAliveSVC2);
        
        final String LOG_EXTENSION=".notification.testcase.log";
        
        if (isAliveSVC1) {
        	stopTailingLogFileOnServer(HostConfigurator.getSecServiceUnit0(), NscsServiceGetter.SVC_1_SECSERV_LOG);
        	
        	startTailingFileOnServer(HostConfigurator.getSecServiceUnit0(), NscsServiceGetter.SVC_1_SECSERV_LOG, NscsServiceGetter.SVC_1_SECSERV_LOG + LOG_EXTENSION, sleepTime);
        }
        
        if (isAliveSVC2) {
        	stopTailingLogFileOnServer(HostConfigurator.getSecServiceUnit1(), NscsServiceGetter.SVC_2_SECSERV_LOG);
        	
        	startTailingFileOnServer(HostConfigurator.getSecServiceUnit1(), NscsServiceGetter.SVC_2_SECSERV_LOG, NscsServiceGetter.SVC_2_SECSERV_LOG + LOG_EXTENSION, sleepTime);
        }
        
        // CREATE KEYS
        final String expectedResult = NscsServiceGetter.SECADM_KEYGEN_CREATE_SUCCESS;
        executeScriptEngineCommandAndVerifyResponseContainsExpectedContent(KeygenOperatorImpl.getKeygenCreateNodeListCommand(nodeName, NscsServiceGetter.ALGORITHM_TYPE_SIZE_RSA2048), expectedResult);
//        if (introduceDelay) { delay(delayInMilliseconds);}
        
//        String fdn = "MeContext=" + nodeName;
//        String expectedLogEntry = String.format(EXPECTED_LOG_RESULT, fdn, nodeName);
//        String expectedLogEntry = "processTask";
        String expectedLogEntry = "Node Security Service - Configuring SSH keys, Process completed : Configuring SSH Keys on Node 'MeContext=" + nodeName + "'";
        
        String notificationOccurencesOnSVC1 = null;
        if (isAliveSVC1) {

        	stopTailingLogFileOnServer(HostConfigurator.getSecServiceUnit0(), NscsServiceGetter.SVC_1_SECSERV_LOG);
        	
	        String svc1LogEntry= getFileContentThatMatchFilter(
	        		HostConfigurator.getSecServiceUnit0(),
	                NscsServiceGetter.SVC_1_SECSERV_LOG + LOG_EXTENSION,
	                expectedLogEntry);
	        
	        System.out.println("SVC1 Log Entry :  " + svc1LogEntry);
	        logger.debug("SVC1 Log Entry :  " + svc1LogEntry);
	        
	        notificationOccurencesOnSVC1 =
	        		
	                getNumberOfOccurencesThatMatchFilter(
	                		HostConfigurator.getSecServiceUnit0(),
	                        NscsServiceGetter.SVC_1_SECSERV_LOG + LOG_EXTENSION,
	                        expectedLogEntry
	                ).trim();
	        System.out.println("SVC1 : Notifcation Occurences  :  " + notificationOccurencesOnSVC1);
	        logger.debug("SVC1 : Notifcation Occurences  :  " + notificationOccurencesOnSVC1);
	        
	        System.out.println("Deleting files " + NscsServiceGetter.SVC_1_SECSERV_LOG + LOG_EXTENSION);
	        deleteFile(HostConfigurator.getSecServiceUnit0(),NscsServiceGetter.SVC_1_SECSERV_LOG + LOG_EXTENSION);
        }

        String notificationOccurencesOnSVC2 = null;
        if (isAliveSVC2) {
        	
        	stopTailingLogFileOnServer(HostConfigurator.getSecServiceUnit1(), NscsServiceGetter.SVC_2_SECSERV_LOG);
        	
	        String svc2LogEntry= getFileContentThatMatchFilter(
	        		HostConfigurator.getSecServiceUnit1(),
	                NscsServiceGetter.SVC_2_SECSERV_LOG + LOG_EXTENSION,
	                expectedLogEntry);	
	        
	        System.out.println("SVC2 Log Entry :  " + svc2LogEntry);
	        
	        logger.debug("SVC2 Log Entry :  " + svc2LogEntry);
		
	        notificationOccurencesOnSVC2 =
	                getNumberOfOccurencesThatMatchFilter(
	                		HostConfigurator.getSecServiceUnit1(),
	                        NscsServiceGetter.SVC_2_SECSERV_LOG + LOG_EXTENSION,
	                        expectedLogEntry
	                ).trim();	
	        
	        System.out.println("SVC2 : Notifcation Occurences  :  " + notificationOccurencesOnSVC2);
	        
	        logger.debug("SVC2 : Notifcation Occurences  :  " + notificationOccurencesOnSVC2);
	        
	        System.out.println("Deleting files " + NscsServiceGetter.SVC_2_SECSERV_LOG + LOG_EXTENSION);
	        deleteFile(HostConfigurator.getSecServiceUnit1(),NscsServiceGetter.SVC_2_SECSERV_LOG + LOG_EXTENSION);
        }
        
        int notificationOnSVC1 = 0;
        if (notificationOccurencesOnSVC1 != null && !notificationOccurencesOnSVC1.isEmpty()) {
        	notificationOnSVC1 = Integer.parseInt(notificationOccurencesOnSVC1);
        }
        int notificationOnSVC2 = 0;
        if (notificationOccurencesOnSVC2 != null && !notificationOccurencesOnSVC2.isEmpty()) {
        	notificationOnSVC2 = Integer.parseInt(notificationOccurencesOnSVC2);
        }
        int expectedOccurrenceResult = notificationOnSVC1 + notificationOnSVC2;
        
        deleteNode(nodeName);
        
        assertEquals(
        		String.format("Notification occurrences of %s must be %d", expectedLogEntry, expectedOccurrenceResult), 
        		1, 
        		expectedOccurrenceResult
        		);
        
    }
    
    private void createSgsnNode(String nodeName, String ip) {

    	createMeContextForTestSgsnSetup(nodeName);
        System.out.println("Created MeContext for SGSN keygen");
        logger.info("Created MeContext for SGSN keygen");
                
        createNetworkElementForTestSgsnSetup(nodeName);
        System.out.println("Created NetworkElement for SGSN keygen");
        logger.info("Created NetworkElement for SGSN keygen");
        
        createComConnectivityInfo(nodeName,ip);
        System.out.println("Created ComConnectivityInfo for SGSN keygen");
        logger.info("Created ComConnectivityInfo for SGSN keygen");

        createCredentialsForSgsnTestSetup(nodeName);
        System.out.println("Created Credentials for SGSN keygen");
        logger.info("Created Credentials for SGSN keygen");
        
    }
    
    private StringBuffer executeShellCommand(String[] commands) {
    	StringBuffer output = null;
    	try {
    		
			Process proc = Runtime.getRuntime().exec(commands);
			proc.waitFor();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			
			output = new StringBuffer();
			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
			
			
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			logger.error(ex.getMessage(),ex);
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			System.err.println(ex.getMessage());
			logger.error(ex.getMessage(),ex);
			ex.printStackTrace();
		}
    	return output;
    }
}
