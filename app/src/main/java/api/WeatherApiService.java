package api;

import Model.DailyWeather;
import Model.currentWeather.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("current")
    Call<WeatherResponse> getCurrentWeatherByCity(
            @Query("city") String city,
            @Query("key") String apiKey
    );

    @GET("current")
    Call<WeatherResponse> getCurrentWeatherByLocation(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("key") String apiKey
    );

    @GET("history/hourly")
    Call<DailyWeather> getHourlyForecastByCity(
            @Query("city") String city,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("key") String apiKey
    );

    @GET("history/hourly")
    Call<DailyWeather> getHourlyForecastByLocation(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("key") String apiKey
    );

    @GET("forecast/daily")
    Call<DailyWeather> getDailyForecastByCity(
            @Query("city") String city,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("key") String apiKey
    );

    @GET("forecast/daily")
    Call<DailyWeather> getDailyForecastByLocation(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("days") int days,
            @Query("key") String apiKey
    );

    @GET("history/daily")
    Call<DailyWeather> getHistoryWeatherByCity(
            @Query("city") String city,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("key") String apiKey
    );

    @GET("history/daily")
    Call<DailyWeather> getHistoryWeatherByLocation(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("key") String apiKey
    );
}
