package com.example.teacherebag.CheckStudentWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.teacherebag.Classes.StudentWorkRemark;
import com.example.teacherebag.MainActivity;
import com.example.teacherebag.NewTeacherWork.NewTeacherWorkActivity;
import com.example.teacherebag.R;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditStudentWorkActivity extends AppCompatActivity {

    private static final String TAG = "EditStudentWorkActivity";
    private long studentWorkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_work);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        String content = intent.getStringExtra("Content");
        studentWorkId = intent.getLongExtra("StudentWorkId",0);

        TextView showStudentWorkTitle = (TextView)findViewById(R.id.show_student_work_title);
        TextView showStudentWorkContent = (TextView)findViewById(R.id.show_student_work_content);

        refreshText(showStudentWorkTitle, title);
        refreshText(showStudentWorkContent, content);

        final NumberPicker bigNumberPicker = (NumberPicker)findViewById(R.id.big_number_picker);
        bigNumberPicker.setMaxValue(100);
        bigNumberPicker.setMinValue(0);
        final NumberPicker smallNumberPicker = (NumberPicker)findViewById(R.id.small_number_picker);
        smallNumberPicker.setMaxValue(9);
        smallNumberPicker.setMinValue(0);

        final EditText studentWorkRemark = (EditText)findViewById(R.id.student_work_remark);

        Button commitStudentWork = (Button)findViewById(R.id.commit_student_work);
        commitStudentWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float score = (float)bigNumberPicker.getValue() + ((float)smallNumberPicker.getValue())/10;
                String remark = studentWorkRemark.getText().toString();

                StudentWorkRemark studentWorkRemark = new StudentWorkRemark(score, remark);

                Gson gson = new Gson();
                String jsonString = gson.toJson(studentWorkRemark);

                commitSW(jsonString);

                finish();
            }
        });
    }
    private void refreshText(final TextView textView, final String stringText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(stringText);
            }
        });
    }

    private void commitSW(final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url("http://192.168.1.105:8080/students/work/score/" + studentWorkId)
                            .put(requestBody)
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
