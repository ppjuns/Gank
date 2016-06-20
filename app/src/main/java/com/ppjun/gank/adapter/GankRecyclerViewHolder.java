package com.ppjun.gank.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @Package :com.ppjun.gank.adapter
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/21 11:30.
 */
public class GankRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;
    private View convertView;

    public GankRecyclerViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
        this.convertView = itemView;
    }

    public <T extends View> T findViewById(int viewId){
        View view=views.get(viewId);
        if(view==null){
            view=convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }






}
