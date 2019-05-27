/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.entities;

import com.deltek.deltekscheduler.entities.interfaces.IMeeting;
import com.deltek.deltekscheduler.entities.interfaces.IPerson;
import com.deltek.deltekscheduler.entities.interfaces.IPersonMeeting;

/**
 *
 * @author hemachandrankalaimani
 */
public class PersonMeeting implements IPersonMeeting {

    private IPerson mPerson;
    private IMeeting mMeeting;

    @Override
    public IPerson getPerson() {
        return this.mPerson;
    }

    @Override
    public void setPerson(IPerson person) {
        this.mPerson = person;
    }

    @Override
    public IMeeting getMeeting() {
        return this.mMeeting;
    }

    @Override
    public void setMetting(IMeeting meeting) {
        this.mMeeting = meeting;
    }

}
