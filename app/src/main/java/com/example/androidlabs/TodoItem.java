package com.example.androidlabs;

public class TodoItem {
    private int id;
    private String text;
    private boolean urgent;

    // Constructor
    public TodoItem(int id, String text, boolean urgent) {
        this.id = id;
        this.text = text;
        this.urgent = urgent;
    }

    // Getter methods

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isUrgent() {
        return urgent;
    }
}
