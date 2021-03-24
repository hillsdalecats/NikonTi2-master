package Objective;

import Main.AcquisitionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ObjectiveConfig {
    private JFrame objectiveConfigurationFrame = new JFrame("Objective Configuration");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private AcquisitionData acquisitionData;

    public ObjectiveConfig(AcquisitionData acquisitionData) {
        this.acquisitionData = acquisitionData;
    }

    public void redisplayWindow() {
        acquisitionData.updateTimeArrays();
        acquisitionData.updateObjectiveArrays();
        objectiveConfigurationFrame.setVisible(true);
    }

    public void setUpObjectiveConfigInterface(){
        objectiveConfigurationFrame.setLayout(new GridBagLayout());
        objectiveConfigurationFrame.setSize(600, 300);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("<html><body><p align=\\\"center\\\">Enter Information Here<br/>1-10X;2-20X;5-60X;</p></body></html>");
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        JButton updatePointsButton = new JButton("Update Points");
        updatePointsButton.addActionListener(e -> {
            try {
                updatePointsPerformed(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        JButton saveTimeConfiguration = new JButton("Save Configuration");
        saveTimeConfiguration.addActionListener(e -> saveConfigurationPerformed(e));
        buttonPanel.add(updatePointsButton);
        buttonPanel.add(saveTimeConfiguration);

        updateTableDisplay();

        constraints.gridy = 0;
        objectiveConfigurationFrame.add(titleLabel, constraints);

        constraints.gridy = 2;
        objectiveConfigurationFrame.add(buttonPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        objectiveConfigurationFrame.add(infoEntryScrollPanel, constraints);

        objectiveConfigurationFrame.setVisible(true);

    }

    private void updatePointsPerformed(ActionEvent e){
        tablePanel.removeAll();
        updateTableDisplay();
        infoEntryScrollPanel.revalidate();
        infoEntryScrollPanel.repaint();
    }

    private void saveConfigurationPerformed(ActionEvent e) {
        objectiveConfigurationFrame.setVisible(false);
    }

    private void updateTableDisplay(){
        acquisitionData.updateTimeArrays();
        acquisitionData.updateObjectiveArrays();
        for (int i = 0; i < acquisitionData.objectiveInfo.size(); i++) {
            JPanel singleRowPanel = new JPanel();
            String xCord = acquisitionData.pointInformation.get(i).get(0).getText();
            String yCord = acquisitionData.pointInformation.get(i).get(1).getText();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ")"));
            singleRowPanel.add(new JLabel(" | Objective:"));
            singleRowPanel.add(acquisitionData.objectiveInfo.get(i));
            tablePanel.add(singleRowPanel);
        }
    }
}
