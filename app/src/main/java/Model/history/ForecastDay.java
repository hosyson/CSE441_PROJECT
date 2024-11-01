package Model.history;

public class ForecastDay {
    private String date;
    private long date_epoch;
    private Day day;

    // Getters and Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public long getDateEpoch() { return date_epoch; }
    public void setDateEpoch(long date_epoch) { this.date_epoch = date_epoch; }

    public Day getDay() { return day; }
    public void setDay(Day day) { this.day = day; }
}
