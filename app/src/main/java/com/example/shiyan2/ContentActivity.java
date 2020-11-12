package com.example.shiyan2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.work.Data;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ContentActivity extends AppCompatActivity {

    private EditText diary_content_title;
    private EditText diary_content_content;
    private MyDatabaseHelper dbHelper;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_content);
        dbHelper =new MyDatabaseHelper(this,"DiaryBook.db",null,1);
        Button button_baocun=(Button)findViewById(R.id.button_baocun);
        Button takePhoto = (Button) findViewById(R.id.take_photo);//拍照按钮
        Button choosePhoto = (Button) findViewById(R.id.choose_from_album);//获取手机中的图片按钮
        diary_content_title=(EditText)findViewById(R.id.diary_content_title);
        diary_content_content=(EditText)findViewById(R.id.diary_content_content);
        button_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
               // Log.d("ContentActivity",diary_content_title.getText().toString());
                //Log.d("ContentActivity",diary_content_content.getText().toString());
                //Log.d("ContentActivity","Bai");
                //Log.d("ContentActivity",diary_content_title.getText().toString());
                values.put("diary_title",diary_content_title.getText().toString());
                values.put("diary_content",diary_content_content.getText().toString());
                java.util.Date data=new java.util.Date();
                long time =data.getTime();
                values.put("diary_time",time);
                values.put("diary_author","Bai");
                db.insert("diary",null,values);
                Toast.makeText(ContentActivity.this,"保存成功",Toast.LENGTH_SHORT).show();

            }
        });




    }
}