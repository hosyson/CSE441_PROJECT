package Model.FivedaysWeather;

import java.util.List;

import Model.Forecast;

public class Forecast5DaysResponse {
    private List<Forecast> dailyForecasts;

    public List<Forecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<Forecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }
}
