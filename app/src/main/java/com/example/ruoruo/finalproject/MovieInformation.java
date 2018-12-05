package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.ruoruo.finalproject.MovieDatabaseHelper.KEY_MOVIE_TITLE;

public class MovieInformation extends Activity {

    protected static final String ACTIVITY_NAME = "MovieInformation";

    /**
     * declare all item
     */
    private TextView movieInfoTitle;
    private TextView movieInfoYear;
    private TextView movieInfoRating;
    private TextView movieInfoRuntime;
    private TextView movieInfoMainActors;
    private TextView movieInfoPlot;
    private TextView movieInfoURL;
    private ImageView imageInfoMovie;
    private Button backToMovie;

    /**
     * declare all information for download
     */
    private String movieTitle;
    private String movieYear;
    private String movieRating;
    private String movieRuntime;
    private String movieMainActors;
    private String moviePlot;
    private String movieURL;
    private Bitmap moviePoster;
    private String movieInfo;

    SQLiteDatabase db;
    Cursor cursor;
    MovieDatabaseHelper movieDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        /**
         * for find all item on the layout
         */
        movieInfoTitle = findViewById(R.id.MovieInfoTitle);
        movieInfoYear = findViewById(R.id.MovieInfoYear);
        movieInfoRating = findViewById(R.id.MovieInfoRating);
        movieInfoRuntime = findViewById(R.id.MovieInfoRuntime);
        movieInfoMainActors = findViewById(R.id.MovieInfoMainActors);
        movieInfoPlot = findViewById(R.id.MovieInfoPlot);
        movieInfoURL = findViewById(R.id.MovieInfoURL);
        imageInfoMovie = findViewById(R.id.imageMoveInfo);
        backToMovie = findViewById(R.id.backMovieButton);
        /**
         * go back to main page when click the back button
         */
        backToMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieInformation.this, MovieActivity.class);
                startActivity(intent);
            }
        });

        movieInfo = getIntent().getStringExtra("movieInfo");
        /**
         * get all information from database
         */
        movieDatabaseHelper = new MovieDatabaseHelper(this);
        db = movieDatabaseHelper.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + movieDatabaseHelper.TABLE_NAME + " WHERE " + KEY_MOVIE_TITLE + "=?", new String[]{movieInfo});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            movieTitle = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_TITLE));
            movieYear = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_YEAR));
            movieRating = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_RATING));
            movieRuntime = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_RUNTIME));
            movieMainActors = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_MAIN_ACTORS));
            moviePlot = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_PLOT));
            movieURL = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_URL));
            FileInputStream file = null;
            try {
                file = openFileInput(movieTitle+".png");
                moviePoster = BitmapFactory.decodeStream(file);
                imageInfoMovie.setImageBitmap(moviePoster);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        /**
         * show all message on view
         */
        movieInfoTitle.setText("Movie: " + movieTitle);
        movieInfoYear.setText("Year: " + movieYear);
        movieInfoRating.setText("Rating: " + movieRating);
        movieInfoRuntime.setText("Runtime: " + movieRuntime);
        movieInfoMainActors.setText("Main actors: " + movieMainActors);
        movieInfoPlot.setText("Plot: " + moviePlot);
        movieInfoURL.setText("Plot: " + movieURL);
        //imageInfoMovie.setImageBitmap(moviePoster);
    }
}
