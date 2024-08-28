package com.ericsson.nms.security.nscs.taf.test.operators;


import com.ericsson.cifwk.taf.tools.http.HttpTool;

public interface LoginOperator {

    HttpTool login();

    boolean logout();

    boolean checkIfLoggedIn(); //, final Host host);

    void assignRoleThatUserShouldHaveOnCreation(String userRole);

    void assignUserNameThatUserShouldHaveOnCreation(String userName);

    void deleteUser();

    boolean checkUserExists();
}


