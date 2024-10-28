package repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import Model.currentWeather.Weather;
import Model.currentWeather.WeatherData;
import Model.currentWeather.WeatherResponse;
import api.WeatherApiService;
import database.WeatherDao;
import database.WeatherDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.LocationManager;

import java.io.IOException;

public class WeatherRepository {
    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/";
    private static final String API_KEY = "fb0111d3c64c419989a6e5aaa834fdf6";
    private static final String TAG = "WeatherRepository";
    private final WeatherApiService apiService;
    private final WeatherDao weatherDao;
    private final LocationManager locationManager;
    private final Context context;

    public WeatherRepository(Context context) {
        this.context = context;

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(WeatherApiService.class);
        WeatherDatabase db = WeatherDatabase.getInstance(context);
        weatherDao = db.weatherDao();
        locationManager = new LocationManager(context);
    }

    public LiveData<Weather> getCurrentWeather(String city, Double latitude, Double longitude) {
        MutableLiveData<Weather> weatherData = new MutableLiveData<>();

        Call<WeatherResponse> call;
        if (city != null) {
            call = apiService.getCurrentWeatherByCity(city, API_KEY);
        } else {
            call = apiService.getCurrentWeatherByLocation(latitude, longitude, API_KEY);
        }

        Log.d(TAG, "API Call URL: " + call.request().url());

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Raw Response: " + new Gson().toJson(response.body()));

                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse.getData() != null && !weatherResponse.getData().isEmpty()) {
                        Weather weather = convertResponseToWeather(weatherResponse.getData().get(0));
                        weatherData.setValue(weather);

                    } else {
                        Log.e(TAG, "Response data is null or empty");
                        weatherData.setValue(null);
                    }
                } else {
                    try {
                        Log.e(TAG, "Error Response: " +
                                (response.errorBody() != null ? response.errorBody().string() : "null"));
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading error body", e);
                    }
                    weatherData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, "API call failed", t);
                weatherData.setValue(null);
            }
        });

        return weatherData;
    }

    private Weather convertResponseToWeather(WeatherData data) {
        Weather weather = new Weather();
        weather.setCityName(data.getCityName());
        weather.setLat(data.getLat());
        weather.setLon(data.getLon());
        weather.setDatetime(data.getDatetime());
        weather.setDescription(data.getWeather().getDescription());
        weather.setTemperature(data.getTemperature());
        weather.setHumidity(data.getHumidity());
        weather.setPrecipProbability(data.getPrecipProbability());
        weather.setWindSpeed(data.getWindSpeed());
        weather.setIcon(data.getWeather().getIcon());
        return weather;
    }

    private boolean isNetworkAvailable(Context context) {
        // Implement logic to check network availability
        return true;
    }

    private void showNoInternetDialog() {
        // Implement logic to show no internet dialog
    }
}



