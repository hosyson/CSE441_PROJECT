package ui.home;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import Model.currentWeather.Weather;
import repository.WeatherRepository;
import utils.LocationManager;

public class HomeViewModel extends AndroidViewModel {
    private static final String TAG = "HomeViewModel";
    private LocationManager locationManager;
    private final WeatherRepository weatherRepository;
    private final MutableLiveData<Weather> weatherData;

    public HomeViewModel(@NonNull Application application, LocationManager locationManager) {
        super(application);
        Log.d(TAG, "HomeViewModel initialized");
        this.locationManager = locationManager;
        weatherData = new MutableLiveData<Weather>();
        weatherRepository = new WeatherRepository(application.getApplicationContext());
        initializeWeatherData();
    }

    private void initializeWeatherData() {
        Log.d(TAG, "Initializing weather data");

        // Cập nhật vị trí trước khi lấy dữ liệu thời tiết
        locationManager.updateCurrentLocation(location -> {
            if (location != null) {
                Log.d(TAG, String.format("Location updated - Lat: %f, Long: %f",
                        location.getLatitude(),
                        location.getLongitude()));
                loadWeatherData();
            } else {
                Log.e(TAG, "Location is null after update");
            }
        });
    }

    private void loadWeatherData() {
        Double latitude = locationManager.getLatitude();
        Double longitude = locationManager.getLongitude();

        Log.d(TAG, String.format("Loading weather data with Lat: %s, Long: %s",
                latitude, longitude));

        if (latitude == null || longitude == null) {
            Log.e(TAG, "Location coordinates are null");
            return;
        }

        LiveData<Weather> repoData = weatherRepository.getCurrentWeather(null, latitude, longitude);

        repoData.observeForever(weatherResponse -> {
            Log.d(TAG, "Received weather update: " +
                    (weatherResponse != null ? weatherResponse.toString() : "null"));
            weatherData.setValue(weatherResponse);
        });
    }

    public LiveData<Weather> getWeatherData() {

        return weatherData;
    }
}

