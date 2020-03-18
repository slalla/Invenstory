package com.example.invenstory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.saveItem);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), "This button needs to save", Toast.LENGTH_SHORT);
                t.show();
            }
        });*/
        setContentView(R.layout.activity_new_item);
    }
}
