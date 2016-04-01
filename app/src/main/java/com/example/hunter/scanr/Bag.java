package com.example.hunter.scanr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.bag_content);
    }

    private static final String TAG = "BagClass"; // To be used for log

    private String bagId;
    private String studentFirstName;
    private String studentLastName;
    private long studentID;
    private String shelfID;

    /**
     * Default Constructor
     */
    public Bag() {
        bagId = "defaultValue";
        studentID = 000000000;
        shelfID = "defaultValue";
        studentFirstName = "defaultValue";
        studentLastName = "defaultValue";
    }


    /**
     * Creates a bag ID
     *
     * @param bagid - bag id
     * @param fName - first name
     * @param lName - last name
     * @param shelfid - shelf id
     * @param studId - student id
     */
    public Bag(String bagid, String fName, String lName, String shelfid, long studId) {
        bagId = bagid;
        studentID = studId;
        shelfID = shelfid;
        studentFirstName = fName;
        studentLastName = lName;
    }

    /**
     * GET STUDENT FIRST NAME
     * Returns the student's first name.
     *
     * @return student's first name
     */
    public String getStudentFirstName() {
        return studentFirstName;
    }

    /**
     * SET STUDENT FIRST NAME
     * Set's the student's first name.
     *
     * @param studentFirstName - the student's first name
     */
    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    /**
     * GET STUDENT LAST NAME
     * Get's the student's last name
     *
     * @return student's last name
     */
    public String getStudentLastName() {
        return studentLastName;
    }

    /**
     * SET STUDENT LAST NAME
     * Set's the student's last name
     *
     * @param studentLastName - student's last name
     */
    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
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
    public long getStudentID() {
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
     * @param rack - the rack to set the bag's shelfID to
     */
    public void setRack(String rack) {
        shelfID = rack;
    }

    /**
     * Accessor method to retrieve the shelfID of the bag
     *
     * @return - shelfID - the shelfID of the bag
     */
    public String getShelfID() {
        return shelfID;
    }

}
