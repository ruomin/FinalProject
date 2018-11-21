package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
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


public class MovieDetail extends Activity {

    protected static final String ACTIVITY_NAME = "MovieDetail";

    protected static final String urlString = "http://www.omdbapi.com/?i=tt3896198&apikey=88fe3b26";

    //MovieActivity myMovie;

    ProgressBar progressBar;

    TextView movieDetailTitle;
    TextView movieDetailYear;
    TextView movieDetailRating;
    TextView movieDetailRuntime;
    TextView movieDetailMainActors;
    TextView movieDetailPlot;
    TextView movieDetailURL;
    ImageView imageMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

         movieDetailTitle = findViewById(R.id.MovieDetailTitle);
         movieDetailYear = findViewById(R.id.MovieDetailYear);
         movieDetailRating = findViewById(R.id.MovieDetailRating);
         movieDetailRuntime = findViewById(R.id.MovieDetailRuntime);
         movieDetailMainActors = findViewById(R.id.MovieDetailMainActors);
         movieDetailPlot = findViewById(R.id.MovieDetailPlot);
         movieDetailURL = findViewById(R.id.MovieDetailURL);
         imageMovie = findViewById(R.id.imageMoveDetail);

        MovieQuery movieQuery = new MovieQuery();
        movieQuery.execute(urlString);

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

    public boolean fileExistance(String fname){
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

    public class MovieQuery extends AsyncTask<String, Integer, String> {

        int movieTitle;
        String movieYear;
        String movieRating;
        String movieRuntime;
        String movieMainActors;
        String moviePlot;
        String movieURL;
        String iconName;
        Bitmap icon;

        int hasRead = 0;

        StringBuilder stringBuilder = new StringBuilder();
        @Override
        protected String doInBackground(String... arg) {
//            progressBar.setVisibility(ProgressBar.VISIBLE);

            try{

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
                xpp.setInput( stream , null);
                movieTitle = xpp.getAttributeCount();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()
                        , "utf-8"));
                String line = null;
                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                    hasRead++;

//
//
//                while (xpp.next() != XmlPullParser.END_DOCUMENT) {
//                    Log.i(ACTIVITY_NAME, "In while");
//                    if (xpp.getEventType() != XmlPullParser.START_TAG) {
//                        continue;
//                    }
//
//                    if (xpp.getName().equals("Title")) {
//                        movieTitle = xpp.getAttributeValue(null, "Title");
//                        publishProgress(25);
//                    }if (xpp.getName().equals("Year")) {
//                        movieYear = xpp.getAttributeValue(null, "Year");
//                        publishProgress(50);
//                    }if (xpp.getName().equals("Rated")) {
//                        movieRating = xpp.getAttributeValue(null, "Rated");
//                        publishProgress(75);
//                    }if (xpp.getName().equals("Runtime")) {
//                        movieRuntime = xpp.getAttributeValue(null, "Runtime");
//                    }if (xpp.getName().equals("Actors")) {
//                        movieMainActors = xpp.getAttributeValue(null, "Actors");
//                    }if (xpp.getName().equals("Plot")) {
//                        moviePlot = xpp.getAttributeValue(null, "Plot");
//                    }if (xpp.getName().equals("Website")) {
//                        movieURL = xpp.getAttributeValue(null,"Website");
//                    }
//
//                    if (xpp.getName().equals("Poster")) {
//                        iconName = xpp.getAttributeValue(null, "Poster");
//                        String iconFile = iconName + ".png";
//                        if (fileExistance(iconFile)) {
//                            FileInputStream inputStream = null;
//                            try {
//                                inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            icon = BitmapFactory.decodeStream(inputStream);
//                            Log.i(ACTIVITY_NAME, "Image already exists");
//                        } else {
//                            //Bitmap image  = HTTPUtils.getImage(ImageURL));
//
//                            URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
//                            icon = getImage(iconUrl);
//                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
//                            icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
//                            outputStream.flush();
//                            outputStream.close();
//                            Log.i(ACTIVITY_NAME, "Add a new image");
//                        }
//                    }
                }

            }catch(MalformedURLException urlEX){
                urlEX.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

//            publishProgress(100);
            Log.i(ACTIVITY_NAME, "In doInBackground");
            return null;
//

        }


        @Override
        protected void onProgressUpdate(Integer... values) {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");

        }

        @Override
        protected void onPostExecute(String string) {

            movieDetailTitle.setText(stringBuilder);
            //progressDialog.dismiss();

//            movieDetailTitle.setText("Movie Title"+movieDetailTitle.getText()+movieTitle);
//            movieDetailYear.setText("Movie Year"+movieDetailYear.getText()+movieYear);
//            movieDetailRating.setText("Movie Rating"+movieDetailRating.getText()+movieRating);
//            movieDetailRuntime.setText("Movie Runtime"+movieDetailRuntime.getText()+movieRuntime);
//            movieDetailMainActors.setText("Movie Main Actors"+movieDetailMainActors.getText()+movieMainActors);
//            movieDetailPlot.setText("Movie Plot"+movieDetailPlot.getText()+moviePlot);
//            movieDetailURL.setText("Movie URL"+movieDetailURL.getText()+movieURL);
//            movieDetailTitle.setText("Movie Title"+movieDetailTitle.getText()+movieTitle);
//
//            imageMovie.setImageBitmap(icon);
            //progressBar.setVisibility(View.INVISIBLE);

        }
    }

}
