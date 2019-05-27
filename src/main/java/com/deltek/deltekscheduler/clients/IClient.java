/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.clients;

import com.deltek.deltekscheduler.exceptions.*;
import java.util.Collection;

/**
 * This interface is an abstract means of adding, updating, retrieving data for
 * various entities By using generic it can be used or sub-classed for any
 * entity
 *
 * @author hemachandrankalaimani
 * @param <T>
 */
public interface IClient<T> {

    /**
     * loads the object from the database using the primary key which has been
     * set within the object
     *
     * @param obj the object to load from the database
     * @return if the object has been loaded successfully
     * @throws com.deltek.deltekscheduler.exceptions.ClientException
     */
    public boolean loadByPrimaryKey(T obj) throws ClientException;

    /**
     * loads all rows from the database into a collection of objects
     *
     * @param list the list that will be populated with objects loaded from the
     * database
     * @throws com.deltek.deltekscheduler.exceptions.ClientException if an error
     * occurs
     */
    public void loadAll(Collection<T> list) throws ClientException;

    /**
     * adds a new record to the database
     *
     * @param obj the object containing the fields to be written to the
     * database, with primary KEY set to null
     * @return the object with primary key set
     * @throws com.deltek.deltekscheduler.exceptions.ClientException if an error
     * occurs
     * @throws com.deltek.deltekscheduler.exceptions.DuplicateException if there
     * is a duplicate
     *
     *
     */
    public T add(T obj) throws ClientException, DuplicateException;
}
