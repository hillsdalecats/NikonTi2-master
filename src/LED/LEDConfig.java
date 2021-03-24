package LED;

import Main.AcquisitionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class LEDConfig extends JPanel{
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private AcquisitionData acquisitionData;
    private ArrayList<ArrayList<JTextField>> pointInformation;
    public ArrayList<JTextField> ledIntensityInfo = new ArrayList<JTextField>();

    public LEDConfig(AcquisitionData acquisitionData, ArrayList<ArrayList<JTextField>> pointInformation) {
        this.acquisitionData = acquisitionData;
        this.pointInformation = pointInformation;
    }

    public void setUpLEDTimeConfigInterface(){
        setLayout(new GridBagLayout());
        setSize(600, 300);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("<html><body><p align=\\\"center\\\">Enter Information Here<br/>Please Enter Value like:0.3, 0.5</p></body></html>");
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
        add(titleLabel, constraints);


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
        updateLEDArrays();
        for (int i = 0; i < ledIntensityInfo.size(); i++) {
            JPanel singleRowPanel = new JPanel();
            String xCord = acquisitionData.pointInformation.get(i).get(0).toString();
            String yCord = acquisitionData.pointInformation.get(i).get(1).toString();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ")"));
            singleRowPanel.add(new JLabel(" | LED Intensity:"));
            singleRowPanel.add(ledIntensityInfo.get(i));
            tablePanel.add(singleRowPanel);
        }
    }

    public void updateLEDArrays(){
        int difference = pointInformation.size() - ledIntensityInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                ledIntensityInfo.add(new JTextField("0.0",5));
                acquisitionData.ledSelections.add(0.0);
            }
        } else if(difference < 0) {
            for (int i = ledIntensityInfo.size(); i > pointInformation.size(); i--) {
                ledIntensityInfo.remove(i - 1);
                acquisitionData.ledSelections.remove(i-1);
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
        updateLEDArrays();
        setVisible(true);
    }
}