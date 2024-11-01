package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weather.R;
import java.util.List;
import Model.history.ForecastDay;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<ForecastDay> historyList;

    public HistoryAdapter(List<ForecastDay> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_day, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        ForecastDay forecastDay = historyList.get(position);

        // Kiểm tra xem forecastDay và day có khác null không
        if (forecastDay != null && forecastDay.getDay() != null) {
            holder.tvDate.setText(forecastDay.getDate() != null ? forecastDay.getDate() : "N/A");

            // Lấy nhiệt độ tối đa và tối thiểu
            double maxTemp = forecastDay.getDay().getMaxTempC(); // Sử dụng getMaxTempC()
            double minTemp = forecastDay.getDay().getMinTempC(); // Sử dụng getMinTempC()
            holder.tvTemp.setText(String.format("Max Temp: %.1f°C, Min Temp: %.1f°C", maxTemp, minTemp));

            // Lấy điều kiện thời tiết
            if (forecastDay.getDay().getCondition() != null) {
                holder.tvCondition.setText(String.format("Condition: %s", forecastDay.getDay().getCondition().getText()));
            } else {
                holder.tvCondition.setText("Condition: N/A");
            }
        } else {
            // Thiết lập giá trị mặc định trong trường hợp dữ liệu bị thiếu
            holder.tvDate.setText("N/A");
            holder.tvTemp.setText("Temperature: N/A");
            holder.tvCondition.setText("Condition: N/A");
        }
    }

    @Override
    public int getItemCount() {
        return historyList != null ? historyList.size() : 0;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTemp, tvCondition;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvCondition = itemView.findViewById(R.id.tvCondition);
        }
    }
}
