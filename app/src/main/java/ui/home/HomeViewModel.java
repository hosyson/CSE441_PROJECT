package ui.home;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.app.Application;
import androidx.annotation.NonNull;

import Model.WeatherResponse;
import repository.WeatherRepository;

public class HomeViewModel extends AndroidViewModel {
    private final MutableLiveData<WeatherResponse> weatherData;
    private final WeatherRepository weatherRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        weatherData = new MutableLiveData<>();
        weatherRepository = new WeatherRepository(application.getApplicationContext());
        loadWeatherData();
    }

    // Method to load weather data from WeatherRepository
    private void loadWeatherData() {
        LiveData<WeatherResponse> repoData = weatherRepository.getWeatherForCurrentLocation();

        // Observe the repoData LiveData and update weatherData when repoData changes
        repoData.observeForever(weatherResponse -> weatherData.setValue(weatherResponse));
    }

    public LiveData<WeatherResponse> getWeatherData() {
        return weatherData;
    }
}
