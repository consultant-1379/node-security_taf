/*
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 */

package com.ericsson.nms.security.nscs.taf.test.cases;

import static com.ericsson.cifwk.taf.assertions.TafAsserts.assertNotNull;

import com.ericsson.cifwk.taf.TestCase;
import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.guice.OperatorRegistry;
import com.ericsson.cifwk.taf.tools.http.HttpTool;
import com.ericsson.nms.launcher.LauncherOperator;
import com.ericsson.nms.security.ENMUser;
import com.ericsson.nms.security.OpenIDMOperatorImpl;
import com.ericsson.nms.security.nscs.api.cpp.level.CPPSecurityLevel;
import com.ericsson.nms.security.nscs.api.iscf.IscfResponse;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.IscfServiceOperator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;

/**
 * Functional tests of IscfServiceOperator interface implementations. This is
 * based on the IscfService reference implementation
 * 
 * Aim of these tests is to test IscfService functionality on a live system,
 * which generates XML required by nodes during auto-integration
 * 
 * At this point, a manual pre-step is required to set up dummy PKI data until
 * the real PKI solution is implemented
 * 
 * @author ealemca
 */
public class IscfService_FunctionalTest extends TorTestCaseHelper implements
		TestCase {

    private static Logger logger = Logger
            .getLogger(IscfService_FunctionalTest.class);

	@Inject
	OperatorRegistry<IscfServiceOperator> iscfServiceProvider;

    private final ENMUser oasisUserToCreate = new ENMUser();

    private HttpTool httpTool;

    @Inject
    private LauncherOperator launcherOperator;

    @Inject
    OpenIDMOperatorImpl openIDMOperator;

    @BeforeClass
    public void setUp() {

        try {
            oasisUserToCreate.setUsername(NscsServiceGetter.OASIS_USER_NAME);
            oasisUserToCreate.setFirstName(NscsServiceGetter.OASIS_FIRST_NAME);
            oasisUserToCreate.setLastName(NscsServiceGetter.OASIS_SECOND_NAME);
            oasisUserToCreate.setEmail(NscsServiceGetter.OASIS_EMAIL);
            oasisUserToCreate.setPassword(NscsServiceGetter.OASIS_PASSWORD);
            oasisUserToCreate.setEnabled(true);
logger.info("oasisUserToCreate.set(): BEGIN...");
//            oasisUserToCreate.setUsername("root");
//            oasisUserToCreate.setFirstName(NscsServiceGetter.OASIS_FIRST_NAME);
//            oasisUserToCreate.setLastName(NscsServiceGetter.OASIS_SECOND_NAME);
//            oasisUserToCreate.setEmail(NscsServiceGetter.OASIS_EMAIL);
//            oasisUserToCreate.setPassword("passw0rd");
//            oasisUserToCreate.setEnabled(true);
logger.info("oasisUserToCreate.set():  ...END.");
logger.info("openIDMOperator.*(): BEGIN...");
            openIDMOperator.connect(NscsServiceGetter.ENM_ADMIN_USER_NAME, NscsServiceGetter.ENM_ADMIN_PASSWORD);
            openIDMOperator.createUser(oasisUserToCreate);
            openIDMOperator.assignUsersToRole("ADMINISTRATOR", oasisUserToCreate.getUsername());
            openIDMOperator.assignUsersToRole("OPERATOR", oasisUserToCreate.getUsername());
            openIDMOperator.assignUsersToRole("SECURITY_ADMIN", oasisUserToCreate.getUsername());
logger.info("openIDMOperator.*():  ...END.");
logger.info("launcherOperator.login(): BEGIN...");
            httpTool = launcherOperator.login(NscsServiceGetter.OASISUSER01);
         // httpTool = launcherOperator.login(NscsServiceGetter.OASISUSER02);
logger.info("launcherOperator.login(): ...END.");         

        } catch (final Exception e) {
            logger.error(e.getMessage());
            logger.debug("Setup Error",e);
        }
    }

    @AfterClass
    public void tearDown() {

        try {
            if (launcherOperator.checkIfLoggedIn(httpTool)) {
                httpTool = launcherOperator.logout();
            }

            openIDMOperator.deleteUser(oasisUserToCreate.getUsername());
        } catch (final Exception e) {
            logger.error(e.getMessage());
        }
    }

	@Context(context = { Context.API })
	@Test(groups = { "Acceptance" })
	public void tORF12546_Func_1_Generate_SecLevel_Iscf() {

logger.info("tORF12546_Func_1_Generate_SecLevel_Iscf(): ENTERED...");
logger.info("oasisUserToCreate.getUsername(): " + oasisUserToCreate.getUsername());
logger.info("httpTool.getBaseUri(): " + httpTool.getBaseUri());
logger.info("httpTool.toString(): " + httpTool.toString());
logger.info("!launcherOperator.checkIfLoggedIn(): BEFORE...");		
        if (!launcherOperator.checkIfLoggedIn(httpTool)) {
logger.info("!launcherOperator.checkIfLoggedIn(): ENTERED...");
            Assert.fail("ERROR - Not logged in");
        }

logger.info("final IscfResponse response = : BEFORE...");
		final IscfResponse response = getIscfOperator().generate("IllogicalName",
				"OASIS_ERBS01", CPPSecurityLevel.LEVEL_2,
				CPPSecurityLevel.LEVEL_2);
//		assertOjectNotNull(response);
                assertNotNull(response);
logger.info("final IscfResponse response = : BEFORE...");
		System.out.println("ISCF content");
		try {
			System.out.println(new String(response.getIscfContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println(String.format(
					"Character encoding problem [ %s ]", e.getMessage()));
		}
	}

	private IscfServiceOperator getIscfOperator() {
		final IscfServiceOperator op = iscfServiceProvider.provide(IscfServiceOperator.class);
        op.setHttpTool(httpTool);
        logger.info("ENTERED 'private getIscfOperator()' in class 'IscfService_FunctionalTest()'");
        logger.info("[IscfServiceOperator] op: " + op.toString());
		return op;
	}

}
