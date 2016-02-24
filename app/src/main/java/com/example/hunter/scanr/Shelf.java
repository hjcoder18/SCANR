package com.example.hunter.scanr;

import java.util.List;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Shelf {
    List<Bag> bags;
    String room;
    int roomID;

    void addBag(Bag bag) {
        bags.add(bag);
    }

    // NOT SURE WE NEED THIS
    void removeBag() {
        // are we assuming the API is perfect with no errors?
    }

    // NOT SURE WE NEED THIS
    void display() {

    }
}
