package com.example.androidlabs;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etxtName;

    // Declare ActivityResultLauncher
    private final ActivityResultLauncher<Intent> getResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_CANCELED) {
                    // User wants to change their name
                    etxtName.setText("");  // Clear the EditText
                } else if (result.getResultCode() == RESULT_OK) {
                    // User is happy, close the app
                    finish();  // Close the activity and app
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etxtName = findViewById(R.id.etxtName);
        Button btnNext = findViewById(R.id.btnNext);

        // Load the name from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String savedName = prefs.getString("user_name", null);
        if (savedName != null) {
            etxtName.setText(savedName);
        }

        // Set onClick listener for "Next" button
        btnNext.setOnClickListener(view -> {
            // Get the name from the EditText
            String name = etxtName.getText().toString();

            // Create an Intent to start NameActivity
            Intent intent = new Intent(MainActivity.this, NameActivity.class);
            intent.putExtra("userName", name);

            // Launch NameActivity using the ActivityResultLauncher
            getResult.launch(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the current name to SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_name", etxtName.getText().toString());
        editor.apply();
    }
}
