package com.ericsson.nms.security.nscs.taf.test.getters;

import com.ericsson.cifwk.taf.data.User;
import com.ericsson.cifwk.taf.data.UserType;

import javax.inject.Singleton;

@Singleton
public class NscsServiceGetter {

    public static final int NUMBER_OF_NODES_NEEDED_FOR_TESTS = 12;
    public static final int NUMBER_OF_NODES_NEEDED_FOR_CREDENTIALS_TESTS = 12;
    public static final int NUMBER_OF_NODES_TO_USE_FOR_CREDENTIAL_PERFORMANCE_TEST=5;
    
    

    //    LTE28ERBS00001 : Only MeContext MO exists
//    LTE28ERBS00002 : Only Network ElementMO Exists, No MeContext exists
//    LTE28ERBS00003 : Both MeContext and NetworkElement exist
//    LTE28ERBS00004 : MeContext, NetworkElement and SecurityFunction , CmFunction, CppConnectivityInfo exist    , No Credentials
//    LTE28ERBS00005 : MeContext, NetworkElement and SecurityFunction , CmFunction, CppConnectivityInfo and NetworkElementSecurity MO exist with wrong password, and Syncronised
//TODO CHANGE PASSWORD IN TEST CASE RATHER THAN SETUP
//    LTE28ERBS00006 : MeContext, NetworkElement and SecurityFunction ,CmFunction, CppConnectivityInfo and  NetworkElementSecurity and Synchronised
//    LTE28ERBS00007 : MeContext, NetworkElement and SecurityFunction ,CmFunction, CppConnectivityInfo and  NetworkElementSecurity and Synchronised
//    LTE28ERBS00008 : MeContext, NetworkElement and SecurityFunction ,CmFunction, CppConnectivityInfo and  NetworkElementSecurity and Synchronised
//    LTE28ERBS00009 : MeContext, NetworkElement and SecurityFunction ,CmFunction, CppConnectivityInfo and  NetworkElementSecurity and Synchronised
//    LTE28ERBS00010 : MeContext, NetworkElement and SecurityFunction ,CmFunction, CppConnectivityInfo and  NetworkElementSecurity and Synchronised
//
    public static final int INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_DOES_NOT_EXIST =0;
    public static final int INDEXOF_NODE_ME_CONTEXT_DOES_NOT_EXIST_AND_NETWORK_ELEMENT_EXISTS =1;

    public static final int INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_EXISTS_NO_SECURITY_FUNCTIONN_EXISTS =2;
    public static final int INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_AND_SYSTEM_FUNCTIONS_EXIST =3;

    public static final int INDEXOF_NODE_ME_CONTEXT_EXISTS_AND_NETWORK_ELEMENT_EXISTS_NOT_SYNCHRONISED =2;
    public static final int INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_DOES_NOT_EXIST =3;
    //TODO CHANGE PASSWORD IN TEST CASE RATHER THAN SETUP
    public static final int INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST_INCORRECT_NE_PASSWORD =4;
    public static final int INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST =5;
    public static final int INDEXOF_NODE_USED_FOR_NOTIFICATION_ALARM_TEST =7;
    public static final int START_INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST =5;
    public static final int END_INDEXOF_NODE_SYNCHRONISED_NETWORK_ELEMENT_SECURITY_EXIST =8;

    public static final int INDEXOF_NODE_NETWORK_ELEMENT_SECURITY_DOES_NOT_EXIST = 3;
    public static final int INDEXOF_NETWORK_ELEMENT_SECURITY = 5;
    
    // Index of Keygen create/update
    public static final int END_INDEXOF_SGSN_NODE = 7;
        
    public static final int INDEXOF_NODE_NO_SECURITY_FUNCTION = 1;
    public static final int INDEXOF_NODE_NO_NETWORK_ELEMENT_SECURITY = 2;
        
    public static final int INDEXOF_NODE_KEYPAIR_NOT_FOUND = 3;
    
    public static final int INDEXOF_NODE_TOCREATE_KEYS = 4;
    public static final int INDEXOF_NODE_TOUPDATE_KEYS = INDEXOF_NODE_TOCREATE_KEYS;
    public static final int INDEXOF_NODE_KEYPAIR_ALREADY_GENERATED = INDEXOF_NODE_TOCREATE_KEYS;
        
    public static final int INDEXOF_NODE_TOCREATE_KEYS_UNASSOCIATED = 5;
    public static final int INDEXOF_NODE_TOCREATE_KEYS_FILE = 6;
    public static final int INDEXOF_NODE_TOUPDATE_KEYS_FILE = INDEXOF_NODE_TOCREATE_KEYS_FILE;
    
    public static final int DELAY_KEY_GENERATION = 30000;
    
    // Index if Certificate Issue
    public static final int END_INDEXOF_CPP_COM_FOR_CERTIFICATE = 3;
    public static final int INDEXOF_NODE_NOT_CERTIFIABLE = 3;
    public static final int INDEXOF_NODE_NOT_SYNCHRONIZED = 2;

    // Index of Trust Distribute
    public static final int END_INDEXOF_CPP_FOR_TRUST_DISTRIBUTE = 2;
    public static final int START_INDEXOF_COM_FOR_TRUST_DISTRIBUTE = END_INDEXOF_CPP_FOR_TRUST_DISTRIBUTE+1;
    public static final int INDEXOF_NODE_NOT_CERTIFIABLE_TRUST_DISTR = 3;
    public static final int INDEXOF_NODE_NOT_SYNCHRONIZED_TRUST_DISTR = 2;
    

    public static final String SECURITY_LEVEL_INITIATED_EXPECTED_MESSAGE_IN_LOG = "Security level change initiated, check the system logs for results";

    public static final String SC1_SECSERV_LOG="/var/ericsson/log/jboss/SecServ_su_0_jee_cfg/server.log";
    public static final String SC2_SECSERV_LOG="/var/ericsson/log/jboss/SecServ_su_1_jee_cfg/server.log";
    public static final String SC1_CMSERV_LOG="/var/ericsson/log/jboss/CMServ_su_0_jee_cfg/server.log";
    public static final String SC2_CMSERV_LOG="/var/ericsson/log/jboss/CMServ_su_1_jee_cfg/server.log";
    public static final String SC1_MSCM_LOG="/var/ericsson/log/jboss/MSCM_su_0_jee_cfg/server.log";
    public static final String SC2_MSCM_LOG="/var/ericsson/log/jboss/MSCM_su_1_jee_cfg/server.log";
    
    public static final String SVC_1_SECSERV_LOG = "/ericsson/3pp/jboss/standalone/log/server.log";
    public static final String SVC_2_SECSERV_LOG = SVC_1_SECSERV_LOG;

    /** FOR BACKWARD COMPATIBILITY ONLY ... **/
    public static final String ISCF_SECLEVEL_REST_RESOURCE_OLD = "/node-security/iscf/oldseclevel";
    public static final String ISCF_IPSEC_REST_RESOURCE_OLD = "/node-security/iscf/oldipsec";
    public static final String ISCF_COMBINED_REST_RESOURCE_OLD = "/node-security/iscf/oldcombined";
    /** ... FOR BACKWARD COMPATIBILITY ONLY **/
    
    public static final String ISCF_SECLEVEL_REST_RESOURCE = "/node-security/iscf/seclevel";
    public static final String ISCF_IPSEC_REST_RESOURCE = "/node-security/iscf/ipsec";
    public static final String ISCF_COMBINED_REST_RESOURCE = "/node-security/iscf/combined";

    //Newly added

    public static final String LEVEL_1="LEVEL_1";
    public static final String LEVEL_2="LEVEL_2";
    public static final String LEVEL_3="LEVEL_3";
    public static final String LEVEL_ALL = "*";

    public static final String ONE_INSTANCE = "1 instance(s)";

    public static final String SECADM_SL_GET_COMMAND_WITH_SECURITY_LEVEL = "secadm securitylevel get --level %s --nodelist %s";
    public static final String SECADM_SL_GET_ALL_COMMAND_WITH_SECURITY_LEVEL = "secadm securitylevel get --level %s --all";

    public static final String SECADM_SL_GET_COMMAND = "secadm securitylevel get --nodelist %s";
    public static final String SECADM_SL_GET_COMMAND_SHORT = "secadm sl get -n %s";

    public static final String SECADM_SL_GET_STAR_COMMAND = "secadm sl get *";
    public static final String SECADM_SL_GET_ALL_COMMAND = "secadm sl get --all";
    public static final String SECADM_SL_GET_ALL_COMMAND_SHORT = "secadm sl get -a";


    public static final String SECADM_SL_SET_COMMAND = "secadm securitylevel set --level %s --nodelist %s ";
    public static final String SECADM_SL_SET_COMMAND_SHORT = "secadm sl set --level %s -n %s";


    public static final String SECURITY_ADMIN = "SECURITY_ADMIN";
    public static final String OPERATOR = "OPERATOR";
    public static final String FIELD_TECHNICIAN = "FIELD_TECHNICIAN";

    public static final String ACCESS_VIOLATION_ERROR_MESSAGE = "Error 9999 : Internal Error com.ericsson.oss.itpf.sdk.security.accesscontrol.SecurityViolationException: access control decision: denied to invoke: execute on resource: secadm";


    public static final String CMEDIT_CREATE_MECONTEXT_COMMAND = "cmedit create MeContext=%s MeContextId=%s,neType=%s, platformType=%s -ns=OSS_TOP -version=3.0.0";                                                                
    public static final String CMEDIT_CREATE_NETWORK_ELEMENT_COMMAND = "cmedit create NetworkElement=%s networkElementId=%s, neType=%s, platformType=%s, ossPrefix=\"MeContext=%s\" -ns=OSS_NE_DEF -version=2.0.0";
    public static final String CMEDIT_CREATE_SECURITY_FUNCTION_COMMAND = "cmedit create NetworkElement=%s,SecurityFunction=1 securityFunctionId=1 -namespace=OSS_NE_SEC_DEF -version=1.0.0";
    public static final String CMEDIT_CREATE_CM_FUNCTION_COMMAND = "cmedit create NetworkElement=%s,CmFunction=1 CmFunctionId=1 -ns=OSS_NE_CM_DEF -version=1.0.0";
    public static final String CMEDIT_CREATE_CPP_CONNECTIVITY_INFO_COMMAND = "cmedit create NetworkElement=%s,CppConnectivityInformation=1 CppConnectivityInformationId=1, ipAddress=\"%s\", port=80 -ns=CPP_MED -version=1.0.0";

    public static final String CMEDIT_CREATE_COM_CONNECTIVITY_INFO_COMMAND = "cmedit create NetworkElement=%s,ComConnectivityInformation=1 ComConnectivityInformationId=1, ipAddress=\"%s\", port=80,strictHostKeyChecking=\"yes\",isNetconfSubsystemSupported=\"true\" -ns=COM_MED -version=1.0.0";


    public static final String CMEDIT_CREATE_MECONTEXT_SGSN_COMMAND = "cmedit create MeContext=%s MeContextId=%s,neType=SGSN-MME, platformType=SGSN_MME -ns=OSS_TOP -version=3.0.0";
    public static final String CMEDIT_CREATE_NETWORK_ELEMENT_SGSN_COMMAND = "cmedit create NetworkElement=%s networkElementId=%s, neType=SGSN-MME, platformType=SGSN_MME, ossPrefix=\"MeContext=%s\" -ns=OSS_NE_DEF -version=2.0.0";
    public static final String CMEDIT_CREATE_COM_CONNECTIVITY_INFO = "cmedit create NetworkElement=%s,ComConnectivityInformation=1 ComConnectivityInformationId=\"1\",port=\"22\",ipAddress=\"%s\",strictHostKeyChecking=\"yes\",isNetconfSubsystemSupported=\"true\" -namespace=COM_MED -version=1.0.0";
    public static final String SECADM_CREATE_CREDENTIAL_FOR_SGSN = "secadm credentials create --secureuserpassword " + NscsServiceGetter.NETSIM_SECURE_PASSWORD + " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME + " --nodelist %s";
//    public static final String CMEDIT_CREATE_NETWORK_ELEMENT_SECURITY_SGSN_COMMAND = "cmedit create NetworkElement=%s,SecurityFunction=1,NetworkElementSecurity=1 NetworkElementSecurityId=1, secureUserName=sun, secureUserPassword=sup -namespace=OSS_NE_SEC_DEF -version=1.0.0";


    
    public static final String FMEDIT_ALARM_SUPERVISION_COMMAND = "fmedit set NetworkElement=%s,FmAlarmSupervision=1 alarmSupervisionState=%s";

    public static final String FMEDIT_ALARM_LIST_COMMAND = "fmedit get * OpenAlarm.(objectOfReference==\"MeContext=%s\") OpenAlarm.*";
    public static final String FMEDIT_ALARM_ACKNOWLEDGE_COMMAND = "fmedit set * OpenAlarm.(objectOfReference==\"MeContext=%s\") alarmState=\"ack\"";
    public static final String FMEDIT_ALARM_CLEAR_COMMAND = "fmedit set * OpenAlarm.(objectOfReference==\"MeContext=%s\") alarmState=\"clear\"";


    public static final String CMEDIT_GET_MECONTEXT_COMMAND = "cmedit get MeContext=%s";
    public static final String CMEDIT_GET_NETWORKELEMENT_COMMAND = "cmedit get NetworkElement=%s";
    public static final String CMEDIT_GET_NETWORK_ELEMENT_SECURITY_COMMAND = "cmedit get NetworkElement=%s,SecurityFunction=1,NetworkElementSecurity=1";

    public static final String CMEDIT_DELETE_NETWORK_ELEMENT_SECURITY_COMMAND = "cmedit delete NetworkElement=%s,SecurityFunction=1,NetworkElementSecurity=1";

    public static final String CMEDIT_SYNCH_COMMAND = "cmedit action NetworkElement=%s,CmFunction=1 sync";
    public static final String CMEDIT_SYNCH_COMMAND_NEW = "cmedit set NetworkElement=%s,CmNodeHeartbeatSupervision=1 active=true";
    public static final String CMEDIT_UNSYNCH_COMMAND = "cmedit set NetworkElement=%s,CmNodeHeartbeatSupervision=1 active=false";


    public static final String CMEDIT_GET_FM_ALARM_SUPERVISION_COMMAND = "cmedit get NetworkElement=%s,FmAlarmSupervision=1.";
    public static final String CMEDIT_ATTRIBUTE_VALUE_WHEN_ALARM_SUPERVISION_ON = "alarmSupervisionState : true";


    public static final String CMEDIT_GET_ALL_NETWORK_ELEMENT_COMMAND = "cmedit get * NetworkElement";
    public static final String CMEDIT_GET_ALL_MECONTEXT_COMMAND = "cmedit get * MeContext";
    public static final String CMEDIT_GET_ALL_SECURITY_MOS_COMMAND = "cmedit get * Security";

    public static final String CMEDIT_GET_ALL_SYNCHED_NODES_COMMAND = "cmedit get * CmFunction.syncStatus==SYNCHRONIZED";



    public static final String CMEDIT_GET_SYNCH_ATTRIBUTE_COMMAND_NEW = "cmedit get NetworkElement=%s,CmFunction=1";

    public static final String CMEDIT_ATTRIBUTE_VALUE_WHEN_NODE_SYNCHED = "syncStatus : SYNCHRONIZED";


    public static final String NETWORK_ELEMENT_SECURITY_NAMESPACE = "OSS_NE_SEC_DEF";
    public static final String NETWORK_ELEMENT_SECURITY_VERSION = "3.0.0";

    public static final String CMEDIT_CREATE_NETWORK_ELEMENT_SECURITY_COMMAND="cmedit create  NetworkElement=%s,SecurityFunction=1,NetworkElementSecurity=1 NetworkElementSecurityId=1,rootUserName=\"%s\",rootUserPassword=\"%s\",secureUserName=\"%s\"," +
            "secureUserPassword=\"%s\",normalUserName=\"%s\",normalUserPassword=\"%s\",targetGroups=[%s] -namespace=" + NscsServiceGetter.NETWORK_ELEMENT_SECURITY_NAMESPACE + " -version="+NscsServiceGetter.NETWORK_ELEMENT_SECURITY_VERSION;

    public static final String CMEDIT_SET_NETWORK_ELEMENT_SECURITY_COMMAND="cmedit set  NetworkElement=%s,SecurityFunction=1,NetworkElementSecurity=1 rootUserName=\"%s\",rootUserPassword=\"%s\",secureUserName=\"%s\","
            + "secureUserPassword=\"%s\",normalUserName=\"%s\",normalUserPassword=\"%s\"";

    public static final String CMEDIT_DELETE_MECONTEXT_COMMAND ="cmedit delete MeContext=%s -ALL";


    public static final String CMEDIT_DELETE_MECONTEXT_WITH_FILTER_COMMAND ="cmedit delete %s MeContext -ALL";

    public static final String CMEDIT_DELETE_NETWORKELEMENT_WITH_FILTER_COMMAND ="cmedit delete * NetworkElement.networkElementId==%s -ALL";

    public static final String CMEDIT_DELETE_SECURITYFUNCTION_COMMAND ="cmedit delete NetworkElement=%s,SecurityFunction=1";

    // CHECK SECURITYFUNCTION
    public static final String CMEDIT_GET_SECURITYFUNCTION = "cmedit get NetworkElement=%s,SecurityFunction=1";
    
    // COMMANDS TO DELETE NODE
    public static final String CMEDIT_SET_HEARTBEAT_FALSE = "cmedit set NetworkElement=%s,CmNodeHeartbeatSupervision=1 active=false";
    public static final String CMEDIT_SET_INVENTORY_FALSE = "cmedit set NetworkElement=%s,InventorySupervision=1 active=false";
    public static final String CMEDIT_SET_FMALARM_FALSE = "cmedit set NetworkElement=%s,FmAlarmSupervision=1 active=false";
    public static final String CMEDIT_ACTION_CMFUNCTION = "cmedit action NetworkElement=%s,CmFunction=1 deleteNrmDataFromEnm";
    public static final String CMEDIT_DELETE_NETWORKELEMENT_COMMAND ="cmedit delete NetworkElement=%s -ALL";


    public static final String CMEDIT_GET_ALL_NODES_AT_SECURITY_LEVEL= "cmedit get * Security.operationalSecurityLevel==%s";

    public static final String CMEDIT_GET_SECURITY_LEVEL="cmedit get %s Security.operationalSecurityLevel";

    public static final String CMEDIT_GET_ATTRIBUTE="cmedit get %s %s.%s";

    public static final String CMEDIT_SECURITY_LEVEL_ATTRIBUTE="operationalSecurityLevel";

    public static final String SECADM_SECURITY_LEVEL_GET_COMMAND = "secadm securitylevel get --nodelist %s";
    public static final String SECADM_SECURITY_LEVEL_GET_COMMAND_SHORT = "secadm sl get -n %s";



    //COMMAND SUCCESS RESPONSES

    public static final String SECADM_CREDENTIALS_CREATE_SUCCESS = "All credentials were created successfully";
    public static final String SECADM_CREDENTIALS_UPDATE_SUCCESS = "All credentials updated successfully";
    
    public static final String SECADM_KEYGEN_CREATE_SUCCESS = "keygen create command executed";
    public static final String SECADM_KEYGEN_UPDATE_SUCCESS = "keygen update command executed";
    
    public static final String SECADM_CERTIFICATE_ISSUE_CREATE_SUCCESS = "Certificate issue command executed";
    
    public static final String SECADM_TRUST_DISTRIBUTE_SUCCESS = "Trust distribute command executed";

    
    //COMMAND ERROR RESPONSES FOR ALL COMMANDS


    public static final String ERROR_10001_COMMAND_SYNTAX_ERROR = "Error 10001 : Command syntax error";
    public static final String SUGGESTED_SOLUTION_PLEASE_CHECK_ONLINE_HELP_FOR_CORRECT_SYNTAX = "Suggested Solution : Please check Online Help for correct syntax.";

    public static final String ERROR_10002_THE_CONTENTS_OF_THE_FILE_PROVIDED_ARE_NOT_IN_THE_CORRECT_FORMAT ="Error 10002 : The contents of the file provided are not in the correct format";
    public static final String SUGGESTED_SOLUTION_PLEASE_SEE_THE_ONLINE_HELP_FOR_THE_CORRECT_FORMAT_OF_THE_CONTENTS_OF_THE_FILE ="Suggested Solution : Please see the Online Help for the correct format of the contents of the file.";

    public static final String ERROR_10003_THE_LIST_OF_NODES_SPECIFIED_CONTAINS_DUPLICATES ="Error 10003 : The list of nodes specified contains duplicates";
    public static final String SUGGESTED_SOLUTION_PLEASE_REMOVE_THE_DUPLICATES_AND_RE_RUN_COMMAND ="Suggested Solution : Please remove the duplicates and re-run command.";

    public static final String ERROR_10004_THE_MO_SPECIFIED_DOES_NOT_EXIST ="Error 10004 : The MO specified does not exist - ";
    public static final String SUGGESTED_SOLUTION_PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM ="Suggested Solution : Please specify a valid MO that exists in the system.";

     public static final String ERROR_10005_THE_NODE_SPECIFIED_IS_NOT_SYNCHRONIZED ="Error 10005 : The node specified is not synchronized";
    public static final String SUGGESTED_SOLUTION_PLEASE_ENSURE_THE_NODE_SPECIFIED_IS_SYNCHRONIZED ="Suggested Solution : Please ensure the node specified is synchronized.";

    public static final String ERROR_10006_THE_NODE_SPECIFIED_REQUIRES_THE_NODE_CREDENTIALS_TO_BE_DEFINED="Error 10006 : The node specified requires the node credentials to be defined";
    public static final String SUGGESTED_SOLUTION_PLEASE_CREATE_CREDENTIALS_FOR_THE_NODE ="Suggested Solution : Please create credentials for the node (secadm credentials create...) and re-run command.";

    public static final String ERROR_10007_THE_NETWORK_ELEMENT_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED ="Error 10007 : The NetworkElement MO does not exist for the associated MeContext MO";
    public static final String SUGGESTED_SOLUTION_PLEASE_CREATE_THE_NETWORK_ELEMENT_MO_AND_ANY_OTHER_REQUIRED_MOS_FOR_THE_NODE ="Suggested Solution : Please create the NetworkElement MO and any other required MOs for the associated MeContext MO.";

    public static final String ERROR_10008_THE_SECURITY_FUNCTION_MO_DOES_NOT_EXIST_FOR_THE_NODE_SPECIFIED = "Error 10008 : The SecurityFunction MO does not exist for the node specified";
    public static final String SUGGESTED_SOLUTION_PLEASE_CREATE_THE_SECURITY_FUNCTION_MO = "Suggested Solution : Please create the SecurityFunction MO and any other required MOs for the node.";

    public static final String ERROR_10009_THE_NODE_SPECIFIED_ALREADY_HAS_CREDENTIALS_DEFINED = "Error 10009 : The node specified already has credentials defined";
    public static final String SUGGESTED_SOLUTION_PLEASE_SPECIFY_NODES_WITHOUT_EXISTING_CREDENTIALS_DEFINED = "Suggested Solution : Please specify nodes without existing credentials defined.";

    public static final String ERROR_10010_THERE_ARE_ISSUES_WITH_MORE_THAN_ONE_OF_THE_NODES_SPECIFIED= "Error 10010 : There are issues with more than one of the nodes specified";
    public static final String SUGGESTED_SOLUTION_PLEASE_CHECK_SUGGESTED_SOLUTION_FOR_EACH_NODE = "Suggested Solution : Please check suggested solution for each node and re-run command when these issues are addressed. Alternatively, omit these nodes from the list and re-run the command";

    public static final String ERROR_10011_THE_NODE_SPECIFIED_IS_ALREADY_AT_THE_REQUESTED_SECURITY_LEVEL ="Error 10011 : The node specified is already at the requested security level";
    public static final String SECADM_SL_SET_NODE_ALREADY_AT_REQUESTED_LEVEL_SUGGESTED_SOLUTION =
            "Suggested Solution : Please check the requested security level for the node specified.";

    public static final String ERROR_10015_THE_NODE_SPECIFIED_IS_ALREADY_INVOLVED_IN_A_SECURITY_CONFIGURATION_CHANGE ="Error 10015 : The node specified is already involved in a security configuration change";
    public static final String SUGGESTED_SOLUTION_PLEASE_WAIT_UNTIL_THE_CONFIGURATION_CHANGE_IS_COMPLETED_AND_RERUN_COMMAND =
            "Suggested Solution : Please wait until the configuration change is completed and rerun command ";

    public static final String ERROR_10016_THE_ME_CONTEXT_MO_DOES_NOT_EXIST_FOR_THE_ASSOCIATED_NETWORK_ELEMENT_MO ="Error 10016 : The MeContext MO does not exist for the associated NetworkElement MO";
    public static final String SUGGESTED_SOLUTION_PLEASE_CREATE_THE_ME_CONTEXT_CORRESPONDING_TO_THE_SPECIFIED_MO =
            "Suggested Solution : Please create the MeContext corresponding to the specified MO.";

    public static final String NO_NODES_WITH_SECURITY_MO_FOUND = "No nodes with Security MO found";
    public static final String NO_NODES_FOUND_AT_REQUESTED_SECURITY_LEVEL = "No nodes found at requested Security Level";

    // Keygen error
//    public static final String ERROR_10006_NETWORK_ELEMENT_SECURITY_NOT_FOUND = "Error 10004 : The MO specified does not exist";
    public static final String ERROR_10090_UNSUPPORTED_NODE_TYPE = "Error 10090 : Unsupported Node Type";
    public static final String ERROR_10091_INVALID_ALGORITHM_TYPE_SIZE = "Error 10091 : Invalid argument value : "
    		+ "Invalid argument for parameter algorithm-type-size. "
    		+ "Accepted arguments are [DSA_1024, RSA_1024, RSA_2048, RSA_4096, RSA_8192, RSA_16384]";
    public static final String ERROR_10093_KEYPAIR_ALREADY_GENERATED = "Error 10093 : Key pair already generated";
    public static final String ERROR_10092_KEYPAIR_MISSING = "Error 10092 : Key pair not found";
    
    
    
    public static final String NETWORK_ELEMENT_NOTFOUND_ERROR = "10007";
    public static final String NODE_NOT_CERTIFIABLE_ERROR = "10021";    
    public static final String NODE_NOT_SYNCHRONIZED_ERROR = "10005";
    
    public static final String INVALID_INPUT_XML_FILE_CONTENT = "Error 10014 : Input xml validation with Schema failed. Please provide the valid XML input.";
    public static final String INVALID_INPUT_XML_FILE_CONTENT_SOLUTION = "Suggested Solution : Please provide the valid XML. For details please check Online Help.";
    
    public static final String CERTIFICATE_ISSUE_ERROR = "Certificate issue command not executed as some provided nodes are invalid. Details are given below";    
    public static final String HEADER_TABLE = "Node Name,Error Code,Error Detail";

    public static final String THE_MO_SPECIFIED_DOES_NOT_EXIST = "Error 10004 : The MO specified does not exist";
    public static final String PLEASE_SPECIFY_A_VALID_MO_THAT_EXISTS_IN_THE_SYSTEM = "Suggested Solution : Please specify a valid MO that exists in the system.";
    
    public static final String NETWORK_ELEMENT_NOT_FOUND_FOR_THIS_MECONTEXT = "The NetworkElement MO does not exist for the associated MeContext MO";
    
    public static final String NODE_NOT_SYNCHRONIZED = "The node specified is not synchronized";
//    public static final String PLEASE_ENSURE_THE_NODE_SPECIFIED_IS_SYNCHRONIZED = "Please ensure the node specified is synchronized.";
    
    public static final String NODE_NOT_CERTIFIABLE = "Error 10021 : Cannot generate a certificate for this kind of node";
    public static final String SPECIFY_A_CERTFIABLE_NODE = "Suggested Solution : Please specify a certifiable node.";
    
    public static final String CERTIFICATE_INVALID_ARGUMENT = "Error 10091 : Invalid argument value :  Invalid argument for parameter certtype. Accepted arguments are [IPSEC, OAM]";
    public static final String CERTIFICATE_INVALID_ARGUMENT_SOLUTION = "Suggested Solution : Please check Online Help for correct syntax.";
    
    public static final String FILE_CONTENT_NOT_VALID = "Error 10013 : File content is not valid.";
    public static final String FILE_CONTENT_NOT_VALID_SOLUTION = "Suggested Solution : Please provide the XML file with UTF-8 character encoding. For details please check Online Help.";
    
    public static final String MISSING_MANDATORY_PARAMS_FOR_IPSEC = "Error 10022 : Some of the input node attributes are missing or invalid. Please check input file.";
    public static final String MISSING_MANDATORY_PARAMS_FOR_IPSEC_SOLUTION = "Suggested Solution : Please check Online Help for correct syntax.";
    
//    TODO how to test if comando ok (nodi ok) ma wf not started
//    CERTIFICATE_ISSUE_WF_FAILED = "Certificate issue workflow is failed
       
    	
    //TODO when --continue is implemented
    //public static final String SECADM_SL_SET_MULTIPLE_ERROR_SUGGESTED_SOLUTION="Please check suggested solution for each node and re-run command when these issues are addressed. Alternatively omit these nodes from the command or use the --continue option to ignore these nodes";

    //COMMAND ERROR RESPONSES FOR SL SET COMMANDS


    public static final String SECADM_SL_SET_MAX_NODES_EXCEEDED ="Error 10017 : Number of nodes specified exceeds the maximum";
    public static final String SECADM_SL_SET_MAX_NODES_EXCEEDED_SUGGESTED_SOLUTION =
            "The current maximum number of nodes supported is 160. Please specify a valid number of nodes";

    public static final String NETSIM_ROOT_USER_NAME ="netsim";
    public static final String NETSIM_ROOT_PASSWORD ="netsim";

    public static final String NETSIM_NORMAL_USER_NAME ="netsim";
    public static final String NETSIM_NORMAL_PASSWORD ="netsim";

    public static final String NETSIM_SECURE_USER_NAME ="netsim";
    public static final String NETSIM_SECURE_PASSWORD ="netsim";

    public static final String OASIS_USER_NAME="oasistaf01";
    public static final String OASIS_PASSWORD="OasisTafUser01";
    public static final String OASIS_FIRST_NAME="firstname";
    public static final String OASIS_SECOND_NAME="secondname";
    public static final String OASIS_EMAIL="oasistaf01@ericsson.ie";
    
    public static final String CERTISSUE_USER_NAME="certissueUser";
//    public static final String CERTISSUE_PASSWORD="certissuePsw";
    
    public static final String ALGORITHM_TYPE_SIZE_RSA1024 = "RSA_1024";
    public static final String ALGORITHM_TYPE_SIZE_RSA2048 = "RSA_2048";
    public static final String ALGORITHM_TYPE_SIZE_INVALID = "DSA_8192";

    public static final String ENM_ADMIN_USER_NAME="administrator";
    public static final String ENM_ADMIN_PASSWORD="TestPassw0rd";

    public static final User OASISUSER01 = new User(OASIS_USER_NAME, OASIS_PASSWORD, UserType.ADMIN);


    public static final String CREATE_CREDENTIALS_WITH_NODELIST="secadm credentials create"+
            " --rootusername "+ NscsServiceGetter.NETSIM_ROOT_USER_NAME +
            " --rootuserpassword "+ NscsServiceGetter.NETSIM_ROOT_PASSWORD +
            " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
            " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
            " --normaluserpassword "+NscsServiceGetter.NETSIM_NORMAL_PASSWORD +
            " --normalusername "+NscsServiceGetter.NETSIM_NORMAL_USER_NAME +
            " --nodelist %s";

    public static final String CREATE_CREDENTIALS_FOR_SGSN_WITH_NODELIST="secadm credentials create"+
            " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
            " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
            " --nodelist %s";

    public static final String CREATE_CREDENTIALS_WITH_FILE="secadm credentials create"+
            " --rootusername "+ NscsServiceGetter.NETSIM_ROOT_USER_NAME +
            " --rootuserpassword "+ NscsServiceGetter.NETSIM_ROOT_PASSWORD +
            " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
            " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
            " --normaluserpassword "+NscsServiceGetter.NETSIM_NORMAL_PASSWORD +
            " --normalusername "+NscsServiceGetter.NETSIM_NORMAL_USER_NAME +
            " --nodefile %s";

    public static final String CREATE_CREDENTIALS_FOR_SGSN_WITH_FILE="secadm credentials create"+
            " --secureuserpassword "+ NscsServiceGetter.NETSIM_SECURE_PASSWORD +
            " --secureusername "+NscsServiceGetter.NETSIM_SECURE_USER_NAME +
            " --nodefile %s";

    public static final String UPDATE_CREDENTIALS_WITH_NODELIST=CREATE_CREDENTIALS_WITH_NODELIST.replace("create","update");
    public static final String UPDATE_CREDENTIALS_FOR_SGSN_WITH_NODELIST=CREATE_CREDENTIALS_FOR_SGSN_WITH_NODELIST.replace("create","update");
    public static final String UPDATE_CREDENTIALS_WITH_FILE=CREATE_CREDENTIALS_WITH_FILE.replace("create","update");
    public static final String UPDATE_CREDENTIALS_FOR_SGSN_WITH_FILE=CREATE_CREDENTIALS_FOR_SGSN_WITH_FILE.replace("create","update");

    public static final String SECADM_GET_SL_WITH_NODELIST="secadm securitylevel get "+
                        " --nodelist %s";

    public static final String SECADM_GET_SL_WITH_FILE="secadm securitylevel get "+
            " --nodefile %s";

    public static final String SECADM_SET_SL_WITH_NODELIST="secadm securitylevel set --level %s"+
            " --nodelist %s";

    public static final String SECADM_SET_SL_WITH_FILE="secadm securitylevel set --level %s "+
            " --nodefile %s";


    public static final int NODE_NAME_COLUMN_INDEX=0;
    public static final int SECURITY_LEVEL_COLUMN_INDEX=1;
    
    // Certificate Issue
    public static final String CERT_TYPE_IPSEC = "IPSEC";
    public static final String CERT_TYPE_OAM = "OAM";
    public static final String CERT_TYPE_IPSEC_INVALID = "IPSECS";
    public static final String CERT_TYPE_OAM_INVALID = "OAMM";
    public static final String CERT_NODE_FILE_XML = "secadmcertificate.xml";
    public static final String CERT_NODE_FILE_XML_INVALID = "secadmcertificate_invalid.xml";
    public static final String CERT_NODE_FILE_XML_EMPTY = "secadmcertificate_empty.xml";
    public static final String CERT_NODE_FILE_XML_MISSING_PARAM = "secadmcertificate_missingparam.xml";
    public static final String CERT_NODE_FILE_XML_CONTENT = ""
    		+ "<Nodes>"
    		+ "<Node>"
    		+ "<NodeFdn>%s</NodeFdn>"
    		+ "<subjectAltName>subj</subjectAltName>"
    		+ "<subjectAltNameType>1.1.1.1</subjectAltNameType>"
    		+ "</Node>"
    		+ "</Nodes>";
    public static final String CERT_NODE_FILE_XML_CONTENT_INVALID = ""
    		+ "<Nodes>"
    		+ "<Node>"
    		+ "<NodeFdn>%s</NodeFdn>"
    		+ "<subj>subj</subj>"
    		+ "<subjectAltNameType>1.1.1.1</subjectAltNameType>"
    		+ "</Node>"
    		+ "</Nodes>";

    public static final String CMEDIT_GET_CERTIFICATE_ISSUE = "cmedit get MeContext=%s";
    
    // Trust Distribute
    public static final String CERT_TYPE_INVALID = "OEM";
    public static final String TRUST_NODE_FILE = "nodeFileTrust.txt";
    //public static final String TRUST_NODE_LIST = "Test_node_001, Test_node_002, Test_node_003, Test_node_004";
    
    // IPsec
    public static final String EMPTY_STRING = "";
    public static final String COMMA_SEPARATOR = ",";
    public static final String SEMICOLON_SEPARATOR = ";";

    public static final String IPSEC_DATA_FILE_PATH = "/data/IPsec/";
    public static final String IPSEC_OM_ENABLE_COFIG_ONE = "OMConfigurationOne.xml";
    public static final String IPSEC_OM_ENABLE_COFIG_TWO = "OMConfigurationTwo.xml";
    public static final String IPSEC_OM_ENABLE_MULTIPLE_COFIG = "OMEnableDisable.xml";
    public static final String IPSEC_OM_DISABLE_COFIG = "OMDisable.xml";

    public static final String IPSEC_OM_COMMAND = "secadm ipsec --continue --xmlfile file:";
    public static final String IPSEC_STATUS_COMMAND = "secadm ipsec --status --nodefile ";

    public static final String CMEDIT_GET = "cmedit get ";
    public static final String CMEDIT_MECONTEXT = "MeContext=%s";
    public static final String CMEDIT_IPSEC_STATUS_COMMAND = CMEDIT_GET + CMEDIT_MECONTEXT +", ManagedElement=1, IpSystem=1, IpSec=1";
    public static final String CMEDIT_IPSEC_QUERY_COMMAND = CMEDIT_GET + CMEDIT_MECONTEXT +", %s";
    public static final String CMEDIT_IPSEC_OM_IPHOSTLINK_COMMAND = CMEDIT_GET + "* IpHostLink";
    public static final String CMEDIT_IPSEC_TRAFFIC_IPACCESSHOST_COMMAND = CMEDIT_GET + "* IpAccessHostEt";
    
    public static final String FILE_ABC_TXT = "file:abc.txt";
    public static final String IPSEC_TRAFFIC = "TRAFFIC";
    public static final String IPSEC_OM = "OM";
    public static final String IPSEC_TRAFFIC_ACTIVE = "1";
    public static final String IPSEC_OM_ACTIVE = "2";
    public static final String IPSEC_ACT = "ACTIVATED";
    public static final String IPSEC_DEACT = "DEACTIVATED";
    public static final String IPSEC_UNKNOWN = "UNKNOWN";
    public static final String IPSEC_ACT_CONF1 = "ACTIVATED(Configuration 1)";
    public static final String IPSEC_ACT_CONF2 = "ACTIVATED(Configuration 2)";

    public static final String IPSEC_COMMAND_SUCCESS_MSG = "Command response is NOT syntax error";
    public static final String IPSEC_SUCCESS_MESSAGE = "IPSec activation/deactivation change initiated";
    public static final String IPSEC_INVALID_NODE_MESSAGE = "Error 10004 : The MO specified does not exist";
    public static final String IPSEC_COMMAND_SUCCESS_EXECUTED = "Command Executed Successfully";
    
    public static final int DELAY_START_NETSIM = 3000;

}
