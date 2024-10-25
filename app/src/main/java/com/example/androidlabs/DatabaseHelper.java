package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and Table information
    private static final String DATABASE_NAME = "todoDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "todos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TODO_TEXT = "todoText";
    private static final String COLUMN_URGENT = "isUrgent";

    // Getters
    public static String getDatabase_Name() {
        return DATABASE_NAME;
    }
    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }
    public static String getTableName() {
        return TABLE_NAME;
    }
    public static String getColumnId() {
        return COLUMN_ID;
    }
    public static String getColumnTodoText() {
        return COLUMN_TODO_TEXT;
    }
    public static String getColumnUrgent() {
        return COLUMN_URGENT;
    }

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TODO_TEXT + " TEXT, "
                + COLUMN_URGENT + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
