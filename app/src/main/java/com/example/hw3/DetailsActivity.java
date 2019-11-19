package com.example.hw3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the correct content view.
        setContentView(R.layout.activity_details);

        // The intent that contains the student's CWID.
        Intent intent = getIntent();

        // The student index as defined for the List Adapter.
        int studentIndex = intent.getIntExtra("student_Index", 0);

        mCurStudent = StudentDB.GetSingleton().GetStudents().get(studentIndex);

        rootLayout = findViewById(R.id.details_layout);

        Button button = new Button(this);
        button.setText("Back");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If clicked, exit this view back to the summary activity.
                finish();
            }
        });
        rootLayout.addView(button);

        TextView firstNameTextView = new TextView(this);
        firstNameTextView.setText("First Name: " + mCurStudent.GetFirstName());
        firstNameTextView.setPadding(6,2,6,2);
        rootLayout.addView(firstNameTextView);

        TextView lastNameTextView = new TextView(this);
        lastNameTextView.setText("Last Name: " + mCurStudent.GetLastName());
        lastNameTextView.setPadding(6,2,6,2);
        rootLayout.addView(lastNameTextView);

        TextView CWIDTextView = new TextView(this);
        CWIDTextView.setText("CWID: " + String.valueOf(mCurStudent.GetCWID()));
        CWIDTextView.setPadding(6,2,6,2);
        rootLayout.addView(CWIDTextView);

        // The layout parameters for the course enrollments title.
        LinearLayout.LayoutParams lpCenterTitle = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lpCenterTitle.setMargins(2, 2,2,2);
        lpCenterTitle.gravity = Gravity.CENTER_HORIZONTAL;

        TextView courseEnrollmentsTitle = new TextView(this);
        courseEnrollmentsTitle.setText("Course Enrollments");
        courseEnrollmentsTitle.setLayoutParams(lpCenterTitle);
        courseEnrollmentsTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        rootLayout.addView(courseEnrollmentsTitle);

        // The list of student vehicles.
        ArrayList<CourseEnrollment> courseEnrollments = mCurStudent.GetCourseEnrollments();

        // The grid layout for the student vehicles.
        GridLayout gridDetails = new GridLayout(this);
        gridDetails.setBackgroundColor(Color.BLACK);

        rootLayout.addView(gridDetails);

        int gridRows = courseEnrollments.size() + 1;
        int gridColumns = 2;

        // The specifications for the grid.
        GridLayout.Spec[] specRow = new GridLayout.Spec[gridRows];
        GridLayout.Spec[] specColumn = new GridLayout.Spec[gridColumns];

        // Compute the grid specifications.
        for(int i = 0; i < gridRows; i++)
        {
            specRow[i] = GridLayout.spec(i);
        }

        for(int i = 0; i < gridColumns; i++)
        {
            specColumn[i] = GridLayout.spec(i);
        }

        // The columns that belong to each grid cell.
        LinearLayout[][] columns = new LinearLayout[gridRows][gridColumns];

        // The current column being modified.
        LinearLayout CurColumn;

        // Create all of the linear layouts for the grid cells.
        for(int i = 0; i < gridRows; i++)
        {
            for(int j = 0; j < gridColumns; j++)
            {
                // The current grid layout parameters.
                GridLayout.LayoutParams lpGridLayout =
                        new GridLayout.LayoutParams(specRow[i], specColumn[j]);

                if(j == (gridColumns - 1))
                {
                    lpGridLayout.width = GridLayout.LayoutParams.WRAP_CONTENT;
                }
                else
                {
                    lpGridLayout.width = 300;
                }

                lpGridLayout.height = GridLayout.LayoutParams.WRAP_CONTENT;

                lpGridLayout.setGravity(Gravity.FILL_HORIZONTAL);

                lpGridLayout.setMargins(4,4,4,4);

                // Create a new linear layout for a column inside of the grid cell.
                CurColumn = new LinearLayout(this);

                columns[i][j] = CurColumn;

                CurColumn.setLayoutParams(lpGridLayout);
                CurColumn.setOrientation(LinearLayout.VERTICAL);
                CurColumn.setBackgroundColor(Color.BLACK);

                CurColumn.setGravity(Gravity.FILL_HORIZONTAL);

                gridDetails.addView(CurColumn);
            }
        }

        // The layout parameters for a left column.
        LinearLayout.LayoutParams lpColumnLeft = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lpColumnLeft.setMargins(4, 4,4,4);
        lpColumnLeft.weight = 1;
        lpColumnLeft.gravity = Gravity.START;

        // Grid cell (columnIndex, 0)
        CurColumn = columns[0][0];

        // Create the grid titles.
        TextView textViewTitleMake = new TextView(this);
        textViewTitleMake.setLayoutParams(lpColumnLeft);
        textViewTitleMake.setBackgroundColor(Color.WHITE);
        textViewTitleMake.setText("Course ID");
        textViewTitleMake.setPadding(4,4,4,4);
        textViewTitleMake.setTypeface(Typeface.DEFAULT_BOLD);
        CurColumn.addView(textViewTitleMake);

        // Grid cell (columnIndex, 0)
        CurColumn = columns[0][1];

        TextView textViewTitleModel = new TextView(this);
        textViewTitleModel.setLayoutParams(lpColumnLeft);
        textViewTitleModel.setBackgroundColor(Color.WHITE);
        textViewTitleModel.setText("Grade");
        textViewTitleModel.setPadding(4,4,4,4);
        textViewTitleModel.setTypeface(Typeface.DEFAULT_BOLD);
        CurColumn.addView(textViewTitleModel);

        for(int i = 0; i < courseEnrollments.size(); i++) {

            // The current column index.
            int columnIndex = i+1;

            // Grid cell (columnIndex, 0)
            CurColumn = columns[columnIndex][0];

            CourseEnrollment curCourseEnrollment = courseEnrollments.get(i);

            TextView textViewMake = new TextView(this);
            textViewMake.setLayoutParams(lpColumnLeft);
            textViewMake.setBackgroundColor(Color.WHITE);
            textViewMake.setText(curCourseEnrollment.GetCourseID());
            textViewMake.setPadding(4,4,4,4);
            CurColumn.addView(textViewMake);


            // Grid cell (columnIndex, 1)
            CurColumn = columns[columnIndex][1];

            TextView textViewModel = new TextView(this);
            textViewModel.setLayoutParams(lpColumnLeft);
            textViewModel.setBackgroundColor(Color.WHITE);
            textViewModel.setText(curCourseEnrollment.GetGrade());
            textViewModel.setPadding(4,4,4,4);
            CurColumn.addView(textViewModel);
        }
    }

    @Override
    protected void onStart() {
        Log.d(mProgramPageTag, "onStart() called");
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

    // Variables:

    // The program page tag.
    protected final String mProgramPageTag = "Details Page";

    // The current student being displayed.
    protected Student mCurStudent;

    // The root layout of the activity.
    public LinearLayout rootLayout;
}
