package com.ppjun.gank.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * @Package :cn.studyjams.s1.sj14.ouhuijun.widget
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/23 09:34.
 */
public class GankSwipeRefreshLayout extends SwipeRefreshLayout {
    private CanChildScrollUpCallback mCanChildScrollUpCallback;

    public GankSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public GankSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanChildScrollUpCallback(CanChildScrollUpCallback callback) {
        this.mCanChildScrollUpCallback = callback;
    }

    /**
     * 防止子控件比如webview无法正常下拉刷新
     * @return
     */
    @Override
    public boolean canChildScrollUp() {
        if (mCanChildScrollUpCallback != null) {
            return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
        }
        return super.canChildScrollUp();
    }

    public interface CanChildScrollUpCallback {
        boolean canSwipeRefreshChildScrollUp();
    }
}
