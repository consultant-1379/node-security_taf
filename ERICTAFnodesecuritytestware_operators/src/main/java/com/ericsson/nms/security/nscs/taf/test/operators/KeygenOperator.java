package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;

public interface KeygenOperator {

	public HttpResponse createKeypair(String nodeList, String algorithmTypeSize);

    public HttpResponse createKeypairWithFile(String nodeList, String algorithmTypeSize);
    

    public  HttpResponse updateKeypair(String nodeList, String algorithmTypeSize);
    
    public  HttpResponse updateKeypair(String nodeList);

}


