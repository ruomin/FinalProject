package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ruoruo.finalproject.MovieDatabaseHelper.TABLE_NAME;


public class MovieDetail extends Activity {
    /**
     * instance MovieActivity to get user input
     */
    MovieActivity m = new MovieActivity();
    protected static final String ACTIVITY_NAME = "MovieDetail";
    /**
     * movie API URL
     */
    protected String urlString = "http://www.omdbapi.com/?t=" + m.movieInput + "&r=xml&apikey=88fe3b26";
    /**
     * declare movie ProgressBar
     */
    private ProgressBar movieProgressBar;
    /**
     * declare all item
     */
    private TextView movieDetailTitle;
    private TextView movieDetailYear;
    private TextView movieDetailRating;
    private TextView movieDetailRuntime;
    private TextView movieDetailMainActors;
    private TextView movieDetailPlot;
    private TextView movieDetailURL;
    private ImageView imageMovie;
    private Button saveMovie;
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
    //private String iconName;
    private Bitmap moviePoster;
    /**
     * declare database
     */
    private SQLiteDatabase db;
    private Cursor cursor;
    private MovieDatabaseHelper movieDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        /**
         * for find all item on the layout
         */
        movieDetailTitle = findViewById(R.id.MovieDetailTitle);
        movieDetailYear = findViewById(R.id.MovieDetailYear);
        movieDetailRating = findViewById(R.id.MovieDetailRating);
        movieDetailRuntime = findViewById(R.id.MovieDetailRuntime);
        movieDetailMainActors = findViewById(R.id.MovieDetailMainActors);
        movieDetailPlot = findViewById(R.id.MovieDetailPlot);
        movieDetailURL = findViewById(R.id.MovieDetailURL);
        imageMovie = findViewById(R.id.imageMoveDetail);
        saveMovie = findViewById(R.id.saveMovieButton);
        movieProgressBar = findViewById(R.id.movieProgressBar);
        /**
         * run movie query
         */
        MovieQuery movieQuery = new MovieQuery();
        movieQuery.execute(urlString);

        movieDatabaseHelper = new MovieDatabaseHelper(this);
        db = movieDatabaseHelper.getWritableDatabase();

        /**
         * write into database when click save button Meanwhile go back to movie main page
         */
        saveMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MovieDatabaseHelper.KEY_MOVIE_TITLE, movieTitle);
                values.put(MovieDatabaseHelper.KEY_MOVIE_YEAR, movieYear);
                values.put(MovieDatabaseHelper.KEY_MOVIE_RATING, movieRating);
                values.put(MovieDatabaseHelper.KEY_MOVIE_RUNTIME, movieRuntime);
                values.put(MovieDatabaseHelper.KEY_MOVIE_MAIN_ACTORS, movieMainActors);
                values.put(MovieDatabaseHelper.KEY_MOVIE_PLOT, moviePlot);
                values.put(MovieDatabaseHelper.KEY_MOVIE_URL, movieURL);
                db.insert(TABLE_NAME, "null", values);

                Intent intent = new Intent(MovieDetail.this, MovieActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * load information form internet
     */
    public class MovieQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
            movieProgressBar.setVisibility(ProgressBar.VISIBLE);

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                InputStream stream = conn.getInputStream();

                XmlPullParser xpp = Xml.newPullParser();
                xpp.setInput(stream, null);
                /**
                 * loop to find movie information
                 */
                while (xpp.next() != XmlPullParser.END_DOCUMENT) {
                    Log.i(ACTIVITY_NAME, "In while");

                    if (xpp.getName().equals("movie")) {
                        movieTitle = xpp.getAttributeValue(null, "title");
                        publishProgress(10);
                        movieYear = xpp.getAttributeValue(null, "year");
                        publishProgress(20);
                        movieRating = xpp.getAttributeValue(null, "rated");
                        publishProgress(30);
                        movieRuntime = xpp.getAttributeValue(null, "runtime");
                        publishProgress(40);
                        movieMainActors = xpp.getAttributeValue(null, "actors");
                        publishProgress(50);
                        moviePlot = xpp.getAttributeValue(null, "plot");
                        publishProgress(60);
                        movieURL = xpp.getAttributeValue(null, "poster");
                        publishProgress(70);
                        //String iconFile = movieTitle + ".png";
//                        if (fileExistance(iconFile)) {
//                            FileInputStream inputStream = null;
//                            try {
//                                inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            moviePoster = BitmapFactory.decodeStream(inputStream);
//                            Log.i(ACTIVITY_NAME, "Image already exists");
//                        } else {
                        //Bitmap image  = HTTPUtils.getImage(ImageURL);

                        URL iconUrl = new URL(movieURL + movieTitle + ".png");
                        moviePoster = getImage(iconUrl);
                        FileOutputStream outputStream = openFileOutput(movieTitle + ".png", Context.MODE_PRIVATE);
                        moviePoster.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                        Log.i(ACTIVITY_NAME, "Add a new image");
                        break;
                    }
                }
            } catch (MalformedURLException urlEX) {
                urlEX.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            publishProgress(100);
            Log.i(ACTIVITY_NAME, "In doInBackground");
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            movieProgressBar.setVisibility(View.VISIBLE);
            movieProgressBar.setProgress(values[0]);
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
        }

        /**
         * show all message in view
         *
         * @param string
         */
        @Override
        protected void onPostExecute(String string) {
            movieDetailTitle.setText(movieDetailTitle.getText() + ": " + movieTitle);
            movieDetailYear.setText(movieDetailYear.getText() + ": " + movieYear);
            movieDetailRating.setText(movieDetailRating.getText() + ": " + movieRating);
            movieDetailRuntime.setText(movieDetailRuntime.getText() + ": " + movieRuntime);
            movieDetailMainActors.setText(movieDetailMainActors.getText() + ": " + movieMainActors);
            movieDetailPlot.setText(movieDetailPlot.getText() + ": " + moviePlot);
            movieDetailURL.setText(movieDetailURL.getText() + ": " + movieURL);

            imageMovie.setImageBitmap(moviePoster);
            movieProgressBar.setVisibility(View.INVISIBLE);

        }
    }

}
