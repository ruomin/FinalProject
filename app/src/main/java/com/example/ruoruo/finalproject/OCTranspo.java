package com.example.ruoruo.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class OCTranspo extends AppCompatActivity {


    protected static final String ACTIVITY_NAME = "OCTranspo";
    private Button byRoute;
    private Button byStop;
    private EditText userEnter;
    private Button goHomeO;
    private Toolbar ocToolBar;
    private ListView listViewStops;
    private Cursor cursor;

    private EditText editTextSearchRoute;






    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoct,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;


        switch(id){

            case R.id.switchToNutrition:
                intent = new Intent(this, Nutrition.class);
                startActivity(intent);
                break;

            case R.id.switchTocbc:
                intent= new Intent(this, CBC.class);
                startActivity(intent);
                break;

            case R.id.switchTomovie:
             intent= new Intent(this, Movie.class);
                startActivity(intent);

                break;

            case R.id.switchToHome:
                intent= new Intent(this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.help:
                 AlertDialog.Builder message = new AlertDialog.Builder(OCTranspo.this);
                 message.setMessage("Message");
                 message.setTitle("Title").show();

                break;
            /**
             * when click exit, a dialog warning comes out to ask the user whether exit the application or not
             */
            case R.id.exit:

                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);
        Log.i(ACTIVITY_NAME, "In onCreate()");

         Button byRoute = (Button)findViewById(R.id.byRoute);
         Button byStop = (Button)findViewById(R.id.byStop);
         EditText userEnter = (EditText)findViewById(R.id.userEnter);
         Button goHomeO = (Button)findViewById(R.id.goHomeO);

        ocToolBar = (Toolbar) findViewById(R.id.OCToolBar);
        setSupportActionBar(ocToolBar);
        ocToolBar.setTitle("OC Transpo");
        ocToolBar.setSubtitle("Tool Bar");

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
                Intent intent = new Intent(OCTranspo.this, framlayout.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "In onClick()");
            }
        });


        goHomeO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(OCTranspo.this)
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
                                Intent intent = new Intent(OCTranspo.this,MainActivity.class);
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
