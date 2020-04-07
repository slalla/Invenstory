package com.example.invenstory.ui.newItem;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.invenstory.db.asyncTasks.AddItemTask;
import com.example.invenstory.db.asyncTasks.RetrieveItemTask;
import com.example.invenstory.db.asyncTasks.UpdateItemTask;
import com.example.invenstory.model.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class NewItemViewModel extends AndroidViewModel {

    private Context context;
    /**
     * This is a Set of filePaths that currently contain the images to be stored
     * in the Item object.
     */
    private Set<String> filePaths;
    private MutableLiveData<Set<String>> filePathsLive;

    public NewItemViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        filePaths = new HashSet<String>();
        filePathsLive = new MutableLiveData<>();
        updateFilePaths();
    }

    public void insertItem(Item item) {
        ArrayList<String> paths = new ArrayList<>(filePaths);
        item.setPhotoFilePaths(paths);
        AddItemTask addItemTask = new AddItemTask(context);
        addItemTask.execute(item);
    }

    public void updateFilePaths() {
        filePathsLive.setValue(filePaths);
    }

    public void updateFilePaths(String path) {
        filePaths.add(path);
        filePathsLive.setValue(filePaths);
    }

    public void updateItem(Item item){
        ArrayList<String> paths = new ArrayList<>(filePaths);
        item.setPhotoFilePaths(paths);
        UpdateItemTask updateItemTask = new UpdateItemTask(context);
        updateItemTask.execute(item);
    }


    public LiveData<Set<String>> getFilePaths() {
        return filePathsLive;
    };

    public Item getItem(int collectionId, int itemId){
        Item item = null;
        try {
            RetrieveItemTask retrieveItemTask = new RetrieveItemTask(context);
            retrieveItemTask.execute(collectionId, itemId);
            item = retrieveItemTask.get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return item;
    }
}
