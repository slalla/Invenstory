package com.example.invenstory.ui.newCollection;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Collection;
import com.example.invenstory.ui.newItem.NewItemFragmentArgs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

// TODO written by Paul: Implement camera and gallery access for collection thumbnail
public class NewCollectionFragment extends Fragment {

    private NewCollectionViewModel newCollectionViewModel;

    public static NewCollectionFragment newInstance() {
        return new NewCollectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setSaveCollectionFAB(Home.getFAB());
        Home.setFabOn();

        int collectionId = NewCollectionFragmentArgs.fromBundle(getArguments()).getCollectionID();
        int editFlag = NewCollectionFragmentArgs.fromBundle(getArguments()).getEditFlag();
        // if prev page is view item fragment
        if (editFlag == 1) {
            Home.getToolbar().setTitle("Edit Collection");
        }

        View root = inflater.inflate(R.layout.fragment_new_collection, container, false);
        TextInputEditText nameInput = root.findViewById(R.id.NameInput);
        TextInputEditText descInput = root.findViewById(R.id.descriptionInput);
        enterKeyListener(nameInput, descInput);

        nameInput.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        newCollectionViewModel = new ViewModelProvider(this).get(NewCollectionViewModel.class);
        if(collectionId != -1){
            Collection tempCollection = newCollectionViewModel.getCollection(collectionId);

            Log.i("test2", tempCollection + "");
            nameInput.setText(tempCollection.getName());
            descInput.setText(tempCollection.getDescription());
        }

        Home.getFAB().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String description = descInput.getText().toString();

                boolean nameEnt = name != null && !name.equals("") && !name.isEmpty();
                boolean descriptionEnt =  description != null && !description.equals("") && !description.isEmpty();

                if (!nameEnt) {
                    Toast.makeText(getContext(),
                            "Sorry no value has been entered for name",
                            Toast.LENGTH_SHORT).show();
                }
                if (!descriptionEnt){
                    Toast.makeText(getContext(),
                            "Sorry no value has been entered for description",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setCancelable(true);
                    builder.setMessage("Are you sure you want to save?");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.i("New Collection Frag:", "You clicked confirm button");

                            // id input in the parameter here is irrelevant
                            newCollectionViewModel.insertCollection(new Collection(name, 0, description));
                            Toast.makeText(getActivity(), name + " added to your list.", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.i("New Collection Frag:", "You clicked cancel button");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

        });
        return root;

    }

    public void hideSoftKeyboard() throws NullPointerException{
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * This method listens for key press of Enter key which moves the field focus to another input
     * field that is given as second parameter
     * @param currentField the current input field
     * @param nextField the target input field to move focus to
     */
    public void enterKeyListener(TextInputEditText currentField, TextInputEditText nextField) {
        currentField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    currentField.clearFocus();
                    nextField.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newCollectionViewModel = new ViewModelProvider (this).get(NewCollectionViewModel.class);
    }

    public void setSaveCollectionFAB(FloatingActionButton fab) {
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#009688")));
        fab.setImageResource(R.drawable.ic_save_black_24dp);
        Home.setFabOff();
        Home.setFabOn();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            hideSoftKeyboard();
        }
        catch(NullPointerException e){
            Log.i("New Collection Frag: ", "Don't worry the keyboard just wasn't opened yet.");
        }
    }
}
