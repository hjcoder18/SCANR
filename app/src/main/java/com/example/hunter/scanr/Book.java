package com.example.hunter.scanr;

/**
 * Created by Hunter on 2/24/2016.
 */
public class Book {
    String name;
    String author;
    int bookID;

    void setBookName (String id) {
        name = id;
    }

    String getBookName() {
        return name;
    }

    void setBookAuthor (String Auth) {
        author = Auth;
    }

    String getBookAuthor() {
        return author;
    }

    void setBookID (int id) {
        bookID = id;
    }

    int getBookID() {
        return bookID;
    }
}
