package Model.history;

public class Day {
    private double maxTempC;  // Biến để lưu trữ nhiệt độ tối đa
    private double minTempC;  // Biến để lưu trữ nhiệt độ tối thiểu
    private Condition condition;  // Biến để lưu trữ điều kiện thời tiết

    // Phương thức để lấy nhiệt độ tối đa
    public double getMaxTempC() {
        return maxTempC;
    }

    // Phương thức để thiết lập nhiệt độ tối đa
    public void setMaxTempC(double maxTempC) {
        this.maxTempC = maxTempC;
    }

    // Phương thức để lấy nhiệt độ tối thiểu
    public double getMinTempC() {
        return minTempC;
    }

    // Phương thức để thiết lập nhiệt độ tối thiểu
    public void setMinTempC(double minTempC) {
        this.minTempC = minTempC;
    }

    // Phương thức để lấy điều kiện thời tiết
    public Condition getCondition() {
        return condition;
    }

    // Phương thức để thiết lập điều kiện thời tiết
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
