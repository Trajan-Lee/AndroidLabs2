package com.example.androidlabs;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        // Get the data passed from MainActivity
        String characterName = getIntent().getStringExtra("character_name");
        String characterHeight = getIntent().getStringExtra("character_height");
        String characterMass = getIntent().getStringExtra("character_mass");

        // Create the DetailsFragment and pass data through Bundle
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("character_name", characterName);
        bundle.putString("character_height", characterHeight);
        bundle.putString("character_mass", characterMass);
        detailsFragment.setArguments(bundle);

        // Replace the FrameLayout with the DetailsFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, detailsFragment);
        transaction.commit();
    }
}
