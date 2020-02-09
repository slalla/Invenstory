package com.example.invenstory.model;
import java.util.Date;
import java.util.ArrayList;

/**
 * The following class represents an object item that may be stored in the database
 */
public class Item {

    /**
     * The name of the item. Eg Vans Oldskool
     */
    private String name;

    /**
     * The item's physical condition.
     */
    private Condition condition;

    /**
     * The cost of the item.
     */
    private String price;

    /**
     * The item's current location. Eg Bedroom
     */
    private String location;


    /**
     * The id of the collection that the item is placed in
     */
    private int collectionID;

    /**
     *
     */
    private Date initDate;

    /**
     * The item's additional attributes that are stored.
     * May need an additional array list to keep hold of the attribute's name
     */
    private ArrayList<String> attributes;

    /**
     * Creates Item with no extra attributes.
     * @param name Name of item. Ex: Vans Oldskool
     * @param collectionID ID of Collection that the item is in. Ex: Footwear may correspond to a value of 1
     * @param condition The condition of the item Ex: Mint condition
     * @param price The purchase price of the item
     * @param location The current location of the item
     */
    public Item(String name, int collectionID, int condition, String price, String location, Date initDate) {
        this.name = name;
        this.collectionID = collectionID;
        this.price = price;
        this.location = location;
        setCondition(condition);
        if(initDate == null) {
            this.initDate = new Date();
        }
        else{
            this.initDate = initDate;
        }
    }

    /**
     * Creates Item with extra attributes.
     * @param name Name of item. Ex: Vans Oldskool
     * @param collectionID ID of Collection that the item is in. Ex: Footwear may correspond to a value of 1
     * @param condition The condition of the item Ex: Mint condition
     * @param price The purchase price of the item
     * @param location The current location of the item
     * @param att List of extra attributes of the item.
     */
    public Item(String name, int collectionID, int condition, String price, String location, Date initDate, ArrayList<String> att) {
        this(name, collectionID, condition, price, location, initDate);
        this.attributes = att;
    }


    public String getName() { return this.name; }
    public void setName(String name) {this.name = name;}

    public int getCollectionID() { return this.collectionID;}
    public void setCollectionID(int collectionID) {this.collectionID = collectionID;}

    public Condition getCondition() { return  this.condition;}
    private void setCondition(int conditionIn){
        condition = Condition.getCondition(conditionIn);
    }
    private String getConditionText() { return condition.toString();}

    public String getPrice() { return this.price; }
    public void setPrice(String price) { this.price = price; }

    public String getLocation() { return this.location;}
    public void setLocation(String location) { this.location = location;}

    public Date getDate() {return this.initDate;}
    public void setDate(Date initDate){this.initDate = initDate;}

    public ArrayList<String> getAttributes() {return attributes; }
    public void setAttributes(ArrayList<String> attributes) {this.attributes = attributes;}

    //TODO Implement this method that will allow a user to enter new attributes for their item
    public void editAttributes(ArrayList<String> newAttributes){}
}
