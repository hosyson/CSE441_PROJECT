package api;
import model.WeatherResponse;
import model.ForecastResponse;
import model.HistoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("current")
    Call<WeatherResponse> getCurrentWeather(
            @Query("city") String city,
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("key") String apiKey
    );

    @GET("forecast/daily")
    Call<ForecastResponse> getForecastWeather(
            @Query("city") String city,
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("days") int days,
            @Query("key") String apiKey
    );

    @GET("history/daily")
    Call<HistoryResponse> getHistoryWeather(
            @Query("city") String city,
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("key") String apiKey
    );
}