package com.owfg.webservice;

import net.rim.device.api.script.ScriptableFunction;

/**
 * Created by IntelliJ IDEA.
 * User: Waz
 * Date: 5/24/11
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Result {
    ScriptableFunction fn;
    Object[] obj;
    Result(ScriptableFunction fn, Object[] obj) {
        this.fn = fn;
        this.obj = obj;
    }
}
