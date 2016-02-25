package com.example.hunter.scanr;

import java.util.List;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Bag {
    List<Book> books;
    int bagId;
    String studentName;
    int studentID;

    public Bag(int id) {
        bagId = id;
    }

    void addBag(Book book) {
        books.add(book);
    }

    void setStudentName (String name) {
        studentName = name;
    }

    String getStudentName() {
        return studentName;
    }

    void setStudentID (int id) {
        studentID = id;
    }

    int getStudentID() {
        return studentID;
    }

}
