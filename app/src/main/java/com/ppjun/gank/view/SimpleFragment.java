package com.ppjun.gank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.google.gson.reflect.TypeToken;
import com.ppjun.gank.R;
import com.ppjun.gank.adapter.SimpleAdapter;
import com.ppjun.gank.bean.BaseGankData;
import com.ppjun.gank.bean.GankDaily;
import com.ppjun.gank.data.DataManager;
import com.ppjun.gank.gank.GankApi;
import com.ppjun.gank.gank.GankType;
import com.ppjun.gank.utils.Dateutils;
import com.ppjun.gank.utils.ReservoirUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Package :com.ppjun.gank
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/18 15:51.
 */
public class SimpleFragment extends Fragment {
    public static final String TAG = "SimpleFragment";
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    int page = 1;
    GankDate gankDate;
    SimpleAdapter adapter;
    private ArrayList<GankDaily> mList;
    private String mResquestParam;
    private boolean refreshStatus = false;
    private boolean isLoadMore;



    public SimpleFragment(String type) {
        this.mResquestParam = type;


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;


    }

    private void initViews() {
        mList = new ArrayList<>();
        //下拉刷新颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下来动作
                if (mResquestParam == GankApi.DATA_TYPE_DAILY) {
                    getDailyData(true);
                } else if (mResquestParam == GankApi.DATA_TYPE_WELFARE) {

                    getWelfareData(true);
                } else {
                    getTechData(true);

                }
            }
        });
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            refreshStatus = true;
        });

        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);


        gankDate = new GankDate(calendar);


        if (mResquestParam == GankApi.DATA_TYPE_DAILY) {
            getDailyData(true);
        } else if (mResquestParam == GankApi.DATA_TYPE_WELFARE) {

            getWelfareData(true);
        } else {
            getTechData(true);

        }
    }


    public void initFragments(List list) {
        mSwipeRefreshLayout.post(() -> {

            mSwipeRefreshLayout.setRefreshing(false);
            refreshStatus = false;
        });
               if(!isLoadMore){
                   mList.clear();
                   mList.addAll(list);
               }else{

                   mList.addAll(list);
                   adapter.setList(mList);
                   adapter.notifyDataSetChanged();
                   return;
               }



        adapter = new SimpleAdapter(getActivity(), mResquestParam);

        adapter.setList(mList);
        adapter.notifyDataSetChanged();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnWelfareClickListener(new SimpleAdapter.onWelfareClick() {
            @Override
            public void onClick(View v, String url) {
                Intent intent = new Intent(getActivity(), PictureActivity.class);
                intent.putExtra("imageurl", url);
                startActivity(intent);
            }
        });
        adapter.setOnItemClick((v, position) -> {
            Object o = adapter.getItem(position);
            if (o instanceof BaseGankData) {
                //福利，数据的界面
                BaseGankData baseGankData = (BaseGankData) o;
                if (mResquestParam == GankApi.DATA_TYPE_WELFARE) {
                    //福利界面
                    Intent intent = new Intent(getActivity(), PictureActivity.class);
                    intent.putExtra("imageurl", baseGankData.url);
                    startActivity(intent);
                } else {
                    //数据界面
                    Intent intent = new Intent(getActivity(), H5Activity.class);
                    intent.putExtra("url", baseGankData.url);
                    startActivity(intent);

                }
            } else if (o instanceof GankDaily) {
                //每日数据界面
                GankDaily gankDaily = (GankDaily) o;

                //先组装list 再跳转
                this.getDailyDetialData(gankDaily.results).subscribe(new Subscriber<ArrayList<ArrayList<BaseGankData>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<ArrayList<BaseGankData>> arrayLists) {
                       // listener.intentlist(arrayLists);
                        //跳转
                     Intent intent = new Intent(getActivity(), DetailInfoActivity.class);

                        intent.putExtra("list",arrayLists);
                        startActivity(intent);


                    }
                });

            }

        });
        mRecyclerView.setOnScrollListener(this.getRecyclerViewOnScrollListener());
    }

    public Observable<ArrayList<ArrayList<BaseGankData>>> getDailyDetialData(GankDaily.DailyResults result) {
       return Observable.just(result)
                .map(dailyResults -> {
                    ArrayList<ArrayList<BaseGankData>> list = new ArrayList<ArrayList<BaseGankData>>();
                    if (dailyResults.welfareData != null && dailyResults.welfareData.size() > 0)
                        list.add(dailyResults.welfareData);
                    if (dailyResults.androidData != null && dailyResults.androidData.size() > 0)
                        list.add(dailyResults.androidData);
                    if (dailyResults.iosData != null && dailyResults.iosData.size() > 0)
                        list.add(dailyResults.iosData);
                    if (dailyResults.jsData != null && dailyResults.jsData.size() > 0)
                        list.add(dailyResults.jsData);
                    if (dailyResults.videoData != null && dailyResults.videoData.size() > 0)
                        list.add(dailyResults.videoData);
                    return list;
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    private void getDailyData(final boolean isfresh) {
   if(isfresh){
      isLoadMore=false;
       page=1;
   }


        DataManager.getInstance().getDailyDataByNetWork(this.gankDate).subscribe(
                new Subscriber<List<GankDaily>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //可以通过reservoir获取
                        Log.i("TAG", "list null没网");
                        Type resultType = new TypeToken<List<GankDaily>>() {
                        }.getType();

                        ReservoirUtils.get(GankType.daily + "", resultType, new ReservoirGetCallback<List<GankDaily>>() {
                            @Override
                            public void onSuccess(List<GankDaily> gankDailies) {

                                initFragments(gankDailies);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                e.printStackTrace();
                            }
                        });


                    }

                    @Override
                    public void onNext(List<GankDaily> gankDailies) {

                        if (gankDailies == null) {
                            Log.i("TAG", "list null");
                        } else {

                            ReservoirUtils.refresh(GankType.daily + "", gankDailies);


                            initFragments(gankDailies);


                        }
                    }
                }
        );

    }

    private void getTechData(boolean isflesh) {

        if(isflesh){
            page=1;
            isLoadMore=false;
        }

        DataManager.getInstance().getDataByNetWork(mResquestParam, 10, page).subscribe(
                new Subscriber<ArrayList<BaseGankData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Type resultType = new TypeToken<List<BaseGankData>>() {
                        }.getType();
                        ReservoirUtils.get(mResquestParam, resultType, new ReservoirGetCallback<List<BaseGankData>>() {
                            @Override
                            public void onSuccess(List<BaseGankData> baseGankDatas) {
                                initFragments(baseGankDatas);
                            }

                            @Override
                            public void onFailure(Exception e) {

                            }
                        });

                    }

                    @Override
                    public void onNext(ArrayList<BaseGankData> baseGankDatas) {

                        if (baseGankDatas == null) {
                            Log.i("TAG", "list null");
                        } else {
                            ReservoirUtils.refresh(mResquestParam, baseGankDatas);
                            initFragments(baseGankDatas);


                        }
                    }
                }
        );
    }

    private void getWelfareData(boolean isfresh) {


        if(isfresh){
            isLoadMore=false;
            page=1;
        }

        DataManager.getInstance().getDataByNetWork(GankApi.DATA_TYPE_WELFARE, 10, page).subscribe(
                new Subscriber<ArrayList<BaseGankData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Type resultType = new TypeToken<List<BaseGankData>>() {
                        }.getType();
                        ReservoirUtils.get(GankType.welfare + "", resultType, new ReservoirGetCallback<List<BaseGankData>>() {
                            @Override
                            public void onSuccess(List<BaseGankData> baseGankDatas) {
                                initFragments(baseGankDatas);
                            }

                            @Override
                            public void onFailure(Exception e) {

                            }
                        });

                    }

                    @Override
                    public void onNext(ArrayList<BaseGankData> baseGankDatas) {

                        if (baseGankDatas == null) {
                            Log.i("TAG", "list null");
                        } else {
                            ReservoirUtils.refresh(GankType.welfare + "", baseGankDatas);
                            initFragments(baseGankDatas);


                        }
                    }
                }
        );
    }

    private RecyclerView.OnScrollListener getRecyclerViewOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            private boolean tolast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //显示最后一个item

                        if (tolast && manager.findLastCompletelyVisibleItemPosition() == (manager.getItemCount() - 1)) {
                            //开始加载更多
                         loadMoreRequest();



                        }

                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0)
                    tolast = true;
                else
                    tolast = false;
            }
        };


    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private void loadMoreRequest() {

        if (!this.refreshStatus) {
            mSwipeRefreshLayout.post(() -> {
                mSwipeRefreshLayout.setRefreshing(true);

            });
            this.setPage(page + 1);
            this.loadMore(mResquestParam);
            isLoadMore=true;
        }

    }

    private void loadMore(String gankType) {
        if (gankType == GankApi.DATA_TYPE_DAILY) {
            getDailyData(false);
        }else if(gankType==GankApi.DATA_TYPE_WELFARE){
            getWelfareData(false);
        }else{

         getTechData(false);
        }

    }

    public class GankDate {

        private Calendar calendar;

        public GankDate(Calendar calendar) {
            this.calendar = calendar;
        }

        public int getYear() {

            return calendar.get(Calendar.YEAR);
        }

        public int getMonth() {
            //因为返回值从0开始
            return calendar.get(Calendar.MONTH) + 1;
        }

        public int getDay() {
            return calendar.get(Calendar.DAY_OF_MONTH);
        }


        public List<GankDate> getPastTime() {
            List<GankDate> dateList = new ArrayList<>();
            for (int i = 0; i < GankApi.DEFAULT_DAILY_SIZE; i++) {
                //当前时间戳 - 一天的一天或者几天的时间戳
                long time = this.calendar.getTimeInMillis() - ((page - 1) * GankApi.DEFAULT_DAILY_SIZE * Dateutils.ONE_DAY) - i * Dateutils.ONE_DAY;
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(time);

                GankDate date = new GankDate(c);
                dateList.add(date);
                Log.i("getPastTime", dateList.size() + "");
            }
            return dateList;
        }
    }
}
