/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.resources;

import com.deltek.deltekscheduler.clients.MeetingClient;
import com.deltek.deltekscheduler.datacontracts.MeetingDataContract;
import com.deltek.deltekscheduler.datacontracts.MeetingsListDataContract;
import com.deltek.deltekscheduler.entities.interfaces.IMeeting;
import com.deltek.deltekscheduler.exceptions.ClientException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hemachandrankalaimani
 */
@Path("/meetings")
public class MeetingsResource extends WebResourceBase {

    protected Collection<IMeeting> mMeetings;

    public MeetingsResource(Collection<IMeeting> meetings) {
        this.mMeetings = meetings;
    }

    public MeetingsResource() {
        this(null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MeetingDataContract[] getMeetings() {
        try {

            if (mMeetings == null) {
                mMeetings = new ArrayList<>();
                MeetingClient meetingClient = new MeetingClient(getDataProvider());
                meetingClient.loadAll(mMeetings);
            }

            //
            // if stil null no meetings found
            //
            if (mMeetings == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            List<MeetingDataContract> meetDataContracts
                    = mMeetings.stream()
                            .map(x -> MeetingDataContract.getContract(x))
                            .collect(Collectors.toList());

            return meetDataContracts.toArray(new MeetingDataContract[meetDataContracts.size()]);

        } catch (ClientException clinetExc) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        //return retVal;
    }

}
