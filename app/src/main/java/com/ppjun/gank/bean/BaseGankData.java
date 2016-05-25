package com.ppjun.gank.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package :com.ppjun.gank.bean
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/18 17:57.
 */
public class BaseGankData implements Serializable {
    /**
     * 发布人
     **/
    @SerializedName("who")
    public String who;
    /**
     * 出版时间
     **/
    @SerializedName("publishedAt")
    public Date publishedAt;
    /**
     * 标题
     **/
    @SerializedName("desc")
    public String desc;
    /**
     * 种类一般是福利
     **/
    @SerializedName("type")
    public String type;
    /**
     * 图片的url
     ***/
    @SerializedName("url")
    public String url;
    /**
     * 是否可用，一般可用
     **/
    @SerializedName("used")
    public boolean used;
    /**
     * 对象id
     **/
    @SerializedName("objectId")
    public String objectId;
    /**
     * 创建时间
     **/
    @SerializedName("createdAt")
    public Date createdAt;
    /**
     * 更新时间
     **/
    @SerializedName("updatedAt")
    public Date updatedAt;
}
