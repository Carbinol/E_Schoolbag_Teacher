package com.example.teacherebag.NewTeacherWork;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teacherebag.R;

public class ChooseLabelActivity extends AppCompatActivity {

    private static final String TAG = "ChooseLabelActivity";
    private String policy = "";
    private int i = 0;
    private String labelText1 = "";
    private String labelText2 = "";
    private String labelText3 = "";
    private TextView labelDetail1;
    private TextView labelDetail2;
    private TextView labelDetail3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_label);

        Button labelButton1 = (Button)findViewById(R.id.label_button1);
        Button labelButton2 = (Button)findViewById(R.id.label_button2);
        Button labelButton3 = (Button)findViewById(R.id.label_button3);

        labelDetail1 = (TextView)findViewById(R.id.label_detail1);
        labelDetail2 = (TextView)findViewById(R.id.label_detail2);
        labelDetail3 = (TextView)findViewById(R.id.label_detail3);

        labelButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLabelActivity.this, EditLabelActivity.class);
                intent.putExtra("LabelNumber", 1);
                startActivityForResult(intent, 11);
            }
        });
        labelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLabelActivity.this, EditLabelActivity.class);
                intent.putExtra("LabelNumber", 2);
                startActivityForResult(intent, 11);
            }
        });
        labelButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLabelActivity.this, EditLabelActivity.class);
                intent.putExtra("LabelNumber", 3);
                startActivityForResult(intent, 11);
            }
        });

        Button confirmPolicy = (Button)findViewById(R.id.confirm_policy);
        confirmPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultCode = 0;
                Intent intent = new Intent();
                if(i != 0){
                    if(i != 1) {
                        policy += " 1of" + i + " ";
                    }
                    resultCode = 1;
                }
                Log.d(TAG, policy);
                intent.putExtra("Policy", policy);
                intent.putExtra("LabelText1", labelText1);
                intent.putExtra("LabelText2", labelText2);
                intent.putExtra("LabelText3", labelText3);
                setResult(resultCode, intent);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        String label = "";
        switch (resultCode){
            case 11:
                    label = data.getStringExtra("LabelString");
                    policy += label;
                    labelText1 += data.getStringExtra("LabelText");
                    Log.d(TAG, label);
                    Log.d(TAG, labelText1);
                    i++;
                    refreshLabel(labelDetail1, labelText1);
                    break;
            case 12:
                label = data.getStringExtra("LabelString");
                policy += label;
                labelText2 += data.getStringExtra("LabelText");
                Log.d(TAG, label);
                Log.d(TAG, labelText2);
                i++;
                refreshLabel(labelDetail2, labelText2);
                break;
            case 13:
                    label = data.getStringExtra("LabelString");
                    policy += label;
                    labelText3 += data.getStringExtra("LabelText");
                    Log.d(TAG, label);
                    Log.d(TAG, labelText3);
                    i++;
                    refreshLabel(labelDetail3, labelText3);
                    break;
            default:
        }
    }

    private void refreshLabel(final TextView textView, final String label){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(label);
            }
        });
    }
}
