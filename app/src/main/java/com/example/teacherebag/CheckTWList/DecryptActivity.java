package com.example.teacherebag.CheckTWList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.example.teacherebag.Cpabe;
import com.example.teacherebag.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;

public class DecryptActivity extends AppCompatActivity {

    private static final String TAG = "DecryptActivity";

    static String dir = "/data/user/0/com.example.teacherebag/files";
    static String pubfile = dir + "/pubkey.pub";
    static String mskfile = dir + "/masterkey.msk";
    static String prvfile = dir + "/prvkey.prv";

    static String inputfile = dir + "/input";
    static String encfile = dir + "/input.cpabe";
    static String decfile = dir + "/output";
    static String attr_str = "character:teacher";

    private TextView showTeacherWorkTitle;
    private TextView getShowTeacherWorkContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        showTeacherWorkTitle = (TextView)findViewById(R.id.show_teacher_work_title);
        getShowTeacherWorkContent = (TextView)findViewById(R.id.show_teacher_work_content);

        Intent intent = getIntent();
        final String encContent = intent.getStringExtra("EncContent");
        final String title = intent.getStringExtra("Title");

        refreshText(showTeacherWorkTitle, title);

        FileOutputStream outEncContent = null;
        BufferedOutputStream encContentWriter = null;
        try {
//                        "/data/user/0/com.example.cpabe/files/pubkey.pub"
            outEncContent = openFileOutput("input.cpabe", Context.MODE_PRIVATE);
            encContentWriter = new BufferedOutputStream(outEncContent);
            encContentWriter.write(Base64.decode(encContent, Base64.DEFAULT));
            encContentWriter.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (encContentWriter != null){
                    encContentWriter.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        Cpabe testCpabe = new Cpabe();
        try {
            testCpabe.keygen(pubfile, prvfile, mskfile, attr_str);
            testCpabe.dec(pubfile, prvfile, encfile, decfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder decContentBuilder = new StringBuilder();
        try {
            in = openFileInput("output");
            reader = new BufferedReader(new InputStreamReader(in));
            String  line = "";
            while ((line = reader.readLine()) != null){
                decContentBuilder.append(line);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Log.d(TAG, decContentBuilder.toString());
        refreshText(getShowTeacherWorkContent, decContentBuilder.toString());
    }

    private void refreshText(final TextView textView, final String stringText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(stringText);
            }
        });
    }
}
