package com.example.invenstory.db.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;

public class DeleteCollectionTask extends AsyncTask<Collection, Void, Long> {
    private Context context;

    public DeleteCollectionTask(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Collection... collections) {
        Collection collection = collections[0];

        if (collection != null) {
            InvenstoryDbHelper dbHelper = new InvenstoryDbHelper(context);
            Long id = dbHelper.deleteCollection(collection);
            return id;
        }

        return null;
    }
}
