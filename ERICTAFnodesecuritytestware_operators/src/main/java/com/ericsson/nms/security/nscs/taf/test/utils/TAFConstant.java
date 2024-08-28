/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.nms.security.nscs.taf.test.utils;

import com.ericsson.cifwk.taf.data.Host;
//import com.ericsson.nms.host.HostConfigurator;

public enum TAFConstant {
	TRAFFIC("TRAFFIC"), 
	OM("OM"), 
	TRAFFIC_VPN_ID("1"),
	OM_VPN_DEACTIVE_ID("1"),
	OM_VPN_ACTIVE_ID("2"),
	ACT("ACTIVATED"),
	DEACT("DEACTIVATED"),
	UNKNOWN("UNKNOWN"),
	ACT_CONF1("ACTIVATED-CONFIGURATION1"),
	ACT_CONF2("ACTIVATED-CONFIGURATION2");
	
	private String constant;

	private TAFConstant(final String constant) {
		this.constant = constant;
	}

	public String getConstant() {
		return constant;
	}
	
	// Host Configuration
/*	public static final Host svc1 = HostConfigurator.getSVC1();
	public static final Host svc2 = HostConfigurator.getSVC2();
	public static final Host locale = NodeSecurityTestCaseHelper.getNMServer(); */
	public static final Host secserv1  = NodeSecurityTestUtil.getSecServiceUnit1();
	
}
