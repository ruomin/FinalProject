package com.example.ruoruo.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AtyCbcDetail extends AppCompatActivity {
    private TextView title;
    private TextView guid;
    private TextView pubDate;
    private TextView author;
    private TextView category;
    private Button link;
    private Button goDescription;
    private Button save;
    private Button statistical;
    private Button snackButton;


    private NewsItem news;
    private MyDbUtil myDbUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_cbc_detail);
        myDbUtil = new MyDbUtil(this);
        title = (TextView) findViewById(R.id.title);
        guid = (TextView) findViewById(R.id.guid);
        pubDate = (TextView) findViewById(R.id.pubDate);
        author = (TextView) findViewById(R.id.author);
        category = (TextView) findViewById(R.id.category);
        link = (Button) findViewById(R.id.link);
        goDescription = (Button) findViewById(R.id.goDescription);
        save = (Button) findViewById(R.id.save);
        statistical = (Button) findViewById(R.id.statistical);
        snackButton =findViewById(R.id.cbc_snack);
        initData();
        initListener();
    }

    private void initData() {
        news = (NewsItem) getIntent().getSerializableExtra("data");
        title.setText(news.title);
        pubDate.setText(news.pubDate);
        author.setText(news.author);
        title.setText(news.title);
        guid.setText(news.guid);
        category.setText(news.category);
    }

    private void initListener() {
        /**
         * go to the link page
         */
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AtyCbcDetail.this, AtyCbcWeb.class);
                intent.putExtra("type", "1");
                intent.putExtra("url", news.link);
                startActivity(intent);
            }
        });

        /**
         * go the description page
         */
        goDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AtyCbcDetail.this, AtyCbcWeb.class);
                intent.putExtra("type", "2");
                intent.putExtra("url", news.description);
                startActivity(intent);
            }
        });

        /**
         * add listener to determine if the news already exist or not
         */
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long result = myDbUtil.addNews(news);
                if (result > 0) {
                    Toast.makeText(AtyCbcDetail.this, "add success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AtyCbcDetail.this, "add error news existed", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * add listener to the button to go the framelayout for count the values
         */
        statistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AtyCbcDetail.this, AtyCbcCount.class);
                startActivity(intent);
            }
        });

        /**
         * add a snackBar to the exit button to exit the activity
         */
        snackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v,R.string.cbc_button_quit, Snackbar.LENGTH_LONG).setAction("Confirm", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AtyCbcDetail.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).show();
            }
        });
    }
}
