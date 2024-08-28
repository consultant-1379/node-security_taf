package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.Sleeper;

import javax.inject.Inject;

import java.util.Set;
import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

//@Operator(context = Context.REST)
public class NodeOperatorImpl implements NodeOperator {

    private static Logger log = Logger.getLogger(NodeOperatorImpl.class);

//    @Inject
//    private OperatorRegistry<ScriptEngineOperator> scriptEngineProvider;
//

    @Inject
    private ScriptEngineOperatorImpl scriptEngineOperator;


/**
     * @param nodeName
     * @param ipAddress - ipAdddress of the Node to be Added
     * @return*/




    public boolean createNode(final String nodeName,
                            final String ipAddress ){  
        
        log.info("Starting createNode for NE : "+nodeName);
        
        final String nameSgsn = "SGSN";
        String neType = "ERBS";
        String platformType = "CPP";
        
        if(nodeName.contains(nameSgsn)){
            neType = "SGSN-MME";
            platformType = "SGSN_MME";
        }

        if (checkNodeExists(nodeName)){
            log.error("NE already exists: "+ nodeName);
            return false;
        }

        
        final String createMeContext=String.format(NscsServiceGetter.CMEDIT_CREATE_MECONTEXT_COMMAND,nodeName,nodeName, neType, platformType);
        final String createNetworkElement=String.format(NscsServiceGetter.CMEDIT_CREATE_NETWORK_ELEMENT_COMMAND,nodeName,nodeName, neType, platformType, nodeName);
        final String createSecurityFunction=String.format(NscsServiceGetter.CMEDIT_CREATE_SECURITY_FUNCTION_COMMAND,nodeName);
        final String createCmFunction = String.format(NscsServiceGetter.CMEDIT_CREATE_CM_FUNCTION_COMMAND,nodeName);
        final String createCppConnectivityInfo =String.format(NscsServiceGetter.CMEDIT_CREATE_CPP_CONNECTIVITY_INFO_COMMAND,nodeName,ipAddress);
        final String createComConnectivityInfo =String.format(NscsServiceGetter.CMEDIT_CREATE_COM_CONNECTIVITY_INFO_COMMAND,nodeName,ipAddress);

        final String expectedResult = NscsServiceGetter.ONE_INSTANCE;

         boolean commandResult = scriptEngineOperator.runCommand(createMeContext,expectedResult);
         if(commandResult) {
             commandResult = scriptEngineOperator.runCommand(createNetworkElement, expectedResult);
         }
         if(commandResult) {
             commandResult = scriptEngineOperator.runCommand(createSecurityFunction, expectedResult);
         }
         if(commandResult) {
             commandResult = scriptEngineOperator.runCommand(createCmFunction, expectedResult);
         }
         if(commandResult) {
             if(nodeName.contains(nameSgsn)){
                 commandResult = scriptEngineOperator.runCommand(createComConnectivityInfo, expectedResult);
             } else {
                 commandResult = scriptEngineOperator.runCommand(createCppConnectivityInfo, expectedResult);
             }          
         }


        if(commandResult) {
            log.info("Created NE successfully : "+ nodeName);
        } else {
            log.error("Problem Creating NE : " + nodeName);
        }

        return commandResult;

    }




    // TODO NICK - Should param list include what is expected result rather than hard coding it. Ie. After create, we may want to check it exists, after delete we may want to check it is deleted
    public boolean checkNodeExists(final String nodeName){

        String commandString = String.format(NscsServiceGetter.CMEDIT_GET_MECONTEXT_COMMAND,nodeName);
        boolean nodeExists = executeScriptEngineCommandWithExpectedResult(commandString,NscsServiceGetter.ONE_INSTANCE);

        if (!nodeExists){
            return false;
        }

        commandString = String.format(NscsServiceGetter.CMEDIT_GET_NETWORKELEMENT_COMMAND,nodeName);
        nodeExists = executeScriptEngineCommandWithExpectedResult(commandString,NscsServiceGetter.ONE_INSTANCE);

        return nodeExists;
    }

    public boolean deleteNode(final String nodeName){

//        String commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_MECONTEXT_COMMAND, nodeName);
    	
    	String expectedResult = ONE_INSTANCE;
    	String commandString = String.format(NscsServiceGetter.CMEDIT_GET_SECURITYFUNCTION, nodeName);
    	if (executeScriptEngineCommandWithExpectedResult(commandString,expectedResult)) {
    	
	    	commandString = String.format(NscsServiceGetter.CMEDIT_SET_HEARTBEAT_FALSE, nodeName);
	        executeScriptEngineCommand(commandString);
	        
	        commandString = String.format(NscsServiceGetter.CMEDIT_SET_INVENTORY_FALSE, nodeName);
	        executeScriptEngineCommand(commandString);
	        
	        commandString = String.format(NscsServiceGetter.CMEDIT_SET_FMALARM_FALSE, nodeName);
	        executeScriptEngineCommand(commandString);
	        
	        commandString = String.format(NscsServiceGetter.CMEDIT_ACTION_CMFUNCTION, nodeName);
	        executeScriptEngineCommand(commandString);
	
	        commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_NETWORKELEMENT_COMMAND, nodeName);
	        executeScriptEngineCommand(commandString);
        
    	} else {
    		commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_MECONTEXT_COMMAND, nodeName);
    		executeScriptEngineCommand(commandString);
    		
    		commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_NETWORKELEMENT_COMMAND, nodeName);
	        executeScriptEngineCommand(commandString);
    	}
        
        final boolean nodeExists = checkNodeExists(nodeName);

        return (!nodeExists);
    }


    public void deleteNodesBasedOnFilter(final String filter){

        //Delete All MeContext that start with that name
        String commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_MECONTEXT_WITH_FILTER_COMMAND, filter + "*");
        executeScriptEngineCommand(commandString);

        //Delete All NetworkElement one at a time

       // for (String nodeName: nodeList) {
        commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_NETWORKELEMENT_WITH_FILTER_COMMAND, filter + "*");
        executeScriptEngineCommand(commandString);
       // }



    }


    public Set<String> getAllNodes(){
        return scriptEngineOperator.getAllMOsOfParticularType("MeContext");
    }



    private String executeScriptEngineCommand( final String command) {

        final HttpResponse httpResponse = scriptEngineOperator.runCommand(command);

        final String httpResponseBody = httpResponse.getBody();

        return httpResponseBody;
    }


    private boolean executeScriptEngineCommandWithExpectedResult( final String command, final String expectedResult) {

        final boolean result = scriptEngineOperator.runCommand(command,expectedResult);

        return result;
    }

}
