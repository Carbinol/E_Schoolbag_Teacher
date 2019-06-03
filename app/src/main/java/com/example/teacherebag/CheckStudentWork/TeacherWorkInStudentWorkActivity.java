package com.example.teacherebag.CheckStudentWork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.teacherebag.Classes.TeacherWork;
import com.example.teacherebag.Classes.TwInSwItem;
import com.example.teacherebag.R;
import com.example.teacherebag.MainActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class TeacherWorkInStudentWorkActivity extends AppCompatActivity {

    private static final String TAG = "TWInStudentWork";

    private List<TwInSwItem> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_work_in_student_work);

        getTwInSwList();
    }

    private void getTwInSwList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("https://wenkui0229.top:18080/teachers/work")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");
                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    List<TeacherWork> teacherWorkList = gson.fromJson(resData, new TypeToken<List<TeacherWork>>(){}.getType());

                    initTeacherCourseList(teacherWorkList);
                    showResponse();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initTeacherCourseList(List<TeacherWork> teacherWorkList){
        for(TeacherWork teacherWork :teacherWorkList){
            itemList.add(new TwInSwItem(teacherWork.getTeacherWorkId(), teacherWork.getTitle(), teacherWork.getCourseId(), teacherWork.getContent()));
        }
    }

    private void showResponse(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.tw_in_sw_recycler_view);
                recyclerView.addItemDecoration(new DividerItemDecoration(TeacherWorkInStudentWorkActivity.this, DividerItemDecoration.VERTICAL));
                LinearLayoutManager layoutManager = new LinearLayoutManager(TeacherWorkInStudentWorkActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                TeacherWorkInStudentWorkAdapter adapter = new TeacherWorkInStudentWorkAdapter(itemList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
