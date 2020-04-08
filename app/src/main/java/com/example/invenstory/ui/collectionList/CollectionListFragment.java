package com.example.invenstory.ui.collectionList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Collection;
import com.example.invenstory.ui.collectionList.CollectionListFragmentDirections.ActionNavGalleryToItemListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CollectionListFragment extends Fragment {

    private CollectionListViewModel collectionListViewModel;

    private ListView listView;

    // used for long click to open action mode menu
    private AbsListView.MultiChoiceModeListener modeListener;
    private static ActionMode actionMode;
    private boolean isActionMode = false;
    private List<String> userSelection = new ArrayList<>();
    private int clickedPosition = -1; // init as -1 not 0 or nothing. java makes it set to 0 when set to nothing
    private Menu actionModeMenu;

    // refreshing list data
    public void onStart() {
        super.onStart();
        collectionListViewModel.updateCollectionList();
    }

    // TODO written by Paul: Insert Thumbnail photo for each collection
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setAddCollectionFAB(Home.getFAB());
        Home.setFabOn();

        // connecting files
        View root = inflater.inflate(R.layout.fragment_collection_list, container, false);
        listView = root.findViewById(R.id.collection_list_view);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        initMultipleChoiceModeListener();
        listView.setMultiChoiceModeListener(modeListener);

        collectionListViewModel = new ViewModelProvider(this).get(CollectionListViewModel.class);
        collectionListViewModel.getCollectionList().observe(getViewLifecycleOwner(), collection -> { prepCollection(collection); });
        return root;
    }

    /**
     * Collection initiating method.
     * Setting up list view using collection adapter with collection param.
     * @param collection Collection data in ArrayList form that was fetched from the database
     */
    private void prepCollection(ArrayList<Collection> collection) {
        String[] mCollectionName = new String[collection.size()];
        int[] mCollectionId = new int[collection.size()];
        int[] images = new int[collection.size()];
        for (int i = 0; i < collection.size(); i++) {
            mCollectionName[i] = collection.get(i).getName();
            mCollectionId[i] = collection.get(i).getId();
            images[i] = R.drawable.ic_menu_gallery;
        }

        MyAdapter adapter = new MyAdapter(getActivity(), mCollectionName, mCollectionId, images, actionMode);
        listView.setAdapter(adapter);

        // click to item list page
        listView.setOnItemClickListener((parent, view, position, id) -> {
            int collectionId = mCollectionId[position];

            ActionNavGalleryToItemListFragment actionNavGalleryToItemListFragment =
                    CollectionListFragmentDirections.actionNavGalleryToItemListFragment(collectionId);
            NavController navController = findNavController(this);
            navController.navigate(actionNavGalleryToItemListFragment);
        });
    }


    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String[] rCollectionName;
        int[] rCollectionId;
        int[] rImgs;
        ActionMode actionMode;

        MyAdapter (Context c, String[] collectionName, int[] collectionId, int[] imgs, ActionMode actionMode) {
            super(c, R.layout.collection_row, collectionName);
            this.context = c;
            this.rCollectionName = collectionName;
            this.rCollectionId = collectionId;
            this.rImgs = imgs;
            this.actionMode = actionMode;
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

            CheckBox checkBox = (CheckBox) row.findViewById(R.id.collection_checkBox);
            checkBox.setTag(position);

            if (isActionMode) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }

            if (position == clickedPosition) {
                checkBox.setChecked(true);
                userSelection.add(rCollectionId[position] + "");
                clickedPosition = -1;
                Log.i("array1*",""+position);
                Log.i("array2*",""+userSelection);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    int position = (int) buttonView.getTag();

                    if (userSelection.contains(rCollectionId[position]+"")) {
                        userSelection.remove(rCollectionId[position]+"");
                    } else {
                        userSelection.add(rCollectionId[position]+"");
                    }
                    Log.i("array*",""+userSelection);

                    // remove edit icon if there's more than one selected
                    // only one item is editable at a time
                    MenuItem edit = actionModeMenu.findItem(R.id.collection_edit);
                    if (userSelection.size() > 1) {
                        edit.setVisible(false);
                    } else {
                        edit.setVisible(true);
                    }

                    // setting title
                    CollectionListFragment.actionMode.setTitle(userSelection.size() + " collection selected...");
                }
            });

            return row;
        }
    }

    /**
     * Action Mode method.
     * When user long click, this method is called.
     */
    private void initMultipleChoiceModeListener() {
        modeListener = new AbsListView.MultiChoiceModeListener() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.list_context_menu, menu);
                actionMode = mode;
                isActionMode = true;
                actionModeMenu = menu;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.collection_edit:
                        editCollection();
                        return true;
                    case R.id.collection_delete:
                        deleteCollection();
                        return false;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
                isActionMode = false;
                actionModeMenu = null;
                userSelection.clear();
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle("1 collection selected");
                clickedPosition = position;
            }
        };
    }

    // edit single collection
    public void editCollection() {
        CollectionListFragmentDirections.ActionNavGalleryToNewCollectionFragment actionNavGalleryToNewCollectionFragment =
                CollectionListFragmentDirections.actionNavGalleryToNewCollectionFragment();
        actionNavGalleryToNewCollectionFragment.setEditFlag(1);
        actionNavGalleryToNewCollectionFragment.setCollectionID(Integer.parseInt(userSelection.get(0)));

        NavController navController = findNavController(this);
        navController.navigate(actionNavGalleryToNewCollectionFragment);
        actionMode.finish();
    }

    // delete selected collections
    public void deleteCollection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (userSelection.size() == 0) {
            builder.setCancelable(true);
            builder.setMessage("Choose a collection first!");
            builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Name: ", "You clicked cancel button");
                }
            });
        } else {
            builder.setCancelable(true);
            builder.setMessage("Do you want to delete this collection?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Name: ", "You clicked good button");

                    String name = "";
                    Log.i("name", userSelection.size()+"");
                    for (int j = 0; j < userSelection.size(); j++) {
                        name += collectionListViewModel.getCollection(Integer.parseInt(userSelection.get(j))).getName() + ", ";
                        collectionListViewModel.deleteCollection(Integer.parseInt(userSelection.get(j)));
                    }
                    Toast.makeText(getActivity(), name + " deleted.", Toast.LENGTH_SHORT).show();

                    collectionListViewModel.updateCollectionList();
                    actionMode.finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Name: ", "You clicked cancel button");
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setAddCollectionFAB(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#009688")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewCollection();
            }
        });
    }

    /**
     * Starts the newCollectionFragment with no information populated.
     */
    public void startNewCollection(){
        NavController navController = findNavController(this);
        navController.navigate(R.id.action_nav_gallery_to_newCollectionFragment);
        Home.setFabOff();
        Home.setFabOn();
    }
}