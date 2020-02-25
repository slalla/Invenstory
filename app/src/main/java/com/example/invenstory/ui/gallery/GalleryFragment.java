package com.example.invenstory.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Item;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    //TODO make this non static
    public static GalleryViewModel galleryViewModel;

    private ListView listView;
    private ArrayList<Item> collection;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Home.setFabOn();
        Home.setPageID(1);

        // connecting files
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        listView = root.findViewById(R.id.collection_list_view);

        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        galleryViewModel.getCollectionList().observe(getViewLifecycleOwner(), collection -> {

            // ***** Temp : Paul
            String[] mCollectionName = new String[collection.size()];
            String[] mCollectionId = new String[collection.size()];
            int[] images = new int[collection.size()];
            for (int i = 0; i < collection.size(); i++) {
                mCollectionName[i] = collection.get(i).getName();
                mCollectionId[i] = collection.get(i).getId() + "";
                images[i] = R.drawable.ic_menu_gallery;
            }

            MyAdapter adapter = new MyAdapter(getActivity(), mCollectionName, mCollectionId, images);
            listView.setAdapter(adapter);

            // ***** Test: checking if tapping collection work
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(getActivity(), ItemListActivity.class);
                intent.putExtra("COLLECTION_ID", position);
                startActivity(intent);
            });
        });
        // ***** Temp : Paul
        return root;
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rCollectionName[];
        String rCollectionId[];
        int rImgs[];

        MyAdapter (Context c, String collectionName[], String collectionId[], int imgs[]) {
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
            Id.setText(rCollectionId[position]);

            return row;
        }
    }
}