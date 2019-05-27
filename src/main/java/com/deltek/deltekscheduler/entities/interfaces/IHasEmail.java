/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.entities.interfaces;

/**
 *
 * @author hemachandrankalaimani
 */
public interface IHasEmail {

    /**
     * Gets the email of the person
     *
     * @return
     */
    public String getEmail();

    /**
     * Sets the email of the person
     *
     * @param _email
     */
    public void setEmail(String _email);
}
