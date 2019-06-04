package com.example.studentams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsFragment extends Fragment {
    RecyclerView nrecyclerview;
    List<String> nlist;
    DatabaseReference attendanceDB;
    DatabaseReference studentDB;
//    String
    int itlowcount, cslowcount, mechlowcount,civillowcount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nrecyclerview = getView().findViewById(R.id.rvnotifications);
        nlist = new ArrayList<>();
        studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");
        attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance");





    }
}
