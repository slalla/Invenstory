package com.example.invenstory.db;

import android.provider.BaseColumns;

/**
 * The ItemContract is used to set constants for database
 * columns, tables, etc. so that you only have to update
 * the names in one place and then it will propagate through
 * the rest of the code.
 */
public final class ItemContract {

    private ItemContract() {}

    public static final String TABLE_NAME = "items";
    public static final String TABLE_ID = "item" + BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DATE = "init_date";
    public static final String COLUMN_PHOTOS = "photo_uris";
    public static final String COLUMN_COLLECTION = "collection_id";
}
