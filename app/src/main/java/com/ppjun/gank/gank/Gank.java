package com.ppjun.gank.gank;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Package :com.ppjun.gank.gank
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/18 16:40.
 */
public class Gank {
    private static volatile Gank mInstance=new Gank();
   private GankService mGankService;
    public Gank() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GankApi.BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

       this.mGankService=retrofit.create(GankService.class);
    }

    public static Gank getInsance() {
        if (mInstance == null) {

            Gank mIns = mInstance;
            synchronized (Gank.class) {
                if (mIns == null) {
                    mIns = new Gank();
                    mInstance = mIns;
                }
            }
        }
        return mInstance;
    }

    public GankService getGankService(){

        return mGankService;
    }
}
