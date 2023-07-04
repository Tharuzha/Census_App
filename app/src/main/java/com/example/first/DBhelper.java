package com.example.first;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;


public class DBhelper extends SQLiteOpenHelper {

    //    RadioButton male,female;


    public DBhelper(@Nullable Context context) {
        super(context,"Userdata.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(name TEXT primary key,age NUMBER,gender TEXT,img LONG)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop table if exists UserDetails");
        onCreate(DB);
    }

    public boolean insertuserdata(String name, String age, String gender, String img){
        SQLiteDatabase DB =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("gender",gender);
        contentValues.put("img",img);


        long result = DB.insert("UserDetails",null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select*from UserDetails",null);
        return cursor;
    }

    public void cleardata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("DELETE from UserDetails");
    }


}

