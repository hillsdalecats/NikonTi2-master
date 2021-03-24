package Main;

import Filter.FilterConfig;
import LED.LEDConfig;
import Laser.LaserConfig;
import Objective.ObjectiveConfig;

import javax.swing.*;
import java.util.ArrayList;

public class AcquisitionData {
    //Array list storing point information
    public ArrayList<ArrayList<Double>> pointInformation = new ArrayList<ArrayList<Double>>();

    //Array lists storing laser configuration information
    protected ArrayList<ArrayList<String>> laserSelections = new ArrayList<ArrayList<String>>();

    //Array lists storing time configuration information
    protected String[] timeUnits = {"Seconds", "Minutes", "Hours"};
    protected ArrayList<ArrayList<Integer>> timeIntervals = new ArrayList<ArrayList<Integer>>();
    protected ArrayList<Boolean> pauseSelection = new ArrayList<Boolean>();
    protected int timeIntervalBetweenShot;
    protected int totalExperimentTime;
    protected int exposureTime;

    //Array lists storing LED configuration information
    public ArrayList<Double> ledSelections = new ArrayList<Double>();

    //Array lists storing Objective configuration information
    public ArrayList<Integer> objectiveSelections = new ArrayList<Integer>();

    //Array lists storing Filter configuration information
    public ArrayList<Integer> filter1name = new ArrayList<Integer>();
    public ArrayList<Integer> filter2name = new ArrayList<Integer>();

    public void saveFinalConfigs(int totalExperimentTime, int exposureTime, TimeConfig timeConfig, LaserConfig laserConfig, LEDConfig ledConfig, ObjectiveConfig objectiveConfig, FilterConfig filterConfig){
        this.totalExperimentTime = totalExperimentTime;
        this.exposureTime = exposureTime;
        for(int i = 0; i < timeConfig.timeInfo.size(); i++) {
            for(int j = 0; j < timeConfig.timeInfo.get(i).size(); j++) {
                if (timeConfig.timeUnitSelected.get(i).get(j).getSelectedItem().equals("Seconds")) {
                    timeIntervals.get(i).set(j, Integer.parseInt(timeConfig.timeInfo.get(i).get(j).getText()));
                } else if (timeConfig.timeUnitSelected.get(i).get(j).getSelectedItem().equals("Minutes")) {
                    timeIntervals.get(i).set(j, Integer.parseInt(timeConfig.timeInfo.get(i).get(j).getText()) * 60);
                } else {
                    timeIntervals.get(i).set(j, Integer.parseInt(timeConfig.timeInfo.get(i).get(j).getText()) * 60 * 60);
                }
            }
        }
        for(int i = 0; i < laserConfig.laserSelections.size(); i++){
            ArrayList<String> laserSelectionForPoint = new ArrayList<String>();
            for(int j = 0; j < laserConfig.laserSelections.get(i).size(); j++){
                laserSelectionForPoint.add(laserConfig.laserSelections.get(i).get(j).getSelectedValue().toString());
            }
            this.laserSelections.add(laserSelectionForPoint);
        }

        for(int i = 0; i < ledConfig.ledIntensityInfo.size(); i++){
            ledSelections.set(i, Double.parseDouble(ledConfig.ledIntensityInfo.get(i).getText()));
        }

        for(int i = 0; i < objectiveConfig.objectiveInfo.size(); i++) {
            objectiveSelections.set(i,Integer.parseInt(objectiveConfig.objectiveInfo.get(i).getText()));
        }

        for(int i = 0; i < filterConfig.filterInfo1.size(); i++) {
            filter1name.set(i,Integer.parseInt(filterConfig.filterInfo1.get(i).getText()));
        }

        for(int i = 0; i < filterConfig.filterInfo2.size(); i++) {
            filter2name.set(i,Integer.parseInt(filterConfig.filterInfo2.get(i).getText()));
        }
    }

    public void updatePointInformationArray(ArrayList<ArrayList<JTextField>> pointInformation){
        int difference = pointInformation.size() - this.pointInformation.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                this.pointInformation.add(new ArrayList<Double>());
            }
        } else if(difference < 0) {
            for (int i = this.pointInformation.size(); i > pointInformation.size(); i--) {
                this.pointInformation.remove(i-1);
            }
        }
        for(int i = 0; i < pointInformation.size(); i++){
            ArrayList<Double> temp = new ArrayList<Double>();
            for(int j = 0; j < pointInformation.get(i).size(); j++){
                String value = pointInformation.get(i).get(j).getText();
                if(value.equals("")){
                    temp.add(0.0);
                } else{
                    temp.add(Double.parseDouble(value));
                }
            }
            this.pointInformation.set(i,temp);
        }
    }
}