package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.nms.security.ENMUser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eeidwn
 * Date: 12/09/14
 * Time: 08:41
 * To change this template use File | Settings | File Templates.
 */
public interface MyOpenIDMOperator {
    String USER_URI = "openidm/managed/user/%s";

    void connect(String userName, String userPassword);

    void createUser(final ENMUser user);

    void assignUsersToRole(String role, String... usernames);

    void deleteUser(final String username);

    List<String> getRequestLog();

    String getUser(final String username);

    boolean checkUserExists(String username);

    String getUsers();
}
