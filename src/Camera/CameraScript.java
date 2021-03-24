package Camera;

import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.scijava.input.KeyCode;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.micromanager.data.Coordinates;

public class CameraScript implements Runnable {
    //private static AcquisitionData acquisitionData;
    private Dimension dim;
    private int rand;
    private Robot robot;
    private static CMMCore core;
    private volatile boolean stop = false;

    public CameraScript(CMMCore core) {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
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
        Random random = new Random();
        robot.delay(500);
        robot.mouseMove(511, 1419);
        robot.delay(200);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        robot.delay(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        robot.delay(1000);
        //capture image
        robot.mouseMove(1361, 437);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        robot.delay(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        DateFormat df = new SimpleDateFormat("mmss");
        String dateandtime = df.format(new Date());
        char[] ch = dateandtime.toCharArray();
        int i_1 = Integer.parseInt(String.valueOf(ch[0]));
        int i_2 = Integer.parseInt(String.valueOf(ch[1]));
        int i_3 = Integer.parseInt(String.valueOf(ch[2]));
        int i_4 = Integer.parseInt(String.valueOf(ch[3]));
        robot.keyPress(KeyEvent.VK_F);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_F);
        if (i_1==0) {
            robot.keyPress(KeyEvent.VK_0);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_0);
        }else if (i_1==1) {
            robot.keyPress(KeyEvent.VK_1);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_1);
        }else if (i_1==2) {
            robot.keyPress(KeyEvent.VK_2);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_2);
        }else if (i_1==3) {
            robot.keyPress(KeyEvent.VK_3);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_3);
        }else if (i_1==4) {
            robot.keyPress(KeyEvent.VK_4);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_4);
        }else if (i_1==5) {
            robot.keyPress(KeyEvent.VK_5);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_5);
        }else if (i_1==6) {
            robot.keyPress(KeyEvent.VK_6);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_6);
        }else if (i_1==7) {
            robot.keyPress(KeyEvent.VK_7);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_7);
        }else if (i_1==8) {
            robot.keyPress(KeyEvent.VK_8);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_8);
        }else if (i_1==9) {
            robot.keyPress(KeyEvent.VK_9);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_9);
        }
        if (i_2==0) {
            robot.keyPress(KeyEvent.VK_0);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_0);
        }else if (i_2==1) {
            robot.keyPress(KeyEvent.VK_1);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_1);
        }else if (i_2==2) {
            robot.keyPress(KeyEvent.VK_2);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_2);
        }else if (i_2==3) {
            robot.keyPress(KeyEvent.VK_3);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_3);
        }else if (i_2==4) {
            robot.keyPress(KeyEvent.VK_4);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_4);
        }else if (i_2==5) {
            robot.keyPress(KeyEvent.VK_5);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_5);
        }else if (i_2==6) {
            robot.keyPress(KeyEvent.VK_6);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_6);
        }else if (i_2==7) {
            robot.keyPress(KeyEvent.VK_7);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_7);
        }else if (i_2==8) {
            robot.keyPress(KeyEvent.VK_8);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_8);
        }else if (i_2==9) {
            robot.keyPress(KeyEvent.VK_9);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_9);
        }
        if (i_3==0) {
            robot.keyPress(KeyEvent.VK_0);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_0);
        }else if (i_3==1) {
            robot.keyPress(KeyEvent.VK_1);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_1);
        }else if (i_3==2) {
            robot.keyPress(KeyEvent.VK_2);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_2);
        }else if (i_3==3) {
            robot.keyPress(KeyEvent.VK_3);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_3);
        }else if (i_3==4) {
            robot.keyPress(KeyEvent.VK_4);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_4);
        }else if (i_3==5) {
            robot.keyPress(KeyEvent.VK_5);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_5);
        }else if (i_3==6) {
            robot.keyPress(KeyEvent.VK_6);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_6);
        }else if (i_3==7) {
            robot.keyPress(KeyEvent.VK_7);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_7);
        }else if (i_3==8) {
            robot.keyPress(KeyEvent.VK_8);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_8);
        }else if (i_3==9) {
            robot.keyPress(KeyEvent.VK_9);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_9);
        }
        if (i_4==0) {
            robot.keyPress(KeyEvent.VK_0);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_0);
        }else if (i_4==1) {
            robot.keyPress(KeyEvent.VK_1);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_1);
        }else if (i_4==2) {
            robot.keyPress(KeyEvent.VK_2);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_2);
        }else if (i_4==3) {
            robot.keyPress(KeyEvent.VK_3);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_3);
        }else if (i_4==4) {
            robot.keyPress(KeyEvent.VK_4);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_4);
        }else if (i_4==5) {
            robot.keyPress(KeyEvent.VK_5);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_5);
        }else if (i_4==6) {
            robot.keyPress(KeyEvent.VK_6);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_6);
        }else if (i_4==7) {
            robot.keyPress(KeyEvent.VK_7);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_7);
        }else if (i_4==8) {
            robot.keyPress(KeyEvent.VK_8);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_8);
        }else if (i_4==9) {
            robot.keyPress(KeyEvent.VK_9);
            robot.delay(20);
            robot.keyRelease(KeyEvent.VK_9);
        }
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        System.out.println("Bright field image " + dateandtime + " captured");
    }

    public synchronized void stop() {
        stop = true;
    }

    public static void main() throws Exception {
        CameraScript mc = new CameraScript(core);
        Thread mcThread = new Thread(mc);
        System.out.println("Started Camera script");
        mcThread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        mc.stop();
        System.out.println("Stopped Camera script");
    }
}


