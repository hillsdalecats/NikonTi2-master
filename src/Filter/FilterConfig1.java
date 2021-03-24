package Filter;

import Main.AcquisitionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class FilterConfig1 extends JPanel{
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private AcquisitionData acquisitionData;
    private ArrayList<ArrayList<JTextField>> pointInformation;
    protected ArrayList<JTextField> filterInfo1 = new ArrayList<JTextField>();
    protected ArrayList<JTextField> filterInfo2 = new ArrayList<JTextField>();

    public FilterConfig1(AcquisitionData acquisitionData, ArrayList<ArrayList<JTextField>> pointInformation) {
        this.acquisitionData = acquisitionData;
        this.pointInformation = pointInformation;
    }

    public void setUpFilterConfigInterface(){
        setLayout(new GridBagLayout());
        setSize(600, 300);

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
        updateFilterArrays1();
        updateFilterArrays2();
        for (int i = 0; i < acquisitionData.pointInformation.size(); i++) {
            JPanel singleRowPanel = new JPanel();
            String xCord = acquisitionData.pointInformation.get(i).get(0).toString();
            String yCord = acquisitionData.pointInformation.get(i).get(1).toString();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ")"));
            singleRowPanel.add(new JLabel(" | Filter1:"));
            singleRowPanel.add(filterInfo1.get(i));
            singleRowPanel.add(new JLabel(" | Filter2:"));
            singleRowPanel.add(filterInfo2.get(i));
            tablePanel.add(singleRowPanel);
        }
    }

    public void updateFilterArrays1() {
        int difference = pointInformation.size() - filterInfo1.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                filterInfo1.add(new JTextField("0",5));
                acquisitionData.filter1name.add(0);
            }
        } else if(difference < 0) {
            for (int i = filterInfo1.size(); i > pointInformation.size(); i--) {
                filterInfo1.remove(i - 1);
            }
        }
    }

    public void updateFilterArrays2() {
        int difference = pointInformation.size() - filterInfo2.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                filterInfo2.add(new JTextField("0",5));
                acquisitionData.filter2name.add(0);
            }
        } else if(difference < 0) {
            for (int i = filterInfo2.size(); i > pointInformation.size(); i--) {
                filterInfo2.remove(i - 1);
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
        updateFilterArrays1();
        updateFilterArrays2();
        setVisible(true);
    }
}
