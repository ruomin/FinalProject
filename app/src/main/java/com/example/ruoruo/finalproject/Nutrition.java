package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Switch;

import android.support.v7.widget.Toolbar;

public class Nutrition extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "Nutrition";

    Button buttonclick;
    Button Exit;
    Button Fragment;
    public Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        buttonclick = findViewById(R.id.clickableButton);
        Exit = findViewById(R.id.clickableButton2);
        Fragment = findViewById(R.id.fragmentButton);
        toolbar = findViewById(R.id.newToolBar);
        setSupportActionBar(toolbar);


        Toast.makeText(Nutrition.this, "Nutriment is the best!", Toast.LENGTH_LONG).show();

        buttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "What a nice Snack bar, so much nutriment", Snackbar.LENGTH_LONG).show();
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                Toast.makeText(Nutrition.this, "Exit to Main Application page", Toast.LENGTH_LONG).show();
            }
        });

        Fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentPage fragmentPage = new FragmentPage();
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentPage).commit();
            }
        });


        Log.i(ACTIVITY_NAME, "In onCreate()");


    }

    private void showDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("Exit the application?")//set the title
                .setMessage("Are you sure you want to exit this page?")//set the context
                //confirm button
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                //cancel button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();//create the dialog
        dialog.show();//show the dialog
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        AlertDialog.Builder builder;
        //   Dialog dialog = new AlertDialog.Builder(this);
        switch (id) {

            case R.id.nutritionItem:
                // intent = new Intent(this, Nutrition.class);
                // startActivity(intent);
                break;

            case R.id.movieNutrition:
                intent = new Intent(this, Movie.class);
                startActivity(intent);
                break;

            case R.id.helpNutrition:

                builder = new AlertDialog.Builder(Nutrition.this);
                builder.setMessage(R.string.Information);
                builder.setTitle(R.string.Title)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();//create the dialog
                builder.show();
        }
        return true;
    }






        @Override
        protected void onResume () {
            super.onResume();
            Log.i(ACTIVITY_NAME, "In onResume()");
        }
        @Override
        protected void onStart () {
            super.onStart();
            Log.i(ACTIVITY_NAME, "In onStart()");
        }
        @Override
        protected void onPause () {
            super.onPause();
            Log.i(ACTIVITY_NAME, "In onPause()");
        }
        @Override
        protected void onStop () {
            super.onStop();
            Log.i(ACTIVITY_NAME, "In onStop()");
        }
        @Override
        protected void onDestroy () {
            super.onDestroy();
            Log.i(ACTIVITY_NAME, "In onDestroy()");
        }

    }

