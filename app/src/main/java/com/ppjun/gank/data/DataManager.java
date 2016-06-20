package com.ppjun.gank.data;

import com.ppjun.gank.bean.BaseGankData;
import com.ppjun.gank.bean.GankDaily;
import com.ppjun.gank.gank.Gank;
import com.ppjun.gank.view.SimpleFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Package :com.ppjun.gank.data
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/20 10:03.
 */
public class DataManager {


    private static DataManager mInstance;

    public  static DataManager getInstance() {

        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    public Observable<List<GankDaily>> getDailyDataByNetWork(SimpleFragment.GankDate date) {

        return Observable.just(date).flatMapIterable(SimpleFragment.GankDate::getPastTime)
                .flatMap(gankDate -> {
                    /**
                     *
                     */
                    return Gank.getInsance().getGankService().getDaily(gankDate.getYear()
                            , gankDate.getMonth(), gankDate.getDay())
                            .filter(dailyData ->
                                    dailyData.results.androidData != null);
                    })
                .toSortedList((dailyData, dailyData2) -> {
                    return dailyData2.results.androidData.get(0).
                            publishedAt.compareTo(dailyData.results.androidData.get(0).publishedAt);

                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());



    }


    public Observable<ArrayList<BaseGankData>> getDataByNetWork(String type, int amount, int page){
        return Gank.getInsance().getGankService().getData(type,amount,page)
                .map(gankData -> gankData.results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }
}
