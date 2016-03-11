package com.example.hunter.scanr;

import java.util.Date;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Book {
    // Variables for book
    private String name;
    private String author;
    private double width;
    private String publisher;
    private Date copyrightDate;
    private int bookID;
    private String classes;

    //book non-default constructor
    public Book(int id) {
        bookID = id;
    }


    //BOOKNAME getters and setters
    public void setBookName(String id) {
        name = id;
    }

    public String getBookName() {
        return name;
    }


    //BOOKAUTHOR getters and setters
    public void setBookAuthor(String Auth) {
        author = Auth;
    }

    public String getBookAuthor() {
        return author;
    }

    //BOOKWIDTH getters and setters
    public void setWidth(double wdh) {
        width = wdh;
    }

    public double getWidth() {
        return width;
    }

    //BOOKPUBLISHER getters and setters
    public void setPublisher(String pub) {
        publisher = pub;
    }

    public String getPublisher() {
        return publisher;
    }

    //COPYRIGHTDATE getters and setters
    public void setCopyrightDate(Date crDate) {
        copyrightDate = crDate;
    }

    public Date getCopyrightDate() {
        return copyrightDate;
    }

    //BOOKID getters and setters
    public void setBookID(int id) {
        bookID = id;
    }

    public int getBookID() {
        return bookID;
    }

}