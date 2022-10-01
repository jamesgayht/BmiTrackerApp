package com.ssfMiniProject.BmiTrackerApp.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("user")
public class User {

    private String username;
    private String password;
    Map<String, DayObj> dayMap = new HashMap<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, DayObj> getDayMap() {
        return dayMap;
    }

    public void setDayMap(Map<String, DayObj> dayMap) {
        this.dayMap = dayMap;
    }

    public void addDay(DayObj dayObj) {
        dayMap.put(dayObj.day, dayObj);
    }

    public void delDay(String day) {
        dayMap.remove(day);
    }

    public void addBmi(String day, BmiData bmiData){ 
        if (dayMap.containsKey(day)){
            dayMap.get(day).getDailyBmi().addBmi(bmiData);
        } else {
            DayObj dayObj = new DayObj();
            GeneratedBmiObj generatedBmiObj = new GeneratedBmiObj();
            generatedBmiObj.addBmi(bmiData);
            dayObj.setDailyBmi(generatedBmiObj);
            dayMap.put(day, dayObj);
        }
    }

    public GeneratedBmiObj getBmiObj (String day) {
        if (!dayMap.containsKey(day)) {
            DayObj dayObj = new DayObj();
            GeneratedBmiObj generatedBmiObj = new GeneratedBmiObj();
            dayObj.setDailyBmi(generatedBmiObj);
            dayMap.put(day, dayObj);
        }
        return dayMap.get(day).getDailyBmi();
    }

    // public void delBmi(String day, int i) {
    //     dayMap.get(day).getDailyBmi().delBmi(i);
    // }
    
    public void delBmiFromListObj(String day, GeneratedBmiObj generatedBmiObj) {
        if (getDayMap().containsKey(day)) {
            delDay(day);
        }
    }
}
