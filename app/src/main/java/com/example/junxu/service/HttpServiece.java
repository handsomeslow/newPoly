package com.example.junxu.service;

import com.example.junxu.newPoly.ResultRespone;
import com.example.junxu.newPoly.TohDetail;
import com.example.junxu.newPoly.URLContents;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by junxu on 2016/7/13.
 */public interface HttpServiece {
    @GET("")
    List<ResultRespone> listRepos(@Query("type") String type);


    @GET(URLContents.TOH)
    Call<TohDetail> getTohDetail(@Query(URLContents.TOH_KEY)String aipKey,
                                 @Query(URLContents.TOH_VER)String ver,
                                 @Query(URLContents.TOH_MONTH)int month,
                                 @Query(URLContents.TOH_DAY)int day);



}
