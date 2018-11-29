package com.jt.wb.ys.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jt.wb.ys.common.R;
import com.jt.wb.ys.common.widget.recycler.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class GalleryView extends RecyclerView {

    private static final int LOADER_ID = 0X100;

    // 最大选中图片数量
    private static final int MAX_IMAGE_COUNT = 3;
    // 最小图片大小
    private static final int MIN_IMAGE_FILE_SIZE = 10 * 1024;

    private Adapter mAdapter = new Adapter();

    private LoaderCallback mLoaderCallBack = new LoaderCallback();

    private List<Image> mSelectedImages = new LinkedList<>();

    private SelectChangeListener mListener;

    public GalleryView(Context context) {
        super(context);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        setLayoutManager(new GridLayoutManager(getContext(), 4));

        setAdapter(mAdapter);

        mAdapter.setmListener(new RecyclerAdapter.AdapterListenerImpl<GalleryView.Image>() {

            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Image image) {
                // 点击改变 Cell 的状态，更新界面
                if (onItemSelect(image)){
                    //noinspection unchecked
                    holder.updateData(image);
                }
            }

        });
    }

    /**
     * 初始化方法
     * @param loaderManager
     * @return 返回一个 LoaderId，可用于销毁 Loader
     */
    public int setUp(LoaderManager loaderManager, SelectChangeListener listener){
        loaderManager.initLoader(LOADER_ID, null, mLoaderCallBack);
        mListener = listener;
        return LOADER_ID;
    }

    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor>{

        private final String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
        };

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

            // 创建一个 Loader
            if (id == LOADER_ID){
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        // 倒序查询
                        IMAGE_PROJECTION[2] + " DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

            // 当 Loader 记载完成时

            List<Image> images = new ArrayList<>();

            if (data != null){
                int count = data.getCount();
                if (count > 0){
                    // 移动游标到开始
                    data.moveToFirst();

                    int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);

                    do {
                        // 循环读取直到最后
                        int id = data.getInt(indexId);
                        String path = data.getString(indexPath);
                        long date = data.getLong(indexDate);

                        File file = new File(path);
                        if (!file.exists() || file.length() < MIN_IMAGE_FILE_SIZE){
                            // 图片不存在或图片太小
                            continue;
                        }

                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = date;
                        images.add(image);

                    }while (data.moveToNext());
                }
            }

            updateSource(images);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            // 当 Loader 销毁或重置时
            updateSource(null);
        }
    }

    /**
     * 通知 Adapter 数据更改的方法
     * @param images
     */
    private void updateSource(List<Image> images){
        mAdapter.replace(images);
    }

    /**
     * Cell 点击的具体逻辑
     * @param image
     * @return true 代表进行了数据更改，需要刷新。反之不需要
     */
    private Boolean onItemSelect(Image image){

        // 是否需要进行刷新
        boolean notifyRefresh;

        if (mSelectedImages.contains(image)){
            // 之前存在，移除
            mSelectedImages.remove(image);
            image.isSelect = false;
            // 状态已经改变，需要更新
            notifyRefresh = true;
        }else {
            if (mSelectedImages.size() >= MAX_IMAGE_COUNT){
                // Toast 一个提示
                String notic = getResources().getString(R.string.label_gallery_select_max_size);
                Toast.makeText(getContext(), String.format(notic, MAX_IMAGE_COUNT + ""), Toast.LENGTH_SHORT).show();
                notifyRefresh = false;
            }else {
                mSelectedImages.add(image);
                image.isSelect = true;
                notifyRefresh = true;
            }
        }

        if (notifyRefresh){
            // 如果数据有更改，需要通知外面监听者
            notifySelectChanged();
        }

        return true;
    }

    /**
     * 通知选中状态改变
     */
    private void notifySelectChanged(){
        // 得到监听回调
        SelectChangeListener listener = this.mListener;
        if (listener != null){
            listener.onSelectedCountChange(mSelectedImages.size());
        }
    }

    /**
     * 得到选中的图片的全部地址
     * @return 返回一个字符串数组
     */
    public String[] getSelectedPath(){

        String[] paths = new String[mSelectedImages.size()];
        int index = 0;
        for (Image image : mSelectedImages){
            paths[index++] = image.path;
        }

        return paths;
    }

    /**
     * 可以进行清空选中的图片
     */
    public void clear(){
        for (Image image : mSelectedImages){
            // 重置状态
            image.isSelect = false;
        }
        mSelectedImages.clear();
        // 通知更新
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 内部数据接口
     */
    private static class Image{
        // 数据 Id
        int id;
        // 图片路径
        String path;
        // 图片创建日期
        long date;
        // 是否选中
        boolean isSelect;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Image image = (Image) o;
            return Objects.equals(path, image.path);
        }

        @Override
        public int hashCode() {

            return Objects.hash(path);
        }

        @Override
        public String toString() {
            return "Image{" +
                    "id=" + id +
                    ", path='" + path + '\'' +
                    ", date=" + date +
                    '}';
        }
    }

    private class Adapter extends RecyclerAdapter<Image>{

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(android.view.View root, int viewType) {
            return new GalleryView.ViewHolder(root);
        }
    }

    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image>{

        private ImageView mPic;
        private View mShade;
        private CheckBox mSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            mPic = itemView.findViewById(R.id.im_image);
            mShade = itemView.findViewById(R.id.view_shade);
            mSelected = itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void onBind(Image image) {
            Glide.with(getContext())
                    .load(image.path)
                    // 不使用缓存，因为是本地图片
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    // 默认颜色
                    .placeholder(R.color.grey_200)
                    .into(mPic);

            mShade.setVisibility(image.isSelect ? VISIBLE : INVISIBLE);
            mSelected.setChecked(image.isSelect);
            mSelected.setVisibility(VISIBLE);
        }
    }

    /**
     * 对外的一个监听器
     */
    public interface SelectChangeListener{
        void onSelectedCountChange(int count);
    }
}
