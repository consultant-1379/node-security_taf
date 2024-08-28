package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpResponse;

public interface CredentialsOperator {

     HttpResponse createCredentials(String nodeList,
                                     String rootUserName,String rootUserPassword,
                                     String secureUserName,String secureUserPassword,
                                     String normalUserName,String normalUserPassword);

     HttpResponse createCredentialsForSgsn(String nodeList,
                                     String secureUserName,String secureUserPassword);

    public  HttpResponse createCredentialsWithFile(String nodeList,
                                           String rootUserName,String rootUserPassword,
                                           String secureUserName,String secureUserPassword,
                                           String normalUserName,String normalUserPassword);

    public  HttpResponse updateCredentials(String nodeList,
            String rootUserName,String rootUserPassword,
            String secureUserName,String secureUserPassword,
            String normalUserName,String normalUserPassword);

    public  HttpResponse updateCredentialsForSgsn(String nodeList,
            String secureUserName,String secureUserPassword );
    
    public  HttpResponse updateCredentialsWithFile(String nodeList,
                                                   String rootUserName,String rootUserPassword,
                                                   String secureUserName,String secureUserPassword,
                                                   String normalUserName,String normalUserPassword);

    public  HttpResponse updateCredentialsWithFileForSgsn(String nodeList,
                                                   String secureUserName,String secureUserPassword);



    boolean checkCredentialsExist(final String nodeName);

    boolean deleteCredentials(final String nodeName);

}


