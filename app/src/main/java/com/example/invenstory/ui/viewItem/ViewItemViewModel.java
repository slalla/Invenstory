package com.example.invenstory.ui.viewItem;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.invenstory.db.asyncTasks.RetrieveCollectionTask;
import com.example.invenstory.db.asyncTasks.RetrieveItemTask;
import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;

public class ViewItemViewModel extends AndroidViewModel {

    private Context context;

    private int itemId;
    private int collectionId;

    public ViewItemViewModel(@NonNull Application application, int itemId, int collectionId) {
        super(application);
        this.context = application.getApplicationContext();
        this.itemId = itemId;
        this.collectionId = collectionId;
    }

    public int getCollectionId() {
        return this.collectionId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public Item getItem() {
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

    public Collection getCollection(){
        Collection collection = null;
        try {
            RetrieveCollectionTask retrieveCollectionTask = new RetrieveCollectionTask(context);
            retrieveCollectionTask.execute(collectionId);
            collection = retrieveCollectionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return collection;
    }
}
