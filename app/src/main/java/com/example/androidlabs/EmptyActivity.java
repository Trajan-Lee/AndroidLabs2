package com.example.androidlabs;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        // Create an instance of DetailsFragment
        DetailsFragment detailsFragment = new DetailsFragment();

        // Check if the fragment is being replaced for the first time
        if (savedInstanceState == null) {
            // Add the fragment to the FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, detailsFragment)
                    .commit();
        }
    }
}
