package com.ericsson.nms.security.nscs.taf.test.operators;


import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.oss.services.scriptengine.spi.dtos.Command;

import java.io.File;
import java.util.Set;

public interface ScriptEngineOperator {


    HttpResponse runCommand( final String commandString); // , Host host);

    HttpResponse runCommand(  final String commandString ,File file, String fileName); //, final Host host);

    boolean runCommand( final String commandString ,String expectedString); //, Host host);

    Set<String> getAllMOsOfParticularType(String moType);

	String executeWithFile(Command command);

}
