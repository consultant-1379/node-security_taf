package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.data.User;
import com.ericsson.cifwk.taf.data.UserType;
import com.ericsson.cifwk.taf.tools.http.HttpTool;
import com.ericsson.nms.launcher.LauncherOperator;
   import com.ericsson.nms.security.ENMUser;
 //In /home/enmadmin/workspace_TCs/TAF/taf-tor-operators/OpenIDM-impl/src/main/java/com/ericsson/nms/security/OpenIDMOperatorImpl.java:
 //import com.ericsson.nms.data.ENMUser;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
//@Operator(context = Context.REST)
public class LoginOperatorImpl implements LoginOperator {


    private static Logger logger = Logger.getLogger(LoginOperatorImpl.class);

//    @Inject
//    private OperatorRegistry<LauncherOperator> launcherOperatorProvider;
    @Inject
    private LauncherOperator launcherOperator;

    //TODO REmove MyOpenIDMOperator
//    @Inject
//    private OperatorRegistry<MyOpenIDMOperatorImpl> openIDMOperatorProvider;

    @Inject
    private MyOpenIDMOperatorImpl openIDMOperator;
//TODO Remove MyOpenIDMOperator


    @Inject
    private ENMUser enmUser;

    private HttpTool httpTool;

    private String userRole="ADMINISTRATOR";
    private String userName=NscsServiceGetter.OASIS_USER_NAME;

    @Override
    public void assignUserNameThatUserShouldHaveOnCreation(final String userName){
        this.userName=userName;
    }

    @Override
    public void assignRoleThatUserShouldHaveOnCreation(final String userRole){

        //System.out.println("\n **********  Setting user Role to " + userRole + " This will be used on user creation ***************:   ");

        switch (userRole) {
            case "ADMINISTRATOR":
                this.userRole=userRole;
                break;
            case "OPERATOR":
                this.userRole=userRole;
                break;
            case "SECURITY_ADMIN":
                this.userRole=userRole;
                break;
            case "FIELD_TECHNICIAN":
                this.userRole=userRole;
                break;
            default: throw new RuntimeException("Not Supported Role Type");
        }
        //System.out.println("\n **********  role is now  " + userRole + " ***************:   ");

    }

    @Override
    public HttpTool login() {

        if (httpTool!=null)   {
            System.out.println("\n **********  User already logged in " + userName + " ***************:   ");
            return httpTool;
        }

        createUser();

        System.out.println("\n **********  Attempting to login with user " + userName + " ***************:   ");
        httpTool = launcherOperator.login(new User(userName, NscsServiceGetter.OASIS_PASSWORD , UserType.ADMIN));

        if (!launcherOperator.checkIfLoggedIn(httpTool)){
            throw new RuntimeException("Problem logging in as " + userName + " with password " +  NscsServiceGetter.OASIS_PASSWORD);
        }

        System.out.println("\n **********  Login Successful " + userName + " ***************:   ");

        return httpTool;
    }

    @Override
    public boolean logout() {
        if (httpTool == null ){
            return false;
        }
        //login never called
        if (launcherOperator == null ){
            return false;
        }

        System.out.println("\n **********  logging out user " +  userName  + " ***************:   " );
        httpTool = launcherOperator.logout();

//Delete the user if it exists at this point
        deleteUser();

        return true;
    }

    @Override
    public boolean checkIfLoggedIn() {
        if (httpTool == null ){
            return false;
        }
        return launcherOperator.checkIfLoggedIn(httpTool);
    }


    public void deleteUser(){

        //If never logged in or created a user previously then the openIDMOperator not available
        //Delete of users was intended to be called on JVM shutdown so cant use openIDMOperatorProvider.provide during shutdown of JVM
        //Now using for RBAC tests to delete them as we cant change the roles at runtime yet (no operator support)

        openIDMOperator.connect(NscsServiceGetter.ENM_ADMIN_USER_NAME, NscsServiceGetter.ENM_ADMIN_PASSWORD);
        logger.debug("\n\n**********  deleting user  " + userName + " ***************:   ");
        openIDMOperator.deleteUser(userName);
        final boolean userExists = openIDMOperator.checkUserExists(userName);

        if (userExists){
            throw new RuntimeException("Problem: : User Still exists after calling delete");
        }


    }

    private void createUser(){

        if (!checkUserExists()){


            System.out.println("\n\n**********  User does not exist : Attempting to create user  " + userName + " with role " + userRole  + " ***************:   ");

            enmUser.setUsername(userName);
            enmUser.setFirstName(NscsServiceGetter.OASIS_FIRST_NAME);
            enmUser.setLastName(NscsServiceGetter.OASIS_SECOND_NAME);
            enmUser.setEmail(NscsServiceGetter.OASIS_EMAIL);
            enmUser.setPassword(NscsServiceGetter.OASIS_PASSWORD);
            enmUser.setEnabled(true);

            openIDMOperator.connect(NscsServiceGetter.ENM_ADMIN_USER_NAME, NscsServiceGetter.ENM_ADMIN_PASSWORD);
            openIDMOperator.createUser(enmUser);

            //Assign user to the intended role
            openIDMOperator.assignUsersToRole(userRole, enmUser.getUsername());

            if (!openIDMOperator.checkUserExists(userName)){
                throw new RuntimeException("Problem: : User does not exist after calling create user");
            }

            System.out.println("\n\n**********  Created User " + userName + " ***************:   ");
            System.out.println("\n\n**********  User Details  " + userName + " : " + openIDMOperator.getUser(userName) + " ***************:   ");
        }
    }


    public boolean checkUserExists(){

        //If never logged in or created a user previously then the openIDMOperator not available
        //Delete of users was intended to be called on JVM shutdown so cant use openIDMOperatorProvider.provide during shutdown of JVM
        //Now using for RBAC tests to delete them as we cant change the roles at runtime yet (no operator support)


        openIDMOperator.connect(NscsServiceGetter.ENM_ADMIN_USER_NAME, NscsServiceGetter.ENM_ADMIN_PASSWORD);
        if (openIDMOperator.checkUserExists(userName)) {
            return true;
        }
        return false;

    }


    //TODO Remove MyOpenIDMOperator
//    private MyOpenIDMOperatorImpl getOpenIDMOperator(){
//        openIDMOperator=openIDMOperatorProvider.provide(MyOpenIDMOperatorImpl.class);
//        return openIDMOperator;
//    }
//    //TODO Remove MyOpenIDMOperator


}


