package com.example.invenstory.model;
import java.util.Date;
import java.util.ArrayList;

public class Item {

    private String name;
    private String groupName;
    private Date initDate;
    private ArrayList<String> attributes;

    /**
     * Creates Item with no extra attributes.
     * @param name Name of item. Ex: Vans Oldskool
     * @param groupName Name of group that item is in. Ex: Footwear
     */
    public Item(String name, String groupName) {
        this.name = name;
        this.groupName = groupName;
        this.initDate = new Date();
    }

    /**
     * Creates Item with extra attributes.
     * @param att List of extra attributes of the item.
     */
    public Item(String name, String groupName, ArrayList<String> att) {
        this(name, groupName);
        this.attributes = att;
    }

    public String getName() {
        return this.name;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public Date getDate() {
        return this.initDate;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

}
