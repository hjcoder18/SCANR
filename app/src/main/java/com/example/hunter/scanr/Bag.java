package com.example.hunter.scanr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Bag extends AppCompatActivity{

    /**********************************************************************
     * Questions to ask
     *  - Should bag know what shelf it is on? A: needs to know the aisle
     *  - Does name come as a single string, or does first and last name come
     *    as separate strings?
     **********************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bag_content);
    }

    private static final String TAG = "EntityListActivity";

    private List<Book> books = new ArrayList<Book>();
    private String bagId; //the bag id is the main key and the primary key by which they are ordered...
    private String studentFirstName; // used by bag class
    private String studentLastName;  // used by bag class
    private int studentID;
    private int aisle;
    private String shelfID; //
    //private int bagIndex;  // used by Shelf class

    /*88888888888888888888888888888888888888888888888888888888888888888
      NOTES FROM Hunter
      To be used by shelf
        - get first bag student's last name
        - get last bag student's last name
    8888888888888888888888888888888888888888888888888888888888888888888*/
    public Bag() {
        //bagIndex = -1;
    }

    //non-default constructor
    public Bag(String id) {
        bagId = id;
    }

    //add a book to the list of books
    public void addBook(Book book) {
        try {
            books.add(book);
        } catch(Exception e) {
            Log.d(TAG, "ERROR: Did not add the book to bag!", e);
        }

    }

    //STUDENT NAME getters and setters
    public void setStudentName (String firstName, String lastName) {
        studentFirstName = firstName;
        studentLastName = lastName;
        Log.i(TAG, "This will set the name of the student.");
    }
    public String getStudentName() {
        String name = studentLastName + ", " + studentFirstName;
        return name;
    }

    //STUDENT ID getters and setters
    public void setStudentID (int id) {
        studentID = id;
    }
    public int getStudentID() {
        return studentID;
    }


    //ID getters and setters
    public void setBagId (String id) {
        bagId = id;
    }
    public String getBagId() {
        return bagId;
    }

    //return the number of books in the bag...
    public double numberOfBooks() {
        return books.size();
    }

//    public void setIndex (int ind) { bagIndex = ind; }
//    public int getIndex() { return bagIndex; }

    //AISLE getters and setters
    public void setAisle(int pos) {
        this.aisle = pos;
    }
    public int getAisle() {
        return aisle;
    }

    //SHELF getters and setters
    public void setShelf(String shelf) {
        shelfID = shelf;
    }
    public String getShelfID() {
        return shelfID;
    }

}
