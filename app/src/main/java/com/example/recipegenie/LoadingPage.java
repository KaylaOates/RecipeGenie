package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingPage extends AppCompatActivity {

    private EditText chatInput;
    private TextView chatResponse;
    private Button sendButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt);

        chatInput = findViewById(R.id.chatInput);
        chatResponse = findViewById(R.id.chatOutput);
        sendButton = findViewById(R.id.generateButton);
        progressBar = findViewById(R.id.progressBar);

        sendButton.setOnClickListener(v -> {
            String prompt = chatInput.getText().toString();
            if (!prompt.isEmpty()) {
                // Show ProgressBar when the generate button is pressed
                progressBar.setVisibility(View.VISIBLE);

                // Pass the chatResponse TextView to the ChatGptTask
                new ChatGptTask(chatResponse, openAiKey, progressBar).execute(prompt);
            }
            else {
                chatResponse.setText("Please enter a prompt.");
            }
        });

        Button buttonNext = findViewById(R.id.ContinueToWeeklyMenu);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(LoadingPage.this, WeeklyMenu.class);
                startActivity(intent);
            }
        });
    }
}