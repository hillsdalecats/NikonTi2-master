package Main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;

public class runExternalSoftware {
    //private static CMMCore core;
    //private static MMStudio gui;
    public static void main() throws Exception{
        //gui = (MMStudio) app;
        //core = gui.getCMMCore();
        Process process = new ProcessBuilder("C:\\Program Files\\NIS-Elements\\nis_ar.exe", "-mw", "C:\\Users\\qin.luo\\OneDrive - University of Florida\\Microscope Programming Project\\Qin\\Macros\\Test_2.mac").start();
        //process.waitFor();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        //core.setXYPosition(2000,3000);
        //System.out.printf("finished");
        //String line;

        //System.out.printf("Output of running %s is:", Arrays.toString(args));

        //while ((line = br.readLine()) != null) {
        //    System.out.println(line);
        //}
    }
}