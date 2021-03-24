package Objective;

import Main.AcquisitionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ObjectiveConfig1 extends JPanel{
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private AcquisitionData acquisitionData;
    private ArrayList<ArrayList<JTextField>> pointInformation;
    protected ArrayList<JTextField> objectiveInfo = new ArrayList<JTextField>();

    public ObjectiveConfig1(AcquisitionData acquisitionData, ArrayList<ArrayList<JTextField>> pointInformation) {
        this.acquisitionData = acquisitionData;
        this.pointInformation = pointInformation;
    }

    public void setUpObjectiveConfigInterface(){
        setLayout(new GridBagLayout());
        setSize(600, 300);

        JPanel titlePanel = new JPanel(new GridLayout(0,1));
        titlePanel.add(new JLabel("Enter Information Here:"));
        titlePanel.add(new JLabel("1-10X;2-20X;5-60X;"));

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
        add(titlePanel, constraints);

        constraints.gridy = 2;
        add(buttonPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(infoEntryScrollPanel, constraints);

        setVisible(true);

    }

    private void updateTableDisplay(){
        acquisitionData.updatePointInformationArray(pointInformation);
        updateObjectiveArrays();
        for (int i = 0; i < objectiveInfo.size(); i++) {
            JPanel singleRowPanel = new JPanel();
            String xCord = acquisitionData.pointInformation.get(i).get(0).toString();
            String yCord = acquisitionData.pointInformation.get(i).get(1).toString();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ")"));
            singleRowPanel.add(new JLabel(" | Objective:"));
            singleRowPanel.add(objectiveInfo.get(i));
            tablePanel.add(singleRowPanel);
        }
    }

    public void updateObjectiveArrays() {
        int difference = pointInformation.size() - objectiveInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                objectiveInfo.add(new JTextField("0",5));
                acquisitionData.objectiveSelections.add(0);
            }
        } else if(difference < 0) {
            for (int i = objectiveInfo.size(); i > pointInformation.size(); i--) {
                objectiveInfo.remove(i - 1);
            }
        }
    }

    private void updatePointsPerformed(ActionEvent e){
        tablePanel.removeAll();
        updateTableDisplay();
        infoEntryScrollPanel.revalidate();
        infoEntryScrollPanel.repaint();
    }

    private void saveConfigurationPerformed(ActionEvent e) {
        setVisible(false);
    }

    public void redisplayWindow() {
        updateObjectiveArrays();
        setVisible(true);
    }
}
