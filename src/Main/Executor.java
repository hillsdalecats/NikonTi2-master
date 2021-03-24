package Main;


import Laser.LaserScript;
import mmcorej.CMMCore;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.util.ArrayList;
        import java.util.concurrent.Executors;
        import java.util.concurrent.Future;
        import java.util.concurrent.ScheduledExecutorService;
        import java.util.concurrent.TimeUnit;
        import java.util.concurrent.atomic.AtomicInteger;

public class Executor {
    private AcquisitionData acquisitionData;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private CMMCore core;

    private JFrame pauseResumeFrame = new JFrame();
    private JButton pauseButton = new JButton("Pause");
    private JButton resumeButton = new JButton("Resume");
    private boolean run = true;

    public Executor(AcquisitionData acquisitionData, CMMCore core){
        this.acquisitionData = acquisitionData;
        this.core = core;
        pauseResumeFrame.setSize(600, 300);
        JPanel buttonPanel = new JPanel(new GridLayout(0,2));
        pauseButton.addActionListener(e -> pauseButtonPerformed(e));
        resumeButton.addActionListener(e -> resumeButtonPerformed(e));
        buttonPanel.add(pauseButton);
        buttonPanel.add(resumeButton);
        pauseResumeFrame.add(buttonPanel);
        pauseResumeFrame.setVisible(true);
    }

    public void execute(){
        try {
            core.loadDevice("XY Stage", "DemoCamera", "DXYStage");
            //core.setAutoFocusDevice("PFS");
            core.loadDevice("Z Stage", "DemoCamera","DStage");
            //core.getProperty("DiaLamp", "State");
            //core.getProperty("DiaLamp", "Intensity");
            //core.getProperty("Nosepiece","State");

            // load camera adapter
            /*core.loadDevice("Blackfly S BFS-U3-70S7M", "SpinnakerCamera", "Blackfly S BFS-U3-70S7M");
            core.setProperty("Blackfly S BFS-U3-70S7M","Serial Number","19034055");
            core.initializeDevice("Blackfly S BFS-U3-70S7M");
            core.setCameraDevice("Blackfly S BFS-U3-70S7M");
            core.getProperty("Blackfly S BFS-U3-70S7M", "Binning");*/

            core.initializeAllDevices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runnable endExecutorTask = () -> {
            executorService.shutdown();
        };
        int totalExperimentTime = acquisitionData.totalExperimentTime;
        for(int i = 0; i < acquisitionData.pointInformation.size(); i++) {
            //ArrayList<String> laserSelectionForPoint = acquisitionData.laserSelections.get(i);
            double x = acquisitionData.pointInformation.get(i).get(0);
            double y = acquisitionData.pointInformation.get(i).get(1);
            //double maxZ = acquisitionData.pointInformation.get(i).get(2);
            //double minZ = acquisitionData.pointInformation.get(i).get(3);
            //double zStepSize = acquisitionData.pointInformation.get(i).get(4);
            int startTime = acquisitionData.timeIntervals.get(i).get(0);
            int interval = acquisitionData.timeIntervals.get(i).get(1);
            //int objective = acquisitionData.objectiveSelections.get(i);
            //int filterTurret1 = acquisitionData.filter1name.get(i);
            //int filterTurret2 = acquisitionData.filter2name.get(i);
            //double intensity = acquisitionData.ledSelections.get(i);
            //for(double z = maxZ; z >= minZ; z = z - zStepSize){
            scheduleTaskForAPoint(x, y, startTime,interval, totalExperimentTime);
            //}
        }
        executorService.schedule(endExecutorTask, totalExperimentTime, TimeUnit.SECONDS);
    }
    private void scheduleTaskForAPoint(double x, double y, int startTime, int interval, int totalTime){
        AtomicInteger cnt = new AtomicInteger(1);
        Runnable runnableTask = () -> {
            try {
                /*for(String laserSelection : laserSelectionForPoint){
                    switch (laserSelection){
                        case " Violet (405 nm) ":
                            core.setStateLabel("Dev1", "1");
                            Thread.sleep(acquisitionData.exposureTime);
                            break;
                        case " Blue (488 nm) ":
                            core.setStateLabel("Dev1", "2");
                            Thread.sleep(acquisitionData.exposureTime);
                            break;
                        case " Yellow (561 nm) ":
                            core.setStateLabel("Dev1", "4");
                            Thread.sleep(acquisitionData.exposureTime);
                            break;
                        case " Orange (640 nm) ":
                            core.setStateLabel("Dev1", "8");
                            Thread.sleep(acquisitionData.exposureTime);
                            break;
                        default:
                            core.setStateLabel("Dev1", "0");
                            Thread.sleep(acquisitionData.exposureTime);
                            break;
                    }
                }
                core.setExposure("Blackfly S BFS-U3-70S7M", acquisitionData.exposureTime);
                core.setProperty("Nosepiece","State",objective-1);
                core.setProperty("FilterTurret1","State",filterTurret1-1);
                core.setProperty("FilterTurret2","State",filterTurret2-1);
                core.setProperty("DiaLamp", "State", 1);
                core.setProperty("DiaLamp", "Intensity", intensity*2100);
                Thread.sleep(acquisitionData.exposureTime);
                core.setProperty("DiaLamp", "State", 0);
                */
                //call the macro, tell elements to run the macro
                pauseResume();
                core.setXYPosition(x,y);
                System.out.println("Moved Microscope Stage to (" + x + ", " + y + ") | " + "time:" + cnt.getAndIncrement() +  " totaltime: " + totalTime + " interval: " + interval);
                //core.setPosition(z);
                //Thread.sleep(acquisitionData.exposureTime);
                //core.snapImage();
                //CameraScript.main();
               LaserScript.main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        executorService.scheduleAtFixedRate(runnableTask, startTime, interval, TimeUnit.SECONDS);
    }

    public void pauseButtonPerformed(ActionEvent e){
        run = false;
    }

    public void resumeButtonPerformed(ActionEvent e){
        run = true;
    }

    public void pauseResume(){
        while(!run){
            try{
                Thread.sleep(100);
            } catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }
    }
}