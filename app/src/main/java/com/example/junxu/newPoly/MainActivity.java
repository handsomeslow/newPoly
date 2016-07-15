package com.example.junxu.newPoly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.junxu.service.HttpServiece;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
                //Snackbar snackbar = Snackbar.make(view, "暂时没有内容", Snackbar.LENGTH_LONG);
                //snackbar.setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,TohActivity.class);
                startActivity(intent);
            }
        });

        ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(imageLoaderConfiguration);
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
                URLContents.newTopURL, type, ResultRespone.class, topnewInfoListenercallback(), topnewInfoErrorListenercallback()
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
                TopnewActivity.start(MainActivity.this,
                        data.get(position).getTitle(),
                        data.get(position).getThumbnail_pic_s());
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
                //getResultRespone("caijing");
                // intent = new Intent(MainActivity.this,TohActivity.class);
                //startActivity(intent);
                getTohDetail();
                break;
        }
    }

    private void getTohDetail(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLContents.JUHE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiece serviece = retrofit.create(HttpServiece.class);
        Call<TohDetail> tohCall = serviece.getTohDetail(URLContents.API_KEY,"1.0",7,14);
        tohCall.enqueue(new Callback<TohDetail>() {
            @Override
            public void onResponse(Call<TohDetail> call, Response<TohDetail> response) {
                TohDetail tohDetail = response.body();
            }

            @Override
            public void onFailure(Call<TohDetail> call, Throwable t) {

            }
        });
    }
}
