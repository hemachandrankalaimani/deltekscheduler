/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.clients;

import com.deltek.deltekscheduler.data.InMemoryDataProvider;
import com.deltek.deltekscheduler.entities.PersonMeeting;
import com.deltek.deltekscheduler.entities.interfaces.*;
import com.deltek.deltekscheduler.exceptions.*;
import com.deltek.deltekscheduler.utils.DateUtils;
import java.util.Collection;
import java.util.Comparator;

/**
 *
 * @author hemachandrankalaimani
 */
public class MeetingClient implements IClient<IMeeting> {

    //
    // Instance variable
    // 
    private final InMemoryDataProvider inMemoryDataProvider;

    /**
     * default Constructor
     *
     * @param dataProvider
     */
    public MeetingClient(InMemoryDataProvider dataProvider) {
        this.inMemoryDataProvider = dataProvider;
    }

    @Override
    public boolean loadByPrimaryKey(IMeeting obj) throws ClientException {
        boolean retVal = false;

        if (obj != null && this.inMemoryDataProvider != null) {
            //
            // Check if the meeting with ID exists in the provider
            //
            IMeeting p = this.inMemoryDataProvider.getMeetings().stream().
                    filter(x -> x.getID().equals(obj.getID()))
                    .findFirst().orElse(null);

            if (p != null) {
                copyProperties(p, obj);
                obj.setIsPopulated(true);
                retVal = true;
            }
        }

        return retVal;
    }

    @Override
    public void loadAll(Collection<IMeeting> list) throws ClientException {
        if (this.inMemoryDataProvider != null) {
            //
            // Get all the persons from the provider
            //
            if (list != null) {
                list.addAll(this.inMemoryDataProvider.getMeetings());
            }
        }
    }

    @Override
    public IMeeting add(IMeeting obj) throws ClientException {

        //
        // validate the time first
        //
        if(!DateUtils.checkIfMeetingStartIsInFuture(obj.getStartTimeInMilliSeconds())){
            throw new ClientException("Meeting should start only be in the future");
        }
        if (!DateUtils.checkIfMilliSecondIsInHour(obj.getStartTimeInMilliSeconds())) {
            throw new ClientException("Meeting starttime should be only at the hour mark");
        }
        if (!DateUtils.checkIfMilliSecondIsInHour(obj.getEndTimeInMilliSeconds())) {
            throw new ClientException("Meeting endtime should be only at the hour mark");
        }
        if (!DateUtils.checkIfValidTimeIntervalForMeetings(obj.getStartTimeInMilliSeconds(), obj.getEndTimeInMilliSeconds())) {
            throw new ClientException("Meeting can last only one hour");
        }

        //
        // Now add the person
        //
        int idForNewMeeting = 1;
        if (!this.inMemoryDataProvider.getMeetings().isEmpty()) {
            IMeeting meetingWithMaxid = this.inMemoryDataProvider.getMeetings().
                    stream().max(Comparator.comparingInt(IMeeting::getID)).get();
            //
            // get the max id of the current users and increement by 1
            //
            idForNewMeeting = (meetingWithMaxid.getID() + 1);
        }

        //
        //set the ID for the meeting and add it 
        //
        obj.setID(idForNewMeeting);
        obj.setIsPopulated(true);
        this.inMemoryDataProvider.getMeetings().add(obj);

        //
        //
        // If there are peopel to be added to the meeting
        if (obj.getIsPersonsPopulated()) {
            //
            // add the aasociated persons to the meeting
            //
            Collection<IPerson> peopleOfMeeting = obj.getPersons();

            if (peopleOfMeeting != null) {

                PersonClient pClient = new PersonClient(this.inMemoryDataProvider);
                //
                // Now add these people to the meeting
                //
                for (IPerson person : peopleOfMeeting) {
                    //
                    // Step -1 Check if the person exists
                    //
                    if (pClient.loadByID(person)) {
                        //
                        // Now create a person client record
                        // 
                        PersonMeeting personMeeting = new PersonMeeting();
                        personMeeting.setMetting(obj);
                        personMeeting.setPerson(person);

                        this.inMemoryDataProvider.getPersonMeetings().add(personMeeting);
                    }
                }
            }
        }

        return obj;
    }

    /**
     * Copies the properties of the from object to object
     *
     * @param from
     * @param to
     */
    private void copyProperties(IMeeting from, IMeeting to) {

        to.setID(from.getID());
        to.setStartTimeInMilliSeconds(from.getStartTimeInMilliSeconds());
        to.setEndTimeInMilliSeconds(from.getEndTimeInMilliSeconds());
        to.setIsPopulated(from.isPopulated());

        to.setIsPersonsPopuated(from.getIsPersonsPopulated());
        to.setPersons(from.getPersons());
    }

}
