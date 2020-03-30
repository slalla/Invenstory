package com.example.invenstory.ui.itemList;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.db.asyncTasks.AddItemTask;
import com.example.invenstory.db.asyncTasks.DeleteItemTask;
import com.example.invenstory.db.asyncTasks.RetrieveItemsTask;
import com.example.invenstory.db.asyncTasks.UpdateItemTask;
import com.example.invenstory.model.Item;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ItemListViewModel extends AndroidViewModel {
    private Context context;
    private List<Item> items;

    public ItemListViewModel (@NonNull Application application, int collectionId) {
        super(application);
        this.context = application.getApplicationContext();
        items = updateItemsList(collectionId);
    }

    public List<Item> updateItemsList(int collectionId) {
        RetrieveItemsTask retrieveItemsTask = new RetrieveItemsTask(context);
        retrieveItemsTask.execute(collectionId);
        try {
            items = retrieveItemsTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return items;
    }


    public void insertItem(Item item) {
        AddItemTask addItemTask = new AddItemTask(context);
        addItemTask.execute(item);
    }

    public void deleteItem(Item item) {
        DeleteItemTask deleteItemTask = new DeleteItemTask(context);
        deleteItemTask.execute(item);
    }

    public void updateItem(Item item) {
        UpdateItemTask updateItemTask = new UpdateItemTask(context);
        updateItemTask.execute(item);
    }
}
