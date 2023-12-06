package com.example.recipegenie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.Arrays;

public class ChatGptTask extends AsyncTask<String, Void, String> {
    private final TextView responseTextView;
    private final String apiKey;
    private final ProgressBar progressBar;
    private String breakfast = null;
    private String lunch = null;
    private String dinner = null;
    private final Button continueButton;

    public ChatGptTask(TextView responseTextView, String apiKey, ProgressBar progressBar, Button continueButton) {
        this.responseTextView = responseTextView;
        this.apiKey = apiKey;
        this.progressBar = progressBar;
        this.continueButton = continueButton; // Initialize button reference
    }

    @Override
    protected String doInBackground(String... strings) {
        // Initializse the OpenAI service with the API key
        OpenAiService service = new OpenAiService(apiKey);

        String modifiedPrompt = "You are a friendly and intelligent nutritional wellness coach communicating with a human. "
                + "Try to respond in a way that a health advisor would, using understandable language. "
                + "Generate three healthy meal ideas for the day, including breakfast, lunch, and dinner. Keep all the answers very short\n\n"
                + "User: " + strings[0];
        //Take into consideration the user's dietary preferences, allergies, and nutritional needs: " + userInput + ".

        // Create a chat completion request with the appropriate model
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .messages(Arrays.asList(new ChatMessage("user", modifiedPrompt)))
                .model("gpt-3.5-turbo") // This model is optimized for conversations
                .build();
        try {
            // Execute the request and get the response
            String response = service.createChatCompletion(request).getChoices().get(0).getMessage().getContent();

            // Process the response to extract the generated meal plan
            String mealPlan = processResponse(response);

            return mealPlan;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage(); // Return error message
        }
    }

    private String processResponse(String response) {
        //extract and format the meal plan from the response

        // Split the response into lines
        String[] lines = response.split("\n");

        // Iterate through each line and categorize the meal
        for (String line : lines) {
            if (line.toLowerCase().contains("breakfast")) {
                breakfast = line.trim();
            } else if (line.toLowerCase().contains("lunch")) {
                lunch = line.trim();
            } else if (line.toLowerCase().contains("dinner")) {
                dinner = line.trim();
            }
        }

        // Format the result
        StringBuilder result = new StringBuilder();
        if (breakfast != null) {
            result.append(breakfast).append("\n").append("\n");
        }
        if (lunch != null) {
            result.append(lunch).append("\n").append("\n");
        }
        if (dinner != null) {
            result.append(dinner).append("\n").append("\n");
        }

        return "Generated Meal Plan:\n" + "\n" + result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        // Update UI with the result on the main thread
        if (result != null && !result.isEmpty()) {
            responseTextView.setText(result);
            progressBar.setVisibility(View.GONE);
            saveMealPlanToPrefs(result);

            // Enable the ContinueToWeeklyMenu button
            continueButton.setEnabled(true);
            continueButton.setAlpha(1f);

        } else {
            responseTextView.setText("Error retrieving response, please try again.");
            progressBar.setVisibility(View.GONE);
        }
    }


    private void saveMealPlanToPrefs(String mealPlan) {
        // Use SharedPreferences to save the meal plan
        SharedPreferences preferences = responseTextView.getContext().getSharedPreferences("MealPlanPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mealPlan", mealPlan);
        editor.apply();
    }
}