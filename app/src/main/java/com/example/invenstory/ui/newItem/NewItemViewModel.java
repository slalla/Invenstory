package com.example.invenstory.ui.newItem;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.invenstory.db.asyncTasks.AddItemTask;
import com.example.invenstory.model.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        item.setPhotoFilePaths(new ArrayList<>(filePaths));
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

    public LiveData<Set<String>> getFilePaths() {
        return filePathsLive;
    };
}
