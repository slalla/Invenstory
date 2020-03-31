package com.example.invenstory;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.invenstory.db.InvenstoryDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

/**
 * This class represents the Home Activity from which Invenstiry is launched.
 * All fragments are loaded into this activity.
 */
public class Home extends AppCompatActivity {

    /**
     * This floating action button serves multiple purposes, and can be disabled or enabled
     */
    private static FloatingActionButton fab;

    /**
     * This integer represents the fragment that has been launched.
     * A value of 0 indicates the home fragment is open.
     */
    private static int pageID = 0;

    private AppBarConfiguration mAppBarConfiguration;

    private InvenstoryDbHelper dbHelper;

    /**
     * This variable represents the DrawerLayout used for navigation.
     * It can be disabled and enabled using methods below.
     */
    private static DrawerLayout drawer;

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
                if(pageID==1){
                    startNewCollection();
                }
                //TODO edit the temporary pageID
                else if(pageID == 88){
                    startNewItem();
                }
                else{
                    Log.i("ID:: ", pageID +"");
                }
            }
        });
        fab.hide();

        drawer = findViewById(R.id.drawer_layout);

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

    /**
     * This method disables the floating action button
     */
    public static void setFabOn() {fab.show();}

    /**
     * This method enables the floating action button
     */
    public static void setFabOff(){ fab.hide();}

    /**
     * This method sets the pageID which can be used to change the behaviour of the fab
     * @param id the page idNumber
     */
    public static void setPageID(int id){
        pageID = id;
        //TODO edit these temporary pageID values
        if(pageID==1||pageID == 88){
            fab.setImageResource(R.drawable.ic_add_black_24dp);
        }
        else if (pageID == 99){
            fab.setImageResource(R.drawable.ic_edit_black_24dp);
        }
    }

    /**
     * This method disables the drawer from being opened by sliding from the left
     * However the bar still opens if being opened via title bar
     */
    public static void setDrawerOff(){drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);}

    /**
     * This method re-enables the drawer from being opened by sliding from the left
     */
    public static void setDrawerOn(){drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);}

    /**
     * Starts the newCollectionFragment with no information populated.
     */
    public void startNewCollection(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.action_nav_gallery_to_newCollectionFragment);
    }

    /**
     * Starts the newItemFragment with no information populated
     */
    public void startNewItem(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.action_itemListFragment_to_newItemFragment);
    }
}
