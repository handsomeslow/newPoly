package com.example.junxu.newPoly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopnewActivity extends AppCompatActivity {
    @BindView(R.id.new_detail_img)
    ImageView newDetailImg;

    @BindView(R.id.new_detail_title)
    TextView newDetailTitle;

    String urlString;
    String titleString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topnew);
        ButterKnife.bind(this);
        initData();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(urlString, newDetailImg);
        /*
        imageLoader.setDefaultLoadingListener(new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Toast.makeText(TopnewActivity.this,"加载完成",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        */
        imageLoader.displayImage(urlString,newDetailImg,new ImageSize(200,200));
        newDetailTitle.setText(titleString);
    }

    private void initData() {
        Intent intent = getIntent();
        this.urlString = intent.getStringExtra("url");
        this.titleString = intent.getStringExtra("title");

    }

    public static void start(Activity act, String title, String url) {
        Intent intent = new Intent(act, TopnewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        act.startActivity(intent);
    }
}
