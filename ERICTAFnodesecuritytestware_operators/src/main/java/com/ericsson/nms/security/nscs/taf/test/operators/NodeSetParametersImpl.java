/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.nms.security.nscs.taf.test.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author enmadmin
 */
public class NodeSetParametersImpl implements NodeSetParameters {

    Logger logger = LoggerFactory.getLogger(this.getClass());
   
    @Override
    public String getNeTypeForCurrentNode(final String nodeName) {
        
        String neType;
                
        if(nodeName.contains(NodeParameters.SGSN.name())){
            neType = NodeParameters.SGSN.getNeType();
        } else {
            neType = NodeParameters.ERBS.getNeType();        
        }

        return neType;
    }

    @Override
    public String getPlatformTypeForCurrentNode(String nodeName) {

        String platformType;
                
        if(nodeName.contains(NodeParameters.SGSN.name())){
            platformType = NodeParameters.SGSN.getPlatformType();
        } else {
            platformType = NodeParameters.ERBS.getPlatformType();        
        }

        return platformType;
    }
    
    @Override
    public boolean checkSgsnForCurrentNode(String nodeName){
        return (nodeName.contains(NodeParameters.SGSN.name()))?true:false;
    }
    
}
