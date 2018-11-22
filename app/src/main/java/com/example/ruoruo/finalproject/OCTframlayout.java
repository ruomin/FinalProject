package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OCTframlayout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framlayout);


        Button FrameLayoutBtn = findViewById(R.id.FrameLayoutBtn);

        FrameLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override


                public void onClick(View v) {
                octFragment myF = new octFragment();
                getFragmentManager().beginTransaction().replace(R.id.octFrameLayout, myF).commit();
            }
        });
    }
}
