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

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    Context mctx;
    List<Attendance> attendanceList;
    String studentID;
    String branch;
    String semester;
//    DatabaseReference studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");
//    DatabaseReference attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance");

    public AttendanceAdapter(Context mctx, List<Attendance> attendanceList,String branch, String studentID, String semester) {
        this.mctx = mctx;
        this.attendanceList = attendanceList;
        this.branch = branch;
        this.studentID = studentID;
        this.semester = semester;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.list_subjects, parent, false);
        AttendanceViewHolder attendancevh = new AttendanceViewHolder(view);
        return attendancevh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);
        String str="";
        str="Subject: " + attendance.SubjectName;
        holder.tvsubjectName.setText(str);
        str="Lectures Attended: " + attendance.studentAttendance;
        holder.tvstudentAttendance.setText(str);
        str="Total Attendance: " + attendance.totalAttendanceTaken;
        holder.tvtotalattendance.setText(str);
        str=String.valueOf(attendance.percentage);
        holder.tvpercentage.setText(str+"%");

        //For opening the PopUp Activity used to update the attendance
        holder.llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx,PopUpAttendanceActivity.class);
                intent.putExtra("StudentBranch",branch);
                intent.putExtra("StudentID",studentID);
                intent.putExtra("Semester",semester);
                intent.putExtra("SubjectName",holder.tvsubjectName.getText().toString().substring(9));
                v.getContext().startActivity(intent);
            }
        });
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
