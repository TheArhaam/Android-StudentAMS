package com.example.studentams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class PopUpAttendanceActivity extends AppCompatActivity {
String studentBranch;
String studentID;
String semester;
String subjectName;
DatabaseReference attendanceDB;

Float percentage;

TextView tvsubjectname, tvpercentage;
EditText etstudentattendance, etattendancetaken;
Button bupdate;

DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_attendance);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.4));

        df.setRoundingMode(RoundingMode.UP);

        tvsubjectname = findViewById(R.id.textView34);
        tvpercentage = findViewById(R.id.textView38);
        etstudentattendance = findViewById(R.id.editText14);
        etattendancetaken = findViewById(R.id.editText16);
        bupdate = findViewById(R.id.button5);

        studentBranch = getIntent().getExtras().getString("StudentBranch");
        studentID = getIntent().getExtras().getString("StudentID");
        semester = getIntent().getExtras().getString("Semester");
        subjectName = getIntent().getExtras().getString("SubjectName");

        attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(studentBranch).child(studentID).child(semester).child(subjectName);

        tvsubjectname.setText(subjectName);

        etattendancetaken.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etattendancetaken.getText().toString().isEmpty()) {
                    calcPercentage();
                }
            }
        });

        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcPercentage();
                Attendance att = new Attendance(subjectName,Integer.parseInt(etstudentattendance.getText().toString()),Integer.parseInt(etattendancetaken.getText().toString()));
                attendanceDB.setValue(att);
                Toast.makeText(v.getContext(),"Attendance Updated",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void calcPercentage() {
        percentage = Float.parseFloat(etstudentattendance.getText().toString()) / Float.parseFloat(etattendancetaken.getText().toString()) * 100;
        tvpercentage.setText(String.valueOf(percentage));
    }
}
