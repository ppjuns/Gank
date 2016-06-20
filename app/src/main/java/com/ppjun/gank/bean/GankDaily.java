package com.ppjun.gank.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.Error;
import java.util.ArrayList;

/**
 * @Package :com.ppjun.gank.bean
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/19 09:27.
 */
public class GankDaily extends Error implements Serializable {
    /**
     * 返回结果
     **/
    @SerializedName("results")
    public DailyResults results;
    /**
     * 返回内容,一般是android ios js等
     **/
    @SerializedName("category")
    public ArrayList<String> category;


    /**
     * 成功返回的内容
     **/
    public static class DailyResults {
        @SerializedName("福利")
        public ArrayList<BaseGankData> welfareData;
        @SerializedName("Android")
        public ArrayList<BaseGankData> androidData;
        @SerializedName("iOS")
        public ArrayList<BaseGankData> iosData;
        @SerializedName("前端")
        public ArrayList<BaseGankData> jsData;
        @SerializedName("休息视频")
        public ArrayList<BaseGankData> videoData;
        @SerializedName("拓展资源")
        public ArrayList<BaseGankData> resourceData;

        public ArrayList<BaseGankData> appData;
        @SerializedName("瞎推荐")
        public ArrayList<BaseGankData> recommendData;

    }
}
