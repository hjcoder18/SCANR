package com.example.hunter.scanr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Bag extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bag_content);
    }

    private List<Book> books;
    private int bagId;
    private String studentName;
    private int studentID;

    //non-default constructor
    public Bag(int id) {
        bagId = id;
    }

    //add a book to the list of books
    public void addBook(Book book) {
        books.add(book);
    }

    //STUDENT NAME getters and setters
    public void setStudentName (String name) {
        studentName = name;
    }
    public String getStudentName() {
        return studentName;
    }


    //STUDENT ID getters and setters
    public void setStudentID (int id) {
        studentID = id;
    }
    public int getStudentID() {
        return studentID;
    }


    //ID getters and setters
    public void setBagId (int id) {
        bagId = id;
    }
    public int getBagId() {
        return bagId;
    }

    //return the number of books in the bag...
    public double numberOfBooks() {
        return books.size();
    }

}
