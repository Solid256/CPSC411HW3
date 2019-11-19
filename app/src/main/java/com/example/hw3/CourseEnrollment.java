package com.example.hw3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CourseEnrollment extends PersistentObject{

    public CourseEnrollment() {

    }

    // Methods:

    // Initializes the course enrollment.
    public void Init(CourseEnrollmentDesc desc) {
        mCourseID = desc.mCourseID;
        mGrade = desc.mGrade;
        mCWIDOfOwner = desc.mCWIDOfOwner;
    }

    @Override
    public void Insert(SQLiteDatabase database) {
        // The content values being sent to the database.
        ContentValues contentValues = new ContentValues();
        contentValues.put("CourseID", mCourseID);
        contentValues.put("Grade", mGrade);
        contentValues.put("CWID", mCWIDOfOwner);

        database.insert("COURSEENROLLMENT", null, contentValues);
    }

    @Override
    public void InitFrom(Cursor cursor, SQLiteDatabase database)
    {
        mCourseID = cursor.getString(cursor.getColumnIndex("CourseID"));
        mGrade = cursor.getString(cursor.getColumnIndex("Grade"));
        mCWIDOfOwner = cursor.getInt(cursor.getColumnIndex("CWID"));
    }

    // Setters:
    void SetCourseID(String sCourseID) {
        mCourseID = sCourseID;
    }

    void SetGrade(String sGrade) {
        mGrade = sGrade;
    }

    // Getters:
    String GetCourseID() {
        return mCourseID;
    }

    String GetGrade() {
        return mGrade;
    }

    // Variables:
    // The CWID of the course enrollment's owner.
    private int mCWIDOfOwner;

    // The course ID.
    private String mCourseID;

    // The grade the student got in the course.
    private String mGrade;
}
