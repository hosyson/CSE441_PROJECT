package repository;

import Model.FivedaysWeather.Forecast5DaysResponse;
import api.WeatherApiService;
import retrofit2.Call;
import retrofit2.Callback; // Nhập khẩu Callback
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Weather5DaysForecastRepository {
    private WeatherApiService api;

    public Weather5DaysForecastRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WeatherApiService.class);
    }

    public void getFiveDaysForecast(String apiKey, String location, int days, Callback<Forecast5DaysResponse> callback) {
        api.getForecast5DaysResponse(apiKey, location, days).enqueue(new Callback<Forecast5DaysResponse>() {
            @Override
            public void onResponse(Call<Forecast5DaysResponse> call, Response<Forecast5DaysResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Response failed with code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Forecast5DaysResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}
