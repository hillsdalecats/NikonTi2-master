package Main;

import mmcorej.CMMCore;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class AcquisitionData {

    //Array list storing point information
    public ArrayList<ArrayList<JTextField>> pointInformation = new ArrayList<ArrayList<JTextField>>();

    //Array lists storing time configuration information
    private String[] timeUnits = {"Seconds", "Minutes", "Hours"};
    protected ArrayList<Integer> timeIntervals = new ArrayList<Integer>();
    public ArrayList<JTextField> timeInfo = new ArrayList<JTextField>();
    public ArrayList<JComboBox> timeUnitSelected = new ArrayList<JComboBox>();
    protected JTextField timeIntervalBetweenShots = new JTextField("Time Interval", 20);
    protected JComboBox<String> unitsForInterval = new JComboBox<>(timeUnits);
    public JTextField totalExperimentTime = new JTextField("Total Time", 20);
    protected JComboBox<String> unitsForExperimentTime = new JComboBox<>(timeUnits);
    protected JTextField exposureTime = new JTextField("Exposure Time(ms)", 20);

    //LED related configurations
    public ArrayList<JTextField> ledintensityInfo = new ArrayList<JTextField>();

    //Objective related configurations
    protected ArrayList<Integer> objectivename = new ArrayList<Integer>();
    public ArrayList<JTextField> objectiveInfo = new ArrayList<JTextField>();

    //Filter related configurations
    protected ArrayList<Integer> filter1name = new ArrayList<Integer>();
    public ArrayList<JTextField> filterInfo1 = new ArrayList<JTextField>();
    protected ArrayList<Integer> filter2name = new ArrayList<Integer>();
    public ArrayList<JTextField> filterInfo2 = new ArrayList<JTextField>();

    //Array lists storing laser configuration information
    public ArrayList<ArrayList<JList>> laserSelections = new ArrayList<ArrayList<JList>>();
    protected int numOfLaserStages = 2;

    public void updateTimeArrays(){
        int difference = pointInformation.size() - timeInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                timeInfo.add(new JTextField(5));
                timeInfo.get(timeInfo.size() - 1).setText("0");
                timeIntervals.add(0);
            }
        } else if(difference < 0) {
            for (int i = timeInfo.size(); i > pointInformation.size(); i--) {
                timeInfo.remove(i - 1);
                timeIntervals.remove(i - 1);
            }
        }
    }

    public void updateLaserArrays(){
        String[] laserConfigurations = {" Laser 1 ", " Laser 2 ", " Laser 3 ", " Laser 4 "};
        int difference = pointInformation.size() - laserSelections.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                ArrayList<JList> laserSelectionForPoint = new ArrayList<JList>();
                for(int j = 0; j < numOfLaserStages; j++) {
                    JList laserSelection = new JList(laserConfigurations);
                    laserSelection.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    laserSelection.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                    laserSelection.setVisibleRowCount(-4);
                    laserSelectionForPoint.add(laserSelection);
                }
                laserSelections.add(laserSelectionForPoint);
            }
        } else if(difference < 0) {
            for (int i = laserSelections.size(); i > pointInformation.size(); i--) {
                laserSelections.remove(i - 1);
            }
        }
    }


    public void updateObjectiveArrays() {
        int difference = pointInformation.size() - objectiveInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                objectiveInfo.add(new JTextField(5));
                objectiveInfo.get(objectiveInfo.size() - 1).setText("0");
                objectivename.add(0);
            }
        } else if(difference < 0) {
            for (int i = objectiveInfo.size(); i > pointInformation.size(); i--) {
                objectiveInfo.remove(i - 1);
            }
        }
        for(int i = 0; i < objectiveInfo.size(); i++) {
            objectivename.set(i,Integer.parseInt(objectiveInfo.get(i).getText()));
        }
    }

    public void updateFilterArrays1() {
        int difference = pointInformation.size() - filterInfo1.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                filterInfo1.add(new JTextField(5));
                filterInfo1.get(filterInfo1.size() - 1).setText("0");
                filter1name.add(0);
            }
        } else if(difference < 0) {
            for (int i = filterInfo1.size(); i > pointInformation.size(); i--) {
                filterInfo1.remove(i - 1);
            }
        }
        for(int i = 0; i < filterInfo1.size(); i++) {
            filter1name.set(i,Integer.parseInt(filterInfo1.get(i).getText()));
        }
    }

    public void updateFilterArrays2() {
        int difference = pointInformation.size() - filterInfo2.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                filterInfo2.add(new JTextField(5));
                filterInfo2.get(filterInfo2.size() - 1).setText("0");
                filter2name.add(0);
            }
        } else if(difference < 0) {
            for (int i = filterInfo2.size(); i > pointInformation.size(); i--) {
                filterInfo2.remove(i - 1);
            }
        }
        for(int i = 0; i < filterInfo2.size(); i++) {
            filter2name.set(i,Integer.parseInt(filterInfo2.get(i).getText()));
        }
    }

    public void updateledArrays(){
        int difference = pointInformation.size() - ledintensityInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                ledintensityInfo.add(new JTextField(5));
                ledintensityInfo.get(ledintensityInfo.size() - 1).setText("0");
            }
        } else if(difference < 0) {
            for (int i = ledintensityInfo.size(); i > pointInformation.size(); i--) {
                ledintensityInfo.remove(i - 1);
            }
        }

    }

    public void setUpUniversalTextFields(){
        timeIntervalBetweenShots.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(timeIntervalBetweenShots.getText().equals("Time Interval")){
                    timeIntervalBetweenShots.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(timeIntervalBetweenShots.getText().equals("")){
                    timeIntervalBetweenShots.setText("Time Interval");
                }
            }
        });

        totalExperimentTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(totalExperimentTime.getText().equals("Total Time")){
                    totalExperimentTime.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(totalExperimentTime.getText().equals("")){
                    totalExperimentTime.setText("Total Time");
                }
            }
        });

        exposureTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(exposureTime.getText().equals("Exposure Time(ms)")){
                    exposureTime.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(exposureTime.getText().equals("")){
                    exposureTime.setText("Exposure Time(ms)");
                }
            }

        });
    }

    public void saveFinalConfigs(){
        updateTimeArrays();
        for(int i = 0; i < timeInfo.size(); i++){
            if(timeUnitSelected.get(i).getSelectedItem().equals("Seconds")){
                timeIntervals.set(i,Integer.parseInt(timeInfo.get(i).getText()));
            } else if (timeUnitSelected.get(i).getSelectedItem().equals("Minutes")){
                timeIntervals.set(i,Integer.parseInt(timeInfo.get(i).getText()) * 60);
            } else{
                timeIntervals.set(i,Integer.parseInt(timeInfo.get(i).getText()) * 60 * 60);
            }
        }
    }

}
