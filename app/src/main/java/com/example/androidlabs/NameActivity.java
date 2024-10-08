package com.example.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NameActivity extends AppCompatActivity {

    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        // Initialize views
        txtWelcome = findViewById(R.id.txtWelcome);
        Button btnNotName = findViewById(R.id.btnNotName);
        Button btnThanks = findViewById(R.id.btnThanks);

        // Get the name passed from MainActivity
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        // Update the txtWelcome with a personalized message
        if (userName != null && !userName.isEmpty()) {
            txtWelcome.setText("Welcome " + userName + "!");
        }

        // Set Listener for "Don't call me that" button
        btnNotName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the result to RESULT_CANCELED and finish the activity
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // Set Listener for "Thank you" button
        btnThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the result to RESULT_OK and finish the activity
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
