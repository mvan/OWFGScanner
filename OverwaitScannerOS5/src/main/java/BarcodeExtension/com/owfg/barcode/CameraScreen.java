package com.owfg.barcode;

import net.rim.device.api.ui.container.MainScreen;

public final class CameraScreen extends MainScreen {
    CameraThread cThread;

    public CameraScreen() {}

    public void startThread() {
      cThread = new CameraThread();
      cThread.start();
    }

    class CameraThread extends Thread {
        Runnable imgTkr;
        CameraThread() {}

        public void run() {
          imgTkr = new ScreenshotThread();
          try {
              sleep(500);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }

          imgTkr.run();
          return;
        }
    }
}
