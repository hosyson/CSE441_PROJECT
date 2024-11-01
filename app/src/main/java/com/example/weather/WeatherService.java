package com.example.weather;

import Model.FivedaysWeather.Forecast5DaysResponse;
import Model.currentWeather.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("current.json")
    Call<WeatherResponse> getCurrentWeather(
            @Query("q") String cityName,
            @Query("key") String apiKey);

    @GET("forecast.json")
    Call<Forecast5DaysResponse> getFiveDayForecast(
            @Query("q") String cityName,
            @Query("days") int days,
            @Query("key") String apiKey);

}
