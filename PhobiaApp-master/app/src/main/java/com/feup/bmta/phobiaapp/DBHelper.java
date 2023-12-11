package com.feup.bmta.phobiaapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ECGData.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ECG_DATA = "ecg_data";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ECG = "ecg_data";

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the ECG data table
        String createTableQuery = "CREATE TABLE " + TABLE_ECG_DATA +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ECG + " TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed and create fresh table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ECG_DATA);
        onCreate(db);
    }

    // Method to add ECG data to the database
    public void addECGData(String ecgData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ECG, ecgData);
        db.insert(TABLE_ECG_DATA, null, values);
        db.close();
    }
}