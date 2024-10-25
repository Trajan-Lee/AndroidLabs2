package com.example.androidlabs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> todoList;
    private TodoAdapter adapter;
    private EditText editText;
    private Switch switchUrgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the List and Adapter
        todoList = new ArrayList<>();
        adapter = new TodoAdapter(this, todoList);

        // Reference views
        ListView listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        switchUrgent = findViewById(R.id.switchUrgent);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);

        // Set OnClickListener for the "Add" button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Show the AlertDialog
            showDeleteDialog(position);
            return true;
        });
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(getString(R.string.dialog_message, position));

        // Positive button to delete the item
        builder.setPositiveButton(R.string.delete, (dialog, which) -> {
            // Remove the item and refresh the adapter
            todoList.remove(position);
            adapter.notifyDataSetChanged();
        });

        // Negative button to cancel
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        // Show the AlertDialog
        builder.create().show();
    }

    // Method to add a new todo item
    private void addItem() {
        String text = editText.getText().toString().trim();
        boolean urgent = switchUrgent.isChecked();

        if (!text.isEmpty()) {
            // Create a new TodoItem and add it to the list
            TodoItem newItem = new TodoItem(text, urgent);
            todoList.add(newItem);

            // Notify the adapter of the change
            adapter.notifyDataSetChanged();

            // Clear the EditText
            editText.setText("");
        }
    }
}