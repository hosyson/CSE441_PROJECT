package database;

import androidx.room.*;
import java.util.List;

@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity weather);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WeatherEntity> weatherList);

    @Query("SELECT * FROM weather ORDER BY date DESC LIMIT 1")
    WeatherEntity getLatestWeather();

    @Query("SELECT * FROM weather ORDER BY date DESC LIMIT 11")
    List<WeatherEntity> getWeatherFor11Days();

    // Add other necessary queries
}