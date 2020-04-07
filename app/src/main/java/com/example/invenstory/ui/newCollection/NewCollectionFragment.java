package com.example.invenstory.ui.newCollection;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class NewCollectionFragment extends Fragment {

    private NewCollectionViewModel newCollectionViewModel;

    public static NewCollectionFragment newInstance() {
        return new NewCollectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Home.getFAB().setImageResource(R.drawable.ic_save_black_24dp);
        Home.setFabOn();

        View root = inflater.inflate(R.layout.fragment_new_collection, container, false);
        TextInputEditText nameInput = root.findViewById(R.id.NameInput);
        TextInputEditText descInput = root.findViewById(R.id.descriptionInput);
        enterKeyListener(nameInput, descInput);

        nameInput.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        // TODO written by Paul: User should not be able to save when required field isn't filled
        Home.getFAB().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setCancelable(true);
                    builder.setMessage("You are adding a collection");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.i("Name: ", "You clicked good button");

                            // id input in the parameter here is irrelevant
                            newCollectionViewModel.insertCollection(new Collection(name, 0));
                            Toast.makeText(getActivity(), name + " added to your list.", Toast.LENGTH_SHORT).show();
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
        // TODO: Use the ViewModel (I don't think I made this TODO so I'm not sure what it means)
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideKeyboard(getActivity());
    }
}
