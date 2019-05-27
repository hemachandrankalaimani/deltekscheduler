/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.entities;

import com.deltek.deltekscheduler.entities.interfaces.IHasID;

/**
 *
 * @author hemachandrankalaimani
 */
public abstract class Entity implements IHasID {

    protected Integer mID;
    protected boolean mIsPopulated;

    public Entity() {
        this.mIsPopulated = false;
    }

    @Override
    public void setIsPopulated(boolean isPopulated) {
        this.mIsPopulated = isPopulated;
    }

    @Override
    public boolean isPopulated() {
        return this.mIsPopulated;
    }

    @Override
    public Integer getID() {
        return this.mID;
    }

    @Override
    public void setID(Integer id) {
        this.mID = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Entity.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Entity other = (Entity) obj;
        return this.getID().equals(other.getID());
    }

    @Override
    public int hashCode() {
        return this.getID();
    }
}
