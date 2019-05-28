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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageAttendance extends AppCompatActivity {
    String studentID, studentName;
    TextView tvstudentName, tvstudentID;
    Spinner ssemester;
    DatabaseReference studentDB;
    DatabaseReference attendanceDB;
    BasicStudentInfo bsinfo = new BasicStudentInfo();
    List<Attendance> attendanceList;
    AttendanceAdapter attendanceAdapter;
    RecyclerView rvsubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_attendance);

        tvstudentName = findViewById(R.id.textView28);
        tvstudentID = findViewById(R.id.textView29);
        ssemester = findViewById(R.id.spinner4);

        studentID = getIntent().getExtras().getString("studentID");
        studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo").child(studentID);
        studentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentName = dataSnapshot.child("FName").getValue().toString() +
                        " " + dataSnapshot.child("LName").getValue().toString();
                tvstudentName.setText(studentName);
                tvstudentID.setText(studentID);
                bsinfo = new BasicStudentInfo(dataSnapshot.child("FName").getValue().toString(),
                        dataSnapshot.child("LName").getValue().toString(),
                        Integer.parseInt(dataSnapshot.child("age").getValue().toString()),
                        dataSnapshot.child("Branch").getValue().toString(),
                        dataSnapshot.child("Batch").getValue().toString(),
                        studentID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ssemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                attendanceDB = FirebaseDatabase.getInstance().getReference("Attendancec").child(bsinfo.Branch).child(studentID);
                attendanceDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        rvsubjects = findViewById(R.id.subjectsRecyclerView);
                        rvsubjects.setHasFixedSize(true);
                        rvsubjects.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        attendanceList = new ArrayList<>();
//                        Toast.makeText(view.getContext(),dataSnapshot.getKey(),Toast.LENGTH_LONG).show();
                        for(DataSnapshot ds:dataSnapshot.getChildren()) {
                            //PUT THE KEY IN TOAST AND SEE IF THE VALUES MATCH
                            if(ds.getKey().equals(ssemester.getSelectedItem().toString())) {
                                attendanceList.add(new Attendance(ds.child("SubjectName").getValue().toString(),
                                        Integer.parseInt(ds.child("studentAttendance").getValue().toString()),
                                        Integer.parseInt(ds.child("totalAttendanceTaken").getValue().toString())));
                            }
                        }
                        attendanceAdapter = new AttendanceAdapter(view.getContext(),attendanceList);
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

//        studentID = getIntent().getExtras().getString("studentID");
//        studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo").child(studentID);
//        studentDB.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                displaySInfo(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        ssemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                attendanceDB.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        displayAInfo(dataSnapshot);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

//    private void displayAInfo(DataSnapshot dataSnapshot) {
//        rvsubjects = findViewById(R.id.subjectsRecyclerView);
//        rvsubjects.setHasFixedSize(true);
//        rvsubjects.setLayoutManager(new LinearLayoutManager(this));
//        attendanceList = new ArrayList<>();
//        Attendance attendance = new Attendance();
//        for(DataSnapshot ds:dataSnapshot.getChildren()) {
//            if( ds.child(bsinfo.Branch).equals(ssemester.getSelectedItem().toString()) ) {
//                attendance.SubjectName = ds.child("SubjectName").getValue().toString();
//                attendance.studentAttendance = Integer.parseInt(ds.child("studentAttendance").getValue().toString());
//                attendance.totalAttendanceTaken = Integer.parseInt(ds.child("totalAttendanceTaken").getValue().toString());
//                attendance.percentage = (float) attendance.studentAttendance / attendance.totalAttendanceTaken * 100;
//                attendanceList.add(attendance);
//            }
//        }
//        attendanceAdapter = new AttendanceAdapter(this,attendanceList);
//        rvsubjects.setAdapter(attendanceAdapter);
//    }
//
//    private void displaySInfo(DataSnapshot dataSnapshot) {
//        bsinfo.setFName(dataSnapshot.child("FName").getValue().toString());
//        bsinfo.setLName(dataSnapshot.child("LName").getValue().toString());
//        bsinfo.setAge(Integer.parseInt(dataSnapshot.child("age").getValue().toString()));
//        bsinfo.setBranch(dataSnapshot.child("Branch").getValue().toString());
//        bsinfo.setBatch(dataSnapshot.child("Batch").getValue().toString());
//        bsinfo.setStudentID(dataSnapshot.child("StudentID").getValue().toString());
//        studentName = bsinfo.FName + " " + bsinfo.LName;
//        tvstudentID.setText(bsinfo.StudentID);
//        tvstudentName.setText(studentName);
//
//        attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").
//                child(bsinfo.Branch).
//                child(bsinfo.StudentID).
//                child(ssemester.getSelectedItem().toString());
//
//        attendanceDB.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                displayAInfo(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
