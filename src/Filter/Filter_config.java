package Filter;

import Main.AcquisitionData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;



public class Filter_config {
    private JFrame FilterConfigurationFrame = new JFrame("Filter Configuration");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private AcquisitionData acquisitionData;

    public Filter_config(AcquisitionData acquisitionData) { this.acquisitionData = acquisitionData; }

    public void setUpFilterConfigInterface(){
        FilterConfigurationFrame.setLayout(new GridBagLayout());
        FilterConfigurationFrame.setSize(600, 300);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("<html><body><p align=\\\"center\\\">Enter Information Here<br/>Please Enter Value from [1,2,3,4,5,6]</p></body></html>");
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
        FilterConfigurationFrame.add(titleLabel, constraints);


        constraints.gridy = 2;
        FilterConfigurationFrame.add(buttonPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        FilterConfigurationFrame.add(infoEntryScrollPanel, constraints);

        FilterConfigurationFrame.setVisible(true);

    }

    private void updateTableDisplay(){
        acquisitionData.updateTimeArrays();
        acquisitionData.updateFilterArrays1();
        acquisitionData.updateFilterArrays2();
        for (int i = 0; i < acquisitionData.pointInformation.size(); i++) {
            JPanel singleRowPanel = new JPanel();
            String xCord = acquisitionData.pointInformation.get(i).get(0).getText();
            String yCord = acquisitionData.pointInformation.get(i).get(1).getText();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ")"));
            singleRowPanel.add(new JLabel(" | Filter1:"));
            singleRowPanel.add(acquisitionData.filterInfo1.get(i));
            singleRowPanel.add(new JLabel(" | Filter2:"));
            singleRowPanel.add(acquisitionData.filterInfo2.get(i));
            tablePanel.add(singleRowPanel);
        }
    }

    private void updatePointsPerformed(ActionEvent e){
        tablePanel.removeAll();
        updateTableDisplay();
        infoEntryScrollPanel.revalidate();
        infoEntryScrollPanel.repaint();
    }
    private void saveConfigurationPerformed(ActionEvent e) {
        FilterConfigurationFrame.setVisible(false);
    }

    public void redisplayWindow() {
        acquisitionData.updateTimeArrays();
        acquisitionData.updateFilterArrays1();
        acquisitionData.updateFilterArrays2();
        FilterConfigurationFrame.setVisible(true);
    }
}
