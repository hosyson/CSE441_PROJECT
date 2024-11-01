package ui.history;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import Model.history.Condition;
import Model.history.Day;
import Model.history.ForecastDay;
import adapter.HistoryAdapter;

public class HistoryFragment extends Fragment {
    private RecyclerView rvHistory;
    private HistoryAdapter historyAdapter;
    private ArrayList<ForecastDay> historyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rvHistory = view.findViewById(R.id.recyclerViewHistory);

        // Gán LayoutManager cho RecyclerView
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));  // Thêm dòng này

        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyList);
        rvHistory.setAdapter(historyAdapter);

        // Fetch weather history for Hanoi
        getHistoryData("Hanoi", "2023-10-31"); // Replace with your desired date
        return view;
    }


    private void getHistoryData(String location, String date) {
        String url = "https://api.weatherapi.com/v1/history.json?key=04b3351e3f114bd7abd233330243110&q=Hanoi&dt=2024-10-01" + "&q=" + location + "&dt=" + date;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject forecast = jsonResponse.getJSONObject("forecast");
                            JSONArray forecastDays = forecast.getJSONArray("forecastday");

                            for (int i = 0; i < forecastDays.length(); i++) {
                                JSONObject forecastObject = forecastDays.getJSONObject(i);
                                String date = forecastObject.getString("date");
                                JSONObject day = forecastObject.getJSONObject("day");

                                double maxTemp = day.getDouble("maxtemp_c");
                                double minTemp = day.getDouble("mintemp_c");
                                String conditionText = day.getJSONObject("condition").getString("text");

                                ForecastDay forecastDay = new ForecastDay();
                                forecastDay.setDate(date);
                                Day dayModel = new Day();
                                dayModel.setMaxTempC(maxTemp);
                                dayModel.setMinTempC(minTemp);
                                Condition condition = new Condition();
                                condition.setText(conditionText);
                                dayModel.setCondition(condition);
                                forecastDay.setDay(dayModel);

                                historyList.add(forecastDay);
                            }
                            historyAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e("History Error", "JSON parsing error: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("History Error", "Error occurred: " + error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }


}
