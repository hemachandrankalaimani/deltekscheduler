/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.exceptions;

/**
 *
 * @author hemachandrankalaimani
 */
public class DuplicateException extends Exception {

    public DuplicateException(Exception sqle) {
        super(sqle);
    }

    public DuplicateException(String s) {
        super(s);
    }
}
