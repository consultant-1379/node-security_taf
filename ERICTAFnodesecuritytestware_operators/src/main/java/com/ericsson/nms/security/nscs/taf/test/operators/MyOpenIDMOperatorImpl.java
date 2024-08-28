/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.data.User;
import com.ericsson.cifwk.taf.data.UserType;
import com.ericsson.cifwk.taf.tools.http.HttpResponse;
import com.ericsson.cifwk.taf.tools.http.HttpTool;
import com.ericsson.cifwk.taf.tools.http.RequestBuilder;
import com.ericsson.cifwk.taf.tools.http.constants.ContentType;
import com.ericsson.nms.launcher.LauncherOperator;
import com.ericsson.nms.security.ENMUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;
import java.util.regex.Pattern;

//@Operator(context = Context.REST)
public class MyOpenIDMOperatorImpl implements MyOpenIDMOperator {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String USER_URI = "openidm/managed/user/%s";
    private static final String ROLE_URI = "openidm/endpoint/manageRole?action=adduser&rName=%s";
    private static final String DISABLE_CHANGE_PASS_URI = "openidm/endpoint/passwordReset?state=false";

    private static final String CREATE_USER_COMMAND = "{\"userName\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\"," +
            "\"email\":\"%s\",\"password\":\"%s\",\"userType\":\"enmUser\",\"status\":\"%s\"}";

    private static final String DISABLE_CHANGE_PASSWORD_COMMAND = "[\"%s\"]";

    //############################# NEW   #############################

    private static final String USER_LIST_URI = "openidm/managed/user/?_queryId=query-all-ids";

    //############################# NEW   #############################


    final private List<String> requestLog = new ArrayList<>();

    @Inject
    private LauncherOperator launcherOperator;

    private HttpTool httpTool;

    @Override
    public void connect(final String userName, final String userPassword) {

        httpTool = launcherOperator.login(new User(userName,userPassword, UserType.ADMIN));

    }

    @Override
    public void createUser(final ENMUser user){

        final String status = (user.getEnabled())? "enabled" : "disabled";
        final String uri = String.format(USER_URI,user.getUsername());
        final String command = String.format(CREATE_USER_COMMAND,user.getUsername(),user.getFirstName(),
                user.getLastName(),user.getEmail(),user.getPassword(),status);

        final HttpResponse response = executePutRestCall(uri, command);

        this.logOperation(uri, "", response);

        final String commandDisableChangePass = String.format(
                DISABLE_CHANGE_PASSWORD_COMMAND, user.getUsername());
        final HttpResponse responseChangePass = executePostRestCall(
                DISABLE_CHANGE_PASS_URI, ContentType.APPLICATION_JSON,
                commandDisableChangePass);

    }

    @Override
    public void deleteUser(final String username){

        final String uri = String.format(USER_URI,username);
        final HttpResponse response = executeDeleteRestCall(uri);

        this.logOperation(uri, "", response);

    }


    @Override
    public void assignUsersToRole(final String role, final String... usernames){

        String usernamesStr = Arrays.toString(usernames);
        usernamesStr = usernamesStr.trim().replaceAll("\\[","").replaceAll("]","");
        usernamesStr = usernamesStr.replaceAll(" ","");

        final Map<String,String> usernamesHeader = new HashMap<>();
        usernamesHeader.put("X-Usernames",usernamesStr);

        final String uri = String.format(ROLE_URI,role);

        final HttpResponse response = executeGetRestCall(uri, ContentType.APPLICATION_JSON, usernamesHeader);

        this.logOperation(uri,usernamesStr,response);
    }

    @Override
    public List<String> getRequestLog() {
        return this.requestLog;
    }

    private HttpResponse executePutRestCall(final String uri, final String jsonString) {
        logger.debug("put URL:{}", httpTool.getBaseUri() + uri);

        final RequestBuilder request = httpTool.request();

        final HttpResponse response = request
                .contentType(ContentType.APPLICATION_JSON)
                .header("Accept", "application/json")
                .header("X-Requested-With", "XMLHttpRequest")
                .body(jsonString)
                .timeout(15)
                .put(uri);

        return response;
    }

    private HttpResponse executeGetRestCall(final String uri, final String contentType,
                                            final Map<String,String> additionalHeaders) {
        logger.debug("get URL:{}", httpTool.getBaseUri() + uri);

        final RequestBuilder request = httpTool.request();

        if(additionalHeaders != null) {
            for(String headerName : additionalHeaders.keySet()) {
                request.header(headerName, additionalHeaders.get(headerName));
            }
        }

        final HttpResponse response = request
                .contentType(contentType)
                .header("Accept", "application/json")
                .header("X-Requested-With", "XMLHttpRequest")
                .timeout(15)
                .get(uri);

        return response;
    }

    private HttpResponse executePostRestCall(final String uri, final String contentType, final String jsonString) {
        logger.debug("post URL:{}", httpTool.getBaseUri() + uri);

        final RequestBuilder request = httpTool.request();

        final HttpResponse response = request
                .contentType(contentType)
                .header("Accept", "application/json")
                .header("X-Requested-With", "XMLHttpRequest")
                .body(jsonString)
                .timeout(15)
                .post(uri);

        return response;
    }

    private HttpResponse executeDeleteRestCall(final String uri) {
        logger.debug("put URL:{}", httpTool.getBaseUri() + uri);

        final RequestBuilder request = httpTool.request();

        final HttpResponse response = request
                .contentType(ContentType.APPLICATION_JSON)
                .header("Accept", "application/json")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("If-Match", "\"*\"")
                .timeout(15)
                .delete(uri);

        return response;
    }

    private void logOperation(final String url, final String requestBody, final HttpResponse response) {

        final StringBuilder logBuilder = new StringBuilder();

        logBuilder.append("Request URL: ").append(url);
        logBuilder.append(System.lineSeparator());
        logBuilder.append("Request Body: ").append(requestBody);
        logBuilder.append(System.lineSeparator());
        logBuilder.append("Response Code: ").append(response.getResponseCode().getCode())
                .append(" ").append(response.getResponseCode().name());
        logBuilder.append(System.lineSeparator());
        logBuilder.append("Response Body: ").append(response.getBody());

        requestLog.add(logBuilder.toString());
    }




    @Override
    public String getUser(final String username){

        final String uri = String.format(USER_URI,username);
        final HttpResponse response = executeGetRestCall(uri,ContentType.APPLICATION_JSON, null);

        final String userDetails = response.getBody();

        System.out.println("\n\n **********  User Details: " + userDetails  );

        return userDetails;
    }

    @Override
    public String getUsers(){

        final String uri = USER_LIST_URI;

        final HttpResponse response = executeGetRestCall(uri,ContentType.APPLICATION_JSON, null);

        this.logOperation(uri,"",response);

        final String userList = response.getBody();

        return userList;
    }


    @Override
    public boolean checkUserExists(final String username){

        final String responseBody = getUsers();

        //return (responseBody.contains(username));
        /// [luck]
        final Pattern pattern = Pattern.compile("[^a-zA-Z0-9]?" + username + "[^a-zA-Z0-9]?");
        final boolean result = pattern.matcher(responseBody).find();

        System.out.println("\n\n **********  result of checking  if user : " + username + " exists : user exists " + result );

        return result;
    }



}
