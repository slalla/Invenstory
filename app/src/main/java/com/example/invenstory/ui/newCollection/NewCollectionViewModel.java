package com.example.invenstory.ui.newCollection;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;

public class NewCollectionViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private InvenstoryDbHelper dbHelper;

    public NewCollectionViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new InvenstoryDbHelper(application.getApplicationContext());
    }

    public void insertCollection(Collection collection) {
        dbHelper.addCollection(collection);
    }
}
