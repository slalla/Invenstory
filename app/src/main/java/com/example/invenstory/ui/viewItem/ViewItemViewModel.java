package com.example.invenstory.ui.viewItem;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.invenstory.model.Item;

import java.util.HashSet;

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
}
