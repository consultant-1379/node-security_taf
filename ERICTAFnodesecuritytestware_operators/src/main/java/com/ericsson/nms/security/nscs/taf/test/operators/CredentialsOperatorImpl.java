package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.testng.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

//@Operator(context = Context.REST)
public class CredentialsOperatorImpl implements CredentialsOperator {

    @Inject
    private ScriptEngineOperatorImpl scriptEngineOperator;

    @Override
	public HttpResponse createCredentials(final String nodeList,
                                     final String rootUserName,final String rootUserPassword,
                                     final String secureUserName,final String secureUserPassword,
                                     final String normalUserName,final String normalUserPassword){

        final String createCredentialsCommand = "secadm credentials create"+
                " --rootusername "+rootUserName+
                " --rootuserpassword "+rootUserPassword+
                " --secureuserpassword "+secureUserPassword+
                " --secureusername "+secureUserName+
                " --normaluserpassword "+normalUserPassword+
                " --normalusername "+normalUserName+
                " --nodelist "+nodeList;

        return scriptEngineOperator.runCommand(createCredentialsCommand);

    }

    @Override
    public HttpResponse createCredentialsForSgsn(final String nodeList,
                                    final  String secureUserName,final String secureUserPassword){

        final String createCredentialsCommand = "secadm credentials create"+
                " --secureuserpassword "+secureUserPassword+
                " --secureusername "+secureUserName+
                " --nodelist "+nodeList;

        return scriptEngineOperator.runCommand(createCredentialsCommand);
    
    }

    @Override
    public HttpResponse createCredentialsWithFile(final String nodeList,
                                          final String rootUserName,final String rootUserPassword,
                                          final String secureUserName,final String secureUserPassword,
                                          final String normalUserName,final String normalUserPassword){


        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeList.replace(",", ";").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());  //cause test case to fail
        }

        final String createCredentialsCommand = "secadm credentials create"+
                " --rootusername "+rootUserName+
                " --rootuserpassword "+rootUserPassword+
                " --secureuserpassword "+secureUserPassword+
                " --secureusername "+secureUserName+
                " --normaluserpassword "+normalUserPassword+
                " --normalusername "+normalUserName+
                " --nodefile "+ fileName;


        final File file = new File(fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileContents);
            fileOutputStream.close();
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return scriptEngineOperator.runCommand(createCredentialsCommand, file, fileName);
    }

    //TODO Operator needs to be updated to allow only specify some of these fields ?
    @Override
   	public HttpResponse updateCredentials(final String nodeList,
                                        final String rootUserName,final String rootUserPassword,
                                        final String secureUserName,final String secureUserPassword,
                                        final String normalUserName,final String normalUserPassword){
           final String createCredentialsCommand = getCredentialUpdateCommand(nodeList,
                   rootUserName, rootUserPassword,
                   secureUserPassword, secureUserName,
                   normalUserPassword, normalUserName);
           return scriptEngineOperator.runCommand(createCredentialsCommand);
       }

    @Override
    public  HttpResponse updateCredentialsForSgsn(final String nodeList,
               final String secureUserName,final String secureUserPassword ){

        final String createCredentialsCommand = getCredentialUpdateCommandForSgsn(nodeList,
                   secureUserPassword, secureUserName);
           return scriptEngineOperator.runCommand(createCredentialsCommand);
    }

    @Override
    public HttpResponse updateCredentialsWithFile(final String nodeList,
                                                  final String rootUserName,final String rootUserPassword,
                                                  final String secureUserName,final String secureUserPassword,
                                                  final String normalUserName,final String normalUserPassword){

        final String fileName = "file:SecadmInputFile.txt";

        final String updateCredentialsCommandWithFile = getCredentialUpdateCommandWithFile(fileName,
                rootUserName, rootUserPassword,
                secureUserPassword, secureUserName,
                normalUserPassword, normalUserName);

        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeList.replace(",", ";").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());  //cause test case to fail
        }

        final File file = new File(fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileContents);
            fileOutputStream.close();
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return scriptEngineOperator.runCommand(updateCredentialsCommandWithFile, file, fileName);
    }

    @Override
    public  HttpResponse updateCredentialsWithFileForSgsn(final String nodeList,
                                                   final String secureUserName,final String secureUserPassword){
       
        final String fileName = "file:SecadmInputFile.txt";

        final String updateCredentialsCommandWithFile = getCredentialUpdateCommandWithFileForSgsn(fileName,
                secureUserPassword, secureUserName);

        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeList.replace(",", ";").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());  //cause test case to fail
        }

        final File file = new File(fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileContents);
            fileOutputStream.close();
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return scriptEngineOperator.runCommand(updateCredentialsCommandWithFile, file, fileName);    
    }


    @Override
	public boolean checkCredentialsExist(final String nodeName){

        final String commandString = String.format(NscsServiceGetter.CMEDIT_GET_NETWORK_ELEMENT_SECURITY_COMMAND,nodeName);

        return scriptEngineOperator.runCommand(commandString,NscsServiceGetter.ONE_INSTANCE);

        }

    @Override
    public boolean deleteCredentials(final String nodeName) {

        final String commandString = String.format(NscsServiceGetter.CMEDIT_DELETE_NETWORK_ELEMENT_SECURITY_COMMAND,nodeName);
        return scriptEngineOperator.runCommand(commandString,NscsServiceGetter.ONE_INSTANCE);

    }
    
    public static String getCredentialUpdateCommand(final String nodeList,
                                        final String rootUserName,final String rootUserPassword,
                                        final String secureUserName,final String secureUserPassword,
                                        final String normalUserName,final String normalUserPassword) {
    	return String.format("secadm credentials update %s %s %s %s %s %s %s",    	
                rootUserName == null ? 			" " : " --rootusername " + rootUserName,
                rootUserPassword == null ? 		" " : " --rootuserpassword " + rootUserPassword,
                secureUserPassword == null ?	" " : " --secureuserpassword " + secureUserPassword,
                secureUserName == null ? 		" " : " --secureusername " + secureUserName,
                normalUserName == null ? 		" " : " --normalusername " + normalUserName,					   
                normalUserPassword == null ? 	" " : " --normaluserpassword " +  normalUserPassword,	               		  
                " --nodelist " + nodeList);
    }

    
    public static String getCredentialUpdateCommandForSgsn(final String nodeList,
                                        final String secureUserName,final String secureUserPassword) {
    	return String.format("secadm credentials update %s %s %s",    	
                secureUserPassword == null ?	" " : " --secureuserpassword " + secureUserPassword,
                secureUserName == null ? 		" " : " --secureusername " + secureUserName,
                " --nodelist " + nodeList);
    }

    public static String getCredentialUpdateCommandWithFile(final String fileName,
                                                    final String rootUserName,final String rootUserPassword,
                                                    final String secureUserName,final String secureUserPassword,
                                                    final String normalUserName,final String normalUserPassword) {
        return String.format("secadm credentials update %s %s %s %s %s %s %s",
                rootUserName == null ? 			" " : " --rootusername " + rootUserName,
                rootUserPassword == null ? 		" " : " --rootuserpassword " + rootUserPassword,
                secureUserPassword == null ?	" " : " --secureuserpassword " + secureUserPassword,
                secureUserName == null ? 		" " : " --secureusername " + secureUserName,
                normalUserName == null ? 		" " : " --normalusername " + normalUserName,
                normalUserPassword == null ? 	" " : " --normaluserpassword " +  normalUserPassword,
                " --nodefile "+ fileName);
    }

    public static String getCredentialUpdateCommandWithFileForSgsn(final String fileName,
                                                    final String secureUserName,final String secureUserPassword) {
        return String.format("secadm credentials update %s %s %s",
                secureUserPassword == null ?	" " : " --secureuserpassword " + secureUserPassword,
                secureUserName == null ? 		" " : " --secureusername " + secureUserName,
                " --nodefile "+ fileName);
    }

}
