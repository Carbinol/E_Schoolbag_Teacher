package com.example.teacherebag.CheckTWList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teacherebag.Classes.TWIndex;
import com.example.teacherebag.R;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CheckTWAdapter extends RecyclerView.Adapter<CheckTWAdapter.ViewHolder> {
    private List<TWIndex> mTWIndex;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View twIndexView;
        TextView twIndexTitle;
        TextView twIndexCourseid;

        public ViewHolder(View view){
            super(view);
            twIndexView = view;
            twIndexTitle = (TextView)view.findViewById(R.id.teacher_work_title);
            twIndexCourseid = (TextView)view.findViewById(R.id.teacher_work_courseid);
        }
    }

    public CheckTWAdapter(List<TWIndex> twIndexList){
        mTWIndex = twIndexList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tw_index, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.twIndexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TWIndex twIndex = mTWIndex.get(position);

                Intent intent = new Intent(parent.getContext(), DecryptActivity.class);
                intent.putExtra("Title", twIndex.getTitle());
                intent.putExtra("EncContent", twIndex.getEncContent());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        TWIndex twIndex = mTWIndex.get(position);
        holder.twIndexTitle.setText(twIndex.getTitle());
        holder.twIndexCourseid.setText(twIndex.getCourseName());
    }

    @Override
    public int getItemCount(){
        return mTWIndex.size();
    }
}
