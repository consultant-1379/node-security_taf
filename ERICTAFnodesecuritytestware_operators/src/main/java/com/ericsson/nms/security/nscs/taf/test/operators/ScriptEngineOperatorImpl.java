package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.cifwk.taf.tools.http.HttpTool;
import com.ericsson.nms.security.nscs.taf.test.utils.NodeSecurityTestUtil;
import com.ericsson.oss.services.cm.rest.CmEditorRestOperator;
import com.ericsson.oss.services.scriptengine.spi.dtos.Command;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.InputStream;
import java.util.Set;




//@Operator(context = Context.REST)
@Singleton
public class ScriptEngineOperatorImpl implements ScriptEngineOperator {

    private static Logger logger = Logger.getLogger(ScriptEngineOperatorImpl.class);

    private HttpTool httpTool;

//    @Inject
//    private OperatorRegistry<LoginOperator> loginOperatorProvider;

    @Inject
    private LoginOperatorImpl loginOperator;

    @Inject
    private CmEditorRestOperator cmEditorRestOperator;


    public void login() {
        if ( httpTool == null ) {

            httpTool = loginOperator.login();

            cmEditorRestOperator.setTool(httpTool);

            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    logout();
                }
            });
        }
    }

    public void logout() {
        if ( loginOperator != null ) {
            loginOperator.logout();
        }
    }

    public boolean runCommand( final String commandString , final String expectedString){

        System.out.println("\n\n*** Executing Command :   *** "+commandString);

        final HttpResponse response = sendHttpRequests(commandString);

        final String httpResponseBody = response.getBody();

//        System.out.println("\n\n*** Response body(JSON)in httpResponse is : ***");
        System.out.println("\n\n***  " + httpResponseBody + "\n");

        final ResponseDtoWrapper responseDtoWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponseBody);
        return responseDtoWrapper.containsString(expectedString);

    }

    public HttpResponse runCommand( final String commandString){

        System.out.println("\n\n*** Executing Command :   *** "+commandString);

        final HttpResponse httpResponse = sendHttpRequests(commandString);

        final String httpResponseBody = httpResponse.getBody();

//        System.out.println("\n\n*** Response body(JSON)in httpResponse is : ***");
        System.out.println("\n\n***  " + httpResponseBody + "\n");

        return httpResponse;
    }


    public HttpResponse runCommand(  final String commandString , final File file, final String fileName){

        System.out.println("\n\n*** Executing Command :   *** "+commandString);

        final HttpResponse httpResponse = sendHttpRequestsWithFile(commandString, file, fileName);

        final String httpResponseBody = httpResponse.getBody();
//        System.out.println("\n\n*** Response body(JSON)in httpResponse is : ***");
        System.out.println("\n\n***  " + httpResponseBody + "\n");

        return httpResponse;

    }

    public Set<String> getAllMOsOfParticularType(final String moType){

        final HttpResponse httpResponse = runCommand("cmedit get * " + moType);

        final ResponseDtoWrapper reponseDTOWrapper = ResponseDtoWrapper.newResponseDtoWrapper(httpResponse.getBody());

        return reponseDTOWrapper.fetchFdnOfMosFromResponseFromCmeditMoQuery();
    }

    /**
     * load the xml template and execute ipsec om & traffic command
     *
     * @param string
     *            command
     * @return httpResponseBody
     */
    @Override
    public String executeWithFile(final Command command) {
        logger.info("Inside ScriptEngineRestOperator -> executeWithFile");
        String fileName = (String) command.getProperties().get("fileName");
        final String filePath = (String) command.getProperties().get("filePath");

        fileName = "file:" + fileName;
        final InputStream inputStream = NodeSecurityTestUtil.getStreamFromAbsoluteFilePath(filePath);
        final File file = NodeSecurityTestUtil.inputStreamToFile(inputStream, fileName);
        final String cmd = command.getCommand();
        System.out.println("Command : " + cmd);
        //          NodeSecurityTestUtil.displayFile(file);
        final HttpResponse response = runCommand(cmd, file, file.getName());
        return response != null ? response.getBody() : null;

    }

    private HttpResponse sendHttpRequests(final String commandString) {
        login();
        return cmEditorRestOperator.getHttpResponse(commandString);
    }

    private HttpResponse sendHttpRequestsWithFile(final String commandString, final File file, final String fileName) {
        login();
        return cmEditorRestOperator.getHttpResponseWithFile(commandString, file);
    }
}