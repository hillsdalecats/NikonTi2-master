package Laser;

import mmcorej.CMMCore;
import org.micromanager.internal.MMStudio;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;
import java.util.Set;

import static com.sun.jna.platform.win32.Kernel32Util.deleteFile;

public class LaserScript implements Runnable {
    //private static AcquisitionData acquisitionData;
    private Dimension dim;
    private Random rand;
    private Robot robot;
    private static CMMCore core;
    private MMStudio gui;
    private volatile boolean stop = false;

    public LaserScript(CMMCore core) {
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
           // robot.delay(200);
            robot.delay(1000);
            //robot.keyPress(KeyEvent.VK_WINDOWS);
            //robot.keyPress(KeyEvent.VK_1);
            //robot.keyRelease(KeyEvent.VK_1);
            //robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.mouseMove(100, 1000);
            robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_F4);
            robot.keyRelease(KeyEvent.VK_F4);
            robot.delay(200);
            //robot.keyPress(KeyEvent.VK_WINDOWS);
            //robot.keyPress(KeyEvent.VK_1);
            //robot.keyRelease(KeyEvent.VK_1);
            //robot.keyRelease(KeyEvent.VK_WINDOWS);
            //boolean run = true;
            //while (run) {
               // String a = null;
               // try {
               //     a = core.getProperty("FilterTurret1", "State");
               // } catch (Exception e) {
               //     e.printStackTrace();
              //  }
              //  if (a.equals("1")) {
               //     try {
               //         core.setProperty("FilterTurret1", "State", 0);
               //    } catch (Exception e) {
                //        e.printStackTrace();
                //    }
                 //   System.out.println("finished");
                 //   run = false;
               // } else {
                //    try {
                 //       Thread.sleep(200);
                 //       System.out.println("waiting");
                  //  } catch (InterruptedException ie) {
                //    }
               // }
           // }
    }

    public synchronized void stop() {
        stop = true;
    }

    public static void main() throws Exception {
        LaserScript mc = new LaserScript(core);
        Thread mcThread = new Thread(mc);
        mcThread.start();
        System.out.println("Started Elements script");
        try {
            Thread.sleep(150000);
        //    Thread.sleep(60000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        mc.stop();
        System.out.println("Stopped Elements script");
        //mcThread.start();
        //System.out.println("Started laser script");
        //String filepath = "D:\\Tang Lab\\Qin\\Report";
        //boolean contains = false;
        //while(!contains){
            //File file = new File(filepath);
            //File[] array = file.listFiles();
            //String [] fileName = file.list();
            //for(int i=0;i<array.length;i++){
                //if(fileName != null){
                    //contains = true;
                    //mc.stop();
                    //System.out.println("Stopped laser script");
                    //deleteFile("D:\\Tang Lab\\Qin\\Report\\report.rpt");
                    //System.out.println("Deleted report");
              //  }
           // }
       // }
        //Thread.sleep(20000);
        //mc.stop();
        //System.out.println("Script stopped");
    }
}
