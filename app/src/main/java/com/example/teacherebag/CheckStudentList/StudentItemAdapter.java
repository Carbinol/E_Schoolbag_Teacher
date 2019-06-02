package com.example.teacherebag.CheckStudentList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teacherebag.R;
import com.example.teacherebag.Classes.StudentItem;

import java.util.List;

public class StudentItemAdapter extends RecyclerView.Adapter<StudentItemAdapter.ViewHolder> {
    private List<StudentItem> mStudentItem;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View studentItemView;
        TextView studentItemName;
        TextView studentItemNumber;

        public ViewHolder(View view){
            super(view);
            studentItemView = view;
            studentItemName = (TextView)view.findViewById(R.id.student_name);
            studentItemNumber = (TextView)view.findViewById(R.id.student_number);
        }
    }

    public StudentItemAdapter(List<StudentItem> studentItemList){
        mStudentItem = studentItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        StudentItem studentItem = mStudentItem.get(position);
        holder.studentItemName.setText(studentItem.getName());
        holder.studentItemNumber.setText(studentItem.getNumber());
    }

    @Override
    public int getItemCount(){
        return mStudentItem.size();
    }
}
