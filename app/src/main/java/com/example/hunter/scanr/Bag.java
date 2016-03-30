package com.example.hunter.scanr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * The Bag Class, represents each bag on a rack
 *
 * The bags are being checked on each rack, as such the bag class represents
 * the information being held by each bag, namely the students information
 * bag content information has been excluded as this is outside the scope of this
 * program.
 *
 * @author Korey MacGill, Hunter Marshall, William Montesdeoca
 * @version 2016.0215
 * @since 1.0
 */
public class Bag extends AppCompatActivity{

    /*8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
      8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
      88 DELETE ME, DELETE ME, DELETE ME, DELETE ME, DELETE ME, DELETE ME, DELETE ME, DELETE ME,  88
      88 DELETE ME, DELETE ME, DELETE ME, DELETE ME, DELETE ME, DELETE ME, DELETE ME, DELETE ME   88
      8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
      8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.bag_content);
    }

    private static final String TAG = "BagClass"; // To be used for log

    private String bagId; //the bag id is the main key and the primary key by which they are ordered...
    private String studentFirstName; // used by bag class
    private String studentLastName;  // used by bag class
    private int studentID;           // The student
    private String rackID; //
    //private int bagIndex;  // used by Shelf class

    /**
     * Default Constructor
     */
    public Bag() {
        bagId = "";
        studentID = 0;
        rackID = "";
        studentFirstName = "";
        studentLastName = "";
    }

    /**
     * non-default constructor
     *
     * @param id - the unique identifier of the bag.
     */
    public Bag(String id) {
        bagId = id;
    }

    /**
     * Method that sets the student's name associated with the bag.
     *
     * @param firstName - the first name of the student
     * @param lastName - the last name of the student
     */
    public void setStudentName (String firstName, String lastName) {
        studentFirstName = firstName;
        studentLastName = lastName;
        Log.i(TAG, "This will set the name of the student.");
    }

    /**
     * The accessor for retrieving the student's first and last name
     *
     * @return name - a string of the students first and last name
     */
    public String getStudentName() {
        String name = studentLastName + ", " + studentFirstName;
        return name;
    }

    /**
     * The student id mutator method
     *
     * @param id - the id that the student's id will be set to.
     */
    public void setStudentID (int id) {
        studentID = id;
    }

    /**
     * Accessor method for retrieving a students ID
     *
     * @return studentID - returns the students ID
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * mutator method for bag's ID
     *
     * @param id - the id the bag's ID will be set to.
     */
    public void setBagId (String id) {
        bagId = id;
    }

    /**
     * Accessor Method for retrieving the bag ID
     *
     * @return bagId - the bag's id
     */
    public String getBagId() {
        return bagId;
    }

    /**
     * the mutator method to set and change the Rack ID of the bag
     *
     * @param rack - the rack to set the bag's rackID to
     */
    public void setRack(String rack) {
        rackID = rack;
    }

    /**
     * Accessor method to retrieve the rackID of the bag
     *
     * @return - rackID - the rackID of the bag
     */
    public String getRackID() {
        return rackID;
    }

}
