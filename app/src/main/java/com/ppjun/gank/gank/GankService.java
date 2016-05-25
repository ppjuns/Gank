package com.ppjun.gank.gank;

import com.ppjun.gank.bean.GankDaily;
import com.ppjun.gank.bean.GankData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @Package :com.ppjun.gank.gank
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/18 17:12.
 */
public interface GankService {


    @GET("day/{year}/{month}/{day}")
    Observable<GankDaily>  getDaily(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day
    );


    @GET("data/{type}/{amount}/{pages}")
    Observable<GankData> getData(
            @Path("type") String type,
            @Path("amount") int amount,
            @Path("pages") int pages
    );



}
