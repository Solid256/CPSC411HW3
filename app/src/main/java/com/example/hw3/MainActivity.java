package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the correct content view.
        setContentView(R.layout.student_list_lv);

        mStudentListView = findViewById(R.id.student_list_id);

        mStudentDB = StudentDB.GetSingleton();
        mStudentDB.SetContext(this);
        mStudentDB.InitSQL();
        mStudentDB.RetrieveStudentObjects();

        // Create the student list adapter (which also creates the database).
        mStudentListAdapter = new StudentListAdapter(this);

        mStudentListView.setAdapter(mStudentListAdapter);
    }

    @Override
    protected void onStart() {
        Log.d(mProgramPageTag, "onStart() called");
        mStudentListAdapter.notifyDataSetChanged();
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(mProgramPageTag, "onResume() called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(mProgramPageTag, "onPause() called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(mProgramPageTag, "onStop() called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(mProgramPageTag, "onDestroy() called");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                // If there was an intent, read it and construct a new student.
                if (data != null) {
                    // The bundle being sent from the previous activity.
                    Bundle studentBundle = data.getBundleExtra("student");

                    // The first name of the student.
                    String firstName = studentBundle.getString("firstName");

                    // The last name of the student.
                    String lastName = studentBundle.getString("lastName");

                    // The CWID of the student.
                    int CWID = studentBundle.getInt("CWID");

                    // Create a new student.
                    Student student = new Student();

                    // The descriptor for the student.
                    StudentDesc desc = new StudentDesc();

                    desc.mFirstName = firstName;
                    desc.mLastName = lastName;
                    desc.mCWID = CWID;

                    // The vehicle info being sent over.
                    Bundle courseEnrollmentInfoBundle = data.getBundleExtra(
                            "course_enrollments");

                    ArrayList<String> courseEnrollmentInfo = courseEnrollmentInfoBundle.
                            getStringArrayList("course_enrollments");

                    // If there are no vehicles, skip.
                    if(courseEnrollmentInfo != null) {

                        // The course enrollments for the student desc.
                        ArrayList<CourseEnrollment> courseEnrollments = new ArrayList<>();

                        // Extract the vehicle info
                        for (int i = 0; i < courseEnrollmentInfo.size(); i += 2) {

                            CourseEnrollmentDesc courseEnrollmentDesc = new CourseEnrollmentDesc();

                            courseEnrollmentDesc.mCourseID = courseEnrollmentInfo.get(i);
                            courseEnrollmentDesc.mGrade = courseEnrollmentInfo.get(i+1);
                            courseEnrollmentDesc.mCWIDOfOwner = desc.mCWID;

                            CourseEnrollment courseEnrollment = new CourseEnrollment();
                            courseEnrollment.Init(courseEnrollmentDesc);

                            courseEnrollments.add(courseEnrollment);
                        }

                        desc.mCourseEnrollments = courseEnrollments;
                    }

                    student.Init(desc);
                    student.Insert(mStudentDB.GetSQLiteDatabase());

                    // The array of students.
                    ArrayList<Student> students = mStudentListAdapter.GetStudentDB().GetStudents();

                    students.add(student);

                    // Update the data in the adapter.
                    mStudentListAdapter.GetStudentDB().SetStudents(students);

                    // Update the adapter data view.
                    ((BaseAdapter)mStudentListView.getAdapter()).notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Create the menu.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.summary_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The intent for creating the create student page.
        Intent intentStudentCreation = new Intent(this, CreateStudent.class);

        // Start the create student activity. Set it for result to retrieve student data.
        startActivityForResult(intentStudentCreation, 1);
        return true;
    }

    // Variables:

    // The program page tag.
    protected final String mProgramPageTag = "Summary Screen";

    // The student list view.
    protected ListView mStudentListView;

    // The adapter for the student list view.
    protected StudentListAdapter mStudentListAdapter;

    // The student DB.
    private StudentDB mStudentDB;
}
