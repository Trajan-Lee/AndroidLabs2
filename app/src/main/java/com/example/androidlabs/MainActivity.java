package com.example.androidlabs;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etxtName;
    public static final String PREFS_NAME = "UserPreferences";
    public static final String NAME_KEY = "userName";
    private static final int NAME_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etxtName = findViewById(R.id.etxtName);
        Button btnNext = findViewById(R.id.btnNext);

        // Lod name from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedName = preferences.getString(NAME_KEY, "");
        if (!savedName.isEmpty()) {
            etxtName.setText(savedName);
        }

        // Set Listener for btnNext
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the name entered by the user
                String name = etxtName.getText().toString();

                // Create an Intent to launch NameActivity
                Intent intent = new Intent(MainActivity.this, NameActivity.class);
                intent.putExtra("userName", name);

                // Start NameActivity with the request code
                startActivityForResult(intent, NAME_REQUEST);
            }
        });
    }

    // Handle data received from NameActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NAME_REQUEST && resultCode == RESULT_OK && data != null) {
            String updatedName = data.getStringExtra("updatedName");
            etxtName.setText(updatedName); // Update the EditText with the returned name
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the current name in SharedPreferences when the activity is paused
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAME_KEY, etxtName.getText().toString());
        editor.apply();
    }
}
