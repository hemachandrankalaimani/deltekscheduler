/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.entities.interfaces;

/**
 *
 * @author hemachandrankalaimani
 */
public interface IPersonMeeting {

    /**
     * Gets the person associated with the meeting
     *
     * @return
     */
    public IPerson getPerson();

    /**
     * Sets the person
     *
     * @param person
     */
    public void setPerson(IPerson person);

    /**
     * Gets the meeting that the person attends
     *
     * @return
     */
    public IMeeting getMeeting();

    /**
     * Sets the meeting
     *
     * @param meeting
     */
    public void setMetting(IMeeting meeting);
}
