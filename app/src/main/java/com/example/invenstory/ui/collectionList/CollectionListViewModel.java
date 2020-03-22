package com.example.invenstory.ui.collectionList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionListViewModel extends AndroidViewModel {
    private InvenstoryDbHelper dbHelper;
    private ArrayList<Collection> collections;
    private MutableLiveData<ArrayList<Collection>> collectionListLive;

    public CollectionListViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new InvenstoryDbHelper(application.getApplicationContext());
        collectionListLive = new MutableLiveData<>();
        updateCollectionList();
    }

    public void updateCollectionList() {
        collections = dbHelper.getCollections();
        collectionListLive.setValue(collections);
    }

    public void deleteCollection(Collection collection) {
        dbHelper.deleteCollection(collection);
    }

    public void updateCollection(Collection collection) {
        dbHelper.updateCollection(collection);
    }

    public LiveData<ArrayList<Collection>> getCollectionList() {
        return collectionListLive;
    }

}
