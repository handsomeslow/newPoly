package com.example.junxu.newPoly;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class NewDetailActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newweb);
        webView = (WebView) findViewById(R.id.new_details);
        initData();
    }

    private void initData(){
        Intent intent = getIntent();
        String strUrl= intent.getStringExtra("url");
        String strTitle = intent.getStringExtra("title");
        getSupportActionBar().setTitle(strTitle);
        webView.loadUrl(strUrl);
    }

    public static void start(Activity act,String title,String url){
        Intent intent = new Intent(act,NewDetailActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        act.startActivity(intent);
    }

}
