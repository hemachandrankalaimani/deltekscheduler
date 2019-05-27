/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.data;

import com.deltek.deltekscheduler.entities.interfaces.*;
import java.util.ArrayList;

/**
 *
 * @author hemachandrankalaimani
 */
public class InMemoryDataProvider {

    private ArrayList<IPerson> mAvailablePersons;
    private ArrayList<IMeeting> mAvailableMeetings;
    private ArrayList<IPersonMeeting> mAvalilablePersonMeetings;

    public InMemoryDataProvider() {
        mAvailablePersons = new ArrayList<>();
        mAvailableMeetings = new ArrayList<>();
        mAvalilablePersonMeetings = new ArrayList<>();
    }

    /**
     * Clears all the data
     */
    public void clearAll() {
        this.mAvailableMeetings.clear();
        this.mAvailablePersons.clear();
        this.mAvalilablePersonMeetings.clear();
    }

    public void setPersons(ArrayList<IPerson> persons) {
        this.mAvailablePersons = (persons);
    }

    public void setMeetings(ArrayList<IMeeting> meetings) {
        this.mAvailableMeetings = meetings;
    }

    public void setPersonMeetings(ArrayList<IPersonMeeting> personMeetings) {
        this.mAvalilablePersonMeetings = personMeetings;
    }

    public ArrayList<IPerson> getPersons() {
        return this.mAvailablePersons;
    }

    public ArrayList<IMeeting> getMeetings() {
        return this.mAvailableMeetings;
    }

    public ArrayList<IPersonMeeting> getPersonMeetings() {
        return this.mAvalilablePersonMeetings;
    }
}
