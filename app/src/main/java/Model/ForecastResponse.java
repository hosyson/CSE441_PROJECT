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


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<DailyForecast> getData() {
        return data;
    }

    public void setData(List<DailyForecast> data) {
        this.data = data;
    }

    public static class DailyForecast {
        private String datetime;
        private double temp;
        private double max_temp;
        private double min_temp;

        // Getters and setters

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getMax_temp() {
            return max_temp;
        }

        public void setMax_temp(double max_temp) {
            this.max_temp = max_temp;
        }

        public double getMin_temp() {
            return min_temp;
        }

        public void setMin_temp(double min_temp) {
            this.min_temp = min_temp;
        }
    }
}