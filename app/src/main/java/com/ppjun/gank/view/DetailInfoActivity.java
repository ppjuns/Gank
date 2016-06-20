package com.ppjun.gank.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ppjun.gank.R;
import com.ppjun.gank.adapter.DailyDetialAdapter;
import com.ppjun.gank.bean.BaseGankData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Package :com.ppjun.gank.view
 * @Description :
 * @Author :Rc3
 * @Created at :2016/5/26 08:49.
 */

public class DetailInfoActivity extends Activity {


    @Bind(R.id.dailyrecyclerview)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_dailydetial);
         ButterKnife.bind(this);

        initView();

    }


    private  ArrayList<ArrayList<BaseGankData>> getList(){

       return  (ArrayList<ArrayList<BaseGankData>>) getIntent().getSerializableExtra("list");
    }

    private void initView() {
        LinearLayoutManager manager =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        DailyDetialAdapter adapter=new DailyDetialAdapter(this);
        adapter.setList(getList());
        mRecyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new DailyDetialAdapter.onItemClickListener() {
            @Override
            public void onWelfareClick(String url, View v) {
                Intent intent = new Intent(DetailInfoActivity.this, PictureActivity.class);

                intent.putExtra("imageurl",url);
                startActivity(intent);
            }

            @Override
            public void onCardItemClick(String url, View v) {
                //数据界面
                Intent intent = new Intent(DetailInfoActivity.this, H5Activity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }
}
