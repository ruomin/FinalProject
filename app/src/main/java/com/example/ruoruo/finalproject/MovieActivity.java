package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *
 */
public class MovieActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MovieActivity";

    /**
     *
     */
    private Button btnSearchMovie;

    private Toolbar toolbar;

    private Button novieToHomeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Toast toast = Toast.makeText(MovieActivity.this, "Well come to movie world!",Toast.LENGTH_SHORT);
        toast.show(); //display message box

        novieToHomeButton = findViewById(R.id.homeButton);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Movie");
        toolbar.setSubtitle("Search");
        toolbar.setLogo(R.mipmap.ic_launcher);

        final ArrayList<String> arrayList = new ArrayList<String>();
        MovieAdapter movie = new MovieAdapter(arrayList,this);


        btnSearchMovie = findViewById(R.id.buttonSearchMovie);

        btnSearchMovie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Loading may take some time",Snackbar.LENGTH_SHORT).show();
            }
        });

        novieToHomeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MovieActivity.this);
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

                        })

                        .show();
            }
        });



        Log.i(ACTIVITY_NAME, "In onCreate()");
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

    private class MovieAdapter extends BaseAdapter {

        private ArrayList<String> list;
        private Context ctx;

        public MovieAdapter(ArrayList<String> list, Context ctx) {
            this.list = list;
            this.ctx = ctx;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;

        }

    }
}
