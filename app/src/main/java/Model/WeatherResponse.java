package model;

public class WeatherResponse {
    private String city_name;
    private String country_code;
    private double lat;
    private double lon;
    private String timezone;
    private CurrentWeather data;

    // Getters and setters

    public static class CurrentWeather {
        private long ob_time;  // Thời gian quan sát
        private double temp;   // Nhiệt độ hiện tại
        private double app_temp;  // Nhiệt độ cảm nhận
        private int rh;        // Độ ẩm tương đối
        private double wind_spd;  // Tốc độ gió
        private int wind_dir;     // Hướng gió (độ)
        private String wind_cdir_full;  // Hướng gió (text)
        private double pres;    // Áp suất
        private double vis;     // Tầm nhìn
        private double clouds;  // Độ che phủ của mây
        private String weather_description;  // Mô tả thời tiết
        private double uv;      // Chỉ số UV
        private double precip;  // Lượng mưa

        // Getters and setters
    }

    // Constructors
    public WeatherResponse() {}

    // Các phương thức tiện ích khác nếu cần
}
