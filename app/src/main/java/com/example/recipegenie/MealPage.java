package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MealPage extends AppCompatActivity {

    String BreakfastRecipe = "";
    String LunchRecipe = "";
    String DinnerRecipe = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_page);

        String receivedText = getIntent().getStringExtra("CURR_MEAL_TEXT");
        TextView currMealTextView = findViewById(R.id.CurrMeal); // Replace R.id.CurrMeal with your actual TextView ID
        currMealTextView.setText(receivedText);

        // Retrieve the meal plan from SharedPreferences
        String mealPlan = getMealPlanFromPrefs();

        // Update the UI with the meal plan
        if (mealPlan != null) {

            //split string between breakfast, lunch, and dinner:
            String[] lines = mealPlan.split("\n");
            for (String line : lines) {
                if (line.startsWith("Breakfast:")) {
                    BreakfastRecipe = line.substring("Breakfast: ".length());
                } else if (line.startsWith("Lunch:")) {
                    LunchRecipe = line.substring("Lunch: ".length());
                } else if (line.startsWith("Dinner:")) {
                    DinnerRecipe = line.substring("Dinner: ".length());
                }
            }

            //set the recipe depending on the current meal selected
            String currMeal = "not meal selected...";
            if(receivedText.equals("Breakfast")) {
                currMeal = BreakfastRecipe;
            } else if (receivedText.equals("Lunch")) {
                currMeal = LunchRecipe;
            } else if(receivedText.equals("Dinner")) {
                currMeal = DinnerRecipe;
            }

            TextView mealPlanTextView = findViewById(R.id.mealTextView);
            mealPlanTextView.setText(currMeal);
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