package com.example.teacherebag.CheckStudentList;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teacherebag.R;
import com.example.teacherebag.Classes.TeacherCourse;

import java.util.List;

public class TeacherCourseAdapter extends RecyclerView.Adapter<TeacherCourseAdapter.ViewHolder> {
    private List<TeacherCourse> mTeacherCourse;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View teacherCourseView;
        TextView teacherCourseTitle;
        TextView teacherCourseContent;

        public ViewHolder(View view){
            super(view);
            teacherCourseView = view;
            teacherCourseTitle = (TextView)view.findViewById(R.id.teacher_course_title);
            teacherCourseContent = (TextView)view.findViewById(R.id.teacher_course_content);
        }
    }

    public TeacherCourseAdapter(List<TeacherCourse> teacherCourseList){
        mTeacherCourse = teacherCourseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_course, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.teacherCourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TeacherCourse teacherCourse = mTeacherCourse.get(position);

                Intent intent = new Intent(parent.getContext(), StudentListActivity.class);
                intent.putExtra("CourseId", teacherCourse.getContent());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        TeacherCourse teacherCourse = mTeacherCourse.get(position);
        holder.teacherCourseTitle.setText(teacherCourse.getTitle());
        holder.teacherCourseContent.setText(teacherCourse.getContent());
    }

    @Override
    public int getItemCount(){
        return mTeacherCourse.size();
    }
}
