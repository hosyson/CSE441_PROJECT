package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.weather.R;
import java.util.List;
import Model.FivedaysWeather.Forecast5DaysResponse;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<Forecast5DaysResponse.Forecastday> forecastList;

    public ForecastAdapter(List<Forecast5DaysResponse.Forecastday> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast5DaysResponse.Forecastday forecast = forecastList.get(position);
        holder.tvDate.setText(forecast.getDate());
        holder.tvTemperature.setText(
                forecast.getDay().getMaxtempC() + "°C / " + forecast.getDay().getMintempC() + "°C"
        );

        // Load icon từ URL với Glide
        Glide.with(holder.itemView.getContext())
                .load(forecast.getDay().getCondition().getIcon())
                .into(holder.imgWeatherIcon);
    }

    @Override
    public int getItemCount() {
        return forecastList != null ? forecastList.size() : 0;
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTemperature;
        ImageView imgWeatherIcon;

        ForecastViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            imgWeatherIcon = itemView.findViewById(R.id.imgWeatherIcon);
        }
    }
}
