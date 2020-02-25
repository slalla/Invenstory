package com.example.invenstory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Intent i = getIntent();
        String name = i.getStringExtra("itemName");
        String price = i.getStringExtra("itemPrice");

        //TODO make these all extras from the launched fragment
        String collectionName = "Watches";
        String status = "LOST";
        String purchase_info = "January 23rd 2020";
        String tag = "#Watch #Seiko";




    }
}
