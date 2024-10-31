package api;

import Model.WeatherResponse;
import Model.ForecastResponse;
import Model.HistoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    // Get current weather by city
    @GET("current")
    Call<WeatherResponse> getCurrentWeatherByCity(
            @Query("city") String city,
            @Query("key") String apiKey
    );

    // Get current weather by location (latitude and longitude)
    @GET("current")
    Call<WeatherResponse> getCurrentWeatherByLocation(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("key") String apiKey
    );

    // Get daily forecast by city
    @GET("forecast/daily")
    Call<ForecastResponse> getDailyForecastByCity(
            @Query("city") String city,
            @Query("days") int days,
            @Query("key") String apiKey
    );

    // Get daily forecast by location (latitude and longitude)
    @GET("forecast/daily")
    Call<ForecastResponse> getDailyForecastByLocation(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("days") int days,
            @Query("key") String apiKey
    );

    // Get weather history by city
    @GET("history/daily")
    Call<HistoryResponse> getHistoryWeatherByCity(
            @Query("city") String city,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("key") String apiKey
    );

    // Get weather history by location (latitude and longitude)
    @GET("history/daily")
    Call<HistoryResponse> getHistoryWeatherByLocation(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("key") String apiKey
    );
}
