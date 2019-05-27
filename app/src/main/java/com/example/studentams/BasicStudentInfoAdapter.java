package com.example.studentams;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BasicStudentInfoAdapter extends RecyclerView.Adapter<BasicStudentInfoAdapter.StudentsViewHolder> {

    Context mctx;
    List<BasicStudentInfo> bsinfo;
    DatabaseReference studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");

    public BasicStudentInfoAdapter(Context mctx, List<BasicStudentInfo> bsinfo) {
        this.mctx = mctx;
        this.bsinfo = bsinfo;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.list_students, parent, false);
        StudentsViewHolder studentsvh = new StudentsViewHolder(view);
        return studentsvh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder holder, int position) {
        final BasicStudentInfo basicsinfo = bsinfo.get(position);
        String str;
        holder.tvStudentID.setText(basicsinfo.getStudentID());
        str = "Name: " + basicsinfo.getFName()+ " " + basicsinfo.getLName();
        holder.tvName.setText(str);
        str = "Age: " + String.valueOf(basicsinfo.getAge());
        holder.tvAge.setText(str);
        str = "Branch: " + basicsinfo.getBranch();
        holder.tvBranch.setText(str);
        str = "Batch: " + basicsinfo.getBatch();
        holder.tvBatch.setText(str);

        holder.llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mctx,basicsinfo.getFName()+" was selected",Toast.LENGTH_SHORT).show();
                showPopup(v,basicsinfo);
            }
        });
    }

    private void showPopup(View v, final BasicStudentInfo bsinfo) {
        final View v1 = v;
        PopupMenu popup = new PopupMenu(mctx,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_studentmanagement, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.remove_Student) {
                    removeStudent(bsinfo);
                }
                else if(item.getItemId() == R.id.manage_attendance) {
                    Intent intent = new Intent(mctx,ManageAttendance.class);
                    intent.putExtra("studentID",bsinfo.getStudentID());
                    mctx.startActivity(intent);
                }
                return true;
            }
        });
    }

    private void removeStudent(BasicStudentInfo bsinfo) {
        studentDB.child(bsinfo.StudentID).removeValue();
        Toast.makeText(mctx,"Student Removed.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return bsinfo.size();
    }

    class StudentsViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentID,tvName,tvAge,tvBranch,tvBatch;
        LinearLayout llayout;

        public StudentsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStudentID = itemView.findViewById(R.id.textView22);
            tvName = itemView.findViewById(R.id.textView24);
            tvAge = itemView.findViewById(R.id.textView25);
            tvBatch = itemView.findViewById(R.id.textView26);
            tvBranch = itemView.findViewById(R.id.textView27);
            llayout = itemView.findViewById(R.id.studentitem);
        }
    }

}
