package com.example.invenstory.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.invenstory.R;
import com.example.invenstory.model.Item;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private ListView listView;
    private ArrayList<Item> collection;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // connecting files
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        listView = root.findViewById(R.id.collection_list_view);

        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        galleryViewModel.getCollection().observe(getViewLifecycleOwner(), item -> {

            // ***** Temp : Paul
            String[] mCollectionName = new String[item.size()];
            String[] mCollectionId = new String[item.size()];
            int[] images = new int[item.size()];
            for (int i = 0; i < item.size(); i++) {
                mCollectionName[i] = item.get(i).getName();
                mCollectionId[i] = item.get(i).getId() + "";
                images[i] = R.drawable.ic_menu_gallery;
            }

            MyAdapter adapter = new MyAdapter(getActivity(), mCollectionName, mCollectionId, images);
            listView.setAdapter(adapter);
            // checking if tapping collection work
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        Toast.makeText(getActivity(), "First Item", Toast.LENGTH_LONG);
                    }
                    if (position == 1) {
                        Toast.makeText(getActivity(), "Second Item", Toast.LENGTH_LONG);
                    }
                    if (position == 2) {
                        Toast.makeText(getActivity(), "Third Item", Toast.LENGTH_LONG);
                    }
                    if (position == 3) {
                        Toast.makeText(getActivity(), "Fourth Item", Toast.LENGTH_LONG);
                    }
                    if (position == 4) {
                        Toast.makeText(getActivity(), "Fifth Item", Toast.LENGTH_LONG);
                    }
                    if (position == 5) {
                        Toast.makeText(getActivity(), "Sixth Item", Toast.LENGTH_LONG);
                    }
                }
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