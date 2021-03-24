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

public class LightPathConfig extends JPanel {
    private CMMCore core;
    private MMStudio gui;
    private String[] possibleLightPaths = {"1-EYE", "2-R100", "3-B100", "4-L100"};
    private JList<String> LightPathSelection = new JList<String>(possibleLightPaths);
    private GridBagConstraints constraints = new GridBagConstraints();

    public LightPathConfig(Studio app) {
        this.gui = (MMStudio) app;
        this.core = gui.getCMMCore();
    }

    public void setupLightPathConfig() {
        //Setting up behavior of the main frame
        setLayout(new GridBagLayout());
        setSize(400, 300);


        //Setting up title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("LightPath Control");
        titlePanel.add(titleLabel);

        JPanel lightPathSelectPanel = new JPanel();
        lightPathSelectPanel.setLayout(new GridLayout(0,1));

        LightPathSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        LightPathSelection.setVisibleRowCount(-1); // to keep all values visible
        LightPathSelection.setSelectedIndex(0);
        LightPathSelection.addListSelectionListener(e -> {
            JList list3 = (JList) e.getSource();
            int c;
            c = list3.getSelectedIndex();
            if (c == 0) {
                try {
                    core.setProperty("LightPath", "State", "0");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (c == 1) {
                try {
                    core.setProperty("LightPath", "State", "1");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (c == 2) {
                try {
                    core.setProperty("LightPath", "State", "2");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (c == 3) {
                try {
                    core.setProperty("LightPath", "State", "3");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        lightPathSelectPanel.add(LightPathSelection);

        //Adding all components onto main frame
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 1;
        add(titlePanel, constraints);


        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(lightPathSelectPanel, constraints);

        setVisible(true);
    }

}