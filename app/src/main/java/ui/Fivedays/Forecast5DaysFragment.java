package ui.Fivedays;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Model.Forecast;
import adapter.ForecastAdapter;

public class Forecast5DaysFragment extends Fragment {
    private RecyclerView rvForecast;
    private ForecastAdapter forecastAdapter;
    private ArrayList<Forecast> forecastList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast5days, container, false);
        rvForecast = view.findViewById(R.id.recyclerView);
        forecastList = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(getContext(), forecastList);
        rvForecast.setAdapter(forecastAdapter);

        // Thay thế bằng latitude và longitude thực tế
        getForecastData(12.9716, 77.5946); // Ví dụ: Bangalore, Ấn Độ
        return view;
    }

    private void getForecastData(double latitude, double longitude) {
        String url = "https://api.weather.com/v3/wx/forecast/daily/5day?apiKey=04b3351e3f114bd7abd233330243110&geocode=" + latitude + "," + longitude + "&format=json";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray dailyForecasts = jsonResponse.getJSONArray("dailyForecasts");
                            for (int i = 0; i < dailyForecasts.length(); i++) {
                                JSONObject forecastObject = dailyForecasts.getJSONObject(i);
                                String date = forecastObject.getString("date");
                                String dayOfWeek = forecastObject.getString("dayOfWeek");
                                String temperature = forecastObject.getJSONObject("temperature").getString("max");
                                String weatherCondition = forecastObject.getString("narrative");
                                String iconUrl = forecastObject.getString("icon"); // Điều chỉnh theo logic lấy biểu tượng của bạn

                                Forecast forecast = new Forecast(date, dayOfWeek, temperature, weatherCondition, iconUrl);
                                forecastList.add(forecast);
                            }
                            forecastAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e("Forecast Error", "Lỗi phân tích JSON: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Forecast Error", "Đã xảy ra lỗi: " + error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }
}
