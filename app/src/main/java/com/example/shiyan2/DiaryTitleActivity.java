package com.example.shiyan2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




 class DiaryAdapter extends ArrayAdapter<Diary> {
    private int resourceId;
    public DiaryAdapter(@NonNull Context context, int resource, List<Diary> objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Diary diary=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView item_title=(TextView) view.findViewById(R.id.item_title);
        TextView item_time=(TextView) view.findViewById(R.id.item_time);
        item_title.setText(diary.getTitle());
        item_time.setText(diary.getTime());
        return view;
    }
}



public class DiaryTitleActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private List<Diary> diaryList=new ArrayList<>();
    private Diary diary;

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper =new MyDatabaseHelper(this,"DiaryBook.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        diaryList.clear();
        Cursor cursor=db.query("diary",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String title=cursor.getString(cursor.getColumnIndex("diary_title"));
                long   time1=cursor.getLong(cursor.getColumnIndex("diary_time"));
                SimpleDateFormat time2=new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");
                String time=time2.format(new Date(time1));
                diary=new Diary("标题:"+title,"时间:"+time);
                diaryList.add(diary);

            }while (cursor.moveToNext());
        }
        DiaryAdapter adapter=new DiaryAdapter(DiaryTitleActivity.this,R.layout.title_item,diaryList);
        ListView listView=(ListView) findViewById(R.id.list_view);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_title);

        final ListView listView=(ListView) findViewById(R.id.list_view);

        Button button_back=(Button) findViewById(R.id.button_back);
        Button button_new=(Button) findViewById(R.id.button_new);
        button_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiaryTitleActivity.this,ContentActivity.class);
                startActivity(intent);

            }
        });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Diary diary=diaryList.get(position);
                    Intent intent=new Intent(DiaryTitleActivity.this,ContentActivity2.class);
                    intent.putExtra("diary_Title",position);
                    intent.putExtra("diary_Title2",diary.getTitle().substring(3));
                    Log.d("DiaryTitleActivity",diary.getTitle());
                    startActivity(intent);
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {

                    new AlertDialog.Builder(DiaryTitleActivity.this)
                            .setTitle("")
                            .setMessage("是否删除单词")
                            .setNegativeButton("取消",null)
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Diary diary=diaryList.get(position);
                                    dbHelper =new MyDatabaseHelper(DiaryTitleActivity.this,"DiaryBook.db",null,1);
                                    SQLiteDatabase db=dbHelper.getWritableDatabase();
                                    String[] strings = {diary.getTitle().substring(3)};
                                    int flag = db.delete("diary", "diary_title=?",strings );
                                    Cursor cursor=db.query("diary",null,null,null,null,null,null);
                                    diaryList.clear();
                                    if(cursor.moveToFirst()){
                                        do{
                                            String title=cursor.getString(cursor.getColumnIndex("diary_title"));
                                            long   time1=cursor.getLong(cursor.getColumnIndex("diary_time"));
                                            SimpleDateFormat time2=new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");
                                            String time=time2.format(new Date(time1));
                                            diary=new Diary("标题:"+title,"时间:"+time);
                                            diaryList.add(diary);

                                        }while (cursor.moveToNext());
                                    }
                                    DiaryAdapter adapter=new DiaryAdapter(DiaryTitleActivity.this,R.layout.title_item,diaryList);
                                    ListView listView=(ListView) findViewById(R.id.list_view);

                                    listView.setAdapter(adapter);
                                }
                            }).show();
                    return false;
                }
            });





    }


}