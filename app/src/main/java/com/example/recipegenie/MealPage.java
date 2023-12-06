package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MealPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_page);

        // Retrieve the meal plan from SharedPreferences
        String mealPlan = getMealPlanFromPrefs();

        // Update the UI with the meal plan
        if (mealPlan != null) {
            TextView mealPlanTextView = findViewById(R.id.mealTextView);
            mealPlanTextView.setText(mealPlan);
        }
    }
    private String getMealPlanFromPrefs() {
        // Retrieve the meal plan from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MealPlanPrefs", Context.MODE_PRIVATE);
        return preferences.getString("mealPlan", null);
    }

    public void onBackPressed(View view){
        finish();
    }
}