package com.example.ruoruo.finalproject;


import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruoruo.finalproject.adpater.FoodNetAdapter;
import com.example.ruoruo.finalproject.bean.Food;
import com.example.ruoruo.finalproject.util.DBHelper;
import com.example.ruoruo.finalproject.util.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FoodNetActivity extends AppCompatActivity {
    private String url = "https://api.edamam.com/api/food-database/parser?app_id=b4a7932a&app_key=5f1942229055c644b4aec8358ff7316a&ingr=";
    private ListView listView;
    private LinearLayout proBar;
    private EditText edit;
    private Button search;
    private Button addFavourites;
    private Button favourites;
    List<Food> datas = new ArrayList<Food>();
    private FoodNetAdapter adpater;
    private DBHelper dbHelper;
    private Button help;

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
        setContentView(R.layout.activity_net_food);

        dbHelper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.listView);
        proBar = (LinearLayout) findViewById(R.id.proBar);
        edit = (EditText) findViewById(R.id.edit);
        search = (Button) findViewById(R.id.search);
        addFavourites = (Button) findViewById(R.id.addFavourites);
        favourites = (Button) findViewById(R.id.favourites);
        help = findViewById(R.id.information);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edit.getText().toString().trim();
                if (s.equals("")) {
                    Toast.makeText(FoodNetActivity.this, "Input can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                searchDate(url + s);
            }
        });


        addFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datas.size() < 1) {
                    Toast.makeText(FoodNetActivity.this, "There's nothing to add to favourites", Toast.LENGTH_LONG).show();
                    return;
                }
                add();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.information), "Search for any food in search bar", Snackbar.LENGTH_LONG).show();
            }
        });

        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodNetActivity.this, FoodLocalActivity.class);
                startActivity(intent);
            }
        });

        initData();
    }


    void initData() {
        adpater = new FoodNetAdapter(this, datas);
        listView.setAdapter(adpater);
    }

    /**
     * load the data
     *
     * @param url
     */
    void searchDate(final String url) {

        new AsyncTask<Void, Integer, String>() {
            @Override
            protected void onPreExecute() {

                proBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(Void... params) {
                String result = FoodNetActivity.get(url);
                return result;
            }


            @Override
            protected void onPostExecute(String i) {
                proBar.setVisibility(View.GONE);
                System.out.println(i);
                parseJson(i);
                Toast.makeText(FoodNetActivity.this, "Load success", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }


    private void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String parseData = jsonObject.getString("parsed");//拿到解析好的数据
            JSONArray jsonArray = new JSONArray(parseData);
            datas.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.getJSONObject(i);
                Food item = GsonUtil.GsonToBean(j.getString("food"), Food.class);
                datas.add(item);
            }
            for (Food f : datas) {
                System.out.println(f.toString());
            }
            handler.sendEmptyMessage(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Internet Request
     *
     * @param urlPath
     * @return
     */
    public static String get(String urlPath) {
        HttpURLConnection connection = null;
        InputStream is = null;
        String ex = "";
        try {
            URL url = new URL(urlPath);
            //Get the URL
            connection = (HttpURLConnection) url.openConnection();
            //Get HttpURLConnection
            connection.setRequestMethod("GET");
            // the initial the method is GET
            connection.setUseCaches(false);

            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            //read from httpUrlConnection
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                is = connection.getInputStream();
                //get the BufferedReader
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                //Use StringBuilder to store the data
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ex = e.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
                connection = null;
            }
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                    ex = e.toString();
                }
            }
        }
        return ex;
    }


    private void add() {
        for (Food food : datas) {
            ContentValues values = new ContentValues();
            values.put("foodId", food.getFoodId());
            values.put("url", food.getUri());
            values.put("label", food.getLabel());
            values.put("nutrients", food.getNutrients().toString());
            values.put("category", food.getCategory());
            values.put("categoryLabel", food.getCategoryLabel());
            values.put("tag", "");
            if (dbHelper.insert(values)) {
                Toast.makeText(FoodNetActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FoodNetActivity.this, "Food already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
