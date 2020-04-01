package com.example.invenstory.db.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Item;

public class UpdateItemTask extends AsyncTask<Item, Void, Long> {

    private Context context;

    public UpdateItemTask(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Item... items) {
        Item item = items[0];

        if (item != null) {
            InvenstoryDbHelper dbHelper = new InvenstoryDbHelper(context);
            Long id = dbHelper.updateItem(item);
            return id;
        }
        return null;
    }
}
