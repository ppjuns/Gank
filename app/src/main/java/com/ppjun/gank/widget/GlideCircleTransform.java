package com.ppjun.gank.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @Package :com.ppjun.gank.widget
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/20 16:32.
 */
public class GlideCircleTransform extends BitmapTransformation {

    public GlideCircleTransform(Context context){
        super(context);


    }
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap bitmap, int outWidth, int outHeight) {
        if(bitmap==null) return null;
        int size=Math.min(bitmap.getWidth(),bitmap.getHeight());
        int x=(bitmap.getWidth()-size)/2;
        int y=(bitmap.getHeight()-size)/2;
        Bitmap squared=Bitmap.createBitmap(bitmap,x,y,size,size);
        Bitmap result =pool.get(size,size,Bitmap.Config.ARGB_8888);
        if(result==null){
            result=Bitmap.createBitmap(size,size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas=new Canvas(result);
        Paint paint =new Paint();
        paint.setShader(new BitmapShader(squared,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r=size/2f;
        canvas.drawCircle(r,r,r,paint);

        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
