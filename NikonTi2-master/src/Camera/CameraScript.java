package Camera;

import mmcorej.CMMCore;
import org.micromanager.acquisition.AcquisitionManager;
import org.micromanager.acquisition.SequenceSettings;
import org.micromanager.internal.MMStudio;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Random;

public class CameraScript implements Runnable {
    //private static AcquisitionData acquisitionData;
    private Dimension dim;
    private Random rand;
    private Robot robot;
    private static CMMCore core;
    private MMStudio gui;
    private volatile boolean stop = false;

    public CameraScript(CMMCore core) {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        rand = new Random();
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        //this.acquisitionData = acquisitionData;
        this.core = core;
    }

    //setting up automated process
    public void run() {
        try {
            core.setXYPosition(8000,6000);
            System.out.println("stage move finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new Thread(() -> {
               AcquisitionManager acquisitionManager = gui.getAcquisitionManager();
               SequenceSettings Settings = acquisitionManager.getAcquisitionSettings();
               acquisitionManager.setAcquisitionSettings(Settings);
               acquisitionManager.runAcquisition();
              }).start();
            System.out.println("camera snap finished");

        } catch (Exception e) {
            System.out.println("camera snap not finished");
            e.printStackTrace();
        }
    }

    public synchronized void stop() {
        stop = true;
    }

    public static void main() throws Exception {
        CameraScript mc = new CameraScript(core);
        Thread mcThread = new Thread(mc);
        System.out.println("Camera Controller start");
        mcThread.start();
        try {
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        mc.stop();
        System.out.println("Camera Controller stopped");
    }
}
