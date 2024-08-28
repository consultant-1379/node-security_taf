package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.oss.services.scriptengine.spi.dtos.Command;

public interface SecurityCommandsOperator {

	HttpResponse setSecurityLevel(String nodeList, String securityLevel);

	HttpResponse getSecurityLevel(String nodeList);

	HttpResponse getSecurityLevel(String nodeList, String level);

	HttpResponse getSecurityLevelForAllNodes();

	HttpResponse getSecurityLevelForAllNodes(String level);

	String executeIPSecStatusCommand(String command, String fdn);

	String executeIPSecCommand(String commandString, String fileName);

}