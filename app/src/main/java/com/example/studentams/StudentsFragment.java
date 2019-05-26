package com.example.studentams;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentsFragment extends Fragment {
    Button addstudent, removestudent, managestudents;
    Spinner sbranch,sbatch;
//    ScrollView sv;
//    TextView students;
//    ListView lvstudents;
//    ArrayList <String> students = new ArrayList<>();
    DatabaseReference studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");
    RecyclerView recyclerView;
    BasicStudentInfoAdapter adapter;
    List<BasicStudentInfo> studentlist;

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
        sbranch = getView().findViewById(R.id.spinner5);
        sbatch = getView().findViewById(R.id.spinner6);

        sbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        displayStudents(dataSnapshot);
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
        sbatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        displayStudents(dataSnapshot);
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


//        sv = getView().findViewById(R.id.StudentScrollView);
//        students = getView().findViewById(R.id.textView22);

//        lvstudents = getView().findViewById(R.id.ListViewStudents);
//        final ArrayAdapter <String> aastudents = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,students);
//        lvstudents.setAdapter(aastudents);


        //Displaying students
        studentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayStudents(dataSnapshot);
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

    public void displayStudents(DataSnapshot dataSnapshot) {
        recyclerView = getView().findViewById(R.id.rcview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentlist = new ArrayList<>();
        sbranch = getView().findViewById(R.id.spinner5);
        sbatch = getView().findViewById(R.id.spinner6);
//        students.setText("");
        for(DataSnapshot ds:dataSnapshot.getChildren()) {
            if(ds.child("Branch").getValue().toString().equals(sbranch.getSelectedItem().toString()) &&
                    ds.child("Batch").getValue().toString().equals(sbatch.getSelectedItem().toString())) {
                StudentInfo sinfo = new StudentInfo();
                sinfo.FName=ds.child("FName").getValue().toString();
                sinfo.LName=ds.child("LName").getValue().toString();
                sinfo.age=Integer.parseInt(ds.child("age").getValue().toString());
                sinfo.Batch=ds.child("Batch").getValue().toString();
                sinfo.Branch=ds.child("Branch").getValue().toString();
                sinfo.StudentID=ds.child("StudentID").getValue().toString();

                studentlist.add(new BasicStudentInfo(
                        sinfo.FName,
                        sinfo.LName,
                        sinfo.age,
                        sinfo.Branch,
                        sinfo.Batch,
                        sinfo.StudentID
                ));

//                    students.append(sinfo.StudentID+"\n");
//                    students.append("Name: " + sinfo.FName + " " + sinfo.LName + "\n");
//                    students.append("Age: "+String.valueOf(sinfo.age)+"\n");
//                    students.append("Batch: "+sinfo.Batch+"\n");
//                    students.append("Branch: "+sinfo.Branch+"\n");
//                    students.append("===================="+"\n");

//                    students.add(sinfo.StudentID+"\n" +
//                            "Name: " + sinfo.FName + " " + sinfo.LName + "\n" +
//                            "Age: " + String.valueOf(sinfo.age) + "\n" +
//                            "Batch: " + sinfo.Batch+"\n" +
//                            "Branch: " + sinfo.Branch + "\n");
//                    aastudents.notifyDataSetChanged();
            }
        }
        adapter = new BasicStudentInfoAdapter(getContext(),studentlist);
        recyclerView.setAdapter(adapter);
    }
}
