package com.example.androidlabs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class TodoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TodoItem> todoList;

    public TodoAdapter(Context context, ArrayList<TodoItem> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    // Returns the number of items in the list
    @Override
    public int getCount() {
        return todoList.size();
    }

    // Returns the item at the specified position
    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    // Returns the ID for the item at the specified position (just returning the position here)
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Returns the view for each item in the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for each list item (todo_item.xml)
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.todo_item, parent, false);
        }

        // Get the current TodoItem
        TodoItem currentItem = (TodoItem) getItem(position);

        // Find the TextView in the inflated layout and set the text
        TextView todoTextView = convertView.findViewById(R.id.todoTextView);
        todoTextView.setText(currentItem.getText());

        // If the item is marked as urgent, change the background and text color
        if (currentItem.isUrgent()) {
            convertView.setBackgroundColor(Color.RED);
            todoTextView.setTextColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);  // Default background
            todoTextView.setTextColor(Color.BLACK);  // Default text color
        }

        return convertView;
    }
}
