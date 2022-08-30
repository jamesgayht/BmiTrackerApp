package com.ssfMiniProject.BmiTrackerApp.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RapidApiGetBmi {

    private static final Logger logger = LoggerFactory.getLogger(RapidApiGetBmi.class);

    private double bmi;
    private BmiRapidApiQuery bmiRapidApiQuery;

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public BmiRapidApiQuery getBmiRapidApiQuery() {
        return bmiRapidApiQuery;
    }

    public void setBmiRapidApiQuery(BmiRapidApiQuery bmiRapidApiQuery) {
        this.bmiRapidApiQuery = bmiRapidApiQuery;
    }

    public static RapidApiGetBmi createJson(String json) throws IOException {

        logger.info("currency createJson");
        RapidApiGetBmi rapidApiGetBmi = new RapidApiGetBmi();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();

            logger.info(">>>>>>>" + jsonObject.getJsonObject("bmiRapidApiQuery"));

            rapidApiGetBmi.bmiRapidApiQuery = BmiRapidApiQuery.createJson(jsonObject.getJsonObject("bmiRapidApiQuery"));
            rapidApiGetBmi.bmi = jsonObject.getJsonNumber("bmi").doubleValue();

            logger.info(">>>>>>>" + rapidApiGetBmi.toString());
        }

        return rapidApiGetBmi;
    }
}
