package com.ppjun.gank.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.ppjun.gank.R;
import com.ppjun.gank.widget.GlideCircleTransform;


/**
 * @Package :com.ppjun.gank.utils
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/20 15:58.
 */
public class GlideUtils {

    public static final String TAG="GlideUtils";
   public static void display(ImageView view,String url){
       displayUrl(view,url, R.drawable.image_default);


   }
    public static  void displayUrl(ImageView view,String url,int defaultImage){
      if(view==null){
          Log.e(TAG,"GlideUtils->displayUrl-> imageview is null");
          return;
      }
        //不能崩，判断当前activity finish了吗
        Context context=view.getContext();
        if(context instanceof Activity){
            if(((Activity) context).isFinishing()){
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(defaultImage)
                    .crossFade()
                    .centerCrop()
                    .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if(!view.isShown()){
                                view.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayNative(ImageView view,int resId){
        if(view==null){
            Log.e(TAG,"GlideUtils->displayUrl-> imageview is null");
            return;
        }
        //不能崩，判断当前activity finish了吗
        Context context=view.getContext();
        if(context instanceof Activity){
            if(((Activity) context).isFinishing()){
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(resId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .centerCrop()
                    .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if(!view.isShown()){
                                view.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void displayCircleHeader(ImageView view ,int res){

        if(view==null){
            Log.e(TAG,"GlideUtils->displayUrl-> imageview is null");
            return;
        }
        //不能崩，判断当前activity finish了吗
        Context context=view.getContext();
        if(context instanceof Activity){
            if(((Activity) context).isFinishing()){
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(res)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.image_default)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .crossFade()
                    .centerCrop()
                    .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if(!view.isShown()){
                                view.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
