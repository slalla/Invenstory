package com.example.invenstory.ui.itemList;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;
import com.example.invenstory.ui.collectionList.CollectionListModel;
import com.example.invenstory.ui.collectionList.CollectionListViewModel;
import com.example.invenstory.ui.itemList.ItemListFragmentDirections.ActionItemListFragmentToViewItemFragment;
import com.example.invenstory.ui.itemList.ItemListFragmentArgs;
import com.example.invenstory.ui.itemList.ItemListFragmentDirections;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ItemListFragment extends Fragment {

    private ItemListViewModel itemListViewModel;

    private ListView listView;

    private int collectionId;

    // refreshing list data
    public void onStart() {
        super.onStart();
        Log.i("Blank text", "This is not working ");
        itemListViewModel.updateItemsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Home.setFabOn();
        //TODO this is a temp value please fix it in the next update
        Home.setPageID(88);

        View root = inflater.inflate(R.layout.fragment_item_list, container, false);
        listView = root.findViewById(R.id.item_list_view2);

        // getting collectionId from prev fragment
        collectionId = ItemListFragmentArgs.fromBundle(getArguments()).getCollectionId();
        Home.setCollectionId(collectionId);

        // view model
        itemListViewModel = new ViewModelProvider(this, new ItemListViewModelFactory(getActivity().getApplication(), collectionId)).get(ItemListViewModel.class);

        itemListViewModel.getItemList().observe(getViewLifecycleOwner(), items -> {

            if (items.size() == 0) {
                Toast.makeText(getActivity(), "PROTOTYPE: This collection does not have any items.", Toast.LENGTH_SHORT).show();
            }

            String[] mItemName = new String[items.size()];
            String[] mItemPrice = new String[items.size()];
            String[] images = new String[items.size()];
            int[] mItemId = new int[items.size()];

            for (int i = 0; i < items.size(); i++) {
                mItemName[i] = items.get(i).getName();
                mItemId[i] = items.get(i).getItemId();
                mItemPrice[i] = items.get(i).getPrice() + "";
                if (items.get(i).getPhotoFilePaths() != null) {
                    images[i] = items.get(i).getPhotoFilePaths().get(0);
                } else {
                    images[i] = "";
                }
            }

            ItemListFragment.MyAdapter adapter = new ItemListFragment.MyAdapter(getActivity(), mItemName, mItemPrice, images);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                //Note that the first parameter should be the itemID and the second the collectionID
                int itemId = mItemId[position];
                ActionItemListFragmentToViewItemFragment actionItemListFragmentToViewItemFragment =
                        ItemListFragmentDirections.actionItemListFragmentToViewItemFragment(itemId, collectionId);

                NavController navController = findNavController(this);
                navController.navigate(actionItemListFragmentToViewItemFragment);
            });
        });
        return root;
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rItemName[];
        String rItemPrice[];
        String rImgs[];

        MyAdapter (Context c, String itemName[], String priceName[], String imgs[]) {
            super(c, R.layout.collection_row, itemName);
            this.context = c;
            this.rItemName = itemName;
            this.rItemPrice = priceName;
            this.rImgs = imgs;
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.item_list_row, parent, false);
            ImageView images = row.findViewById(R.id.item_image);
            TextView name = row.findViewById(R.id.item_name_view);
            TextView price = row.findViewById(R.id.item_price_view);

            File imgFile = new  File(rImgs[position]);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                images.setImageBitmap(myBitmap);
            }
            name.setText(rItemName[position]);
            price.setText(rItemPrice[position]);

            return row;
        }
    }

    // factory is used to pass extra arguments when view model is initiated
    public class ItemListViewModelFactory implements ViewModelProvider.Factory {
        private Application mApplication;
        private int collectionId;

        public ItemListViewModelFactory(Application application, int id) {
            mApplication = application;
            collectionId = id;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ItemListViewModel(mApplication, collectionId);
        }
    }
}
