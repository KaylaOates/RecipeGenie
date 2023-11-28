package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DailyMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_menu);

        Button BreakfastButton = findViewById(R.id.Breakfast);
        Button LunchtButton = findViewById(R.id.Lunch);
        Button DinnerButton = findViewById(R.id.Dinner);
        BreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(DailyMenu.this, MealPage.class);
                startActivity(intent);
            }
        });
        LunchtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(DailyMenu.this, MealPage.class);
                startActivity(intent);
            }
        });
        DinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(DailyMenu.this, MealPage.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed(View view){
        finish();
    }

}