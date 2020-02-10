package com.example.invenstory.db;

import android.provider.BaseColumns;

/**
 * The AttributesContract is used to set constants for database
 * columns, tables, etc. so that you only have to update
 * the names in one place and then it will propagate through
 * the rest of the code.
 */
public final class AttributesContract {

    private AttributesContract() {}

    public static final String TABLE_NAME = "fields";
    public static final String TABLE_ID = "field" + BaseColumns._ID;
    public static final String COLUMN_NAME = "field_name";
    public static final String COLUMN_VALUE = "field_value";
    public static final String COLUMN_ITEM = "item_id";
}
