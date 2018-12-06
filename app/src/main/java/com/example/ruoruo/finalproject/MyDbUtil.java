package com.example.ruoruo.finalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbUtil extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String DB_NAME = "news_reader.db";
    private final static String TABLE_NAME = "t_new_reader";

    private SQLiteDatabase db;

    //SQLiteOpenHelper get the constructor
    public MyDbUtil(Context context, String name, CursorFactory factory, int version) {
        //use super to get super Constructor
        super(context, name, factory, version);
    }


    public MyDbUtil(Context context) {
        this(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String CREATE_TBL = "create table t_new_reader(guid  primary key, title text, link text, pubDate text, author text, category text, description text)";

        db.execSQL(CREATE_TBL);
    }

    //callback, and pass the DBHelperto Version and the previous Version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //insert method
    public long addNews(NewsItem news) {
        long result = -1;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", news.title);
        values.put("link", news.link);
        values.put("guid", news.guid);
        values.put("pubDate", news.pubDate);
        values.put("author", news.author);
        values.put("category", news.category);
        values.put("description", news.description);
        //insert to the database
        result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    //query method
    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        //get Cursor
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        return c;

    }

    //close the database
    public void close() {
        if (db != null) {
            db.close();
        }
    }

}
