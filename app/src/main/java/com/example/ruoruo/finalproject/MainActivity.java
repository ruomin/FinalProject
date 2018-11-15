package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    protected static final String ACTIVITY_NAME = "MainActivity";

    ImageView nutrition;
    ImageView cbc;
    ImageView movie;
    ImageView octranspo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        nutrition = findViewById(R.id.Nutrition);
        cbc = findViewById(R.id.CBC);
        movie = findViewById(R.id.Movie);
        octranspo = findViewById(R.id.OCTranspo);

        nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Nutrition.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "In onClick()");
            }
        });

        cbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CBC.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "In onClick()");
            }
        });

        movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "In onClick()");
            }
        });

        octranspo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OCTranspoActivity.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "In onClick()");
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
}
