package com.example.junxu.newPoly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BasicActivity {

    @BindView(R.id.bt_toutiao)
    Button btToutiao;
    @BindView(R.id.bt_tiyu)
    Button btTiyu;
    @BindView(R.id.bt_caijing)
    Button btCaijing;
    @BindView(R.id.list_item)
    RecyclerView listItem;

    private RequestQueue mQueue;
    MyListAdapter myListAdapter;
    private static final String newTopURL = "http://v.juhe.cn/toutiao/index?key=f680bf9f08cf266d37a3736cbd5e1fcf&type="; //聚合数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecycleView();
        mQueue = Volley.newRequestQueue(this);
        getResultRespone("tiyu");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "暂时没有内容", Snackbar.LENGTH_LONG);
                snackbar.setAction("Action", null).show();
            }
        });
    }

    private void initRecycleView() {
        //recyclerView = (RecyclerView) findViewById(R.id.list_item);
        listItem.setLayoutManager(new LinearLayoutManager(this));
        listItem.setHasFixedSize(true);
        //recyclerView.setAdapter(new MyListAdapter(this,null));
    }


    /**
     * 图片加载
     */
    private void getImage(String imgURL) {
        ImageRequest imageRequest = new ImageRequest(imgURL,
                new Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        //imgView.setImageBitmap(bitmap);
                    }
                }, 120, 120, Bitmap.Config.RGB_565,
                new ErrorListener() {
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
    private void getResultRespone(String type) {
        MyJsonRequeset<ResultRespone> gsonRequest = new MyJsonRequeset<ResultRespone>(
                newTopURL, type, ResultRespone.class, topnewInfoListenercallback(), topnewInfoErrorListenercallback()
        );
        mQueue.add(gsonRequest);
    }

    private Listener<ResultRespone> topnewInfoListenercallback() {
        Listener<ResultRespone> listener = new Listener<ResultRespone>() {
            @Override
            public void onResponse(ResultRespone result) {
                if (result.getErr_code() == 0) {  //数据返回成功
                    List<TopnewInfo> resultRespone = result.getResult().getData();//返回新闻列表
                    ShowNewList(resultRespone);
                } else {
                    result.getReason(); //获取服务器返回错误类型
                }
            }
        };
        return listener;
    }

    private ErrorListener topnewInfoErrorListenercallback() {
        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        };
        return errorListener;
    }

    private void ShowNewList(final List<TopnewInfo> data) {
        myListAdapter = new MyListAdapter(this, data);
        listItem.setAdapter(myListAdapter);
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


    @OnClick({R.id.bt_toutiao, R.id.bt_tiyu, R.id.bt_caijing})
    public void onClick(View view) {
        switch (view.getId()) {
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
}
