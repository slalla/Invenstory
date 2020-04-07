package com.example.invenstory.db.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.invenstory.R;
import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Item;

public class RetrieveItemTask extends AsyncTask<Integer, Void, Item> {
    private Context context;

    public RetrieveItemTask(Context context) {
        this.context = context;
    }

    @Override
    protected Item doInBackground(Integer... integers) {
        InvenstoryDbHelper dbHelper = new InvenstoryDbHelper(context);
        return dbHelper.getItem(integers[0], integers[1]);
    }

}
