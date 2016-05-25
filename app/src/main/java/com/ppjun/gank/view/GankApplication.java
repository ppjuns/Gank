package com.ppjun.gank.view;

import android.app.Application;

import com.anupcowkur.reservoir.Reservoir;

/**
 * @Package :com.ppjun.gank.view
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/22 10:47.
 */
public class GankApplication extends Application {


    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;

    @Override
    public void onCreate() {
        super.onCreate();

        initReservoir();
    }

    private void initReservoir() {


        try {
            Reservoir.init(this, CACHE_DATA_MAX_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
