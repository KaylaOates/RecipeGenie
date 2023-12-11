package com.example.recipegenie;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GroceryStores extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationManager locationManager;
    //EditText GroceryStoresList;

    TextView store1;
    TextView store2;
    TextView store3;
    TextView store4;
    TextView store5;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_stores);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //GroceryStoresList = findViewById(R.id.groceryStoresList);
        store1 = findViewById(R.id.store1);
        store2 = findViewById(R.id.store2);
        store3 = findViewById(R.id.store3);
        store4 = findViewById(R.id.store4);
        store5 = findViewById(R.id.store5);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        // Check if the app has permission to access fine location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            // Request location updates
            requestLocationUpdates();
        }
    }

    private void requestLocationUpdates() {
        // Check if the app has permission to access fine location (again, just to be sure)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Request location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Called when the location is updated
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Log.d("GroceryStores", "LOCATION CHANGED");
            // Now you have the user's coordinates (latitude, longitude)
            // Proceed to the next step: sending the coordinates to OpenStreetMap or another service.
            sendCoordinatesToOverpassAPI(latitude, longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Called when the status of the location provider changes
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Called when the location provider is enabled
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Called when the location provider is disabled
        }
    };

    private void sendCoordinatesToOverpassAPI(double latitude, double longitude) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String overpassQuery = "[out:json];" +
                        "node(around:7000," + latitude + "," + longitude + ")['shop'='supermarket'];" +
                        "out;";

                URL url = new URL("https://overpass-api.de/api/interpreter?data=" + URLEncoder.encode(overpassQuery, "UTF-8"));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    Scanner scanner = new Scanner(urlConnection.getInputStream());
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                    parseOverpassResponse(response.toString());

                    runOnUiThread(() -> {
                        //Log.d("GroceryStores", response.toString());
                        //GroceryStoresList.setText(response.toString());
                    });
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void parseOverpassResponse(String jsonResponse) {
        try {
            JSONObject responseJson = new JSONObject(jsonResponse);
            Log.d("GroceryStores", "Response: " + responseJson.toString());

            if (responseJson.has("elements")) {
                JSONArray elementsArray = responseJson.getJSONArray("elements");
                Log.d("GroceryStores", "PARSING");

                int numStores = 5;
                if (elementsArray.length() < 5)
                {
                    numStores = elementsArray.length();
                }
                for (int i = 0; i < numStores; i++) {
                    JSONObject element = elementsArray.getJSONObject(i);
                    Log.d("GroceryStores", "ENTERED LOOP");

                    if (element.has("tags")) {
                        JSONObject tags = element.getJSONObject("tags");
                        String name = tags.optString("name", "");
                        String street = tags.optString("addr:street", "");

                        // Do something with the name and street information (e.g., display in UI)
                        if (i == 0)
                        {
                            if (street.equals(""))
                            {
                                store1.setText(name);
                            }
                            else {
                                store1.setText(name + " on " + street);
                            }

                        }
                        else if (i == 1)
                        {
                            if (street.equals(""))
                            {
                                store2.setText(name);
                            }
                            else {
                                store2.setText(name + " on " + street);
                            }
                        }
                        else if (i == 2)
                        {
                            if (street.equals(""))
                            {
                                store3.setText(name);
                            }
                            else {
                                store3.setText(name + " on " + street);
                            }
                        }
                        else if (i == 3)
                        {
                            if (street.equals(""))
                            {
                                store4.setText(name);
                            }
                            else {
                                store4.setText(name + " on " + street);
                            }
                        }
                        else
                        {
                            if (street.equals(""))
                            {
                                store5.setText(name);
                            }
                            else {
                                store5.setText(name + " on " + street);
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        Log.d("SupermarketInfo", "Name: " + name + ", Street: " + street);
                    }
                }
            } else {
                Log.d("GroceryStores", "No 'elements' array found in the response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop receiving location updates when the activity is destroyed
        locationManager.removeUpdates(locationListener);
    }
}