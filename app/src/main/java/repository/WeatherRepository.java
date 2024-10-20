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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeatherRepository {
    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/";
    private static final String API_KEY = "YOUR_API_KEY";
    private final WeatherApiService apiService;
    private final WeatherDao weatherDao;
    private final LocationManager locationManager;

    public WeatherRepository(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(WeatherApiService.class);
        WeatherDatabase db = WeatherDatabase.getInstance(context);
        weatherDao = db.weatherDao();
        locationManager = new LocationManager(context);
    }

    public LiveData<WeatherResponse> getCurrentWeather(String city, Double latitude, Double longitude) {
        MutableLiveData<WeatherResponse> data = new MutableLiveData<>();
        apiService.getCurrentWeather(city, latitude, longitude, API_KEY)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            data.setValue(response.body());
                            saveWeatherData(response.body());
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

    public LiveData<ForecastResponse> getForecastWeather(String city, Double latitude, Double longitude, int days) {
        MutableLiveData<ForecastResponse> data = new MutableLiveData<>();
        apiService.getForecastWeather(city, latitude, longitude, days, API_KEY)
                .enqueue(new Callback<ForecastResponse>() {
                    @Override
                    public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            data.setValue(response.body());
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

    public LiveData<HistoryResponse> getHistoryWeather(String city, Double latitude, Double longitude, int daysAgo) {
        MutableLiveData<HistoryResponse> data = new MutableLiveData<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();

        String endDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo);
        String startDate = sdf.format(calendar.getTime());

        apiService.getHistoryWeather(city, latitude, longitude, startDate, endDate, API_KEY)
                .enqueue(new Callback<HistoryResponse>() {
                    @Override
                    public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            data.setValue(response.body());
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
                getCurrentWeather(null, location.getLatitude(), location.getLongitude());
            } else {
                data.setValue(null);
            }
        });
        return data;
    }

    private void saveWeatherData(WeatherResponse weatherResponse) {
        WeatherEntity weatherEntity = new WeatherEntity();
        // Map WeatherResponse to WeatherEntity
        weatherDao.insert(weatherEntity);
    }
}