package Laser;

import Main.AcquisitionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class LaserConfig{
    private JFrame laserConfigurationFrame = new JFrame("Laser Configuration");
    private GridBagConstraints constraints = new GridBagConstraints();
    private ArrayList<ArrayList<JCheckBox>> laserConfiguration = new ArrayList<ArrayList<JCheckBox>>();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private ArrayList<JCheckBox> uniformLaserConfig = new ArrayList<JCheckBox>();
    private AcquisitionData acquisitionData;
    public ArrayList<ArrayList<JList>> laserSelections = new ArrayList<ArrayList<JList>>();
    private int numOfLaserStages = 4;
    private ArrayList<ArrayList<JTextField>> pointInformation;


    public LaserConfig(AcquisitionData acquisitionData, ArrayList<ArrayList<JTextField>> pointInformation) {
        this.acquisitionData = acquisitionData;
        this.pointInformation = pointInformation;
    }

    public void setUpLaserConfigInterface(){
        laserConfigurationFrame.setLayout(new GridBagLayout());
        laserConfigurationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        laserConfigurationFrame.setSize(1200,350);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Enter Information Here");
        titlePanel.add(titleLabel);

        updateTableDisplay();

        JPanel buttonPanel = new JPanel();
        JButton updatePointsButton = new JButton("Update Points");
        updatePointsButton.addActionListener(e -> updatePointsPerformed(e));
        JButton saveTimeConfiguration = new JButton("Save");
        saveTimeConfiguration.addActionListener(e -> saveTimeConfigurationPerformed(e));
        buttonPanel.add(updatePointsButton);
        buttonPanel.add(saveTimeConfiguration);

        constraints.gridy = 0;
        laserConfigurationFrame.add(titleLabel, constraints);

        constraints.gridy = 2;
        laserConfigurationFrame.add(buttonPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        laserConfigurationFrame.add(infoEntryScrollPanel, constraints);

        laserConfigurationFrame.setVisible(true);
    }

    public void updateTableDisplay(){
        acquisitionData.updatePointInformationArray(pointInformation);
        updateLaserArrays();
        for(int i = 0; i < laserSelections.size(); i++) {
            JPanel singleRowPanel = new JPanel();
            ArrayList<JList> subArray = laserSelections.get(i);
            String xCord = acquisitionData.pointInformation.get(i).get(0).toString();
            String yCord = acquisitionData.pointInformation.get(i).get(1).toString();
            String zCordMin = acquisitionData.pointInformation.get(i).get(2).toString();
            String zCordMax = acquisitionData.pointInformation.get(i).get(3).toString();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ", " + zCordMin + " to " + zCordMax + ")"));
            singleRowPanel.add(new JLabel(" | Laser Configurations:"));
            for(int j = 0; j < subArray.size(); j++){
                singleRowPanel.add(subArray.get(j));
            }
            tablePanel.add(singleRowPanel);
        }
        infoEntryScrollPanel.setPreferredSize(new Dimension(650,250));
    }

    private void updatePointsPerformed(ActionEvent e) {
        tablePanel.removeAll();
        updateTableDisplay();
        infoEntryScrollPanel.revalidate();
        infoEntryScrollPanel.repaint();
    }

    public void updateLaserArrays(){
        String[] laserConfigurations = {" Violet (405 nm) ", " Blue (488 nm) ", " Yellow (561 nm) ", " Orange (640 nm) "};
        int difference = acquisitionData.pointInformation.size() - laserSelections.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                ArrayList<JList> laserSelectionForPoint = new ArrayList<JList>();
                for(int j = 0; j < numOfLaserStages; j++) {
                    JList laserSelection = new JList(laserConfigurations);
                    laserSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    laserSelection.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                    laserSelection.setVisibleRowCount(-4);
                    laserSelectionForPoint.add(laserSelection);
                }
                laserSelections.add(laserSelectionForPoint);
            }
        } else if(difference < 0) {
            for (int i = laserSelections.size(); i > acquisitionData.pointInformation.size(); i--) {
                laserSelections.remove(i - 1);
            }
        }
    }

    private void saveTimeConfigurationPerformed(ActionEvent e) {
        updateLaserArrays();
        laserConfigurationFrame.setVisible(false);
    }



    public void redisplayWindow(){
        laserConfigurationFrame.setVisible(true);
    }
}