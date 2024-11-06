package com.example.androidlabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView catImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ImageView and ProgressBar
        catImageView = findViewById(R.id.catImageView);
        progressBar = findViewById(R.id.progressBar);

        // Start the AsyncTask to load the image and update progress
        new CatImages().execute();
    }

    private class CatImages extends AsyncTask<String, Integer, String> {
        private Bitmap currentCatImage;

        @Override
        protected String doInBackground(String... params) {
            while (!isCancelled()) { // Check if the task is cancelled to stop the infinite loop
                try {
                    // Fetch the cat picture JSON
                    URL url = new URL("https://cataas.com/cat?json=true");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in = new BufferedInputStream(connection.getInputStream());

                    // Convert InputStream to String (JSON)
                    String json = convertStreamToString(in);
                    System.out.println(json);
                    JSONObject jsonObject = new JSONObject(json);
                    String catId = jsonObject.getString("_id");
                    String catImageUrl = "https://cataas.com/cat/" + catId;

                    // Check if image exists locally and load or download it
                    if (imageExists(catId)) {
                        currentCatImage = loadImageFromStorage(catId);
                    } else {
                        currentCatImage = downloadImage(catImageUrl);
                        saveImageToStorage(catId, currentCatImage);
                    }

                    // Update progress bar and image view
                    for (int i = 0; i < 100; i++) {
                        publishProgress(i);
                        Thread.sleep(30);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            if (currentCatImage != null) {
                catImageView.setImageBitmap(currentCatImage);
            }
        }

        private String convertStreamToString(InputStream is) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        private boolean imageExists(String id) {
            File file = new File(getExternalFilesDir(null), id + ".jpg");
            return file.exists();
        }

        private Bitmap downloadImage(String urlString) throws IOException {
            URL url = new URL(urlString);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }

        private void saveImageToStorage(String id, Bitmap image) {
            File file = new File(getExternalFilesDir(null), id + ".jpg");
            try (FileOutputStream out = new FileOutputStream(file)) {
                image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Bitmap loadImageFromStorage(String id) {
            File file = new File(getExternalFilesDir(null), id + ".jpg");
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
    }
}
