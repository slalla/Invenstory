package com.example.invenstory.ui.share;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Item;
import com.example.invenstory.ui.collectionList.CollectionListFragment;
import com.example.invenstory.ui.collectionList.CollectionListFragmentDirections;
import com.example.invenstory.ui.collectionList.CollectionListViewModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private final String TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss";

    private ListView listView;
    private ArrayList<Item> collection;

    // refreshing list data
    public void onStart() {
        super.onStart();
        shareViewModel.updateCollectionList();
    }

    // TODO written by Paul: Insert Thumbnail photo for each collection
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Home.setFabOff();

        // connecting files
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        listView = root.findViewById(R.id.share_collection_list);

        shareViewModel = new ViewModelProvider(this).get(ShareViewModel.class);

        shareViewModel.getCollectionList().observe(getViewLifecycleOwner(), collection -> {

            String[] mCollectionName = new String[collection.size()];
            int[] mCollectionId = new int[collection.size()];
            int[] images = new int[collection.size()];
            for (int i = 0; i < collection.size(); i++) {
                mCollectionName[i] = collection.get(i).getName();
                mCollectionId[i] = collection.get(i).getId();
                images[i] = R.drawable.ic_menu_gallery;
            }

            ShareFragment.MyAdapter adapter = new ShareFragment.MyAdapter(getActivity(), mCollectionName, mCollectionId, images);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                int collectionId = mCollectionId[position];
                //TODO send email to self of file.
                try{
                    File export = createExportFile();
                    PrintWriter printWriter = new PrintWriter(export);
                    printWriter.println("TEST");
                    printWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });


        return root;
    }

    private File createExportFile() throws IOException {
        // Create an export file name
        String timeStamp = new SimpleDateFormat(TIME_STAMP_FORMAT).format(new Date());
        String imageFileName = "EXPORT_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File export = new File(storageDir,
                imageFileName+  // prefix
                ".sql"   // suffix
        );
        return export;
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

            images.setImageResource(rImgs[position]);
            name.setText(rCollectionName[position]);

            return row;
        }
    }
}