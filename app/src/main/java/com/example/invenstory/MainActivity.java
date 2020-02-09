package com.example.invenstory;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TimeUtils;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
