package com.example.junxu.newPoly;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response.*;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RequestQueue mQueue;
    private RecyclerView recyclerView;
    private Button bt_toutiao;
    private Button bt_tiyu;
    private Button bt_caijing;
    private MyListAdapter myListAdapter;
    private static final String newTopURL = "http://v.juhe.cn/toutiao/index?key=f680bf9f08cf266d37a3736cbd5e1fcf&type="; //聚合数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_toutiao = (Button)findViewById(R.id.bt_toutiao);
        bt_tiyu = (Button)findViewById(R.id.bt_tiyu);
        bt_caijing = (Button)findViewById(R.id.bt_caijing);
        bt_toutiao.setOnClickListener(this);
        bt_tiyu.setOnClickListener(this);
        bt_caijing.setOnClickListener(this);

        initRecycleView();
        mQueue = Volley.newRequestQueue(this);
        getResultRespone("tiyu");
    }

    private void initRecycleView(){
        recyclerView = (RecyclerView) findViewById(R.id.list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //recyclerView.setAdapter(new MyListAdapter(this,null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_toutiao:
                getResultRespone("keji");
                break;

            case R.id.bt_tiyu:
                getResultRespone("tiyu");
                break;

            case R.id.bt_caijing:
                getResultRespone("caijing");
                break;
        }
    }

/**
 * 图片加载
 */
    private void getImage(String imgURL){
        ImageRequest imageRequest = new ImageRequest(imgURL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        //imgView.setImageBitmap(bitmap);
                    }
                }, 120, 120, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                });
        mQueue.add(imageRequest);
    }

/**
 * Gson数据解析
 */
    private void getResultRespone(String type){
       MyJsonRequeset<ResultRespone> gsonRequest = new MyJsonRequeset<ResultRespone>(
               newTopURL,type,ResultRespone.class,topnewInfoListenercallback(),topnewInfoErrorListenercallback()
       );
        mQueue.add(gsonRequest);
    }

    private Listener<ResultRespone> topnewInfoListenercallback(){
        Listener<ResultRespone> listener = new Listener<ResultRespone>() {
            @Override
            public void onResponse(ResultRespone result) {
                if(result.getErr_code() == 0){  //数据返回成功
                    List<TopnewInfo> resultRespone = result.getResult().getData();//返回新闻列表
                    ShowNewList(resultRespone);
                }else {
                    result.getReason(); //获取服务器返回错误类型
                }
            }
        };
        return listener;
    }

    private ErrorListener topnewInfoErrorListenercallback(){
        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        };
        return errorListener;
    }

    private void ShowNewList(final List<TopnewInfo> data){
        myListAdapter = new MyListAdapter(this,data);
        recyclerView.setAdapter(myListAdapter);
        myListAdapter.setOnItemClickListener(new MyListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                NewDetailActivity.start(MainActivity.this,
                        data.get(position).getTitle(),
                        data.get(position).getUrl());
            }

            @Override
            public void OnItemLongClick(View view, int position) {

            }
        });
    }
}
