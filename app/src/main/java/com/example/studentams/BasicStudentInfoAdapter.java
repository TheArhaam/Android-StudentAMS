package com.example.studentams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BasicStudentInfoAdapter extends RecyclerView.Adapter<BasicStudentInfoAdapter.StudentsViewHolder> {

    Context mctx;
    List<BasicStudentInfo> bsinfo;

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
            }
        });
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
