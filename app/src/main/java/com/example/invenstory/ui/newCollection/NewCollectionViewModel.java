package com.example.invenstory.ui.newCollection;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.db.asyncTasks.AddCollectionTask;
import com.example.invenstory.model.Collection;

public class NewCollectionViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private Context context;

    public NewCollectionViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void insertCollection(Collection collection) {
        AddCollectionTask addCollectionTask = new AddCollectionTask(context);
        addCollectionTask.execute(collection);
    }
}
