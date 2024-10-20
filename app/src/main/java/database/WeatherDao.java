package database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity weather);

    @Query("SELECT * FROM weather WHERE city = :city")
    LiveData<WeatherEntity> getWeatherForCity(String city);

    @Query("SELECT * FROM weather")
    LiveData<List<WeatherEntity>> getAllWeather();
}
