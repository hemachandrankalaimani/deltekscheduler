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
public interface IMeeting extends IHasID {

    /**
     * Gets the start tine of the meeting
     *
     * @return
     */
    public long getStartTimeInMilliSeconds();

    /**
     * Sets the start time of the meeting
     *
     * @param startTimeInMilliSeconds
     */
    public void setStartTimeInMilliSeconds(long startTimeInMilliSeconds);

    /**
     * Gets the end time of the meeting
     *
     * @return
     */
    public long getEndTimeInMilliSeconds();

    /**
     * Sets the end time of the meeting
     *
     * @param endTimeInMilliSeconds
     */
    public void setEndTimeInMilliSeconds(long endTimeInMilliSeconds);

    /**
     * Gets the people of the meeting
     *
     * @return
     */
    public Collection<IPerson> getPersons();

    /**
     * Sets the persons associated with the meeting
     *
     * @param persons
     */
    public void setPersons(Collection<IPerson> persons);

    /**
     * Gets if the people are populated
     *
     * @return
     */
    public boolean getIsPersonsPopulated();

    /**
     * Sets if the people are populated
     *
     * @param isPersonsPopulated
     */
    public void setIsPersonsPopuated(boolean isPersonsPopulated);
}
