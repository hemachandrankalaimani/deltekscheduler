/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.application;

import com.deltek.deltekscheduler.data.InMemoryDataProvider;

/**
 *
 * @author hemachandrankalaimani
 */
public class ApplicationDataSource {

    protected static InMemoryDataProvider mApplicationData;

    public static InMemoryDataProvider getApplicationDataSource() {
        createApplicationData();
        return mApplicationData;
    }

    public static void createApplicationData() {
        if (mApplicationData == null) {
            mApplicationData = new InMemoryDataProvider();
        }
    }

    /*
    public static  void setApplicationdata(ArrayList<IPerson> persons, ArrayList<IMeeting> meetings, ArrayList<IPersonMeeting> available){   
        createApplicationData();
        mApplicationData.setPersons(persons);
        mApplicationData.setPersonMeetings(available);
        mApplicationData.setMeetings(meetings);
    }*/
}
