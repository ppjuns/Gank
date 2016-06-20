package com.ppjun.gank.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * @Package :com.ppjun.gank.utils
 * @Description :
 * @Author :Rc3
 * @Created at :2016/6/20 08:34.
 */

public class Typefaces {

    private static final String TAG = Typefaces.class.getSimpleName();
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();


    private Typefaces(){}

    static Typeface get(Context context,String assetPath ){

        synchronized (cache){

            if(!cache.contains(assetPath)){
                try {
                    Typeface t=Typeface.createFromAsset(context.getAssets(),assetPath);
                    cache.put(assetPath,t);
                } catch (Exception e) {
                    Log.e(TAG,"Typefaces not found");
                    e.printStackTrace();
                }
            }
        }

        return  cache.get(assetPath);
    }


    public static Typeface getRobotoBlack(Context context){
        return  get(context,"fonts/Roboto-Black.ttf");
    }
}
