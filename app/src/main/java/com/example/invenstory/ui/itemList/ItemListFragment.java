package com.example.invenstory.ui.itemList;

import android.app.Application;
import android.content.Context;
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


import java.util.ArrayList;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ItemListFragment extends Fragment {

    private ItemListViewModel itemListViewModel;

    private ListView listView;
    private CollectionListViewModel collectionListViewModel;

    private int collectionId;

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

        // view model
        itemListViewModel = new ViewModelProvider(this, new ItemListViewModelFactory(getActivity().getApplication(), collectionId)).get(ItemListViewModel.class);

        itemListViewModel.getItemList().observe(getViewLifecycleOwner(), item -> {

            if (item.size() == 0) {
                Toast.makeText(getActivity(), "PROTOTYPE: This collection does not have any items.", Toast.LENGTH_SHORT).show();
            }

            String[] mItemName = new String[item.size()];
            String[] mItemPrice = new String[item.size()];
            int[] images = new int[item.size()];
            int[] mItemId = new int[item.size()];

            for (int i = 0; i < item.size(); i++) {
                mItemName[i] = item.get(i).getName();
                mItemId[i] = item.get(i).getItemId();
                mItemPrice[i] = item.get(i).getPrice() + "";
                images[i] = R.drawable.ic_menu_camera;
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
        int rImgs[];

        MyAdapter (Context c, String itemName[], String priceName[], int imgs[]) {
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

            images.setImageResource(rImgs[position]);
            name.setText(rItemName[position]);
            price.setText(rItemPrice[position]);

            return row;
        }
    }

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
