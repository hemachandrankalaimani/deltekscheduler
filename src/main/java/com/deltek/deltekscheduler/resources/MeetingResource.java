/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.resources;

import com.deltek.deltekscheduler.clients.MeetingClient;
import com.deltek.deltekscheduler.clients.PersonClient;
import com.deltek.deltekscheduler.datacontracts.MeetingDataContract;
import com.deltek.deltekscheduler.datacontracts.PersonDataContract;
import com.deltek.deltekscheduler.entities.Meeting;
import com.deltek.deltekscheduler.entities.interfaces.IMeeting;
import com.deltek.deltekscheduler.entities.interfaces.IPerson;
import com.deltek.deltekscheduler.exceptions.ClientException;
import com.deltek.deltekscheduler.utils.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
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
@Path("/meeting")
public class MeetingResource extends WebResourceBase {

    private Meeting mMeeting;

    public MeetingResource(Meeting m) {
        ensureMeeting(m);
    }

    public MeetingResource() {
        this(null);
    }

    private void ensureMeeting(Meeting m) {
        this.mMeeting = m;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{meetingID}")
    public MeetingResource getMeeting(@PathParam("meetingID") Integer meetingID) {

        Meeting meetingObj = new Meeting();
        meetingObj.setID(meetingID);

        MeetingResource retVal = new MeetingResource(meetingObj);
        return retVal;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MeetingDataContract getMeeting() {
        MeetingDataContract retVal = null;

        try {
            MeetingClient meetingClient = new MeetingClient(getDataProvider());

            if (mMeeting == null || !meetingClient.loadByPrimaryKey(mMeeting)) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            retVal = MeetingDataContract.getContract(mMeeting);

        } catch (ClientException clinetExc) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return retVal;
    }

    /**
     * Create a meeting
     *
     * @param meeting
     * @return
     */
    @POST
    @Path("/create")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
    public Response createMeeting(MeetingDataContract meeting) {

        Response retVal;

        try {

            //
            // check if valid input
            //
            if (meeting == null) {
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }
            if (meeting.getId()!= null) {
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }

            //
            // convert the datacontract to needed format
            //
            IMeeting meetingFromContract = meeting.toMeetingEntity();

            //
            // Create the client and add the person data
            //
            MeetingClient meetingClient = new MeetingClient(getDataProvider());
            IMeeting addedMeeting = meetingClient.add(meetingFromContract);

            //
            // Set the response
            //
            retVal = Response.status(Response.Status.CREATED)
                    .entity(MeetingDataContract.getContract(addedMeeting))
                    .build();

        } catch (ClientException clientExc) {
            retVal = Response.status(Response.Status.NOT_ACCEPTABLE)
                    .build();
        }
        return retVal;
    }

    @POST
    @Path("/suggestMeetings/{noOfSuggestions}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
    public MeetingsResource suggestMeetingsForPeople(@PathParam("noOfSuggestions") Integer noOfSuggestions,
            PersonDataContract[] persons) {

        //
        // Validate the number of suggestions needed
        //
        if (noOfSuggestions < 1) {
            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
        }

        ArrayList<IMeeting> retVal = new ArrayList<>();

        //
        // Step 1 Validate the persons
        //
        if (persons == null || persons.length < 1) {
            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
        }

        PersonClient pClient = new PersonClient(getDataProvider());

        Collection<IPerson> loadedPeople = Arrays.stream(persons)
                .map(x -> x.toPersonEntity())
                .collect(Collectors.toList());

        loadedPeople.forEach(item -> {
            try {

                //
                // check if all people are in the database/memeory/source
                //
                if (!pClient.loadByID(item)) {
                    throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
                }

                //
                // Load the meetings for the person
                //
                pClient.loadMeetings(item);
            } catch (ClientException ex) {
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }
        });

        //
        // Get the next nearest hour
        //
        long nextHearHourMilliSeconds = DateUtils.getNextNearestHour();

        //
        // We are suggesting only for 24 hours
        //
        int i = 0;
        while (i < 24) {

            //
            // check if we have crossed the number of suggestions 
            //
            if (retVal.size() >= noOfSuggestions) {
                break;
            }

            boolean thisTimeslotPossible = true;

            for (IPerson p : loadedPeople) {

                //
                // Check if this person has any meeting for this time
                //
                final long nextStartHour = nextHearHourMilliSeconds;
                thisTimeslotPossible = p.getMeetings()
                        .stream()
                        .noneMatch(x -> x.getStartTimeInMilliSeconds() / 1000 == nextStartHour / 1000);

                if (!thisTimeslotPossible) {
                    break;
                }
            }

            //
            // No meetings at this time for any of these people
            //
            if (thisTimeslotPossible) {

                //
                // Add a new meeting object for this timeslot
                //
                Meeting nextPossibleMeeting = new Meeting();
                nextPossibleMeeting.setStartTimeInMilliSeconds(nextHearHourMilliSeconds);
                nextPossibleMeeting.setEndTimeInMilliSeconds(nextHearHourMilliSeconds + (60 * 60 * 1000));

                retVal.add(nextPossibleMeeting);
            }

            //
            // do check for the next hour
            //
            nextHearHourMilliSeconds = nextHearHourMilliSeconds + (60 * 60 * 1000);
        }

        MeetingsResource retValResource = new MeetingsResource(retVal);
        return retValResource;
    }

}
