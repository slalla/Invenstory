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
import com.example.invenstory.ui.itemList.ItemListFragmentArgs;
import com.example.invenstory.ui.itemList.ItemListFragmentDirections;
import com.example.invenstory.ui.newItem.NewItemFragment;
import com.example.invenstory.ui.viewItem.ViewItemFragment;
import com.example.invenstory.ui.viewItem.ViewItemFragmentDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

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
     * Contains back button, hamberger button, settings, and title of each page
     */
    private static Toolbar toolbar;

    // TODO written by Paul: Discuss what the best practice for FAB is when data communication is required
    private static int collectionId;

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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
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

    public static FloatingActionButton getFAB() {
        return fab;
    }
    public static Toolbar getToolbar() { return toolbar; }

    // see collectionId variable above for TODO comment
    public static void setCollectionId(int id) {
        collectionId = id;
    }
    public static int getCollectionId() {
        return collectionId;
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

}
