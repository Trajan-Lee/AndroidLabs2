package com.example.androidlabs;

public class TodoItem {
    private String text;
    private boolean urgent;

    // Constructor
    public TodoItem(String text, boolean urgent) {
        this.text = text;
        this.urgent = urgent;
    }

    // Getter methods
    public String getText() {
        return text;
    }

    public boolean isUrgent() {
        return urgent;
    }
}
