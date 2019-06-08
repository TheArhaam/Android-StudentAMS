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
import static com.example.studentams.CurrentSem.getCurrSemesterShort;

public class NotificationsFragment extends Fragment {
    RecyclerView nrecyclerview;
    List<String> nlist;
    DatabaseReference attendanceDB,attendanceDB2;
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

        //Getting Student Information
        //1st way
//        studentDB.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                nlist = new ArrayList<>();
//                nrecyclerview.setHasFixedSize(true);
//                nrecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
//                //Traversing the list of students
//                for(DataSnapshot dsstudents : dataSnapshot.getChildren()) {
//                    studentID = dsstudents.getKey();
//                    studentname = dsstudents.child("FName").getValue().toString() + " " + dsstudents.child("LName").getValue().toString();
//                    sbranch = dsstudents.child("Branch").getValue().toString();
//                    sbatch = dsstudents.child("Batch").getValue().toString();
//                    Toast.makeText(view.getContext(),studentname,Toast.LENGTH_SHORT).show();
//
//                    //Checking attendance of student
//                    attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(sbranch).child(studentID).child(getCurrSemester(sbatch));
//                    attendanceDB.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(Float.parseFloat(dataSnapshot.child("SemPercentage").getValue().toString()) < 75) {
//
//                                nlist.add(studentname + " (" + studentID + ")" + ", from " + sbranch + " (" + sbatch + ")" + " has low attendance.");
//                            }
//                            nadapter = new NotificationsAdapter(view.getContext(),nlist);
//                            nrecyclerview.setAdapter(nadapter);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //2nd way
        attendanceDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nrecyclerview.setHasFixedSize(true);
                nrecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
               // nlist = new ArrayList<>();
                //For branches under attendance
                for(DataSnapshot dsbranches : dataSnapshot.getChildren()) {
                    sbranch = dsbranches.getKey();
                    //For StudentIDs under branches
                    for(DataSnapshot dsstudentids : dsbranches.getChildren()){
                        studentID = dsstudentids.getKey();
                        //For Current Semester under StudentID
                        for(DataSnapshot dssemester : dsstudentids.getChildren()) {
                            if(dssemester.getKey().equals(getCurrSemesterShort(studentID.substring(0,4)))) {
                               if(Float.parseFloat(dssemester.child("SemPercentage").getValue().toString()) < 75 ) {
                                   switch(sbranch) {
                                       case "Information Technology":
                                           itcount++;
                                           break;
                                       case "Computer Science":
                                           cscount++;
                                           break;
                                       case "Mechanical":
                                           mechcount++;
                                           break;
                                       case "Civil":
                                           civilcount++;
                                           break;
                                   }
                               }

                            }
                        }

                    }
                }
                if(itcount > 0) {nlist.add(itcount + " student(s) from Information Technology have low attendance.");}
                if(cscount > 0){nlist.add(cscount + " student(s) from Computer Science have low attendance.");}
                if(mechcount > 0){nlist.add(mechcount + " student(s) from Mechanical have low attendance.");}
                if(civilcount > 0){nlist.add(civilcount + " student(s) from Civil have low attendance.");}
                nadapter = new NotificationsAdapter(view.getContext(),nlist);
                nrecyclerview.setAdapter(nadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

