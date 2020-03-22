package com.example.invenstory.ui.itemList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Item;

import java.util.List;

public class ItemListViewModel extends AndroidViewModel {
    private InvenstoryDbHelper dbHelper;
    private List<Item> items;

    public ItemListViewModel (@NonNull Application application, int collectionId) {
        super(application);
        dbHelper = new InvenstoryDbHelper(application.getApplicationContext());
        items = updateItemsList(collectionId);
    }

    public List<Item> updateItemsList(int collectionId) {
        return dbHelper.getItems(collectionId);
    }


    public void insertItem(Item item) {
        dbHelper.addItem(item);
    }

    public void deleteItem(Item item) {
        dbHelper.deleteItem(item);
    }

    public void updateItem(Item item) {
        dbHelper.updateItem(item);
    }
}
