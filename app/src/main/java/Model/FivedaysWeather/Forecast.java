package Model.FivedaysWeather;

public class Forecast {
    private String date;
    private Day day;

    public String getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }

    public class Day {
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

    public class Condition {
        private String icon;

        public String getIcon() {
            return "https:" + icon; // icon là đường dẫn tương đối, thêm "https:" để tạo URL đầy đủ
        }
    }
}

