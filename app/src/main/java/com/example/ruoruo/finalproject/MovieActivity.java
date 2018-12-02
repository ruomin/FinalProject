package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.ruoruo.finalproject.MovieDatabaseHelper.TABLE_NAME;

/**
 *
 */
public class MovieActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MovieActivity";

    /**
     *the button to start search movie form internet
     */
    private Button btnSearchMovie;

    private Button btn_fragment;

    /**
     * toorbar for movie application
     */
    public Toolbar toolbar;

    /**
     * when click search button, the result should be shown on the listView
     */
    private ListView listViewMovie;
    private ArrayList<String> movieTitleList = new ArrayList<>();
    /**
     * boolean frame Layout is exist or not
     */
    boolean ifFrameLayoutExist;
    
    private Cursor cursor;

    private EditText editTextSearchMovieTitle;

    private String inputMovieSearch;

    public static String movieInput;



    /**
     * for database part
     */
    private MovieDatabaseHelper movieDatabaseHelper;
    private SQLiteDatabase db;
    //private ArrayList<MovieResult> arrayList;
    MovieAdapter movieAdapter;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        /**
         * display wellcome message Toast on the screen when enter the application
         */
        //Toast.makeText(MovieActivity.this, "Well come to movie world!",Toast.LENGTH_SHORT).show();

        /**
         * boolean check if the FrameLayout exists on the screen
         */
        ifFrameLayoutExist = findViewById(R.id.frame) != null;

        //arrayList = new ArrayList<MovieResult>();
        //MovieAdapter movieAdapter = new MovieAdapter(movieTitleList,this);

        /**
         * add MovieToolbar to the page
         */
        toolbar = findViewById(R.id.movieToolbar);
        setSupportActionBar(toolbar);
        /**
         * find search movie button and set on click listener
         */
        btnSearchMovie = findViewById(R.id.buttonSearchMovie);
        btn_fragment = findViewById(R.id.recommendedMovie);
        movieDatabaseHelper = new MovieDatabaseHelper(this);
        db = movieDatabaseHelper.getWritableDatabase();

//        getAllMessageFromDb();
        cursor = db.query(true,movieDatabaseHelper.TABLE_NAME, null, null, null, null, null, null,null);

        Log.i(ACTIVITY_NAME, "Cursor's Column Count " + cursor.getColumnCount());

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(movieDatabaseHelper.KEY_MOVIE_TITLE)));
            movieTitleList.add(cursor.getString(cursor.getColumnIndex(movieDatabaseHelper.KEY_MOVIE_TITLE)));
            cursor.moveToNext();
        }
        listViewMovie = findViewById(R.id.listViewMovie);
        movieAdapter = new MovieAdapter(movieTitleList,this);
        listViewMovie.setAdapter (movieAdapter);
//        arrayList.add(messageResult);
//        movieAdapter.notifyDataSetChanged();

        /**
         *
         * for search movie and show it in movie detail page
         */
        btnSearchMovie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editTextSearchMovieTitle = findViewById(R.id.searchMovieTittle);
                movieInput = editTextSearchMovieTitle.getText().toString();
                if (movieInput.isEmpty()) {//if user input is empty, display a message Toast on the screen when enter the application
                    {Toast.makeText(MovieActivity.this,"Please input a movie title", Toast.LENGTH_LONG).show();}
                } else {
                    Intent intent = new Intent(MovieActivity.this, MovieDetail.class);
                    startActivity(intent);
                }
            }

                    //inputMovieSearch = editTextSearchMovieTitle.getText().toString();
//
//                MovieResult messageResult = new MovieResult(-1, inputMovieSearch);
//                arrayList.add(messageResult);
//                movieAdapter.notifyDataSetChanged();
//
//                ContentValues values = new ContentValues();
//                values.put(MovieDatabaseHelper.KEY_MESSAGE, inputMovieSearch);
//                db.insert(TABLE_NAME, "null", values);
//
//                editTextSearchMovieTitle.setText("");

        });
        /**
         * find listview of movie page, when click item it should show details of the movie
         */
        listViewMovie.setOnItemClickListener((adapterView, view, position, id) -> {
            String msg = movieAdapter.getItem(position);
            long ID = movieAdapter.getItemId(position);

            MovieFragment myFragment = new MovieFragment();

            if (ifFrameLayoutExist) {
                getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
            } else {
                Intent next = new Intent(MovieActivity.this, MovieFragment.class);
                next.putExtra("Message", msg);
                next.putExtra("ID", ID);

                startActivityForResult(next, 10);
            }

        });
        //if（movieDatabaseHelper.）
        Log.i(ACTIVITY_NAME, "In onCreate()");
        /**
         * fragment to show Recommended Movie
         */

        btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MovieFragment myFragment = new MovieFragment();
                getFragmentManager().beginTransaction().replace(R.id.testFragment, myFragment).commit();

            }
        });
//        getFragmentManager().beginTransaction().remove(myFragment).commit();
        
    }

    /**
     * add option menu on the layout
     * @param menu layout
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.movie_menu,menu);
        return true;
    }

    /**
     * use switch case to set MovieToolbar action
     * @param item on toorbar
     * @return item
     */
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;
        AlertDialog.Builder builder;

        switch(id){
            /**
             * when click nutrition icon, go to nutrition main page
             */
            case R.id.nutritionItemInMovie:
                intent = new Intent(this, NutritionActivity.class);
                startActivity(intent);
                break;
            /**
             * when click cbc icon, go to cbc main page
             */
            case R.id.cbcNewsItemInMovie:
                intent= new Intent(this, CBCNewsActivity.class);
                startActivity(intent);
                break;
            /**
             * when click movie icon, go to movie main page, not use
             */
            case R.id.movieItemInMovie:
//                intent = new Intent(this, MovieActivity.class);
//                startActivity(intent);
                /**
                 * when click the button, a snackbar for warning user already in movie page that comes out from the bottom of the screen
                 */
                Snackbar.make(btnSearchMovie,R.string.movie_warning_snackbar,Snackbar.LENGTH_SHORT).show();
                break;
            /**
             * when click octransport icon, go to octransport main page
             */
            case R.id.OCTranspoItemInMovie:
                intent = new Intent(this, OCTranspoActivity.class);
                startActivity(intent);
                break;
            /**
             * when click help, a dialog show up with author, version information and tips of the application
             */
            case R.id.helpMovie:
                builder = new AlertDialog.Builder(MovieActivity.this);
                    builder.setMessage(R.string.dialog_help_message);
                    builder.setTitle(R.string.dialog_help_title)
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onStop();
                                // User cancelled the dialog
                            }
                        }).show();
                break;
            /**
             * when click exit, a dialog warning comes out to ask the user whether exit the application or not
             */
            case R.id.exitMovie:
                builder = new AlertDialog.Builder(MovieActivity.this);
                    builder.setMessage(R.string.dialog_message);
                    builder.setTitle(R.string.dialog_title)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("Response", getString(R.string.home_message));
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    onStop();
                                    // User cancelled the dialog
                                }
                            }).show();
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
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

    /**
     * Database
     */
//    private void getAllMessageFromDb() {
//        cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
//        cursor.moveToFirst();
//
//        Log.i(ACTIVITY_NAME, "Cursor's Column Count " + cursor.getColumnCount());
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                final String chat = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_TITLE));
//                final long id = cursor.getLong(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MOVIE_ID));
//                MovieResult result = new MovieResult(id, chat);
//                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + chat);
//                arrayList.add(result);
//            }
//        }
//
//        for (int x = 0; x < cursor.getColumnCount(); x++) {
//            Log.i(ACTIVITY_NAME, "Cursor’s  column name = " + cursor.getColumnName(x));
//        }
//    }

    /**
     * for build arrayList of the list view
     */
    private class MovieAdapter extends BaseAdapter {

        //private ArrayList<MovieResult> list;

        private Context ctx;

        public MovieAdapter(ArrayList<String> list, Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public int getCount() {
            return movieTitleList.size();
        }

        @Override
        public String getItem(int position) {
            return movieTitleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = MovieActivity.this.getLayoutInflater();
            View result;

                result = inflater.inflate(R.layout.movie_list_row, null);

            TextView message = result.findViewById(R.id.movieListRowTitle);

            message.setText(getItem(position)); // get the string at position
            return result;

        }

    }


}
