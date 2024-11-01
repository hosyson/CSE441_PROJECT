package Model.history;

public class Day {
    private double maxtemp_c;
    private double mintemp_c;
    private double avgtemp_c;
    private Condition condition;

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

    public double getAvgTempC() {
        return avgtemp_c;
    }

    public void setAvgTempC(double avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    @Override
    public String toString() {
        return "Day{" +
                "maxtemp_c=" + maxtemp_c +
                ", mintemp_c=" + mintemp_c +
                ", avgtemp_c=" + avgtemp_c +
                ", condition=" + condition +
                '}';
    }

}
