package com.ppjun.gank.view;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ppjun.gank.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Package :com.ppjun.gank.view
 * @Description :
 * @Author :Rc3
 * @Created at :2016/5/26 20:19.
 */

public class H5Activity extends Activity {


    @Bind(R.id.webview)
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

        WebSettings WebSettings=mWebView.getSettings();
        WebSettings.setJavaScriptEnabled(true);
        WebSettings.setDisplayZoomControls(false);
        WebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        WebSettings.setDefaultTextEncodingName("utf-8");

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }

    private void   initData(){
        String url=getIntent().getStringExtra("url");
        mWebView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
