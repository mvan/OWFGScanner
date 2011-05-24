package com.owfg.webservice;

import java.io.IOException;
import net.rim.device.api.script.ScriptableFunction;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.UiApplication;

import java.lang.Runnable;
import java.util.Timer;
import java.util.TimerTask;

public final class Client extends ScriptableFunction {

    public static final long GUID = 0x2051fd67b72d11L;
    public static final String APP_NAME = "WebService Plugin";

    Thread thread;
    public Timer tm;
    TestTimerTask tt;
    ScriptableFunction error;

    public Object invoke(Object obj, Object[] args) throws Exception {
        error = (ScriptableFunction) args[1];
        EventLogger.register(GUID, APP_NAME, EventLogger.VIEWER_STRING);
        Logger.logErrorEvent("Client.invoke(): invoke");
        Runnable runnable = new WorkerThread(obj, args);
        thread = new Thread(runnable);
        timeout();
        thread.start();
        return UNDEFINED;
    }

    private void timeout() {
		tm = new Timer();
		tt = new TestTimerTask();
	    tm.schedule(tt,5000);
        Logger.logErrorEvent("Client.Timeout(): Started");
	}

	private class TestTimerTask extends TimerTask
	{
	    public final void run()
	    {
            synchronized(UiApplication.getEventLock()) {
                thread.interrupt();
            }
            Logger.logErrorEvent("TestTimerTask.run(): timeout hit");
            try {
                String[] str = new String[1];
                str[0] = new String("timeout");
                error.invoke(error, str);
            } catch (Exception e) {
                Logger.logErrorEvent("TestTimerTask.run(): invoke");
            }
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
            Result result = null;
            if (ws == null) {
                Logger.logErrorEvent("Client.invoke(): create WS");
                ws = new WebService((String) args[2], (String) args[3], (String) args[4]);
            }

            try {
                Logger.logErrorEvent("Client() function called: " + fnName);
                if (fnName.equals(GET_INFO)) {
                    Logger.logErrorEvent("Client.invoke(): get Info");
                    result = ws.getInfo(success, error, (String) args[6]);
                }
                if (fnName.equals(GET_BANNERS)) {
                    Logger.logErrorEvent("Client.invoke(): get banners");
                    result = ws.getBanners(success, error);
                }
                if (fnName.equals(GET_STORES)) {
                    Logger.logErrorEvent("Client.invoke(): get stores");
                    result = ws.getStores(success, error);
                }
                if (fnName.equals(SET_STORE)) {
                    String storeIDstr = (String) args[6];
                    Long storeID = new Long(Long.parseLong(storeIDstr));
                    Logger.logErrorEvent("Client.invoke(): get stores. (StoreID: " + storeID + ")");
                    ws.setStore(success, error, storeID);
                }
            } catch (Exception e) {
                Logger.logErrorEvent("Exception: Client.Invoke(); (" + e + ") " + e.getMessage());
            }
            synchronized(UiApplication.getEventLock()) {
                tm.cancel();
            }
            if (result != null) {
                try {
                    result.fn.invoke(result.fn, result.obj);
                } catch (Exception e) {
                    Logger.logErrorEvent("Invoke()");
                }
            }
        }
    }
}

