/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.entities.interfaces;

import java.util.Collection;

/**
 *
 * @author hemachandrankalaimani
 */
public interface IPerson extends IHasID, IHasEmail {

    /**
     * Gets the first name of the person
     *
     * @return
     */
    public String getFirstName();

    /**
     * sets the first name of the person
     *
     * @param firstName
     */
    public void setFirstName(String firstName);

    /**
     * Gets the last name of the person
     *
     * @return
     */
    public String getLastName();

    /**
     * Sets the last name of the person
     *
     * @param lastName
     */
    public void setLastName(String lastName);

    /**
     * Gets the available meetings of the person
     *
     * @return
     */
    public Collection<IMeeting> getMeetings();

    /**
     * Sets the meetings of the person
     *
     * @param meetings
     */
    public void setMeetings(Collection<IMeeting> meetings);

    /**
     * Gets if the meeting is populated
     *
     * @return
     */
    public boolean getIsMeetingsPopulated();

    /**
     * Sets if the meeting is populated
     *
     * @param isMeetingsPopulated
     */
    public void setIsMeetingsPopuated(boolean isMeetingsPopulated);

}
