package com.example.studentams;

import android.content.Context;
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

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    Context mctx;
    List<Attendance> attendanceList;
//    DatabaseReference studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");
//    DatabaseReference attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance");

    public AttendanceAdapter(Context mctx, List<Attendance> attendanceList) {
        this.mctx = mctx;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.list_subjects, parent, false);
        AttendanceViewHolder attendancevh = new AttendanceViewHolder(view);
        return attendancevh;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
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

    class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvsubjectName,tvstudentAttendance,tvtotalattendance,tvpercentage;
        LinearLayout llayout;

        public AttendanceViewHolder(View itemView){
            super(itemView);
            tvsubjectName = itemView.findViewById(R.id.textView30);
            tvstudentAttendance = itemView.findViewById(R.id.textView31);
            tvtotalattendance = itemView.findViewById(R.id.textView32);
            tvpercentage = itemView.findViewById(R.id.textView33);
            llayout = itemView.findViewById(R.id.subjectitem);
        }

    }
}
