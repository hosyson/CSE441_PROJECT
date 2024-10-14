package com.example.weather;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

        searchBar.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // SearchView gained focus
                Toast.makeText(this, "Gained focus", Toast.LENGTH_SHORT).show();
            } else {
                // SearchView lost focus
                Toast.makeText(this, "Lost focus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();  // Get the currently focused view
            if (v != null && searchBar.isFocused()) {
                // Check if the touch is outside the SearchView
                int[] searchViewLocation = new int[2];
                searchBar.getLocationOnScreen(searchViewLocation);

                float x = ev.getRawX();
                float y = ev.getRawY();

                if (x < searchViewLocation[0] || x > (searchViewLocation[0] + searchBar.getWidth())
                        || y < searchViewLocation[1] || y > (searchViewLocation[1] + searchBar.getHeight())) {
                    // Hide the keyboard and clear focus from SearchView
                    searchBar.clearFocus();
                    View view = this.getCurrentFocus();
                    hideKeyboard(view);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}