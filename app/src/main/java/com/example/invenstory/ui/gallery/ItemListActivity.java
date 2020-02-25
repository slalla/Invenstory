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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.invenstory.MainActivity;
import com.example.invenstory.R;
import com.example.invenstory.ViewItemActivity;
import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    private ListView listView;
    private GalleryViewModel galleryViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        listView = findViewById(R.id.item_list_view);

        Intent intent = getIntent();
        int collectionId = intent.getIntExtra("COLLECTION_ID", 1);

        // ***** TEMPORARY USING GALLERY VIEW MODEL, TALK WITH GROUP ABOUT FUTURE WIREFRAME
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        Collection collection = galleryViewModel.getCollection(collectionId);
        ArrayList<Item> item = collection.getCollection();

        if (item.size() == 0) {
            Toast.makeText(this, "PROTOTYPE: This collection does not have any items.", Toast.LENGTH_SHORT).show();
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

        MyAdapter adapter = new MyAdapter(this, mItemName, mItemPrice, images);
        listView.setAdapter(adapter);

        // ***** Test: checking if tapping collection work
        listView.setOnItemClickListener((parent, view, position, id) -> {

            // Should open item page for that item selected

            // temp toast
            if (position == 0) {
                //Toast.makeText(this, "First Item", Toast.LENGTH_LONG).show();
                Intent t = new Intent(ItemListActivity.this, ViewItemActivity.class);
                t.putExtra("itemName", mItemName[position]);
                t.putExtra("itemPrice", mItemPrice[position]);
                startActivity(t);

            }
            if (position == 1) {
                Toast.makeText(this, "Second Item", Toast.LENGTH_LONG).show();
            }
            if (position == 2) {
                Toast.makeText(this, "Third Item", Toast.LENGTH_LONG).show();
            }
            if (position == 3) {
                Toast.makeText(this, "Fourth Item", Toast.LENGTH_LONG).show();
            }
            if (position == 4) {
                Toast.makeText(this, "Fifth Item", Toast.LENGTH_LONG).show();
            }
            if (position == 5) {
                Toast.makeText(this, "Sixth Item", Toast.LENGTH_LONG).show();
            }
        });
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
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


