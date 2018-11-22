package com.example.ruoruo.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "MessagesDataBase";
    public static int VERSION_NUM = 1;

    public MovieDatabaseHelper( Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public  void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
