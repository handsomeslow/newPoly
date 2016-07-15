package com.example.junxu.newPoly;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by junxu on 2016/7/14.
 */
public class TohListAdapter extends RecyclerView.Adapter<TohListAdapter.ViewHolder> {
    Context context;
    List<TohDetail.TohInfo> data;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    public TohListAdapter(Context context, List<TohDetail.TohInfo> data) {
        this.context = context;
        this.data = data;
        initImageQueue();
    }

    private void initImageQueue() {
        mQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.cell_toh, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tohTitle.setText(data.get(position).getTitle());
        holder.tohYear.setText(data.get(position).getYear()+"");
        holder.tohPic.setDefaultImageResId(R.drawable.nullpic);
        holder.tohPic.setErrorImageResId(R.drawable.nullpic);
        holder.tohPic.setImageUrl(data.get(position).getPic(),mImageLoader);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.OnItemClick(holder.itemView,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.toh_title)
        TextView tohTitle;
        @BindView(R.id.toh_year)
        TextView tohYear;
        @BindView(R.id.toh_pic)
        NetworkImageView tohPic;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View view,int postion);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
