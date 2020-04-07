package com.example.invenstory.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvenstoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Invenstory.db";

    public static final String TAG = "InvenstoryDbHelper";

    //SQLite Create Table statement for the Collection table
    public static final String SQL_CREATE_COLLECTION =
            "CREATE TABLE " + CollectionContract.TABLE_NAME + " (" +
                    CollectionContract.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CollectionContract.COLUMN_NAME + " TEXT" +
                    ");"
            ;

    //SQLite Create Table statement for the Item table
    public static final String SQL_CREATE_ITEM =
            "CREATE TABLE " + ItemContract.TABLE_NAME + " (" +
                    ItemContract.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ItemContract.COLUMN_NAME + " TEXT," +
                    ItemContract.COLUMN_CONDITION + " INTEGER," +
                    ItemContract.COLUMN_STATUS + " INTEGER," +
                    ItemContract.COLUMN_PRICE + " TEXT," +
                    ItemContract.COLUMN_LOCATION + " TEXT," +
                    ItemContract.COLUMN_DATE + " DATE," +
                    ItemContract.COLUMN_PHOTOS + " TEXT," +
                    ItemContract.COLUMN_COLLECTION + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + ItemContract.COLUMN_COLLECTION + ") " +
                    "REFERENCES " + CollectionContract.TABLE_NAME + "(" + CollectionContract.TABLE_ID + ")" +
                    ");"
            ;
    //SQLite Create Table statement for the Attributes table
    public static final String SQL_CREATE_ATTRIBUTES =
            "CREATE TABLE " + AttributesContract.TABLE_NAME + " (" +
                    AttributesContract.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AttributesContract.COLUMN_NAME + " TEXT," +
                    AttributesContract.COLUMN_VALUE + " TEXT," +
                    AttributesContract.COLUMN_ITEM + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + AttributesContract.COLUMN_ITEM + ") " +
                    "REFERENCES " + ItemContract.TABLE_NAME + "(" + ItemContract.TABLE_ID + ")" +
                    ");"
            ;

    //TODO figure out what to do with this delete entries
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
        values.put(ItemContract.COLUMN_NAME, item.getName());
        values.put(ItemContract.COLUMN_CONDITION, item.getCondition().ordinal());
        values.put(ItemContract.COLUMN_STATUS, item.getStatus().ordinal());
        values.put(ItemContract.COLUMN_PRICE, item.getPrice());
        values.put(ItemContract.COLUMN_LOCATION, item.getLocation());
        values.put(ItemContract.COLUMN_DATE, item.getDate().toString());
        if (item.getPhotoFilePaths() != null) {
            values.put(ItemContract.COLUMN_PHOTOS, String.join(",", item.getPhotoFilePaths()));
        } else {
            values.put(ItemContract.COLUMN_PHOTOS, "");
        }
        values.put(ItemContract.COLUMN_COLLECTION, item.getCollectionID());

        // returns the id of the newly create row or -1 if there was an error
        return db.insert(ItemContract.TABLE_NAME, null, values);
    }

    public long updateItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemContract.COLUMN_NAME, item.getName());
        values.put(ItemContract.COLUMN_CONDITION, item.getCondition().ordinal());
        values.put(ItemContract.COLUMN_STATUS, item.getStatus().ordinal());
        values.put(ItemContract.COLUMN_PRICE, item.getPrice());
        values.put(ItemContract.COLUMN_LOCATION, item.getLocation());
        values.put(ItemContract.COLUMN_DATE, item.getDate().toString());
        if (item.getPhotoFilePaths() != null) {
            values.put(ItemContract.COLUMN_PHOTOS, String.join(",", item.getPhotoFilePaths()));
        } else {
            values.put(ItemContract.COLUMN_PHOTOS, "");
        }
        values.put(ItemContract.COLUMN_COLLECTION, item.getCollectionID());

        return db.update(ItemContract.TABLE_NAME, values, ItemContract.TABLE_ID + "=" + item.getItemId(), null);
    }

    public long deleteItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(ItemContract.TABLE_NAME, ItemContract.TABLE_ID + "=" + item.getItemId(), null);
    }

    public long addCollection(Collection collection) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CollectionContract.COLUMN_NAME, collection.getName());

        return db.insert(CollectionContract.TABLE_NAME, null, values);

    }

    public long updateCollection(Collection collection) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CollectionContract.COLUMN_NAME, collection.getName());

        return db.update(CollectionContract.TABLE_NAME, values, ItemContract.TABLE_ID + "=" + collection.getId(), null);

    }

    public long deleteCollection(Collection collection) {
        int collectionId = collection.getId();

        ArrayList<Item> collectionItems = getItems(collectionId);

        for (Item item: collectionItems) {
            deleteItem(item);
        }

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(CollectionContract.TABLE_NAME, CollectionContract.TABLE_ID + "=" + collectionId, null);
    }

    public ArrayList<Item> getItems(int collectionId) {
        ArrayList<Item> items = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + ItemContract.TABLE_NAME +
                " WHERE " + ItemContract.COLUMN_COLLECTION + " = " + collectionId;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_NAME)) != null) {
                Item item = new Item(cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(ItemContract.COLUMN_COLLECTION)),
                        cursor.getInt(cursor.getColumnIndex(ItemContract.COLUMN_CONDITION)),
                        cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_LOCATION)),
                        null);

                item.setItemId(cursor.getInt(cursor.getColumnIndex(ItemContract.TABLE_ID)));
                try {
                    String joinedPaths = cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_PHOTOS));
                    ArrayList<String> paths = new ArrayList(Arrays.asList(joinedPaths.split(",")));
                    item.setPhotoFilePaths(paths);
                }
                catch(Exception e){
                    e.printStackTrace();
                    item.setPhotoFilePaths(null);
                }
                items.add(item);
            }
            cursor.moveToNext();
        }
        db.close();
        return items;
    }

    public Item getItem(int collectionId, int itemId){
        Item item = null;
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + ItemContract.TABLE_NAME +
                " WHERE " + ItemContract.COLUMN_COLLECTION + " = " + collectionId + " and " + ItemContract.TABLE_ID + "=" +itemId;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_NAME)) != null) {
                item = new Item(cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(ItemContract.COLUMN_COLLECTION)),
                        cursor.getInt(cursor.getColumnIndex(ItemContract.COLUMN_CONDITION)),
                        cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_LOCATION)),
                        null);

                item.setItemId(cursor.getInt(cursor.getColumnIndex(ItemContract.TABLE_ID)));
                try {
                    String joinedPaths = cursor.getString(cursor.getColumnIndex(ItemContract.COLUMN_PHOTOS));
                    ArrayList<String> paths = new ArrayList(Arrays.asList(joinedPaths.split(",")));
                    item.setPhotoFilePaths(paths);
                }
                catch(Exception e){
                    e.printStackTrace();
                    item.setPhotoFilePaths(null);
                }
            }
            cursor.moveToNext();
        }
        db.close();
        return item;
    }

    public Collection getCollection(int collectionId){
        Collection collection = null;
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + CollectionContract.TABLE_NAME +
                " WHERE " + CollectionContract.TABLE_ID + " = " + collectionId;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(CollectionContract.COLUMN_NAME)) != null) {
                collection = new Collection(cursor.getString(cursor.getColumnIndex(CollectionContract.COLUMN_NAME)),
                        cursor.getColumnIndex(CollectionContract.TABLE_ID));

                collection.setId(cursor.getInt(cursor.getColumnIndex(CollectionContract.TABLE_ID)));

            }
            cursor.moveToNext();
        }
        db.close();
        return collection;
    }

    public ArrayList<Collection> getCollections() {
        ArrayList<Collection> collections = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + CollectionContract.TABLE_NAME + ";";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(CollectionContract.COLUMN_NAME)) != null) {
                Collection collection = new Collection(cursor.getString(cursor.getColumnIndex(CollectionContract.COLUMN_NAME)),
                        cursor.getColumnIndex(CollectionContract.TABLE_ID));

                collection.setId(cursor.getInt(cursor.getColumnIndex(CollectionContract.TABLE_ID)));

                collections.add(collection);
            }
            cursor.moveToNext();
        }
        db.close();
        return collections;
    }

    public int insertFromFile(Context context, int resId) {
        Log.i(" This shouldn't be running", "Who called us???");
        //Insert statement count
        int result = 0;

        SQLiteDatabase db = getWritableDatabase();

        // Open the resource
        InputStream insertsStream = context.getResources().openRawResource(resId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        try {
            // Iterate through lines (assuming each insert has its own line)
            while (insertReader.ready()) {
                String insertStmt = insertReader.readLine();
                db.execSQL(insertStmt);
                result++;
            }
            insertReader.close();
        } catch (IOException e) {
            Log.e(TAG, "Error reading from file", e);
        }

        return result;
    }
}
