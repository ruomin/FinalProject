package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OCTranspoActivity extends Activity {


    protected static final String ACTIVITY_NAME = "OCTranspoActivity";
    private Button byRoute;
    private Button byStop;
    private EditText userEnter;
    private Button goHomeO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);
        Log.i(ACTIVITY_NAME, "In onCreate()");

         Button byRoute = (Button)findViewById(R.id.byRoute);
         Button byStop = (Button)findViewById(R.id.byStop);
         EditText userEnter = (EditText)findViewById(R.id.userEnter);
         Button goHomeO = (Button)findViewById(R.id.goHomeO);

        Toast.makeText(getApplicationContext(), "message", Toast.LENGTH_SHORT).show();

        byRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make( v, "message", Snackbar.LENGTH_LONG).show();
            }
        });

        byStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make( v, "message", Snackbar.LENGTH_LONG).show();
            }
        });


        goHomeO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(OCTranspoActivity.this)
                        .setTitle("Notice!")
                        .setMessage("You are leaving the page!")

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(OCTranspoActivity.this,MainActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
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
