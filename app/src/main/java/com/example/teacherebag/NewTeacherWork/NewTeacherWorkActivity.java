package com.example.teacherebag.NewTeacherWork;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teacherebag.Classes.TeacherWork;
import com.example.teacherebag.Classes.UserKey;
import com.example.teacherebag.Cpabe;
import com.example.teacherebag.MainActivity;
import com.example.teacherebag.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewTeacherWorkActivity extends AppCompatActivity {

    private static final String TAG = "NewTeacherWorkActivity";
    private EditText twContent;
    private EditText twTitle;
    static String dir = "/data/user/0/com.example.teacherebag/files";
    static String encfile = dir + "/input.cpabe";
    static String inputfile = dir + "/input";
    static String pubfile = dir + "/pubkey.pub";
    String policy;
    String deadline;
    TextView courseIdTextView;
    TextView studentLabel1;
    TextView studentLabel2;
    TextView studentLabel3;
    TextView deadlineDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teacher_work);

        courseIdTextView = (TextView)findViewById(R.id.new_teacher_work_courseid);
        studentLabel1 = (TextView)findViewById(R.id.student_label1);
        studentLabel2 = (TextView)findViewById(R.id.student_label2);
        studentLabel3 = (TextView)findViewById(R.id.student_label3);

        deadlineDate = (TextView)findViewById(R.id.deadline_date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        deadline = sdf.format(calendar.getTime());
        refreshText(deadlineDate, deadline);

        Intent intent = getIntent();
        final String courseId = intent.getStringExtra("CourseId");
        Log.d(TAG, courseId);

        refreshText(courseIdTextView, courseId);

        Button chooseLabel = (Button)findViewById(R.id.choose_label);
        chooseLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewTeacherWorkActivity.this, ChooseLabelActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button pickDeadline = (Button)findViewById(R.id.pick_deadline);
        pickDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(NewTeacherWorkActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Log.d(TAG, "onDateSet: year: " + year + ", month: " + month + ", dayOfMonth: " + dayOfMonth);

                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                deadline = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                                Log.d(TAG, deadline);
                                refreshText(deadlineDate, deadline);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                showDialog(dialog);
            }
        });

        twContent = (EditText)findViewById(R.id.twcontent);
        twTitle = (EditText)findViewById(R.id.twtitle);
        Button commitNewTW = (Button)findViewById(R.id.commit_new_tw);
        commitNewTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getKey();

                String content = twContent.getText().toString();
                FileOutputStream outContent = null;
                BufferedWriter Writer = null;
                try {
                    outContent = openFileOutput("input", Context.MODE_PRIVATE);
                    Writer = new BufferedWriter(new OutputStreamWriter(outContent));
                    Writer.write(content);
                }
                catch (IOException e){
                    e.printStackTrace();
                }finally {
                    try {
                        if (Writer != null){
                            Writer.close();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

                Cpabe cpabe = new Cpabe();
                try{
                    cpabe.enc(pubfile, policy, inputfile, encfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                InputStream is = null;
                String encContent = null;
                try {

                     is = openFileInput("input.cpabe");
                    int size = is.available();
                    byte[] cc = new byte[size];
                    is.read(cc);
                    encContent = Base64.encodeToString(cc,Base64.DEFAULT);
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                String title = twTitle.getText().toString();
                TeacherWork teacherWork = new TeacherWork(MainActivity.teacherId, policy, courseId, title, encContent, deadline);

                Gson gson = new Gson();
                String jsonString = gson.toJson(teacherWork);
                Log.d(TAG,encContent);

                commitTW(jsonString);
                finish();
            }
        });
    }

    private  void showDialog(final Dialog dialog){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10:
                if(resultCode == 1){
                    policy = (data.getStringExtra("Policy") + " character:teacher 1of2").trim();
                    Log.d(TAG, policy);
                    while (policy.contains("  ")){
                        policy = policy.replaceAll(" {2}", " ");
                    }
                    Log.d(TAG, policy);
                    refreshText(studentLabel1, data.getStringExtra("LabelText1"));
                    if(!data.getStringExtra("LabelText2").equals("")){
                        refreshText(studentLabel2, "或" + data.getStringExtra("LabelText2"));
                    }
                    else {
                        refreshText(studentLabel2, "");
                    }
                    if(!data.getStringExtra("LabelText3").equals("")){
                        refreshText(studentLabel3, "或" + data.getStringExtra("LabelText3"));
                    }
                    else {
                        refreshText(studentLabel3, "");
                    }
                }
            default:
        }
    }

    private void getKey(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("https://wenkui0229.top:18080/key")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");

                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    UserKey userKey = gson.fromJson(resData, new TypeToken<UserKey>(){}.getType());
                    Log.d(TAG, userKey.getMasterKey());
                    Log.d(TAG, userKey.getPubKey());
                    FileOutputStream outPubkey = null;
                    FileOutputStream outMasterkey = null;
                    BufferedOutputStream pubkeyWriter = null;
                    BufferedOutputStream masterkeyWriter = null;
                    try {
//                        "/data/user/0/com.example.cpabe/files/pubkey.pub"
                        outPubkey = openFileOutput("pubkey.pub", Context.MODE_PRIVATE);
                        outMasterkey = openFileOutput("masterkey.msk", Context.MODE_PRIVATE);
                        pubkeyWriter = new BufferedOutputStream(outPubkey);
                        pubkeyWriter.write(Base64.decode(userKey.getPubKey(), Base64.DEFAULT));
                        pubkeyWriter.flush();
                        masterkeyWriter = new BufferedOutputStream(outMasterkey);
                        masterkeyWriter.write(Base64.decode(userKey.getMasterKey(), Base64.DEFAULT));
                        masterkeyWriter.flush();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        try {
                            if (pubkeyWriter != null){
                                pubkeyWriter.close();
                            }
                            if (masterkeyWriter != null){
                                masterkeyWriter.close();
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void commitTW(final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url("https://wenkui0229.top:18080/teachers/work")
                            .post(requestBody)
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
