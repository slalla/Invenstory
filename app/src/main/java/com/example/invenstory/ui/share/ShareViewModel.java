package com.example.invenstory.ui.share;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.invenstory.db.asyncTasks.DeleteCollectionTask;
import com.example.invenstory.db.asyncTasks.RetrieveCollectionsTask;
import com.example.invenstory.db.asyncTasks.UpdateCollectionTask;
import com.example.invenstory.model.Collection;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ShareViewModel extends AndroidViewModel {
    private ArrayList<Collection> collections;
    private MutableLiveData<ArrayList<Collection>> collectionListLive;

    private Context context;

    public ShareViewModel(@NonNull Application application) {
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
}