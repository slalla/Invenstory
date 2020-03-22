package com.example.invenstory.ui.newItem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.invenstory.Home;
import com.example.invenstory.R;

public class newItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Home.setFabOff();
        //TODO change Temp page id
        Home.setPageID(-1);
        return inflater.inflate(R.layout.fragment_new_item, container, false);
    }
}
