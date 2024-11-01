package com.example.weather;

import Model.history.ForecastDay;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHistory;
    private HistoryAdapter adapter;
    private List<ForecastDay> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HistoryAdapter(historyList);
        recyclerViewHistory.setAdapter(adapter);

        fetchWeatherHistory();
    }

    private void fetchWeatherHistory() {
        // Gọi API Weather và cập nhật `historyList` với dữ liệu lịch sử nhận được
        String url = "https://api.weatherapi.com/v1/history.json?key=04b3351e3f114bd7abd233330243110&" +
                "q=YourLocation&dt=SelectedDate";

        // Sau khi lấy dữ liệu thành công
        historyList.clear();
        historyList.addAll(historyResponse.getForecast().getForecastDay());
        adapter.notifyDataSetChanged();
    }
}
