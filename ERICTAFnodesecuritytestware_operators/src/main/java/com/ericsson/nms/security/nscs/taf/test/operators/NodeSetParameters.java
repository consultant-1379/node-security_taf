/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.operators;

/**
 *
 * @author enmadmin
 */
public interface NodeSetParameters {
    
    public String getNeTypeForCurrentNode(final String nodeName);    
    
    public String getPlatformTypeForCurrentNode(final String nodeName);    

    public boolean checkSgsnForCurrentNode(String nodeName);    
    
}
