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
public interface IHasID {

    /**
     * Gets the unique Id of th object
     *
     * @return
     */
    public Integer getID();

    /**
     * sets the unique id of the object
     *
     * @param id
     */
    public void setID(Integer id);

    /**
     * Sets if the object is populated
     *
     * @param isPopulated
     */
    public void setIsPopulated(boolean isPopulated);

    /**
     * Gets if the object is populated
     *
     * @return
     */
    public boolean isPopulated();

}
