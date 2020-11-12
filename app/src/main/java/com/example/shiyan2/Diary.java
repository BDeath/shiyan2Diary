package com.example.shiyan2;

public class Diary {
    private String title;
    private String time;
    public Diary(String name,String time){
        this.title=name;
        this.time=time;
    }
    public String getTitle(){
        return title;
    }
    public String getTime(){
        return time;
    }
}
