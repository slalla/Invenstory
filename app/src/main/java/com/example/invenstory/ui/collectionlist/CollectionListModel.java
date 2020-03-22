package com.example.invenstory.ui.collectionlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.invenstory.model.Collection;
import com.example.invenstory.model.Item;

import java.util.ArrayList;

//TODO This is a temp data file. to see how to implement it with the other please refer to CollectionListViewModel
// which is the correct file that will be used in the final project
// you may want to keep some of the methods from this file however as they are not implemented in the other ViewModel
public class CollectionListModel extends ViewModel {

    private MutableLiveData<ArrayList<Collection>> collectionListLive;

    private ArrayList<Collection> collectionList = new ArrayList<Collection>();
    private MutableLiveData<ArrayList<Collection>> collectionLive;

    public CollectionListModel() {

        // ***** TEMPORARY VARIABLES FOR PROTOTYPE: PAUL
        Item seikoSkx007 = new Item("Seiko SKX007", 0, 10, "500.00 CAD", "Fredericton", null);
        Item omegaSpeedy = new Item("Omega Speedmaster", 0, 9, "6000.00 CAD", "Seoul", null);
        Item rolexSubm = new Item("Rolex Submariner", 0, 8, "31000.00 CAD", "Fredericton", null);
        Item longineCon = new Item("Longine Conquest", 0, 7, "1500.00 CAD", "Seoul", null);
        Item orientRay = new Item("Orient Ray II", 0, 6, "200.00 CAD", "Fredericton", null);
        Item orientMako = new Item("Orient Mako II", 0, 5, "200.00 CAD", "Seoul", null);
        Item timexWeek = new Item("Timex Weekender", 0, 4, "50.00 CAD", "Fredericton", null);
        Item casioGshock = new Item("Casio G Shock", 0, 3, "200.00 CAD", "Ottawa", null);
        Item patekNaut = new Item("Patek Phillipe Nautilus", 0, 2, "60000.00 CAD", "Fredericton", null);
        Item rolexDate = new Item("Rolex DateJust", 0, 1, "9000.00 CAD", "Toronto", null);
        Item omegaRail = new Item("Omega Railmaster", 0, 2, "5000.00 CAD", "Fredericton", null);
        Item omegaSea = new Item("Omega Seamaster", 0, 3, "5500.00 CAD", "Vancouber", null);
        Collection a = new Collection("Watches", 0);
        a.addItem(seikoSkx007);
        a.addItem(omegaRail);
        a.addItem(omegaSea);
        a.addItem(rolexDate);
        a.addItem(omegaSpeedy);
        a.addItem(rolexSubm);
        a.addItem(longineCon);
        a.addItem(orientMako);
        a.addItem(orientRay);
        a.addItem(timexWeek);
        a.addItem(casioGshock);
        a.addItem(patekNaut);

        Item b1 = new Item("Converse Chuck Tay", 0, 10, "75.00 CAD", "Fredericton", null);
        Item b2 = new Item("Vans Authentic", 0, 9, "65.00 CAD", "Seoul", null);
        Item b3 = new Item("Dr. Martens 1460", 0, 8, "200.00 CAD", "Fredericton", null);
        Item b4 = new Item("Nike Air Force 1", 0, 7, "100.00 CAD", "Seoul", null);
        Item b5 = new Item("test shoes 1", 0, 6, "123.00 CAD", "Fredericton", null);
        Item b6 = new Item("test shoes 2", 0, 5, "321.00 CAD", "Seoul", null);
        Item b7 = new Item("test shoes 3", 0, 4, "456.00 CAD", "Fredericton", null);
        Item b8 = new Item("test shoes 4", 0, 3, "654.00 CAD", "Ottawa", null);
        Item b9 = new Item("test shoes 5", 0, 2, "789.00 CAD", "Fredericton", null);
        Collection b = new Collection("Shoes", 1);
        b.addItem(b1);
        b.addItem(b2);
        b.addItem(b3);
        b.addItem(b4);
        b.addItem(b5);
        b.addItem(b6);
        b.addItem(b7);
        b.addItem(b8);
        b.addItem(b9);
        // ***** TEMPORARY VARIABLES FOR PROTOTYPE: PAUL

        // ***** TEMPORARY VARIABLES FOR PROTOTYPE: PAUL
        Collection c = new Collection("Books", 2);
        Collection d = new Collection("Games", 3);
        Collection e = new Collection("Rings", 4);
        Collection f = new Collection("Test1", 5);
        Collection g = new Collection("Test2", 6);
        Collection h = new Collection("Test3", 7);
        Collection i = new Collection("Test4", 8);
        Collection j = new Collection("Test5", 9);
        Collection k = new Collection("Test6", 10);
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
        collectionListLive = new MutableLiveData<ArrayList<Collection>>();
        collectionListLive.setValue(collectionList);
    }

    public LiveData<ArrayList<Collection>> getCollectionList() {
        return collectionListLive;
    }

    //TODO remove this call and allow it to update db instead
    public void addCollection(Collection collectionIn){
        collectionList.add(collectionIn);
        collectionListLive.postValue(collectionList);
    }

    public int getCollectionLength(){
        return collectionList.size();
    }

    public Collection getCollection(int id) {
        Collection selected = new Collection("temp", -1);
        for (Collection collection : this.collectionList) {
            if (collection.getId() == id) {
                selected = collection;
            }
        }
        return selected;
    }
}