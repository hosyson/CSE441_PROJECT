package database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "weather")
public class WeatherEntity {

    @PrimaryKey
    @NonNull
    public String date;

    public String city_name;
    public double lat;
    public double lon;
    public double current_temp;
    public double avg_temp;
    public String weather_description;
    public String hourly_forecast; // Store as JSON string
    public int aqi; // Air Quality Index

    // Constructor
    public WeatherEntity() {
        this.date = new Date().toString();
    }

    // Getters
    public String getCity_name() {
        return city_name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getCurrent_temp() {
        return current_temp;
    }

    public double getAvg_temp() {
        return avg_temp;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public String getHourly_forecast() {
        return hourly_forecast;
    }

    public int getAqi() {
        return aqi;
    }

    public String getDate() {
        return date;
    }

    // Setters
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setCurrent_temp(double current_temp) {
        this.current_temp = current_temp;
    }

    public void setAvg_temp(double avg_temp) {
        this.avg_temp = avg_temp;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public void setHourly_forecast(String hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
