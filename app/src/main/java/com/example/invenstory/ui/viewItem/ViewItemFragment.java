package com.example.invenstory.ui.viewItem;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.util.ArrayList;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ViewItemFragment extends Fragment {

    private ViewItemViewModel viewItemViewModel;
    private Menu viewItemMenu;

    public static ViewItemFragment newInstance() {
        return new ViewItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setEditItemFAB(Home.getFAB());
        Home.setFabOn();
        View root=  inflater.inflate(R.layout.fragment_view_item, container, false);

        int collectionId = ViewItemFragmentArgs.fromBundle(getArguments()).getCollectionId();
        int itemId = ViewItemFragmentArgs.fromBundle(getArguments()).getItemId();

        viewItemViewModel = new ViewModelProvider(this, new ViewItemViewModelFactory(getActivity().getApplication(), itemId, collectionId)).get(ViewItemViewModel.class);
        Item item = viewItemViewModel.getItem();
        Collection collection = viewItemViewModel.getCollection();

        Log.i("This is an item: ", item.getName());
        Log.i("This is a collection: ", collection.getName());


        String name = item.getName();
        String price = item.getPrice();
        String collectionName = collection.getName();
        String status = item.getStatusText();
        String condition = item.getConditionText();
        String location = item.getLocation();
        String description = item.getDescription();
        ArrayList<String> filePaths = item.getPhotoFilePaths();


        Home.getFAB().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewItemFragmentDirections.ActionViewItemFragmentToNewItemFragment actionViewItemFragmentToNewItemFragment =
                        ViewItemFragmentDirections.actionViewItemFragmentToNewItemFragment();
                actionViewItemFragmentToNewItemFragment.setCollectionID(collectionId);
                actionViewItemFragmentToNewItemFragment.setItemID(itemId);
                actionViewItemFragmentToNewItemFragment.setEditFlag(1);

                NavController navController = findNavController(ViewItemFragment.this);
                navController.navigate(actionViewItemFragmentToNewItemFragment);
            }
        });

        TextView nameText = root.findViewById(R.id.textName);
        TextView conditionText = root.findViewById(R.id.textCondition);
        TextView collectionText = root.findViewById(R.id.textCollection);
        TextView purchasePriceText = root.findViewById(R.id.textPurchasePrice);
        TextView locationText = root.findViewById(R.id.textLocation);
        TextView descriptionText = root.findViewById(R.id.textDescription);
        CarouselView carouselView = root.findViewById(R.id.viewItemCarousel);

        nameText.setText(nameText.getText()+" " + name);
        conditionText.setText(conditionText.getText() + " " + condition);
        collectionText.setText(collectionText.getText()+" " + collectionName);
        purchasePriceText.setText(purchasePriceText.getText() +" "+ price);
        locationText.setText(locationText.getText() + location);
        descriptionText.setText(descriptionText.getText() + " " + description);

        setHasOptionsMenu(true);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                File imgFile = new File(filePaths.get(position));

                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                }
            }
        };

        carouselView.setPageCount(filePaths.size());
        carouselView.setImageListener(imageListener);

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewItemViewModel = ViewModelProviders.of(this).get(ViewItemViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        viewItemMenu = menu;
        inflater.inflate(R.menu.view_item_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.i("View Item Fragment: ", "This was the menu option selected " + id);
        if(id == 2131230781){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setMessage("Do you want to delete this item?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("View Item Fragment: ", "You clicked confirm button");

                    String name = viewItemViewModel.getItem().getName();
                    viewItemViewModel.deleteItem();
                    Toast.makeText(getActivity(), name + " was deleted.", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("View Item Fragment: ", "You clicked cancel button");
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public class ViewItemViewModelFactory implements ViewModelProvider.Factory {
        private Application mApplication;
        private int itemId;
        private int collectionId;

        public ViewItemViewModelFactory(Application application, int itemId, int collectionId) {
            mApplication = application;
            this.itemId = itemId;
            this.collectionId = collectionId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ViewItemViewModel(mApplication, itemId, collectionId);
        }
    }

    public void setEditItemFAB(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_edit_black_24dp);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2486A")));
    }

}
