package com.example.ruoruo.finalproject;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruoruo.finalproject.adpater.FoodLocalAdapter;
import com.example.ruoruo.finalproject.bean.FoodLocal;
import com.example.ruoruo.finalproject.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class FoodLocalActivity extends AppCompatActivity {
    private ListView listView;
    private EditText edit;
    private Button search;
    private Button total;
    List<FoodLocal> datas = new ArrayList<FoodLocal>();
    private FoodLocalAdapter adpater;
    private DBHelper dbHelper;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adpater.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_food);

        dbHelper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.listView);
        edit = (EditText) findViewById(R.id.edit);
        search = (Button) findViewById(R.id.search);
        total = (Button) findViewById(R.id.total);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edit.getText().toString().trim();
                if (s.equals("")) {
                    Toast.makeText(FoodLocalActivity.this, "Input can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                searchDate(s);
            }
        });


        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodLocalActivity.this, FoodTotalActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FoodLocalActivity.this, FoodDetailActivity.class);
                intent.putExtra("data", datas.get(i));
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    void initData() {
        datas.clear();
        adpater = new FoodLocalAdapter(this, datas);
        listView.setAdapter(adpater);
        Cursor c = dbHelper.query();
        while (c.moveToNext()) {
            String foodId = c.getString(c.getColumnIndex("foodId"));
            String uri = c.getString(c.getColumnIndex("url"));
            String label = c.getString(c.getColumnIndex("label"));
            String nutrients = c.getString(c.getColumnIndex("nutrients"));
            String category = c.getString(c.getColumnIndex("category"));
            String categoryLabel = c.getString(c.getColumnIndex("categoryLabel"));
            String tag = c.getString(c.getColumnIndex("tag"));

            //use FoodLocal to put the data
            FoodLocal p = new FoodLocal(foodId, uri, label, nutrients, category, categoryLabel, tag);
            datas.add(p);
        }

        adpater.notifyDataSetChanged();
    }

    /**
     * load the data
     */
    void searchDate(String tag) {
        datas.clear();
        Cursor c = dbHelper.query();
        while (c.moveToNext()) {
            String foodId = c.getString(c.getColumnIndex("foodId"));
            String uri = c.getString(c.getColumnIndex("url"));
            String label = c.getString(c.getColumnIndex("label"));
            String nutrients = c.getString(c.getColumnIndex("nutrients"));
            String category = c.getString(c.getColumnIndex("category"));
            String categoryLabel = c.getString(c.getColumnIndex("categoryLabel"));
            String tagStr = c.getString(c.getColumnIndex("tag"));

            //use FoodLocal to put the data
            FoodLocal p = new FoodLocal(foodId, uri, label, nutrients, category, categoryLabel, tagStr);
            datas.add(p);
        }


        List<FoodLocal> temp = new ArrayList<FoodLocal>();
        for (FoodLocal foodLocal : datas) {
            if (tag.equals(foodLocal.getTag())) {
                temp.add(foodLocal);
            }
        }
        datas.clear();
        datas.addAll(temp);
        adpater.notifyDataSetChanged();
    }


}
