package com.example.junxu.newPoly;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by junxu on 2016/7/6.
 */
public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {
    private Context context;
    private List<TopnewInfo> data;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private DisplayMetrics dm;

    public MyListAdapter(Context context, List<TopnewInfo> data) {
        this.context = context;
        this.data = data;
        initImageQueue();
        Resources resources = context.getResources();
        dm = resources.getDisplayMetrics();
    }

    private void initImageQueue(){
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
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
        void OnItemLongClick(View view,int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_title.setText(data.get(i).getTitle());
        myViewHolder.tv_type.setText(data.get(i).getType());
        myViewHolder.tv_realtype.setText(data.get(i).getRealtype());
        myViewHolder.tv_author.setText(data.get(i).getAuthor_name());
        myViewHolder.tv_date.setText(data.get(i).getDate());
        myViewHolder.iv_new.setDefaultImageResId(R.drawable.loading);  //设置加载时的默认显示图片
        myViewHolder.iv_new.setErrorImageResId(R.drawable.loading);  //设置加载失败显示的图片
        myViewHolder.iv_new.setImageUrl(data.get(i).getThumbnail_pic_s(), mImageLoader);  //设置URL

        //myViewHolder.itemView.setTag(context);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.OnItemClick(myViewHolder.itemView,i);
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.cell_topnew,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_type;
        TextView tv_realtype;
        TextView tv_author;
        TextView tv_date;
        NetworkImageView iv_new;
        LinearLayout ll;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.new_title);
            tv_type = (TextView) itemView.findViewById(R.id.new_type);
            tv_realtype = (TextView) itemView.findViewById(R.id.new_realtype);
            tv_author = (TextView) itemView.findViewById(R.id.new_author);

            tv_date = (TextView) itemView.findViewById(R.id.new_date);
            iv_new = (NetworkImageView) itemView.findViewById(R.id.new_img);
            iv_new.setAdjustViewBounds(true);
            iv_new.setMaxWidth(dm.widthPixels/5);
            iv_new.setMaxHeight(dm.heightPixels/10);

            ll = (LinearLayout) itemView.findViewById(R.id.new_contextlay);
            ll.setLayoutParams(new LinearLayout.LayoutParams(4*dm.widthPixels/5-25,dm.heightPixels/10));
        }
    }


}
