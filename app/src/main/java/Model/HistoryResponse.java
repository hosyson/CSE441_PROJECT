package Model;

import java.util.List;

public class HistoryResponse {
    private String city_name;
    private String country_code;
    private double lat;
    private double lon;
    private String timezone;
    private List<DailyHistory> data;

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

    public List<DailyHistory> getData() {
        return data;
    }

    public void setData(List<DailyHistory> data) {
        this.data = data;
    }

    public static class DailyHistory {
        private String datetime;
        private double temp;

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
    }
}