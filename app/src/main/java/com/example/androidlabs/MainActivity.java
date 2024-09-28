package com.example.androidlabs;

import static com.example.androidlabs.R.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.activity_main_constraint);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //button click listener
        TextView tv1 = findViewById(R.id.textView);
        EditText ev1 = findViewById(R.id.editTextText);
        Button submitButton = findViewById(R.id.button);
        submitButton.setOnClickListener(v -> {
            String inputtext = ev1.getText().toString();
            tv1.setText(inputtext);
            Toast.makeText(MainActivity.this, getString(R.string.buttontoast) , Toast.LENGTH_SHORT).show();
        });


        //checkbox listener
        CheckBox cb1 = findViewById(R.id.checkBox);
        cb1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Snackbar snackbar = Snackbar.make(buttonView, isChecked ? getString(R.string.checkon) : getString(R.string.checkoff), Snackbar.LENGTH_LONG)
                    .setAction("Undo", v -> {
                        revertcheck(isChecked);
                    });
            snackbar.show();
        });

    }
    //was giving error on setChecked due to static reference on non-static var,
    private void revertcheck(boolean checkcheck) {
        CheckBox cb1 = findViewById(R.id.checkBox);
        cb1.setChecked(!checkcheck);
    }
}