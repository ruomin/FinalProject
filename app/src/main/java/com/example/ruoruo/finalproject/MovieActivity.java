package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

    private Button btn_testAsyncTask;

    /**
     * toorbar for movie application
     */
    public Toolbar toolbar;

    /**
     * when click search button, the result should be shown on the listView
     */
    private ListView listViewMovie;

    /**
     * boolean frame Layout is exist or not
     */
    boolean ifFrameLayoutExist;
    
    private Cursor cursor;

    private EditText editTextSearchMovieTitle;

    private String inputMovieSearch;

    /**
     * for database part
     */
    private MovieDatabaseHelper movieDatabaseHelper;
    private SQLiteDatabase db;
    private ArrayList<MovieResult> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        /**
         * display wellcome message Toast on the screen when enter the application
         */
        Toast.makeText(MovieActivity.this, "Well come to movie world!",Toast.LENGTH_SHORT).show();

        /**
         * boolean check if the FrameLayout exists on the screen
         */
        ifFrameLayoutExist = findViewById(R.id.frame) != null;

        final ArrayList<MovieResult> arrayList = new ArrayList<MovieResult>();
        MovieAdapter movieAdapter = new MovieAdapter(arrayList,this);

        /**
         * add MovieToolbar to the page
         */
        toolbar = findViewById(R.id.movieToolbar);
        setSupportActionBar(toolbar);
        /**
         * find search movie button and set on click listener
         */
        btnSearchMovie = findViewById(R.id.buttonSearchMovie);

        /**
         * Test Fragment
         */
        btnSearchMovie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    MovieFragment myFragment = new MovieFragment();
                    getFragmentManager().beginTransaction().replace(R.id.testFragment, myFragment).commit();

//                inputMovieSearch = editTextSearchMovieTitle.getText().toString();
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
            }
        });
        /**
         * find listview of movie page, when click item it should show details of the movie
         */
        listViewMovie = findViewById(R.id.listViewMovie);
//        listViewMovie.setOnItemClickListener((adapterView, view, position, id) -> {
//            String msg = movieAdapter.getItem(position);
//            long ID = movieAdapter.getItemId(position);
//
//            MovieFragment myFragment = new MovieFragment();
//
//            if (ifFrameLayoutExist) {
//                getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
//            } else {
//                Intent next = new Intent(MovieActivity.this, MovieFragment.class);
//                next.putExtra("Message", msg);
//                next.putExtra("ID", ID);
//
//                startActivityForResult(next, 10);
//            }
//
//        });
        Log.i(ACTIVITY_NAME, "In onCreate()");
        /**
         * test AsyncTask
         */
        btn_testAsyncTask = findViewById(R.id.testAsyncTaskButton);
        btn_testAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this,MovieDetail.class);
                startActivity(intent);
            }
        });
        
        
    }


    /**
     * add option menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.movie_menu,menu);
        return true;
    }

    /**
     * use switch case to set MovieToolbar action
     * @param item
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
    private void getAllMessageFromDb() {
        cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();

        Log.i(ACTIVITY_NAME, "Cursor's Column Count " + cursor.getColumnCount());

        if (cursor != null) {
            while (cursor.moveToNext()) {
                final String chat = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_MESSAGE));
                final long id = cursor.getLong(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID));
                MovieResult result = new MovieResult(id, chat);
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + chat);
                arrayList.add(result);
            }
        }

        for (int x = 0; x < cursor.getColumnCount(); x++) {
            Log.i(ACTIVITY_NAME, "Cursor’s  column name = " + cursor.getColumnName(x));
        }
    }

    /**
     * for build arrayList of the list view
     */
    private class MovieAdapter extends BaseAdapter {

        private ArrayList<MovieResult> list;
        private Context ctx;

        public MovieAdapter(ArrayList<MovieResult> list, Context ctx) {
            this.list = list;
            this.ctx = ctx;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public MovieResult getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = MovieActivity.this.getLayoutInflater();
            View result;

            if (position % 2 == 0) {

                result = inflater.inflate(R.layout.activity_movie_detail, null);

            } else {

                result = inflater.inflate(R.layout.activity_movie_detail, null);
            }

//            TextView message = result.findViewById(R.id.message_text);
//
//            message.setText(getItem(position).getMsg()); // get the string at position
            return null;

        }

    }
}
