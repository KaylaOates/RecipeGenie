package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;


public class TextToSpeechActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt);

        textView = findViewById(R.id.chatOutput);
        Intent intent = getIntent();
        if (intent != null) {
            String textToSpeak = intent.getStringExtra("textToSpeak");
            if (textToSpeak != null && !textToSpeak.isEmpty()) {
                textView.setText(textToSpeak);
            }
        }

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Log.d("TextToSpeechActivity", "TextToSpeech initialization successful");

                    int languageResult = textToSpeech.setLanguage(Locale.getDefault());
                    if (languageResult == TextToSpeech.LANG_MISSING_DATA || languageResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Handle the case where the language is not available or not supported
                        // For simplicity, we'll use the default language.
                    }

                    speakText();
                } else {
                    Log.e("TextToSpeechActivity", "TextToSpeech initialization failed with status: " + status);
                }
            }

        });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // called when started
            }

            @Override
            public void onDone(String utteranceId) {
                // Called when text-to-speech is done
                finish();
            }

            @Override
            public void onError(String utteranceId) {
                // Called when there's an error in text-to-speech
            }


        });

        Button buttonNext = findViewById(R.id.ContinueToWeeklyMenu);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                Intent intent = new Intent(TextToSpeechActivity.this, DailyMenu.class);
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
                startActivity(intent);
            }
        });


    }

    private void speakText() {
        String textToRead = textView.getText().toString();

        if (textToSpeech != null && textToSpeech.isLanguageAvailable(Locale.getDefault()) != TextToSpeech.LANG_NOT_SUPPORTED) {
            textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}