/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.resources;

import com.deltek.deltekscheduler.clients.PersonClient;
import com.deltek.deltekscheduler.datacontracts.PersonDataContract;
import com.deltek.deltekscheduler.entities.Person;
import com.deltek.deltekscheduler.entities.interfaces.IPerson;
import com.deltek.deltekscheduler.exceptions.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hemachandrankalaimani
 */
@Path("/person")
public class PersonResource extends WebResourceBase {

    private Person mPerson;

    public PersonResource(Person p) {
        ensurePerson(p);
    }

    public PersonResource() {
        this(null);
    }

    private void ensurePerson(Person p) {
        this.mPerson = p;
    }

    @GET
    @Path("{personID}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResource getPersonResource(@PathParam("personID") Integer personId) {

        Person personObj = new Person();
        personObj.setID(personId);

        PersonResource retVal = new PersonResource(personObj);
        return retVal;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDataContract getPerson() {
        PersonDataContract retVal;

        try {
            PersonClient pc = new PersonClient(getDataProvider());

            if (mPerson == null || !pc.loadByID(mPerson)) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            retVal = PersonDataContract.getContract(mPerson);

        } catch (ClientException clinetExc) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return retVal;
    }

    /**
     * Creates the person object
     *
     * @param person
     * @return
     */
    @POST
    @Path("/create")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
    public Response createPerson(PersonDataContract person) {

        Response retVal;

        try {

            //
            // check if valid input
            //
            if (person == null) {
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }
            if (person.getId()!= null) {
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }

            //
            // convert the datacontract to needed format
            //
            IPerson personFromContract = person.toPersonEntity();

            //
            // Create the client and add the person data
            //
            PersonClient personClient = new PersonClient(getDataProvider());
            IPerson addedPerson = personClient.add(personFromContract);

            //
            // Set the response
            //
            retVal = Response.status(Response.Status.CREATED)
                    .entity(PersonDataContract.getContract(addedPerson))
                    .build();

        } catch (DuplicateException | ClientException exc) {
            retVal = Response.status(Response.Status.NOT_ACCEPTABLE)
                    .build();
        }
        return retVal;
    }

    @GET
    @Path("{personID}/meetings")
    @Produces(MediaType.APPLICATION_JSON)
    public MeetingsResource getMeetings(@PathParam("personID") Integer personId) {

        MeetingsResource retVal;

        try {

            Person personObj = new Person();
            personObj.setID(personId);

            this.mPerson = personObj;

            PersonClient pClient = new PersonClient(getDataProvider());

            if (!pClient.loadByID(this.mPerson)) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            if (!mPerson.getIsMeetingsPopulated()) {
                pClient.loadMeetings(mPerson);
            }

            retVal = new MeetingsResource(mPerson.getMeetings());

        } catch (ClientException exc) {
            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
        }
        return retVal;
    }

}
