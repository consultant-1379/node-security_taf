package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;

public interface TrustDistributeOperator {

	public HttpResponse trustDistributeNodeFile(String certType, String nodeFile);
	
	public HttpResponse trustDistributeNodeList(String certType, String nodeList);

}
