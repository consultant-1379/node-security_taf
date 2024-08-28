package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.Operator;
import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Operator(context = Context.REST)
public class SyncNodeOperatorImpl implements SyncNodeOperator {

    @Inject
    private ScriptEngineOperatorImpl scriptEngineOperator;
    /**
     *
     * @param nodeName
     * @return*/

    @Override
    public boolean checkNodeIsSynchronisedOrTimeout(final String nodeName, final int timeout, final int numberOfTimesToCheck){

        boolean nodeIsSynched = false;
        int waitTime = timeout;
        for (int i = 0; i < numberOfTimesToCheck; i++) {
            if (checkNodeIsSynchronised(nodeName)){
                nodeIsSynched = true;
                System.out.println("\n\n Node  :" + nodeName + " : synched ");

                break;
            } else {
                try {
                    System.out.println("\n\n Node  :" + nodeName + " : not synched ");
                    System.out.println("\n\nWaiting " + waitTime + " seconds before checking if  " + nodeName + " synchronised : Sleeping : sleep number + " + i + "\n\n");
                    Thread.sleep(waitTime * 1000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                    //TODO what is best handling here ?
                }
            }
        }
        return nodeIsSynched;
    }

    @Override
    public boolean checkNodeIsSynchronised(final String nodeName){

        final String commandString = String.format(NscsServiceGetter.CMEDIT_GET_SYNCH_ATTRIBUTE_COMMAND_NEW, nodeName);
        final String httpResponseBody = executeScriptEngineCommand(commandString);
        System.out.println(httpResponseBody);

        final boolean nodeIsSynchronised = httpResponseBody.contains(NscsServiceGetter.CMEDIT_ATTRIBUTE_VALUE_WHEN_NODE_SYNCHED);
        return(nodeIsSynchronised);

    }

    @Override
    public Set<String> getAllSynchronisedNodes() {

        final String commandString = NscsServiceGetter.CMEDIT_GET_ALL_SYNCHED_NODES_COMMAND;
        final String httpResponseBody = executeScriptEngineCommand(commandString);
        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        final Set<String> cmFunctionFdnSet = responseDtoWrapper.fetchFdnOfMosFromResponseFromCmeditMoQuery();
        final Set<String> nodeNameSet = new HashSet();

        for (String fdn : cmFunctionFdnSet){
            nodeNameSet.add(fdn.replace("NetworkElement=","").replace(",CmFunction=1",""));
        }

        System.out.println("getAllSynchronisedNodes.nodeNameSet"+nodeNameSet.toString());
        System.out.println("getAllSynchronisedNodes.nodeNameSet"+nodeNameSet.size());
        System.out.println("getAllSynchronisedNodes.nodeNameSet"+nodeNameSet.toArray().toString());
        return nodeNameSet;
    }


    @Override
    public void orderSynchroniseNodeList(final String nodeList)
    {
        String[] nodeArray=splitCommaSeparatedStringIntoArray(nodeList);
        for (String nodeName : nodeArray) {
            orderSynchroniseNode(nodeName);
        }
    }

    @Override
    public void orderSynchroniseNode(final String nodeName)
    {
        final String commandString = String.format(NscsServiceGetter.CMEDIT_SYNCH_COMMAND_NEW, nodeName);
        final String httpResponseBody = executeScriptEngineCommand(commandString);
        boolean moactionExecutedSuccessfully = httpResponseBody.contains(NscsServiceGetter.ONE_INSTANCE);
        if(!moactionExecutedSuccessfully){
            throw new RuntimeException("Problem ordering the synch of the node : " + nodeName);
        }
    }



    @Override
    public boolean synchroniseNode(final String nodeName)
    {

        orderSynchroniseNode(nodeName);

        boolean nodeSynced=false;

        // check every 5 seconds if node is synched, check 12 times
        final int waitTimeInSeconds=5;
        for(int i=0;i<12;i++){
            nodeSynced = checkNodeIsSynchronised(nodeName);
            if(nodeSynced){
                break;
            }
            else{
                try {
                    System.out.println("\n\nWaiting " + waitTimeInSeconds + " seconds for Node to Synchronise : Sleeping : sleep number + " + i + "\n\n");
                    Thread.sleep(waitTimeInSeconds * 1000);
                } catch (final InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        return nodeSynced;
    }


    private String executeScriptEngineCommand( final String command) {

        final HttpResponse httpResponse = scriptEngineOperator.runCommand(command);
        return httpResponse.getBody();

    }
    private String[] splitCommaSeparatedStringIntoArray(String commaSeparatedString) {
        if (commaSeparatedString.contains(",")) {
            String[] stringArray = commaSeparatedString.split(",");
            return stringArray;
        }
        return new String[]{commaSeparatedString};
    }
}
