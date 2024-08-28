package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil;
import com.ericsson.nms.security.nscs.taf.test.utils.TAFConstant;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.oss.services.scriptengine.spi.dtos.Command;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil.*;
import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

//@Operator(context = Context.REST)
public class SecurityCommandsOperatorImpl implements SecurityCommandsOperator {

    private static Logger log = Logger.getLogger(SecurityCommandsOperatorImpl.class);

    @Inject
    private ScriptEngineOperatorImpl scriptEngineOperator;

    @Override
    public HttpResponse getSecurityLevel(final String nodeList) {

        final String secadmGetCommand= String.format(NscsServiceGetter.SECADM_SL_GET_COMMAND,nodeList);

        return scriptEngineOperator.runCommand(secadmGetCommand);
    }

    @Override
    public HttpResponse getSecurityLevel(final String nodeList, final String securityLevel) {

        final String secadmGetCommand= String.format(NscsServiceGetter.SECADM_SL_GET_COMMAND_WITH_SECURITY_LEVEL,securityLevel,nodeList);


        return scriptEngineOperator.runCommand(secadmGetCommand);
    }

    @Override
    public HttpResponse getSecurityLevelForAllNodes() {
        final String secadmGetCommand= NscsServiceGetter.SECADM_SL_GET_ALL_COMMAND;

        return scriptEngineOperator.runCommand(secadmGetCommand);
    }

    @Override
    public HttpResponse getSecurityLevelForAllNodes(final String securityLevel) {

        final String secadmGetCommand = String.format(NscsServiceGetter.SECADM_SL_GET_ALL_COMMAND_WITH_SECURITY_LEVEL, securityLevel);

        return scriptEngineOperator.runCommand(secadmGetCommand);
    }

    @Override
    public HttpResponse setSecurityLevel(final String nodeList, final String securityLevel) {

         final String secadmSetCommand= String.format(NscsServiceGetter.SECADM_SL_SET_COMMAND,securityLevel,nodeList);

         return scriptEngineOperator.runCommand(secadmSetCommand);
    }
    
    private HttpResponse executeCommandWithFile(final String command, final File file,
			final String fileName) {

		return scriptEngineOperator.runCommand(command, file, fileName);
	}

   /**
	 * execute ipsec status command
	 * 
	 * @param string command
	 * @param string fdn
	 * @return httpResponseBody
	 */
	@Override
	public String executeIPSecStatusCommand(String command, String fdn) {
		byte[] fileData = createBytesFromString(fdn);
		File file = createFileFromByte(fileData, null);
		HttpResponse response = scriptEngineOperator.runCommand(command, file, FILE_ABC_TXT);
		return response != null ? response.getBody() : EMPTY_STRING;
	}

	/**
	 * load the xml template and execute ipsec om & traffic command 
	 * 
	 * @param string command
	 * @param string fdn
	 * @return httpResponseBody
	 */
	@Override
	public String executeIPSecCommand(String commandString, String fileName) {
		final Map<String, Object> properties = templateLoader(fileName,
				IPSEC_DATA_FILE_PATH);
		Command command = new Command(null, commandString, properties);
		return scriptEngineOperator.executeWithFile(command);
	}

}
