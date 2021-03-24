package Filter;

import Main.AcquisitionData;
import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.logging.LogFileManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Filter_interface<bolean> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    //Initializing all core components of the class that will allow for manipulation of the microscope
    private CMMCore core;
    private MMStudio gui;
    private FileHandler fh;
    //Initializing additional configuration classes to allow to more personalized configurations
    private AcquisitionData acquisitionData = new AcquisitionData();

    //Initializing all components necessary to setup the main frame.
    private JFrame FiltermainFrame = new JFrame("Filter Control");
    private GridBagConstraints constraints = new GridBagConstraints();

    public void setupFilterInterface(Studio app) {
        //Setting up behavior of the main frame
        FiltermainFrame.setLayout(new GridBagLayout());
        FiltermainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FiltermainFrame.setSize(1000, 300);

        //Setting up core components
        gui = (MMStudio) app;
        core = gui.getCMMCore();

        //Setting up title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Filter Control");
        titlePanel.add(titleLabel);


        //Setting up 0n/off button
        JPanel Filter1Panel = new JPanel();
        JToggleButton on_off_1Button = new JToggleButton("Filter1 Switch");
        Filter1Panel.add(on_off_1Button);
        on_off_1Button.addChangeListener(event -> {
            if (on_off_1Button.isSelected()){
                on_off_1Button.setText("Filter1 is  ON");
                try {
                    core.setProperty("Turret1Shutter", "State", "1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                on_off_1Button.setText("Filter1 is OFF");
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

        JPanel Filter1SelectPanel = new JPanel();
        LayoutManager layout1 = new FlowLayout();
        Filter1SelectPanel.setLayout(layout1);

        String[] numbers1 = {"1:", "2:", "3:", "4:", "5:","6:"};
        JList<String> listBox1 = new JList<>(numbers1);
        listBox1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listBox1.setVisibleRowCount(-1); // to keep all values visible
        listBox1.setSelectedIndex(0);
        listBox1.addListSelectionListener(e -> {
            JList list1 = (JList)e.getSource();
            int a;
            a = list1.getSelectedIndex();
            if (a==0) {
                try {
                    core.setProperty("FilterTurret1", "State", "0");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (a==1) {
                try {
                    core.setProperty("FilterTurret1", "State", "1");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (a==2){
                try {
                    core.setProperty("FilterTurret1", "State", "2");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (a==3){
                try {
                    core.setProperty("FilterTurret1", "State", "3");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (a==4){
                try {
                    core.setProperty("FilterTurret1", "State", "4");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (a==5){
                try {
                    core.setProperty("FilterTurret1", "State", "5");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }


        });

        Filter1SelectPanel.add(listBox1);



        JPanel Filter2SelectPanel = new JPanel();
        LayoutManager layout2 = new FlowLayout();
        Filter1SelectPanel.setLayout(layout2);

        String[] numbers2 = {"1:", "2:", "3:", "4:", "5:","6:"};
        JList<String> listBox2 = new JList<>(numbers2);
        listBox2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listBox2.setVisibleRowCount(-1); // to keep all values visible
        listBox2.setSelectedIndex(0);
        listBox2.addListSelectionListener(e -> {
            JList list2 = (JList)e.getSource();
            int b;
            b= list2.getSelectedIndex();
            if (b==0) {
                try {
                    core.setProperty("FilterTurret2", "State", "0");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (b==1) {
                try {
                    core.setProperty("FilterTurret2", "State", "1");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (b==2){
                try {
                    core.setProperty("FilterTurret2", "State", "2");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (b==3){
                try {
                    core.setProperty("FilterTurret2", "State", "3");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (b==4){
                try {
                    core.setProperty("FilterTurret2", "State", "4");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (b==5){
                try {
                    core.setProperty("FilterTurret2", "State", "5");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });
        Filter2SelectPanel.add(listBox2);




        //Adding all components onto main frame
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 1;
        FiltermainFrame.getContentPane().add(titlePanel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(10, 10, 10, 10);
        FiltermainFrame.getContentPane().add(on_off_1Button, constraints);

        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(10, 10, 10, 10);
        FiltermainFrame.getContentPane().add(on_off_2Button, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        FiltermainFrame.getContentPane().add(Filter1SelectPanel, constraints);

        constraints.gridx = 5;
        constraints.gridy = 3;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        FiltermainFrame.getContentPane().add(Filter2SelectPanel, constraints);


        FiltermainFrame.setVisible(true);
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
