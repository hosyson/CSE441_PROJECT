package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;

import java.util.List;

import Model.WeatherData;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private final List<WeatherData> weatherDataList;

    public CityAdapter(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    @NonNull
    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.CityViewHolder holder, int position) {
        WeatherData weatherData = weatherDataList.get(position);

        holder.cityName.setText(weatherData.getCityName());
        holder.dayTemp.setText(weatherData.getMaxTempC() + "/" + weatherData.getMinTempC());
        holder.currentTemp.setText(String.valueOf(weatherData.getCurrentTempC()));
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {

        TextView cityName, dayTemp, currentTemp;;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.txtCity);
            dayTemp = itemView.findViewById(R.id.txtDayTemp);
            currentTemp = itemView.findViewById(R.id.txtCurrentTemp);
        }
    }
}
