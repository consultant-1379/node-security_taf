/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.cases;

//import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.Output;
import com.ericsson.cifwk.taf.annotations.TestStep;

import static com.ericsson.cifwk.taf.assertions.TafAsserts.assertNotNull;

import com.ericsson.cifwk.taf.guice.OperatorRegistry;
import com.ericsson.cifwk.taf.tools.http.HttpTool;
import com.ericsson.nms.launcher.LauncherOperator;
import com.ericsson.nms.security.ENMUser;
import com.ericsson.nms.security.OpenIDMOperatorImpl;
import com.ericsson.nms.security.nscs.api.cpp.level.CPPSecurityLevel;
import com.ericsson.nms.security.nscs.api.enums.EnrollmentMode;
import com.ericsson.nms.security.nscs.api.enums.SecurityLevel;
import com.ericsson.nms.security.nscs.api.iscf.IpsecArea;
import com.ericsson.nms.security.nscs.api.iscf.IscfResponse;
import com.ericsson.nms.security.nscs.api.iscf.SubjectAltNameFormat;
import com.ericsson.nms.security.nscs.api.model.NodeModelInformation;
import com.ericsson.nms.security.nscs.api.model.NodeModelInformation.ModelIdentifierType;
import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;
import com.ericsson.nms.security.nscs.taf.test.operators.IscfServiceOperator;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 *
 * @author enmadmin
 */
public class IscfServicePositiveTestSteps extends NodeSecurityTestCaseHelper {

	private static Logger logger = Logger.getLogger(IscfServicePositiveTestSteps.class);

	@Inject
	OperatorRegistry<IscfServiceOperator> iscfServiceProvider;

    private final ENMUser oasisUserToCreate = new ENMUser();

    private HttpTool httpTool;

    @Inject
    private LauncherOperator launcherOperator;

    @Inject
    OpenIDMOperatorImpl openIDMOperator;

    @TestStep(id = "setUp")
    public void setUp() {

        try {
            oasisUserToCreate.setUsername(NscsServiceGetter.OASIS_USER_NAME);
            oasisUserToCreate.setFirstName(NscsServiceGetter.OASIS_FIRST_NAME);
            oasisUserToCreate.setLastName(NscsServiceGetter.OASIS_SECOND_NAME);
            oasisUserToCreate.setEmail(NscsServiceGetter.OASIS_EMAIL);
            oasisUserToCreate.setPassword(NscsServiceGetter.OASIS_PASSWORD);
            oasisUserToCreate.setEnabled(true);

            openIDMOperator.connect(NscsServiceGetter.ENM_ADMIN_USER_NAME, NscsServiceGetter.ENM_ADMIN_PASSWORD);
            openIDMOperator.createUser(oasisUserToCreate);
            openIDMOperator.assignUsersToRole("ADMINISTRATOR", oasisUserToCreate.getUsername());
            openIDMOperator.assignUsersToRole("OPERATOR", oasisUserToCreate.getUsername());
            openIDMOperator.assignUsersToRole("SECURITY_ADMIN", oasisUserToCreate.getUsername());

            httpTool = launcherOperator.login(NscsServiceGetter.OASISUSER01);

        } catch (final Exception e) {
            logger.error(e.getMessage());
            logger.debug("Setup Error",e);
        }
    }

    @TestStep(id = "tearDown")
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

    /** FOR BACKWARD COMPATIBILITY ONLY ... **/
    /*
	 * OLD O&M (= CORBA, SecLevel) generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldseclevel?
	 *   wanted=LEVEL_2&
	 *   minimum=LEVEL_2&
	 *   node=LTEPIPPO&
	 *   logical=Pippo
	 */
    @TestStep(id = "tORF12546_Func_1_Generate_SecLevel_Iscf")
	public void tORF12546_Func_1_Generate_SecLevel_Iscf() {

    	logger.info(" \n\n ############# Starting TestStep tORF12546_Func_1_Generate_SecLevel_Iscf ############# \n\n");
    	
    	logger.debug(httpTool);
        if (!launcherOperator.checkIfLoggedIn(httpTool)) {
            Assert.fail("ERROR - Not logged in");
        }

        /*
		public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final CPPSecurityLevel wantedSecLevel,
			final CPPSecurityLevel minimumSecLevel
        */
		final IscfResponse response = getIscfOperator().generate(
				"IllogicalName",
				"OASIS_ERBS01",
				CPPSecurityLevel.LEVEL_2,
				CPPSecurityLevel.LEVEL_2);
//		assertObjectNotNull(response);
		assertNotNull(response);
		logger.info("ISCF content");
		try {
			logger.info(new String(response.getIscfContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
					"Character encoding problem [ %s ]", e.getMessage()));
		}
	}
    
    /*
	 * OLD IPSec generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldipsec?
	 *   node=LTEPIETRO&
	 *   logical=Pietro&
	 *   subjectAltName=Pietro&
	 *   subjectAltNameType=IPV4&
	 *   ipsecAreas=OM
	 */
    @TestStep(id = "tORF12546_Func_1_Generate_IPSec_Iscf")
	public void tORF12546_Func_1_Generate_IPSec_Iscf() {

    	logger.info(" \n\n ############# Starting TestStep tORF12546_Func_1_Generate_IPSec_Iscf ############# \n\n");

        if (!launcherOperator.checkIfLoggedIn(httpTool)) {
            Assert.fail("ERROR - Not logged in");
        }

     // SubjectAltNameFormat subjectAltNameFormat = SubjectAltNameFormat.valueOf("IPV4");
        SubjectAltNameFormat subjectAltNameFormat = SubjectAltNameFormat.IPV4;
        IpsecArea ipsecArea = IpsecArea.OM;
        Set<IpsecArea> wantedIpSecAreas = new TreeSet<IpsecArea>();
        wantedIpSecAreas.add(ipsecArea);

        /*
        public IscfResponse generate(
	        final String logicalName,
	        final String nodeFdn,
			final String ipsecUserLabel,  // TODO - MISSING?
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas)
		*/
        final IscfResponse response = getIscfOperator().generate(
				"Pietro",
				"LTEPIETRO",
				"ipsecUserLabel",    // Added "final String ipsecUserLabel,"    // TODO - MISSING?
				"Pietro",
				subjectAltNameFormat,
				wantedIpSecAreas
				);
//		assertObjectNotNull(response);
		assertNotNull(response);
		logger.info("ISCF content");
		try {
			logger.info(new String(response.getIscfContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
					"Character encoding problem [ %s ]", e.getMessage()));
		}
	}
    
	/*
	 * OLD COMBO generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/oldcombined?
	 *   node=LTESTEFANO&
	 *   logical=LTESTEFANO&
	 *   wanted=LEVEL_2&
	 *   minimum=LEVEL_2&
	 *   subjectAltName=Stefano&
	 *   subjectAltNameType=IPV4&
	 *   ipsecAreas=OM
	 */
    @TestStep(id = "tORF12546_Func_1_Generate_COMBO_Iscf")
	public void tORF12546_Func_1_Generate_COMBO_Iscf() {

    	logger.info(" \n\n ############# Starting TestStep tORF12546_Func_1_Generate_COMBO_Iscf ############# \n\n");

    /* TODO MDTR - MODIFY ALL */
        if (!launcherOperator.checkIfLoggedIn(httpTool)) {
            Assert.fail("ERROR - Not logged in");
        }

		CPPSecurityLevel wantedSecLevelEnum = CPPSecurityLevel.getSecurityLevel("2");
		CPPSecurityLevel minimumSecLevelEnum = CPPSecurityLevel.getSecurityLevel("2");

     // String ipsecSubjectAltName = "Stefano";
		
     // SubjectAltNameFormat subjectAltNameFormat = SubjectAltNameFormat.valueOf("IPV4");
        SubjectAltNameFormat subjectAltNameFormat = SubjectAltNameFormat.IPV4;
        IpsecArea ipsecArea = IpsecArea.OM;
        Set<IpsecArea> wantedIpSecAreas = new TreeSet<IpsecArea>();
        wantedIpSecAreas.add(ipsecArea);

        /*
        public IscfResponse generate(
			final String logicalName,
			final String nodeFdn,
			final CPPSecurityLevel wantedSecLevel,
			final CPPSecurityLevel minimumSecLevel,
			final String ipsecUserLabel,	 // TODO - MISSING?
			final String ipsecSubjectAltName,
			final SubjectAltNameFormat subjectAltNameFormat,
			final Set<IpsecArea> wantedIpSecAreas)
		*/
        final IscfResponse response = getIscfOperator().generate(
				"LTESTEFANO",
				"LTESTEFANO",
				wantedSecLevelEnum,
				minimumSecLevelEnum,
				"ipsecUserLabel",    // Added "final String ipsecUserLabel,"    // TODO - MISSING?
				"Stefano",
				subjectAltNameFormat,
				wantedIpSecAreas
				);
//		assertObjectNotNull(response);
		assertNotNull(response);
		logger.info("ISCF content");
		try {
			logger.info(new String(response.getIscfContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
					"Character encoding problem [ %s ]", e.getMessage()));
		}
	}
    
    /** ... FOR BACKWARD COMPATIBILITY ONLY **/
    
	/*
	 * NEW O&M (= CORBA, SecLevel) generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/seclevel?
	 *   wanted=LEVEL_2&minimum=LEVEL_2&node=LTELUCA&logical=Luca&
	 *   mode=SCEP&version=E.1.49&nodeType=ERBS
	 */
    @TestStep(id = "generate_OAM_Iscf")
	public void generate_OAM_Iscf (
			@Input("logicalName") final String logicalName,
			@Input("nodeFdn") final String nodeFdn,
			@Input("wantedSecLevel") final String wantedSecLevel,
			@Input("minimumSecLevel") final String minimumSecLevel,
			@Input("wantedEnrollmentMode") final String wantedEnrolMode,
	        @Input("nodeType") final String nodeType,
	        @Input("mimVersion") final String mimVersion,
			/*@Input("nodeModelInformation") final NodeModelInformation nodeModelInformation, // TODO 1 - WRONG! REPRISTINATE!! */
	        @Output("expectedKeyLength") final char expectedKeyLength,
	        @Output("expectedEnrollmentMode") final char expectedEnrollmentMode,
	        @Output("expectedCertificateAuthorityDn") final String expectedCertificateAuthorityDn) {

    	logger.info(" \n\n ############# Starting TestStep Generate_OAM_Iscf ############# \n\n");
        
		logger.info("logicalName: " + logicalName);
		logger.info("nodeFdn: " + nodeFdn);
		logger.info("wantedSecLevel: " + wantedSecLevel);
		logger.info("minimumSecLevel: " + minimumSecLevel);
		logger.info("wantedEnrolMode: " + wantedEnrolMode);
		logger.info("nodeType: " + nodeType);
		logger.info("mimVersion: " + mimVersion);
		logger.info("expectedKeyLength: " + expectedKeyLength);
		logger.info("expectedEnrollmentMode: " + expectedEnrollmentMode);
		logger.info("expectedCertificateAuthorityDn: " + expectedCertificateAuthorityDn);
		
        if (!launcherOperator.checkIfLoggedIn(httpTool)) {
            Assert.fail("ERROR - Not logged in");
        }
      
		SecurityLevel wantedSecLevelEnum = SecurityLevel.getSecurityLevel(wantedSecLevel);
		SecurityLevel minimumSecLevelEnum = SecurityLevel.getSecurityLevel(minimumSecLevel);
		
		/* For snip2code
		String pippo = "SCEP";
		EnrollmentMode pippoMode = EnrollmentMode.valueOf(pippo);
		String pluto = "PLUTO";
		EnrollmentMode plutoMode = EnrollmentMode.valueOf(pluto);
		*/

		EnrollmentMode wantedEnrollmentMode = EnrollmentMode.valueOf(wantedEnrolMode);
		
		/* TODO 1 - No Use? */
		////////EnrollmentMode wantedEnrollmentModeEnum = EnrollmentDataDto.getEnrollmentMode();  // TODO UNDERSTAND!
		
		NodeModelInformation nodeModelInformation = new NodeModelInformation(mimVersion, ModelIdentifierType.MIM_VERSION, nodeType);
		
        logger.info("Generate_OAM_Iscf: Getting response...");
		final IscfResponse response = getIscfOperator().generate(
				logicalName,
				nodeFdn,
				wantedSecLevelEnum,
				minimumSecLevelEnum,
				wantedEnrollmentMode,
				nodeModelInformation
				/*mimVersion,
				nodeType*/);
        logger.info("Generate_OAM_Iscf: Got response...");
		
	 // assertObjectNotNull(response);
		assertNotNull(response);

		logger.info("ISCF content");
		String iscfContent = "";
		try {
			iscfContent = new String(response.getIscfContent(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
					"Character encoding problem [ %s ]", e.getMessage()));
		}
		logger.info(iscfContent);  // TODO remove, or save to a file?
		

//////// String iscfContent processing: Begin...

   //// a) General purpose (==> on 'iscfContent')...:
		logger.info("Generate_OAM_Iscf: ISCF file contents testing...");
		assertTrue("Iscf file doesn't begin with <?xml > tag", iscfContent.contains("<?xml version="));
		logger.info("Iscf file contents begins correctly with <?xml> tag");	
		assertTrue("Iscf file doesn't contains <secConfData> tag", iscfContent.contains("<secConfData "));
		logger.info("Iscf file contents contains <secConfData> tag");

   //// b) ...and scenario-specific (==> on 'extracted secEnrollmentData_subString'):
		int aOAM = getIndexOfTagInXmlContent(iscfContent, "<secEnrollmentData");
		int zOAM = getIndexOfTagInXmlContent(iscfContent, "secEnrollmentData>");
		String secEnrollmentData_subString = iscfContent.substring(aOAM, zOAM);

		try {

		// keyLength field test
			logger.info("keyLength field testing");
			tagValueIsAsExpected(
					secEnrollmentData_subString,
					"keyLength",
					expectedKeyLength);
		
		// enrollmentMode field test
			logger.info("enrollmentMode field testing");
			tagValueIsAsExpected(
					secEnrollmentData_subString,
					"enrollmentMode",
					expectedEnrollmentMode);
			
		// distinguishedName field test
			logger.info("distinguishedName field testing");
			String expectedDistinguishedName = "CN=" + nodeFdn;
			logger.info("expectedDistinguishedName: " + expectedDistinguishedName);
			tagValueIsAsExpectedString(
					secEnrollmentData_subString,
					"distinguishedName",
					expectedDistinguishedName);
			
			// certificateAuthorityDn field test (TODO re-enable in integration with real PKI)
/*			logger.info("certificateAuthorityDn field testing");
			tagValueIsAsExpectedString(
					secEnrollmentData_subString,
					"certificateAuthorityDn",
					expectedCertificateAuthorityDn);*/
			
        // <dataChallengePassword> test: Begin...
			logger.info("<dataChallengePassword> tag testing");
			assertTrue("<secEnrollmentData> section doesn't contains <dataChallengePassword> tag", secEnrollmentData_subString.contains("<dataChallengePassword>"));
			logger.info("<secEnrollmentData> section contains <dataChallengePassword> tag");
			int aOTP = getIndexOfTagInXmlContent(secEnrollmentData_subString, "<dataChallengePassword");
			int zOTP = getIndexOfTagInXmlContent(secEnrollmentData_subString, "</dataChallengePassword>");
			String secDataChallengePassword_subString = secEnrollmentData_subString.substring(aOTP, zOTP);
			assertTrue("<dataChallengePassword> section doesn't contains <encryptedContent> tag", secDataChallengePassword_subString.contains("<encryptedContent "));
			logger.info("<dataChallengePassword> section contains <encryptedContent> tag");
		 // <encryptedContent> test: Begin...
			int aENC = getIndexOfTagInXmlContent(secDataChallengePassword_subString, "<encryptedContent");
			int zENC = getIndexOfTagInXmlContent(secDataChallengePassword_subString, "encryptedContent>");
			String secEncryptedContent_subString = secDataChallengePassword_subString.substring(aENC, zENC);
			assertTrue("<dataChallengePassword> section doesn't contains <encryptedContent> tag", secDataChallengePassword_subString.contains("<encryptedContent "));
			logger.info("<dataChallengePassword> section contains <encryptedContent> tag");
			// Check inside <encryptedContent> section that both fields PBKDF2salt and PBKDF2iterationCount are present
			assertTrue("<encryptedContent> section doesn't contains first field (PBKDF2salt)", secEncryptedContent_subString.contains("PBKDF2salt"));
			logger.info("<encryptedContent> section contains first field (PBKDF2salt)");
			assertTrue("<encryptedContent> section doesn't contains second field (PBKDF2iterationCount)", secEncryptedContent_subString.contains("PBKDF2iterationCount"));
			logger.info("<encryptedContent> section contains second field (PBKDF2iterationCount)");
			// TODO Add a check for real encrypted content presence?
		 // <encryptedContent> test: ...End.
		// <dataChallengePassword> test:  ...End.
			
		} catch (final Exception e) {
		/// e.printStackTrace();
         // log.debug("Prerequisite test [FAILED]", e);
         // Assert.assertFalse(true, "Prerequisite test [FAILED]");
            logger.error(e.getMessage());
            logger.debug("Setup Error", e);
		}
//////// String iscfContent processing: ...End.
		
        logger.info("Generate_OAM_Iscf: Exiting...");
	}
    
	/*
	 * NEW IPSec generate
	 * https://enmapache.athtem.eei.ericsson.se/node-security/iscf/ipsec?
	 *   node=LTEMARCO&logical=Marco&subjectAltName=Marco&subjectAltNameType=IPV4&ipsecAreas=OM&
	 *   mode=SCEP&version=E.1.50&nodeType=ERBS
	 */
    /* TODO INSIDE */
    @TestStep(id = "generate_IPSec_Iscf")
	public void generate_IPSec_Iscf (
			@Input("logicalName") final String logicalName,
			@Input("nodeFdn") final String nodeFdn,
			@Input("ipsecUserLabel") final String ipsecUserLabel,	// Added "final String ipsecUserLabel,"    // TODO - MISSING?
			@Input("subjectAltName") final String subjectAltName,
			@Input("subjectAltNameType") final String subjectAltNameType,
			@Input("ipsecAreas") final String ipsecAreas, 			// TODO change type? --> RESTOp: Set<IpsecArea> wantedIpSecAreas
			@Input("wantedEnrollmentMode") final String wantedEnrolMode,
	        @Input("nodeType") final String nodeType,
	        @Input("mimVersion") final String mimVersion,
	        @Output("expectedKeyLength") final char expectedKeyLength,
	        @Output("expectedEnrollmentMode") final char expectedEnrollmentMode,
	        @Output("expectedCertificateAuthorityDn") final String expectedCertificateAuthorityDn) {

    	logger.info(" \n\n ############# Starting TestStep Generate_IPSec_Iscf ############# \n\n");

		logger.info("logicalName: " + logicalName);
		logger.info("nodeFdn: " + nodeFdn);
		logger.info("ipsecUserLabel: " + ipsecUserLabel);
		logger.info("subjectAltName: " + subjectAltName);
		logger.info("subjectAltNameType: " + subjectAltNameType);
		logger.info("ipsecAreas: " + ipsecAreas);
		logger.info("wantedEnrolMode: " + wantedEnrolMode);
		logger.info("nodeType: " + nodeType);
		logger.info("mimVersion: " + mimVersion);
		logger.info("expectedKeyLength: " + expectedKeyLength);
		logger.info("expectedEnrollmentMode: " + expectedEnrollmentMode);
		logger.info("expectedCertificateAuthorityDn: " + expectedCertificateAuthorityDn);
		
        if (!launcherOperator.checkIfLoggedIn(httpTool)) {
            Assert.fail("ERROR - Not logged in");
        }
      
     // Not matching format between SubjectAltNameFormat.valueOf() and EnrollmentMode.getEnrollmentMode(); N.I.
        SubjectAltNameFormat subjectAltNameTypeEnum = SubjectAltNameFormat.valueOf(subjectAltNameType);
		
		// http://www.java-tips.org/java-se-tips/java.util/how-to-create-a-set.html
		// Create a set
		Set<IpsecArea> wantedIpSecAreas = new HashSet();
	    // Add some elements to the set
	    wantedIpSecAreas.add(IpsecArea.valueOf(ipsecAreas));
	//	wantedIpSecAreas.add(IpsecArea.valueOf(ipsecAreas));	// Adding an element that already exists has no effect
	    // Retrieve number of elements in the set
	    int size = wantedIpSecAreas.size();
	    // 1!
		logger.info("wantedIpSecAreas.size(): " + size);
		
		EnrollmentMode wantedEnrollmentMode = EnrollmentMode.valueOf(wantedEnrolMode);
		
		NodeModelInformation nodeModelInformation = new NodeModelInformation(mimVersion, ModelIdentifierType.MIM_VERSION, nodeType);

		logger.info("Generate_IPSec_Iscf: Getting response...");
		final IscfResponse response = getIscfOperator().generate(
				logicalName,
				nodeFdn,
			    ipsecUserLabel,				// Added "final String ipsecUserLabel,"    // TODO - MISSING?
				subjectAltName,
				subjectAltNameTypeEnum,
				wantedIpSecAreas,			// Set<IpsecArea> wantedIpSecAreas,
				wantedEnrollmentMode,
				nodeModelInformation
			  /*mimVersion,
				nodeType*/);
		logger.info("Generate_IPSec_Iscf: Got response...");
		
	 // assertObjectNotNull(response);
		assertNotNull(response);

		logger.info("ISCF content");
		String iscfContent = "";
		try {
			iscfContent = new String(response.getIscfContent(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
					"Character encoding problem [ %s ]", e.getMessage()));
		}
		logger.info(iscfContent);  // TODO remove, or save to a file?
			
//////// String iscfContent processing: Begin...

  //// a) General purpose (==> on 'iscfContent')...:
		logger.info("Iscf file contents testing");
		assertTrue("Iscf file doesn't begin with <?xml > tag", iscfContent.contains("<?xml version="));
		logger.info("Iscf file contents begins correctly with <?xml> tag");	
		assertTrue("Iscf file doesn't contains <secConfData > tag", iscfContent.contains("<secConfData "));
		logger.info("Iscf file contents begins correctly with <?xml> tag");

   //// b) ...and scenario-specific (==> on 'extracted ipsecEnrollmentData_subString'):
		int a = getIndexOfTagInXmlContent(iscfContent, "<ipsecEnrollmentData");
		int z = getIndexOfTagInXmlContent(iscfContent, "ipsecEnrollmentData>");
		String ipsecEnrollmentData_subString = iscfContent.substring(a, z);
		
		try {
		// keyLength field test
			logger.info("keyLength field testing");
			tagValueIsAsExpected(
					ipsecEnrollmentData_subString,
					"keyLength",
					expectedKeyLength);
			
		// enrollmentMode field test
			logger.info("enrollmentMode field testing");
			tagValueIsAsExpected(
					ipsecEnrollmentData_subString,
					"enrollmentMode",
					expectedEnrollmentMode);
			
		// distinguishedName field test
			logger.info("distinguishedName field testing");
			String expectedDistinguishedName = "CN=" + nodeFdn;
			logger.info("expectedDistinguishedName: " + expectedDistinguishedName);
			tagValueIsAsExpectedString(
					ipsecEnrollmentData_subString,
					"distinguishedName",
					expectedDistinguishedName);
			
			// certificateAuthorityDn field test (TODO re-enable in integration with real PKI)
/*			logger.info("certificateAuthorityDn field testing");
			tagValueIsAsExpectedString(
					ipsecEnrollmentData_subString,
					"certificateAuthorityDn",
					expectedCertificateAuthorityDn);*/

        // <dataChallengePassword> test: Begin...
			logger.info("<dataChallengePassword> tag testing");
			assertTrue("<secEnrollmentData> section doesn't contains <dataChallengePassword> tag", ipsecEnrollmentData_subString.contains("<dataChallengePassword>"));
			logger.info("<secEnrollmentData> section contains <dataChallengePassword> tag");
			int aOTP = getIndexOfTagInXmlContent(ipsecEnrollmentData_subString, "<dataChallengePassword");
			int zOTP = getIndexOfTagInXmlContent(ipsecEnrollmentData_subString, "</dataChallengePassword>");
			String ipsecDataChallengePassword_subString = ipsecEnrollmentData_subString.substring(aOTP, zOTP);
			assertTrue("<dataChallengePassword> section doesn't contains <encryptedContent> tag", ipsecDataChallengePassword_subString.contains("<encryptedContent "));
			logger.info("<dataChallengePassword> section contains <encryptedContent> tag");
		 // <encryptedContent> test: Begin...
			int aENC = getIndexOfTagInXmlContent(ipsecDataChallengePassword_subString, "<encryptedContent");
			int zENC = getIndexOfTagInXmlContent(ipsecDataChallengePassword_subString, "encryptedContent>");
			String ipsecEncryptedContent_subString = ipsecDataChallengePassword_subString.substring(aENC, zENC);
			assertTrue("<dataChallengePassword> section doesn't contains <encryptedContent> tag", ipsecDataChallengePassword_subString.contains("<encryptedContent "));
			logger.info("<dataChallengePassword> section contains <encryptedContent> tag");
			// Check inside <encryptedContent> section that both fields PBKDF2salt and PBKDF2iterationCount are present
			assertTrue("<encryptedContent> section doesn't contains first field (PBKDF2salt)", ipsecEncryptedContent_subString.contains("PBKDF2salt"));
			logger.info("<encryptedContent> section contains first field (PBKDF2salt)");
			assertTrue("<encryptedContent> section doesn't contains second field (PBKDF2iterationCount)", ipsecEncryptedContent_subString.contains("PBKDF2iterationCount"));
			logger.info("<encryptedContent> section contains second field (PBKDF2iterationCount)");
			// TODO Add a check for real encrypted content presence?
		 // <encryptedContent> test: ...End.
		// <dataChallengePassword> test:  ...End.
			
		} catch (final Exception e) {
		/// e.printStackTrace();
         // log.debug("Prerequisite test [FAILED]", e);
         // Assert.assertFalse(true, "Prerequisite test [FAILED]");
            logger.error(e.getMessage());
            logger.debug("Setup Error", e);
		}
//////// String iscfContent processing: ...End.
		
        logger.info("Generate_IPSec_Iscf: Exiting...");
	}

	/*	NEW COMBO generate
	 *  https://enmapache.athtem.eei.ericsson.se/node-security/iscf/combined?
	 *    node=LTECINZIA&logical=Cinzia&
	 *    wanted=LEVEL_2&minimum=LEVEL_2&
	 *    subjectAltName=Cinzia&subjectAltNameType=IPV4&ipsecAreas=OM&
	 *    mode=SCEP&version=5.1.100&nodeType=ERBS
	 */

    @TestStep(id = "generate_COMBO_Iscf")
	public void generate_COMBO_Iscf (
			@Input("logicalName") final String logicalName,
			@Input("nodeFdn") final String nodeFdn,
			@Input("ipsecUserLabel") final String ipsecUserLabel,	// Added "final String ipsecUserLabel,"    // TODO - MISSING?
			@Input("subjectAltName") final String subjectAltName,
			@Input("subjectAltNameType") final String subjectAltNameType,
			@Input("ipsecAreas") final String ipsecAreas, 			// TODO change type? --> RESTOp: Set<IpsecArea> wantedIpSecAreas
			@Input("wantedSecLevel") final String wantedSecLevel,
			@Input("minimumSecLevel") final String minimumSecLevel,
			@Input("wantedEnrollmentMode") final String wantedEnrolMode,
	        @Input("nodeType") final String nodeType,
	        @Input("mimVersion") final String mimVersion,
	        @Output("expectedKeyLengthOAM") final char expectedKeyLengthOAM,
	        @Output("expectedEnrollmentModeOAM") final char expectedEnrollmentModeOAM,
	        @Output("expectedCertificateAuthorityDnOAM") final String expectedCertificateAuthorityDnOAM,
	        @Output("expectedKeyLengthIPSec") final char expectedKeyLengthIPSec,
	        @Output("expectedEnrollmentModeIPSec") final char expectedEnrollmentModeIPSec,
	        @Output("expectedCertificateAuthorityDnIPSec") final String expectedCertificateAuthorityDnIPSec
	        ) {

    	logger.info(" \n\n ############# Starting TestStep Generate_COMBO_Iscf ############# \n\n");

		logger.info("logicalName: " + logicalName);
		logger.info("nodeFdn: " + nodeFdn);
		logger.info("ipsecUserLabel: " + ipsecUserLabel);
		logger.info("subjectAltName: " + subjectAltName);
		logger.info("subjectAltNameType: " + subjectAltNameType);
		logger.info("ipsecAreas: " + ipsecAreas);
		logger.info("wantedSecLevel: " + wantedSecLevel);
		logger.info("minimumSecLevel: " + minimumSecLevel);
		logger.info("wantedEnrolMode: " + wantedEnrolMode);
		logger.info("nodeType: " + nodeType);
		logger.info("mimVersion: " + mimVersion);
		logger.info("expectedKeyLengthOAM: " + expectedKeyLengthOAM);
		logger.info("expectedEnrollmentModeOAM: " + expectedEnrollmentModeOAM);
		logger.info("expectedCertificateAuthorityDnOAM: " + expectedCertificateAuthorityDnOAM);
		logger.info("expectedKeyLengthIPSec: " + expectedKeyLengthIPSec);
		logger.info("expectedEnrollmentModeIPSec: " + expectedEnrollmentModeIPSec);
		logger.info("expectedCertificateAuthorityDnIPSec: " + expectedCertificateAuthorityDnIPSec);

        if (!launcherOperator.checkIfLoggedIn(httpTool)) {
            Assert.fail("ERROR - Not logged in");
        }
      
		SecurityLevel wantedSecLevelEnum = SecurityLevel.getSecurityLevel(wantedSecLevel);
		SecurityLevel minimumSecLevelEnum = SecurityLevel.getSecurityLevel(minimumSecLevel);
		
	     // Not matching format between SubjectAltNameFormat.valueOf() and EnrollmentMode.getEnrollmentMode(); N.I.
        SubjectAltNameFormat subjectAltNameTypeEnum = SubjectAltNameFormat.valueOf(subjectAltNameType);
		
		// http://www.java-tips.org/java-se-tips/java.util/how-to-create-a-set.html
		// Create a set
		Set<IpsecArea> wantedIpSecAreas = new HashSet();
	    // Add some elements to the set
	    wantedIpSecAreas.add(IpsecArea.valueOf(ipsecAreas));
	//	wantedIpSecAreas.add(IpsecArea.valueOf(ipsecAreas));	// Adding an element that already exists has no effect
	    // Retrieve number of elements in the set
	    int size = wantedIpSecAreas.size();
	    // 1!
		logger.info("wantedIpSecAreas.size(): " + size);
		
		EnrollmentMode wantedEnrollmentMode = EnrollmentMode.valueOf(wantedEnrolMode);
		
		NodeModelInformation nodeModelInformation = new NodeModelInformation(mimVersion, ModelIdentifierType.MIM_VERSION, nodeType);

		logger.info("Generate_COMBO_Iscf: Getting response...");
		final IscfResponse response = getIscfOperator().generate(
				logicalName,
				nodeFdn,
				wantedSecLevelEnum,
				minimumSecLevelEnum,
			    ipsecUserLabel,				// Added "final String ipsecUserLabel,"    // TODO - MISSING?
				subjectAltName,
				subjectAltNameTypeEnum,
				wantedIpSecAreas,			// Set<IpsecArea> wantedIpSecAreas,
				wantedEnrollmentMode,
				nodeModelInformation
			 /* mimVersion,
				nodeType */);
		logger.info("Generate_COMBO_Iscf: Got response...");
		
	 // assertObjectNotNull(response);
		assertNotNull(response);

		logger.info("ISCF content");
		String iscfContent = "";
		try {
			iscfContent = new String(response.getIscfContent(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info(String.format(
					"Character encoding problem [ %s ]", e.getMessage()));
		}
		logger.info(iscfContent);  // TODO remove, or save to a file?
		
////////String iscfContent processing: Begin...

 //// a) General purpose (==> on 'iscfContent')...:		
		logger.info("Iscf file contents testing");
		assertTrue("Iscf file doesn't begin with <?xml > tag", iscfContent.contains("<?xml version="));
		logger.info("Iscf file contents begins correctly with <?xml> tag");	
		assertTrue("Iscf file doesn't contains <secConfData > tag", iscfContent.contains("<secConfData "));
		logger.info("Iscf file contents begins correctly with <?xml> tag");

//// b) ...and scenario-specific (==> on the two 'extracted_EnrollmentData_subString',
////                                         one for <secEnrollmentData> and one for <ipsecEnrollmentData>):
//// b1) for OAM (<secEnrollmentData> section)...:
		int aOAM = getIndexOfTagInXmlContent(iscfContent, "<secEnrollmentData");
		int zOAM = getIndexOfTagInXmlContent(iscfContent, "secEnrollmentData>");
		String secEnrollmentData_subString = iscfContent.substring(aOAM, zOAM);

		try {
		// keyLength field test
			logger.info("keyLength field testing");
			tagValueIsAsExpected(
					secEnrollmentData_subString,
					"keyLength",
					expectedKeyLengthOAM);

		// enrollmentMode field test
			logger.info("enrollmentMode field testing");
			tagValueIsAsExpected(
					secEnrollmentData_subString,
					"enrollmentMode",
					expectedEnrollmentModeOAM);

		// distinguishedName field test
			logger.info("distinguishedName field testing");
			String expectedDistinguishedName = "CN=" + nodeFdn;
			logger.info("expectedDistinguishedName: " + expectedDistinguishedName);
			tagValueIsAsExpectedString(
					secEnrollmentData_subString,
					"distinguishedName",
					expectedDistinguishedName);
			
		// certificateAuthorityDn field test (TODO re-enable in integration with real PKI)
/*			logger.info("certificateAuthorityDn field testing");
			tagValueIsAsExpectedString(
					secEnrollmentData_subString,
					"certificateAuthorityDn",
					expectedCertificateAuthorityDnOAM);*/
			
       // <dataChallengePassword> test: Begin...
			logger.info("<dataChallengePassword> tag testing");
			assertTrue("<secEnrollmentData> section doesn't contains <dataChallengePassword> tag", secEnrollmentData_subString.contains("<dataChallengePassword>"));
			logger.info("<secEnrollmentData> section contains <dataChallengePassword> tag");
			int aOTP = getIndexOfTagInXmlContent(secEnrollmentData_subString, "<dataChallengePassword");
			int zOTP = getIndexOfTagInXmlContent(secEnrollmentData_subString, "</dataChallengePassword>");
			String secDataChallengePassword_subString = secEnrollmentData_subString.substring(aOTP, zOTP);
			assertTrue("<dataChallengePassword> section doesn't contains <encryptedContent> tag", secDataChallengePassword_subString.contains("<encryptedContent "));
			logger.info("<dataChallengePassword> section contains <encryptedContent> tag");
		 // <encryptedContent> test: Begin...
			int aENC = getIndexOfTagInXmlContent(secDataChallengePassword_subString, "<encryptedContent");
			int zENC = getIndexOfTagInXmlContent(secDataChallengePassword_subString, "encryptedContent>");
			String secEncryptedContent_subString = secDataChallengePassword_subString.substring(aENC, zENC);
			assertTrue("<dataChallengePassword> section doesn't contains <encryptedContent> tag", secDataChallengePassword_subString.contains("<encryptedContent "));
			logger.info("<dataChallengePassword> section contains <encryptedContent> tag");
			// Check inside <encryptedContent> section that both fields PBKDF2salt and PBKDF2iterationCount are present
			assertTrue("<encryptedContent> section doesn't contains first field (PBKDF2salt)", secEncryptedContent_subString.contains("PBKDF2salt"));
			logger.info("<encryptedContent> section contains first field (PBKDF2salt)");
			assertTrue("<encryptedContent> section doesn't contains second field (PBKDF2iterationCount)", secEncryptedContent_subString.contains("PBKDF2iterationCount"));
			logger.info("<encryptedContent> section contains second field (PBKDF2iterationCount)");
			// TODO Add a check for real encrypted content presence?
		 // <encryptedContent> test: ...End.
		// <dataChallengePassword> test:  ...End.
		
		} catch (final Exception e) {
		/// e.printStackTrace();
         // log.debug("Prerequisite test [FAILED]", e);
         // Assert.assertFalse(true, "Prerequisite test [FAILED]");
            logger.error(e.getMessage());
            logger.debug("Setup Error", e);
		}

//// b2) ... and for IPSec (<ipsecEnrollmentData> section)...:
		int aIPSec = getIndexOfTagInXmlContent(iscfContent, "<ipsecEnrollmentData");
		int zIPSec = getIndexOfTagInXmlContent(iscfContent, "ipsecEnrollmentData>");
		String ipsecEnrollmentData_subString = iscfContent.substring(aIPSec, zIPSec);
		
		try {
		// keyLength field test
			logger.info("keyLength field testing");
			tagValueIsAsExpected(
					ipsecEnrollmentData_subString,
					"keyLength",
					expectedKeyLengthIPSec);

		// enrollmentMode field test
			logger.info("enrollmentMode field testing");
			tagValueIsAsExpected(
					ipsecEnrollmentData_subString,
					"enrollmentMode",
					expectedEnrollmentModeIPSec);

		// distinguishedName field test
			logger.info("distinguishedName field testing");
			String expectedDistinguishedName = "CN=" + nodeFdn;
			logger.info("expectedDistinguishedName: " + expectedDistinguishedName);
			tagValueIsAsExpectedString(
					ipsecEnrollmentData_subString,
					"distinguishedName",
					expectedDistinguishedName);
			
			// certificateAuthorityDn field test (TODO re-enable in integration with real PKI)
/*			logger.info("certificateAuthorityDn field testing");
			tagValueIsAsExpectedString(
					ipsecEnrollmentData_subString,
					"certificateAuthorityDn",
					expectedCertificateAuthorityDnIPSec);*/

        // <dataChallengePassword> test: Begin...
			logger.info("<dataChallengePassword> tag testing");
			assertTrue("<secEnrollmentData> section doesn't contains <dataChallengePassword> tag", ipsecEnrollmentData_subString.contains("<dataChallengePassword>"));
			logger.info("<secEnrollmentData> section contains <dataChallengePassword> tag");
			int aOTP = getIndexOfTagInXmlContent(ipsecEnrollmentData_subString, "<dataChallengePassword");
			int zOTP = getIndexOfTagInXmlContent(ipsecEnrollmentData_subString, "</dataChallengePassword>");
			String ipsecDataChallengePassword_subString = ipsecEnrollmentData_subString.substring(aOTP, zOTP);
			assertTrue("<dataChallengePassword> section doesn't contains <encryptedContent> tag", ipsecDataChallengePassword_subString.contains("<encryptedContent "));
			logger.info("<dataChallengePassword> section contains <encryptedContent> tag");
		 // <encryptedContent> test: Begin...
			int aENC = getIndexOfTagInXmlContent(ipsecDataChallengePassword_subString, "<encryptedContent");
			int zENC = getIndexOfTagInXmlContent(ipsecDataChallengePassword_subString, "encryptedContent>");
			String ipsecEncryptedContent_subString = ipsecDataChallengePassword_subString.substring(aENC, zENC);
			assertTrue("<dataChallengePassword> section doesn't contains <encryptedContent> tag", ipsecDataChallengePassword_subString.contains("<encryptedContent "));
			logger.info("<dataChallengePassword> section contains <encryptedContent> tag");
			// Check inside <encryptedContent> section that both fields PBKDF2salt and PBKDF2iterationCount are present
			assertTrue("<encryptedContent> section doesn't contains first field (PBKDF2salt)", ipsecEncryptedContent_subString.contains("PBKDF2salt"));
			logger.info("<encryptedContent> section contains first field (PBKDF2salt)");
			assertTrue("<encryptedContent> section doesn't contains second field (PBKDF2iterationCount)", ipsecEncryptedContent_subString.contains("PBKDF2iterationCount"));
			logger.info("<encryptedContent> section contains second field (PBKDF2iterationCount)");
			// TODO Add a check for real encrypted content presence?
		 // <encryptedContent> test: ...End.
		// <dataChallengePassword> test:  ...End.
			
		} catch (final Exception e) {
		/// e.printStackTrace();
         // log.debug("Prerequisite test [FAILED]", e);
         // Assert.assertFalse(true, "Prerequisite test [FAILED]");
            logger.error(e.getMessage());
            logger.debug("Setup Error", e);
		}
//////// String iscfContent processing: ...End.

        logger.info("Generate_COMBO_Iscf: Exiting...");

	}

    /**
	 * 
	 * @param xmlContent
	 * @param tagName
	 */
	// TODO OPT indexOfTag -> tagIndex
	private int getIndexOfTagInXmlContent(
			String xmlContent,
			String tagName)
	{
		// E.g.:
		//  <secEnrollmentData
		//      or
		//  secEnrollmentData>
		//  |   // TODO OPT: 'Tag' -> 'Property'
	    //  |      |                         //.indexOf(<secEnrollmentData)
		int indexOfTagInXmlContent = xmlContent.indexOf(tagName);
		logger.info("indexOfTag " + tagName + "in xmlContent: " + indexOfTagInXmlContent);
		//  E.g.: TODO OPT
		
		return indexOfTagInXmlContent;
	}

	/**
	 * 
	 * @param xmlContent
	 * @param tagName
	 * @param expectedKeyLength
	 */
	private void tagValueIsAsExpected(
			String xmlContent,
			String tagName,
			final char expectedTagValue)
	{
		//  keyLength="1"
		//  |   // 'Tag' -> 'Property' TODO
	    //  |      |                         //.indexOf("keyLength=")
		int indexOfTagInXmlContent = xmlContent.indexOf(tagName + "=");
		logger.info("indexOfTag " + tagName + "in xmlContent: " + indexOfTagInXmlContent);
		//  E.g.: 5550
		
	 // int tagLength = "keyLength=".length();  // 9
		int tagLength = tagName.length();       // 9 for keyLength
		logger.info("tagLength: " + tagLength + "(Should be 9 for keyLength, etc.)");
		
		//  keyLength="1"
		//             |
		int indexOfTagValueInXmlContent = indexOfTagInXmlContent + tagLength + 2;
		logger.info("indexOfTagValueInXmlContent: " + indexOfTagValueInXmlContent);
		//  E.g.: 5561 = 5550 + 9 (the length) + 2 (the two =" characters)
		
		char actualTagValue = '\0';
		try {
			actualTagValue = xmlContent.charAt(indexOfTagValueInXmlContent);
		} catch (IndexOutOfBoundsException e) {
			logger.error(e.getMessage());
		    logger.debug("IndexOutOfBoundsException", e);
		}
		logger.info("Found    tagValue: " + actualTagValue);
		logger.info("Expected tagValue: " + expectedTagValue);
		                                        // | TODO substitute 
		assertEquals(actualTagValue, expectedTagValue);  // char == '0' (char)
		logger.info("tagValue found: " + actualTagValue + " is as expected: " + expectedTagValue);
	}

	/**
	 * 
	 * @param xmlContent
	 * @param tagName
	 * @param expectedKeyLength
	 */
	private void tagValueIsAsExpectedString(
			String xmlContent,
			String tagName,
			final String expectedTagValue)
	{
	                                         //.indexOf("keyLength=")
		int indexOfTagInXmlContent = xmlContent.indexOf(tagName + "=");
		logger.info("indexOfTag " + tagName + "in xmlContent: " + indexOfTagInXmlContent);
		//  E.g.: 5550
		
	 // int tagLength = "keyLength=".length();           // 14
		int tagLength = tagName.length();                // 14 for certificateAuthorityDn
		logger.info("tagLength: " + tagLength + "(Should be 14 for certificateAuthorityDn, etc.)");
		
		int indexOfTagValueBeginInXmlContent = indexOfTagInXmlContent + tagLength + 2;
		logger.info("indexOfTagValueBeginInXmlContent: " + indexOfTagValueBeginInXmlContent);
		
		int expectedTagValueLength = expectedTagValue.length();                    // 11 for CN=ENMOAMCA
		logger.info("expectedTagValueLength: " + expectedTagValueLength + "(Should be 11 for CN=ENMOAMCA, etc.)");
		
		int indexOfTagValueEndInXmlContent = indexOfTagValueBeginInXmlContent + expectedTagValueLength;
		logger.info("indexOfTagValueEndInXmlContent: " + indexOfTagValueEndInXmlContent);
		
		String actualTagValue = "";
		try {
			actualTagValue = xmlContent.substring(indexOfTagValueBeginInXmlContent, indexOfTagValueEndInXmlContent);
		} catch (IndexOutOfBoundsException e) {
			logger.error(e.getMessage());
		    logger.debug("IndexOutOfBoundsException", e);
		}
		logger.info("Found    tagValue: " + actualTagValue);
		logger.info("Expected tagValue: " + expectedTagValue);
		                                        // | TODO substitute 
		assertEquals(actualTagValue, expectedTagValue);  // char == '0' (char)
		logger.info("tagValue found: " + actualTagValue + " is as expected: " + expectedTagValue);
	}

	/* Used by all public method "Generate_*" (TestSteps) above */
	private IscfServiceOperator getIscfOperator() {
		final IscfServiceOperator op = iscfServiceProvider
				.provide(IscfServiceOperator.class);
        op.setHttpTool(httpTool);
		return op;
	}

}
