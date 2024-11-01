package Model.history;

import java.util.List;

public class HistoryResponse {
    private Location location;
    private Forecast forecast;

    // Getter và Setter
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public static class Location {
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        private String tz_id;
        private String localtime;

        // Getter và Setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
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

        public String getTz_id() {
            return tz_id;
        }

        public void setTz_id(String tz_id) {
            this.tz_id = tz_id;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }
    }

    public static class Forecast {
        private List<ForecastDay> forecastday;

        // Getter và Setter
        public List<ForecastDay> getForecastday() {
            return forecastday;
        }

        public void setForecastday(List<ForecastDay> forecastday) {
            this.forecastday = forecastday;
        }
    }

    public static class ForecastDay {
        private String date;
        private Day day;

        // Getter và Setter
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Day getDay() {
            return day;
        }

        public void setDay(Day day) {
            this.day = day;
        }
    }

    public static class Day {
        private double maxtemp_c; // Nhiệt độ tối đa
        private double mintemp_c; // Nhiệt độ tối thiểu
        private Condition condition; // Sử dụng class Condition để ánh xạ

        // Getter và Setter
        public double getMaxtemp_c() {
            return maxtemp_c;
        }

        public void setMaxtemp_c(double maxtemp_c) {
            this.maxtemp_c = maxtemp_c;
        }

        public double getMintemp_c() {
            return mintemp_c;
        }

        public void setMintemp_c(double mintemp_c) {
            this.mintemp_c = mintemp_c;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }
    }

    public static class Condition {
        private String text; // Văn bản mô tả điều kiện thời tiết
        private String icon; // Đường dẫn đến icon thời tiết
        private int code; // Mã điều kiện thời tiết

        // Getter và Setter
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
