package com.example.invenstory.ui.collectionList;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.invenstory.db.asyncTasks.DeleteCollectionTask;
import com.example.invenstory.db.asyncTasks.RetrieveCollectionTask;
import com.example.invenstory.db.asyncTasks.RetrieveCollectionsTask;
import com.example.invenstory.db.asyncTasks.UpdateCollectionTask;
import com.example.invenstory.model.Collection;

import java.util.ArrayList;
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

    public long deleteCollection(int collectionId){
        long result = 0;
        Collection deleting = getCollection(collectionId);
        try{
            DeleteCollectionTask deleteCollectionTask = new DeleteCollectionTask(context);
            deleteCollectionTask.execute(deleting);
            result = deleteCollectionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Collection getCollection(int collectionId){
        Collection result = null;
        try{
            RetrieveCollectionTask retrieveCollectionTask = new RetrieveCollectionTask(context);
            retrieveCollectionTask.execute(collectionId);
            result = retrieveCollectionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateCollection(Collection collection) {
        UpdateCollectionTask updateCollectionTask = new UpdateCollectionTask(context);
        updateCollectionTask.execute(collection);
    }

    public LiveData<ArrayList<Collection>> getCollectionList() {
        return collectionListLive;
    }

}
