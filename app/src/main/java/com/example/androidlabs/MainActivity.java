package com.example.androidlabs;

import android.os.Bundle;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate specific content for MainActivity
        getLayoutInflater().inflate(R.layout.activity_main, findViewById(R.id.activity_content));
    }
}
