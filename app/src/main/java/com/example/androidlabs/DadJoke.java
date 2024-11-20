package com.example.androidlabs;

import android.os.Bundle;

public class DadJoke extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate specific content for DadJoke activity
        getLayoutInflater().inflate(R.layout.activity_dad_joke, findViewById(R.id.activity_content));
    }
}
