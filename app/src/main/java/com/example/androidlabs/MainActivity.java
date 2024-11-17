package com.example.androidlabs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CharacterAdapter adapter;
    private List<Characters> characters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchStarWarsCharactersTask().execute();
        listView = findViewById(R.id.listView);
        adapter = new CharacterAdapter(this, characters);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Check if the device is a phone or tablet
            FrameLayout frameLayout = findViewById(R.id.frameLayout); // Check for tablet layout

            if (frameLayout == null) {  // On phone, start EmptyActivity
                Intent intent = new Intent(MainActivity.this, EmptyActivity.class);
                Bundle bundle = new Bundle();
                // Pass relevant data to the bundle
                bundle.putString("character_name", characters.get(position).getName());
                // Add other character details to the bundle
                intent.putExtras(bundle);
                startActivity(intent);
            } else {  // On tablet, replace fragment directly
                DetailsFragment detailsFragment = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("character_name", characters.get(position).getName());
                bundle.putString("character_height", characters.get(position).getHeight());
                bundle.putString("character_mass", characters.get(position).getMass());
                detailsFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, detailsFragment)
                        .commit();
            }
        });
    }

    private class FetchStarWarsCharactersTask extends AsyncTask<Void, Void, List<Characters>> {

        @Override
        protected List<Characters> doInBackground(Void... voids) {
            List<Characters> characterList = new ArrayList<>();
            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonStringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }

                String jsonResponse = jsonStringBuilder.toString();
                Log.d("FetchStarWarsCharacters", "JSON Response: " + jsonResponse);

                JSONObject jsonObject = new JSONObject(jsonStringBuilder.toString());
                JSONArray results = jsonObject.getJSONArray("results");

                // Parse JSON and populate characterList
                for (int i = 0; i < results.length(); i++) {
                    JSONObject characterJson = results.getJSONObject(i);
                    String name = characterJson.getString("name");
                    String height = characterJson.getString("height");
                    String mass = characterJson.getString("mass");

                    // Create a Characters object and add it to the list
                    Characters character = new Characters(name, height, mass);
                    characterList.add(character);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return characterList;
        }

        @Override
        protected void onPostExecute(List<Characters> result) {
            if (result != null) {
                characters.clear();
                characters.addAll(result);  // Add fetched characters
                adapter.notifyDataSetChanged();  // Notify adapter that data has changed
            }
        }
    }

}