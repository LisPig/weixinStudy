/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ego.core.net;

/**
 *
 * @author Administrator
 */
public class Param {

    private Object name = null;
    private Object value = null;

    public Param(Object name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
}
