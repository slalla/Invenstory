package com.example.invenstory.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InvenstoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Invenstory.db";

    public static final String TAG = "InvenstoryDbHelper";

    public static final String SQL_CREATE_COLLECTION =
            "CREATE TABLE " + CollectionContract.TABLE_NAME + " (" +
                CollectionContract.TABLE_ID + " INTEGER PRIMARY KEY," +
                CollectionContract.COLUMN_NAME + " TEXT," +
                CollectionContract.COLUMN_ITEM + " INTEGER," +
                "FOREIGN KEY(" + CollectionContract.COLUMN_ITEM + ") " +
                "REFERENCES " + ItemContract.TABLE_NAME + "(" + ItemContract.TABLE_ID + ")" +
            ");"
            ;

    public static final String SQL_CREATE_ITEM =
            "CREATE TABLE " + ItemContract.TABLE_NAME + " (" +
                ItemContract.TABLE_ID + " INTEGER PRIMARY KEY," +
                ItemContract.COLUMN_NAME + " TEXT," +
                ItemContract.COLUMN_CONDITION + "TEXT," +
                ItemContract.COLUMN_PRICE + " REAL," +
                ItemContract.COLUMN_LOCATION + " TEXT," +
                ItemContract.COLUMN_PHOTO + " BLOB," +
                ItemContract.COLUMN_COLLECTION + " INTEGER," +
                "FOREIGN KEY(" + ItemContract.COLUMN_COLLECTION + ") " +
                "REFERENCES " + CollectionContract.TABLE_NAME + "(" + CollectionContract.TABLE_ID + ")" +
            ");"
            ;

    public static final String SQL_DELETE_ENTRIES = "";

    public InvenstoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_COLLECTION);
            db.execSQL(SQL_CREATE_ITEM);
        } catch (SQLException e) {
            Log.i(TAG, "Error creating sql tables.");
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(SQL_DELETE_ENTRIES);
        } catch (SQLException e) {
            Log.i(TAG, "Error upgrading database");
            e.printStackTrace();
        }
        onCreate(db);
    }
}
