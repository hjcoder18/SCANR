package com.example.hunter.scanr;

import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Shelf {
    private List<Bag> bags;
    private int shelfId;

    public Shelf(int id) {
        shelfId = id;
    }

    public void addBag(Bag bag) {
        bags.add(bag);
    }

    public void setShelfId (int id) {
        shelfId = id;
    }

    public int getShelfId() {
        return shelfId;
    }

    //return the number of bags in the list
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
