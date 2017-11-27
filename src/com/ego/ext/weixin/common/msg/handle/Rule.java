/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ego.ext.weixin.common.msg.handle;

import com.ego.ext.weixin.common.msg.Msg;

/**
 *@deprecated 
 * @author Administrator
 */
public class Rule<T extends Msg> {

    protected boolean test(T fromMsg) {
        return true;
    }
}
