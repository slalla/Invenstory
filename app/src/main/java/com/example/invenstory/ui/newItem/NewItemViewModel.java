package com.example.invenstory.ui.newItem;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.invenstory.db.asyncTasks.AddItemTask;
import com.example.invenstory.model.Item;

public class NewItemViewModel extends AndroidViewModel {

    private Context context;

    public NewItemViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void insertItem(Item item) {
        AddItemTask addItemTask = new AddItemTask(context);
        addItemTask.execute(item);
    }
}
