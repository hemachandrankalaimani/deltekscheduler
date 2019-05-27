/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.datacontracts;

import com.deltek.deltekscheduler.entities.interfaces.IMeeting;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hemachandrankalaimani
 */
@XmlRootElement(name = "meetingList")
@XmlAccessorType(XmlAccessType.FIELD)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MeetingsListDataContract {

    //@XmlElement(required = false)
    protected MeetingDataContract[] meetings;

    public MeetingsListDataContract() {
        this.meetings = null;
    }

    public MeetingDataContract[] getMeetings() {
        return this.meetings;
    }

    public void setMeetings(MeetingDataContract[] meetingsArray) {
        this.meetings = meetingsArray;
    }

    /**
     * Converts the meeting contract object to meeting entity with the
     * associated people set
     *
     * @return
     */
    public Collection<IMeeting> toMeetingEntity() {
        Collection<IMeeting> retVal = null;

        if (this.getMeetings() != null) {
            retVal = Arrays.stream(this.getMeetings())
                    .map(x -> x.toMeetingEntity())
                    .collect(Collectors.toList());

        }

        return retVal;
    }

    /**
     *
     * @param meetingsCollection
     * @return
     */
    public static MeetingsListDataContract getContract(Collection<IMeeting> meetingsCollection) {

        MeetingsListDataContract retVal = new MeetingsListDataContract();

        if (meetingsCollection != null) {

            List<MeetingDataContract> meetDataContracts
                    = meetingsCollection.stream()
                            .map(x -> MeetingDataContract.getContract(x))
                            .collect(Collectors.toList());
            retVal.setMeetings(meetDataContracts.toArray(new MeetingDataContract[meetDataContracts.size()]));
        }

        return retVal;
    }

}
