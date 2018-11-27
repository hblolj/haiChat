package com.jt.wb.ys.common.widget.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jt.wb.ys.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {

    private final List<Data> mDataList;

    private AdapterListener<Data> mListener;

    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> mListener) {
        this(new ArrayList<Data>(), mListener);
    }

    public RecyclerAdapter(List<Data> mDataList, AdapterListener<Data> mListener) {
        this.mDataList = mDataList;
        this.mListener = mListener;
    }

    /**
     * 复写默认的布局类型返回
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    protected abstract int getItemViewType(int position, Data data);

    /**
     * @param parent RecyclerView
     * @param viewType 约定定为 xml 布局的 Id
     * @return
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        // 设置 View 的 Tag 为 ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        holder.unbinder = ButterKnife.bind(holder, root);
        // 绑定 callback
        holder.callback = this;

        return holder;
    }

    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 数据绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> holder, int position) {
        // 得到需要绑定的数据
        Data data = mDataList.get(position);
        // 触发 Holder 的绑定方法
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据并刷新
     * @param data 数据
     */
    public void add(Data data){
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据，并刷新
     * @param dataList 新数据
     */
    public void add(Data... dataList){
        if (dataList != null && dataList.length > 0){
            int startIndex = mDataList.size() - 1;
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startIndex, dataList.length);
        }
    }

    /**
     * 插入一堆数据，并刷新
     * @param dataList 新数据
     */
    public void add(Collection<Data> dataList){
        if (dataList != null && dataList.size() > 0){
            int startIndex = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startIndex, dataList.size());
        }
    }

    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 数据替换为一个新的集合
     * @param dataList 新数据
     */
    public void replace(Collection<Data> dataList){
        mDataList.clear();
        if (dataList == null || dataList.size() == 0){
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void update(Data data, ViewHolder<Data> holder) {
        int position = holder.getAdapterPosition();
        if (position >= 0){
            mDataList.remove(position);
            mDataList.add(position, data);
            notifyItemChanged(position);
        }
    }

    @Override
    public void onClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (mListener != null){
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (mListener != null){
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 自定义监听器
     * @param <Data>
     */
    public interface AdapterListener<Data>{

        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    public void setmListener(AdapterListener<Data> mListener) {
        this.mListener = mListener;
    }

    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder{

        private Unbinder unbinder;

        private AdapterCallback<Data> callback;

        protected Data mData;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用户绑定数据的触发
         * @param data 数据
         */
        void bind(Data data){
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候，会触发，必须复写
         * @param data 数据
         */
        protected abstract void onBind(Data data);

        /**
         * Holder 对自己对应的 Holder 进行更新
         * @param data 数据
         */
        public void updateData(Data data){
            if (this.callback != null){
                this.callback.update(data, this);
            }
        }
    }

    /**
     * 对回调接口做一次实现
     * @param <Data>
     */
    public static abstract class AdapterListenerImpl<Data> implements AdapterListener<Data>{

        @Override
        public void onItemClick(ViewHolder holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, Data data) {

        }
    }
}
