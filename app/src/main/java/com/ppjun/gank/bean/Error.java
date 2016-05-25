package com.ppjun.gank.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Package :com.ppjun.gank.bean
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/19 09:31.
 */
public class Error  implements Serializable{
    @SerializedName("error")
    public boolean error;
    @SerializedName("msg")
    public String msg;
}
