package com.example.androidlabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar = new ProgressBar(R.id.progressBar);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private class CatImages extends AsyncTask<Void, Integer, Void> {
        private Bitmap currentCatImage;

        @Override
        protected Void doInBackground(Void... voids) {
            // Load the local image from the drawable folder
            currentCatImage = BitmapFactory.decodeResource(getResources(), R.drawable.person);

            // Simulate an infinite loop with progress updates
            while (true) {
                // Give time to appreciate the image by updating the progress bar
                for (int i = 0; i <= 100; i++) {
                    publishProgress(i);
                    try {
                        Thread.sleep(30); // Adjust the speed as desired
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update the progress bar
            progressBar.setProgress(values[0]);

            // Update the ImageView with the current image
            if (currentCatImage != null) {
                catImageView.setImageBitmap(currentCatImage);
            }
        }
    }

}
