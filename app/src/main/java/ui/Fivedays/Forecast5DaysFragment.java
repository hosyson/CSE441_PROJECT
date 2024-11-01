package ui.Fivedays;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager; // Đảm bảo bạn import LinearLayoutManager
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

import Model.FivedaysWeather.Forecast;
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

        // Thiết lập LayoutManager
        rvForecast.setLayoutManager(new LinearLayoutManager(getContext()));

        forecastList = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(getContext(), forecastList);
        rvForecast.setAdapter(forecastAdapter);

        // Thay thế bằng latitude và longitude thực tế
        getForecastData(12.9716, 77.5946); // Ví dụ: Bangalore, Ấn Độ
        return view;
    }

    private void getForecastData(double latitude, double longitude) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=04b3351e3f114bd7abd233330243110&q=" + latitude + "," + longitude + "&days=5&aqi=no&alerts=no";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray dailyForecasts = jsonResponse.getJSONObject("forecast").getJSONArray("forecastday"); // Đảm bảo lấy đúng mảng

                            // Xóa danh sách cũ trước khi thêm dữ liệu mới
                            forecastList.clear();

                            for (int i = 0; i < dailyForecasts.length(); i++) {
                                JSONObject forecastObject = dailyForecasts.getJSONObject(i);
                                String date = forecastObject.getString("date");

                                // Thay đổi cách lấy "maxtemp_c" và "condition" cho đúng với cấu trúc JSON
                                String temperature = forecastObject.getJSONObject("day").getString("maxtemp_c");
                                String weatherCondition = forecastObject.getJSONObject("day").getJSONObject("condition").getString("text");
                                String iconUrl = forecastObject.getJSONObject("day").getJSONObject("condition").getString("icon");

                                // Thêm thông tin vào đối tượng Forecast
                                Forecast forecast = new Forecast(date, "", temperature, weatherCondition, iconUrl);
                                forecastList.add(forecast);
                            }
                            forecastAdapter.notifyDataSetChanged(); // Cập nhật adapter
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
