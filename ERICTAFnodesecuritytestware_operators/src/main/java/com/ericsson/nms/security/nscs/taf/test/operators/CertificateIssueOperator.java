package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;

public interface CertificateIssueOperator {

	public HttpResponse certificateIssue(String certType, String xmlNodeFile);

}
