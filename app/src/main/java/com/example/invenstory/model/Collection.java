package com.example.invenstory.model;

/**
 * The following class an Collection that may be stored in the database
 */
public class Collection {

    /**
     * The name of the collection. EG Video Games
     */
    private String name;

    /**
     * The id of the collection
     */
    private int id;

    /**
     * Creates a new Collection item
     * @param name The name of the collection
     * @param id The collection's id
     */
    public Collection(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName() { return name;}

    public int getId() { return id;}
}
