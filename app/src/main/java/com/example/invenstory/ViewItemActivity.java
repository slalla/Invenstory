package com.example.invenstory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        String image;
        int imageId = R.mipmap.ic_watch;
        FloatingActionButton edit = findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), "Editing will be available in the next version", Toast.LENGTH_SHORT);
                t.show();
                //TODO this button should start a new activity or fragment to update the current item
            }
        });

        TextView nameText = findViewById(R.id.textName);
        TextView statusText = findViewById(R.id.textStatus);
        TextView collectionText = findViewById(R.id.textCollection);
        TextView purchasePriceText = findViewById(R.id.textPurchasePrice);
        TextView purchaseDateText = findViewById(R.id.textPurchaseDate);
        TextView tagsText = findViewById(R.id.textTags);

        ImageView imageImageView = findViewById(R.id.imageItemPicture);

        nameText.setText(nameText.getText()+" " + name);
        statusText.setText(statusText.getText() + " " + status);
        collectionText.setText(collectionText.getText()+" " + collectionName);
        purchasePriceText.setText("Purchase Price: "+ price);
        purchaseDateText.setText("Purchase Date: " + purchase_info);
        tagsText.setText(tagsText.getText() + " " + tag);

        imageImageView.setImageResource(imageId);


    }
}
