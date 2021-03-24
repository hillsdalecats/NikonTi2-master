package LightPath;

import Main.AcquisitionData;
import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.logging.LogFileManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LightPathInterface {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    //Initializing all core components of the class that will allow for manipulation of the microscope
    private CMMCore core;
    private MMStudio gui;
    private FileHandler fh;
    //Initializing additional configuration classes to allow to more personalized configurations
    private AcquisitionData acquisitionData = new AcquisitionData();

    //Initializing all components necessary to setup the main frame.
    private JFrame LightPathmainFrame = new JFrame("LightPath Control");
    private GridBagConstraints constraints = new GridBagConstraints();
    protected ArrayList<Integer> selectedlightpath = new ArrayList<Integer>();

    public void setupObjectiveInterface(Studio app){
        //Setting up behavior of the main frame
        LightPathmainFrame.setLayout(new GridBagLayout());
        LightPathmainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LightPathmainFrame.setSize(400, 300);

        //Setting up core components
        gui = (MMStudio) app;
        core = gui.getCMMCore();

        //Setting up title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("LightPath Control");
        titlePanel.add(titleLabel);

        JPanel LightPathSelectPanel = new JPanel();
        LayoutManager layout3 = new FlowLayout();
        LightPathSelectPanel.setLayout(layout3);

        String[] numbers3 = {"1-EYE", "2-R100", "3-B100", "4-L100"};
        JList<String> lightpathlist = new JList<>(numbers3);
        lightpathlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lightpathlist.setVisibleRowCount(-1); // to keep all values visible
        lightpathlist.setSelectedIndex(0);
        lightpathlist.addListSelectionListener(e -> {
            JList list3 = (JList)e.getSource();
            int c;
            c = list3.getSelectedIndex();
            try {
                core.setProperty("LightPath", "State", c);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        LightPathSelectPanel.add(lightpathlist);

        //Adding all components onto main frame
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 1;
        LightPathmainFrame.getContentPane().add(titlePanel, constraints);


        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        LightPathmainFrame.getContentPane().add(LightPathSelectPanel, constraints);

        LightPathmainFrame.setVisible(true);
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
