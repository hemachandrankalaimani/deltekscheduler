/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.datacontracts;

import com.deltek.deltekscheduler.entities.Person;
import com.deltek.deltekscheduler.entities.interfaces.IPerson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author hemachandrankalaimani
 */
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDataContract {

    @XmlElement
    protected Integer id;

    @XmlElement(required = true)
    protected String firstName;

    @XmlElement(required = true)
    protected String lastName;

    @XmlElement(required = true)
    protected String email;

    public PersonDataContract() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer personid) {
        this.id = personid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String personFirstName) {
        this.firstName = personFirstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String personLastName) {
        this.lastName = personLastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String personEmail) {
        this.email = personEmail;
    }

    /**
     * Converts the person object to person data contract
     *
     * @param p
     * @return
     */
    public static PersonDataContract getContract(IPerson p) {
        PersonDataContract retVal = new PersonDataContract();

        retVal.setEmail(p.getEmail());
        retVal.setId(p.getID());
        retVal.setFirstName(p.getFirstName());
        retVal.setLastName(p.getLastName());

        return retVal;
    }

    /**
     * Gets the person entity representation of the contract
     *
     * @return
     */
    public IPerson toPersonEntity() {

        Person retVal = new Person();

        retVal.setID(getId());
        retVal.setEmail(getEmail());
        retVal.setFirstName(getFirstName());
        retVal.setLastName(getLastName());

        return retVal;
    }
}
