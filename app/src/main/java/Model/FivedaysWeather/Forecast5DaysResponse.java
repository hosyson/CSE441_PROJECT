package Model.FivedaysWeather;

import java.util.List;

public class Forecast5DaysResponse {
    private Forecast forecast;

    public Forecast getForecast() {
        return forecast;
    }

    public static class Forecast {
        private List<Forecastday> forecastday;

        public List<Forecastday> getForecastday() {
            return forecastday;
        }
    }

    public static class Forecastday {
        private String date;
        private Day day;

        public String getDate() {
            return date;
        }

        public Day getDay() {
            return day;
        }
    }

    public static class Day {
        private double maxtemp_c;
        private double mintemp_c;
        private Condition condition;

        public double getMaxtempC() {
            return maxtemp_c;
        }

        public double getMintempC() {
            return mintemp_c;
        }

        public Condition getCondition() {
            return condition;
        }
    }

    public static class Condition {
        private String text;
        private String icon;

        public String getText() {
            return text;
        }

        public String getIcon() {
            return "https:" + icon; // Đảm bảo URL icon là đầy đủ
        }
    }
}
