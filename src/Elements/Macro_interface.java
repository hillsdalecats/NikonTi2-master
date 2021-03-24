package Elements;

import Laser.LaserScript;
import Main.AcquisitionData;
import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.logging.LogFileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Macro_interface {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    //Initializing all core components of the class that will allow for manipulation of the microscope
    private CMMCore core;
    private MMStudio gui;
    private FileHandler fh;
    //Initializing additional configuration classes to allow to more personalized configurations
    private AcquisitionData acquisitionData = new AcquisitionData();
    private boolean timeConfigShown = false;
    static final int MIN = 0;
    static final int MAX = 2100;

    //Initializing all components necessary to setup the main frame.
    private JFrame MacromainFrame = new JFrame("Macro Editor");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0, 1));

    //Initializing final entry class
    private LaserScript mousecontroller;


    public void setupLaserInterface(Studio app) {
        //Setting up behavior of the main frame
        MacromainFrame.setLayout(new GridBagLayout());
        MacromainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MacromainFrame.setSize(400, 300);

        //Setting up core components
        gui = (MMStudio) app;
        core = gui.getCMMCore();


        //Setting up title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Macro Editor");
        titlePanel.add(titleLabel);

        //setting up run button
        JPanel laserrunPanel = new JPanel();
        JButton enterButton = new JButton("Run");
        enterButton.addActionListener(e -> {
            try {
                enterButtonPerformed(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        laserrunPanel.add(enterButton);
        //Setting up input panel for time and laser configurations
        //JPanel LaserConfigurations = new JPanel(new GridBagLayout());
        //acquisitionData.setUpUniversalTextFields();
        //constraints.gridy = 0;
        //LaserConfigurations.add(acquisitionData.LasertimeIntervals, constraints);
        //constraints.gridy = 1;
        //LaserConfigurations.add(acquisitionData.Lasertotaltime, constraints);
        //constraints.gridy = 0;
        //LaserConfigurations.add(acquisitionData.unitsForExperimentTime, constraints);

        //Adding all components onto main frame
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 1;
        constraints.gridx = 5;
        MacromainFrame.getContentPane().add(titlePanel, constraints);


        constraints.gridx = 5;
        constraints.gridy = 4;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(20, 20, 20, 20);
        MacromainFrame.getContentPane().add(laserrunPanel, constraints);

        MacromainFrame.setVisible(true);
    }

    private void enterButtonPerformed(ActionEvent e) throws Exception {
        LaserScript.main();
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
}
