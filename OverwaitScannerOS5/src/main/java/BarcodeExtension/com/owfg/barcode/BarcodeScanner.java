package com.owfg.barcode;

import java.util.Hashtable;
import java.util.Vector;

import net.rim.device.api.script.ScriptableFunction;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.oned.MultiFormatOneDReader;

public class BarcodeScanner implements Runnable {
    MainScreen cameraScreen;
    ScriptableFunction successCallback;
    ScriptableFunction errorCallback;

    BarcodeScanner(MainScreen cScreen, ScriptableFunction success, ScriptableFunction error) {
        cameraScreen = cScreen;
        successCallback = success;
        errorCallback = error;
    }

    public void run() {
        Reader reader;
        String[] args = new String[1];
        int scans = 500;
        Result result = null;
        Hashtable hints = new Hashtable(1);
        Vector readerHints = new Vector(4);
        Bitmap bitmap = new Bitmap(Display.getWidth(), Display.getHeight());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        readerHints.addElement(BarcodeFormat.UPC_A);
        readerHints.addElement(BarcodeFormat.UPC_E);
        readerHints.addElement(BarcodeFormat.CODE_128);
        readerHints.addElement(BarcodeFormat.ITF);

        hints.put(DecodeHintType.POSSIBLE_FORMATS, readerHints);
        reader = new MultiFormatOneDReader(hints);

        while (true) {
            Display.screenshot(bitmap);
            LuminanceSource source = new CustomBitmapLuminanceSource(bitmap);
            BinaryBitmap bitmap1 = new BinaryBitmap(new GlobalHistogramBinarizer(source));
            try {
                result = reader.decode(bitmap1);
            } catch (ReaderException e) {
                //Logger.logDebugEvent("ScreenshotThread.run(): " + e);
                //return;
            }
            if (result != null) {
                args[0] = result.getText();
                try {
                    successCallback.invoke(successCallback, args);
                } catch(Exception e) {
                    // TODO: Handle this somehow...
                }
                break;
            } else {
                if (scans-- == 0) {
                    args[0] = "BarcodeScanner: Timeout.";
                    try {
                        errorCallback.invoke(errorCallback, args);
                    } catch(Exception e) {
                        // TODO: Handle this somehow...
                    }
                    break;
                }
                continue;
            }
        }

        // No more need for this.
        synchronized(UiApplication.getEventLock()) {
            UiApplication.getUiApplication().popScreen(cameraScreen);
        }
    }
}
