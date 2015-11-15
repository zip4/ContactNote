package com.example.pashulya.contactnote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new SearchJavascriptInterface(getApplicationContext()), "search");
        getSharedPreferences("ContactNotePrefs", Context.MODE_PRIVATE).edit().clear().commit();
        //String Url = "http://google.com.ua";

        String Url = "file:///android_asset/www/search.html";
        webView.loadUrl(Url);
    }
}
