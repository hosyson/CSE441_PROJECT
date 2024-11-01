package Model.FivedaysWeather;

public class Forecast {
    private String date; // Ngày
    private String dayOfWeek; // Ngày trong tuần
    private String temperature; // Nhiệt độ
    private String weatherCondition; // Điều kiện thời tiết
    private String iconUrl; // Đường dẫn đến biểu tượng thời tiết

    public Forecast(String date, String dayOfWeek, String temperature, String weatherCondition, String iconUrl) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.iconUrl = iconUrl;
    }

    public String getDate() {
        return date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date='" + date + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", temperature='" + temperature + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
