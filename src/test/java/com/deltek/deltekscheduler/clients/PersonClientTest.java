/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.clients;

import com.deltek.deltekscheduler.data.InMemoryDataProvider;
import com.deltek.deltekscheduler.entities.Meeting;
import com.deltek.deltekscheduler.entities.Person;
import com.deltek.deltekscheduler.entities.PersonMeeting;
import com.deltek.deltekscheduler.entities.interfaces.IPerson;
import com.deltek.deltekscheduler.exceptions.DuplicateException;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hemachandrankalaimani
 */
public class PersonClientTest {
    
    public PersonClientTest() {
    }
    

    private InMemoryDataProvider mDataProvider;

    
    @Before
    public void setUp() {
         mDataProvider = new InMemoryDataProvider();
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of loadByPrimaryKey method, of class PersonClient.
     */
    @Test
    public void testLoadByPrimaryKey_WithNullObj() throws Exception {
        System.out.println("testLoadByPrimaryKey_WithNullObj");
        Person obj = null;
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        boolean expResult = false;
        boolean result = instance.loadByPrimaryKey(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of loadByPrimaryKey method, of class PersonClient.
     */
    @Test
    public void testLoadByPrimaryKey_WithNullObj_AndOneRecord() throws Exception {
        System.out.println("testLoadByPrimaryKey_WithNullObj");
        Person obj = null;
        
        Person alreadyInList = new Person();
        alreadyInList.setID(1);
        alreadyInList.setEmail("hk@test.co.uk");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        boolean expResult = false;
        boolean result = instance.loadByPrimaryKey(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of loadByPrimaryKey method, of class PersonClient.
     */
    @Test
    public void testLoadByPrimaryKeyWithoutCorrectEmail() throws Exception {
        System.out.println("testLoadByPrimaryKey_WithNullObj");
        String email = "hk@test.co.uk";
        
        Person obj = new Person();
        obj.setEmail(email);
        
        Person alreadyInList = new Person();
        alreadyInList.setID(1);
        alreadyInList.setEmail("hk1test@tst.co.uk");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        boolean expResult = false;
        boolean result = instance.loadByPrimaryKey(obj);
        assertEquals(expResult, result);
        
        assertFalse(obj.isPopulated());
    }
    
    /**
     * Test of loadByPrimaryKey method, of class PersonClient.
     */
    @Test
    public void testLoadByPrimaryKey() throws Exception {
        System.out.println("testLoadByPrimaryKey_WithNullObj");
        String email = "hk@test.co.uk";
        
        Person obj = new Person();
        obj.setEmail(email);
        
        Person alreadyInList = new Person();
        alreadyInList.setID(1);
        alreadyInList.setEmail(email);
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        boolean expResult = true;
        boolean result = instance.loadByPrimaryKey(obj);
        assertEquals(expResult, result);
        
        assertEquals(alreadyInList.getEmail(), obj.getEmail());
        assertEquals(alreadyInList.getID(), obj.getID());
        assertEquals(alreadyInList.getFirstName(), obj.getFirstName());
        assertEquals(alreadyInList.getLastName(), obj.getLastName());
        assertTrue(obj.isPopulated());
    }

    /**
     * Test of loadAll method, of class PersonClient.
     */
    @Test
    public void testLoadAllWithNone() throws Exception {
        System.out.println("loadAll");
        Collection<IPerson> list = new ArrayList<IPerson>();
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        instance.loadAll(list);
        assertEquals( this.mDataProvider.getPersons().size() , list.size());
        
    }
    
    /**
     * Test of loadAll method, of class PersonClient.
     */
    @Test
    public void testLoadAllWithOne() throws Exception {
        System.out.println("loadAll");
        Collection<IPerson> list = new ArrayList<IPerson>();
        
        Person alreadyInList = new Person();
        alreadyInList.setID(1);
        alreadyInList.setEmail("hk@gmail.com");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        instance.loadAll(list);
        assertEquals( this.mDataProvider.getPersons().size() , list.size());
        
    }
    
    /**
     * Test of loadAll method, of class PersonClient.
     */
    @Test
    public void testLoadAllWithMany() throws Exception {
        System.out.println("loadAll");
        Collection<IPerson> list = new ArrayList<IPerson>();
        
        Person alreadyInList = new Person();
        alreadyInList.setID(1);
        alreadyInList.setEmail("hk@gmail.com");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        
        Person alreadyInList1 = new Person();
        alreadyInList1.setID(2);
        alreadyInList1.setEmail("hk1@gmail.com");
        alreadyInList1.setFirstName("h1");
        alreadyInList1.setLastName("k1");
        
        this.mDataProvider.getPersons().add(alreadyInList1);
        
        Person alreadyInList2 = new Person();
        alreadyInList2.setID(3);
        alreadyInList2.setEmail("hk2@gmail.com");
        alreadyInList2.setFirstName("h2");
        alreadyInList2.setLastName("k2");
        
        this.mDataProvider.getPersons().add(alreadyInList2);
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        instance.loadAll(list);
        assertEquals( this.mDataProvider.getPersons().size() , list.size());
        
    }

    /**
     * Test of add method, of class PersonClient.
     */
    @Test
    public void testAdd() throws Exception {
       
        System.out.println("add");
       
        Person alreadyInList = new Person();
        alreadyInList.setEmail("hk@gmail.com");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        int beforeSize = this.mDataProvider.getPersons().size();
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        IPerson retVal = instance.add(alreadyInList);
        assertEquals( beforeSize + 1 , this.mDataProvider.getPersons().size());
        assertEquals(1, retVal.getID().intValue());
    }
    
    /**
     * Test of add method, of class PersonClient.
     */
    @Test(expected = DuplicateException.class)
    public void testAddDuplicateAdd() throws Exception {
       
        System.out.println("add");
       
        Person alreadyInList = new Person();
        alreadyInList.setEmail("hk@gmail.com");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        instance.add(alreadyInList);
        instance.add(alreadyInList);
        
    }
    
    /**
     * Test of loadByPrimaryKey method, of class PersonClient.
     */
    @Test
    public void testLoadByIDWithoutCorrectID() throws Exception {
        System.out.println("testLoadByIDWithoutCorrectID");
        
        Person obj = new Person();
        obj.setID(4);
        
        Person alreadyInList = new Person();
        alreadyInList.setID(1);
        alreadyInList.setEmail("hk1test@tst.co.uk");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        boolean expResult = false;
        boolean result = instance.loadByID(obj);
        assertEquals(expResult, result);
        
        assertFalse(obj.isPopulated());
    }
    
    /**
     * Test of loadByPrimaryKey method, of class PersonClient.
     */
    @Test
    public void testLoadByID() throws Exception {
        System.out.println("testLoadByID");
        
        Person obj = new Person();
        obj.setID(4);
        
        Person alreadyInList = new Person();
        alreadyInList.setID(4);
        alreadyInList.setEmail("hk@doc.oc.uk");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        
        boolean expResult = true;
        boolean result = instance.loadByID(obj);
        assertEquals(expResult, result);
        
        assertEquals(alreadyInList.getEmail(), obj.getEmail());
        assertEquals(alreadyInList.getID(), obj.getID());
        assertEquals(alreadyInList.getFirstName(), obj.getFirstName());
        assertEquals(alreadyInList.getLastName(), obj.getLastName());
        assertTrue(obj.isPopulated());
    }
    
    
    /**
     * Test of loadByPrimaryKey method, of class PersonClient.
     */
    @Test
    public void testLoadMeetingsZeroMeetings() throws Exception {
        System.out.println("testLoadByID");
        
        Person alreadyInList = new Person();
        alreadyInList.setID(4);
        alreadyInList.setEmail("hk@doc.oc.uk");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        
        Meeting meeting = new Meeting();
        meeting.setID(1);
        long start = (new java.util.Date()).getTime();
        meeting.setStartTimeInMilliSeconds(start );
        meeting.setEndTimeInMilliSeconds(start + (60 * 60 * 60 * 1000));
        
        this.mDataProvider.getMeetings().add(meeting);
        
        assertFalse(alreadyInList.getIsMeetingsPopulated());
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        instance.loadMeetings(alreadyInList);
        
        assertTrue(alreadyInList.getIsMeetingsPopulated());
        assertTrue(alreadyInList.getMeetings().isEmpty());
       
    }
    
    /**
     * Test of loadByPrimaryKey method, of class PersonClient.
     */
    @Test
    public void testLoadMeetingsOneMeetings() throws Exception {
        System.out.println("testLoadByID");
        
        Person alreadyInList = new Person();
        alreadyInList.setID(4);
        alreadyInList.setEmail("hk@doc.oc.uk");
        alreadyInList.setFirstName("h");
        alreadyInList.setLastName("k");
        
        this.mDataProvider.getPersons().add(alreadyInList);
        
        Meeting meeting = new Meeting();
        meeting.setID(1);
        long start = (new java.util.Date()).getTime();
        meeting.setStartTimeInMilliSeconds(start );
        meeting.setEndTimeInMilliSeconds(start + (60 * 60 * 60 * 1000));
        
        this.mDataProvider.getMeetings().add(meeting);
        
        PersonMeeting pMeetingLink = new PersonMeeting();
        pMeetingLink.setMetting(meeting);
        pMeetingLink.setPerson(alreadyInList);
        
        this.mDataProvider.getPersonMeetings().add(pMeetingLink);
        
        assertFalse(alreadyInList.getIsMeetingsPopulated());
        
        PersonClient instance = new PersonClient(this.mDataProvider);
        instance.loadMeetings(alreadyInList);
        
        assertTrue(alreadyInList.getIsMeetingsPopulated());
        assertTrue(!alreadyInList.getMeetings().isEmpty());
        
        assertTrue(alreadyInList.getMeetings().size() == 1);
      
    }
    
}
