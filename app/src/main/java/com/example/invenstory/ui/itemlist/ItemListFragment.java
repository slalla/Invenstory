package com.example.invenstory.ui.itemlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.invenstory.R;
import com.example.invenstory.ui.collectionlist.CollectionListViewModel;

//TODO HI PAUL the ItemListView model is the new "model" that should be used here
/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ItemListFragment extends Fragment {
    @Override

    private ListView listView;
    private CollectionListViewModel collectionListViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item_list, container, false);

        listView = root.findViewById(R.id.item_list_view2);

        int collectionId =













        return root;

    }
}
