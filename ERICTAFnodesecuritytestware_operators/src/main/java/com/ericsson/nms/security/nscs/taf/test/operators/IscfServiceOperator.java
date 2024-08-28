/*
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 */

package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.cifwk.taf.tools.http.HttpTool;
import com.ericsson.nms.security.nscs.api.IscfService;

/**
 * Interface for exposing ISCF business logic to TAF tests
 *
 * @author ealemca
 */
public interface IscfServiceOperator extends IscfService {

    void setHttpTool(HttpTool tool);

}
