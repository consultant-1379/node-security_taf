package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;

import org.testng.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

//@Operator(context = Context.REST)
public class KeygenOperatorImpl implements KeygenOperator {

    @Inject
    private ScriptEngineOperatorImpl scriptEngineOperator;

    @Override
	public HttpResponse createKeypair(final String nodeList, final String algorithmTypeSize){

        final String createdKeys = "secadm keygen create"+
                " --algorithm-type-size "+algorithmTypeSize+
                " --nodelist "+nodeList;

        return scriptEngineOperator.runCommand(createdKeys);
    }

    @Override
    public HttpResponse createKeypairWithFile(final String nodeList, final String algorithmTypeSize){


        final String fileName = "file:SecadmInputFile.txt";
        byte[] fileContents = new byte[0];
        try {
            fileContents = nodeList.replace(",", ";").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());  //cause test case to fail
        }

        final String createdKeys = "secadm keygen create"+
                " --algorithm-type-size "+algorithmTypeSize+
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

        return scriptEngineOperator.runCommand(createdKeys, file, fileName);
    }
    
    @Override
	public HttpResponse updateKeypair(final String nodeList, final String algorithmTypeSize){

        final String updatedKeys = "secadm keygen update"+
                " --algorithm-type-size "+algorithmTypeSize+
                " --nodelist "+nodeList;

        return scriptEngineOperator.runCommand(updatedKeys);
    }
    
    @Override
   	public HttpResponse updateKeypair(final String nodeList){

       final String updatedKeys = "secadm keygen update"+
               " --nodelist "+nodeList;

       return scriptEngineOperator.runCommand(updatedKeys);
   }
    
    public static String getKeygenCreateNodeListCommand(final String nodeList, final String algorithmTypeSize) {
    	return String.format("secadm keygen create --nodelist %s --algorithm-type-size %s", nodeList, algorithmTypeSize);
    }
    
    public static String getKeygenCreateNodeFileCommand(final String nodeFile, final String algorithmTypeSize) {
    	return String.format("secadm keygen create --nodefile file:%s --algorithm-type-size %s", nodeFile, algorithmTypeSize);
    }
    
    public static String getKeygenUpdateNodeListCommand(final String nodeList, final String algorithmTypeSize) {
    	return String.format("secadm keygen update --nodelist %s --algorithm-type-size %s", nodeList, algorithmTypeSize);
    }
    
    public static String getKeygenUpdateNodeListCommand(final String nodeList) {
    	return String.format("secadm keygen update --nodelist %s", nodeList);
    }    

    public static String getKeygenUpdateNodeFileCommand(final String nodeFile, final String algorithmTypeSize) {
    	return String.format("secadm keygen update --nodefile file:%s --algorithm-type-size %s", nodeFile, algorithmTypeSize);
    }
    
    public static String getKeygenUpdateNodeFileCommand(final String nodeFile) {
    	return String.format("secadm keygen update --nodefile file:%s", nodeFile);
    }
}
