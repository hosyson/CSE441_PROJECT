package Model;
public class WeatherData {
    private double minTempC;
    private double maxTempC;
    private double minTempF;
    private double maxTempF;
    private double currentTempC;
    private double currentTempF;
    private int rainChance;
    private double windSpeedKPH;
    private double windSpeedMPH;
    private int humidity;
    private String date;
    private String time;
    private String description;
    private String cityName;

    public WeatherData(double minTempC, double maxTempC, double minTempF, double maxTempF, double currentTempC, double currentTempF, int rainChance, double windSpeedKPH, double windSpeedMPH, int humidity, String date, String time, String description, String cityName) {
        this.minTempC = minTempC;
        this.maxTempC = maxTempC;
        this.minTempF = minTempF;
        this.maxTempF = maxTempF;
        this.currentTempC = currentTempC;
        this.currentTempF = currentTempF;
        this.rainChance = rainChance;
        this.windSpeedKPH = windSpeedKPH;
        this.windSpeedMPH = windSpeedMPH;
        this.humidity = humidity;
        this.date = date;
        this.time = time;
        this.description = description;
        this.cityName = cityName;
    }

    public double getMinTempC() {
        return minTempC;
    }

    public void setMinTempC(double minTempC) {
        this.minTempC = minTempC;
    }

    public double getMaxTempC() {
        return maxTempC;
    }

    public void setMaxTempC(double maxTempC) {
        this.maxTempC = maxTempC;
    }

    public double getMinTempF() {
        return minTempF;
    }

    public void setMinTempF(double minTempF) {
        this.minTempF = minTempF;
    }

    public double getMaxTempF() {
        return maxTempF;
    }

    public void setMaxTempF(double maxTempF) {
        this.maxTempF = maxTempF;
    }

    public double getCurrentTempC() {
        return currentTempC;
    }

    public void setCurrentTempC(double currentTempC) {
        this.currentTempC = currentTempC;
    }

    public double getCurrentTempF() {
        return currentTempF;
    }

    public void setCurrentTempF(double currentTempF) {
        this.currentTempF = currentTempF;
    }

    public int getRainChance() {
        return rainChance;
    }

    public void setRainChance(int rainChance) {
        this.rainChance = rainChance;
    }

    public double getWindSpeedKPH() {
        return windSpeedKPH;
    }

    public void setWindSpeedKPH(double windSpeedKPH) {
        this.windSpeedKPH = windSpeedKPH;
    }

    public double getWindSpeedMPH() {
        return windSpeedMPH;
    }

    public void setWindSpeedMPH(double windSpeedMPH) {
        this.windSpeedMPH = windSpeedMPH;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
