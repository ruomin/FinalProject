package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Nutrition extends Activity {

    protected static final String ACTIVITY_NAME = "Nutrition";

    Button buttonclick;
    Button Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        buttonclick = findViewById(R.id.clickableButton);
        Exit = findViewById(R.id.clickableButton2);

        Toast.makeText(Nutrition.this,"Nutrition is the best!", Toast.LENGTH_LONG).show();

        buttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make( v, "What a nice Snack bar, so much nutrition", Snackbar.LENGTH_LONG).show();
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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
