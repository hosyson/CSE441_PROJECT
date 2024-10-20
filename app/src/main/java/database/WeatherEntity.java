package database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "weather")
public class WeatherEntity {
    @PrimaryKey
    @NonNull public String date;

    public String city_name;
    public double lat;
    public double lon;
    public double current_temp;
    public double avg_temp;
    public String weather_description;
    public String hourly_forecast; // Store as JSON string

    public WeatherEntity() {
        date = new Date().toString();
    }

    // Add getters and setters
}