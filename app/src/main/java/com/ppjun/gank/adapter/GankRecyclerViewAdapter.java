package com.ppjun.gank.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Package :com.ppjun.gank.adapter
 * @Description :
 * @Author :Rc3
 * @Created at :2016/4/21 11:08.
 */
public abstract  class GankRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList mList;
    private GankRecyclerViewHolder.OnItemClickListener mOnItemClickListener;
    private GankRecyclerViewHolder.OnItemLongClickListener mOnItemLongClickListener;

    public GankRecyclerViewAdapter(){

        mList=new ArrayList();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType<0)return null;
        if(this.getItemLayouts()==null)return null;
        int[] layoutIds=this.getItemLayouts();
        if(layoutIds.length<1) return null;
        int itemLayoutId;
        if(layoutIds.length==1){
            itemLayoutId=layoutIds[0];
        }else{
            itemLayoutId=layoutIds[viewType];
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(itemLayoutId,parent,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new GankRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            GankRecyclerViewHolder gankRecyclerViewHolder= (GankRecyclerViewHolder) holder;
            this.onBindRecyclerViewHolder(gankRecyclerViewHolder,position);
            gankRecyclerViewHolder.setOnItemClickListener(this.mOnItemClickListener,position);
            gankRecyclerViewHolder.setOnItemLongClickListener(this.mOnItemLongClickListener,position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public abstract void onBindRecyclerViewHolder(GankRecyclerViewHolder viewHolder,int position);

    public abstract  int getRecyclerViewItemType(int position);

    @Override
    public int getItemViewType(int position) {
        return this.getRecyclerViewItemType(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public <T> T getItem(int position){
      return  (T)this.mList.get(position);
    }
    public void setList(List list){

        if (mList==null)return;
        this.mList.clear();
        this.mList.addAll(list);

    }
    public void clear(){
        if (mList==null)return;
        mList.clear();
    }
    public void remove(Object o){

        if (mList==null)return;
        mList.remove(o);
    }
    public List getList(){
        if (mList==null) return null;
        return this.mList;
    }
    public void addAll(Collection list){
        if (mList==null)return;
        this.mList.addAll(list);
    }

    public <T> T getItemByPosition(int position){
        return this.getItem(position);
    }

    public abstract  int[] getItemLayouts();



    public void setOnItemClickListener(GankRecyclerViewHolder.OnItemClickListener listener){
        this.mOnItemClickListener=listener;

    }

    public void setOnItemLongClickListener(GankRecyclerViewHolder.OnItemLongClickListener listener){
        this.mOnItemLongClickListener=listener;
    }

}
