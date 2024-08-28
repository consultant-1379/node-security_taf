/*
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 */

package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.Operator;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.Ports;
import com.ericsson.cifwk.taf.handlers.AsRmiHandler;
import com.ericsson.cifwk.taf.tools.http.HttpTool;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.api.IscfService;
import com.ericsson.nms.security.nscs.api.enums.EnrollmentMode;
import com.ericsson.nms.security.nscs.api.cpp.level.CPPSecurityLevel; /* FOR BACKWARD COMPATIBILITY ONLY */
import com.ericsson.nms.security.nscs.api.enums.SecurityLevel;
import com.ericsson.nms.security.nscs.api.exception.IscfServiceException;
import com.ericsson.nms.security.nscs.api.iscf.IpsecArea;
import com.ericsson.nms.security.nscs.api.iscf.IscfResponse;
import com.ericsson.nms.security.nscs.api.iscf.SubjectAltNameFormat;
import com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil;
//import com.ericsson.nms.security.nscs.taf.test.cases.IscfServiceTestSteps;

import com.ericsson.nms.security.nscs.api.model.NodeModelInformation;

import javax.inject.Singleton;

import org.apache.log4j.Logger;

import java.util.Set;

@Operator(context = Context.API)
@Singleton
public class IscfServiceApiOperator implements IscfServiceOperator {

	private static Logger logger = Logger.getLogger(IscfServiceApiOperator.class);
	
	  //private final Host host = HostConfigurator.getSecServiceUnit0();
    private Host host = null;

	private HttpTool tool;

    @Override
    public void setHttpTool(final HttpTool tool) {

        this.tool = tool;
    }

/* A - generate */
/* FOR BACKWARD COMPATIBILITY ONLY ... */
	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final CPPSecurityLevel wantedSecLevel,
			final CPPSecurityLevel minimumSecLevel) throws IscfServiceException {

		return locateEjb().generate(
				logicalName,
				nodeFdn,
				wantedSecLevel,
				minimumSecLevel);
	}

	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final String ipsecUserLabel,
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas) throws IscfServiceException {

		return locateEjb().generate(
				logicalName,
				nodeFdn,
				ipsecUserLabel,
				ipsecSubjectAltName,
				subjectAltNameFormat,
				wantedIpSecAreas);
	}

	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final CPPSecurityLevel wantedSecLevel,
			final CPPSecurityLevel minimumSecLevel,
			final String ipsecUserLabel,
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas) throws IscfServiceException {

		return locateEjb().generate(
				logicalName,
				nodeFdn,
				wantedSecLevel,
				minimumSecLevel,
				ipsecUserLabel,
				ipsecSubjectAltName,
				subjectAltNameFormat,
				wantedIpSecAreas);
	}
/* ... FOR BACKWARD COMPATIBILITY ONLY */

/*	NEW -- 1/3 - CORBA only */
// TODO
	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final SecurityLevel wantedSecLevel,
			final SecurityLevel minimumSecLevel,
			final EnrollmentMode wantedEnrollmentMode,
		 /* final String mimVersion,
			final String nodeType*/
			final NodeModelInformation paramNodeModelInformation) throws IscfServiceException {

		return locateEjb().generate(
				logicalName,
				nodeFdn,
				wantedSecLevel,
				minimumSecLevel,
				wantedEnrollmentMode,
				paramNodeModelInformation);       // contains mimVersion, nodeType
	}

/*	NEW -- 2/3 - Ipsec only */
	// TODO
	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final String ipsecUserLabel,
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas,
			final EnrollmentMode wantedEnrollmentMode,
		 /* final String mimVersion,
			final String nodeType*/
			final NodeModelInformation paramNodeModelInformation) throws IscfServiceException {

		return locateEjb().generate(
				logicalName,
				nodeFdn,
				ipsecUserLabel,
				ipsecSubjectAltName,
				subjectAltNameFormat,
				wantedIpSecAreas,
				wantedEnrollmentMode,
				paramNodeModelInformation);       // contains mimVersion, nodeType
	}

/*	NEW -- 3/3 - Combined */
	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final SecurityLevel wantedSecLevel,
			final SecurityLevel minimumSecLevel,
			final String ipsecUserLabel,
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas,
			final EnrollmentMode wantedEnrollmentMode,
		 /* final String mimVersion,
			final String nodeType*/
			final NodeModelInformation paramNodeModelInformation) throws IscfServiceException {

		return locateEjb().generate(
				logicalName,
				nodeFdn,
				wantedSecLevel,
				minimumSecLevel,
				ipsecUserLabel,
				ipsecSubjectAltName,
				subjectAltNameFormat,
				wantedIpSecAreas,
				wantedEnrollmentMode,
				paramNodeModelInformation);       // contains mimVersion, nodeType
	}

	@Override
	public void cancel(final String fdn) throws IscfServiceException {

		locateEjb().cancel(fdn);
	}

	private IscfService locateEjb() {
		final String jndiString = (String) DataHandler.getAttribute("iscf.jndi");
		logger.info("jndiString:" + jndiString);
		try {
		////host = HostConfigurator.getSecServiceUnit0();
			host = NodeSecurityTestUtil.getSecServiceUnit1();
			Ports portsType = Ports.RMI;
		    logger.info("locateEjb: Host host [from NodeSecurityTestUtil.getSecServiceUnit1()]");
		    logger.info("Hostname:" + host.getHostname());
		    logger.info("Ip:" + host.getIp());
		    logger.info("OriginalIp:" + host.getOriginalIp());
		    logger.info("Unit:" + host.getUnit());
		    logger.info("User:" + host.getUser());
		    logger.info("Ports:" + host.getPort(portsType));
			final AsRmiHandler asRmiHandler = new AsRmiHandler(host);
			logger.info("Debugging host: API (locateEjb() method)");
			logger.info("host.getHostname(): " + host.getHostname());
			logger.info("host.getIp(): " + host.getIp());
			logger.info("host.getOffset(): " + host.getOffset());
			return (IscfService) asRmiHandler
					.getServiceViaJndiLookup(jndiString);
		} catch (Exception error) {
			throw new IllegalStateException(
					"Failed to locate IscfService EJB on SC1!", error);
		}
	}

}
