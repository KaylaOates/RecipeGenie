package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Toast;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Locale;
import android.widget.Button;

public class SpeechRecognitionActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt);

        // Initialize SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                // Called when the speech recognition is ready to start.
            }

            @Override
            public void onBeginningOfSpeech() {
                // Called when the user has started speaking.
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // Called when the RMS value of the audio input changes.
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // Called when sound has been received by the recognizer.
            }

            @Override
            public void onEndOfSpeech() {
                // Called when the user has finished speaking.
            }

            @Override
            public void onError(int error) {
                // Called when an error occurs during speech recognition.
                Toast.makeText(SpeechRecognitionActivity.this, "Error during speech recognition", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                // Called when the recognition is successful.
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);
                    // Do something with the recognized text (e.g., display it in a TextView)
                    // textView.setText(recognizedText);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // Called when partial recognition results are available.
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // Called when an event related to speech recognition occurs.
            }
        });

        // Setup other UI elements and event listeners in your existing activity
        Button startRecognitionButton = findViewById(R.id.recognitionButton);
        startRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechRecognition();
            }
        });
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            speechRecognizer.startListening(intent);
        } catch (Exception e) {
            // Handle exceptions, if any
            Toast.makeText(this, "Error starting speech recognition", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        // Release SpeechRecognizer resources when your activity is destroyed.
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        super.onDestroy();
    }
}