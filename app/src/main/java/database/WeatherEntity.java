package database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather")
public class WeatherEntity {
    @PrimaryKey
    @NonNull public String city;
    public double temperature;
    public String description;
    public long timestamp;

    public WeatherEntity() {
        city = "Ha Noi";
    }

    // Constructor, getters, and setters
}
