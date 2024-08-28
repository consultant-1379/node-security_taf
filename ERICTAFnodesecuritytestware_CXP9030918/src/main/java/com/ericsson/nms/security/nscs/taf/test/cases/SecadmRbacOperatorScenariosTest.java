package com.ericsson.nms.security.nscs.taf.test.cases;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SecadmRbacOperatorScenariosTest extends NodeSecurityTestCaseHelper {

    @Context(context = { Context.REST })
    @Test(groups={"Acceptance"})
    public void verifyCannotUseSecadmWhenOperatorUserRole(
            )
    {
        System.out.println(" \n\n ############# Starting test verifyCannotUseSecadmWhenOperatorUserRole ############# \n\n");

        getLoginOperator().assignRoleThatUserShouldHaveOnCreation(NscsServiceGetter.OPERATOR);
        getLoginOperator().assignUserNameThatUserShouldHaveOnCreation("oasis_operator");
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

}




