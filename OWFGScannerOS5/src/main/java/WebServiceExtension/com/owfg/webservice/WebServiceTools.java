package com.owfg.webservice;

import net.rim.device.api.script.Scriptable;

/**
 * Webworks required file used to call the Client class from javascript
 */
public final class WebServiceTools extends Scriptable {
    public static final String FIELD_QUERY = "query";
    private Client _callQuery;

    public WebServiceTools() {
        _callQuery = new Client();
    }
    
    public Object getField(String name) throws Exception {
        if(name.equals(FIELD_QUERY)) {
            return this._callQuery;
        }
        return super.getField(name);
    }
}
