package com.example.invenstory.ui.collectionList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;

import java.util.List;

public class CollectionListViewModel extends AndroidViewModel {
    private InvenstoryDbHelper dbHelper;
    private List<Collection> collections;


    public CollectionListViewModel(@NonNull Application application) {
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
