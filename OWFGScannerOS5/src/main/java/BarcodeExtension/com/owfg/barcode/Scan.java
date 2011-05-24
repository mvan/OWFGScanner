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

/**
* Class which creates the video stream and creates a BarcodeScanner and ScannerThread
* <p>
* Creates a video stream and adds it to the cScreen. Then creates a new 
* BarcodeScanner Object and a new Scanner Thread and starts the Scanner Thread.
*
* @author Mohamed Sheriffdeen
* @author Tom Nightingale
**/
public final class Scan extends ScriptableFunction {

    public Object invoke(Object obj, Object[] args) throws Exception {
        Player player;
        MainScreen cScreen = new MainScreen();

        try {
            player = Manager.createPlayer("capture://video");
            player.realize();
            player.start();
            VideoControl vc = (VideoControl) player.getControl("VideoControl");
            Field viewFinder = (Field) vc.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, "net.rim.device.api.ui.Field");

            try {
                cScreen.add(viewFinder);
            } catch (Exception e) {
                Logger.logSevereErrorEvent("cScreen.add error: " + e);
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

        } catch (Exception e) {
            Logger.logSevereErrorEvent("Error: " + e);
        }

        return UNDEFINED;
    }
}
