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
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.cifwk.taf.tools.http.HttpTool;
import com.ericsson.nms.host.HostConfigurator;
import com.ericsson.nms.security.nscs.api.cpp.level.CPPSecurityLevel; /* FOR BACKWARD COMPATIBILITY ONLY */
import com.ericsson.nms.security.nscs.api.enums.EnrollmentMode;
import com.ericsson.nms.security.nscs.api.enums.SecurityLevel;
import com.ericsson.nms.security.nscs.api.exception.IscfServiceException;
import com.ericsson.nms.security.nscs.api.iscf.IpsecArea;
import com.ericsson.nms.security.nscs.api.iscf.IscfResponse;
import com.ericsson.nms.security.nscs.api.iscf.SubjectAltNameFormat;
import com.ericsson.nms.security.nscs.api.model.NodeModelInformation;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;

import javax.inject.Singleton;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * REST-based implementation of IscfServiceOperator
 * 
 * @author ealemca
 */
@Operator(context = Context.REST)
@Singleton
public class IscfServiceRestOperator implements IscfServiceOperator {

	private static Logger logger = Logger.getLogger(IscfServiceRestOperator.class);
	
    private final Host host = HostConfigurator.getSecServiceUnit0();

    private HttpTool tool;

    @Override
    public void setHttpTool(final HttpTool tool) {

        this.tool = tool;
    }

    /**
	 ** 1st level - public methods "generate()"
	 **/

    /** FOR BACKWARD COMPATIBILITY ONLY ... **/
    /*
	 * OLD O&M (= CORBA, SecLevel) generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldseclevel?
	 *   wanted=LEVEL_2&
	 *   minimum=LEVEL_2&
	 *   node=LTEPIPPO&
	 *   logical=Pippo
	 */
    @Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final CPPSecurityLevel wantedSecLevel,
			final CPPSecurityLevel minimumSecLevel) throws IscfServiceException {
    	
		final IscfResponse response = new IscfResponse();

		/*
		private HttpResponse restGenerateIscfSecLevel(
				final String logicalName,
				final String nodeFdn,
				final String wantedSecLevel,
				final String minimumSecLevel)
		 */
		final HttpResponse httpResponse = restGenerateIscfSecLevel(
				logicalName,
				nodeFdn,
				wantedSecLevel.name(),
				minimumSecLevel.name());
		try {
			response.setIscfContent(httpResponse.getBody().getBytes(("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.info(String
				.format("Character encoding problem [ %s ], setting IscfResponse with hint",
					e.getMessage()));
			response.setRbsIntegrityCode("Character encoding problem");
		}

		return response;
	}
	
    /*
	 * OLD IPSec generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldipsec?
	 *   node=LTEPIETRO&
	 *   logical=Pietro&
	 *   subjectAltName=Pietro&
	 *   subjectAltNameType=IPV4&
	 *   ipsecAreas=OM
	 */
	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final String ipsecUserLabel,	// TODO - UNUSED?
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas) throws IscfServiceException {
		
		final IscfResponse response = new IscfResponse();

		/*
		private HttpResponse restGenerateIscfIpsec(
				final String nodeName,
				final String logicalName,
				final String subjectAltName,
				final String subjectAltNameType,
				final String ipsecArea1,
				final String ipsecArea2)
		*/
		final HttpResponse httpResponse = restGenerateIscfIpsec(
				nodeFdn,
				logicalName,
				ipsecSubjectAltName,
				subjectAltNameFormat.toString(),
				wantedIpSecAreas.iterator().next().toString(),
				wantedIpSecAreas.iterator().next().toString());
		try {
			response.setIscfContent(httpResponse.getBody().getBytes(("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.info(String
				.format("Character encoding problem [ %s ], setting IscfResponse with hint",
					e.getMessage()));
			response.setRbsIntegrityCode("Character encoding problem");
		}

		return response;
	}

	/*
	 * OLD COMBO generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldcombined?
	 *   node=LTESTEFANO&
	 *   logical=LTESTEFANO&
	 *   wanted=LEVEL_2&
	 *   minimum=LEVEL_2&
	 *   subjectAltName=Stefano&
	 *   subjectAltNameType=IPV4&
	 *   ipsecAreas=OM
	 */
	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final CPPSecurityLevel wantedSecLevel,
			final CPPSecurityLevel minimumSecLevel,
			final String ipsecUserLabel,	 // TODO - UNUSED?
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas) throws IscfServiceException {
		
		final IscfResponse response = new IscfResponse();

		/*
		private HttpResponse restGenerateIscfCombined(
				final String nodeName,
				final String logicalName,
				final String wantedSecurityLevel,
				final String minimumSecurityLevel,
				final String subjectAltName,
				final String subjectAltNameType,
				final String ipsecArea1,
				final String ipsecArea2)
		*/
		final HttpResponse httpResponse = restGenerateIscfCombined(
				nodeFdn,
				logicalName,
				wantedSecLevel.toString(),
				minimumSecLevel.toString(),
				ipsecSubjectAltName,
				subjectAltNameFormat.toString(),
				wantedIpSecAreas.iterator().next().toString(),
				wantedIpSecAreas.iterator().next().toString());
		try {
			response.setIscfContent(httpResponse.getBody().getBytes(("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.info(
				String.format(
						"Character encoding problem [ %s ], setting IscfResponse with hint",
						e.getMessage()));
			response.setRbsIntegrityCode("Character encoding problem");
		}

		return response;
	}
    /** ... FOR BACKWARD COMPATIBILITY ONLY **/

	/*
	 * NEW O&M (= CORBA, SecLevel) generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/seclevel?
	 *   wanted=LEVEL_2&minimum=LEVEL_2&node=LTELUCA&logical=Luca&
	 *   mode=SCEP&version=E.1.49&nodeType=ERBS
	 */
		/*
		 * public abstract IscfResponse generate(
		 * String paramString1, 
		 * String paramString2,
		 * SecurityLevel paramSecurityLevel1,
		 * SecurityLevel paramSecurityLevel2,
		 * EnrollmentMode paramEnrollmentMode,
		 * NodeModelInformation paramNodeModelInformation) throws IscfServiceException;
		 */
	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final SecurityLevel wantedSecLevel,
			final SecurityLevel minimumSecLevel,
			final EnrollmentMode wantedEnrollmentMode,
			// TODO 2 */
		 /* final String mimVersion,
			final String nodeType*/
			final NodeModelInformation paramNodeModelInformation) throws IscfServiceException {
		
		logger.info("Debugging host: REST (1/3 - CORBA only)");
		logger.info("host.getHostname(): " + host.getHostname());
		logger.info("host.getIp(): " + host.getIp());
		logger.info("host.getOffset(): " + host.getOffset());
		
		final IscfResponse response = new IscfResponse();

		final HttpResponse httpResponse = restGenerateIscfSecLevel(
				logicalName,
				nodeFdn,
				wantedSecLevel.name(),
				minimumSecLevel.name(),
				wantedEnrollmentMode.name(),
				paramNodeModelInformation.getModelIdentifier(), // mimVersion?
				paramNodeModelInformation.getNodeType());       // nodeType
		try {
			response.setIscfContent(httpResponse.getBody().getBytes(("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
				"Character encoding problem [ %s ], setting IscfResponse with hint",
					e.getMessage()));
			response.setRbsIntegrityCode("Character encoding problem");
		}

		return response;
	}
	
	/*
	 * NEW IPSec generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/ipsec?
	 *   node=LTEMARCO&logical=Marco&subjectAltName=Marco&subjectAltNameType=IPV4&ipsecAreas=OM&
	 *   mode=SCEP&version=E.1.50&nodeType=ERBS
	 */
		/*
		 * public abstract IscfResponse generate(
		 * String paramString1,
		 * String paramString2,
		 * String paramString3,
		 * String paramString4,
		 * SubjectAltNameFormat paramSubjectAltNameFormat,
		 * Set<IpsecArea> paramSet,
		 * EnrollmentMode paramEnrollmentMode,
		 * NodeModelInformation paramNodeModelInformation) throws IscfServiceException;
		 */
	@Override
	public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final String ipsecUserLabel,
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas,
	        final EnrollmentMode wantedEnrollmentMode,
	      /*final String mimVersion,
	        final String nodeType*/
	        final NodeModelInformation paramNodeModelInformation) throws IscfServiceException {
	
		final IscfResponse response = new IscfResponse();

		final HttpResponse httpResponse = restGenerateIscfIpsec(
				nodeFdn,
				logicalName,
				ipsecSubjectAltName,
				subjectAltNameFormat.toString(),
				wantedIpSecAreas.iterator().next().toString(),
				wantedIpSecAreas.iterator().next().toString(),
				wantedEnrollmentMode.name(),
				paramNodeModelInformation.getModelIdentifier(), // mimVersion?
				paramNodeModelInformation.getNodeType());       // nodeType
		try {
			response.setIscfContent(httpResponse.getBody().getBytes(("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
				"Character encoding problem [ %s ], setting IscfResponse with hint",
					e.getMessage()));
			response.setRbsIntegrityCode("Character encoding problem");
		}

		return response;
	}

	/*	NEW COMBO generate
	 *  https://enmapache.athtem.eei.ericsson.se/node-security/iscf/combined?
	 *    node=LTECINZIA&logical=Cinzia&wanted=LEVEL_2&minimum=LEVEL_2&
	 *    subjectAltName=Cinzia&subjectAltNameType=IPV4&ipsecAreas=OM&
	 *    mode=SCEP&version=5.1.100&nodeType=ERBS
	 */
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
		      /*final String mimVersion,
	        final String nodeType*/
	        final NodeModelInformation paramNodeModelInformation) throws IscfServiceException {

		final IscfResponse response = new IscfResponse();

		final HttpResponse httpResponse = restGenerateIscfCombined(
				nodeFdn,
				logicalName,
				wantedSecLevel.toString(),
				minimumSecLevel.toString(),
				ipsecSubjectAltName,
				subjectAltNameFormat.toString(),
				wantedIpSecAreas.iterator().next().toString(),
				wantedIpSecAreas.iterator().next().toString(),
				wantedEnrollmentMode.name(),
				paramNodeModelInformation.getModelIdentifier(), // mimVersion?
				paramNodeModelInformation.getNodeType());       // nodeType
		try {
			response.setIscfContent(httpResponse.getBody().getBytes(("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
				"Character encoding problem [ %s ], setting IscfResponse with hint",
					e.getMessage()));
			response.setRbsIntegrityCode("Character encoding problem");
		}

		return response;
	}
	
	
	@Override
	public void cancel(final String fdn) throws IscfServiceException {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private String getTestUrl(final String testResource) {
		return "http://" + host.getHostname() + ":8080" + testResource;
	}


	/**
	 ** 2nd level - private methods, called by above public methods "generate()"
	 **/

    /** FOR BACKWARD COMPATIBILITY ONLY ... **/
	/* Used by OLD O&M (= CORBA, SecLevel) generate */
    /*
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldseclevel?
	 *   wanted=LEVEL_2&
	 *   minimum=LEVEL_2&
	 *   node=LTEPIPPO&
	 *   logical=Pippo
	 */
	private HttpResponse restGenerateIscfSecLevel(
			final String logicalName,
			final String nodeFdn,
			final String wantedSecLevel,
			final String minimumSecLevel) {
		final String urlParams = String.format(
			"wanted=%s&minimum=%s&node=%s&logical=%s",
				wantedSecLevel,
				minimumSecLevel,
				nodeFdn,
				logicalName);
	 // TODO Add if (KVM) { doThis } else { PhysServ - https://ienmapache.athtem.eei.ericsson.se/ = getTestUrl()}
		String url_variable = NscsServiceGetter.ISCF_SECLEVEL_REST_RESOURCE_OLD + "?" + urlParams;
		logger.info("Executing REST at URL: " + "https://enmapache.athtem.eei.ericsson.se/" + url_variable);
		logger.info("Fetching ISCF via REST resource with params " + urlParams);
		return tool.get(url_variable);
	}

	/* Used by OLD IPSec generate */
	/* 
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldipsec?
	 *   node=LTEPIETRO&
	 *   logical=Pietro&
	 *   subjectAltName=Pietro&
	 *   subjectAltNameType=IPV4&
	 *   ipsecAreas=OM    // TODO MDTR - Missing ipsecArea2 in the REST call?
	 */
	private HttpResponse restGenerateIscfIpsec(
			final String nodeName,
			final String logicalName,
			final String subjectAltName,
			final String subjectAltNameType,
			final String ipsecArea1,
			final String ipsecArea2) {
		final String urlParams = String.format(
			"node=%s&logical=%s&subjectAltName=%s&subjectAltNameType=%s&ipsecAreas=%s&ipsecAreas=%s",
				nodeName,
				logicalName,
				subjectAltName,
				subjectAltNameType,
				ipsecArea1,
				ipsecArea2);
	 // TODO Add if (KVM) { doThis } else { PhysServ - https://ienmapache.athtem.eei.ericsson.se/ }
		String url_variable = NscsServiceGetter.ISCF_IPSEC_REST_RESOURCE_OLD + "?" + urlParams;
		logger.info("Executing REST at URL: " + "https://enmapache.athtem.eei.ericsson.se/" + url_variable);
		logger.info("Fetching ISCF via REST resource with params " + urlParams);
		return tool.get(url_variable);
	}

	/* Used by OLD COMBO generate */
	/*
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldcombined?
	 *   node=LTESTEFANO&
	 *   logical=LTESTEFANO&
	 *   wanted=LEVEL_2&
	 *   minimum=LEVEL_2&
	 *   subjectAltName=Stefano&
	 *   subjectAltNameType=IPV4&
	 *   ipsecAreas=OM	// TODO MDTR - Missing ipsecArea2 in the REST call?
	 */
	private HttpResponse restGenerateIscfCombined(
			final String nodeName,
			final String logicalName,
			final String wantedSecurityLevel,
			final String minimumSecurityLevel,
			final String subjectAltName,
			final String subjectAltNameType,
			final String ipsecArea1,
			final String ipsecArea2) {
		final String urlParams = String.format(
			"node=%s&logical=%s&wanted=%s&minimum=%s&subjectAltName=%s&subjectAltNameType=%s&ipsecAreas=%s&ipsecAreas=%s",
				nodeName, 
				logicalName,
				wantedSecurityLevel,
				minimumSecurityLevel,
				subjectAltName,
				subjectAltNameType,
				ipsecArea1,
				ipsecArea2);
	 // TODO Add if (KVM) { doThis } else { PhysServ - https://ienmapache.athtem.eei.ericsson.se/ }
		String url_variable = NscsServiceGetter.ISCF_COMBINED_REST_RESOURCE_OLD + "?" + urlParams;
		logger.info("Executing REST at URL: " + "https://enmapache.athtem.eei.ericsson.se/" + url_variable);
		logger.info("Fetching ISCF via REST resource with params " + urlParams);
		return tool.get(url_variable);
	}
    /** ... FOR BACKWARD COMPATIBILITY ONLY **/
	
	/*	Used by NEW O&M (= CORBA, SecLevel) generate */
	private HttpResponse restGenerateIscfSecLevel(final String logicalName,
			final String nodeFdn,
			final String wantedSecLevel,
			final String minimumSecLevel,
			final String wantedEnrollmentMode,
			final String mimVersion,
			final String nodeType) {
		final String urlParams = String.format(
			"wanted=%s&minimum=%s&node=%s&logical=%s&mode=%s&version=%s&nodeType=%s",
				wantedSecLevel,
				minimumSecLevel,
				nodeFdn,
				logicalName,
				wantedEnrollmentMode,
				mimVersion,
				nodeType);
		logger.info("Fetching ISCF via REST resource with params " + urlParams);
	 // TODO Add if (KVM) { doThis } else { PhysServ - https://ienmapache.athtem.eei.ericsson.se/ }
		String url_variable = NscsServiceGetter.ISCF_SECLEVEL_REST_RESOURCE + "?" + urlParams;
		logger.info("Executing REST at URL: " + "https://enmapache.athtem.eei.ericsson.se/" + url_variable);
		return tool.get(url_variable);
	}

	/*	Used by NEW IPSec generate --- TODO body (format: add 3 parameters) */
	private HttpResponse restGenerateIscfIpsec(
			final String nodeName,
			final String logicalName,
			final String subjectAltName,
			final String subjectAltNameType,
			final String ipsecArea1,
			final String ipsecArea2,
			final String wantedEnrollmentMode,
	        final String mimVersion,
	        final String nodeType) {
		final String urlParams = String.format(
			"node=%s&logical=%s&subjectAltName=%s&subjectAltNameType=%s&ipsecAreas=%s&ipsecAreas=%s",
				nodeName,
				logicalName,
				subjectAltName,
				subjectAltNameType,
				ipsecArea1,
				ipsecArea2);
		logger.info("Fetching ISCF via REST resource with params " + urlParams);
	 // TODO Add if (KVM) { doThis } else { PhysServ - https://ienmapache.athtem.eei.ericsson.se/ }
		String url_variable = NscsServiceGetter.ISCF_IPSEC_REST_RESOURCE + "?" + urlParams;
		logger.info("Executing REST at URL: " +
					"https://enmapache.athtem.eei.ericsson.se/" + url_variable);
		return tool.get(url_variable);
	}

	/*	Used by NEW COMBO generate --- TODO body (format: add 3 parameters) */
	private HttpResponse restGenerateIscfCombined(
			final String nodeName,
			final String logicalName,
			final String wantedSecurityLevel,
			final String minimumSecurityLevel,
			final String subjectAltName,
			final String subjectAltNameType,
			final String ipsecArea1,
			final String ipsecArea2,
			final String wantedEnrollmentMode,
	        final String mimVersion,
	        final String nodeType) {
		final String urlParams = String.format(
			"node=%s&logical=%s&wanted=%s&minimum=%s&subjectAltName=%s&subjectAltNameType=%s&ipsecAreas=%s&ipsecAreas=%s",
				nodeName,
				logicalName,
				wantedSecurityLevel,
				minimumSecurityLevel,
				subjectAltName,
				subjectAltNameType,
				ipsecArea1,
				ipsecArea2);
		logger.info("Fetching ISCF via REST resource with params " + urlParams);
	 // TODO Add if (KVM) { doThis } else { PhysServ - https://ienmapache.athtem.eei.ericsson.se/ }
		String url_variable = NscsServiceGetter.ISCF_COMBINED_REST_RESOURCE + "?" + urlParams;
		logger.info("Executing REST at URL: " +
					"https://enmapache.athtem.eei.ericsson.se/" + url_variable);
		return tool.get(url_variable);
	}

}
