package Model;

import java.util.List;

public class DailyWeather {
    private String date;
    private double temperature;
    private String icon;

    // Constructors
    public DailyWeather() {}

    // Getters and Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}