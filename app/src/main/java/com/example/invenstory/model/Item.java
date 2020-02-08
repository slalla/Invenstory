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
    private String condition;

    /**
     * The cost of the item.
     */
    private String price;

    /**
     * The item's current location. Eg Bedroom
     */
    private String location;


    /**
     * The collection that the item is placed in
     * This could potentially be implemented using a map to minimize the size of an Item
     */
    private String groupName;

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
     * @param groupName Name of group that item is in. Ex: Footwear
     * @param condition The condition of the item Ex: Mint condition
     * @param price The purchase price of the item
     * @param location The current location of the item
     */
    public Item(String name, String groupName, String condition, String price, String location) {
        this.name = name;
        this.groupName = groupName;
        this.condition = condition;
        this.price = price;
        this.location = location;
        this.initDate = new Date();
    }

    /**
     * Creates Item with extra attributes.
     * @param name Name of item. Ex: Vans Oldskool
     * @param groupName Name of group that item is in. Ex: Footwear
     * @param condition The condition of the item Ex: Mint condition
     * @param price The purchase price of the item
     * @param location The current location of the item
     * @param att List of extra attributes of the item.
     */
    public Item(String name, String groupName, String condition, String price, String location, ArrayList<String> att) {
        this(name, groupName, condition, price, location);
        this.attributes = att;
    }


    public String getName() {
        return this.name;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getCondition() { return  this.condition;}

    public String getLocation() { return this.location;}

    public Date getDate() {
        return this.initDate;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

}
