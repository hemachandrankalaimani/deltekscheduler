/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.clients;

import com.deltek.deltekscheduler.data.InMemoryDataProvider;
import com.deltek.deltekscheduler.entities.interfaces.*;
import com.deltek.deltekscheduler.exceptions.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *
 * @author hemachandrankalaimani
 */
public class PersonClient implements IClient<IPerson> {

    //
    // Instance variable
    // 
    private final InMemoryDataProvider inMemoryDataProvider;

    /**
     * default Constructor
     *
     * @param dataProvider
     */
    public PersonClient(InMemoryDataProvider dataProvider) {
        this.inMemoryDataProvider = dataProvider;
    }

    @Override
    public boolean loadByPrimaryKey(IPerson obj) throws ClientException {
        return this.loadByEmail(obj);
    }

    @Override
    public void loadAll(Collection<IPerson> list) throws ClientException {
        if (this.inMemoryDataProvider != null) {
            //
            // Get all the persons from the provider
            //
            if (list != null) {
                list.addAll(this.inMemoryDataProvider.getPersons());
            }
        }
    }

    /**
     * Loads the object by ID
     *
     * @param obj
     * @return
     * @throws ClientException
     */
    public boolean loadByID(IPerson obj) throws ClientException {
        boolean retVal = false;

        if (obj != null && this.inMemoryDataProvider != null) {
            //
            // Check if the person with ID exists in the provider
            //
            IPerson p = this.inMemoryDataProvider.getPersons().stream().filter(x -> x.getID().equals(obj.getID()))
                    .findFirst().orElse(null);

            if (p != null) {
                copyProperties(p, obj);
                obj.setIsPopulated(true);
                retVal = true;
            }
        }

        return retVal;
    }

    /**
     * Loads the object by Email
     *
     * @param obj
     * @return
     * @throws ClientException
     */
    private boolean loadByEmail(IPerson obj) throws ClientException {
        boolean retVal = false;

        if (obj != null && this.inMemoryDataProvider != null) {
            //
            // Check if the person with ID exists in the provider
            //
            IPerson p = this.inMemoryDataProvider.getPersons().stream().filter(x -> x.getEmail().equals(obj.getEmail()))
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
    public IPerson add(IPerson obj) throws ClientException, DuplicateException {

        //
        // Check if person exists 
        // 
        long personsWithSame = this.inMemoryDataProvider.getPersons().stream().
                filter(x -> x.getEmail().equals(obj.getEmail()))
                .count();
        if (personsWithSame == 0) {

            //
            // Now add the person
            //
            int idForNewUser = 1;
            if (!this.inMemoryDataProvider.getPersons().isEmpty()) {
                IPerson personWithMaxid = this.inMemoryDataProvider.getPersons().
                        stream().max(Comparator.comparingInt(IPerson::getID)).get();
                //
                // get the max id of the current users and increement by 1
                //
                idForNewUser = (personWithMaxid.getID() + 1);
            }

            //
            //set the ID for the person and add him
            //
            obj.setID(idForNewUser);
            obj.setIsPopulated(true);
            this.inMemoryDataProvider.getPersons().add(obj);
            return obj;
        } else {
            //
            // throw duplicate excpetion
            //
            throw new DuplicateException("Person with email " + obj.getEmail() + " already exists");
        }
    }

    /**
     * Copies the properties of the from object to object
     *
     * @param fromPerson
     * @param toPerson
     */
    private void copyProperties(IPerson fromPerson, IPerson toPerson) {

        toPerson.setID(fromPerson.getID());
        toPerson.setEmail(fromPerson.getEmail());
        toPerson.setFirstName(fromPerson.getFirstName());
        toPerson.setLastName(fromPerson.getLastName());
        toPerson.setIsPopulated(fromPerson.isPopulated());

        toPerson.setIsMeetingsPopuated(fromPerson.getIsMeetingsPopulated());
        toPerson.setMeetings(fromPerson.getMeetings());
    }

    /**
     * Loads the associated meetings of the person object
     *
     * @param obj
     * @throws com.deltek.deltekscheduler.exceptions.ClientException
     */
    public void loadMeetings(IPerson obj) throws ClientException {

        if (!obj.getIsMeetingsPopulated()) {
            Collection<IMeeting> meetings
                    = this.inMemoryDataProvider.getPersonMeetings()
                            .stream()
                            .filter(x -> x.getPerson().getID().equals(obj.getID()))
                            .map(y -> y.getMeeting())
                            .collect(Collectors.toList());

            obj.setMeetings(meetings);
            obj.setIsMeetingsPopuated(true);
        }
    }

}
