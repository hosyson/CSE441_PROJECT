package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weather.R;

import java.util.ArrayList;
import java.util.List;

import model.WeatherResponse;

public class TodayTempItemAdapter extends ArrayAdapter<WeatherResponse> {

    Activity context;
    ArrayList<WeatherResponse> arrayList;
    int layoutId;

    public TodayTempItemAdapter(Activity context, int layoutId, ArrayList<WeatherResponse> arrayList) {
        super(context, layoutId, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        TextView txtTodayHour = convertView.findViewById(R.id.txtTodayHour);
        TextView txtTodayTemp = convertView.findViewById(R.id.txtTodayTemp);
        WeatherResponse weatherResponse = arrayList.get(position);
        List<WeatherResponse.HourlyForecast> hourlyForecasts;
        hourlyForecasts = weatherResponse.getHourly();
        txtTodayHour.setText(hourlyForecasts.get(position).getTimestamp());
        txtTodayTemp.setText(String.valueOf(hourlyForecasts.get(position).getTemp()));
        return convertView;
    }
}
