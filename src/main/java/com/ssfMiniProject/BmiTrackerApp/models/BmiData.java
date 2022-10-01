package com.ssfMiniProject.BmiTrackerApp.models;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;

public class BmiData {

    private static Logger logger = LoggerFactory.getLogger(BmiData.class);

    private double userBmi;
    private double userWeight;
    private double userHeight;
    private static ArrayList<BmiData> bmiList = new ArrayList<>();
    
    
    public static ArrayList<BmiData> getBmiList() {
        return bmiList;
    }

    public static void setBmiList(ArrayList<BmiData> bmiList) {
        BmiData.bmiList = bmiList;
    }

    public double getUserBmi() {
        return userBmi;
    }

    public void setUserBmi(double userBmi) {
        this.userBmi = userBmi;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight) {
        this.userWeight = userWeight;
    }

    public double getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(double userHeight) {
        this.userHeight = userHeight;
    }

    public static BmiData createJson(JsonObject o) throws IOException {

        logger.info("createJson bmiData");
        BmiData bmiData = new BmiData();
        
        JsonNumber bmiNum = o.getJsonNumber("bmi");
        bmiData.userBmi = bmiNum.doubleValue();
        logger.info("bmiData userBmi > " + bmiData.userBmi);

        String weightNum = o.getJsonString("weight").getString();
        bmiData.userWeight = Double.parseDouble(weightNum); 
        logger.info("bmiData userWeight > " + bmiData.userWeight);

        String heightNum = o.getJsonString("height").getString();
        logger.info("bmiData userHeightNum > " + heightNum);
        
        bmiData.userHeight = Double.parseDouble(heightNum);
        logger.info("bmiData userHeight > " + bmiData.userHeight);

        return bmiData;
    }

}
