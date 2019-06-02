package com.example.teacherebag.NewTeacherWork;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.teacherebag.Classes.TeacherWork;
import com.example.teacherebag.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewTeacherWorkActivity extends AppCompatActivity {

    private static final String TAG = "NewTeacherWorkActivity";
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
        String courseId = intent.getStringExtra("CourseId");
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

        Button commitNewTW = (Button)findViewById(R.id.commit_new_tw);
        commitNewTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    policy = data.getStringExtra("Policy");
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
}
