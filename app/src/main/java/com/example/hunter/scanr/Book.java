package com.example.hunter.scanr;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Book {
    private String name;
    private String author;
    private int bookID;

    //book non-default constructor
    public Book(int id) {
        bookID = id;
    }


    //BOOKNAME getters and setters
    public void setBookName (String id) {
        name = id;
    }
    public String getBookName() {
        return name;
    }


    //BOOKAUTHOR getters and setters
    public void setBookAuthor (String Auth) {
        author = Auth;
    }
    public String getBookAuthor() {
        return author;
    }


    //BOOKID getters and setters
    public void setBookID (int id) {
        bookID = id;
    }
    public int getBookID() {
        return bookID;
    }
}
