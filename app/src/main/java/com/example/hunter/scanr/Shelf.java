package com.example.hunter.scanr;

import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
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

    private List<Bag> bags = new ArrayList<Bag>();
    private String rackId;   // String because we can use the barcode
    private String room;      // What room the shelf is in
    private String range;     // The range of what student's is on the shelf

    /**
     * Non-Default Constructor - sets the rack id
     *
     * @param id - id of the rack, which is the car code for the rack
     */
    public Shelf(String id) {         // Non-Default Contructor
        rackId = id;
    }

    /**
     * The mutator for setting the rack id
     * 
     * @param id - The id of the rack.
     */
    public void setShelfId (String id) { rackId = id;    }

    /**
     * The mutator for setting the room the rack is in.
     *
     * @param theRoom - The room the rack is in.
     */
    public void setRoom (String theRoom) { room = theRoom ; }

    /**
     * The accessor for retrieving the rack ID
     *
     * @return rackId - returns a rack ID
     */
    public String getShelfId() { return rackId; }

    /**
     * The accessor for retrieving the room the rack is in
     *
     * @return room - returns a room
     */
    public String getRoom   () { return room;    }

    /**
     * Adds a scanned back, from the rack, to a list.
     *
     * @param bag - passes in the bag that needs to be added to the list
     */
    public void addBag(Bag bag) {
        bags.add(bag);
    }

    /**
     * Returns the number of bags in the rack
     *
     * @return numOfBags - returns number of bags
     */
    public double numberOfBags() {
        int numOfBags = bags.size();
        return numOfBags;
    }
}

//input = result of scan;
//
//Shelf shelf = new shelf();
//
//shelf.setId(input);

//
//while (!isfinished) {
//        anotherinput = result of another scan;
//        if (anotherinput != input) {
//        Bag temp = new Bag();
//        temp.setid(anotherinput);
//        ...
//        do all the other stuff to create a bag...
//        ...
//        shelf.addbag(temp);
//        }
//        else {
//        isfinished = true;
//        }
//        }
