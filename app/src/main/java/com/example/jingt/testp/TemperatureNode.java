package com.example.jingt.testp;

public class TemperatureNode {
    private double temperature;
    private double windSpeed;
    private double windDirection;
    public String date;
    private long dateSeconds;

    TemperatureNode(String date) {
        this.date = date;
    }
    TemperatureNode(long dateSeconds) {
        this.dateSeconds= dateSeconds;
    }

    public TemperatureNode(double temperature, double windSpeed, double windDirection, String date) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.date = date;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public String getDate() { return date; }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public long getDateSeconds() {
        return dateSeconds;
    }

    public void setDateSeconds(long dateSeconds) {
        this.dateSeconds = dateSeconds;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
