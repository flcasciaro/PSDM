package com.google.sample.cloudvision.custom_objects;

/**
 * Created by Simone on 05/06/2017.
 */

public class Trash {

    private String type;
    private String typeOfWaste;
    private String name;
    private int id;
    private double maxVolume;
    private double currentVolume;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeOfWaste() {
        return typeOfWaste;
    }

    public void setTypeOfWaste(String typeOfWaste) {
        this.typeOfWaste = typeOfWaste;
    }

    public String getStoreName() {
        return name;
    }

    public void setStoreName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public double getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(double currentVolume) {
        this.currentVolume = currentVolume;
    }

    public int getPercentage(){
        return (int)(currentVolume/maxVolume * 100);
    }

    @Override
    public String toString() {
        return this.name + " " + "(id:" + this.getId() + ") : " + getPercentage() + "%";
    }
}
