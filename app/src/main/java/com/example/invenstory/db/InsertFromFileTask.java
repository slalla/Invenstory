package com.example.invenstory.db;

import android.content.Context;
import android.os.AsyncTask;

import com.example.invenstory.R;

public class InsertFromFileTask extends AsyncTask<Context, Void, Integer> {

    private InvenstoryDbHelper dbHelper;

    protected Integer doInBackground(Context... contexts) {
        dbHelper = new InvenstoryDbHelper(contexts[0]);
        return dbHelper.insertFromFile(contexts[0], R.raw.test_data);
    }

}
