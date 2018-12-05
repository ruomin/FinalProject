package com.example.ruoruo.finalproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CBC extends AppCompatActivity {
    private Button cbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cbc = (Button) findViewById(R.id.cbc);
        cbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CBC.this, AtyCbcMain.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int featureId = item.getItemId();
        if (featureId == R.id.about) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("message");
            alert.setMessage("Author:Xin Zhao\n" +
                    "Date:2018-11-30");
            alert.create().show();
        }
        if (featureId == R.id.exit) {
            CBC.this.finish();
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
