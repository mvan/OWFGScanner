package com.owfg.webservice;

import java.io.IOException;
import net.rim.device.api.script.ScriptableFunction;
import net.rim.device.api.system.EventInjector;
import net.rim.device.api.system.EventLogger;
import java.lang.Runnable;

public final class Client extends ScriptableFunction {

    public static final long GUID = 0x2051fd67b72d11L;
    public static final String APP_NAME = "WebService Plugin";


    public Object invoke(Object obj, Object[] args) throws Exception {
        EventLogger.register(GUID, APP_NAME, EventLogger.VIEWER_STRING);
        Logger.logErrorEvent("Client.invoke(): invoke");
        Runnable runnable = new WorkerThread(obj, args);
        Thread thread = new Thread(runnable);
        thread.start();
        return UNDEFINED;
    }

}

class WorkerThread implements Runnable {
    public static final String GET_INFO = "getInfo";
    public static final String GET_BANNERS = "getBanners";
    public static final String GET_STORES = "getStores";
    public static final String SET_STORE = "setStore";
    private WebService ws = null;
    private Object obj;
    private Object[] args;
    public WorkerThread(Object obj, Object[] args) {
        this.obj = obj;
        this.args = args;
    }
    public void run(){
        String fnName = (String) args[5];
        ScriptableFunction success = (ScriptableFunction) args[0];
        ScriptableFunction error = (ScriptableFunction) args[1];
        if (ws == null) {
            Logger.logErrorEvent("Client.invoke(): create WS");
            ws = new WebService((String) args[2], (String) args[3], (String) args[4]);
        }

        try {
            if (fnName.equals(GET_INFO)) {
                Logger.logErrorEvent("Client.invoke(): get Info");
                ws.getInfo(success, error, (String) args[6]);
            }
            if (fnName.equals(GET_BANNERS)) {
                Logger.logErrorEvent("Client.invoke(): get banners");
                ws.getBanners(success, error);
            }
            if (fnName.equals(GET_STORES)) {
                Logger.logErrorEvent("Client.invoke(): get stores");
                ws.getStores(success, error);
            }
            if (fnName.equals(SET_STORE)) {
                Logger.logErrorEvent("Client.invoke(): get stores");
                ws.setStore(success, error, (Long) args[6]);
            }
        } catch (Exception e) {
            Logger.logErrorEvent("Exception: Client.Invoke(); (" + e + ") " + e.getMessage());
        }
    }
}
