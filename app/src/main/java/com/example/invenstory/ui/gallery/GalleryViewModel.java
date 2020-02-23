package com.example.invenstory.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.invenstory.model.Collection;

import java.util.ArrayList;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Collection>> collectionLive;

    public GalleryViewModel() {

        // This is for ITEM LIST in the future
//        // ***** TEMPORARY VARIABLES FOR PROTOTYPE: PAUL
//        Item seikoSkx007 = new Item("Seiko SKX007", 0, 10, "500.00 CAD", "Fredericton", null);
//        Item omegaSpeedy = new Item("Omega Speedmaster", 0, 9, "6000.00 CAD", "Seoul", null);
//        Item rolexSubm = new Item("Rolex Submariner", 0, 8, "31000.00 CAD", "Fredericton", null);
//        Item longineCon = new Item("Longine Conquest", 0, 7, "1500.00 CAD", "Seoul", null);
//        Item orientRay = new Item("Orient Ray II", 0, 6, "200.00 CAD", "Fredericton", null);
//        Item orientMako = new Item("Orient Mako II", 0, 5, "200.00 CAD", "Seoul", null);
//        Item timexWeek = new Item("Timex Weekender", 0, 4, "50.00 CAD", "Fredericton", null);
//        Item casioGshock = new Item("Casio G Shock", 0, 3, "200.00 CAD", "Ottawa", null);
//        Item patekNaut = new Item("Patek Phillipe Nautilus", 0, 2, "60000.00 CAD", "Fredericton", null);
//        Item rolexDate = new Item("Rolex DateJust", 0, 1, "9000.00 CAD", "Toronto", null);
//        Item omegaRail = new Item("Omega Railmaster", 0, 2, "5000.00 CAD", "Fredericton", null);
//        Item omegaSea = new Item("Omega Seamaster", 0, 3, "5500.00 CAD", "Vancouber", null);
//        collection.addItem(seikoSkx007);
//        collection.addItem(omegaRail);
//        collection.addItem(omegaSea);
//        collection.addItem(rolexDate);
//        collection.addItem(omegaSpeedy);
//        collection.addItem(rolexSubm);
//        collection.addItem(longineCon);
//        collection.addItem(orientMako);
//        collection.addItem(orientRay);
//        collection.addItem(timexWeek);
//        collection.addItem(casioGshock);
//        collection.addItem(patekNaut);
//        // ***** TEMPORARY VARIABLES FOR PROTOTYPE: PAUL

        // ***** TEMPORARY VARIABLES FOR PROTOTYPE: PAUL
        Collection a = new Collection("Watches", 0);
        Collection b = new Collection("Shoes", 1);
        Collection c = new Collection("Books", 2);
        Collection d = new Collection("Games", 3);
        Collection e = new Collection("Rings", 4);
        Collection f = new Collection("Test1", 5);
        Collection g = new Collection("Test2", 6);
        Collection h = new Collection("Test3", 7);
        Collection i = new Collection("Test4", 8);
        Collection j = new Collection("Test5", 9);
        Collection k = new Collection("Test6", 10);
        ArrayList<Collection> collectionList = new ArrayList<Collection>();
        collectionList.add(a);
        collectionList.add(b);
        collectionList.add(c);
        collectionList.add(d);
        collectionList.add(e);
        collectionList.add(f);
        collectionList.add(g);
        collectionList.add(h);
        collectionList.add(i);
        collectionList.add(j);
        collectionList.add(k);
        // ***** TEMPORARY VARIABLES FOR PROTOTYPE: PAUL
        collectionLive = new MutableLiveData<>();
        collectionLive.setValue(collectionList);
    }

    public LiveData<ArrayList<Collection>> getCollection() {
        return collectionLive;
    }
}