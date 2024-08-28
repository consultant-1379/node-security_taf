/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class SecadmRbacFieldTechnicianScenariosTestSteps extends NodeSecurityTestCaseHelper {
   
    @TestStep(id = "verifyCannotUseSecadmWhenFieldTechnicianUserRole")
    public void verifyCannotUseSecadmWhenFieldTechnicianUserRole()
    {
        System.out.println(" \n\n ############# Starting test verifyCannotUseSecadmWhenFieldTechnicianUserRole ############# \n\n");

        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.FIELD_TECHNICIAN);
        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation("oasis_fieldtechnician");
        getLoginOperator().deleteUser();

        //secadm get
        String httpResponseBody = getSecurityCommandsOperator().getSecurityLevel("someNodeName").getBody();
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ACCESS_VIOLATION_ERROR_MESSAGE));

        //secadm set
        httpResponseBody=getSecurityCommandsOperator().setSecurityLevel("someNodeName","2").getBody();
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ACCESS_VIOLATION_ERROR_MESSAGE));

        //secadm credentials create
        httpResponseBody=getCredentialsOperator().createCredentials("someNodeName",
                NscsServiceGetter.NETSIM_ROOT_USER_NAME,
                NscsServiceGetter.NETSIM_ROOT_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
                NscsServiceGetter.NETSIM_NORMAL_PASSWORD).getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ACCESS_VIOLATION_ERROR_MESSAGE));

        httpResponseBody=getCredentialsOperator().updateCredentials("someNodeName",
                NscsServiceGetter.NETSIM_ROOT_USER_NAME,
                NscsServiceGetter.NETSIM_ROOT_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_NORMAL_USER_NAME,
                NscsServiceGetter.NETSIM_NORMAL_PASSWORD).getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ACCESS_VIOLATION_ERROR_MESSAGE));

        getLoginOperator().deleteUser();

    }
   
    @TestStep(id = "verifyCannotUseSecadmWhenFieldTechnicianUserRoleForSgsn")
    public void verifyCannotUseSecadmWhenFieldTechnicianUserRoleForSgsn()
    {
        System.out.println(" \n\n ############# Starting test verifyCannotUseSecadmWhenFieldTechnicianUserRoleForSgsn ############# \n\n");

        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.FIELD_TECHNICIAN);
        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation("oasis_fieldtechnician");
        getLoginOperator().deleteUser();

        //secadm get
        String httpResponseBody = getSecurityCommandsOperator().getSecurityLevel("someNodeName").getBody();
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ACCESS_VIOLATION_ERROR_MESSAGE));

        //secadm set
        httpResponseBody=getSecurityCommandsOperator().setSecurityLevel("someNodeName","2").getBody();
        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ACCESS_VIOLATION_ERROR_MESSAGE));

        //secadm credentials create
        httpResponseBody=getCredentialsOperator().createCredentialsForSgsn("someNodeName",
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD).getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ACCESS_VIOLATION_ERROR_MESSAGE));

        httpResponseBody=getCredentialsOperator().updateCredentialsForSgsn("someNodeName",
                NscsServiceGetter.NETSIM_SECURE_PASSWORD,
                NscsServiceGetter.NETSIM_SECURE_PASSWORD).getBody();

        Assert.assertTrue(httpResponseBody.contains(NscsServiceGetter.ACCESS_VIOLATION_ERROR_MESSAGE));

        getLoginOperator().deleteUser();

    }
    
}
