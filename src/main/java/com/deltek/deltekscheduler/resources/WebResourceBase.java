/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.resources;

import com.deltek.deltekscheduler.application.*;
import com.deltek.deltekscheduler.data.InMemoryDataProvider;

/**
 *
 * @author hemachandrankalaimani
 */
public class WebResourceBase {

    /**
     * Returns the data provider to be used Replace this one with Mysql client
     * if needed
     *
     * @return
     */
    protected InMemoryDataProvider getDataProvider() {
        return ApplicationDataSource.getApplicationDataSource();
    }
}
