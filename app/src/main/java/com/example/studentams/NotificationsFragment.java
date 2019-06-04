package com.example.studentams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import static com.example.studentams.CurrentSem.getCurrSemester;

public class NotificationsFragment extends Fragment {
    RecyclerView nrecyclerview;
    List<String> nlist;
    List<String> studlist;
    DatabaseReference attendanceDB;
    DatabaseReference studentDB;
    NotificationsAdapter nadapter;
    String studentID,sbranch,sbatch,studentname;
    int itcount=0,cscount=0,mechcount=0,civilcount=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications,null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nrecyclerview = getView().findViewById(R.id.rvnotifications);
        nlist = new ArrayList<>();
        studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");
        attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance");

        studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");

        //CAN BE DONE ANOTHER WAY
        //START WITH ATTENDANCEDB THAT WAY ITLL BE ORDERED BY BRANCH THEN WITHIN BRANCH IT WILL BE ORDERED BY BATCH
        //WITHING ATTENDANCEDB VALUEEVENTLISTENER USE THE STUDENTID AS A FOREIGNKEY TO GET THE STUDENT INFORMATION IF REQUIRED


        //Getting Student Information
        studentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nlist = new ArrayList<>();
                studlist = new ArrayList<>();
                nrecyclerview.setHasFixedSize(true);
                nrecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
                //Traversing the list of students
                for(DataSnapshot dsstudents : dataSnapshot.getChildren()) {
                    studentID = dsstudents.getKey();
                    studentname = dsstudents.child("FName").getValue().toString() + " " + dsstudents.child("LName").getValue().toString();
                    sbranch = dsstudents.child("Branch").getValue().toString();
                    sbatch = dsstudents.child("Batch").getValue().toString();

                    //Checking attendance of student
                    attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(sbranch).child(studentID).child(getCurrSemester(sbatch));
                    attendanceDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(Float.parseFloat(dataSnapshot.child("SemPercentage").getValue().toString()) < 75) {
                                nlist.add(studentname + " (" + studentID + ")" + ", from " + sbranch + " (" + sbatch + ")" + " has low attendance.");
                            }
                            nadapter = new NotificationsAdapter(view.getContext(),nlist);
                            nrecyclerview.setAdapter(nadapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
//                Toast.makeText(getContext(),String.valueOf(studlist.size()),Toast.LENGTH_SHORT).show();
//                if(itcount > 0) {nlist.add("IT branch has " + itcount + " students with low attendance.");}
//                else if(cscount > 0) {nlist.add("CS branch has " + cscount + " students with low attendance.");}
//                else if(mechcount > 0) {nlist.add("IT branch has " + mechcount + " students with low attendance.");}
//                else if(civilcount > 0) {nlist.add("IT branch has " + civilcount + " students with low attendance.");}
//                nadapter = new NotificationsAdapter(view.getContext(),nlist);
//                nrecyclerview.setAdapter(nadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

