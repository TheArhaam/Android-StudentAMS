package com.example.studentams;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StudentsFragment extends Fragment {
    Button addstudent, removestudent, managestudents;
    ScrollView sv;
    TextView students;
    DatabaseReference studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_students,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addstudent = getView().findViewById(R.id.button11);
        removestudent = getView().findViewById(R.id.button12);
        managestudents = getView().findViewById(R.id.button13);
        sv = getView().findViewById(R.id.StudentScrollView);
        students = getView().findViewById(R.id.textView22);

        //Displaying students
        studentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students.setText("");
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    StudentInfo sinfo = new StudentInfo();
                    sinfo.FName=ds.child("FName").getValue().toString();
                    sinfo.LName=ds.child("LName").getValue().toString();
                    sinfo.age=Integer.parseInt(ds.child("age").getValue().toString());
                    sinfo.Batch=ds.child("Batch").getValue().toString();
                    sinfo.Branch=ds.child("Branch").getValue().toString();
                    sinfo.StudentID=ds.child("StudentID").getValue().toString();

                    students.append(sinfo.StudentID+"\n");
                    students.append("Name: " + sinfo.FName + " " + sinfo.LName + "\n");
                    students.append("Age: "+String.valueOf(sinfo.age)+"\n");
                    students.append("Batch: "+sinfo.Batch+"\n");
                    students.append("Branch: "+sinfo.Branch+"\n");
                    students.append("===================="+"\n");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Adding Students
        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddStudent.class);
                startActivity(intent);

            }
        });
    }
}
