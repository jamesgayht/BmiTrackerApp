package com.ssfMiniProject.BmiTrackerApp.models;

import java.io.Serializable;
import java.util.Random;

public class UserBmi implements Serializable {

    private String name;
    private String email;
    private double height;
    private double weight;
    private String id;
    private double bmi;

    public UserBmi() {
        this.id = generateId(8);
    }

    public UserBmi(String name, String email, double height, double weight) {
        this.id = generateId(8);
        this.name = name;
        this.email = email;
        this.height = height;
        this.weight = weight;
    }

    private synchronized String generateId(int numchars) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < numchars) {
            stringBuilder.append(Integer.toHexString(random.nextInt()));
        }

        return stringBuilder.toString().substring(0, numchars);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

}
