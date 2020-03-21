package com.example.invenstory.ui.itemlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invenstory.R;
import com.example.invenstory.ViewItemActivity;
import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;
import com.example.invenstory.ui.collectionlist.CollectionListModel;
import com.example.invenstory.ui.collectionlist.CollectionListViewModel;

import java.util.ArrayList;

//TODO HI PAUL the ItemListView model is the new "model" that should be used here
/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ItemListFragment extends Fragment {


    private ListView listView;
    private CollectionListViewModel collectionListViewModel;

    //TODO remove this temp data class
    private CollectionListModel collectionListModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item_list, container, false);

        listView = root.findViewById(R.id.item_list_view2);

        int collectionId = ItemListFragmentArgs.fromBundle(getArguments()).getCollectionId();

        collectionListModel = new ViewModelProvider(this).get(CollectionListModel.class);
        Collection collection = collectionListModel.getCollection(collectionId);
        ArrayList<Item> item = collection.getCollection();

        if (item.size() == 0) {
            Toast.makeText(getActivity(), "PROTOTYPE: This collection does not have any items.", Toast.LENGTH_SHORT).show();
        }

        // ***** Temp : Paul
        String[] mItemName = new String[item.size()];
        String[] mItemPrice = new String[item.size()];
        int[] images = new int[item.size()];

        for (int i = 0; i < item.size(); i++) {
            mItemName[i] = item.get(i).getName();
            mItemPrice[i] = item.get(i).getPrice() + "";
            images[i] = R.drawable.ic_menu_camera;
        }

        ItemListFragment.MyAdapter adapter = new ItemListFragment.MyAdapter(getActivity(), mItemName, mItemPrice, images);
        listView.setAdapter(adapter);

        // ***** Test: checking if tapping collection work
        listView.setOnItemClickListener((parent, view, position, id) -> {

            // Should open item page for that item selected

            // temp toast
            if (position == 0) {
                //Toast.makeText(this, "First Item", Toast.LENGTH_LONG).show();
//                Intent t = new Intent(ItemListActivity.this, ViewItemActivity.class);
//                t.putExtra("itemName", mItemName[position]);
//                t.putExtra("itemPrice", mItemPrice[position]);
//                startActivity(t);

            }
            else {
                Toast.makeText(getActivity(), "I have not been implemented yet", Toast.LENGTH_LONG).show();
            }
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
}
