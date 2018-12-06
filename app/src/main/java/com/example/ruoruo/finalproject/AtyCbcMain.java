package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AtyCbcMain extends AppCompatActivity {
    private List<NewsItem> newsItemList = new ArrayList<NewsItem>();
    private ListView listView;
    LinearLayout progressBar;
    NewsItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_cbc_main);

        Toolbar cbc_toolbar = (Toolbar)findViewById(R.id.cbcToolBar);
        setSupportActionBar(cbc_toolbar) ;

        listView = (ListView) findViewById(R.id.listView);
        progressBar = (LinearLayout) findViewById(R.id.progressBar);
        initData();


        /**
         * go to the detail page
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AtyCbcMain.this, AtyCbcDetail.class);
                intent.putExtra("data", newsItemList.get(i));
                startActivity(intent);
            }
        });

    }

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.activity_menu, m );
        return true;
    }

    /**
     * set the click listener to use different items to go to different page or exit or help
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;
        AlertDialog.Builder builder;

        switch (id){
            case R.id.cbc_item_help:
                builder = new AlertDialog.Builder(AtyCbcMain.this);
                builder.setTitle(R.string.cbc_helper_message)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onStop();
                            }
                        }).show();
                break;
            case R.id.cbc_item_quit:
                builder = new AlertDialog.Builder(AtyCbcMain.this);
                builder.setMessage(R.string.cbc_button_quit);
                builder.setTitle("Quit?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("Response", "Quit the program");
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }

                        })
                        .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onStop();
                            }
                        }).show();
                break;
            case R.id.cbc_n_item: intent = new Intent(this, Nutrition.class);
                startActivity(intent);
                break;
            case R.id.cbc_movie_item: intent = new Intent(this, Movie.class);
                startActivity(intent);
                break;
            case R.id.cbc_oc_item: intent = new Intent(this, OCTranspo.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initial the data
     */
    private void initData() {
        adapter = new NewsItemAdapter(this, newsItemList);
        listView.setAdapter(adapter);
        readNewsItem();

    }


    /**
     * get news
     */
    private void readNewsItem() {

        new AsyncTask<Void, Integer, String>() {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection connection = null;
                InputStream is = null;
                String ex = "";
                try {
                    URL url = new URL("https://www.cbc.ca/cmlink/rss-world");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setUseCaches(false);
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    connection.setDoInput(true);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        is = connection.getInputStream();
                        //get the input
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        //change the input to string
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
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            ex = e.toString();
                        }
                    }
                }
                return ex;
            }


            @Override
            protected void onPostExecute(String i) {
                progressBar.setVisibility(View.GONE);
                if (i == null) {
                    Toast.makeText(AtyCbcMain.this, "read error", Toast.LENGTH_LONG).show();
                    return;
                }
                parseNews(i);
            }
        }.execute();
    }


    private void parseNews(String s) {
        try {
            newsItemList.clear();
            InputStream in = new StringBufferInputStream(s);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("item")) {
                    newsItemList.add(readNewsItem(parser));
                }
            }
            adapter.notifyDataSetChanged();//刷新
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private NewsItem readNewsItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "item");
        String title = null;
        String link = null;
        String guid = null;
        String pubDate = null;
        String author = null;
        String category = null;
        String description = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                parser.require(XmlPullParser.START_TAG, null, "title");
                title = parseXML(parser).trim();
                parser.require(XmlPullParser.END_TAG, null, "title");
            } else if (name.equals("link")) {
                parser.require(XmlPullParser.START_TAG, null, "link");
                link = parseXML(parser).trim();
                parser.require(XmlPullParser.END_TAG, null, "link");
            } else if (name.equals("guid")) {
                parser.require(XmlPullParser.START_TAG, null, "guid");
                guid = parseXML(parser).trim();
                parser.require(XmlPullParser.END_TAG, null, "guid");
            } else if (name.equals("pubDate")) {
                parser.require(XmlPullParser.START_TAG, null, "pubDate");
                pubDate = parseXML(parser).trim();
                parser.require(XmlPullParser.END_TAG, null, "pubDate");
            } else if (name.equals("author")) {
                parser.require(XmlPullParser.START_TAG, null, "author");
                author = parseXML(parser).trim();
                parser.require(XmlPullParser.END_TAG, null, "author");
            } else if (name.equals("category")) {
                parser.require(XmlPullParser.START_TAG, null, "category");
                category = parseXML(parser).trim();
                parser.require(XmlPullParser.END_TAG, null, "category");
            } else if (name.equals("description")) {
                parser.require(XmlPullParser.START_TAG, null, "description");
                description = parseXML(parser).trim();
                parser.require(XmlPullParser.END_TAG, null, "description");
            }
        }
        return new NewsItem(title, link, guid, pubDate, author, category, description);
    }

    private String parseXML(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    public static String readAssetsTxt(Context context, String fileName) {
        try { //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName + ".txt");
            int size = is.available(); // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close(); // Convert the buffer into a string.
            String text = new String(buffer, "utf-8"); // Finally stick the string into the text view.
            return text;
        } catch (IOException e) { // Should never happen! //
            e.printStackTrace();
        }
        return "R.string.cbc_detail_alert";
    }


}


