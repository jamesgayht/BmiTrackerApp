package com.ssfMiniProject.BmiTrackerApp.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;

public class BmiRapidApiQuery {

    private static final Logger logger = LoggerFactory.getLogger(BmiRapidApiQuery.class);

    private double weight;
    private double height;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public static BmiRapidApiQuery createJson (JsonObject jsonObject) {
        logger.info("createJson query");
        BmiRapidApiQuery bmiRapidApiQuery = new BmiRapidApiQuery(); 

        // JsonObject queryObj = o.getJsonObject("query");

        JsonNumber jsWeight = jsonObject.getJsonNumber("weight"); 
        bmiRapidApiQuery.weight = jsWeight.doubleValue(); 

        JsonNumber jsHeight = jsonObject.getJsonNumber("height"); 
        bmiRapidApiQuery.height = jsHeight.doubleValue(); 

        return bmiRapidApiQuery;
    }
}
