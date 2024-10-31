package repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import api.WeatherApiService;
import database.WeatherDao;
import database.WeatherDatabase;
import database.WeatherEntity;
import Model.WeatherResponse;
import Model.ForecastResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.LocationManager;
import java.util.ArrayList;
import java.util.List;

public class WeatherRepository {
    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/";
    private static final String API_KEY = "fb0111d3c64c419989a6e5aaa834fdf6";
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

    // New method to fetch weather for the current location
    public LiveData<WeatherResponse> getWeatherForCurrentLocation() {
        Double latitude = locationManager.getLatitude();
        Double longitude = locationManager.getLongitude();
        return getTodayWeather(null, latitude, longitude);
    }

    // Method to fetch today's weather
    public LiveData<WeatherResponse> getTodayWeather(String city, Double latitude, Double longitude) {
        MutableLiveData<WeatherResponse> data = new MutableLiveData<>();

        if (!isNetworkAvailable(context)) {
            WeatherEntity cachedWeather = weatherDao.getLatestWeather();
            if (cachedWeather != null) {
                data.setValue(convertEntityToResponse(cachedWeather));
                showNoInternetDialog();
            } else {
                data.setValue(null);
                showNoInternetDialog();
            }
            return data;
        }

        Call<WeatherResponse> call = (city != null) ?
                apiService.getCurrentWeatherByCity(city, API_KEY) :
                apiService.getCurrentWeatherByLocation(latitude, longitude, API_KEY);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse currentWeather = response.body();

                    // Optionally, fetch forecast data for better accuracy
                    apiService.getDailyForecastByLocation(currentWeather.getLat(), currentWeather.getLon(), 1, API_KEY)
                            .enqueue(new Callback<ForecastResponse>() {
                                @Override
                                public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        data.setValue(currentWeather);
                                        saveWeatherData(currentWeather);
                                    } else {
                                        data.setValue(null);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ForecastResponse> call, Throwable t) {
                                    data.setValue(null);
                                }
                            });
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    // Method to fetch 11-day forecast
    public LiveData<List<ForecastResponse.DailyForecast>> get11DaysForecast(String city, Double latitude, Double longitude) {
        MutableLiveData<List<ForecastResponse.DailyForecast>> data = new MutableLiveData<>();
        List<ForecastResponse.DailyForecast> result = new ArrayList<>();

        if (!isNetworkAvailable(context)) {
            List<WeatherEntity> cachedWeather = weatherDao.getWeatherFor11Days();
            if (!cachedWeather.isEmpty()) {
                for (WeatherEntity entity : cachedWeather) {
                    result.add(convertEntityToDailyForecast(entity));
                }
                data.setValue(result);
                showNoInternetDialog();
            } else {
                data.setValue(null);
                showNoInternetDialog();
            }
            return data;
        }

        Call<ForecastResponse> forecastCall = (city != null) ?
                apiService.getDailyForecastByCity(city, 11, API_KEY) :
                apiService.getDailyForecastByLocation(latitude, longitude, 11, API_KEY);

        forecastCall.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    // Convert WeatherEntity to WeatherResponse
    private WeatherResponse convertEntityToResponse(WeatherEntity entity) {
        WeatherResponse response = new WeatherResponse();
        response.setCityName(entity.getCity_name());
        response.setLat(entity.getLat());
        response.setLon(entity.getLon());
        response.setAvg_temp(entity.getAvg_temp());
        response.setDate(entity.getDate());
        response.setAqi(entity.getAqi());
        return response;
    }

    // Save WeatherResponse into the database
    private void saveWeatherData(WeatherResponse weather) {
        WeatherEntity entity = new WeatherEntity();
        entity.setCity_name(weather.getCityName());
        entity.setLat(weather.getLat());
        entity.setLon(weather.getLon());
        entity.setAvg_temp(weather.getAvg_temp());
        entity.setDate(weather.getDate());
        entity.setAqi(weather.getAqi());
        weatherDao.insertWeather(entity);
    }

    // Convert WeatherEntity to DailyForecast
    private ForecastResponse.DailyForecast convertEntityToDailyForecast(WeatherEntity entity) {
        ForecastResponse.DailyForecast forecast = new ForecastResponse.DailyForecast();
        forecast.setTemp(entity.getAvg_temp());
        forecast.setDatetime(entity.getDate());
        return forecast;
    }

    private boolean isNetworkAvailable(Context context) {
        // Implement network availability check
        return true;
    }

    private void showNoInternetDialog() {
        // Show a dialog when no internet is available
    }
}
