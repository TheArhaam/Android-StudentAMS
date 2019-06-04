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
    TextView tvstudentName, tvstudentID, tvsempercentage;
    Spinner ssemester;
    DatabaseReference studentDB;
    DatabaseReference attendanceDB;
    BasicStudentInfo bsinfo = new BasicStudentInfo();
    List<Attendance> attendanceList;
    AttendanceAdapter attendanceAdapter;
    RecyclerView rvsubjects;
    int semattendance=0,semattended=0;
    float sempercentage=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_attendance);

        tvstudentName = findViewById(R.id.textView28);
        tvstudentID = findViewById(R.id.textView29);
        ssemester = findViewById(R.id.spinner4);
        tvsempercentage = findViewById(R.id.textView13);

        studentID = getIntent().getExtras().getString("studentID");
        studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo").child(studentID);
        //Displaying Name and StudentID of the respective Student
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

        //Changing subjects according to the selected Semester
        ssemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(bsinfo.Branch).child(studentID).child(ssemester.getSelectedItem().toString());
                rvsubjects = findViewById(R.id.subjectsRecyclerView);
                rvsubjects.setHasFixedSize(true);
                rvsubjects.setLayoutManager(new LinearLayoutManager(view.getContext()));
                attendanceDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        attendanceList = new ArrayList<>();
                        semattended = 0;
                        semattendance = 0;
                                for(DataSnapshot dssubs : dataSnapshot.getChildren()) {
                                    if(dssubs.hasChildren()) {
                                        attendanceList.add(new Attendance(dssubs.child("SubjectName").getValue().toString(),
                                                Integer.parseInt(dssubs.child("studentAttendance").getValue().toString()),
                                                Integer.parseInt(dssubs.child("totalAttendanceTaken").getValue().toString())));
                                        semattended = semattended + Integer.parseInt(dssubs.child("studentAttendance").getValue().toString());
                                        semattendance = semattendance + Integer.parseInt(dssubs.child("totalAttendanceTaken").getValue().toString());
                                    }
                        }
                        if(semattendance == 0){sempercentage = 0;}
                        else{sempercentage = (float) semattended / semattendance * 100;}
                        attendanceDB.child("SemAttended").setValue(semattended);
                        attendanceDB.child("SemAttendance").setValue(semattendance);
                        attendanceDB.child("SemPercentage").setValue(sempercentage);
                        if(sempercentage>=75) {tvsempercentage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));}
                        else{tvsempercentage.setTextColor(getResources().getColor(R.color.incorrect));}
                        attendanceAdapter = new AttendanceAdapter(view.getContext(),attendanceList,bsinfo.Branch,studentID,ssemester.getSelectedItem().toString());
                        rvsubjects.setAdapter(attendanceAdapter);
                        tvsempercentage.setText(String.valueOf(sempercentage+"%"));
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
