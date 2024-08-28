package com.ericsson.nms.security.nscs.taf.test.operators;

import com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter;

import java.util.Set;

public interface SyncNodeOperator {

    boolean synchroniseNode(String nodeName);

    public boolean checkNodeIsSynchronisedOrTimeout(String nodeName, int timeout, int numberOfTimesToCheck);

    public boolean checkNodeIsSynchronised(String nodeName);

    Set<String> getAllSynchronisedNodes();

    public void orderSynchroniseNodeList(String nodeList);

    public void orderSynchroniseNode(String nodeName);

}


