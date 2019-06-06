package com.example.studentams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentLoggedIn extends AppCompatActivity {
String studentID, studentName,studentBranch;
TextView tvstudentname,tvsempercentage;
Spinner ssemester;
List<Attendance> attendanceList;
AttendanceAdapterForStudents attendanceAdapter;
DatabaseReference studentDB,attendanceDB;
RecyclerView rvsubjects;
Float sempercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_logged_in);
        tvstudentname = findViewById(R.id.textView40);
        tvsempercentage = findViewById(R.id.textView42);
        rvsubjects = findViewById(R.id.rvsubjects);
        ssemester = findViewById(R.id.spinner7);

        studentID = getIntent().getExtras().getString("StudentID");
        studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo").child(studentID);
        studentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvstudentname.setText(dataSnapshot.child("FName").getValue().toString()+"!");
                studentBranch = dataSnapshot.child("Branch").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ssemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(studentBranch).child(studentID).child(ssemester.getSelectedItem().toString());
                rvsubjects.setHasFixedSize(true);
                rvsubjects.setLayoutManager(new LinearLayoutManager(view.getContext()));

                attendanceDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        attendanceList = new ArrayList<>();
                        for(DataSnapshot dssubs : dataSnapshot.getChildren()) {
                            if(dssubs.hasChildren()) {
                                attendanceList.add(new Attendance(dssubs.child("SubjectName").getValue().toString(),
                                        Integer.parseInt(dssubs.child("studentAttendance").getValue().toString()),
                                        Integer.parseInt(dssubs.child("totalAttendanceTaken").getValue().toString())));
                            }
                        }
                        sempercentage = Float.parseFloat(dataSnapshot.child("SemPercentage").getValue().toString());
                        tvsempercentage.setText(sempercentage + "%");
                        if(sempercentage>=75) {tvsempercentage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));}
                        else{tvsempercentage.setTextColor(getResources().getColor(R.color.incorrect));}
                        attendanceAdapter = new AttendanceAdapterForStudents(getApplicationContext(),attendanceList,studentBranch,studentID,ssemester.getSelectedItem().toString());
                        rvsubjects.setAdapter(attendanceAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
