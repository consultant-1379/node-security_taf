package com.ericsson.nms.security.nscs.taf.test.operators;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;

//@Operator(context = Context.REST)
public class TrustDistributeOperatorImpl implements TrustDistributeOperator {

    @Inject
    private ScriptEngineOperatorImpl scriptEngineOperator;

    @Override
	public HttpResponse trustDistributeNodeFile(String certType, String nodeFile) {

        return scriptEngineOperator.runCommand(getTrustDistributeCommandNodeFile(certType, nodeFile));
    }
    
    @Override
    public HttpResponse trustDistributeNodeList(String certType, String nodeList){
    	
    	return scriptEngineOperator.runCommand(getTrustDistributeCommandNodeList(certType, nodeList));
    }
    
    public static String getTrustDistributeCommandNodeFile(final String certType, final String nodeFile) {
    	return String.format("secadm trust distribute --certtype %s --nodefile file:%s", certType, nodeFile);
    }
    
    public static String getTrustDistributeCommandNodeList(final String certType, final String nodeList) {
    	return String.format("secadm trust distribute --certtype %s --nodelist %s", certType, nodeList);
    }
    
}
