package com.example.invenstory.model;

import java.util.ArrayList;

/**
 * The following class an Collection that may be stored in the database
 */
public class Collection{

    /**
     * The name of the collection. EG Video Games
     */
    private String name;

    /**
     * The id of the collection
     */
    private int id;

    /**
     * Collection of Items
     */
    private ArrayList<Item> collection;

    /**
     * Description of the Collection
     */
    private String description;

    /**
     * Creates a new Collection item
     * @param name The name of the collection
     * @param id The collection's id
     */
    public Collection(String name, int id, String description){
        this.name = name;
        this.id = id;
        this.description = description;
        this.collection = new ArrayList<Item>();
    }

    public String getName() { return name;}
    public void setName(String name) {this.name = name;}

    public int getId() { return id;}
    public void setId(int id) {this.id = id;}

    public String getDescription() { return description;}
    public void setString(String description) {this.description = description;}

    public void addItem(Item val) {
        this.collection.add(val);
    }
    public ArrayList<Item> getCollection() {
        return collection;
    }
}
