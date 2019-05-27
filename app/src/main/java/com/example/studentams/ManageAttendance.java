package com.example.studentams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ManageAttendance extends AppCompatActivity {
    String studentID;
    TextView tvstudentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_attendance);
        tvstudentID = findViewById(R.id.textView29);
        studentID = getIntent().getExtras().getString("studentID");
        tvstudentID.setText(studentID);
    }
}
