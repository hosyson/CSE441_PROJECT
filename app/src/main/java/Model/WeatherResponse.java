package Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("city_name")
    private String cityName;

    @SerializedName("country_code")
    private String countryCode;

    private double lat;
    private double lon;
    private String timezone;

    private CurrentWeather current;
    private List<HourlyForecast> hourly;
    private double avg_temp;
    private String date;

    private int aqi; // Air Quality Index

    // Getters and setters
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    public List<HourlyForecast> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyForecast> hourly) {
        this.hourly = hourly;
    }

    public double getAvg_temp() {
        return avg_temp;
    }

    public void setAvg_temp(double avg_temp) {
        this.avg_temp = avg_temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public static class CurrentWeather {
        private double temp;
        private double app_temp;
        private int rh;
        private double wind_spd;
        private String wind_cdir_full;
        private double precip;
        private String weather_description;

        // Getters and setters
        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getApp_temp() {
            return app_temp;
        }

        public void setApp_temp(double app_temp) {
            this.app_temp = app_temp;
        }

        public int getRh() {
            return rh;
        }

        public void setRh(int rh) {
            this.rh = rh;
        }

        public double getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(double wind_spd) {
            this.wind_spd = wind_spd;
        }

        public String getWind_cdir_full() {
            return wind_cdir_full;
        }

        public void setWind_cdir_full(String wind_cdir_full) {
            this.wind_cdir_full = wind_cdir_full;
        }

        public double getPrecip() {
            return precip;
        }

        public void setPrecip(double precip) {
            this.precip = precip;
        }

        public String getWeather_description() {
            return weather_description;
        }

        public void setWeather_description(String weather_description) {
            this.weather_description = weather_description;
        }
    }

    public static class HourlyForecast {
        private String timestamp;
        private double temp;
        private int rh;
        private double wind_spd;
        private double precip;
        private String weather_description;

        // Getters and setters
        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public int getRh() {
            return rh;
        }

        public void setRh(int rh) {
            this.rh = rh;
        }

        public double getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(double wind_spd) {
            this.wind_spd = wind_spd;
        }

        public double getPrecip() {
            return precip;
        }

        public void setPrecip(double precip) {
            this.precip = precip;
        }

        public String getWeather_description() {
            return weather_description;
        }

        public void setWeather_description(String weather_description) {
            this.weather_description = weather_description;
        }
    }
}
