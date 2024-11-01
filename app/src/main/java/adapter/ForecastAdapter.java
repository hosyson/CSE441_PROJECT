package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;

import java.util.List;

import Model.Forecast;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private Context context;
    private List<Forecast> forecastList;

    public ForecastAdapter(Context context, List<Forecast> forecastList) {
        this.context = context;
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast forecast = forecastList.get(position);
        holder.tvDate.setText(forecast.getDate());
        holder.tvTemperature.setText(forecast.getTemperature() + "°C");
        holder.tvCondition.setText(forecast.getWeatherCondition());
        // Tải biểu tượng thời tiết bằng thư viện như Glide hoặc Picasso
        // Glide.with(context).load(forecast.getIconUrl()).into(holder.imgWeatherIcon);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvTemperature;
        TextView tvCondition;
        ImageView imgWeatherIcon;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvCondition = itemView.findViewById(R.id.tvCondition);
            imgWeatherIcon = itemView.findViewById(R.id.imgWeather);
        }
    }
}
