package com.example.invenstory.db;

import android.provider.BaseColumns;

/**
 * The CollectionContract is used to set constants for database
 * columns, tables, etc. so that you only have to update
 * the names in one place and then it will propagate through
 * the rest of the code.
 */
public final class CollectionContract {

    private CollectionContract() {}

    public static final String TABLE_NAME = "collections";
    public static final String TABLE_ID = "collection" + BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
}
