package com.example.invenstory.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.invenstory.model.Item;

public class InvenstoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Invenstory.db";

    public static final String TAG = "InvenstoryDbHelper";

    public static final String SQL_CREATE_COLLECTION =
            "CREATE TABLE " + CollectionContract.TABLE_NAME + " (" +
                    CollectionContract.TABLE_ID + " INTEGER PRIMARY KEY," +
                    CollectionContract.COLUMN_NAME + " TEXT" +
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
                    ItemContract.COLUMN_COLLECTION + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + ItemContract.COLUMN_COLLECTION + ") " +
                    "REFERENCES " + CollectionContract.TABLE_NAME + "(" + CollectionContract.TABLE_ID + ")" +
                    ");"
            ;

    public static final String SQL_CREATE_ATTRIBUTES =
            "CREATE TABLE " + AttributesContract.TABLE_NAME + " (" +
                    AttributesContract.TABLE_ID + " INTEGER PRIMARY KEY," +
                    AttributesContract.COLUMN_NAME + " TEXT," +
                    AttributesContract.COLUMN_VALUE + " TEXT," +
                    AttributesContract.COLUMN_ITEM + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + AttributesContract.COLUMN_ITEM + ") " +
                    "REFERENCES " + ItemContract.TABLE_NAME + "(" + ItemContract.TABLE_ID + ")" +
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
            db.execSQL(SQL_CREATE_ATTRIBUTES);
            Log.d(TAG, "Database successfully created!");
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

    public long addItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(ItemContract.TABLE_ID, item.getId());
        values.put(ItemContract.COLUMN_NAME, item.getName());
        //  values.put(ItemContract.COLUMN_CONDITION, item.getCondition());
        // values.put(ItemContract.COLUMN_PRICE, item.getValue());
        // values.put(ItemContract.COLUMN_LOCATION, item.getLocation());
        //TODO: Add image column
        values.put(ItemContract.COLUMN_COLLECTION, item.getGroupName());

        // returns the id of the newly create row or -1 if there was an error
        return db.insert(ItemContract.TABLE_NAME, null, values);
    }
}
