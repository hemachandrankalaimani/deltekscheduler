/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.resources;

import com.deltek.deltekscheduler.application.ApplicationConfig;
import com.deltek.deltekscheduler.application.ApplicationDataSource;
import com.deltek.deltekscheduler.datacontracts.MeetingDataContract;
import com.deltek.deltekscheduler.datacontracts.PersonDataContract;
import com.deltek.deltekscheduler.utils.DateUtils;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hemachandrankalaimani
 */
public class MeetingResourceTest extends JerseyTest {
    
    public MeetingResourceTest() {
    }
    
    @Before
    public void beforeEachTest() {
        ApplicationDataSource.getApplicationDataSource().clearAll();
    }
    
    @Override
    protected Application configure() {
        ApplicationConfig config = new ApplicationConfig();
        config.property("jersey.config.server.provider.packages", 
                "com.deltek.deltekscheduler.resources");
        return config;
    }
    
    /**
     * Creates a test person
     * @param email
     * @param fName
     * @param lName
     * @return 
     */
    private PersonDataContract createTestPerson(String email, String fName, String lName){
        PersonDataContract person = new PersonDataContract();
        person.setEmail(email);
        person.setFirstName(fName);
        person.setLastName(lName);
        
        Entity<PersonDataContract> userEntity = Entity.entity(person, MediaType.APPLICATION_JSON);
        
        
        Response response = target("/person/create").request().post(userEntity);
        assertEquals( Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        PersonDataContract createdPerson = response.readEntity(PersonDataContract.class);
        
        PersonDataContract retVal = new PersonDataContract();
        retVal.setId(createdPerson.getId());
        
        return retVal;
    }

    
    /**
     * Test of createMeeting method, of class MeetingResource.
     */
    @Test
    public void testCreateMeetingWithOnePerson() {
        System.out.println("createMeeting");
        
        
        Date startDate = new Date(DateUtils.getNextNearestHourForTheDate(new Date()));
        Date endDate = new Date(DateUtils.getNextNearestHourForTheDate(startDate));
        
        
        PersonDataContract person = createTestPerson("hk@co.uk", "H", "K");
        
        MeetingDataContract meeting = new MeetingDataContract();
        meeting.setStartTimeInMilliSeconds(startDate.getTime());
        meeting.setEndTimeInMilliSeconds(endDate.getTime());
        
        meeting.setPersonsInMeeting(new PersonDataContract[] { person });
        
        Entity<MeetingDataContract> meetingEntity = Entity.entity(meeting, MediaType.APPLICATION_JSON);
        
        Response response = target("/meeting/create").request().post(meetingEntity);
        assertEquals( Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        MeetingDataContract createdMeeting = response.readEntity(MeetingDataContract.class);
        
        assertTrue(createdMeeting.getId()!= null) ;
        assertEquals(meeting.getStartTimeInMilliSeconds(), createdMeeting.getStartTimeInMilliSeconds());
        assertEquals(meeting.getEndTimeInMilliSeconds(), createdMeeting.getEndTimeInMilliSeconds());
        assertEquals(meeting.getPersonsInMeeting().length, createdMeeting.getPersonsInMeeting().length);
        assertEquals(meeting.getPersonsInMeeting()[0].getId(), createdMeeting.getPersonsInMeeting()[0].getId());
    
    }
    
    /**
     * Test of createMeeting method, of class MeetingResource.
     */
    @Test
    public void testCreateMeetingWithMoreThanOnePerson() {
        System.out.println("createMeeting");
        
        Date startDate = new Date(DateUtils.getNextNearestHourForTheDate(new Date()));
        Date endDate = new Date(DateUtils.getNextNearestHourForTheDate(startDate));
        
        
        PersonDataContract person1 = createTestPerson("hk@co.uk", "H", "K");
        
        PersonDataContract person2 = createTestPerson("hk1@co.uk", "H", "K");
        
        PersonDataContract person3 = createTestPerson("hk2@co.uk", "H", "K");
        
        MeetingDataContract meeting = new MeetingDataContract();
        meeting.setStartTimeInMilliSeconds(startDate.getTime());
        meeting.setEndTimeInMilliSeconds(endDate.getTime());
        
        meeting.setPersonsInMeeting(new PersonDataContract[] { person1, person2, person3 });
        
        Entity<MeetingDataContract> meetingEntity = Entity.entity(meeting, MediaType.APPLICATION_JSON);
        
        Response response = target("/meeting/create").request().post(meetingEntity);
        assertEquals( Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        MeetingDataContract createdMeeting = response.readEntity(MeetingDataContract.class);
        
        assertTrue(createdMeeting.getId()!= null) ;
        assertEquals(meeting.getStartTimeInMilliSeconds(), createdMeeting.getStartTimeInMilliSeconds());
        assertEquals(meeting.getEndTimeInMilliSeconds(), createdMeeting.getEndTimeInMilliSeconds());
        assertEquals(meeting.getPersonsInMeeting().length, createdMeeting.getPersonsInMeeting().length);
        
    }
    
    /**
     * Test of createMeeting method, of class MeetingResource.
     */
    @Test
    public void testCreateMeetingWithInvalidStartDate() {
        System.out.println("createMeeting");
        
        Date startDate = new Date();
        Date endDate = new Date(DateUtils.getNextNearestHourForTheDate(startDate));
        
        
        PersonDataContract person = new PersonDataContract();
        person.setEmail("h@k.co.uk");
        person.setFirstName("h");
        person.setLastName("k");
        
        MeetingDataContract meeting = new MeetingDataContract();
        meeting.setStartTimeInMilliSeconds(startDate.getTime());
        meeting.setEndTimeInMilliSeconds(endDate.getTime());
        
        meeting.setPersonsInMeeting(new PersonDataContract[] { person });
        
        Entity<MeetingDataContract> meetingEntity = Entity.entity(meeting, MediaType.APPLICATION_JSON);
        
        Response response = target("/meeting/create").request().post(meetingEntity);
        assertEquals( Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        
    }
    
    /**
     * Test of createMeeting method, of class MeetingResource.
     */
    @Test
    public void testCreateMeetingWithInvalidEndDate() {
        System.out.println("createMeeting");
        
        //Generate a date for Jan. 9, 2013, 10:11:12 AM
        Date startDate = new Date(DateUtils.getNextNearestHourForTheDate(new Date()));
        Date endDate = new Date();
        
        
        PersonDataContract person = new PersonDataContract();
        person.setEmail("h@k.co.uk");
        person.setFirstName("h");
        person.setLastName("k");
        
        MeetingDataContract meeting = new MeetingDataContract();
        meeting.setStartTimeInMilliSeconds(startDate.getTime());
        meeting.setEndTimeInMilliSeconds(endDate.getTime());
        
        meeting.setPersonsInMeeting(new PersonDataContract[] { person });
        
        Entity<MeetingDataContract> meetingEntity = Entity.entity(meeting, MediaType.APPLICATION_JSON);
        
        Response response = target("/meeting/create").request().post(meetingEntity);
        assertEquals( Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        
    }
    
    
    /**
     * Test of createMeeting method, of class MeetingResource.
     */
    @Test
    public void testCreateMeetingWithPastDate() {
        System.out.println("createMeeting");
        
        
        Calendar cal = Calendar.getInstance();
        
        cal.set(2019, Calendar.MAY, 26, 10, 0, 0); //Year, month, day of month, hours, minutes and seconds
        Date startDate = cal.getTime();
        
        
        cal.set(2019, Calendar.MAY, 26, 11, 0, 0); //Year, month, day of month, hours, minutes and seconds
        Date endDate = cal.getTime();
        
        
        PersonDataContract person = createTestPerson("hk@co.uk", "H", "K");
        
        MeetingDataContract meeting = new MeetingDataContract();
        meeting.setStartTimeInMilliSeconds(startDate.getTime());
        meeting.setEndTimeInMilliSeconds(endDate.getTime());
        
        meeting.setPersonsInMeeting(new PersonDataContract[] { person });
        
        Entity<MeetingDataContract> meetingEntity = Entity.entity(meeting, MediaType.APPLICATION_JSON);
        
        Response response = target("/meeting/create").request().post(meetingEntity);
        assertEquals( Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        
    }
}
