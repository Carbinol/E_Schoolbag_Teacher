package com.example.teacherebag.NewTeacherWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.teacherebag.R;

public class EditLabelActivity extends AppCompatActivity {

    private static final String TAG = "EditLabelActivity";
    private String labelString = "";
    private String labelText = "";
    private int labelNumber = 0;
    private String cGrade = "";
    private String javaGrade = "";
    private String kotlinGrade = "";
    private String pythonGrade = "";
    private String uwpGrade = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_label);

        labelString = "";
        labelText = "";
        labelNumber = 0;

        Intent intent = getIntent();
        labelNumber = intent.getIntExtra("LabelNumber", 0);

        final Button labelCommit = (Button)findViewById(R.id.label_commit);

        final CheckBox checkboxC = (CheckBox)findViewById(R.id.checkbox_c);
        final CheckBox checkboxJava = (CheckBox)findViewById(R.id.checkbox_java);
        final CheckBox checkboxKotlin = (CheckBox)findViewById(R.id.checkbox_kotlin);
        final CheckBox checkboxPython = (CheckBox)findViewById(R.id.checkbox_python);
        final CheckBox checkboxUwp = (CheckBox)findViewById(R.id.checkbox_uwp);

        Spinner spinnerC = (Spinner)findViewById(R.id.spinner_c);
        Spinner spinnerJava = (Spinner)findViewById(R.id.spinner_java);
        Spinner spinnerKotlin = (Spinner)findViewById(R.id.spinner_kotlin);
        Spinner spinnerPython = (Spinner)findViewById(R.id.spinner_python);
        Spinner spinnerUwp = (Spinner)findViewById(R.id.spinner_uwp);

        spinnerC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] grade = getResources().getStringArray(R.array.grades);
                cGrade = " c:" + grade[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerJava.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] grade = getResources().getStringArray(R.array.grades);
                javaGrade = " java:" + grade[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerKotlin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] grade = getResources().getStringArray(R.array.grades);
                kotlinGrade = " kotlin:" + grade[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerPython.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] grade = getResources().getStringArray(R.array.grades);
                pythonGrade = " python:" + grade[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerUwp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] grade = getResources().getStringArray(R.array.grades);
                uwpGrade = " uwp:" + grade[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        labelCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                int resultCode = 0;
                if(checkboxC.isChecked()){
                    labelString += cGrade;
                    labelText += cGrade;
                    i++;
                }
                if(checkboxJava.isChecked()){
                    labelString += javaGrade;
                    if(labelText.equals("")){
                        labelText += javaGrade;
                    }
                    else {
                        labelText += "且" + javaGrade;
                    }
                    i++;
                }
                if(checkboxKotlin.isChecked()){
                    labelString += kotlinGrade;
                    if(labelText.equals("")){
                        labelText += kotlinGrade;
                    }
                    else {
                        labelText += "且" + kotlinGrade;
                    }
                    i++;
                }
                if(checkboxPython.isChecked()){
                    labelString += pythonGrade;
                    if(labelText.equals("")){
                        labelText += pythonGrade;
                    }
                    else {
                        labelText += "且" + pythonGrade;
                    }
                    i++;
                }
                if(checkboxUwp.isChecked()){
                    labelString += uwpGrade;
                    if(labelText.equals("")){
                        labelText += uwpGrade;
                    }
                    else {
                        labelText += "且" + uwpGrade;
                    }
                    i++;
                }
                if(i != 0){
                    resultCode = 10 + labelNumber;
                    if(i != 1){
                    labelString += " " + i + "of" + i +" ";
                    }
                }
                Log.d(TAG, labelString);
                Log.d(TAG, labelText);
                Intent intent = new Intent();
                intent.putExtra("LabelString", labelString);
                intent.putExtra("LabelText", labelText);
                setResult(resultCode, intent);
                finish();
            }
        });
    }
}
