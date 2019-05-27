/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.application;

/**
 *
 * @author hemachandrankalaimani
 */
public class ApplicationConfig extends org.glassfish.jersey.server.ResourceConfig {

    public ApplicationConfig() {
        ApplicationDataSource.createApplicationData();
    }

    /*
    public ApplicationConfig(ArrayList<IPerson> persons, ArrayList<IMeeting> meetings, ArrayList<IPersonMeeting> available) {
        this();
        ApplicationDataSource.setApplicationdata(persons, meetings, available);
    }*/
}
