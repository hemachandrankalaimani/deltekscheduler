/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.entities;

import com.deltek.deltekscheduler.entities.interfaces.IHasEmail;
import com.deltek.deltekscheduler.entities.interfaces.IMeeting;
import com.deltek.deltekscheduler.entities.interfaces.IPerson;
import java.util.Collection;

/**
 *
 * @author hemachandrankalaimani
 */
public class Person extends Entity implements IPerson, IHasEmail {

    protected String mFirstName;
    protected String mLastName;
    protected String mEmail;

    protected Collection<IMeeting> mMeetings;
    protected boolean mIsMeetingsPopulated;

    public Person() {
        super();
        this.mFirstName = null;
        this.mLastName = null;
        this.mEmail = null;
        this.mIsMeetingsPopulated = false;
        this.mMeetings = null;
    }

    @Override
    public String getFirstName() {
        return this.mFirstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.mLastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    @Override
    public String getEmail() {
        return this.mEmail;
    }

    @Override
    public void setEmail(String email) {
        this.mEmail = email;
    }

    @Override
    public Collection<IMeeting> getMeetings() {
        return this.mMeetings;
    }

    @Override
    public void setMeetings(Collection<IMeeting> meetings) {
        this.mMeetings = meetings;
    }

    @Override
    public boolean getIsMeetingsPopulated() {
        return this.mIsMeetingsPopulated;
    }

    @Override
    public void setIsMeetingsPopuated(boolean isMeetingsPopulated) {
        this.mIsMeetingsPopulated = isMeetingsPopulated;
    }

}
