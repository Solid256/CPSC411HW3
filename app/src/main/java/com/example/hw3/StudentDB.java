package com.example.hw3;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;


public class StudentDB {

    StudentDB() {
    }

    // Methods:
    public void InitSQL() {
        // The database file handler.
        File databaseFile = mContext.getDatabasePath("student.db");

        // Open or create the SQLite database.
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(databaseFile, null);

        // Deletes the database. Comment out if not needed.
        //SQLiteDatabase.deleteDatabase(databaseFile);

        // Create the default SQL tables.
        CreateDefaultSQLTables();

        // Create the default SQL Student and Course Enrollment objects.
        // Comment out if not needed.
        //CreateDefaultSQLObjects();
    }

    protected void CreateDefaultSQLTables() {
        // The sql commands being executed.
        String sqlCommands;

        sqlCommands = "CREATE TABLE IF NOT EXISTS STUDENT (FirstName Text, LastName Text, CWID INTEGER)";
        mSQLiteDatabase.execSQL(sqlCommands);

        sqlCommands = "CREATE TABLE IF NOT EXISTS COURSEENROLLMENT (CourseID Text, Grade Text, CWID INTEGER)";
        mSQLiteDatabase.execSQL(sqlCommands);
    }

    protected void CreateDefaultSQLObjects() {
        // The students used in this first iteration of the program.
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();

        // The object descriptors for the students and course enrollments.
        StudentDesc studentDesc = new StudentDesc();
        CourseEnrollmentDesc courseEnrollmentDesc = new CourseEnrollmentDesc();

        // Student 1.
        // Vehicles.
        CourseEnrollment course1 = new CourseEnrollment();
        courseEnrollmentDesc.mCourseID = "000001";
        courseEnrollmentDesc.mGrade = "A";
        courseEnrollmentDesc.mCWIDOfOwner = 00000001;
        course1.Init(courseEnrollmentDesc);

        CourseEnrollment course2 = new CourseEnrollment();
        courseEnrollmentDesc.mCourseID = "000002";
        courseEnrollmentDesc.mGrade = "A";
        course2.Init(courseEnrollmentDesc);

        studentDesc.mFirstName = "Adam";
        studentDesc.mLastName = "Jensen";
        studentDesc.mCWID = 00000001;
        studentDesc.mCourseEnrollments.add(course1);
        studentDesc.mCourseEnrollments.add(course2);

        student1.Init(studentDesc);
        student1.Insert(mSQLiteDatabase);


        // Student 2.
        studentDesc.mCourseEnrollments = new ArrayList<>();

        // Vehicles.
        CourseEnrollment course3 = new CourseEnrollment();
        courseEnrollmentDesc.mCourseID = "000001";
        courseEnrollmentDesc.mGrade = "C";
        courseEnrollmentDesc.mCWIDOfOwner = 00000002;
        course3.Init(courseEnrollmentDesc);

        // Course enrollment for sneaking.
        studentDesc.mFirstName = "Luigi";
        studentDesc.mLastName = "Mario";
        studentDesc.mCWID = 00000002;
        studentDesc.mCourseEnrollments.add(course3);

        student2.Init(studentDesc);
        student2.Insert(mSQLiteDatabase);

        // Student 3.
        studentDesc.mCourseEnrollments = new ArrayList<>();

        // Vehicles.
        CourseEnrollment course4 = new CourseEnrollment();
        courseEnrollmentDesc.mCourseID = "000003";
        courseEnrollmentDesc.mGrade = "D";
        courseEnrollmentDesc.mCWIDOfOwner = 00000003;
        course4.Init(courseEnrollmentDesc);

        studentDesc.mFirstName = "Sonic";
        studentDesc.mLastName = "Hedgehog";
        studentDesc.mCWID = 00000003;
        studentDesc.mCourseEnrollments.add(course4);

        student3.Init(studentDesc);
        student3.Insert(mSQLiteDatabase);

        // Student 4.
        studentDesc.mCourseEnrollments = new ArrayList<>();

        // Vehicles.
        CourseEnrollment course5 = new CourseEnrollment();
        courseEnrollmentDesc.mCourseID = "000001";
        courseEnrollmentDesc.mGrade = "A";
        courseEnrollmentDesc.mCWIDOfOwner = 00000004;
        course5.Init(courseEnrollmentDesc);

        // Course enrollment for running.
        CourseEnrollment course6 = new CourseEnrollment();
        courseEnrollmentDesc.mCourseID = "000002";
        courseEnrollmentDesc.mGrade = "A";
        course6.Init(courseEnrollmentDesc);

        // Course enrollment for running.
        CourseEnrollment course7 = new CourseEnrollment();
        courseEnrollmentDesc.mCourseID = "000003";
        courseEnrollmentDesc.mGrade = "C";
        course7.Init(courseEnrollmentDesc);

        studentDesc.mFirstName = "Razputin";
        studentDesc.mLastName = "Aquato";
        studentDesc.mCWID = 00000004;
        studentDesc.mCourseEnrollments.add(course5);
        studentDesc.mCourseEnrollments.add(course6);
        studentDesc.mCourseEnrollments.add(course7);

        student4.Init(studentDesc);
        student4.Insert(mSQLiteDatabase);

        mStudents.add(student1);
        mStudents.add(student2);
        mStudents.add(student3);
        mStudents.add(student4);
    }

    public ArrayList<Student> RetrieveStudentObjects() {
        // The list of students being created.
        ArrayList<Student> studentList = new ArrayList<Student>();

        // The cursor for searching for the students in the database.
        Cursor cursor = mSQLiteDatabase.query("STUDENT",
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                Student curStudent = new Student();
                curStudent.InitFrom(cursor, mSQLiteDatabase);
                studentList.add(curStudent);
            }
        }

        mStudents = studentList;
        return studentList;
    }

    // Setters:
    public void SetStudents(ArrayList<Student> sStudents) {
        mStudents = sStudents;
    }

    public void SetContext(Context sContext) {
        mContext = sContext;
    }

    // Getters:
    static public StudentDB GetSingleton() {
        return mSingleton;
    }

    public ArrayList<Student> GetStudents() {
        return mStudents;
    }

    public Student GetStudentByCWID(int CWID) {
        for(int i = 0; i < mStudents.size(); i++) {
            Student curStudent = mStudents.get(i);

            if(curStudent.GetCWID() == CWID) {
                return curStudent;
            }
        }

        return null;
    }

    public SQLiteDatabase GetSQLiteDatabase() {
        return mSQLiteDatabase;
    }

    // Variables:

    // The singleton of this studentDB object.
    private static final StudentDB mSingleton = new StudentDB();

    // The students in the student database. Starts off non-existent.
    private ArrayList<Student> mStudents = new ArrayList<>();

    // The context of the SQLite database.
    private Context mContext;

    // The SQLite database.
    private SQLiteDatabase mSQLiteDatabase;
}
