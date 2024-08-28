package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.Operator;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.AsRmiHandler;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.oss.services.nscs.workflow.tasks.api.WorkflowTaskService;
import com.ericsson.oss.services.nscs.workflow.tasks.api.request.WorkflowActionTask;
import com.ericsson.oss.services.nscs.workflow.tasks.api.request.WorkflowQueryTask;

import javax.inject.Singleton;

@Operator(context = Context.API)
@Singleton
public class WorkflowTaskOperatorImpl implements WorkflowTaskOperator {

    private Host host = HostConfigurator.getSecServiceUnit0();

	public static final String JNDI_WF_TASK_SERVICE = "node-security.workflowtaskservice.jndi";

	@Override
	public void setHost(final Host host) {
		this.host = host;
	}

	private WorkflowTaskService locateEjb() {

		final String jndiString = (String) DataHandler
				.getAttribute(JNDI_WF_TASK_SERVICE);

		try {
			final AsRmiHandler asRmiHandler = new AsRmiHandler(host);

			return (WorkflowTaskService) asRmiHandler
					.getServiceViaJndiLookup(jndiString);
		} catch (final Exception e) {
			throw new IllegalStateException(
					"Failed to locate WorkflowTaskService EJB!", e);
		}
	}

	@Override
	public void processTask(final WorkflowActionTask action) {
		locateEjb().processTask(action);

	}

	@Override
	public String processTask(final WorkflowQueryTask query) {
		return locateEjb().processTask(query);
	}
}
