package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                String sendText = "Breakfast";
                intent.putExtra("CURR_MEAL_TEXT", sendText);
                startActivity(intent);
            }
        });
        LunchtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(DailyMenu.this, MealPage.class);
                String sendText = "Lunch";
                intent.putExtra("CURR_MEAL_TEXT", sendText);
                startActivity(intent);
            }
        });
        DinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(DailyMenu.this, MealPage.class);
                String sendText = "Dinner";
                intent.putExtra("CURR_MEAL_TEXT", sendText);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed(View view){
        finish();
    }

}