package repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import api.WeatherApiService;
import database.WeatherDao;
import database.WeatherDatabase;
import database.WeatherEntity;
import model.WeatherResponse;
import model.ForecastResponse;
import model.HistoryResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.LocationManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeatherRepository {
    private static final String BASE_URL = " https://api.weatherbit.io/v2.0/current";
    private static final String API_KEY = "fb0111d3c64c419989a6e5aaa834fdf6";
    private final WeatherApiService apiService;
    private final WeatherDao weatherDao;
    private final LocationManager locationManager;
    private final Context context;

    public WeatherRepository(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(WeatherApiService.class);
        WeatherDatabase db = WeatherDatabase.getInstance(context);
        weatherDao = db.weatherDao();
        locationManager = new LocationManager(context);
    }

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
                apiService.getCurrentWeather(city, API_KEY) :
                apiService.getCurrentWeather(latitude, longitude, API_KEY);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse currentWeather = response.body();

                    // Fetch hourly forecast
                    apiService.getHourlyForecast(currentWeather.getLat(), currentWeather.getLon(), 24, API_KEY)
                            .enqueue(new Callback<WeatherResponse>() {
                                @Override
                                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        currentWeather.setHourly(response.body().getHourly());
                                        data.setValue(currentWeather);
                                        saveWeatherData(currentWeather);
                                    } else {
                                        data.setValue(null);
                                    }
                                }

                                @Override
                                public void onFailure(Call<WeatherResponse> call, Throwable t) {
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String endDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -5);
        String startDate = sdf.format(calendar.getTime());

        Call<HistoryResponse> historyCall = (city != null) ?
                apiService.getHistoryWeather(city, startDate, endDate, API_KEY) :
                apiService.getHistoryWeather(latitude, longitude, startDate, endDate, API_KEY);

        historyCall.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (HistoryResponse.DailyHistory history : response.body().getData()) {
                        result.add(convertHistoryToDailyForecast(history));
                    }

                    Call<ForecastResponse> forecastCall = (city != null) ?
                            apiService.getDailyForecast(city, 6, API_KEY) :
                            apiService.getDailyForecast(latitude, longitude, 6, API_KEY);

                    forecastCall.enqueue(new Callback<ForecastResponse>() {
                        @Override
                        public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                result.addAll(response.body().getData());
                                data.setValue(result);
                                saveWeatherFor11Days(result);
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
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<WeatherResponse> getWeatherForCurrentLocation() {
        MutableLiveData<WeatherResponse> data = new MutableLiveData<>();
        locationManager.getCurrentLocation(location -> {
            if (location != null) {
                getTodayWeather(null, location.getLatitude(), location.getLongitude()).observeForever(data::setValue);
            } else {
                data.setValue(null);
            }
        });
        return data;
    }

    private void saveWeatherData(WeatherResponse weatherResponse) {
        WeatherEntity weatherEntity = convertResponseToEntity(weatherResponse);
        weatherDao.insert(weatherEntity);
    }

    private void saveWeatherFor11Days(List<ForecastResponse.DailyForecast> dailyForecasts) {
        List<WeatherEntity> entities = new ArrayList<>();
        for (ForecastResponse.DailyForecast dailyForecast : dailyForecasts) {
            entities.add(convertDailyForecastToEntity(dailyForecast));
        }
        weatherDao.insertAll(entities);
    }

    // Implement these conversion methods
    private WeatherEntity convertResponseToEntity(WeatherResponse response) {
        // TODO: Implement conversion logic
        return new WeatherEntity();
    }

    private WeatherResponse convertEntityToResponse(WeatherEntity entity) {
        // TODO: Implement conversion logic
        return new WeatherResponse();
    }

    private WeatherEntity convertDailyForecastToEntity(ForecastResponse.DailyForecast dailyForecast) {
        // TODO: Implement conversion logic
        return new WeatherEntity();
    }

    private ForecastResponse.DailyForecast convertEntityToDailyForecast(WeatherEntity entity) {
        // TODO: Implement conversion logic
        return new ForecastResponse.DailyForecast();
    }

    private ForecastResponse.DailyForecast convertHistoryToDailyForecast(HistoryResponse.DailyHistory history) {
        // TODO: Implement conversion logic
        return new ForecastResponse.DailyForecast();
    }

    private boolean isNetworkAvailable(Context context) {
        // TODO: Implement network check logic
        return true;
    }

    private void showNoInternetDialog() {
        // TODO: Implement dialog showing logic
    }
}