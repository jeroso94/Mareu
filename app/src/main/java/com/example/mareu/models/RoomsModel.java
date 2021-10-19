package com.example.mareu.models;

/**
 * Created by JeroSo94 on 04/10/2021.
 */

/**
 * Model object representing a Room
 */
public class RoomsModel {

    /** Identifier */
    private long mId;
    /** Location of the room */
    private String mLocation;
    /** Name */
    private String mName;

    /**
     * Constructor
     * @param id
     * @param location
     * @param name
     */
    public RoomsModel(long id, String location, String name) {
        this.mId = id;
        this.mLocation = location;
        this.mName = name;
    }


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
