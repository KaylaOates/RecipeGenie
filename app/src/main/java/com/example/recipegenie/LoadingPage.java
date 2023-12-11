package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingPage extends AppCompatActivity {

    private EditText chatInput;
    private TextView chatResponse;
    private Button sendButton;

    private ProgressBar progressBar;

    private ImageView soundButton;

    //add chat gpt key here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt);

        chatInput = findViewById(R.id.chatInput);
        chatResponse = findViewById(R.id.chatOutput);
        sendButton = findViewById(R.id.generateButton);
        progressBar = findViewById(R.id.progressBar);
        soundButton = findViewById(R.id.speakButton);

        // Disable the ContinueToWeeklyMenu button initially
        Button buttonNext = findViewById(R.id.ContinueToWeeklyMenu);
        buttonNext.setEnabled(false);
        buttonNext.setAlpha(0.25f);

        sendButton.setOnClickListener(v -> {
            String prompt = chatInput.getText().toString();
            if (!prompt.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                new ChatGptTask(chatResponse, openAiKey, progressBar, buttonNext).execute(prompt);
            } else {
                chatResponse.setText("Please enter a prompt.");
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(LoadingPage.this, DailyMenu.class);
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
                startActivity(intent);
            }
        });
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                String textToSpeak = chatResponse.getText().toString();
                Intent intent = new Intent(LoadingPage.this, TextToSpeechActivity.class);
                intent.putExtra("textToSpeak", textToSpeak);
                startActivity(intent);
                overridePendingTransition(0, 0); // 0 for no animation
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                String userSpeech = data.getStringExtra("userSpeech");
                chatInput.setText(userSpeech);
            }
        }
    }
}