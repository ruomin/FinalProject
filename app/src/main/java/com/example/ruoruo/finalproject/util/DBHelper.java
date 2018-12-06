package com.example.ruoruo.finalproject.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String DB_NAME = "food.db";
    private final static String TABLE_NAME = "food";
    private final static String CREATE_TBL = "create table food(foodId  primary key, url text, label text, nutrients text, category text, categoryLabel text, tag text)";
    private SQLiteDatabase db;

    //SQLiteOpenHelper constructor
    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        //use super
        super(context, name, factory, version);
    }

    //the constructor of the database, there are three input
    public DBHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    //database constructor with one input, and the name, version
    public DBHelper(Context context) {
        this(context, DB_NAME, null, VERSION);
    }

    // callback method to create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TBL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //insert method
    public boolean insert(ContentValues values) {
        boolean flag = false;
        //get the SQLiteDatabase instance
        SQLiteDatabase db = getWritableDatabase();
        //insert to the database
        flag = db.insert(TABLE_NAME, null, values) > 0;
        db.close();
        return flag;
    }

    //query method
    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        //get the Cursor
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        return c;

    }

    //use id to delete the data
    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "foodId=?", new String[]{id});
    }


    //update the database information
    public void update(ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, values, whereClause, whereArgs);
    }


    public void close() {
        if (db != null) {
            db.close();
        }
    }

}
