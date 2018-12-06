package com.example.ruoruo.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;

public class Nutrition extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "Nutrition";



    Button buttonclick;
    public Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        buttonclick = findViewById(R.id.clickableButton);
        toolbar = findViewById(R.id.newToolBar);
        setSupportActionBar(toolbar);



        Toast.makeText(Nutrition.this, "Nutriment is the best!", Toast.LENGTH_LONG).show();

        buttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nutrition.this, FoodNetActivity.class);
                startActivity(intent);
            }
        });




        Log.i(ACTIVITY_NAME, "In onCreate()");


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

            case R.id.movieNutrition:
                intent = new Intent(this, Movie.class);
                startActivity(intent);
                break;

                case R.id.OCTranspoNutrition: intent = new Intent(this, OCTranspo.class);
                startActivity(intent);
                break;

            case R.id.cbcNewsNutrition: intent = new Intent(this, AtyCbcMain.class);
                startActivity(intent);
                break;

            case R.id.helpNutrition:

                builder = new AlertDialog.Builder(Nutrition.this);
                builder.setMessage(R.string.Information);
                builder.setTitle(R.string.Title)
                        .setNegativeButton("Finish", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();//create the dialog
                builder.show();
                break;


            case R.id.exitNutrition:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

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
