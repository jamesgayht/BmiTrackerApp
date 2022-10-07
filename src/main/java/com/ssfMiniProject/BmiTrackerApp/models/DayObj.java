package com.ssfMiniProject.BmiTrackerApp.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component("dayObj")
public class DayObj {
    public String day;
    public GeneratedBmiObj dailyBmi;
    
    
    public DayObj(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        this.day = localDate.format(dateTimeFormatter);
    }

    public DayObj(String day){
        this.day = day;
    }
    
    public GeneratedBmiObj getDailyBmi() {
        return dailyBmi;
    }

    public void setDailyBmi(GeneratedBmiObj dailyBmi) {
        this.dailyBmi = dailyBmi;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    
    public void newDay(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        this.day = localDate.format(dateTimeFormatter);
    }
}
