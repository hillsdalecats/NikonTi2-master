import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception{
        Process process = new ProcessBuilder("C:\\Program Files\\NIS-Elements\\nis_ar.exe", "C:\\Users\\qin.luo\\OneDrive - University of Florida\\Microscope Programming Project\\Qin\\Macros\\Test_2.mac").start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;

        System.out.printf("Output of running %s is:", Arrays.toString(args));

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}