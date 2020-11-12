package com.example.shiyan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edit_name;
    private EditText edit_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_submit=(Button) findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
                edit_name=(EditText)findViewById(R.id.e_name);
                edit_password=(EditText)findViewById(R.id.e_password);
                String e_name=edit_name.getText().toString();
                String e_password=edit_password.getText().toString();
                String name=pref.getString("name","Bai");
                String password=pref.getString("password","1234");
                if(name.equals(e_name) && password.equals(e_password)){
                    Intent intent=new Intent(MainActivity.this,DiaryTitleActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,"账号密码错误！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}