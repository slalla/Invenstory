package com.example.invenstory.ui.collection;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;

import java.util.List;

public class CollectionViewModel extends AndroidViewModel {
    private InvenstoryDbHelper dbHelper;
    private List<Collection> collections;


    public CollectionViewModel (@NonNull Application application) {
        super(application);
        dbHelper = new InvenstoryDbHelper(application.getApplicationContext());
        collections = updateCollectionsList();
    }

    public List<Collection> updateCollectionsList() {
        return dbHelper.getCollections();
    }

    public void insertCollection(Collection collection) {
        dbHelper.addCollection(collection);
    }

    public void deleteCollection(Collection collection) {
        dbHelper.deleteCollection(collection);
    }

    public void updateCollection(Collection collection) {
        dbHelper.updateCollection(collection);
    }

}
