package com.example.ruoruo.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AtyCbcWeb extends AppCompatActivity {
    //use webView to deliver a web application or a web page
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_cbc_web);
        webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String type = intent.getStringExtra("type");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDefaultTextEncodingName("UTF-8");
        if (type.equals("1")) {
            //load the page
            webView.loadUrl(url);//
        } else {
            webView.loadData(url, "text/html; charset=UTF-8", null);
        }
        webView.setWebViewClient(new WebViewClient() {
            /**
             * load the view to the webview
             * @param view
             * @param url
             */
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            /**
             * use the onPageFinished
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }


}
