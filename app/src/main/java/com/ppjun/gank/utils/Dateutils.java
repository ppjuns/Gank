package com.ppjun.gank.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package :com.ppjun.gank.utils
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/19 16:49.
 */
public class Dateutils {

//计算时间戳，单位毫秒
    public static final long ONE_SECOND=1000;
    public static final long ONE_MINUTE=ONE_SECOND*60;
    public static final long ONE_HOUR=ONE_MINUTE*60;
    public static final long ONE_DAY=ONE_HOUR*24;


public static String date2String(Date date, String fmt){
    SimpleDateFormat sdf=new SimpleDateFormat(fmt);
    return sdf.format(date);
}

}
