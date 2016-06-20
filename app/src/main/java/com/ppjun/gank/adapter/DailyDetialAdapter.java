package com.ppjun.gank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ppjun.gank.R;
import com.ppjun.gank.bean.BaseGankData;
import com.ppjun.gank.utils.GlideUtils;
import com.ppjun.gank.widget.RatioImageView;

import java.util.ArrayList;

import static com.ppjun.gank.gank.GankApi.DATA_TYPE_WELFARE;

/**
 * @Package :com.ppjun.gank.adapter
 * @Description :
 * @Author :Rc3
 * @Created at :2016/5/27 10:48.
 */

public class DailyDetialAdapter extends GankRecyclerViewAdapter {

    Context mContext;
    private static final int dividerColor=0xffCCCCCC;
    public DailyDetialAdapter(Context context) {
        this.mContext = context;

    }

    @Override
    public void onBindRecyclerViewHolder(GankRecyclerViewHolder viewHolder, int position) {
        ArrayList<BaseGankData> gankDatas = this.getItem(position);
        if (gankDatas == null || gankDatas.size() < 0) return;
        LinearLayout detial = viewHolder.findViewById(R.id.ll_daily);
        detial.removeAllViews();

        //显示type
        for (int i = 0; i < gankDatas.size(); i++) {
            BaseGankData gankData = gankDatas.get(i);
            if (i == 0) {
                TextView type = new TextView(mContext);
                type.setText(gankData.type);
                type.setTextSize(16);
                //type.setTypeface(Typefaces.getRobotoBlack(mContext));
                type.setPadding(12,12,12,12);
                type.setTextColor(Color.parseColor("#000000"));
                detial.addView(type);
                detial.addView(createDivider());
            }
            if (gankData.type.equals(DATA_TYPE_WELFARE)) {
                RatioImageView imageView = (RatioImageView) LayoutInflater.from(mContext).inflate(R.layout.view_ratioimage, null);

                GlideUtils.display(imageView, gankData.url);

               imageView.setOnClickListener(v->{
                   if(listener!=null)
                       listener.onWelfareClick(gankData.url,imageView);
               });
                detial.addView(imageView);
            } else {
                //非福利的其他数据
                TextView type = new TextView(mContext);
                type.setText(gankData.desc);
                type.setTextSize(12);
                type.setPadding(8,12,8,12);
                type.setTextColor(Color.parseColor("#868686"));
                detial.addView(type);
                type.setOnClickListener(v-> {

                        if(listener!=null)
                            listener.onCardItemClick(gankData.url,v);


                });


            }


        }

    }

    @Override
    public int getRecyclerViewItemType(int position) {
        return position;
    }

    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.item_dailydetial};
    }
    onItemClickListener listener;

    public interface onItemClickListener {
        void onWelfareClick(String url, View v);

        void onCardItemClick(String url, View v);
    }

    public void setItemClickListener(onItemClickListener listener){

        this.listener=listener;
    }

    public View createDivider(){
        View divider =new View(mContext);
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        divider.setBackgroundColor(dividerColor);
        return  divider;
    }
}
