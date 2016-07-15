package com.example.junxu.newPoly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.junxu.service.HttpServiece;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TohActivity extends AppCompatActivity {
    @BindView(R.id.bt_toh)
    Button btToh;
    @BindView(R.id.list_toh)
    RecyclerView listToh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toh);
        ButterKnife.bind(this);
        initView();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        getTohDetail(month , day);
    }

    private void initView(){
        //listToh.setLayoutManager(new GridLayoutManager(this,4)); //网格
        listToh.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)); //瀑布
        //listToh.setHasFixedSize(true);
        //listToh.setAdapter();
    }

    private void showTohDetail(final List<TohDetail.TohInfo> infos){
        TohListAdapter adapter = new TohListAdapter(this,infos);
        listToh.setAdapter(adapter);
        adapter.setOnItemClickListener(new TohListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int postion) {
                TohDetailActivity.start(TohActivity.this,
                        infos.get(postion));
            }
        });
    }

    @OnClick(R.id.bt_toh)
    public void onClick() {

    }

    private void getTohDetail(int month,int day) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLContents.JUHE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpServiece serviece = retrofit.create(HttpServiece.class);
        Call<TohDetail> tohCall = serviece.getTohDetail(URLContents.API_KEY, "1.0", month, day);
        tohCall.enqueue(new Callback<TohDetail>() {
            @Override
            public void onResponse(Call<TohDetail> call, Response<TohDetail> response) {
                Toast.makeText(TohActivity.this,"加载成功",Toast.LENGTH_LONG).show();
                TohDetail tohDetail = response.body();
                List<TohDetail.TohInfo> tohInfos = tohDetail.getResult();
                showTohDetail(tohInfos);
            }

            @Override
            public void onFailure(Call<TohDetail> call, Throwable t) {
                Toast.makeText(TohActivity.this,"加载失败",Toast.LENGTH_LONG).show();
            }
        });
    }
}
