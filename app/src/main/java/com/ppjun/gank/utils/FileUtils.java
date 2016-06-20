package com.ppjun.gank.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @Package :com.ppjun.gank.utils
 * @Description :
 * @Author :Rc3
 * @Created at :2016/5/26 16:25.
 */

public class FileUtils {



    public static File getDiskCacheDir(Context context,String uniqueName){

        String cachePath;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())||
                !Environment.isExternalStorageRemovable()){

            cachePath=context.getExternalCacheDir().getPath();

        }else{
            cachePath=context.getCacheDir().getPath();

        }
        return new File(cachePath+ File.separator+uniqueName);
    }
}
