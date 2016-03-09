package com.example.hunter.scanr;

import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Shelf {
    private ArrayList<Bag> bags;
    private String shelfId;   // String because we can use the barcode
    private String room;      // What room the shelf is in
    private String range;     // The range of what student's is on the shelf
    private double numOfBags; // The number of bags on the shelf

    // CONTRUCTORS
    public Shelf(String id) {         // Non-Default Contructor
        shelfId = id;
    }

    // Setters
    public void setShelfId (String id     ) { shelfId = id;    }
    public void setRoom    (String theRoom) { room = theRoom ; }
    public void setRange() {
        Bag bag1 = bags.get(0);
        String name1 = bag1.getStudentName();

        Bag bag2 = bags.get(bags.size() - 1);
        String name2 = bag2.getStudentName();

        range = name1 + " - " + name2;
    }

    // Getters
    public String getShelfId() { return shelfId; }
    public String getRoom   () { return room;    }
    public String getRange()   { return range;   }

    // add bag to shelf
    public void addBag(Bag bag) {
        bags.add(bag);
        bag.setIndex(bags.indexOf(bag));
        if (bag.getIndex() == (bags.size() - 1) || bag.getIndex() == 0) {
            setRange();
        }
    }

    // removes bag from shelf
    public void removeBag(Bag bag) {
        int index = bag.getIndex();
        if (index == (bags.size() - 1) || index == 0) {
            bags.remove(index);
            setRange();
        } else {
            bags.remove(index);
        }
    }

    // Returns the number of bags in the list(on the shelf)
    public double numberOfBags() {
        return bags.size();
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
