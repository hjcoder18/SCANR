package com.example.hunter.scanr;

import java.util.List;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Shelf {
    List<Bag> bags;
    int shelfId;

    public Shelf(int id) {
        shelfId = id;
    }

    void addBag(Bag bag) {
        bags.add(bag);
    }

    void setShelfId (int id) {
        shelfId = id;
    }

    int getShelfId() {
        return shelfId;
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
