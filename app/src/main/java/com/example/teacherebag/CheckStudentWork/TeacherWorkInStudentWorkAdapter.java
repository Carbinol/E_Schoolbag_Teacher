package com.example.teacherebag.CheckStudentWork;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.teacherebag.Classes.TwInSwItem;
import com.example.teacherebag.R;

import java.util.List;

public class TeacherWorkInStudentWorkAdapter extends RecyclerView.Adapter<TeacherWorkInStudentWorkAdapter.ViewHolder> {
    private List<TwInSwItem> mTwInSwItem;

    static class ViewHolder extends RecyclerView.ViewHolder {
    View twInSwItemView;
    TextView twInSwItemTitle;
    TextView twInSwItemCourseid;

    public ViewHolder(View view){
        super(view);
        twInSwItemView = view;
        twInSwItemTitle = (TextView)view.findViewById(R.id.tw_in_sw_title);
        twInSwItemCourseid = (TextView)view.findViewById(R.id.tw_in_sw_courseid);
    }
}

    public TeacherWorkInStudentWorkAdapter(List<TwInSwItem> twInSwItemList){
        mTwInSwItem = twInSwItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tw_in_sw_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.twInSwItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TwInSwItem twInSwItem = mTwInSwItem.get(position);

                Intent intent = new Intent(parent.getContext(), StudentWorkActivity.class);
                intent.putExtra("Title", twInSwItem.getTitle());
                intent.putExtra("TeacherWorkId", twInSwItem.getTeacherWorkId());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        TwInSwItem twInSwItem = mTwInSwItem.get(position);
        holder.twInSwItemTitle.setText(twInSwItem.getTitle());
        holder.twInSwItemCourseid.setText(twInSwItem.getCourseName());
    }

    @Override
    public int getItemCount(){
        return mTwInSwItem.size();
    }
}
