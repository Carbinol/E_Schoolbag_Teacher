package com.example.teacherebag.CheckTWList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.teacherebag.Classes.TWIndex;
import com.example.teacherebag.Classes.TeacherWork;
import com.example.teacherebag.MainActivity;
import com.example.teacherebag.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class CheckTWActivity extends AppCompatActivity {

    private static final String TAG = "CheckTWActivity";
    private List<TWIndex> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_tw);

        getTWList();
    }

    private void getTWList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://192.168.1.105:8080/teachers/work")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");
                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    List<TeacherWork> twList = gson.fromJson(resData, new TypeToken<List<TeacherWork>>(){}.getType());


                    initTeacherCourseList(twList);
                    for(TWIndex course :itemList){
                        Log.d(TAG, course.getCourseName());
                        Log.d(TAG, course.getTitle());
                    }


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
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.tw_recycler_view);
                recyclerView.addItemDecoration(new DividerItemDecoration(CheckTWActivity.this, DividerItemDecoration.VERTICAL));
                LinearLayoutManager layoutManager = new LinearLayoutManager(CheckTWActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                CheckTWAdapter adapter = new CheckTWAdapter(itemList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void initTeacherCourseList(List<TeacherWork> teacherWorkList){
        for(TeacherWork teacherWork :teacherWorkList){
            itemList.add(new TWIndex(teacherWork.getTitle(), teacherWork.getCourseId(), teacherWork.getContent()));
        }
    }
}
