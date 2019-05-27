/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.datacontracts;

import com.deltek.deltekscheduler.entities.Meeting;
import com.deltek.deltekscheduler.entities.interfaces.IMeeting;
import com.deltek.deltekscheduler.entities.interfaces.IPerson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author hemachandrankalaimani
 */
//@XmlRootElement(name = "meeting")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeetingDataContract {

    @XmlElement(required = false)
    protected Integer id;

    @XmlElement(required = false)
    protected long startTimeInMilliSeconds;

    @XmlElement(required = false)
    protected long endTimeInMilliSeconds;

    @XmlElement(required = false)
    protected PersonDataContract[] personsInMeeting;

    public MeetingDataContract() {
        this.personsInMeeting = null;
        this.endTimeInMilliSeconds = Long.MIN_VALUE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer meetingId) {
        this.id = meetingId;
    }

    public long getStartTimeInMilliSeconds() {
        return startTimeInMilliSeconds;
    }

    public void setStartTimeInMilliSeconds(long startTimeInMilliSeconds) {
        this.startTimeInMilliSeconds = startTimeInMilliSeconds;
    }

    public long getEndTimeInMilliSeconds() {
        return endTimeInMilliSeconds;
    }

    public void setEndTimeInMilliSeconds(long endTimeInMilliSeconds) {
        this.endTimeInMilliSeconds = endTimeInMilliSeconds;
    }

    public PersonDataContract[] getPersonsInMeeting() {
        return this.personsInMeeting;
    }

    public void setPersonsInMeeting(PersonDataContract[] peopleAttendingMeeting) {
        this.personsInMeeting = peopleAttendingMeeting;
    }

    /**
     * Converts the meeting contract object to meeting entity with the
     * associated people set
     *
     * @return
     */
    public IMeeting toMeetingEntity() {
        Meeting retVal = new Meeting();

        retVal.setID(getId());
        retVal.setStartTimeInMilliSeconds(getStartTimeInMilliSeconds());
        retVal.setEndTimeInMilliSeconds(getEndTimeInMilliSeconds());

        PersonDataContract[] peopleOfMeet = this.getPersonsInMeeting();
        if (peopleOfMeet != null) {

            //
            // set the perople from the contract to the person object
            // 
            Collection<IPerson> personColls = Arrays.stream(peopleOfMeet).map(x -> x.toPersonEntity())
                    .collect(Collectors.toList());
            retVal.setPersons(personColls);
        } else {
            retVal.setPersons(new ArrayList<>());
        }
        retVal.setIsPersonsPopuated(true);

        return retVal;
    }

    /**
     *
     * @param meeting
     * @return
     */
    public static MeetingDataContract getContract(IMeeting meeting) {

        MeetingDataContract retVal = new MeetingDataContract();

        retVal.setId(meeting.getID());
        retVal.setStartTimeInMilliSeconds(meeting.getStartTimeInMilliSeconds());
        retVal.setEndTimeInMilliSeconds(meeting.getEndTimeInMilliSeconds());

        if (meeting.getIsPersonsPopulated()) {
            Collection<IPerson> peopleOfMeet = meeting.getPersons();
            if (peopleOfMeet != null) {

                //
                // set the perople from the contract to the person object
                // 
                Collection<PersonDataContract> personColls = peopleOfMeet.stream()
                        .map(x -> PersonDataContract.getContract(x))
                        .collect(Collectors.toList());
                retVal.setPersonsInMeeting(personColls.toArray(new PersonDataContract[personColls.size()]));
            }
        }

        return retVal;
    }

}
