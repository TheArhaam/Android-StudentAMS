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
    DatabaseReference attendanceDB;
    DatabaseReference studentDB;
    NotificationsAdapter nadapter;
    String studentID,sbranch,sbatch;

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
        studentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nlist = new ArrayList<>();
                nrecyclerview.setHasFixedSize(true);
                nrecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));
                //Traversing the list of students
                for(DataSnapshot dsstudents : dataSnapshot.getChildren()) {
                    studentID = dsstudents.getKey();
                    sbranch = dsstudents.child("Branch").getValue().toString();
                    sbatch = dsstudents.child("Batch").getValue().toString();

                    //Checking attendance of student
                    attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(sbranch).child(studentID).child(getCurrSemester(sbatch));
                    attendanceDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Getting the attendance of the current semester and checking if its below 75%

                            //NOT ABLE TO KEEP COUNT FOR SOME REASON
                            //CHANGES ARE IMPLEMENTED ONLY WITHIN INNER CLASS AND RESET OUTSIDE THE INNER CLASS



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if( > 0) {nlist.add("IT branch has " +  + " students with low attendance.");}
                else if( > 0) {nlist.add("CS branch has " +  + " students with low attendance.");}
                else if( > 0) {nlist.add("IT branch has " +  + " students with low attendance.");}
                else if( > 0) {nlist.add("IT branch has " +  + " students with low attendance.");}
                nadapter = new NotificationsAdapter(view.getContext(),nlist);
                nrecyclerview.setAdapter(nadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

