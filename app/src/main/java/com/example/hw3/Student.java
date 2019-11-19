package com.example.hw3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Student extends PersistentObject{

    public Student() {

    }

    // Methods:

    // Initializes the student.
    public void Init(StudentDesc desc) {
        mFirstName = desc.mFirstName;
        mLastName = desc.mLastName;
        mCWID = desc.mCWID;
        mCourseEnrollments = desc.mCourseEnrollments;
    }

    @Override
    public void Insert(SQLiteDatabase database) {
        // The content values being sent to the table.
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName", mFirstName);
        contentValues.put("LastName", mLastName);
        contentValues.put("CWID", mCWID);

        database.insert("STUDENT", null, contentValues);

        // Insert the course enrollments.
        for(int i = 0; i < mCourseEnrollments.size(); i++) {
            mCourseEnrollments.get(i).Insert(database);
        }
    }

    @Override
    public void InitFrom(Cursor cursor, SQLiteDatabase database)
    {
        mFirstName = cursor.getString(cursor.getColumnIndex("FirstName"));
        mLastName = cursor.getString(cursor.getColumnIndex("LastName"));
        mCWID = cursor.getInt(cursor.getColumnIndex("CWID"));

        // Get the course enrollment objects from the database.
        mCourseEnrollments = new ArrayList<CourseEnrollment>();

        // The secondary cursor for the course enrollments of this student.
        Cursor cursor2 = database.query("COURSEENROLLMENT",
                null,
                "CWID=?",
                new String[]{new Integer(mCWID).toString()},
                null,
                null,
                null);

        // Check if there are any course enrollments.
        if(cursor2.getCount() > 0) {

            // Go through every course enrollment in the program that belongs to this student and
            // create them from the database.
            while (cursor2.moveToNext()) {
                CourseEnrollment curCourseEnrollment = new CourseEnrollment();
                curCourseEnrollment.InitFrom(cursor2, database);
                mCourseEnrollments.add(curCourseEnrollment);
            }
        }
    }

    // Setters:
    void SetCWID(int sCWID) {
        mCWID = sCWID;
    }

    void SetFirstName(String sFirstName) {
        mFirstName = sFirstName;
    }

    void SetLastName(String sLastName) {
        mLastName = sLastName;
    }

    void SetCourseEnrollments(ArrayList<CourseEnrollment> sCourseEnrollments) {
        mCourseEnrollments = sCourseEnrollments;
    }

    // Getters:
    int GetCWID() {
        return mCWID;
    }

    String GetFirstName() {
        return mFirstName;
    }

    String GetLastName() {
        return mLastName;
    }

    ArrayList<CourseEnrollment> GetCourseEnrollments() {
        return mCourseEnrollments;
    }

    // Variables:
    // The student's CWID.
    protected int mCWID;

    // The first name of the student.
    protected String mFirstName;

    // The last name of the student.
    protected String mLastName;

    // The course enrollments for this student.
    protected ArrayList<CourseEnrollment> mCourseEnrollments = new ArrayList<>();
}
