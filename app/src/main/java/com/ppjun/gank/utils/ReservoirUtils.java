package com.ppjun.gank.utils;

import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirDeleteCallback;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * @Package :com.ppjun.gank.utils
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/22 10:22.
 */
public class ReservoirUtils {
    public static final String TAG = "ReservoirUtils";

    public static void put(String key, Object object) {

        if (object == null) return;
        Reservoir.putAsync(key, object, new ReservoirPutCallback() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "put success:key" + key + "  object=" + object.getClass());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean contains(String key) {
        try {
            return Reservoir.contains(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void delete(String key) {
        if (ReservoirUtils.contains(key))
            Reservoir.deleteAsync(key);
    }

    public  static void refresh(String key, Object object) {
        if (ReservoirUtils.contains(key)) {
            Reservoir.deleteAsync(key, new ReservoirDeleteCallback() {
                @Override
                public void onSuccess() {
                    ReservoirUtils.put(key, object);
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            ReservoirUtils.put(key, object);
        }
    }

    public static <T> Observable<T> get(String key, Class<T> clazz) {
        return Reservoir.getAsync(key, clazz);

    }

    public static <T> Observable<T> get(Class<T> clazz) {
        String key = clazz.getSimpleName();
        return get(key, clazz);
    }

    public static <T> void get(final String key, final Type typeOfT, final ReservoirGetCallback<T> callback) {
        Reservoir.getAsync(key, typeOfT, callback);
    }

}
