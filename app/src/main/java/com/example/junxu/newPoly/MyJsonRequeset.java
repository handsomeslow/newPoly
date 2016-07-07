package com.example.junxu.newPoly;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;

/**
 * Created by junxu on 2016/7/5.
 */
public class MyJsonRequeset<T> extends Request<T> {
    private Listener<T> mListener = null;
    Gson mGson;
    private Class<T> mClass;

    public MyJsonRequeset(int method, String url, Response.ErrorListener errlistener) {
        super(method, url, errlistener);
    }

    public MyJsonRequeset(int method, String url,Class<T> Jsonclass, Listener<T> listener, Response.ErrorListener errlistener) {
        super(method, url, errlistener);
        mListener = listener;
        mClass = Jsonclass;
        mGson = new Gson();
    }

    public MyJsonRequeset( String url,Class<T> Jsonclass, Listener<T> listener, Response.ErrorListener errlistener) {
        super(Method.GET, url, errlistener);
        mListener = listener;
        mClass = Jsonclass;
        mGson = new Gson();
    }

    public MyJsonRequeset( String url,String type,Class<T> Jsonclass, Listener<T> listener, Response.ErrorListener errlistener) {
        super(Method.GET, url+type, errlistener);
        mListener = listener;
        mClass = Jsonclass;
        mGson = new Gson();
    }

    @Override
    protected void deliverResponse(T t) {
        mListener.onResponse(t);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String strResponse = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(mGson.fromJson(strResponse, mClass),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(
                    new ParseError(e)
            );
        }
    }
}
