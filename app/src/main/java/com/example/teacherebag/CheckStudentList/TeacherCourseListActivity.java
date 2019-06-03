package com.example.teacherebag.CheckStudentList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.teacherebag.MainActivity;
import com.example.teacherebag.R;
import com.example.teacherebag.Classes.Course;
import com.example.teacherebag.Classes.TeacherCourse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class TeacherCourseListActivity extends AppCompatActivity {

    private static final String TAG = "TeacherCourseList";
    private List<TeacherCourse> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course);

        Intent intent = getIntent();
        String teacherId = intent.getStringExtra("TeacherId");
        Log.d(TAG, teacherId);

        getCourseList(teacherId);

    }

    private void getCourseList(final String teacherId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("https://wenkui0229.top:18080/teachers/"+teacherId+"/courses")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");
                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    List<Course> courseList = gson.fromJson(resData, new TypeToken<List<Course>>(){}.getType());
                    for(Course course :courseList){
                        Log.d(TAG, course.getCourseId());
                        Log.d(TAG, course.getName());
                        Log.d(TAG, course.getId().toString());
                    }

                    initTeacherCourseList(courseList);

                    showResponse();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.teacher_course_recycler_view);
                recyclerView.addItemDecoration(new DividerItemDecoration(TeacherCourseListActivity.this, DividerItemDecoration.VERTICAL));
                LinearLayoutManager layoutManager = new LinearLayoutManager(TeacherCourseListActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                TeacherCourseAdapter adapter = new TeacherCourseAdapter(itemList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void initTeacherCourseList(List<Course> courseList){
        for(Course course :courseList){
            itemList.add(new TeacherCourse(course.getName(), course.getCourseId()));
        }
    }
}
