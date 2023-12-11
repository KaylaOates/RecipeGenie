package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.Manifest;
import java.util.ArrayList;
import java.util.Locale;
import android.widget.Button;



public class SpeechRecognitionActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private SpeechRecognizer speechRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt);

        // Check for RECORD_AUDIO permission at runtime
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
        }

        // Initialize SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                // Called when the speech recognition is ready to start.
                EditText userInput = findViewById(R.id.chatInput);
                userInput.setText("Listening...");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.e("SpeechRecognition", "listening...");
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
                String errorMessage;
                switch (error) {
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        errorMessage = "Network timeout during speech recognition";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        errorMessage = "Network error during speech recognition";
                        break;
                    case SpeechRecognizer.ERROR_AUDIO:
                        errorMessage = "Audio recording error during speech recognition";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        errorMessage = "No speech input detected";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        errorMessage = "No recognition result matched";
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        errorMessage = "Speech recognizer is busy";
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        errorMessage = "Insufficient permissions for speech recognition";
                        break;
                    default:
                        errorMessage = "Error during speech recognition. Error code: " + error;
                        break;
                }
                Log.e("SpeechRecognition", errorMessage);
                Toast.makeText(SpeechRecognitionActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                // Called when the recognition is successful.
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("userSpeech", recognizedText);
                    setResult(RESULT_OK, resultIntent);
                    finish();
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