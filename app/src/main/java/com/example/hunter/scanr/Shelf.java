package com.example.hunter.scanr;

import java.util.ArrayList;
import java.util.List;

/**
 * Rack Class
 * This class is used to create a rack object that will have a list of bags.
 * Class was named shelf but upon coming to the end of the project, we were informed that the
 * University store calls them racks and not shelves.
 *
 * @author Korey MacGill, Hunter Marshall, William Montesdeoca
 * @version 2016.0215
 * @since 1.0
 */
public class Shelf {
    private static final String TAG = "ShelfClass"; // To be used for Logs

    public String RoomCode;
    public List<String> Containers;

    /**
     * GET CONTAINERS
     * Returns a Container that will be sent to the API
     *
     * @return Containers - contains the rack and bags
     */
    public List<String> getContainers() {
        return Containers;
    }

    /**
     *  SET CONTAINER
     *  Sets the Container
     *
     * @param containers - the list of racks and bags
     */
    public void setContainers(List<String> containers) {
        Containers = containers;
    }

    /**
     * GET ROOM CODE METHOD
     * Gets the room code, which tells us the location of the rack
     *
     * @return RoomCode - tells us the location of the rack
     */
    public String getRoomCode() {
        return RoomCode;
    }

    /**
     * SET ROOM CODE METHOD
     * Sets the RoomCode
     *
     * @param roomCode - the location of the rack
     */
    public void setRoomCode(String roomCode) {
        RoomCode = roomCode;
    }

    /**
     * Default Constructor
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
     * ADD METHOD
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