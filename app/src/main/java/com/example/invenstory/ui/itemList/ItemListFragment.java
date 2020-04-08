package com.example.invenstory.ui.itemList;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.Collection;
import com.example.invenstory.ui.collectionList.CollectionListFragment;
import com.example.invenstory.ui.itemList.ItemListFragmentDirections.ActionItemListFragmentToViewItemFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ItemListFragment extends Fragment {

    private ItemListViewModel itemListViewModel;

    private ListView listView;

    private int collectionId;

    private Menu itemListMenu;

    // used for long click to open action mode menu
    private AbsListView.MultiChoiceModeListener modeListener;
    private static ActionMode actionMode;
    private boolean isActionMode = false;
    private List<String> userSelection = new ArrayList<>();
    private int clickedPosition = -1; // init as -1 not 0 or nothing. java makes it set to 0 when set to nothing
    private Menu actionModeMenu;


    // refreshing list data
    @Override
    public void onStart() {
        itemListViewModel.updateItemsList();
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setAddItemFAB(Home.getFAB());
        Home.setFabOn();

        View root = inflater.inflate(R.layout.fragment_item_list, container, false);
        listView = root.findViewById(R.id.item_list_view2);

        // getting collectionId from prev fragment
        collectionId = ItemListFragmentArgs.fromBundle(getArguments()).getCollectionId();
        Home.setCollectionId(collectionId);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        initMultipleChoiceModeListener();
        listView.setMultiChoiceModeListener(modeListener);

        // view model
        itemListViewModel = new ViewModelProvider(this, new ItemListViewModelFactory(getActivity().getApplication(), collectionId)).get(ItemListViewModel.class);
        itemListViewModel.getItemList().observe(getViewLifecycleOwner(), items -> {

            String[] mItemName = new String[items.size()];
            String[] mItemPrice = new String[items.size()];
            String[] images = new String[items.size()];
            int[] mItemId = new int[items.size()];

            for (int i = 0; i < items.size(); i++) {
                mItemName[i] = items.get(i).getName();
                mItemId[i] = items.get(i).getItemId();
                mItemPrice[i] = items.get(i).getPrice() + "";
                if (items.get(i).getPhotoFilePaths() != null) {
                    images[i] = items.get(i).getPhotoFilePaths().get(0);
                } else {
                    images[i] = "";
                }
            }

            ItemListFragment.MyAdapter adapter = new ItemListFragment.MyAdapter(getActivity(), mItemId, mItemName, mItemPrice, images);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                //Note that the first parameter should be the itemID and the second the collectionID
                int itemId = mItemId[position];
                ActionItemListFragmentToViewItemFragment actionItemListFragmentToViewItemFragment =
                        ItemListFragmentDirections.actionItemListFragmentToViewItemFragment(itemId, collectionId);

                NavController navController = findNavController(this);
                navController.navigate(actionItemListFragmentToViewItemFragment);
            });
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        itemListMenu = menu;
        inflater.inflate(R.menu.item_list_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.i("Item List Frag:", "The menu item selected was "+id);
        if(id ==2131230781){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setMessage("Do you want to delete this collection?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Item List Frag: ", "You clicked confirm button");
                    String name = itemListViewModel.getCollection().getName();
                    itemListViewModel.deleteCollection();
                    Toast.makeText(getActivity(), name + " was deleted.", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Item List Frag: ", "You clicked cancel button");
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        int rItemId[];
        String rItemName[];
        String rItemPrice[];
        String rImgs[];

        MyAdapter (Context c, int itemId[], String itemName[], String priceName[], String imgs[]) {
            super(c, R.layout.collection_row, itemName);
            this.context = c;
            this.rItemId = itemId;
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

            File imgFile = new  File(rImgs[position]);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                images.setImageBitmap(myBitmap);
            }
            name.setText(rItemName[position]);
            price.setText(rItemPrice[position]);

            CheckBox checkBox = (CheckBox) row.findViewById(R.id.item_checkBox);
            checkBox.setTag(position);

            if (isActionMode) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }

            if (position == clickedPosition) {
                checkBox.setChecked(true);
                userSelection.add(rItemId[position] + "");
                clickedPosition = -1;
                Log.i("array1*",""+position);
                Log.i("array2*",""+userSelection);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    int position = (int) buttonView.getTag();

                    if (userSelection.contains(rItemId[position]+"")) {
                        userSelection.remove(rItemId[position]+"");
                    } else {
                        userSelection.add(rItemId[position]+"");
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
                    ItemListFragment.actionMode.setTitle(userSelection.size() + " collection selected...");
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
                        editItem();
                        mode.finish();
                        return true;
                    case R.id.collection_delete:
                        deleteItem();
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

    // edit single item
    public void editItem() {
        ItemListFragmentDirections.ActionItemListFragmentToNewItemFragment actionItemListFragmentToNewItemFragment =
                ItemListFragmentDirections.actionItemListFragmentToNewItemFragment();
        actionItemListFragmentToNewItemFragment.setCollectionID(collectionId);
        actionItemListFragmentToNewItemFragment.setItemID(Integer.parseInt(userSelection.get(0)));
        actionItemListFragmentToNewItemFragment.setEditFlag(1);

        NavController navController = findNavController(this);
        navController.navigate(actionItemListFragmentToNewItemFragment);
    }

    // delete selected items
    public void deleteItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (userSelection.size() == 0) {
            builder.setCancelable(true);
            builder.setMessage("Choose an item first!");
            builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Name: ", "You clicked cancel button");
                }
            });
        } else {
            builder.setCancelable(true);
            builder.setMessage("Do you want to delete this item?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Name: ", "You clicked good button");

                    String name = "";
                    Log.i("name", userSelection.size()+"");
                    for (int j = 0; j < userSelection.size(); j++) {
                        name += itemListViewModel.getItem(Integer.parseInt(userSelection.get(j))).getName() + ", ";
                        itemListViewModel.deleteItem(Integer.parseInt(userSelection.get(j)));
                    }
                    Toast.makeText(getActivity(), name + " deleted.", Toast.LENGTH_SHORT).show();

                    itemListViewModel.updateItemsList();
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

    // factory is used to pass extra arguments when view model is initiated
    public class ItemListViewModelFactory implements ViewModelProvider.Factory {
        private Application mApplication;
        private int collectionId;

        public ItemListViewModelFactory(Application application, int id) {
            mApplication = application;
            collectionId = id;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ItemListViewModel(mApplication, collectionId);
        }
    }

    public void setAddItemFAB(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#009688")));
        Home.setFabOff();
        Home.setFabOn();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewItem();
            }
        });
    }

    /**
     * Starts the newItemFragment with no information populated
     */
    public void startNewItem(){
        ItemListFragmentDirections.ActionItemListFragmentToNewItemFragment actionItemListFragmentToNewItemFragment =
                ItemListFragmentDirections.actionItemListFragmentToNewItemFragment();
        actionItemListFragmentToNewItemFragment.setCollectionID(collectionId);

        NavController navController = findNavController(this);
        navController.navigate(actionItemListFragmentToNewItemFragment);
    }
}
