package Model.FivedaysWeather;

import java.util.List;

public class Forecast5DaysResponse {
    private Location location;
    private Forecast forecast;

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

    public static class Forecast {
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getForecastday() {
            return forecastday;
        }

        public void setForecastday(List<ForecastDay> forecastday) {
            this.forecastday = forecastday;
        }
    }

    public static class Location {
        private String name;
        private String country;
        // Thêm các trường khác nếu cần

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class ForecastDay {
        private String date;
        private Day day;

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
        private double maxtemp_c;
        private double mintemp_c;
        // Thêm các trường khác nếu cần

        public double getMaxTempC() {
            return maxtemp_c;
        }

        public void setMaxTempC(double maxtemp_c) {
            this.maxtemp_c = maxtemp_c;
        }

        public double getMinTempC() {
            return mintemp_c;
        }

        public void setMinTempC(double mintemp_c) {
            this.mintemp_c = mintemp_c;
        }
    }
}
