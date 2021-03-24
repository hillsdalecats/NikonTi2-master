package Main;

import Filter.FilterConfig;
import Filter.FilterConfig1;
import LED.LEDConfig;
import LED.LEDConfig1;
import Laser.LaserConfig;
import LightPath.LightPathConfig;
import Objective.ObjectiveConfig;
import Objective.ObjectiveConfig1;
import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.logging.LogFileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainInterfaceTest{
    //Initializing all core components of the class that will allow for manipulation of the microscope
    private CMMCore core;
    private MMStudio gui;
    private FileHandler fh;
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    //Initializing main data acquisition class
    private AcquisitionData acquisitionData = new AcquisitionData();

    //Initializing final entry class
    private Executor executor;

    //Initializing all components necessary to setup the main frame.
    private JFrame mainFrame = new JFrame("Confocal Microscope Automation");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane pointInfoEntryPanel = new JScrollPane(tablePanel);
    private JList<String> laserSelection;
    private ArrayList<ArrayList<JTextField>> pointInformation = new ArrayList<ArrayList<JTextField>>();
    private JTextField timeIntervalBetweenShots = new JTextField("Time Interval Between Shots", 15);
    private JComboBox<String> unitsForInterval = new JComboBox<>(acquisitionData.timeUnits);
    private JTextField totalExperimentTime = new JTextField("Total Experiment Time", 15);
    private JComboBox<String> unitsForExperimentTime = new JComboBox<>(acquisitionData.timeUnits);
    private JTextField exposureTime = new JTextField("Exposure Time(ms)", 15);

    //Initializing additional configuration classes to allow to more personalized configurations
    private LaserConfig laserConfig = new LaserConfig(acquisitionData, pointInformation);
    private boolean laserConfigShown = false;
    private TimeConfig timeConfig = new TimeConfig(acquisitionData, pointInformation);
    private boolean timeConfigShown = false;
    private LEDConfig ledConfig = new LEDConfig(acquisitionData, pointInformation);
    private boolean ledConfigShown = false;
    private ObjectiveConfig objectiveConfig = new ObjectiveConfig(acquisitionData, pointInformation);
    private boolean objectiveConfigShown = false;
    private FilterConfig filterConfig = new FilterConfig(acquisitionData, pointInformation);
    private boolean FilterConfigShown = false;
    private ObjectiveConfig1 objectiveConfig1 = new ObjectiveConfig1(acquisitionData, pointInformation);
    private LEDConfig1 ledConfig1 = new LEDConfig1(acquisitionData, pointInformation);
    private FilterConfig1 filterConfig1 = new FilterConfig1(acquisitionData, pointInformation);
    private LightPathConfig lightPathConfig;

    public void setupMainInterface(Studio app) throws Exception {
        //Setting up behavior of the main frame
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(2000,1000);

        //Setting up core components
        gui = (MMStudio) app;
        core = gui.getCMMCore();

        //Setting up title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Please Enter Information Below");
        titlePanel.add(titleLabel);

        //Calling function to set up information entry panel
        setupPointInfoEntryPanel();

        //Setting up panels
        lightPathConfig = new LightPathConfig(app);
        executor = new Executor(acquisitionData, core);
        ledConfig1.setUpLEDTimeConfigInterface();
        objectiveConfig1.setUpObjectiveConfigInterface();
        filterConfig1.setUpFilterConfigInterface();
        lightPathConfig.setupLightPathConfig();

        //Setting up final entry button
        JPanel finalEntryPanel = new JPanel();
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> enterButtonPerformed(e));
        finalEntryPanel.add(enterButton);

        //Setting up configuration button panel
        JPanel configurationButtonPanel = new JPanel(new GridLayout(0,1));
        JButton addPointButton = new JButton("Add Point");
        addPointButton.addActionListener(e -> addPointButtonPerformed(e));
        JButton removePointButton = new JButton("Remove Point");
        removePointButton.addActionListener(e -> removePointButtonPerformed(e));
        JButton laserInputButton = new JButton("Additional Laser Configurations");
        laserInputButton.addActionListener(e -> laserInputButtonPerformed(e));
        JButton timeInputButton = new JButton("Additional Time Configurations");
        timeInputButton.addActionListener(e -> TimeInputButtonPerformed(e));

        configurationButtonPanel.add(new JLabel("Current Position:" + "(X,Y)" + "=(" + core.getXPosition() + ", " + core.getYPosition() + ")"));
        configurationButtonPanel.add(addPointButton);
        configurationButtonPanel.add(removePointButton);
        configurationButtonPanel.add(laserInputButton);
        configurationButtonPanel.add(timeInputButton);

        //Setting up input panel for time and laser configurations
        JPanel timeLaserConfigurations = new JPanel(new GridBagLayout());
        setUpUniversalTextFields();
        String[] laserConfigurations = {" Laser 1 ", " Laser 2 ", " Laser 3 ", " Laser 4 "};
        laserSelection = new JList(laserConfigurations);
        laserSelection.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        laserSelection.setLayoutOrientation(JList.VERTICAL_WRAP);
        laserSelection.setVisibleRowCount(-2);
        JScrollPane laserSelectionPane =  new JScrollPane(laserSelection);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        timeLaserConfigurations.add(timeIntervalBetweenShots, constraints);

        constraints.gridy = 1;
        timeLaserConfigurations.add(totalExperimentTime, constraints);

        constraints.gridy = 2;
        timeLaserConfigurations.add(exposureTime, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        timeLaserConfigurations.add(unitsForInterval, constraints);

        constraints.gridy = 1;
        timeLaserConfigurations.add(unitsForExperimentTime, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        timeLaserConfigurations.add(laserSelectionPane, constraints);



        //Adding all components onto main frame
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 0;
        mainFrame.getContentPane().add(titlePanel, constraints);

        constraints.gridy = 4;
        mainFrame.getContentPane().add(finalEntryPanel, constraints);

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
        constraints.gridwidth = 3;
        constraints.gridheight = 2;
        mainFrame.getContentPane().add(pointInfoEntryPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        objectiveConfig1.updateObjectiveArrays();
        mainFrame.getContentPane().add(objectiveConfig1, constraints);

        constraints.gridx = 1;
        ledConfig1.updateLEDArrays();
        mainFrame.getContentPane().add(ledConfig1, constraints);

        constraints.gridx = 2;
        filterConfig1.updateFilterArrays1();
        filterConfig1.updateFilterArrays2();
        mainFrame.getContentPane().add(filterConfig1, constraints);

        constraints.gridx = 3;
        constraints.gridy = 3;
        mainFrame.getContentPane().add(lightPathConfig, constraints);

        mainFrame.setVisible(true);
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
        singleRowPanel.add(new Label("Point " + (pointInformation.size() + 1) + ":"));
        ArrayList<JTextField> textFields = new ArrayList<JTextField>();
        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        labels.add(new JLabel("X-Coordinate:"));
        labels.add(new JLabel("Y-Coordinate:"));
        labels.add(new JLabel("Z-Coordinate Max:"));
        labels.add(new JLabel("Z-Coordinate Min:"));
        labels.add(new JLabel("Z Step Size:"));
        for(int i = 0; i < 5; i++){
            textFields.add(new JTextField("0",3));
            singleRowPanel.add(labels.get(i));
            singleRowPanel.add(textFields.get(i));
        }
        tablePanel.add(singleRowPanel);
        pointInformation.add(textFields);
    }

    //Setting up helper method to update the info entry table and corresponding arraylists
    private void updateTable(){
        if(tablePanel.getComponentCount() > pointInformation.size()){
            tablePanel.remove(tablePanel.getComponentCount() - 1);
        }
        acquisitionData.updatePointInformationArray(pointInformation);
        pointInfoEntryPanel.revalidate();
        pointInfoEntryPanel.repaint();
    }

    //Setting up behavior for when enter button is clicked
    private void enterButtonPerformed(ActionEvent e) {
        int totalExperimentTimeValue = Integer.parseInt(totalExperimentTime.getText());
        int exposureTimeValue = Integer.parseInt(exposureTime.getText());
        if(unitsForExperimentTime.getSelectedItem().equals("Minutes")){
            totalExperimentTimeValue *= 60;
        } else if(unitsForExperimentTime.getSelectedItem().equals("Hours")){
            totalExperimentTimeValue *= 3600;
        }
        acquisitionData.saveFinalConfigs(totalExperimentTimeValue, exposureTimeValue, timeConfig, laserConfig, ledConfig, objectiveConfig, filterConfig);
        executor.execute();
    }

    //Setting up behavior for when time configuration button is clicked
    private void addPointButtonPerformed(ActionEvent e) {
        addRowToTable();
        updateTable();
    }

    //Setting up behavior for when time configuration button is clicked
    private void removePointButtonPerformed(ActionEvent e) {
        pointInformation.remove(pointInformation.size() - 1);
        updateTable();
    }

    //Setting up behavior for when time configuration button is clicked
    private void laserInputButtonPerformed(ActionEvent e) {
        if(laserConfigShown == false) {
            laserConfig.setUpLaserConfigInterface();
            laserConfigShown = true;
        } else{
            laserConfig.redisplayWindow();
        }
    }

    //Setting up behavior for when time configuration button is clicked
    private void TimeInputButtonPerformed(ActionEvent e) {
        timeIntervalBetweenShots.setText("Advanced Configs Present");
        if(timeConfigShown == false) {
            timeConfig.setUpTimeConfigInterface();
            timeConfigShown = true;
        } else{
            timeConfig.redisplayWindow();
        }
    }

    public void setupLogger() {
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

    public void setUpUniversalTextFields(){
        timeIntervalBetweenShots.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(timeIntervalBetweenShots.getText().equals("Time Interval Between Shots")){
                    timeIntervalBetweenShots.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(timeIntervalBetweenShots.getText().equals("")){
                    timeIntervalBetweenShots.setText("Time Interval Between Shots");
                }
            }
        });

        totalExperimentTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(totalExperimentTime.getText().equals("Total Experiment Time")){
                    totalExperimentTime.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(totalExperimentTime.getText().equals("")){
                    totalExperimentTime.setText("Total Experiment Time");
                }
            }
        });

        exposureTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(exposureTime.getText().equals("Exposure Time(ms)")){
                    exposureTime.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(exposureTime.getText().equals("")){
                    exposureTime.setText("Exposure Time(ms)");
                }
            }
        });
    }
}