package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoadingPage extends AppCompatActivity {

    private EditText chatInput;
    private TextView chatResponse;
    private Button sendButton;
    private final String openAiKey = "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"; // Replace with your actual OpenAI API key
    //my key: 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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt);
        chatInput = findViewById(R.id.chatInput);
        chatResponse = findViewById(R.id.chatOutput);
        sendButton = findViewById(R.id.generateButton);
        sendButton.setOnClickListener(v -> {
            String prompt = chatInput.getText().toString();
            if (!prompt.isEmpty()) {
                // Pass the chatResponse TextView to the ChatGptTask
                new ChatGptTask(chatResponse, openAiKey).execute(prompt);
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