package com.example.invenstory.db.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;

import java.util.ArrayList;

public class RetrieveCollectionsTask extends AsyncTask<Void, Void, ArrayList<Collection>> {

    private Context context;

    public RetrieveCollectionsTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Collection> doInBackground(Void... voids) {
        InvenstoryDbHelper dbHelper = new InvenstoryDbHelper(context);
        return dbHelper.getCollections();
    }
}
