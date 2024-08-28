package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.oss.services.nscs.workflow.tasks.api.WorkflowTaskService;

public interface WorkflowTaskOperator extends WorkflowTaskService {
	
	String OPERATION_SUCCESS = "SUCCESS";
	String OPERATION_FAILED = "ERROR";
	String EXCEPTION = "EXCEPTION";
		
	void setHost(Host host);
}
