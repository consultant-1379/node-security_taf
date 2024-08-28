package com.ericsson.nms.security.nscs.taf.test.operators;

import javax.inject.Inject;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;

//@Operator(context = Context.REST)
public class CertificateIssueOperatorImpl implements CertificateIssueOperator {

    @Inject
    private ScriptEngineOperatorImpl scriptEngineOperator;

    @Override
	public HttpResponse certificateIssue(String certType, String xmlNodeFile) {

        return scriptEngineOperator.runCommand(getCertificateIssueCommand(certType, xmlNodeFile));
    }
    
    public static String getCertificateIssueCommand(final String certType, final String xmlNodeFile) {
    	return String.format("secadm certificate issue --certtype %s --xmlfile file:%s", certType, xmlNodeFile);
    }
    
}
