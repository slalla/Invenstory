package com.example.invenstory.ui.newCollection;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.invenstory.db.asyncTasks.AddCollectionTask;
import com.example.invenstory.db.asyncTasks.RetrieveCollectionTask;
import com.example.invenstory.model.Collection;

import java.util.concurrent.ExecutionException;

public class NewCollectionViewModel extends AndroidViewModel {
    private Context context;

    public NewCollectionViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void insertCollection(Collection collection) {
        AddCollectionTask addCollectionTask = new AddCollectionTask(context);
        addCollectionTask.execute(collection);
    }

    public Collection getCollection(int collectionId){
        Collection result = null;
        try{
            RetrieveCollectionTask retrieveCollectionTask = new RetrieveCollectionTask(context);
            retrieveCollectionTask.execute(collectionId);
            result = retrieveCollectionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
