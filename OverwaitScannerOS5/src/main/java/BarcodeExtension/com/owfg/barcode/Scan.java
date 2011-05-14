package com.owfg.barcode;

import java.io.IOException;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.script.ScriptableFunction;

import net.rim.device.api.system.EventInjector;
import net.rim.device.api.system.EventLogger;

public final class Scan extends ScriptableFunction {

    public Object invoke(Object obj, Object[] args) throws Exception {
        Player player;
        MainScreen cScreen = new MainScreen();

        //EventLogger.register(MyApp.GUID, MyApp.APP_NAME, EventLogger.VIEWER_STRING);

        try {
            player = Manager.createPlayer("capture://video");
            player.realize();
            player.start();
            VideoControl vc = (VideoControl) player.getControl("VideoControl");
            Field viewFinder = (Field) vc.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, "net.rim.device.api.ui.Field");

            try {
                cScreen.add(viewFinder);
            } catch (IllegalStateException ise) {
                // TODO: Handle this somehow...
                //Logger.logErrorEvent("MyApp() IllegalStateException: " + ise);
            }

            synchronized(UiApplication.getEventLock()) {
                vc.setDisplayFullScreen(true);
                UiApplication.getUiApplication().pushScreen(cScreen);
            }

            ScriptableFunction success = (ScriptableFunction) args[0];
            ScriptableFunction error = (ScriptableFunction) args[1];
            BarcodeScanner scanner = new BarcodeScanner(cScreen, success, error);
            Thread scannerThread = new Thread(scanner);
            scannerThread.start();

        } catch (IOException ioe) {
            // TODO: Handle this somehow...
            //Logger.logErrorEvent("MyApp() IO Exception: " + ioe);
        } catch (MediaException mee) {
            // TODO: Handle this somehow...
            //Logger.logErrorEvent("MyApp() Media Exception: " + mee);
        }

        return UNDEFINED;
    }
}
