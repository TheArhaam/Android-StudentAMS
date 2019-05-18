package com.example.studentams;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FirebaseDataList extends ArrayAdapter<StaffInfo>{
    private Activity context;
    private List<StaffInfo> stafflist;

    public FirebaseDataList(Activity context, List<StaffInfo> stafflist) {
        super(context,R.layout.firebase_staffdata_list,stafflist);
        this.context=context;
        this.stafflist=stafflist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.firebase_staffdata_list,null,true);


    }
}
