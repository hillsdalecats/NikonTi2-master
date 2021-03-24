package Main;

import Camera.CameraConfig;
import LED.LEDConfig;
import Objective.ObjectiveConfig;
import Filter.Filter_config;
import Laser.LaserConfig;
import XYStage.TimeConfig;
import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.acquisition.AcquisitionManager;
import org.micromanager.acquisition.SequenceSettings;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.logging.LogFileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainInterface{
    //Initializing all core components of the class that will allow for manipulation of the microscope
    private CMMCore core;
    private MMStudio gui;
    private FileHandler fh;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    //Initializing additional configuration classes to allow to more personalized configurations
    private AcquisitionData acquisitionData = new AcquisitionData();
    private Laser.LaserConfig LaserConfig = new LaserConfig(acquisitionData);
    private boolean laserConfigShown = false;
    private TimeConfig timeConfig = new TimeConfig(acquisitionData);
    private boolean timeConfigShown = false;
    private LEDConfig ledtimeConfig = new LEDConfig(acquisitionData);
    private boolean ledtimeConfigShown = false;
    private ObjectiveConfig objectiveConfig = new ObjectiveConfig(acquisitionData);
    private boolean objectiveConfigshown = false;
    private Filter_config filterConfig = new Filter_config(acquisitionData);
    private boolean Filter_configshown = false;
    private CameraConfig cameraConfig = new CameraConfig();
    private boolean cameraConfigshown = false;

    //Initializing final entry class
    private Executor executor;


    //Initializing all components necessary to setup the main frame.
    private JFrame mainFrame = new JFrame("Main Interface");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane pointInfoEntryPanel = new JScrollPane(tablePanel);
    private JList<String> laserSelection;


    public void setupMainInterface(Studio app) throws Exception {
        //Setting up behavior of the main frame
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1400,500);

        //Setting up core components
        gui = (MMStudio) app;
        core = gui.getCMMCore();
        executor = new Executor(acquisitionData, core);

        //Setting up title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Please Enter Information Below");
        titlePanel.add(titleLabel);

        //Setting up current position


        //Calling function to set up information entry panel
        setupPointInfoEntryPanel();

        //Setting up final entry button
        JPanel finalEntryPanel = new JPanel();
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> {
            try {
                enterButtonPerformed(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        finalEntryPanel.add(enterButton);

        //Setting up configuration button panel
        JPanel configurationButtonPanel = new JPanel(new GridLayout(0,1));
        double xposition = core.getXPosition();
        double yposition = core.getYPosition();
        JLabel currentpositionLabel = new JLabel("Current Position:"+"(X,Y)"+"=("+xposition+", "+yposition+")",SwingConstants.CENTER);
        JButton addPointButton = new JButton("Add Point");
        addPointButton.addActionListener(e -> addPointButtonPerformed(e));
        JButton removePointButton = new JButton("Remove Point");
        removePointButton.addActionListener(e -> removePointButtonPerformed(e));
        JButton laserInputButton = new JButton("Additional Laser Configurations");
        laserInputButton.addActionListener(e -> laserInputButtonPerformed(e));
        JButton timeInputButton = new JButton("Additional Time Configurations");
        timeInputButton.addActionListener(e -> TimeInputButtonPerformed(e));
        JButton LEDInputButton = new JButton("DiaLamp Configuration");
        LEDInputButton.addActionListener(e->  LEDInputButtonPerformed(e));
        JButton ObjectiveButton = new JButton("Objective Configuration");
        ObjectiveButton.addActionListener(e->  ObjectiveButtonPerformed(e));
        JButton FilterButton = new JButton("Filter Configuration");
        FilterButton.addActionListener(e->  FilterButtonPerformed(e));
        configurationButtonPanel.add(currentpositionLabel);
        configurationButtonPanel.add(addPointButton);
        configurationButtonPanel.add(removePointButton);
        configurationButtonPanel.add(laserInputButton);
        configurationButtonPanel.add(timeInputButton);
        configurationButtonPanel.add(LEDInputButton);
        configurationButtonPanel.add(ObjectiveButton);
        configurationButtonPanel.add(FilterButton);

        //Setting up 0n/off button
        JPanel Filter1Panel = new JPanel();
        JToggleButton on_off_1Button = new JToggleButton("Filter1 Switch");
        Filter1Panel.add(on_off_1Button);
        on_off_1Button.addChangeListener(event -> {
            if (on_off_1Button.isSelected()){
                on_off_1Button.setText("TurretShutter is  ON");
                try {
                    core.setProperty("Turret1Shutter", "State", "1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                on_off_1Button.setText("TurretShutter is OFF");
                try {
                    core.setProperty("Turret1Shutter", "State", "0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JPanel Filter2Panel = new JPanel();
        JToggleButton on_off_2Button = new JToggleButton("Filter2 Switch");

        Filter2Panel.add(on_off_2Button);
        on_off_2Button.addChangeListener(event -> {
            if (on_off_2Button.isSelected()){
                on_off_2Button.setText("Filter2 is  ON");
                try {
                    core.setProperty("Turret2Shutter", "State", "1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                on_off_2Button.setText("Filter2 is OFF");
                try {
                    core.setProperty("Turret2Shutter", "State", "0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //Setting up LED button
        JPanel LEDPanel = new JPanel();
        JToggleButton LEDButton = new JToggleButton("DiaLamp Switch");

        LEDPanel.add(LEDButton);
        LEDButton.addChangeListener(event -> {
            if (LEDButton.isSelected()){
                LEDButton.setText("DiaLamp is ON ");
                try {
                    core.setProperty("DiaLamp", "State", "1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                LEDButton.setText("DiaLamp is OFF");
                try {
                    core.setProperty("DiaLamp", "State", "0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JPanel SwitchConfigurations = new JPanel(new GridBagLayout());
        JLabel SwitchLabel = new JLabel("Microscope Shutter Switch");
        constraints.gridy = 0;
        SwitchConfigurations.add(SwitchLabel, constraints);
        constraints.gridy = 1;
        SwitchConfigurations.add(Filter1Panel, constraints);
        constraints.gridy = 2;
        SwitchConfigurations.add(LEDPanel, constraints);
        //constraints.gridy = 1;
        //SwitchConfigurations.add(Filter2Panel, constraints);

        //Setting up input panel for time and laser configurations
        JPanel timeLaserConfigurations = new JPanel(new GridBagLayout());
        acquisitionData.setUpUniversalTextFields();
        //String[] laserConfigurations = {" Laser 1 ", " Laser 2 ", " Laser 3 ", " Laser 4 "};
        //laserSelection = new JList(laserConfigurations);
        //laserSelection.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //laserSelection.setLayoutOrientation(JList.VERTICAL_WRAP);
        //laserSelection.setVisibleRowCount(-2);
        //JScrollPane laserSelectionPane =  new JScrollPane(laserSelection);
        // constraints.gridx = 0;
        //constraints.gridy = 0;
        //constraints.gridwidth = 2;
        //timeLaserConfigurations.add(acquisitionData.timeIntervalBetweenShots, constraints);
        constraints.gridy = 0;
        timeLaserConfigurations.add(acquisitionData.totalExperimentTime, constraints);
        constraints.gridy = 1;
        timeLaserConfigurations.add(acquisitionData.exposureTime, constraints);
        //constraints.gridx = 2;
        //constraints.gridy = 0;
        // constraints.gridwidth = 1;
        //timeLaserConfigurations.add(acquisitionData.unitsForInterval, constraints);
        constraints.gridy = 0;
        timeLaserConfigurations.add(acquisitionData.unitsForExperimentTime, constraints);

        //constraints.gridx = 0;
        //constraints.gridy = 3;
        //constraints.gridwidth = 3;
        //timeLaserConfigurations.add(laserSelectionPane, constraints);


        //Adding all components onto main frame
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 1;
        mainFrame.getContentPane().add(titlePanel, constraints);

        constraints.gridy = 3;
        mainFrame.getContentPane().add(finalEntryPanel, constraints);

        constraints.gridy = 0;
        constraints.gridx = 3;
        mainFrame.getContentPane().add(SwitchConfigurations, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(10,10,10,10);
        mainFrame.getContentPane().add(configurationButtonPanel, constraints);



        constraints.gridy = 2;
        mainFrame.getContentPane().add(timeLaserConfigurations, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.6;
        constraints.weightx = 0.6;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;

        mainFrame.getContentPane().add(pointInfoEntryPanel, constraints);

        mainFrame.setVisible(true);
    }

    private void ObjectiveButtonPerformed(ActionEvent e) {
        if(objectiveConfigshown == false) {
            objectiveConfig.setUpObjectiveConfigInterface();
            objectiveConfigshown = true;
        }else{
            objectiveConfig.redisplayWindow();
        }
    }

    private void FilterButtonPerformed(ActionEvent e) {
        if(Filter_configshown == false) {
            filterConfig.setUpFilterConfigInterface();
            Filter_configshown = true;
        }else{
            filterConfig.redisplayWindow();
        }
    }


    private void LEDInputButtonPerformed(ActionEvent e){
        if(ledtimeConfigShown == false) {
            ledtimeConfig.setUpLEDTimeConfigInterface();
            ledtimeConfigShown = true;
        } else{
            ledtimeConfig.redisplayWindow();
        }
    }

    //Setting up point information entry panel
    private void setupPointInfoEntryPanel(){
        for(int i = 0; i < 5; i++){
            addRowToTable();
        }
    }

    //Setting up helper method to add a row to the info entry table
    private void addRowToTable(){
        JPanel singleRowPanel = new JPanel();
        ArrayList<JTextField> textFields = new ArrayList<JTextField>();
        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        labels.add(new JLabel("X-Coordinate:"));
        labels.add(new JLabel("Y-Coordinate:"));
        labels.add(new JLabel("Z-Coordinate Max:"));
        labels.add(new JLabel("Z-Coordinate Min:"));
        labels.add(new JLabel("Z Step Size:"));
        for(int i = 0; i < 5; i++){
            textFields.add(new JTextField(9));
            singleRowPanel.add(labels.get(i));
            singleRowPanel.add(textFields.get(i));
        }
        JLabel pointLabel = new JLabel("Point"+String.valueOf(acquisitionData.pointInformation.size()+1));
        tablePanel.add(singleRowPanel);
        singleRowPanel.add(pointLabel);
        acquisitionData.pointInformation.add(textFields);
    }

    //Setting up helper method to update the info entry table and corresponding arraylists
    private void updateTable(){
        if(tablePanel.getComponentCount() > acquisitionData.pointInformation.size()){
            tablePanel.remove(tablePanel.getComponentCount() - 1);
        }
        pointInfoEntryPanel.revalidate();
        pointInfoEntryPanel.repaint();
    }

    //Setting up behavior for when enter button is clicked
    private void enterButtonPerformed(ActionEvent e) throws Exception {
        acquisitionData.saveFinalConfigs();
        acquisitionData.updateledArrays();
        acquisitionData.updateObjectiveArrays();
        //new Thread(() -> {
        //    AcquisitionManager acquisitionManager = gui.getAcquisitionManager();
        //    SequenceSettings Settings = acquisitionManager.getAcquisitionSettings();
        //    acquisitionManager.setAcquisitionSettings(Settings);
        //    acquisitionManager.runAcquisition();
        //}).start();
        //gui.getSnapLiveManager();
        executor.execute();
    }


    //Setting up behavior for when time configuration button is clicked
    private void addPointButtonPerformed(ActionEvent e) {
        addRowToTable();
        updateTable();
    }

    //Setting up behavior for when time configuration button is clicked
    private void removePointButtonPerformed(ActionEvent e) {
        acquisitionData.pointInformation.remove(acquisitionData.pointInformation.size() - 1);
        updateTable();
    }



    //Setting up behavior for when time configuration button is clicked
    private void laserInputButtonPerformed(ActionEvent e) {
        if(laserConfigShown == false) {
            LaserConfig.setUpLaserConfigInterface();
            laserConfigShown = true;
        } else{
            LaserConfig.redisplayWindow();
        }
    }

    //Setting up behavior for when time configuration button is clicked
    private void TimeInputButtonPerformed(ActionEvent e) {
        if(timeConfigShown == false) {
            timeConfig.setUpTimeConfigInterface();
            timeConfigShown = true;
        } else{
            timeConfig.redisplayWindow();
        }
    }

    public void setupLogger(){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd_HHmmss");
        try {
            String dirname = LogFileManager.getLogFileDirectory().getAbsolutePath();
            fh = new FileHandler(dirname + "/" + this.getClass().getName() + format.format(Calendar.getInstance().getTime()) + ".log");
        } catch (Exception e) {
            e.printStackTrace();
        }
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }
}
