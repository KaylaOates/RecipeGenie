package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }
}