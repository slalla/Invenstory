package com.example.invenstory.ui.collectionList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Item;
import com.example.invenstory.ui.collectionList.CollectionListFragmentDirections.ActionNavGalleryToItemListFragment;
import com.example.invenstory.ui.collectionList.CollectionListFragmentDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CollectionListFragment extends Fragment {

    private CollectionListViewModel collectionListViewModel;

    private ListView listView;
    private ArrayList<Item> collection;

    // refreshing list data
    public void onStart() {
        super.onStart();
        collectionListViewModel.updateCollectionList();
    }

    // TODO written by Paul: Insert Thumbnail photo for each collection
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setAddCollectionFAB(Home.getFAB());
        Home.setFabOn();

        // connecting files
        View root = inflater.inflate(R.layout.fragment_collection_list, container, false);
        listView = root.findViewById(R.id.collection_list_view);

        collectionListViewModel = new ViewModelProvider(this).get(CollectionListViewModel.class);

        collectionListViewModel.getCollectionList().observe(getViewLifecycleOwner(), collection -> {

            String[] mCollectionName = new String[collection.size()];
            int[] mCollectionId = new int[collection.size()];
            int[] images = new int[collection.size()];
            for (int i = 0; i < collection.size(); i++) {
                mCollectionName[i] = collection.get(i).getName();
                mCollectionId[i] = collection.get(i).getId();
                images[i] = R.drawable.ic_menu_gallery;
            }

            MyAdapter adapter = new MyAdapter(getActivity(), mCollectionName, mCollectionId, images);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                int collectionId = mCollectionId[position];

                ActionNavGalleryToItemListFragment actionNavGalleryToItemListFragment =
                        CollectionListFragmentDirections.actionNavGalleryToItemListFragment(collectionId);

                NavController navController = findNavController(this);
                navController.navigate(actionNavGalleryToItemListFragment);
            });
        });


        return root;
    }


    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String[] rCollectionName;
        int[] rCollectionId;
        int[] rImgs;

        MyAdapter (Context c, String[] collectionName, int[] collectionId, int[] imgs) {
            super(c, R.layout.collection_row, collectionName);
            this.context = c;
            this.rCollectionName = collectionName;
            this.rCollectionId = collectionId;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.collection_row, parent, false);
            ImageView images = row.findViewById(R.id.collection_image);
            TextView name = row.findViewById(R.id.collection_name_view);
            TextView Id = row.findViewById(R.id.collection_id_view);

            images.setImageResource(rImgs[position]);
            name.setText(rCollectionName[position]);
            Id.setText(rCollectionId[position]+"");

            return row;
        }
    }

    public void setAddCollectionFAB(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewCollection();
            }
        });
    }

    /**
     * Starts the newCollectionFragment with no information populated.
     */
    public void startNewCollection(){
        NavController navController = findNavController(this);
        navController.navigate(R.id.action_nav_gallery_to_newCollectionFragment);
    }
}