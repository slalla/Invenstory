package com.example.invenstory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.invenstory.ui.newcollection.NewCollectionFragment;

public class NewCollection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_collection_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NewCollectionFragment.newInstance())
                    .commitNow();
        }
    }
}
