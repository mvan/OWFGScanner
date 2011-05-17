package com.owfg.webservice;

import net.rim.device.api.script.Scriptable;

public final class WebServiceTools extends Scriptable {
    public static final String FIELD_CLIENT = "query";
    private Client _callClient;

    public WebServiceTools() {
        _callClient = new Client();
    }
    
    public Object getField(String name) throws Exception {
        if(name.equals(FIELD_CLIENT)) {
            return this._callClient;
        }
        return super.getField(name);
    }
}
