package com.example.weather;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

    floatingActionButton = findViewById(R.id.floatingActionButton4);
    floatingActionButton.setOnClickListener(view -> {
        Intent intent = new Intent(MainActivity.this, AddCity.class);
        startActivity(intent);
    });
    }
}
