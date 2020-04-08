package com.example.invenstory.ui.shareCollection;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.invenstory.db.asyncTasks.DeleteCollectionTask;
import com.example.invenstory.db.asyncTasks.RetrieveCollectionTask;
import com.example.invenstory.db.asyncTasks.RetrieveCollectionsTask;
import com.example.invenstory.db.asyncTasks.RetrieveItemsTask;
import com.example.invenstory.db.asyncTasks.UpdateCollectionTask;
import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ShareCollectionViewModel extends AndroidViewModel {
    private ArrayList<Collection> collections;
    private MutableLiveData<ArrayList<Collection>> collectionListLive;

    private Context context;

    public ShareCollectionViewModel(@NonNull Application application) {
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

    public LiveData<ArrayList<Collection>> getCollectionList() {
        return collectionListLive;
    }

    public Collection getCollection(int collectionID){
        Collection result = null;
        try {
            RetrieveCollectionTask retrieveCollectionTask = new RetrieveCollectionTask(context);
            retrieveCollectionTask.execute(collectionID);
            result = retrieveCollectionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<Item> getItemsFromCollection(int collectionID){
        ArrayList<Item> results = null;
        try{
            RetrieveItemsTask retrieveItemsTask = new RetrieveItemsTask(context);
            retrieveItemsTask.execute(collectionID);
            results = retrieveItemsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return results;
    }
}