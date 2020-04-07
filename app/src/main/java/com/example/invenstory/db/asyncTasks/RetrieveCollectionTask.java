package com.example.invenstory.db.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;

public class RetrieveCollectionTask extends AsyncTask<Integer, Void, Collection> {
    private Context context;

    public RetrieveCollectionTask(Context context) {
        this.context = context;
    }

    @Override
    protected Collection doInBackground(Integer... integers) {
        InvenstoryDbHelper dbHelper = new InvenstoryDbHelper(context);
        return dbHelper.getCollection(integers[0]);
    }
}
