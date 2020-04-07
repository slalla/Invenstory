package com.example.invenstory.ui.viewItem;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static ViewItemFragment newInstance() {
        return new ViewItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Home.setFabOff();
        View root=  inflater.inflate(R.layout.view_item_fragment, container, false);

        int collectionId = ViewItemFragmentArgs.fromBundle(getArguments()).getCollectionId();
        int itemId = ViewItemFragmentArgs.fromBundle(getArguments()).getItemId();

        viewItemViewModel = new ViewModelProvider(this, new ViewItemViewModelFactory(getActivity().getApplication(), itemId, collectionId)).get(ViewItemViewModel.class);
        Item item = viewItemViewModel.getItem();
        Collection collection = viewItemViewModel.getCollection();

        Log.i("This is an item: ", item.getName());
        Log.i("This is a collection: ", collection.getName());


        //TODO make all these be information grabbed from the database
        String name = item.getName();
        String price = item.getPrice();
        String collectionName = collection.getName();
        String status = item.getStatusText();
        ArrayList<String> filePaths = item.getPhotoFilePaths();
        String purchase_info = "January 23rd 2020";
        String tag = "N/A";

        FloatingActionButton edit = root.findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO this button should start a new activity or fragment to update the current item, data should be passed through
                NavController navController = findNavController(ViewItemFragment.this);
                navController.navigate(R.id.action_viewItemFragment_to_newItemFragment);
            }
        });

        TextView nameText = root.findViewById(R.id.textName);
        TextView statusText = root.findViewById(R.id.textStatus);
        TextView collectionText = root.findViewById(R.id.textCollection);
        TextView purchasePriceText = root.findViewById(R.id.textPurchasePrice);
        TextView purchaseDateText = root.findViewById(R.id.textPurchaseDate);
        TextView tagsText = root.findViewById(R.id.textTags);
        CarouselView carouselView = root.findViewById(R.id.viewItemCarousel);

        nameText.setText(nameText.getText()+" " + name);
        statusText.setText(statusText.getText() + " " + status);
        collectionText.setText(collectionText.getText()+" " + collectionName);
        purchasePriceText.setText("Purchase Price: "+ price);
        purchaseDateText.setText("Purchase Date: " + purchase_info);
        tagsText.setText(tagsText.getText() + " " + tag);


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

}
