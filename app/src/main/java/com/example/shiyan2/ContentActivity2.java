package com.example.shiyan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentActivity2 extends AppCompatActivity {

    private EditText diary_content_title;
    private EditText diary_content_content;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_content2);

        final Intent intent=getIntent();

        dbHelper =new MyDatabaseHelper(this,"DiaryBook.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        Button button_baocun=(Button)findViewById(R.id.button_baocun);
        diary_content_title=(EditText)findViewById(R.id.diary_content_title);
        diary_content_content=(EditText)findViewById(R.id.diary_content_content);

        Cursor cursor=db.query("diary",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int i=0;
            Log.d("ContentActivity2","dayinle");
            do{
                String title=cursor.getString(cursor.getColumnIndex("diary_title"));
                String content=cursor.getString(cursor.getColumnIndex("diary_content"));
                Log.d("ContentActivity2",title);
                if(i==intent.getIntExtra("diary_Title",-1)){
                    diary_content_title.setText(title);
                    Log.d("ContentActivit2",title);
                    diary_content_content.setText(content);
                    Log.d("ContentActivity2",cursor.getString(cursor.getColumnIndex("diary_content")));
                    break;
                }
                i++;

            }while (cursor.moveToNext());
        }


        button_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("diary_title",diary_content_title.getText().toString());
                values.put("diary_content",diary_content_content.getText().toString());
                String string1=diary_content_title.getText().toString();
                String string2=diary_content_content.getText().toString();
                String strings=intent.getStringExtra("diary_Title2");
                int flag=db.update("diary",values,"diary_title=?",new String[]{intent.getStringExtra("diary_Title2")});
                Toast.makeText(ContentActivity2.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}