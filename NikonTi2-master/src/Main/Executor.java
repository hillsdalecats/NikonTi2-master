package Main;

import Camera.CameraScript;
import Laser.LaserScript;
import mmcorej.CMMCore;
import org.micromanager.internal.MMStudio;
import org.micromanager.acquisition.AcquisitionManager;
import org.micromanager.acquisition.SequenceSettings;
import org.micromanager.internal.SnapLiveManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Executor {
    private AcquisitionData acquisitionData;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private CMMCore core;
    private MMStudio gui;

    public Executor(AcquisitionData acquisitionData, CMMCore core) {
        this.acquisitionData = acquisitionData;
        this.core = core;
    }

    public void execute() throws Exception {
        try {
            // initialize Ti2 Device
            core.loadDevice("XY Stage", "DemoCamera", "DXYStage");
            core.loadDevice("Z Stage", "DemoCamera","DStage");
            core.getProperty("FilterTurret1", "State");
            //core.getProperty("DiaLamp", "State");
            //core.getProperty("DiaLamp", "Intensity");
            //core.getProperty("Nosepiece","State");

            // load camera adapter
            //core.loadDevice("Blackfly S BFS-U3-70S7M", "SpinnakerCamera", "Blackfly S BFS-U3-70S7M");
            //core.setProperty("Blackfly S BFS-U3-70S7M","Serial Number","19034055");
            //core.initializeDevice("Blackfly S BFS-U3-70S7M");
            //core.setCameraDevice("Blackfly S BFS-U3-70S7M");
            //core.setProperty("Blackfly S BFS-U3-70S7M", "Binning",1);

            // initialize
            core.initializeAllDevices();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Runnable endExecutorTask = () -> {
            executorService.shutdown();
        };
        int totalExperimentTime = Integer.parseInt(acquisitionData.totalExperimentTime.getText());
        //int exposure = (Integer.parseInt(acquisitionData.exposureTime.getText()));
        for(int i = 0; i < acquisitionData.pointInformation.size(); i++) {
            double x = Double.parseDouble(acquisitionData.pointInformation.get(i).get(0).getText());
            double y = Double.parseDouble(acquisitionData.pointInformation.get(i).get(1).getText());
            //double maxZ = Double.parseDouble(acquisitionData.pointInformation.get(i).get(2).getText());
            //double minZ = Double.parseDouble(acquisitionData.pointInformation.get(i).get(3).getText());
            //double zStepSize = Double.parseDouble(acquisitionData.pointInformation.get(i).get(4).getText());
            int interval = acquisitionData.timeIntervals.get(i);
            int objective = acquisitionData.objectivename.get(i);
            //int FilterTurret1 = acquisitionData.filter1name.get(i);
            //int FilterTurret2 = acquisitionData.filter2name.get(i);
            //double intensity = Double.parseDouble(acquisitionData.ledintensityInfo.get(i).getText());
            scheduleTaskForAPoint(x, y,interval,totalExperimentTime,objective);
        }
        executorService.schedule(endExecutorTask, totalExperimentTime, TimeUnit.SECONDS);
    }

    private void scheduleTaskForAPoint(double x, double y, int interval, int totalTime, int objective){
        AtomicInteger cnt = new AtomicInteger(1);
        Runnable runnableTask = () -> {
                //Thread.sleep(Integer.parseInt(acquisitionData.exposureTime.getText()));
                //core.setExposure("Blackfly S BFS-U3-70S7M",exposure);
                //core.setProperty("FilterTurret1","State",FilterTurret1-1);
                //core.setProperty("DiaLamp", "State", 1);
                //core.setProperty("DiaLamp", "Intensity", intensity*2100);
               //core.setPosition(z);
            try {
                core.snapImage();
                System.out.println("Bright field image captured");
            } catch (Exception e) {
                System.out.println("Bright field image not captured");
                e.printStackTrace();
            }
            try {
                core.setXYPosition(x,y);
                System.out.println("Moved Microscope Stage to (" + x + ", " + y + ") | " + "time:" + cnt.getAndIncrement() +  " totaltime: " + totalTime + " interval: " + interval);
                //core.setProperty("Nosepiece","State",objective-1);
                //System.out.println("objective changed to:" + objective);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                CameraScript.main();
                LaserScript.main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        executorService.scheduleAtFixedRate(runnableTask, interval, interval, TimeUnit.SECONDS);
    }
}
