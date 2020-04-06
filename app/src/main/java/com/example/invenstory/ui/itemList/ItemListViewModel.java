package com.example.invenstory.ui.itemList;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.invenstory.db.asyncTasks.DeleteItemTask;
import com.example.invenstory.db.asyncTasks.RetrieveItemsTask;
import com.example.invenstory.db.asyncTasks.UpdateItemTask;
import com.example.invenstory.model.Item;
import com.example.invenstory.ui.collectionList.CollectionListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ItemListViewModel extends AndroidViewModel {
    private Context context;
    private int collectionId;
    private MutableLiveData<ArrayList<Item>> itemListLive;
    private ArrayList<Item> items;

    public ItemListViewModel(@NonNull Application application, int collectionId) {
        super(application);
        this.context = application.getApplicationContext();
        itemListLive = new MutableLiveData<>();
        this.collectionId = collectionId;
        updateItemsList();
    }

    public void updateItemsList() {
        RetrieveItemsTask retrieveItemsTask = new RetrieveItemsTask(context);
        retrieveItemsTask.execute(collectionId);
        try {
            items = retrieveItemsTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        itemListLive.setValue(items);
    }

    public void deleteItem(Item item) {
        DeleteItemTask deleteItemTask = new DeleteItemTask(context);
        deleteItemTask.execute(item);
    }

    public void updateItem(Item item) {
        UpdateItemTask updateItemTask = new UpdateItemTask(context);
        updateItemTask.execute(item);
    }

    public LiveData<ArrayList<Item>> getItemList() {
        return itemListLive;
    }
}
