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
public enum NodeParameters {

    ERBS("ERBS","CPP"),
    SGSN("SGSN-MME","SGSN_MME");
    
    private String neType;
    private String platformType; 

    private NodeParameters(String neType, String platformType) {
        this.neType = neType;
        this.platformType = platformType;
    }

    public String getNeType() {
        return neType;
    }
    
    public String getPlatformType() {
        return platformType;
    }

    
}    

