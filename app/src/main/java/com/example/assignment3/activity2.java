package com.example.assignment3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class activity2 extends AppCompatActivity {
    private ListView universityListView;
    private List<University> universities = new ArrayList<>();
    private UniversityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        universityListView = findViewById(R.id.universityListView);

        String selectedCountry = getIntent().getStringExtra("country");
        getSupportActionBar().setTitle(selectedCountry + "'s Universities");

        FetchUniversitiesTask fetchUniversitiesTask = new FetchUniversitiesTask(selectedCountry);
        fetchUniversitiesTask.fetchUniversities();

        universityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                University selectedUniversity = fetchUniversitiesTask.universities.get(position);
                openActivity3(selectedUniversity);
            }
        });
    }

    private void openActivity3(University university) {
        Intent intent = new Intent(this, activity3.class);
        intent.putExtra("university", university);
        startActivity(intent);
    }


    private class FetchUniversitiesTask {
        private String selectedCountry;
        private List<University> universities = new ArrayList<>();

        public FetchUniversitiesTask(String selectedCountry) {
            this.selectedCountry = selectedCountry;
        }

        public void fetchUniversities() {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    String encodedCountry = URLEncoder.encode(selectedCountry, "UTF-8");
                    String apiUrl = "http://universities.hipolabs.com/search?country=" + encodedCountry;

                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println("Connection Successful"); // Print the apiUrl to the console
                    } else {
                        System.out.println("Connection UnSuccessful"); // Print the apiUrl to the console
                    }
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();

                    String jsonResponse = stringBuilder.toString();

                    runOnUiThread(() -> {
                        try {
                            JSONArray jsonArray = new JSONArray(jsonResponse);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonContent = jsonArray.getJSONObject(i);
                                String universityName = jsonContent.getString("name");
                                String universityDomain = jsonContent.getString("domains");
                                Log.i("Domain", universityDomain);
                                University university = new University(universityName, universityDomain);
                                universities.add(university);
                            }

                            adapter = new UniversityAdapter(activity2.this, universities);
                            universityListView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
