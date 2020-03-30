package com.example.invenstory.ui.collectionList;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.db.asyncTasks.DeleteCollectionTask;
import com.example.invenstory.db.asyncTasks.RetrieveCollectionsTask;
import com.example.invenstory.db.asyncTasks.UpdateCollectionTask;
import com.example.invenstory.db.asyncTasks.UpdateItemTask;
import com.example.invenstory.model.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CollectionListViewModel extends AndroidViewModel {
    private ArrayList<Collection> collections;
    private MutableLiveData<ArrayList<Collection>> collectionListLive;

    private Context context;

    public CollectionListViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        collectionListLive = new MutableLiveData<>();
        updateCollectionList();
    }

    public void updateCollectionList() {

        RetrieveCollectionsTask retrieveCollectionsTask = new RetrieveCollectionsTask(context);
        retrieveCollectionsTask.execute();
        try {
            collections = retrieveCollectionsTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        collectionListLive.setValue(collections);
    }

    public void deleteCollection(Collection collection) {
        DeleteCollectionTask deleteCollectionTask = new DeleteCollectionTask(context);
        deleteCollectionTask.execute(collection);
    }

    public void updateCollection(Collection collection) {
        UpdateCollectionTask updateCollectionTask = new UpdateCollectionTask(context);
        updateCollectionTask.execute(collection);
    }

    public LiveData<ArrayList<Collection>> getCollectionList() {
        return collectionListLive;
    }

}
