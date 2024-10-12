package com.example.weather;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.WeatherData;
import adapter.CityAdapter;

public class AddCity extends AppCompatActivity {

    RecyclerView cityRecyclerView;
    SearchView searchBar;
    CityAdapter cityAdapter;

    private List<WeatherData> weatherDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_city);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityRecyclerView = findViewById(R.id.cityRecyclerView);
        searchBar = findViewById(R.id.searchBar);

        weatherDataList = new ArrayList<>();

        weatherDataList.add(new WeatherData(10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10, 10.0, 10.0, 10, "10/10/10", "10:10", "10", "10"));

        cityAdapter = new CityAdapter(weatherDataList);
        cityRecyclerView.setAdapter(cityAdapter);
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}