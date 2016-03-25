package com.example.hunter.scanr;

/**
 * Created by Willy on 3/11/2016.
 */
public class Student {

    private int studentID;
    private String studentName;

    /**
     * STUDENT CONSTRUCTOR
     */
    public Student() {}

    /**
     * GET STUDENTID METHOD
     *
     * @return studentID - the student's ID #
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * SET STUDENT ID METHOD
     * @param Inumber - the student's iNumber
     */
    public void setStudentID(int Inumber) {
        Inumber = studentID;
    }

    /**
     * GET NAME METHOD
     * @return studentName - returns the student's name.
     */
    public String getName() {
        return studentName;
    }

    /**
     * SET NAME METHOD
     * @param name - the name of the student.
     */
    public void setName(String name) {
        name = studentName;
    }
}
