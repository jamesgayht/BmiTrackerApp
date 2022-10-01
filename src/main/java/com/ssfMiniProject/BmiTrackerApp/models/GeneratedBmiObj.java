package com.ssfMiniProject.BmiTrackerApp.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Component ("generatedBmiObj")
public class GeneratedBmiObj {
    private static Logger logger = LoggerFactory.getLogger(GeneratedBmiObj.class);

    private List<BmiData> bmiList = new ArrayList<>(); 

    public List<BmiData> getBmiList() {
        return bmiList;
    }

    public void setBmiList(List<BmiData> bmiList) {
        this.bmiList = bmiList;
    }

    public void addBmi (BmiData bmiData) {
        bmiList.add(bmiData); 
    }

    public void delBmi(int i){
        bmiList.remove(i);
    }

    public static GeneratedBmiObj createJson(String json) throws IOException {
        logger.info("createJson bmiList");
        GeneratedBmiObj bmiList = new GeneratedBmiObj();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())){
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
                BmiData bmiData = BmiData.createJson(o);
                bmiList.addBmi(bmiData);

                logger.info("User BMI > " + bmiData.getUserBmi());
                logger.info("User Weight > " + bmiData.getUserWeight());
                logger.info("User Height > " + bmiData.getUserHeight());
            }
            
            return bmiList;
        }
    }

