package com.example.androidlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> todoList;
    private TodoAdapter adapter;
    private EditText editText;
    private Switch switchUrgent;
    private int idCount;

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
        loadTodos();
    }

    private void loadTodos() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.getTableName(), null, null, null, null, null, null);

        printCursor(cursor);
        todoList.clear();
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.getColumnId()));
            String todoText = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnTodoText()));
            boolean isUrgent = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.getColumnUrgent())) > 0;
            todoList.add(new TodoItem(ID, todoText, isUrgent));
        }
        Collections.sort(todoList, Comparator.comparingInt(TodoItem::getId));
        if(!todoList.isEmpty()){
            idCount=todoList.get(todoList.size()-1).getId() + 1; //+1 to get the next id in line
        } else {idCount=1;}
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(getString(R.string.dialog_message, position+1));

        // Positive button to delete the item
        builder.setPositiveButton(R.string.delete, (dialog, which) -> {
            // Remove the item and refresh the adapter
            todoList.remove(position);
            deleteTodoFromDatabase(todoList.get(position));
            adapter.notifyDataSetChanged();
        });

        // Negative button to cancel
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        // Show the AlertDialog
        builder.create().show();
    }

    private void deleteTodoFromDatabase(TodoItem todo) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.getTableName(), DatabaseHelper.getColumnId() + "=?", new String[]{String.valueOf(todo.getId())});
        db.close();
    }

    // Method to add a new todo item
    private void addItem() {
        String text = editText.getText().toString().trim();
        boolean urgent = switchUrgent.isChecked();

        if (!text.isEmpty()) {
            // Create a new TodoItem and add it to the list
            addTodoToDatabase(text, urgent);
            TodoItem newItem = new TodoItem(idCount, text, urgent);
            todoList.add(newItem);


            // Notify the adapter of the change
            adapter.notifyDataSetChanged();

            // Clear the EditText
            editText.setText("");
        }
    }
    private void addTodoToDatabase(String todoText, boolean isUrgent) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.getColumnTodoText(), todoText);
        values.put(DatabaseHelper.getColumnUrgent() , isUrgent ? 1 : 0);

        db.insert(DatabaseHelper.getTableName(), null, values);
        db.close();
    }
    private void printCursor(Cursor c) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Log database version
        Log.d("Database Version", "Version: " + db.getVersion());

        //log cursor metadata
        Log.d("DatabaseDebug", "Number of Columns: " + c.getColumnCount());
        Log.d("DatabaseDebug", "Column Names: " + Arrays.toString(c.getColumnNames()));
        Log.d("DatabaseDebug", "Number of Results: " + c.getCount());

        //log each row
        while (c.moveToNext()) {
            StringBuilder row = new StringBuilder();
            for (int i = 0; i < c.getColumnCount(); i++) {
                row.append(c.getString(i)).append(" ");
            }
            Log.d("DatabaseDebug", "Row: " + row.toString().trim());
        }
        c.moveToFirst(); // Reset cursor if needed after logging
    }
}