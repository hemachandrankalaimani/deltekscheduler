/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.entities;

import com.deltek.deltekscheduler.entities.interfaces.IMeeting;
import com.deltek.deltekscheduler.entities.interfaces.IPerson;
import java.util.Collection;

/**
 *
 * @author hemachandrankalaimani
 */
public class Meeting extends Entity implements IMeeting {

    private long mStartTimeInMilliSeconds;
    private long mEndTimeInMilliSeconds;

    protected Collection<IPerson> mPersons;
    protected boolean mIsPersonsPopulated;

    public Meeting() {
        super();
        this.mIsPersonsPopulated = false;
        this.mPersons = null;
    }

    @Override
    public long getStartTimeInMilliSeconds() {
        return this.mStartTimeInMilliSeconds;
    }

    @Override
    public void setStartTimeInMilliSeconds(long startTimeInMilliSeconds) {
        this.mStartTimeInMilliSeconds = startTimeInMilliSeconds;
    }

    @Override
    public long getEndTimeInMilliSeconds() {
        return this.mEndTimeInMilliSeconds;
    }

    @Override
    public void setEndTimeInMilliSeconds(long endTimeInMilliSeconds) {
        this.mEndTimeInMilliSeconds = endTimeInMilliSeconds;
    }

    @Override
    public Collection<IPerson> getPersons() {
        return this.mPersons;
    }

    @Override
    public void setPersons(Collection<IPerson> persons) {
        this.mPersons = persons;
    }

    @Override
    public boolean getIsPersonsPopulated() {
        return this.mIsPersonsPopulated;
    }

    @Override
    public void setIsPersonsPopuated(boolean isPersonsPopulated) {
        this.mIsPersonsPopulated = isPersonsPopulated;
    }
}
