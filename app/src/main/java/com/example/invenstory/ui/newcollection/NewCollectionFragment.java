package com.example.invenstory.ui.newcollection;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Collection;
import com.example.invenstory.ui.gallery.GalleryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.invenstory.Home.setPageID;

public class NewCollectionFragment extends Fragment {

    private NewCollectionViewModel mViewModel;

    public static NewCollectionFragment newInstance() {
        return new NewCollectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setPageID(4);
        Home.setFabOff();
        View root = inflater.inflate(R.layout.new_collection_fragment, container, false);
        TextInputEditText textEditInputLayout = root.findViewById(R.id.NameInput);
        textEditInputLayout.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        FloatingActionButton fab2  = root.findViewById((R.id.floatingActionButton));

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //TODO save the item in the collection page
                    String name = textEditInputLayout.getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                        builder.setCancelable(true);
                        builder.setMessage("You are adding a collection");
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.i("Name: ", "You clicked good button");
                                //TODO Add the item to the list
                                GalleryFragment.galleryViewModel.addCollection(new Collection(name, GalleryFragment.galleryViewModel.getCollectionLength()));
                                Log.i("ID: " , ""+GalleryFragment.galleryViewModel.getCollectionLength());
                                getActivity().onBackPressed();

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.i("Name: ", "You clicked bad button");
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                }

        });
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewCollectionViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setPageID(1);
    }
}
