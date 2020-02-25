package com.example.invenstory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.invenstory.db.InsertFromFileTask;
import com.example.invenstory.db.InvenstoryDbHelper;
import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private static FloatingActionButton fab;
    private static int pageID = 0;
    private AppBarConfiguration mAppBarConfiguration;

    private InvenstoryDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), "This button has been clicked on the " + pageID + " page.", Toast.LENGTH_SHORT);
                //t.show();
                if(pageID==1){
                    Intent testIntent = new Intent(Home.this, NewCollection.class);
                    startActivity(testIntent);
                }
                else{
                    Log.i("ID:: ", pageID +"");
                }

                //TODO make this do different things based on what id is selected
                //eg if the user clicks this on the collection page it should open an add collection fragment
                //if the user selects this on the item page it will try to save the item. run checks first
            }
        });
        fab.hide();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery,
                R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        getApplicationContext().deleteDatabase(InvenstoryDbHelper.DATABASE_NAME);

        //TODO: Remove InsertFromFileTask test code after prototype demonstration.
        InsertFromFileTask insertFromFileTask = new InsertFromFileTask();
        insertFromFileTask.execute(getApplicationContext());

        dbHelper = new InvenstoryDbHelper(getApplicationContext());

        ArrayList<Collection> collections = new ArrayList<>();

        collections = dbHelper.getCollections();

        ArrayList<Item> items1 = new ArrayList<>();
        ArrayList<Item> items2 = new ArrayList<>();

        items1 = dbHelper.getItems(collections.get(0).getId());
        items2 = dbHelper.getItems(collections.get(1).getId());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static void setFabOn() {
        fab.show();
    }
    public static void setFabOff(){
        fab.hide();
    }
    public static void setPageID(int id){
        pageID = id;
    }
}
