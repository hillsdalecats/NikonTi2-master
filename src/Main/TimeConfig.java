package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TimeConfig{
    private JFrame timeConfigurationFrame = new JFrame("Time Configuration");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private final String[] timeUnit = {"Seconds", "Minutes", "Hours"};
    private JComboBox<String> timeIntervalUnits = new JComboBox<>(timeUnit);
    private AcquisitionData acquisitionData;
    protected ArrayList<ArrayList<JTextField>> timeInfo = new ArrayList<ArrayList<JTextField>>();
    protected ArrayList<ArrayList<JComboBox>> timeUnitSelected = new ArrayList<ArrayList<JComboBox>>();
    protected ArrayList<JCheckBox> pauseSelection = new ArrayList<>();
    private ArrayList<ArrayList<JTextField>> pointInformation;

    public TimeConfig(AcquisitionData acquisitionData, ArrayList<ArrayList<JTextField>> pointInformation) {
        this.acquisitionData = acquisitionData;
        this.pointInformation = pointInformation;
    }

    public void setUpTimeConfigInterface(){
        timeConfigurationFrame.setLayout(new GridBagLayout());
        timeConfigurationFrame.setSize(600, 300);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Enter Information Here");
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        JButton updatePointsButton = new JButton("Update Points");
        updatePointsButton.addActionListener(e -> updatePointsPerformed(e));
        JButton saveTimeConfiguration = new JButton("Save Time Configuration");
        saveTimeConfiguration.addActionListener(e -> saveTimeConfigurationPerformed(e));
        buttonPanel.add(updatePointsButton);
        buttonPanel.add(saveTimeConfiguration);

        updateTableDisplay();

        constraints.gridy = 0;
        timeConfigurationFrame.add(titleLabel, constraints);

        constraints.gridy = 2;
        timeConfigurationFrame.add(buttonPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        timeConfigurationFrame.add(infoEntryScrollPanel, constraints);

        timeConfigurationFrame.setVisible(true);
    }

    private void updateTableDisplay(){
        acquisitionData.updatePointInformationArray(pointInformation);
        updateTimeArrays();
        for(int i = 0; i < timeInfo.size(); i++){
            JPanel singleRowPanel = new JPanel();
            String xCord = acquisitionData.pointInformation.get(i).get(0).toString();
            String yCord = acquisitionData.pointInformation.get(i).get(1).toString();
            String zCordMin = acquisitionData.pointInformation.get(i).get(2).toString();
            String zCordMax = acquisitionData.pointInformation.get(i).get(3).toString();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ", " + zCordMin + " to " + zCordMax + ") |"));
            singleRowPanel.add(new JLabel("Start Time: "));
            singleRowPanel.add(timeInfo.get(i).get(0));
            timeUnitSelected.get(i).add(new JComboBox<>(timeUnit));
            singleRowPanel.add(timeUnitSelected.get(i).get(0));
            singleRowPanel.add(new JLabel("Time Interval Between Pictures:"));
            singleRowPanel.add(timeInfo.get(i).get(1));
            timeUnitSelected.get(i).add(new JComboBox<>(timeUnit));
            singleRowPanel.add(timeUnitSelected.get(i).get(1));
            singleRowPanel.add(pauseSelection.get(i));
            tablePanel.add(singleRowPanel);
        }
    }

    private void updatePointsPerformed(ActionEvent e) {
        tablePanel.removeAll();
        updateTableDisplay();
        infoEntryScrollPanel.revalidate();
        infoEntryScrollPanel.repaint();
    }

    private void saveTimeConfigurationPerformed(ActionEvent e) {
        updateTimeArrays();
        timeConfigurationFrame.setVisible(false);
    }

    public void redisplayWindow(){
        updateTimeArrays();
        timeConfigurationFrame.setVisible(true);
    }

    public void updateTimeArrays(){
        int difference = acquisitionData.pointInformation.size() - timeInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                timeInfo.add(new ArrayList<JTextField>());
                timeUnitSelected.add(new ArrayList<JComboBox>());
                pauseSelection.add(new JCheckBox("Pause"));
                acquisitionData.timeIntervals.add(new ArrayList<Integer>());
                for(int j = 0; j < 2; j++) {
                    timeInfo.get(i).add(new JTextField("0", 5));
                    acquisitionData.timeIntervals.get(i).add(0);
                }
            }
        } else if(difference < 0) {
            for (int i = timeInfo.size(); i > acquisitionData.pointInformation.size(); i--) {
                timeInfo.remove(i - 1);
                pauseSelection.remove(i-1);
                acquisitionData.timeIntervals.remove(i - 1);
            }
        }
    }
}