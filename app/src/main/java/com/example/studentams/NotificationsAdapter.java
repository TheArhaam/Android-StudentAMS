package com.example.studentams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsAdapter extends RecyclerView.Adapter <NotificationsAdapter.NAViewHolder>{

    Context mctx;
    List<String> nlist;

    public NotificationsAdapter(Context mctx, List<String> nlist) {
        this.mctx = mctx;
        this.nlist = nlist;
    }

    @NonNull
    @Override
    public NAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.list_notifications, parent, false);
        NAViewHolder nvh = new NAViewHolder(view);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NAViewHolder holder, int position) {
        String notification = nlist.get(position);
        holder.tvnotification.setText(notification);
    }

    @Override
    public int getItemCount() {
        return nlist.size();
    }

    class NAViewHolder extends RecyclerView.ViewHolder {
        TextView tvnotification;
        public NAViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnotification = itemView.findViewById(R.id.textView41);
        }
    }
}
