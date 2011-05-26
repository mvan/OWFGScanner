package com.owfg.webservice;

import java.io.IOException;
import net.rim.device.api.script.ScriptableFunction;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.UiApplication;

import java.lang.Runnable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Recieves function calls from javascript side and calls appropriate java function
 *
 * This class does two things it reads from the javascript function call.  In addition
 * It creates a worker thread for the network access and handles its own timeout.
 * Then it invokes the appropriate call back depending on success or timeout of the network
 * call.
 * @author Warren Voelkl
 **/
public final class Client extends ScriptableFunction {

    public static final long GUID = 0x2051fd67b72d11L;
    public static final String APP_NAME = "WebService Plugin";
    protected final int TIMEOUT = 7500;
    Thread thread;

    ScriptableFunction error;

    /**
     * The constructor creates a worker thread.
     * @param obj length of args
     * @param args objects recieved from javascript
     * @return used by webworks
     */
    public Object invoke(Object obj, Object[] args) {
        error = (ScriptableFunction) args[1];
        EventLogger.register(GUID, APP_NAME, EventLogger.VIEWER_STRING);
        Logger.logErrorEvent("Client.invoke(): invoke");
        Runnable runnable = new WorkerThread(obj, args);
        thread = new Thread(runnable);

        thread.start();
        return UNDEFINED;
    }

    /**
     * The class where the web service is called
     * In addition this class parses the javascript call and sets a
     * timeout which terminates this thread and calls a callback
     * @author Warren Voelkl
     */
    class WorkerThread implements Runnable {
        public Timer tm;
        TestTimerTask tt;
        public static final String GET_INFO = "getInfo";
        public static final String GET_BANNERS = "getBanners";
        public static final String GET_STORES = "getStores";
        public static final String SET_STORE = "setStore";
        public static final String GET_HISTORY = "getHistory";
        private WebService ws = null;
        private Object obj;
        private Object[] args;
        public WorkerThread(Object obj, Object[] args) {
            this.obj = obj;
            this.args = args;
        }

        /**
         * runs this thread.
         */
        public void run(){
            String fnName = (String) args[5];
            ScriptableFunction success = (ScriptableFunction) args[0];
            ScriptableFunction error = (ScriptableFunction) args[1];
            Result result = null;
            timeout();
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
                if (fnName.equals(GET_HISTORY)) {
                    Logger.logErrorEvent("Client.invoke(): get history");
                    result = ws.getHistory(success, error, (String) args[6]);
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

        /**
         * Sets up time timeout
         */
        private void timeout() {
            tm = new Timer();
            tt = new TestTimerTask();
            tm.schedule(tt,TIMEOUT);
            Logger.logErrorEvent("Client.Timeout(): Started");
        }

        /**
         * the object called when/if timeout expires.
         * calls the error callback if this class is activated by timer.
         * @author Warren Voelkl
         */
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
                    str[0] = "timeout";
                    error.invoke(error, str);
                } catch (Exception e) {
                    Logger.logErrorEvent("TestTimerTask.run(): invoke");
                }
            }
        }
    }
}

