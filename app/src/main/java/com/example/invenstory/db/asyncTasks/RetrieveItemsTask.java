package com.example.invenstory.db.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Item;

import java.util.ArrayList;

public class RetrieveItemsTask extends AsyncTask<Integer, Void, ArrayList<Item>> {

    private Context context;

    public RetrieveItemsTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Item> doInBackground(Integer... collectionIds) {
        Integer collectionId = collectionIds[0];
        if (collectionId != null) {
            InvenstoryDbHelper dbHelper = new InvenstoryDbHelper(context);
            return dbHelper.getItems(collectionId);
        }

        return null;
    }
}
