package com.example.studentams;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceAdapterForStudents extends RecyclerView.Adapter<AttendanceAdapterForStudents.AttendanceSViewHolder> {

    Context mctx;
    List<Attendance> attendanceList;
    String studentID;
    String branch;
    String semester;

    public AttendanceAdapterForStudents(Context mctx, List<Attendance> attendanceList,String branch, String studentID, String semester) {
        this.mctx = mctx;
        this.attendanceList = attendanceList;
        this.branch = branch;
        this.studentID = studentID;
        this.semester = semester;
    }

    @NonNull
    @Override
    public AttendanceSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.list_subjects, parent, false);
        AttendanceAdapterForStudents.AttendanceSViewHolder attendancesvh = new AttendanceSViewHolder(view);
        return attendancesvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceAdapterForStudents.AttendanceSViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);
        String str="";
        str="Subject: " + attendance.SubjectName;
        holder.tvsubjectName.setText(str);
        str="Lectures Attended: " + attendance.studentAttendance;
        holder.tvstudentAttendance.setText(str);
        str="Total Attendance: " + attendance.totalAttendanceTaken;
        holder.tvtotalattendance.setText(str);
        str=String.valueOf(attendance.percentage);
        holder.tvpercentage.setText(str);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    class AttendanceSViewHolder extends RecyclerView.ViewHolder {
        TextView tvsubjectName,tvstudentAttendance,tvtotalattendance,tvpercentage;

        public AttendanceSViewHolder(View itemView){
            super(itemView);
            tvsubjectName = itemView.findViewById(R.id.textView30);
            tvstudentAttendance = itemView.findViewById(R.id.textView31);
            tvtotalattendance = itemView.findViewById(R.id.textView32);
            tvpercentage = itemView.findViewById(R.id.textView33);
        }

    }
}

