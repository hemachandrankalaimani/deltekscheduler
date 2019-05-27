/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.resources;

import com.deltek.deltekscheduler.application.ApplicationConfig;
import com.deltek.deltekscheduler.application.ApplicationDataSource;
import com.deltek.deltekscheduler.datacontracts.MeetingDataContract;
import com.deltek.deltekscheduler.datacontracts.MeetingsListDataContract;
import com.deltek.deltekscheduler.datacontracts.PersonDataContract;
import com.deltek.deltekscheduler.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
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
public class PersonResourceTest extends JerseyTest {
    
    public PersonResourceTest() {
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
     * Test of createPerson method, of class PersonResource.
     */
    @Test
    public void testCreatePerson() {
        System.out.println("createPerson");
        
        PersonDataContract person = new PersonDataContract();
        person.setEmail("h@k.co.uk");
        person.setFirstName("h");
        person.setLastName("k");
        
        Entity<PersonDataContract> userEntity = Entity.entity(person, MediaType.APPLICATION_JSON);
        
        
        
        Response response = target("/person/create").request().post(userEntity);
        assertEquals( Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        PersonDataContract createdPerson = response.readEntity(PersonDataContract.class);
        
        assertTrue(createdPerson.getId()!= null) ;
        assertEquals(person.getEmail(), createdPerson.getEmail());
    
    }
    
    /**
     * Test of createPerson method, of class PersonResource.
     */
    @Test
    public void testCreateDuplicatePerson() {
        System.out.println("createPerson");
        
        PersonDataContract person = new PersonDataContract();
        person.setEmail("h@k.co.uk");
        person.setFirstName("h");
        person.setLastName("k");
        
        Entity<PersonDataContract> userEntity = Entity.entity(person, MediaType.APPLICATION_JSON);
        
        
        Response response = target("/person/create").request().post(userEntity);
        assertEquals( Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        PersonDataContract createdPerson = response.readEntity(PersonDataContract.class);
        
        assertTrue(createdPerson.getId()!= null) ;
        assertEquals(person.getEmail(), createdPerson.getEmail());
        
        Entity<PersonDataContract> duplicateUserEntity = Entity.entity(person, MediaType.APPLICATION_JSON);
        
        Response response1 = target("/person/create").request().post(duplicateUserEntity);
        assertEquals( Response.Status.NOT_ACCEPTABLE.getStatusCode(), response1.getStatus());
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
    public void testGetMeetingsOfAPersonOneMeeting() {
        System.out.println("getMeetings");
          
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
        
        
        response = target("/person/" + person.getId() + "/meetings").request().get();
        assertEquals( Response.Status.OK.getStatusCode(), response.getStatus());
        
        String s = response.readEntity(String.class);
                
        ObjectMapper mapper = new ObjectMapper();
        try {
            MeetingsListDataContract jsonObj = mapper.readValue(s
                    , MeetingsListDataContract.class);
            assertEquals( 1, jsonObj.getMeetings().length);
            
            
            assertEquals(meeting.getStartTimeInMilliSeconds(), 
                    jsonObj.getMeetings()[0].getStartTimeInMilliSeconds());
            assertEquals(meeting.getEndTimeInMilliSeconds(), 
                    jsonObj.getMeetings()[0].getEndTimeInMilliSeconds());
            
            assertEquals(meeting.getPersonsInMeeting().length, 
                    jsonObj.getMeetings()[0].getPersonsInMeeting().length);
            
        } catch (IOException ex) {
            fail();
        }
    }
    
    /**
     * Test of createMeeting method, of class MeetingResource.
     */
    @Test
    public void testGetMeetingsOfAPerson() {
        System.out.println("getMeetings");
        
          
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
    
        
        MeetingDataContract meeting1 = new MeetingDataContract();
        meeting1.setStartTimeInMilliSeconds(startDate.getTime());
        meeting1.setEndTimeInMilliSeconds(endDate.getTime());
        
        meeting1.setPersonsInMeeting(new PersonDataContract[] { person });
        
        Entity<MeetingDataContract> meetingEntity1 = Entity.entity(meeting1, MediaType.APPLICATION_JSON);
        
        response = target("/meeting/create").request().post(meetingEntity1);
        assertEquals( Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        
        response = target("/person/" + person.getId() + "/meetings").request().get();
        assertEquals( Response.Status.OK.getStatusCode(), response.getStatus());
        
        String s = response.readEntity(String.class);
                
        ObjectMapper mapper = new ObjectMapper();
        try {
            MeetingsListDataContract jsonObj = mapper.readValue(s
                    , MeetingsListDataContract.class);
            assertEquals( 2, jsonObj.getMeetings().length);
            
            
            assertEquals(meeting.getStartTimeInMilliSeconds(), 
                    jsonObj.getMeetings()[0].getStartTimeInMilliSeconds());
            assertEquals(meeting.getEndTimeInMilliSeconds(), 
                    jsonObj.getMeetings()[0].getEndTimeInMilliSeconds());
            assertEquals(meeting.getPersonsInMeeting().length, 
                    jsonObj.getMeetings()[0].getPersonsInMeeting().length);
            
            assertEquals(meeting1.getStartTimeInMilliSeconds(), 
                    jsonObj.getMeetings()[1].getStartTimeInMilliSeconds());
            assertEquals(meeting1.getEndTimeInMilliSeconds(), 
                    jsonObj.getMeetings()[1].getEndTimeInMilliSeconds());
            assertEquals(meeting1.getPersonsInMeeting().length, 
                    jsonObj.getMeetings()[1].getPersonsInMeeting().length);
            
        } catch (IOException ex) {
            fail();
        }
    }
    
    /**
     * Test of createMeeting method, of class MeetingResource.
     */
    @Test
    public void testSuggestMeetingsOfAPerson() {
        System.out.println("suggestMeetings");
        
          
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
        
        
        PersonDataContract[] personsNeededForMeeting = new PersonDataContract[]{person};
        
        Entity<PersonDataContract[]> personsNeededForMeetingEntity = Entity.entity(personsNeededForMeeting, MediaType.APPLICATION_JSON);
        
        int numberofSuggestions = 3;
        
        response = target("/meeting/suggestMeetings/" + numberofSuggestions).request().post(personsNeededForMeetingEntity);
        assertEquals( Response.Status.OK.getStatusCode(), response.getStatus());
        
        String s = response.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            MeetingsListDataContract jsonObj = mapper.readValue(s
                    , MeetingsListDataContract.class);
            assertEquals( numberofSuggestions, jsonObj.getMeetings().length);
            
            for(MeetingDataContract dc : jsonObj.getMeetings()){
                assertNotEquals(dc.getStartTimeInMilliSeconds(), startDate.getTime());
            }
            
        } catch (IOException ex) {
            fail();
        }
    }
    
    /**
     * Test of createMeeting method, of class MeetingResource.
     */
    @Test
    public void testSuggestMeetingsOfMoreThanOnePerson() {
        System.out.println("suggestMeetings");
        
         //
         // First person meetings
         //
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
        
        //
        // Person1 meetings
        //
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.HOUR, 1);
        Date startDate1 = new Date(DateUtils.getNextNearestHourForTheDate(c.getTime()));
        Date endDate1 = new Date(DateUtils.getNextNearestHourForTheDate(startDate1));
        PersonDataContract person1 = createTestPerson("hk1@co.uk", "H", "K");
        MeetingDataContract meeting1 = new MeetingDataContract();
        meeting1.setStartTimeInMilliSeconds(startDate1.getTime());
        meeting1.setEndTimeInMilliSeconds(endDate1.getTime());
        
        meeting.setPersonsInMeeting(new PersonDataContract[] { person, person1 });
        
        Entity<MeetingDataContract> meetingEntity1 = Entity.entity(meeting1, MediaType.APPLICATION_JSON);
        
        response = target("/meeting/create").request().post(meetingEntity1);
        assertEquals( Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        PersonDataContract[] personsNeededForMeeting = new PersonDataContract[]{person, person1};
        
        Entity<PersonDataContract[]> personsNeededForMeetingEntity = Entity.entity(personsNeededForMeeting, MediaType.APPLICATION_JSON);
        
        int numberofSuggestions = 1;
        
        response = target("/meeting/suggestMeetings/" + numberofSuggestions).request().post(personsNeededForMeetingEntity);
        assertEquals( Response.Status.OK.getStatusCode(), response.getStatus());
        
        String s = response.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            MeetingsListDataContract jsonObj = mapper.readValue(s
                    , MeetingsListDataContract.class);
            assertEquals( numberofSuggestions, jsonObj.getMeetings().length);
            
            for(MeetingDataContract dc : jsonObj.getMeetings()){
                //
                // Not clashing with first meeting
                //
                assertNotEquals(dc.getStartTimeInMilliSeconds(), startDate.getTime());
                
                //
                // Not clashing with second meeting
                //
                assertNotEquals(dc.getStartTimeInMilliSeconds(), startDate1.getTime());
            }
            
            
            
        } catch (IOException ex) {
            fail();
        }
    }
    
}
