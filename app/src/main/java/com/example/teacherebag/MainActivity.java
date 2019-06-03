package com.example.teacherebag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.teacherebag.CheckStudentList.TeacherCourseListActivity;
import com.example.teacherebag.CheckStudentWork.TeacherWorkInStudentWorkActivity;
import com.example.teacherebag.CheckTWList.CheckTWActivity;
import com.example.teacherebag.NewTeacherWork.CourseListTeacherWorkActivity;
import com.example.teacherebag.User.LoginActivity;
import com.example.teacherebag.Classes.UserDataDetail;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String userData;

    public UserDataDetail userDataDetail;

    public static String teacherId = "";

    public static CookieJar cookieJar = new CookieJar() {
        //Cookie缓存区
        private final Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();

        @Override
        public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {
            // TODO Auto-generated method stub
            //移除相同的url的Cookie
            String host = arg0.host();
            List<Cookie> cookiesList = cookiesMap.get(host);
            if (cookiesList != null) {
                cookiesMap.remove(host);
            }
            //再重新添加
            cookiesMap.put(host, arg1);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl arg0) {
            // TODO Auto-generated method stub
            List<Cookie> cookiesList = cookiesMap.get(arg0.host());
            return cookiesList != null ? cookiesList : new ArrayList<Cookie>();
        }
    };

    public static OkHttpClient client  = new OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .cookieJar(cookieJar)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton loginButtonMainpage = (ImageButton)findViewById(R.id.login_button_mainpage);
        loginButtonMainpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        ImageButton teacherCourseButton = (ImageButton) findViewById(R.id.show_student_list);
        teacherCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeacherCourseListActivity.class);
                intent.putExtra("TeacherId", userDataDetail.getTeacher().getTeacherId());
                startActivity(intent);
            }
        });

        ImageButton newTeacherWork = (ImageButton)findViewById(R.id.new_teacher_work);
        newTeacherWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CourseListTeacherWorkActivity.class);
                intent.putExtra("TeacherId", userDataDetail.getTeacher().getTeacherId());
                startActivity(intent);
            }
        });

        ImageButton checkTeacherWork = (ImageButton)findViewById(R.id.check_teacher_work);
        checkTeacherWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckTWActivity.class);
                startActivity(intent);
            }
        });

        ImageButton checkStudentWork = (ImageButton)findViewById(R.id.check_student_work);
        checkStudentWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeacherWorkInStudentWorkActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == 200){
                    userData = data.getStringExtra("UserData");
                    Log.d(TAG, userData);
                    Gson gson = new Gson();
                    userDataDetail = gson.fromJson(userData, UserDataDetail.class);
                    Log.d(TAG, userDataDetail.getTeacher().getTeacherId());
                    teacherId = userDataDetail.getTeacher().getTeacherId();
                }
                default:
        }
    }

    public UserDataDetail getUserDataDetail(){
        return userDataDetail;
    }
}
