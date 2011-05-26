package com.owfg.webservice;

import net.rim.device.api.script.ScriptableFunction;

/**
 * Used to return a callback and the objects used in callback to javascript
 * @author Warren Voelkl
 */
public class Result {
    ScriptableFunction fn;
    Object[] obj;
    Result(ScriptableFunction fn, Object[] obj) {
        this.fn = fn;
        this.obj = obj;
    }
}
