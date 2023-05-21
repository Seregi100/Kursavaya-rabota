package com.mirea.kt.android2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBt = findViewById(R.id.btLogin);
        EditText edLogin = findViewById(R.id.edLogin);
        EditText edPassword = findViewById(R.id.edPassword);
        EditText edGroup = findViewById(R.id.edGroup);
        TextView tvText = findViewById(R.id.tvText);
        loginBt.setOnClickListener(v -> {
            String login = edLogin.getText().toString();
            String password = edPassword.getText().toString();
            String group = edGroup.getText().toString();
            HashMap<String, String> data = new HashMap<>();
            data.put("lgn", login);
            data.put("pwd", password);
            data.put("g", group);
            HTTPRunnable httpRunnable = new HTTPRunnable("https://android-for-students.ru/coursework/login.php", data);
            boolean flag = false;
            Thread th = new Thread(httpRunnable);
            th.start();
            try{
                th.join();
            }catch(InterruptedException ex){
                Log.e("uiop", "InterruptedException");
            }finally{
                try{
                    String text;
                    JSONObject jsonObject = new JSONObject(httpRunnable.getRespBody());
                    text = "Title: " + jsonObject.getString("title") + "\n" + "Task: " + jsonObject.getString("task") + "\n" + "Data: " + jsonObject.getJSONArray("data") + "\n" + "Variant: " + jsonObject.getInt("variant") + "\n" + "Result code: " + jsonObject.getInt("result_code");
                    flag = true;
                }catch(org.json.JSONException e) {
                    Toast toast = Toast.makeText(this, "Неправильные логин, пароль или группа", Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("uiop", "Incorrect data");
                }
            }
            if (flag==true){
                Intent intent = new Intent(this, AppActivity.class);
                startActivity(intent);
            }
        });
    }
}