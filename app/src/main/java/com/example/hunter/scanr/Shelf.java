package com.example.hunter.scanr;

import java.util.List;

/**
 * Rack Class
 * This class is used to create a rack object that will have a list of bags.
 *
 * @author Korey MacGill, Hunter Marshall, William Montesdeoca
 * @version 2016.0215
 * @since 1.0
 */
public class Shelf {
    private static final String TAG = "EntityListActivity";

    public String RoomCode;
    public List<String> Containers;

    public List<String> getContainers() {
        return Containers;
    }

    public void setContainers(List<String> containers) {
        Containers = containers;
    }

    public String getRoomCode() {
        return RoomCode;
    }

    public void setRoomCode(String roomCode) {
        RoomCode = roomCode;
    }

    /**
     * DefaultConstructor
     */
    public Shelf() {}

    /**
     * Non-Default Constructor - sets the rack id
     *
     * @param code - id of the rack, which is the car code for the rack
     */
    public Shelf(String code) {         // Non-Default Contructor
        RoomCode = code;
    }

    /**
     * Adds a scanned container, from the rack, to a list.
     *
     * @param bagCode - passes in the bag that needs to be added to the list
     */
    public void add(String bagCode) {
        Containers.add(bagCode);
    }

    /**
     * Returns the number of bags in the rack
     *
     * @return numOfBags - returns number of bags
     */
    public double size() {
        int amount = Containers.size();
        return amount;
    }
}