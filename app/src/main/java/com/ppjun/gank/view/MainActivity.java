package com.ppjun.gank.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ppjun.gank.R;
import com.ppjun.gank.gank.GankApi;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
      {

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    private String[] TITLES={"每日精彩","美图","Android","iOS","前端","休息视频"};
    private ArrayList<SimpleFragment> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);


        mTabLayout.addTab(mTabLayout.newTab().setText(TITLES[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(TITLES[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(TITLES[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(TITLES[3]));
        mTabLayout.addTab(mTabLayout.newTab().setText(TITLES[4]));
        mTabLayout.addTab(mTabLayout.newTab().setText(TITLES[5]));

        mList=new ArrayList<>();
        mList.add(new SimpleFragment(GankApi.DATA_TYPE_DAILY));
        mList.add(new SimpleFragment(GankApi.DATA_TYPE_WELFARE));
        mList.add(new SimpleFragment(GankApi.DATA_TYPE_ANDROID));
        mList.add(new SimpleFragment(GankApi.DATA_TYPE_IOS));
        mList.add(new SimpleFragment(GankApi.DATA_TYPE_JS));
        mList.add(new SimpleFragment(GankApi.DATA_TYPE_VIDEO));



        SimplePagerAdapter adapter=new SimplePagerAdapter(getSupportFragmentManager(),mList,TITLES);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);



    }




    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SimplePagerAdapter extends FragmentPagerAdapter{
     private ArrayList<SimpleFragment> mList;
        private String[] TITTLES;

        public SimplePagerAdapter(FragmentManager fm, ArrayList<SimpleFragment> list,String[] str) {
            super(fm);
            this.mList=list;
            this.TITTLES=str;
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITTLES[position];
        }
    }

}
