package model;

import java.util.List;

public class ForecastResponse {
    private String city_name;
    private String country_code;
    private double lat;
    private double lon;
    private String timezone;
    private List<DailyForecast> data;

    // Getters and setters

    public static class DailyForecast {
        private String datetime;
        private double temp;
        private double max_temp;
        private double min_temp;
        private int rh; // Relative humidity
        private double wind_spd;
        private String wind_cdir_full;
        private String weather_description;

        // Getters and setters
    }
}
