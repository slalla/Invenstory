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
     * The ID of the item
     */
    private int id;

    /**
     * The item's physical condition.
     */
    private Condition condition;

    /**
     * The item's status (ie lost, loaned, etc)
     */
    private Status status;

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
     * An array list that contains the file paths to the items photos on the device
     */
    private ArrayList<String> photoFilePaths;

    /**
     * The item's description
     */
    private String description;

    // TODO written by Paul: discuss item attribute details with team
    /**
     * Creates Item with no extra attributes.
     * @param name Name of item. Ex: Vans Oldskool
     * @param collectionID ID of Collection that the item is in. Ex: Footwear may correspond to a value of 1
     * @param condition The condition of the item Ex: Mint condition
     * @param price The purchase price of the item
     * @param location The current location of the item
     * @param description The description of the item
     */
    public Item(String name, int collectionID, int condition, String price, String location, Date initDate, String description) {
        this.name = name;
        this.collectionID = collectionID;
        this.price = price;
        this.location = location;
        setCondition(condition);
        setStatus(0);
        this.description = description;
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
    public Item(String name, int collectionID, int condition, String price, String location, Date initDate, String description, ArrayList<String> att) {
        this(name, collectionID, condition, price, location, initDate, description);
        this.attributes = att;
    }


    public String getDescription() {return this.description;}
    public void setDescription(String description){this.description = description;}

    public String getName() { return this.name; }
    public void setName(String name) {this.name = name;}

    public int getItemId() { return this.id; }
    public void setItemId(int id) {this.id = id;}

    public int getCollectionID() { return this.collectionID;}
    public void setCollectionID(int collectionID) {this.collectionID = collectionID;}

    public Condition getCondition() { return  this.condition;}
    public void setCondition(int conditionIn){
        condition = Condition.findCondition(conditionIn);
    }
    public String getConditionText() { return condition.toString();}

    public Status getStatus() { return this.status; }
    public void setStatus(int statusId) { this.status = Status.findStatus(statusId); }

    public String getStatusText() { return this.status.toString(); }

    public String getPrice() { return this.price; }
    public void setPrice(String price) { this.price = price; }

    public String getLocation() { return this.location;}
    public void setLocation(String location) { this.location = location;}

    public Date getDate() {return this.initDate;}
    public void setDate(Date initDate){this.initDate = initDate;}

    public ArrayList<String> getAttributes() {return attributes; }
    public void setAttributes(ArrayList<String> attributes) {this.attributes = attributes;}

    //TODO Implement this method that will allow a user to enter new attributes for their item
    //User should be able to add in multiple attributes for their item that will be stored in the database.
    public void editAttributes(ArrayList<String> newAttributes){}

    public ArrayList<String> getPhotoFilePaths() { return photoFilePaths; }
    public void setPhotoFilePaths(ArrayList<String> filePaths) { this.photoFilePaths = filePaths; }


}
