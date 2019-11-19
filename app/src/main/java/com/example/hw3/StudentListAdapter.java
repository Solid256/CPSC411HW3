package com.example.hw3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StudentListAdapter extends BaseAdapter {

    // The student DB.
    private StudentDB mStudentDB;

    public StudentListAdapter(Context sContext) {
        mStudentDB = StudentDB.GetSingleton();
    }

    // Getters:
    @Override
    public int getCount() {
        return mStudentDB.GetStudents().size();
    }

    @Override
    public Object getItem(int i) {
        return mStudentDB.GetStudents().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // The row view being created.
        View row_view;

        // If the view is null, create a new row view. Otherwise, just set the row view to the
        // // current view.
        if(view == null) {
            // The layout inflator to create the row view.
            LayoutInflater layoutInflator = LayoutInflater.from(viewGroup.getContext());

            // Create the row view.
            row_view = layoutInflator.inflate(R.layout.student_row, viewGroup, false);
        } else {
            row_view = view;
        }

        // The current student being extracted for information to put into the text views inside of
        // the row view.
        Student curStudent = mStudentDB.GetStudents().get(i);

        // The current text view being modified.
        TextView curTextView = row_view.findViewById(R.id.first_name);

        // Set the text for the text views in the row views with the current student's information.
        if(curTextView != null) {
            curTextView.setText(curStudent.GetFirstName());
        }

        curTextView = row_view.findViewById(R.id.last_name);

        if(curTextView != null) {
            curTextView.setText(curStudent.GetLastName());
        }

        // Give the view a tag.
        row_view.setTag(new Integer(i));

        row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // The intent for creating the details activity for the detail page.
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);

                // The view tag for the view object.
                int viewTag = (Integer) view.getTag();

                // Send the student index over to the details page.
                intent.putExtra("student_Index", viewTag);

                view.getContext().startActivity(intent);
            }
        });

        return row_view;
    }

    public StudentDB GetStudentDB() {
        return mStudentDB;
    }
}
