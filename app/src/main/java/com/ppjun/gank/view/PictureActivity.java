package com.ppjun.gank.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ppjun.gank.R;
import com.ppjun.gank.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Package :com.ppjun.gank.view
 * @Description :
 * @Author :Rc3
 * @Created at :2016/5/26 14:35.
 */

public class PictureActivity extends AppCompatActivity {
    @Bind(R.id.welfare_img)
    ImageView image;
    private GlideBitmapDrawable mGlideBitmapDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        ButterKnife.bind(this);


        String imageUrl = getIntent().getStringExtra("imageurl");
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(this)
                    .load(imageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            mGlideBitmapDrawable = (GlideBitmapDrawable) resource;
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(image);


        }

        image.setOnLongClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setMessage("保存图片")
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> {

                        dialog.dismiss();

                    }).setPositiveButton(android.R.string.ok, (dialog, which) -> {

                dialog.dismiss();
                download();//保存图片
            }).show();
            return false;
        });
    }

    public void download() {
        if (mGlideBitmapDrawable != null) {
            getPicObservable(mGlideBitmapDrawable, this).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.i("TAG","onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.i("TAG","onError");
                }

                @Override
                public void onNext(String s) {
                    Log.i("TAG","onNext");
                    Log.i("TAG",s);
                }
            });
        }
    }


    public Observable<String> getPicObservable(final GlideBitmapDrawable drawable, Context context) {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                File file = FileUtils.getDiskCacheDir(context, System.currentTimeMillis() + ".jpg");

                try {
                    FileOutputStream output = new FileOutputStream(file);

                    drawable.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, output);
                    output.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
