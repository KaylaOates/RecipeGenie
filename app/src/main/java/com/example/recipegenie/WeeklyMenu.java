package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WeeklyMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_menu);

        Button SundayButton = findViewById(R.id.Sunday);
        Button MondayButton = findViewById(R.id.Monday);
        Button TuesdayButton = findViewById(R.id.Tuesday);
        Button WednesdayButton = findViewById(R.id.Wednesday);
        Button ThursdayButton = findViewById(R.id.Thursday);
        Button FridayButton = findViewById(R.id.Friday);
        Button SatrudayButton = findViewById(R.id.Saturday);

        SundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(WeeklyMenu.this, DailyMenu.class);
                startActivity(intent);
            }
        });
        MondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(WeeklyMenu.this, DailyMenu.class);
                startActivity(intent);
            }
        });
        TuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(WeeklyMenu.this, DailyMenu.class);
                startActivity(intent);
            }
        });
        WednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(WeeklyMenu.this, DailyMenu.class);
                startActivity(intent);
            }
        });
        ThursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(WeeklyMenu.this, DailyMenu.class);
                startActivity(intent);
            }
        });
        FridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(WeeklyMenu.this, DailyMenu.class);
                startActivity(intent);
            }
        });
        SatrudayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(WeeklyMenu.this, DailyMenu.class);
                startActivity(intent);
            }
        });

    }
}