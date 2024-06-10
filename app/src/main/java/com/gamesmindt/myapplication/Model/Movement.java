package com.gamesmindt.myapplication.Model;

public class Movement {
    private String name;
    private String effect;
    private String accuracy;
    private String power;

    public Movement(String name, String effect, String accuracy, String power) {
        this.name = name;
        this.effect = effect;
        this.accuracy = accuracy;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
