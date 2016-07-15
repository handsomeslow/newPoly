package com.example.junxu.newPoly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class TohDetailActivity extends AppCompatActivity {

    @BindView(R.id.toh_title_detail)
    TextView tohTitleDetail;
    @BindView(R.id.toh_pic_detail)
    ImageView tohPicDetail;
    @BindView(R.id.toh_content_detail)
    TextView tohContentDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toh_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        String strContent = intent.getStringExtra("toh_content");
        String strTitle = intent.getStringExtra("toh_title");
        String strPic = intent.getStringExtra("toh_pic_url");
        getSupportActionBar().setTitle(strTitle);
        tohContentDetail.setText(strContent);
        tohTitleDetail.setText(strTitle);
        loadPic(strPic);
    }

    public static void start(Activity act, TohDetail.TohInfo tohInfo){
        Intent intent = new Intent(act,TohDetailActivity.class);
        intent.putExtra("toh_content",tohInfo.getDes());
        intent.putExtra("toh_title",tohInfo.getTitle());
        intent.putExtra("toh_pic_url",tohInfo.getPic());
        act.startActivity(intent);
    }

    public void loadPic(String url){
        Observable.just(url)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        return getNetBitmap(s);
                    }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {
                Toast.makeText(TohDetailActivity.this,"图片加载完成",Toast.LENGTH_LONG);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                tohPicDetail.setImageBitmap(bitmap);
            }
        });
    }

    private Bitmap getNetBitmap(String url){
        Bitmap bitmap = null;
        URL myFileUrl = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
