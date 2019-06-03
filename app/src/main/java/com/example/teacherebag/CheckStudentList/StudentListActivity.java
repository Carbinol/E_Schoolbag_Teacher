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
import com.example.teacherebag.Classes.Student;
import com.example.teacherebag.Classes.StudentItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class StudentListActivity extends AppCompatActivity {

    private static final String TAG = "StudentListActivity";
    private List<StudentItem> studentItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Intent intent = getIntent();
        String courseId = intent.getStringExtra("CourseId");
        Log.d(TAG, courseId);

        getCourseList(courseId);

    }

    private void getCourseList(final String courseId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("https://wenkui0229.top:18080/courses/"+courseId+"/students")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");
                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    List<Student> studentList = gson.fromJson(resData, new TypeToken<List<Student>>(){}.getType());
                    for(Student student :studentList){
                        Log.d(TAG, student.getName());
                        Log.d(TAG, student.getStudentId());
                    }

                    initStudentItemList(studentList);

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
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.student_list_recycler_view);
                recyclerView.addItemDecoration(new DividerItemDecoration(StudentListActivity.this, DividerItemDecoration.VERTICAL));
                LinearLayoutManager layoutManager = new LinearLayoutManager(StudentListActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                StudentItemAdapter adapter = new StudentItemAdapter(studentItemList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void initStudentItemList(List<Student> studentList){
        for(Student student :studentList){
            studentItemList.add(new StudentItem(student.getName(), student.getStudentId()));
        }
    }
}
