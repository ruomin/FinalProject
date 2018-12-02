package com.example.ruoruo.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "MovieDataBase";
    public static int VERSION_NUM = 2;
    public static final String TABLE_NAME = "MovieTable";

    public static final String KEY_MOVIE_ID = "id";
    public static final String KEY_MOVIE_TITLE = "title";
    public static final String KEY_MOVIE_YEAR = "year";
    public static final String KEY_MOVIE_RATING = "rating";
    public static final String KEY_MOVIE_RUNTIME = "runtime";
    public static final String KEY_MOVIE_MAIN_ACTORS = "mainActors";
    public static final String KEY_MOVIE_PLOT = "plot";
    public static final String KEY_MOVIE_URL = "url";

    public static final String SQL_MOVIE_CREATE ="CREATE TABLE "
            + TABLE_NAME
            + "( "
            + KEY_MOVIE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MOVIE_TITLE
            + " TEXT, "
            + KEY_MOVIE_YEAR
            + " INTEGER, "
            + KEY_MOVIE_RATING
            + " TEXT, "
            + KEY_MOVIE_RUNTIME
            + " INTEGER, "
            + KEY_MOVIE_MAIN_ACTORS
            + " TEXT, "
            + KEY_MOVIE_PLOT
            + " TEXT, "
            + KEY_MOVIE_URL
            + " TEXT "
            + ");";

    public MovieDatabaseHelper( Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_MOVIE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public  void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
